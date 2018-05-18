import time
import logging
import argparse as apa

import os

from voice_ocean import Resample
from voice_ocean import fileprocess as fip
from voice_ocean.sentence_define import SentenceDefine
from voice_ocean import pyvoice
from voice_ocean.pyvoice import vop
from voice_ocean.threaduse import ThreadPool

sound_Name = 'sound'


def callback(status, result):
    """
    根据需要进行的回调函数，默认不执行。
    :param status: action函数的执行状态
    :param result: action函数的返回值
    :return:
    """
    pass


def action(thread_name,num,v,text_dict):
    """
    真实的任务定义在这个函数里
    :param thread_name: 执行该方法的线程名
    :param arg: 该函数需要的参数
    :return:
    """

    filePath= v.fileDir +'/emot/'+ sound_Name + str(num) + ".wav"
    # 处理格式
    Resample.ruler(filePath)
    print("处理过了重采样")
    #
    # emotion = v.get_emotion(num)
    emotion = None
    if emotion != None:
        # print("emotion",emotion)
        # 排序
        print('排序')
        items = emotion.items()
        backitems = [[v[1], v[0]] for v in items]
        backitems.sort(reverse=True)
        emotion = [backitems[i][1] for i in range(0, len(backitems) - 2)]
        print(emotion)
    text = v.upload_one_voice(num)
    print(str(num))
    text_dict[str(num)] = [text, emotion, filePath]
    # print(text_dict)
    # print(filePath)
    # print("第%s个任务调用了线程 %s，并打印了这条信息！" % (num + 1, thread_name))


def voice_process(file_dir='/home/embedded/Audink/musicpackage/a'):
    # 这里音频名就是音频名，从temp中读音频文件，下载时定义好名字来区分
    # 处理音频目录在voice_sea，由主机分配

    #
    text_dict = {}

    # 1.文件是否下载成功
    # fileDir是文件夹路径，filePath是文件路径
    fileDir, filePath = fip.get_abs_speech_path(file_dir)

    if filePath != None:
        # # 0.音频是否需要重采样，如果需要，那么进行重采样
        # Resample.ruler(filePath)

        # 1.接收分配的文件名，并创建与之有关的文件夹
        fip.create_dir(fileDir)

        # 2.降噪

        # 3.切割音频文件，并存入音频文件的emot文件夹，使用情感分析
        v = vop(fileDir, filePath)

        read_list, num_temp_files = v.analysize_music()
        print(read_list)
        print(num_temp_files)
        pool = ThreadPool(20)
        # 创建任务
        for i in range(num_temp_files):
            pool.put(action, (i, v, text_dict), callback)

        while (True):
            time.sleep(5)
            # print('free_list', pool.free_list)
            if len(pool.free_list) == num_temp_files or len(pool.free_list) == 20:
                print('语音处理完成')
                pool.close()
                break

        # fip.save_result(fileDir,emos)
        # # 4.语音识别得到，文本
        # # num = v.cut_voice_to_upload()
        # v.upload_voice()
        # # v.upload_voice()
        # # 5.删除文件夹
        # fip.rm_dir(fileDir)
        #

        sentence_list = []
        print('chaoyuhahaha')
        print(text_dict)
        for i in range(len(text_dict)):
            a = SentenceDefine(text_dict[str(i)][0][0])
            a.set_emotion(text_dict[str(i)][1])
            a.set_speechFile(text_dict[str(i)][2])
            sentence_list.append(a)

        text_dict = {}

        print('-----------------------------')

        '''
        self.text = text
        self.emotion = {}  # 情感标签，string
        self.sound_effect_key = None
        self.sound_effect = None  # 声效，无音效就是０，会变成与音效相关的关键字，最后成为音效文件名,也就是发给逸初最后的形态
        self.is_dialog = None  # 对话和旁白的区分 0接上一句 1旁白 2对话
        self.speech_file = ''  # 语音合成的文件名带路径
        self.bgm = ''  # 发给逸初的时候是
        self.role = 0  # 没角色就是0 ,有角色就是 1 2 3
        '''
        print(len(sentence_list))
        for i in range(len(sentence_list)):
            print(i, sentence_list[i].text)
            print(i, sentence_list[i].emotion)

        # 帅气钧一写的
        return sentence_list
        # 传递给钧一

        # pyvoice.add_backmusic_and_effect(sentence_list,os.path.join(fileDir,'output.wav'))

    else:
        logging.warning('路径' + fileDir + '中无目标文件，可能下载文件失败')


# if __name__ == '__main__':
#     voice_process()