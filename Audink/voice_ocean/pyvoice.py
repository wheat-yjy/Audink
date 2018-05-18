import math
import re
import numpy as np

from voice_ocean import voice_recognition
from pydub import AudioSegment

import voice_ocean.fileprocess as fip
from voice_ocean.util import voice_emotion as ve


# 句子音效背景音乐标记

class vop(object):
    fileDir = None
    filePath = None
    sound = None
    read_list = []
    emotion_lists = []

    voice_recog = None


    def __init__(self):
        pass

    # v2.0
    # voice文件从对应文件夹中找(原为从temp中找)
    # voicetempname为voice的名字
    # voicedir为分配的在voice_sea中的对应文件夹
    def __init__(self,fileDir,filePath):

        self.fileDir = fileDir
        self.filePath = filePath
        # print(self.filePath)
        # 音频导入
        self.sound = AudioSegment.from_file(self.filePath, format=fip.get_file_form(self.filePath),frame_rate=16000)
        self.sound.set_frame_rate(16000)
        self.voice_recog = voice_recognition.recog()
        # print('sample_width', self.sound.sample_width)
        # print('frame_rate:', self.sound.frame_rate)
        # print('frame_width', self.sound.frame_width)

    #空白音频的创建
    def add_blank(sound):
        ten_second_silence = AudioSegment.silent(duration=10000)
        ten_second_silence.export("silenceblanck.wav",format='wav')
        # 合并原音频和空白
        # sound1 = AudioSegment.from_file('东风破_10s.mp3',format='mp3')
        # combined = ten_second_silence + sound1
        # combined.export('output1.wav',format='wav')


    # 返回最小的dBFS(除了-inf)
    def __get_min_dBFS(self,soundlist):
        min_dBFS = 0
        for x in soundlist:
            if (x < min_dBFS) and (not math.isinf(x)):
                min_dBFS = x
        return min_dBFS

    def getdBFS(self,second):
        intervel = 100  # 小音频长度
        sounddB = [self.sound[x * intervel:x * intervel + intervel].dBFS for x in range(len(self.sound) // intervel)]
        min_dBFS = self.__get_min_dBFS(sounddB)

        print('min_dBFS:', min_dBFS)
        print(self.sound[second].dBFS)

    # 分析音乐图像
    # voice_dir 为分配的文件名(随机不重复)，在voice_sea中
    # voice_name 为下载下来的文件名，在temp中
    def analysize_music(self):

        self.read_list = []
        self.emotion_lists = []
        # 得到该文件名的文件在temp中的路径

        # 初始化的时候,sound已经初始化完毕
        # print('len(sound)',len(self.sound))

        # 2.确定音频的最小单位intervel，以及最小音量
        intervel = 100 #小音频长度
        sounddB = [self.sound[x*intervel:x*intervel+intervel].dBFS for x in range(len(self.sound)//intervel)]
        min_dBFS = self.__get_min_dBFS(sounddB)
        max_dBFS = self.sound.max_dBFS
        # print('max_dBFS:',self.sound.max_dBFS)
        print('min_dBFS:',min_dBFS)
        # print('length of soundarray:',len(sounddB))


        # 2.根据音量来选择空白段
        blanck_list = []

        for x in range(len(sounddB)):
            if ( min_dBFS  <= sounddB[x] and sounddB[x] <= (abs(max_dBFS - min_dBFS)) * 0.47 + min_dBFS) or math.isinf(sounddB[x]):
                # print(x/10)
                blanck_list.append([(x*intervel)/1000,(x*intervel+intervel)/1000])


        # 3.空白段合并
        new1_blank_list = []
        start = 0
        end = 0
        for x in range(len(blanck_list)):
            if x != len(blanck_list)-1:
                if blanck_list[x][1] == blanck_list[x+1][0]:
                    end = x + 1
                elif blanck_list[x-1][1] != blanck_list[x][0] and blanck_list[x][1] != blanck_list[x+1][0]:
                    start = x +1
                    end = start
                else:
                    new1_blank_list.append([blanck_list[start][0], blanck_list[end][0]])
                    start = x + 1
                    end = start
            else:
                new1_blank_list.append([blanck_list[start][0], blanck_list[end][0]])

        # 4.窗口检测
        # blank_list为得到的筛选过的空白音频组
        blanck_list = []
        for x in new1_blank_list:
            # print(x)
            #窗口大小为200毫秒
            if (x[1]-x[0] > 0.3):
                blanck_list.append([x[0],x[1]])

        # 5.得到的空白音频段
        print('log',blanck_list)
        for x in blanck_list:
            print(x)

        # plt.plot([ x*200/1000 for x in (range(len(sounddB)))],sounddB)
        # plt.xlabel('time')
        # plt.ylabel('dBFS')
        # plt.show()

        # 选出朗读音频，丢语音情感处理中分析，格式
        sound_read = None

        sound_Name = 'sound'
        if blanck_list[0][0] == 0:
            blanck_list.pop(0)
        # print ('len:',len(blanck_list))

        for x in range(len(blanck_list)):
            if x==0:
                # sound_read = self.sound[0:(blanck_list[0][0]*1000)]
                self.read_list.append([0,(blanck_list[0][0]*1000)])
            else:
                # sound_read = self.sound[(blanck_list[x-1][1]*1000):(blanck_list[x][0]*1000)]
                self.read_list.append([(blanck_list[x-1][1]*1000),(blanck_list[x][0]*1000)])

            # sound_read.export(self.fileDir +'/emot/'+ sound_Name +str(x)+".wav",format="wav")
        # sound_read = self.sound[(blanck_list[len(blanck_list)-1][1] * 1000):len(self.sound)]
        self.read_list.append([(blanck_list[len(blanck_list)-1][1] * 1000), len(self.sound)])
        # sound_read.export(self.fileDir +'/emot/'+ sound_Name + str(len(blanck_list)) + ".wav", format="wav")

        new_read_list = []
        count_ = -1
        # 对于得到的非空白音频区域再进行一次合并，防止出现短音
        for i in range(len(self.read_list)):
            # print('read_list'+str(i),self.read_list[i])
            start_ = self.read_list[i][0]
            end_ = self.read_list[i][1]
            intervel = 40  # 小音频长度
            sounddB_ = [self.sound[x * intervel:x * intervel + intervel].dBFS for x in
                        range(int(start_ // intervel), int(end_ // intervel))]
            np_sounddB = np.array(sounddB_)
            if not math.isinf(np_sounddB.mean()):

                if end_-start_ >= 1000 or start_ == 0:
                    new_read_list.append(self.read_list[i])
                    count_ += 1
                else:
                    print(new_read_list)
                    new_read_list[count_][1] = end_
        self.read_list = new_read_list
        for i in range(len(self.read_list)):
            print(i,self.read_list[i])
            sound_read = self.sound[self.read_list[i][0]: self.read_list[i][1]]
            sound_read.export(self.fileDir + '/emot/' + sound_Name + str(i) + ".wav", format="wav")
            print(i,self.fileDir + '/emot/' + sound_Name + str(i) + ".wav")
        print('print read_list',self.read_list)
        # self.emotion_lists = self.get_emotion_lists(len(blanck_list))
        return self.read_list, len(self.read_list)

    def get_emotion(self,sequence):
        emotion = ve.analysis_emotion(self.fileDir + '/emot/sound' + str(sequence) + '.wav')
        # print('get_emotion_list:emotion', emotion)
        emotion['time'] = self.read_list[sequence]
        self.emotion_lists.append(emotion)

    def get_emotion_lists(self,number):
        emotionlist = []
        for i in range(number):
            # print(i)
            emotion = ve.analysis_emotion(self.fileDir+'/emot/sound'+str(i)+'.wav')
            # print('emotion',emotion)
            emotion['time'] = self.read_list[i]
            emotionlist.append(emotion)
        return emotionlist

    ## 废弃方法，QAQ
    def cut_voice_to_upload(self):
        voice_during_time = len(self.sound)
        sound_Name = 'sound'

        if voice_during_time <= 60000:
            self.sound.export(self.fileDir +'/reco/'+ sound_Name + "0.wav", format="wav",)
            return 1
        else:
            # 音频长度超过了60s
            nums = (int)(voice_during_time/60000) +1
            print('音频总长度为' + str(len(self.sound)))
            print('需要切成' + str(nums) + '份')
            # 至少要切 1 次，nums至少为2
            # 切割点数组
            slice_num_list = [0, ]

            for i in range(nums - 1):
                for j in range(len(self.read_list)):
                    if 60000 < (self.read_list[j][0]-slice_num_list[-1]):
                        if 60000 > (self.read_list[j-1][1]-slice_num_list[-1]):
                            slice_num_list.append((self.read_list[j][0]-200))
                        else:
                            slice_num_list.append((self.read_list[j-1][0]-200))
                        break
            if (len(self.sound) - slice_num_list[-1] > 60000):
                for j in range(len(self.read_list)):
                    if 60000 < (self.read_list[j][0]-slice_num_list[-1]):
                        if 60000 > (self.read_list[j-1][1]-slice_num_list[-1]):
                            slice_num_list.append((self.read_list[j][0]-200))
                        else:
                            slice_num_list.append((self.read_list[j-1][0]-200))
                        break
            # 删除 0
            slice_num_list.pop(0)
            print(slice_num_list)
            # 保存音频文件
            for i in range(len(slice_num_list)):
                if i == 0:
                    sound_read = self.sound[0 : slice_num_list[0]]
                else:
                    sound_read = self.sound[slice_num_list[i - 1] : slice_num_list[i]]
                sound_read.export(self.fileDir + '/reco/' + sound_Name + str(i) + ".wav",
                                  format="wav")
            sound_read = self.sound[ slice_num_list[len(slice_num_list) - 1] : len(self.sound)]
            sound_read.export(self.fileDir + '/reco/' + sound_Name + str(len(slice_num_list)) + ".wav",
                              format="wav")
            return (len(slice_num_list) + 1)

    def upload_one_voice(self,num):
        print("~~~~~~~~~~~~~~~~~~~~~~~~~~~`")
        b = self.voice_recog.get_text(self.fileDir+'/emot/sound'+str(num)+'.wav')
        print(b)
        text_ = b['result']
        return text_

    def upload_voice(self,num):
        print(len(self.sound))
        # nums = self.cut_voice_to_upload()
        text_ = []
        print(self.read_list)
        # for i in range(len(self.read_list)):
        for i in range(num):
            a = voice_recognition.recog()
            b = a.get_text(self.fileDir+'/reco/sound'+str(i)+'.wav')
            print(b)
            text_.append(b['result'])
        print(text_)
        # else:
    def upload_voice(self):
        # print(len(self.sound))
        # nums = self.cut_voice_to_upload()
        text_ = []
        # print(self.read_list)
        # for i in range(len(self.read_list)):
        for i in range(len(self.read_list)):
            a = voice_recognition.recog()
            b = a.get_text(self.fileDir+'/emot/sound'+str(i)+'.wav')
            print(b)
            if 'result' in b:
                text_.append(b['result'])
        print(text_)

# dir是文件夹名称
# speechName是朗读文件名称
# musicName是背景音乐，要插入的音乐文件名
# mode是模式，e:音效，b:背景音乐
# scale是比例, 默认为0
# outputName是输出文件名称，默认为'output.wav'
# 处理原则：背景音乐的分贝为朗读的分贝的2/3
def compound_speech_music(dir,speechName,musicName,mode = 'e',scale = 0,outputName='output.wav'):
    sound1 = AudioSegment.from_file(dir + "/" + speechName, format=(speechName.split('.'))[-1])
    sound2 = AudioSegment.from_file(dir + "/" + musicName, format==(musicName.split('.'))[-1])
    print('开始添加背景音乐')
    db1 = sound1.dBFS
    print('朗读分贝：',db1)
    db2 = sound2.dBFS
    print('音乐分贝：',db2)
    dbsub = abs(db1 - db2) + abs(db1) * 1 // 3
    print('音乐偏移量:',dbsub)
    sound2 = sound2 - dbsub
    if mode == 'e':
        # 输出
        print('position' , sound1.duration_seconds*scale)
        sound1 = sound1.overlay(sound2,position=sound1.duration_seconds*scale - 500)
        # 输出合成文件
        # sound1.export(dir + "/" + outputName, format="wav")
        return sound1
    if mode == 'b':
        # 输出
        sound1 = sound1.overlay(sound2, loop= True)
        return sound1

    #合成的声音
    # played_togther = sound1.overlay(sound2)
    #sound2延迟播放
    # sound2_starts_after_delay = sound1.overlay(sound2,position = 5000)
    #sound2重复播放知道sound1结束
    # sound2重复播放两次
    # sound2_plays_twice = sound1.overlay(sound2,times = 2)
    # 假设sound1为30秒长,sound2为5秒长:
    # sound2_plays_a_lot = sound1.overlay(sound2, times = 10000)
    # len(sound1) == len(sound2_plays_a_lot)
    # 输出
    # sound1 = sound1.overlay(sound2,loop = True)
    # 输出合成文件
    # sound1.export(dir+"/"+outputName,format="wav")

def compound_speech_music_with_sound(speechName,musicdir,mode = 'e',time = 0):
    sound1 = speechName
    sound2 = AudioSegment.from_file(musicdir, format=(musicdir.split('.'))[-1])
    print('开始添加背景音乐')
    db1 = sound1.dBFS
    print('朗读分贝：',db1)
    db2 = sound2.dBFS
    print('音乐分贝：',db2)
    dbsub = abs(db1 - db2) + abs(db1) * 3 / 4
    print('音乐偏移量:',dbsub)
    sound2 = sound2 - dbsub
    if mode == 'e':
        # 输出
        # print('position',sound1.duration_seconds*scale)
        sound2 = sound2 + 15
        sound1 = sound1.overlay(sound2,position=time + 50)
        # 输出合成文件
        # sound1.export(dir + "/" + outputName, format="wav")
        return sound1
    if mode == 'b':
        # 输出
        sound1 = sound1.overlay(sound2, loop=True)
        return sound1


def add_backmusic_and_effect(sentences,outdir='output.wav'):
    voices = [] # 处理好音效的音频
    blackground = [] # 背景音乐
    effects_time = []
    effects_file = []

    for i in range(len(sentences)):
        print(i,sentences[i].speech_file)

    for i in range(len(sentences)):
        sound = AudioSegment.from_file(sentences[i].speech_file, format=fip.get_file_form(sentences[i].speech_file))
        # 是否有关键字
        if sentences[i].sound_effect != None:
            # 得到关键字在句子当中的百分比位置
            effect_scale = word_in_Sentence(sentences[i].keyword,sentences[i].text) - 0.02
            # sound = compound_speech_music_with_sound(sound,sentences[0].sound_effect,mode = 'e' ,scale = effect_locate)
            print(len(sound))
            time = len(sound) * effect_scale
            for j in range(len(voices)):

                time += len(voices[j])
            # 添加音效播放时间,最后合成好后处理
            effects_time.append(time)
            effects_file.append(sentences[i].sound_effect)

        # TODO 固定在结尾插入一定长度的空白音频(待定)
        voices.append(sound)
        blackground.append(sentences[i].bgm)
    voice_then = []
    start = -1
    end = 0
    temp = None

    print('blackground',blackground)
    print('effects_time',effects_time)
    print('effects_file',effects_file)

    if len(blackground) == 1:
        if sentences[0].bgm != '':
            voices[0] = compound_speech_music_with_sound(voices[0], sentences[0].bgm, mode='b')
        voice_then.append(voices[0])
    else:

        while end <= len(blackground) - 1:
            print('start',start,'end:',end)

            if start == -1:
                start = end
                temp = voices[start]

            if end == len(blackground) - 1:
                print('合成1')
                # 将start - (end - 1)合成，并加入voices_then
                for q in range(start + 1, end + 1):
                    temp += voices[q]
                if blackground[start] != '':
                    temp = compound_speech_music_with_sound(temp, blackground[start], mode='b')
                voice_then.append(temp)
                break
            if blackground[start] != blackground[end + 1]:
                for q in range(start + 1, end + 1):
                    temp += voices[q]
                if blackground[start] != '':
                    print('合成2')
                    temp = compound_speech_music_with_sound(temp, blackground[start], mode='b')
                voice_then.append(temp)
                temp = None
                start = -1

            end += 1

    print(voice_then[0])
    print(len(voice_then))
    voice1 = voice_then[0]
    for i in range(1,len(voice_then)):
        print(i)
        voice1 += voice_then[i]

    for i in range(len(effects_time)):
        print(i)
        voice1 = compound_speech_music_with_sound(voice1, effects_file[i], mode='e', time=effects_time[i])

    voice1.export(outdir, format="mp3")

# 前提是必须在句子中
def word_in_Sentence(word, sentence):
    print(word,"~~~",sentence)
    loc = re.search(word, sentence).span()[0] + 1
    # 返回词在句子中的位置比例，正则匹配
    return loc/len(sentence)


if __name__ == '__main__':
    # 给指定sentence数组配音效
    # sens = [] sentence数组
    # 调用下面的函数，输出一个output11.wav
    # add_backmusic_and_effect(sens)
    pass
