import logging
import numpy as np
import os
import data_load
import re

model_for_effect = data_load.load_effect_model()
model_for_emotion = data_load.load_emotion_model()
model_tovec = data_load.load_word2vec_model()
# 上三个深度学习 下三个 分词  词性  依存句法分析
segmentor = data_load.load_cws_model()
postagger = data_load.load_pos_model()
parser = data_load.load_dependency_model()


def sound_effect(sentence):
    """
        调用模型识别是否配音效,需要的话定位然后匹配音效,只提取出关键词而不是音效文件名
        调用模型识别情感
    :param sentence: SentenceDefine对象
    :return: None
    """

    contents = {}
    contents['声'] = []
    contents['响'] = []
    contents['鸣'] = []
    contents['叫'] = []
    flag = 0
    if "声" in sentence.text and not flag:
        contents["声"].append(sentence.text)
        flag = 1
    if "响" in sentence.text and not flag:
        contents["响"].append(sentence.text)
        flag = 1
    if "鸣" in sentence.text and not flag:
        contents["鸣"].append(sentence.text)
        flag = 1
    if "叫" in sentence.text and not flag:
        contents["叫"].append(sentence.text)

    yuek = os.listdir("/home/embedded/Audink/se")
    yueku = []
    for y in yuek:
        y = y.split('.')[0]
        yueku.append(y)

    yues = []

    for y in yueku:
        yues.append(model_tovec[y])

    for (key, value) in contents.items():
        for sentence in value:
            words = segmentor.segment(sentence)
            print(" ".join(words))

            try:
                conWord = [word for word in words if key in word][0]
                words = [word for word in words if key not in word]

                if len(conWord) > 1:
                    words.append(conWord)
                pair = []
                least = None
                index = 0
                for word in words:
                    for y in yueku:
                        #                 print(y, word)
                        if re.match(pattern="[\u3000-\u301e\ufe10-\ufe19\ufe30-\ufe44\ufe50-\ufe6b\uff01-\uffee\“]",
                                    string=word):
                            continue
                        distance = None
                        try:
                            distance = model_tovec.distance(word, y)
                        except:
                            continue
                        if index == 0:
                            least = distance
                            index = 1
                            pair = []
                            pair.append(word)
                            pair.append(y)
                        if distance < least:
                            pair = []
                            pair.append(word)
                            pair.append(y)
                            least = distance

                if least < 0.2:
                    # 设置音效关键字
                    sentence.set_sound_key(pair[0])
                    sentence.set_sound_effect("/home/embedded/Audink/音效/"+pair[1]+".wav")
            except:
                pass


def get_sound_effect_file(text):
    """
        通过依存句法分析得到句子里的关键词 ,直接得到
    :return: 返回句子里与声音相关的关键词
    """
    words = segmentor.segment(text)
    logging.info(" ".join(words))
    segmentor.release()

    postags = postagger.postag(words)
    logging.info(" ".join(postags))
    postagger.release()
    # 分词结果　ｗｏｒｄｓ　词性标注结果　ｐｏｓｔａｇｓ

    arcs = parser.parse(words, postags)
    logging.info("　".join("%s:%s" % (words[arc.head], arc.relation) for arc in arcs))

    key = [k for k in words if "声" in k][0]

    return key
