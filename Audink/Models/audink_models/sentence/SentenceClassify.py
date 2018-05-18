# coding: utf-8

# In[1]:


import numpy as np
import chainer
from chainer import cuda, Function, gradient_check, report, training, utils, Variable
from chainer import datasets, iterators, optimizers, serializers
from chainer import Link, Chain, ChainList
import chainer.functions as F
import chainer.links as L
from chainer.training import extensions
import re
import jieba
import jieba.posseg as psg
import gensim
from xml.etree import ElementTree as ET
from os import listdir
from os.path import isfile, join
from model_work import model_tovec

# In[2]:


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
        self.l_input.reset_state()

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

    def __call__(self, sentences):
        h = self.l1(sentences)
        h = self.l2(h)
        return h


# In[9]:


# 停用词
stop_words = ['的', '了', '一', '在', '和', '到', '一件', '一个', '一些', '一天', '一次', '一片', '一边', '一面',
              '去', '着', '那', '有个', '有', '里', '距', '于', '让', '其', '上', '下', '这', '个', '起', '给']
stop_words2 = ['我', '你', '他', '她', '它', '我们', '你们', '他们', '她们', '它们', '喵', '汪', '二', '三', '四', '五',
               '六', '七', '八', '九', '十', '零', '百', '千', '万', '亿', '又', '是', '也', '使', '地', '中', '前', '后']

maxSequence = 50
wordDim = 64
sentenceBatch = 1


# 将句子分词并处理标点和英文词。问号转为呢，感叹号转为啊，其他去掉。
def cut_and_excute(sentence):
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


# 测试
def get_emotion(sentence_list):
    tag = ['normal', 'happiness', 'sadness', 'surprise', 'fear', 'anger', 'disgust']
    model = SixClassClassify()
    serializers.load_npz('/home/embedded/Audink/Models/audink_models/sentence/sentence_model', model)

    article = article_to_vec(globalWordVector, sentence_list)

    y = model(article).data.argmax(axis=1)
    return y


