import struct
import wave

import os
import logging

import numpy as np
from pydub import AudioSegment

from voice_ocean import fileprocess as fip

logging.basicConfig(level=logging.WARNING,
                    format='%(asctime)s - %(filename)s[line:%(lineno)d] - %(levelname)s: %(message)s')

# 将规则好的文件输入到voice_sea/voice_dir中
# 使用voice_temp_name作为最终的文件保存名
def ruler(filePath):

    # 2.判断是否为wav，不是的话转换成wav，文件名后缀改为wav
    print("第一步")
    if fip.get_file_form(filePath) != 'wav':
        print('是否为wav格式不是')
        tran_to_wav(filePath)# 转换成wav格式,保存在temp的原文件中
        # 最终保存的文件名改变成wav格式
        filePath = fip.change_filename_wav(filePath)

    # 音频格式是否正确
    print("第二步")
    audio_file = None
    try:
        audio_file = wave.open(filePath, 'rb')
    except Exception as e:
        print(e)
    print("第三步")
    # print(audio_file.getnchannels())
    # print(audio_file.getsampwidth())
    # print(audio_file.getnframes())
    print('enter')
    audio_data = audio_file.readframes(audio_file.getnframes())
    audio_data_short = np.fromstring(audio_data, np.int16)
    # print(type(audio_data_short), 'eirje')

    src_fs = audio_file.getframerate()

    audio_file.close()

    if src_fs != 16000:
        print('是否采样正确不是')
        # 临时文件存放路径
        fileTargetPath = os.path.join(filePath + '_temp')
        # 音频格式不正确
        # target frame_rate

        tar_fs = 16000
        # resample
        audio_data_short0 = __Resample(audio_data_short, src_fs, tar_fs)
        waveData = audio_data_short0 * 1.0 / (max(abs(audio_data_short0)))  # wave幅值归一化
        outwave = wave.open(fileTargetPath, 'wb')  # 定义存储路径以及文件名

        nchannels = 1
        sampwidth = 2
        fs = 16000
        data_size = len(waveData)
        framerate = int(fs)
        nframes = data_size
        comptype = "NONE"
        compname = "not compressed"
        outwave.setparams((nchannels, sampwidth, framerate, nframes,
                           comptype, compname))

        for v in waveData:
            outwave.writeframes(struct.pack('h', int(v * 64000 / 2)))  # outData:16位，-32767~32767，注意不要溢出


        outwave.close()
        fip.rmfile(filePath)
        os.rename(fileTargetPath,filePath)
    print('Resample ok')
    return filePath

def __Resample(input_signal,src_fs,tar_fs):
    '''
    :param input_signal:输入信号
    :param src_fs:输入信号采样率
    :param tar_fs:输出信号采样率
    :return:输出信号
    '''
    dtype = input_signal.dtype
    audio_len = len(input_signal)
    audio_time_max = 1.0*(audio_len-1) / src_fs
    src_time = 1.0 * np.linspace(0,audio_len,audio_len) / src_fs
    tar_time = 1.0 * np.linspace(0,np.int(audio_time_max*tar_fs),np.int(audio_time_max*tar_fs)) / tar_fs
    output_signal = np.interp(tar_time,src_time,input_signal).astype(dtype)
    return output_signal

def Generate_Wav():
    #generate the time bar
    t = np.arange(0,time,1.0/framerate)
    #generate the chirp signal from 300 to 3300Hz
    wave_data = signal.chirp(t, frequency_begin, time, frequency_end, method = 'linear')*1000
    #cast to the type of short
    wave_data = wave_data.astype(np.short)
    #open a wav document
    f = wave.open(file_name,"wb")
    #set wav params
    f.setnchannels(channels)
    f.setsampwidth(sampwidth)
    f.setframerate(framerate)
    #turn the data to string
    f.writeframes(wave_data.tostring())
    f.close()

# 文件格式转换成wav
def tran_to_wav(fileDir):
    wave_file = fileDir
    sound = AudioSegment.from_file(wave_file, format=fip.get_file_form(wave_file),frame_rate=16000)
    sound.export(wave_file, format="wav")




if __name__ == '__main__':

    pass
#
#
#     def playSound(audio_data_short, framerate=16000, channels=1):
#         preply = pyaudio.PyAudio()
#         # 播放声音
#         streamreply = preply.open(format=pyaudio.paInt16,
#                                   channels=channels,
#                                   rate=framerate,
#                                   output=True)
#         data = audio_data_short.tostring()
#         streamreply.write(data)
#         streamreply.close()
#         preply.terminate()
#
#
#     audio_file = wave.open('10.wav', 'rb')
#     print(audio_file.getnchannels())
#     print(audio_file.getsampwidth())
#     print(audio_file.getnframes())
#     audio_data = audio_file.readframes(audio_file.getnframes())
#     # print(audio_data)
#     audio_data_short = np.fromstring(audio_data, np.int16)
#     print(type(audio_data_short),'eirje')
#     src_fs = audio_file.getframerate()
#     src_chanels = audio_file.getnchannels()
#     audio_file.close()
#     if src_chanels > 1:
#         audio_data_short = audio_data_short[::src_chanels]
#     print(src_fs)
#     tar_fs = 16000
#     print(tar_fs)
#
#     # playSound(audio_data_short,framerate=src_fs)
#
#     audio_data_short0 = Resample(audio_data_short,src_fs,tar_fs)
#     waveData = audio_data_short0 * 1.0 / (max(abs(audio_data_short0)))  # wave幅值归一化
#
#     outwave = wave.open('C:/Users/XU/Desktop/PythonDesk/Jupyter/tensortest/10.wav', 'wb')  # 定义存储路径以及文件名
#
#     nchannels = 1
#     sampwidth = 2
#     fs = 16000
#     data_size = len(waveData)
#     framerate = int(fs)
#     nframes = data_size
#     comptype = "NONE"
#     compname = "not compressed"
#     outwave.setparams((nchannels, sampwidth, framerate, nframes,
#                        comptype, compname))
#
#     for v in waveData:
#         outwave.writeframes(struct.pack('h', int(v * 64000 / 2)))  # outData:16位，-32767~32767，注意不要溢出
#         print(v)
#     print('ok')
#     outwave.close()
#     # print(type(audio_data_short0))
#     # audio_file = wave.open(wave_file, 'wb')
#     # # audio_file.setnframes()
#     # audio_file.setnchannels(1)
#     # audio_file.setsampwidth(2)
#     # audio_file.setframerate(8000)
#     # audio_file.setnframes(audio_data_short0.tobytes())
#     # audio_file.close()
#     # playSound(audio_data_short0,framerate=tar_fs)