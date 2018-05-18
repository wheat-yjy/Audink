from aip import AipSpeech
import pydub

class recog(object):
    APP_ID = '10784199'
    API_KEY = 'cLBGzbjVZYAnF6G73b1zMAxm'
    SECRET_KEY = 'PzryQiysBmSfg3GvWta2j0XXPmiAzxk3'

    def __init__(self):
        self.client = AipSpeech(self.APP_ID, self.API_KEY, self.SECRET_KEY)

    # 读取文件
    def get_file_content(self,filePath):
        with open(filePath, 'rb') as fp:
            return fp.read()

    def get_text(self,fileDir):
    # 识别本地文件
    #     print(fileDir,'hello')
        ask = self.client.asr(self.get_file_content(fileDir), 'wav', 16000, {
            'lan': 'zh',
        })
        return ask

    def get_texts(self,voice_name):
        pass

