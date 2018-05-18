# coding: utf-8

# # 句子情感、背景音乐整合


import numpy as np
from chainer import datasets, iterators, optimizers, serializers
from chainer import Link, Chain, ChainList
import chainer.functions as F
import chainer.links as L
import re
import jieba.posseg as psg
from xml.etree import ElementTree as ET
from random import randint as rint
from voice_ocean.sentence_define import SentenceDefine
from model_work import model_tovec
from Models.audink_models.para import ParaClassify
from Models.audink_models.sentence import SentenceClassify
from Models.audink_models.subject import SubjectClassify


# 导入词向量
# globalWordVector = gensim.models.KeyedVectors.load_word2vec_format('../news_12g_baidubaike_20g_novel_90g_embedding_64.bin', binary=True)
globalWordVector = model_tovec


# In[3]:


class RnnClassify(Chain):
    def __init__(self):
        super(RnnClassify, self).__init__()
        with self.init_scope():
            self.l_input = L.LSTM(None, 128)
            self.l_merge = L.Linear(None, 2048)
            self.l_merge2 = L.Linear(None, 512)

    def reset_state(self):
        self.l2.reset_state()

    def __call__(self, x):
        sentences = x
        h = F.relu(F.dropout(self.l_input(sentences), 0.5))
        h = F.relu(F.dropout(self.l_merge(h), 0.5))
        h = F.relu(F.dropout(self.l_merge2(h), 0.5))
        return h


# In[4]:


class SixClassClassify(Chain):
    def __init__(self):
        super(SixClassClassify, self).__init__()
        with self.init_scope():
            self.l1 = RnnClassify()
            self.l2 = L.Linear(None, 7)

    def reset_state(self):
        self.l1.reset_state()
        self.l2.reset_state()

    def __call__(self, sentences):
        h = self.l1(sentences)
        h = self.l2(h)
        #         h = F.reshape(h,(-1,7))
        return h


# 加载模型
globalModel = SixClassClassify()
serializers.load_npz('/home/embedded/Audink/Models/emotion', globalModel)

# In[41]:


# 停用词
stop_words = ['的', '了', '一', '在', '和', '到', '一件', '一个', '一些', '一天', '一次', '一片', '一边', '一面',
              '去', '着', '那', '有个', '有', '里', '距', '于', '让', '其', '上', '下', '这', '个', '起', '给']
stop_words2 = ['我', '你', '他', '她', '它', '我们', '你们', '他们', '她们', '它们', '喵', '汪', '二', '三', '四', '五',
               '六', '七', '八', '九', '十', '零', '百', '千', '万', '亿', '又', '是', '也', '使', '地', '中', '前', '后']
stop_words3 = [line.strip() for line in open('/home/embedded/Audink/stop_word.txt', 'r', encoding='utf-8').readlines()]
stop_flag = ['eng', 'x', 'un', 'ul', 'uj', 'nr', 'r']

maxSequence = 50
wordDim = 64
sentenceBatch = 1


# 将句子分词并处理标点和英文词。问号转为呢，感叹号转为啊，其他去掉。
def cut_and_excute(sentence):
    print("cut__________", sentence)
    words = psg.cut(sentence)
    words = [i.word for i in words if (i.flag != 'eng' and i.flag != 'x' and i.word not in stop_words) or (
            i.flag == 'x' and (i.word == '？' or i.word == '！'))]
    for i in range(len(words)):
        if words[i] == '？':
            words[i] = '呢'
        if words[i] == '！':
            words[i] = '啊'
    if len(words) > 50:
        words = [word for word in words if word not in stop_words2]
    return words


# 精确切词
def cut_and_excute_accurate(sentence):
    words = psg.cut(sentence)
    words = [i.word for i in words if (i.flag not in stop_flag and i.word not in stop_words3) or (
            i.flag == 'x' and (i.word == '？' or i.word == '！'))]
    for i in range(len(words)):
        if words[i] == '？':
            words[i] = '呢'
        if words[i] == '！':
            words[i] = '啊'
    return words


# 将分好词的句子转为词向量
def cutted_sentence_to_vec(wordVector, words):
    result = []
    for word in words:
        try:
            vec = wordVector[word]
            result.append(vec)
        except KeyError:
            pass
    for i in range(maxSequence - len(result)):
        result.append([0] * wordDim)

    return np.array(result[:maxSequence], dtype=np.float32)


# 将一篇文章转换为词向量
def article_to_vec(wordVector, sentences):
    result = []
    temp_sentence = [np.array([[0] * wordDim] * maxSequence, dtype=np.float32)] * (sentenceBatch - 1)

    i = 0
    for sentence in sentences:
        temp_sentence.append(cutted_sentence_to_vec(wordVector, cut_and_excute(sentence)))
        result.append(np.array(temp_sentence[i:], dtype=np.float32))
        i += 1
    return np.array(result, dtype=np.float32)


# 将xml文件转换为[(np[sentence], tag)]
def xml_to_list(wordVector, file):
    result = []
    temp_sentence = [np.array([[0] * wordDim] * maxSequence, dtype=np.float32)] * (sentenceBatch - 1)

    print(file)

    root = ET.parse(file).getroot()
    i = 0
    for sentence in root.findall('sentence'):
        cutted = cutted_sentence_to_vec(wordVector, cut_and_excute(sentence.get('text')))
        temp_sentence.append(cutted)
        tag = [0, 0, 0, 0, 0, 0]
        if sentence.get('emotion1') == 'happy' or sentence.get('emotion2') == 'happy':
            tag[0] = 1
        if sentence.get('emotion1') == 'sadness' or sentence.get('emotion2') == 'sadness':
            tag[1] = 1
        if sentence.get('emotion1') == 'surprise' or sentence.get('emotion2') == 'surprise':
            tag[2] = 1
        if sentence.get('emotion1') == 'fear' or sentence.get('emotion2') == 'fear':
            tag[3] = 1
        if sentence.get('emotion1') == 'anger' or sentence.get('emotion2') == 'anger':
            tag[4] = 1
        if sentence.get('emotion1') == 'disgust' or sentence.get('emotion2') == 'disgust':
            tag[5] = 1
        tag = np.array(tag, dtype=np.int32)
        result.append((np.array(temp_sentence[i:], dtype=np.float32), tag))
        i += 1
    return result


# 计算一个文件里所有情感词的平均值
def file_to_avg_vec(file):
    txt = ''
    for line in open(file, 'r', encoding='utf-8').readlines():
        txt += line
    words = cut_and_excute_accurate(txt)
    vecs = []
    for word in words:
        try:
            vecs.append(globalWordVector.get_vector(word))
        except:
            pass
    vecs = np.array(vecs)
    return np.mean(vecs, axis=0)


# vecDic = {'愉快':file_to_avg_vec('happy.txt'), '激动':file_to_avg_vec('excited.txt'),
#           '紧张':file_to_avg_vec('fear.txt'), '伤感':file_to_avg_vec('sad.txt')}

# 给出一堆句子标情感
def sentences_emotion(sentences):
    typeDic = {'愉快': 0, '激动': 0, '紧张': 0, '伤感': 0}
    for words in sentences:
        for word in words:
            try:
                #                 moodList =[(tg, globalWordVector.distances(vecDic[tg], (word,))[0]) for tg in typeDic]
                moodList = [(tg, globalWordVector.distance(tg, word)) for tg in typeDic]
                posMood = min(moodList, key=lambda x: x[1])
                negMood = max(moodList, key=lambda x: x[1])
                # if negMood[1] - posMood[1] >= 0.1:
                typeDic[posMood[0]] += 2
                typeDic[negMood[0]] -= 1
            except KeyError:
                pass
    return typeDic


# def sentences_emotion(sentences):
#     temp = []
#     for words in sentences:
#         for word in words:
#             try:
#                 temp.append(globalWordVector.get_vector(word))
#             except KeyError:
#                 pass
#     temp = np.mean(np.array(temp),axis=0)
#     typeDic = {'愉快':abs(np.linalg.norm(temp-vecDic['愉快'])), 
#                '激动':abs(np.linalg.norm(temp-vecDic['激动'])), 
#                '紧张':abs(np.linalg.norm(temp-vecDic['紧张'])), 
#                '伤感':abs(np.linalg.norm(temp-vecDic['伤感']))}
#     return typeDic


# In[42]:


# 分好段落的list传进来
# SentenceDefine(): text, emotion:{}, sound_effect, is_dialog(1,2), speech_file, bgm
# 准备背景音乐
def prepare_bgm(para_list):  # TODO chaoyu gai
    text = ''.join([''.join([s.text for s in d]) for d in para_list])
    subject = SubjectClassify.get_subject(text)

    subject_list = ['sc','east','west','magic','city','country','military','sport','news','ps']

    print('确定文本题材：', subject)

    # todo 匹配音乐
    bgm_dic = {'愉快': '/home/embedded/Audink/bgm/'+subject_list[subject[0]]+'/愉快.mp3', '激动': '/home/embedded/Audink/bgm/'+subject_list[subject[0]]+'/激动.mp3',
               '紧张': '/home/embedded/Audink/bgm/'+subject_list[subject[0]]+'/紧张.wav', '伤感': '/home/embedded/Audink/bgm/'+subject_list[subject[0]]+'/伤感.mp3'}
    return bgm_dic


# 小段落合并
def merge_short_paragraph(para_list):
    new_para_list = []
    temp_sentence_list = None
    for para in para_list:
        if temp_sentence_list is None:
            temp_sentence_list = para
        else:
            temp_sentence_list += para
            if sum([len(s.text) for s in temp_sentence_list]) > 80:
                new_para_list.append(temp_sentence_list)
                temp_sentence_list = None
    if temp_sentence_list is not None:
        new_para_list.append(temp_sentence_list)
    return new_para_list


# 段落情感分析
def para_emotion_classify(para_list):
    # 传入[[SentenceClass]]
    new_sentence_list = []
    # 每个句子加入base_emotion
    # for para in para_list:
    #
    #     base_emotion = sentences_emotion([cut_and_excute_accurate(s.text) for s in para])
    #     max_key = sorted(base_emotion, key=lambda x: base_emotion[x])[-1]
    #     print(max_key)
    #     for sentence in para:
    #         sentence.emotion['base_emotion'] = max_key
    # return para_list
    for para in para_list:
        base_emotion = ParaClassify.get_emotion(''.join([s.text for s in para]))
        for sentence in para:
            sentence.emotion['base_emotion'] = base_emotion
    return para_list


# 背景乐确定
def determine_bgm(para_list, bgm_dic):
    last_para_bgmed = False
    last_emotion = None
    for para in para_list:
        if len(para) == 0:
            print("para is None.")
            continue
        if not last_para_bgmed:
            # 上一段没有配乐则要配
            for sentence in para:
                sentence.bgm = bgm_dic[sentence.emotion['base_emotion']]
                last_para_bgmed = True
                last_emotion = sentence.emotion['base_emotion']
        elif para[0].emotion['base_emotion'] == last_emotion:
            # 上一段有配乐但是同一情感也要
            for sentence in para:
                sentence.bgm = bgm_dic[sentence.emotion['base_emotion']]
                last_para_bgmed = True
                last_emotion = sentence.emotion['base_emotion']
        else:
            last_para_bgmed = False
    return para_list


# 合并段落，产生每句情感
def single_sentence_emotion(para_list):
    # 合并
    sentence_class_list = []
    for para in para_list:
        sentence_class_list += para
    # 分析情感
    y = SentenceClassify.get_emotion([d.text for d in sentence_class_list])
    print(y)

    # sentence_list = [d.text for d in sentence_class_list]
    # print(sentence_list)
    # article = article_to_vec(globalWordVector, sentence_list)
    # y = globalModel(article).data.argmax(axis=1)
    # print(y)
    for i in range(min(len(sentence_class_list), len(y))):
        sentence_class_list[i].emotion['emotion'] = y[i]  # 新模型输出为数字
    for s in sentence_class_list:
        print(s.text)
        print(s.bgm, s.emotion)
        print()
    return sentence_class_list


# 计算声调和语速
def calculate_pit_speed(sentence_class_list):
    # 硬编码规则
    # 对话和旁白用不同方式读
    # 在单句情感上加入全局情感偏差
    # 每个逗号读的时间长短有微调

    # 定义基本情绪发音[speed,pitch]
    dialog_weight = {'happy': [80 - 20, 70], 'sad': [70 - 20, 40], 'surprise': [77 - 20, 75], 'fear': [74 - 20, 50],
                     'anger': [83 - 10, 70], 'disgust': [70 - 20, 75], 'normal': [77 - 20, 60]}
    narrator_weight = {'happy': [70, 35], 'sad': [60, 20], 'surprise': [75, 35], 'fear': [75, 20],
                       'anger': [80, 35], 'disgust': [60, 28], 'normal': [70, 28]}
    # para_bias = {'愉快':[0,5], '激动':[5,0], '紧张':[0,-5], '伤感':[-5,-5]}
    para_bias = {'愉快': [0, 5], '激动': [0, 0], '紧张': [0, -5], '伤感': [-5, -5]}

    # 保存拆分后的新句子
    new_sentence_list = []

    # 句子拆分，计算语速音调
    for sc in sentence_class_list:
        split_sentences = re.split('[，；]', sc.text.strip())
        long_sentence_speedup = True
        for single_sentence in split_sentences:
            emotion = ''
            if sc.emotion['emotion'] == 1:
                emotion = 'happy'
            elif sc.emotion['emotion'] == 2:
                emotion = 'sad'
            elif sc.emotion['emotion'] == 3:
                emotion = 'surprise'
            elif sc.emotion['emotion'] == 4:
                emotion = 'fear'
            elif sc.emotion['emotion'] == 5:
                emotion = 'anger'
            elif sc.emotion['emotion'] == 6:
                emotion = 'disgust'
            else:
                emotion = 'normal'

            # 计算语速和音高
            speed = 0
            pitch = 0
            # 基准值 + 段落情绪偏差
            max_key = sc.emotion['base_emotion']
            bias = para_bias[max_key]
            # 对话和旁白分开讨论
            if sc.is_dialog == 2:
                speed = dialog_weight[emotion][0] + bias[0]
                pitch = dialog_weight[emotion][1] + bias[1]
            else:
                speed = narrator_weight[emotion][0] + bias[0]
                pitch = narrator_weight[emotion][1] + bias[1]
            # 语速再调整
            # 短句读慢，长句读快，连续长句一快一慢
            if len(single_sentence) <= 7:
                speed -= rint(0, 5)
                long_sentence_speedup = True
            else:
                if long_sentence_speedup:
                    speed -= rint(0, 3)
                else:
                    speed += rint(0, 5)
                long_sentence_speedup = not long_sentence_speedup
            # 校准，避免出现数值超出范围
            if speed > 100:
                speed = 100
            if speed < 0:
                speed = 0
            if pitch > 100:
                pitch = 100
            if pitch < 0:
                pitch = 0
            # 加入新句子
            ns = SentenceDefine(single_sentence)
            ns.is_dialog = sc.is_dialog
            ns.emotion = {'emotion': emotion, 'speed': speed, 'pit': pitch}
            ns.bgm = sc.bgm
            ns.role = sc.role

            new_sentence_list.append(ns)
    return new_sentence_list


def xiesi(sentence_list):
    sentence_class_list = calculate_pit_speed(sentence_list)
    print([(i.text, i.emotion) for i in sentence_class_list])
    sentences = [k for k in sentence_class_list if not len(k.text) < 1]

    return sentences


def clever_chaoyu_junyi(para_list):
    # 整合方法
    # 段落合并短句
    para_list = merge_short_paragraph(para_list)
    # 段落情感分析
    para_list = para_emotion_classify(para_list)
    # bgm确定
    para_list = determine_bgm(para_list, prepare_bgm(para_list))
    # 单句情感分析
    sentence_class_list = single_sentence_emotion(para_list)

    sentences = [k for k in sentence_class_list if not len(k.text) < 1]

    return sentences


# 智能后期情感
def houqi_emotion(sentence_list):
    for s in sentence_list:
        print(s.text)
    copy_para_list = [[SentenceDefine(s.text) for s in sentence_list]]
    copy_para_list = para_emotion_classify(copy_para_list)
    copy_para_list = determine_bgm(copy_para_list, prepare_bgm(copy_para_list))
    for i in range(len(sentence_list)):
        item: SentenceDefine = sentence_list[i]
        # item.emotion = {}
        # item.emotion['base_emotion'] = copy_para_list[0][i].emotion['base_emotion']
        item.bgm = copy_para_list[0][i].bgm
    return sentence_list


# 返回
def article_to_read_sentences(para_list):
    print("atrs传入", para_list)
    # 整合方法
    # 段落合并短句
    para_list = merge_short_paragraph(para_list)
    # 段落情感分析
    para_list = para_emotion_classify(para_list)
    # bgm确定
    para_list = determine_bgm(para_list, prepare_bgm(para_list))
    # 单句情感分析
    sentence_class_list = single_sentence_emotion(para_list)
    # 分逗号计算发音
    sentence_class_list = calculate_pit_speed(sentence_class_list)
    # for sentence in sentence_class_list:
    #     print(sentence.text, "SSSSSSSSSSSSSSSSSSSSSSSSSSSSSS")
    #     print(sentence.emotion)
    #     print(sentence.bgm)
    #     print()

    sentences = [k for k in sentence_class_list if not len(k.text) < 1]

    return sentences


# In[43]:


# 测试部分
def article_test():
    root = ET.parse('NovelDataset/宝刀-阿来-new-dev-para.xml')
    #     root = ET.parse('NovelDataset/ttt/抱月行-少鸿_训练测试.xml')
    sentences = [[SentenceDefine(sentence.get('text').strip()) for sentence in para.findall('sentence')]
                 for para in root.findall('para')]
    #     sentences = [[SentenceDefine(sentence.get('text').strip()) for sentence in root.findall('sentence')]]
    #     print(sentences)
    sentences = article_to_read_sentences(sentences)

    for sentence in sentences:
        #         print('<{},{}>{}'.format(sentence.emotion['speed'],sentence.emotion['pit'],sentence.text))
        print(sentence.text)
        print(sentence.emotion)
        print(sentence.bgm)
        print()
