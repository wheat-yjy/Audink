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
from chainer.training import triggers
import re
import jieba
import jieba.posseg as psg
import gensim
from xml.etree import ElementTree as ET
from os import listdir
from os.path import isfile, join
import matplotlib.pyplot as plt
from random import shuffle
from model_work import model_tovec

# In[2]:


# 导入词向量
# globalWordVector = gensim.models.KeyedVectors.load_word2vec_format('../news_12g_baidubaike_20g_novel_90g_embedding_64.bin', binary=True)
globalWordVector = model_tovec


# In[3]:


class ConvSubjectClassify(Chain):
    def __init__(self):
        super(ConvSubjectClassify, self).__init__()
        with self.init_scope():
            self.l_input = L.ConvolutionND(1, 1000, 6, 3)
            self.l_linear1 = L.Linear(None, 8000)
            self.l_linear2 = L.Linear(None, 200)
            self.l_linear3 = L.Linear(None, 10)

    def __call__(self, x):
        h = x
        h = F.dropout(h, 0.2)
        h = self.l_input(h)
        h = F.relu(h)
        h = F.max_pooling_nd(h, 3)
        h = F.relu(h)
        h = self.l_linear1(h)
        h = F.dropout(h, 0.2)
        h = F.relu(h)
        h = self.l_linear2(h)
        h = F.dropout(h, 0.2)
        h = F.relu(h)
        h = self.l_linear3(h)
        return h


# In[4]:


stop_words3 = [line.strip() for line in open('/home/embedded/Audink/Models/audink_models/para/stop_word', 'r', encoding='utf-8').readlines()]
stop_flag = ['eng', 'x', 'un', 'ul', 'uj', 'nr', 'r']

# In[11]:


maxSequence = 1000
wordDim = 64


# In[24]:


def temp_cut(file):
    article = file.read()
    words = psg.cut(article)
    words = [i.word for i in words if (i.flag not in stop_flag and i.word not in stop_words3)]
    return words


def cut_text_into_word_vecs(text):
    words = psg.cut(text)
    words = [i.word for i in words if (i.flag not in stop_flag and i.word not in stop_words3)]
    result = []
    for word in words:
        try:
            vec = globalWordVector[word]
            result.append(vec)
        except KeyError:
            pass
    for i in range(maxSequence - len(result)):
        result.append([0] * wordDim)
    return np.array(result[:maxSequence], dtype=np.float32)


def get_subject(text):
    model = ConvSubjectClassify()
    serializers.load_npz('/home/embedded/Audink/Models/audink_models/subject/subject_model', model)

    article = cut_text_into_word_vecs(text)

    y = model(np.array([article])).data.argmax(axis=1)

    return y
