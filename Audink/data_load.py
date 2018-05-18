import os
import gensim
import xml.dom.minidom
from pyltp import Segmentor
from pyltp import Parser
from pyltp import Postagger
from aip import AipSpeech
import chainer
import chainer.functions as F
import chainer.links as L
from chainer import serializers


"""
        根据文件名加载文件
    所有的文件名都必须是绝对路径
"""


LTP_DATA_DIR = 'ltp_data_v3.4.0'  # ltp　模型目录的路径

def load_text(file_name):
    """
    :param file_name: 必须是绝对路径
    :return: 打开的文件,
    """
    # TODO 文件读取要优化,不然会很慢 目前有个叫linecache的库可能有用
    file = open(file_name)
    return file


def load_xml(xml_file_name):
    dom = xml.dom.minidom.parse(xml_file_name)
    root = dom.documentElement
    child_nodes = root.getElementsByTagName('sentence')


def load_effect_model():
    """
        加载音效模型
    """

    # model = L.Classifier(Need())
    # serializers.load_npz("Models/need", model)


def load_emotion_model():
    """
        加载情感分析模型
    """



def load_word2vec_model():
    """
        加载词向量模型
    """
    global_word2vec = gensim.models.KeyedVectors.load_word2vec_format('/home/embedded/Audink/Models/news_12g_baidubaike_20g_novel_90g_embedding_64.bin', binary=True)
    return global_word2vec


def load_cws_model():
    """
        加载分词模型
    """
    cws_model_path = os.path.join(LTP_DATA_DIR, 'cws.model')  # 分词
    segmentor = Segmentor()
    segmentor.load(cws_model_path)
    return segmentor


def load_pos_model():
    """
        加载词性标注模型,为了依存句法分析
    """
    pos_model_path = os.path.join(LTP_DATA_DIR, 'pos.model')  # 词性标注
    postagger = Postagger()
    postagger.load(pos_model_path)
    return postagger


def load_dependency_model():
    """
        加载依存句法分析模型
    """
    par_model_path = os.path.join(LTP_DATA_DIR, 'parser.model')  # 依存句法分析
    parser = Parser()
    parser.load(par_model_path)
    return parser


def TTS(sentence, result_name):
    """
        语音合成, 把写入的文件名放进 sentence 的 speech_file 属性
    :param text:  SentenceDefine对象
    :param spd:   语速，取值0-9，默认为5中语速
    :param pit:   音调，取值0-9，默认为5中语调
    :param vol:   音量，取值0-15，默认为5中音量
    :param per:   发音人选择, 0为女声，1为男声，3为情感合成-度逍遥，4为情感合成-度丫丫，默认为普通女
    :return:      None
    """
    APP_ID = '10755356'
    API_KEY = 'otfgcdTsefZPcq2osXotBVQe'
    SECRET_KEY = 'd04MCEVWFbL21jN9L4gfsEI2GVxf94NI'

    client = AipSpeech(APP_ID, API_KEY, SECRET_KEY)

    result = client.synthesis(sentence.text, 'zh', 1, {
        'pit': sentence.emotion['pit']/10.0 - 1,
        'per': sentence.role,
        'spd': sentence.emotion['speed']/10.0 - 1,
    })

    print(sentence.role)

    if not isinstance(result, dict):
        with open("/home/embedded/Audink/TTS_File/" + result_name + ".mp3", 'wb') as f:
            f.write(result)
    else:
        raise('百度语音合成失败', result_name)

    sentence.set_speechFile("/home/embedded/Audink/TTS_File/" + result_name + ".mp3")
