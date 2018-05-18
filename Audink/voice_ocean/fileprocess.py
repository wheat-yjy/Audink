import os
import shutil

# 和pydub音频操作在 同一个目录 下
# 想要创建的文件夹在同一目录下的voice_sea目录下
# 创建两个文件夹 1) emot 语音情感识别时切割的朗读音频段落
# 2)reco 上传百度语音识别的文件夹(可能存在音频超过60s，所以需要分开上传)
# # result 处理结果就直接生成在目录中



def create_dir(fileDir):

    path_emo = os.path.join(fileDir,'emot')
    if not os.path.exists(path_emo):
        os.mkdir(path_emo)

# delete the specific dir and files in the dir
def rm_dir(fileDir):
    current_filedir = os.listdir(fileDir)
    for f in current_filedir:
        path_ = os.path.join(fileDir,f)
        if os.path.isdir(path_):
            print(path_)
            shutil.rmtree(path_)

# 返回
def get_file_form(fileName):
    filetail = (os.path.splitext(fileName)[-1])
    # print(filetail)
    return filetail.split('.')[1]

def change_filename_wav(fileName):
    filehead = (os.path.splitext(fileName)[0])
    os.rename(fileName,filehead + '.wav')
    return filehead + '.wav'
def get_voice_path(voice_name):
    pppath = os.path.join('.', 'voice_sea')
    ppath = os.path.join(pppath, voice_name)
    # path =

def rm_all_dir(fileName):
    pppath = os.path.join('.', 'voice_sea')
    ppath = os.path.join(pppath, fileName)
    shutil.rmtree(ppath)

def get_abs_speech_path(file_dir):
    path = file_dir
    file = None
    for i in os.listdir(path):
        filePath = os.path.join(path,i)
        if os.path.isfile(filePath) and i != 'wav_proc_mid.wav' and i != 'output.wav'\
                and i != 'result.txt':
            file = filePath
    if file!=None:
        return path,file
    else:
        return path,None

# Resample op : return the target dir including voice_name
def get_sea_path(user_dir,voice_dir):
    ppath = os.path.join(pppath,'voice_sea')
    path = os.path.join(ppath, voice_dir)
    path = os.path.join(path, voice_name)
    return path

# 保存情感识别的结果
def save_result(fileDir,info):
    path = os.path.join(fileDir, 'result.txt')
    with open(path,'a+') as f:
        # print(info)
        for i in info:
            f.write(str(i))
            f.write('\n')
    return True


# 移动文件
def movefile(srcfile,dstfile):
    if not os.path.isfile(srcfile):
        pass
        # print("%s not exist!"%(srcfile))
    else:
        fpath,fname=os.path.split(dstfile)    #分离文件名和路径
        if not os.path.exists(fpath):
            os.makedirs(fpath)                #创建路径
        shutil.move(srcfile,dstfile)          #移动文件

def rmfile(filedir):
    if os.path.exists(filedir):
        os.remove(filedir)

if __name__ == '__main__':
    # fileDir, filePath = get_abs_speech_path('a','11')
    # print(fileDir,filePath)
    pass
    # create_dir('sound1')
    # rm_dir('sound1')