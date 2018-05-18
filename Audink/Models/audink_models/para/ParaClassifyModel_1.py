# coding: utf-8

# # 使用单个情感

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

# ### 导入词向量

# In[2]:


# globalWordVector = gensim.models.KeyedVectors.load_word2vec_format('../news_12g_baidubaike_20g_novel_90g_embedding_64.bin', binary=True)
globalWordVector = model_tovec


# In[3]:


class ConvEmotionClassify(Chain):
    def __init__(self):
        super(ConvEmotionClassify, self).__init__()
        with self.init_scope():
            self.l_input = L.ConvolutionND(1, 600, 6, 3)
            self.l_linear1 = L.Linear(None, 8000)
            self.l_linear2 = L.Linear(None, 200)
            self.l_linear3 = L.Linear(None, 4)

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


# ### 导入数据

# In[5]:


# 停用词
stop_words = ['的', '了', '一', '在', '和', '到', '一件', '一个', '一些', '一天', '一次', '一片', '一边', '一面',
              '去', '着', '那', '有个', '有', '里', '距', '于', '让', '其', '上', '下', '这', '个', '起', '给']
stop_words2 = ['我', '你', '他', '她', '它', '我们', '你们', '他们', '她们', '它们', '喵', '汪', '二', '三', '四', '五',
               '六', '七', '八', '九', '十', '零', '百', '千', '万', '亿', '又', '是', '也', '使', '地', '中', '前', '后']
stop_words3 = [line.strip() for line in open('stop_word', 'r', encoding='utf-8').readlines()]
stop_flag = ['eng', 'x', 'un', 'ul', 'uj', 'nr', 'r']

# In[6]:


maxSequence = 600
wordDim = 64


def temp_cut(file):
    article = file.read()
    words = psg.cut(article)
    words = [i.word for i in words if (i.flag not in stop_flag and i.word not in stop_words3)]
    return words


# 把文件转换为词向量
def cut_file_into_word_vecs(file):
    article = file.read()
    words = psg.cut(article)
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


def prepare_dataset():
    happiness_file = ['DataSet/happiness/' + f for f in listdir('DataSet/happiness') if
                      isfile(join('DataSet/happiness/', f))]
    sadness_file = ['DataSet/sadness/' + f for f in listdir('DataSet/sadness') if isfile(join('DataSet/sadness/', f))]
    fear_file = ['DataSet/fear/' + f for f in listdir('DataSet/fear') if isfile(join('DataSet/fear/', f))]
    excited_file = ['DataSet/excited/' + f for f in listdir('DataSet/excited') if isfile(join('DataSet/excited/', f))]

    happiness_tag_file = [(cut_file_into_word_vecs(open(f)), 0) for f in happiness_file]
    sadness_tag_file = [(cut_file_into_word_vecs(open(f)), 1) for f in sadness_file]
    fear_tag_file = [(cut_file_into_word_vecs(open(f)), 2) for f in fear_file]
    excited_tag_file = [(cut_file_into_word_vecs(open(f)), 3) for f in excited_file]

    shuffle(happiness_tag_file)
    shuffle(sadness_tag_file)
    shuffle(fear_tag_file)
    shuffle(excited_tag_file)

    train_set = happiness_tag_file[:-4] + sadness_tag_file[:-4] + fear_tag_file[:-4] + excited_tag_file[:-4]
    test_set = happiness_tag_file[-4:] + sadness_tag_file[-4:] + fear_tag_file[-4:] + excited_tag_file[-4:]

    return train_set, test_set


# 词数统计
def statistic_words():
    trainFiles = ['DataSet/happiness/' + f for f in listdir('DataSet/happiness') if
                  isfile(join('DataSet/happiness/', f))] + [
                     'DataSet/fear/' + f for f in listdir('DataSet/fear') if isfile(join('DataSet/fear/', f))] + [
                     'DataSet/sadness/' + f for f in listdir('DataSet/sadness') if
                     isfile(join('DataSet/sadness/', f))] + [
                     'DataSet/excited/' + f for f in listdir('DataSet/excited') if isfile(join('DataSet/excited/', f))]
    lens = []
    for file in trainFiles:
        file = open(file)
        article = temp_cut(file)
        lens.append(len(article))

    print(lens)

    # 查看图
    import matplotlib.pyplot as plt
    get_ipython().run_line_magic('matplotlib', 'inline')
    plt.hist(lens, 50)
    plt.xlabel('Sequence Length')
    plt.ylabel('Frequency')
    plt.axis([0, 800, 0, 60])
    plt.show()


# In[7]:


# statistic_words()


# ### 构建数据集

# In[9]:


# 导入数据集
train, test = prepare_dataset()

# In[10]:


batchsize = 1024
train_iter = iterators.SerialIterator(train, batchsize)
test_iter = iterators.SerialIterator(test, batchsize, False, False)

# ### 准备训练

# In[11]:


max_epoch = 4000
# model = L.Classifier(SixClassClassify(), lossfun=my_softmax_cross_entropy)
model = L.Classifier(ConvEmotionClassify())
optimizer = optimizers.Adam()
optimizer.setup(model)
updater = training.StandardUpdater(train_iter, optimizer)
trainer = training.Trainer(updater, (max_epoch, 'epoch'), out='para_classify_result_4')

# ### 训练器扩展

# In[12]:


# 收集每一次迭代的loss和accuracy并保存
trainer.extend(extensions.LogReport(trigger=(1, 'epoch')))
# 保存trainer（包括Updater和Optimizer）
# trainer.extend(extensions.snapshot(filename='snapshot_epoch-{.updater.epoch}'), trigger=(10, 'epoch'))
# 保存部分trainer（仅model？）
trainer.extend(extensions.snapshot_object(model.predictor, filename='model_epoch-{.updater.epoch}'),
               trigger=triggers.MaxValueTrigger('validation/main/accuracy', (1, 'epoch')))
# 保存计算图
trainer.extend(extensions.dump_graph('main/loss'))
# 评估
trainer.extend(extensions.Evaluator(test_iter, model), trigger=(1, 'epoch'))
# 打印输出
trainer.extend(extensions.PrintReport(
    ['epoch', 'main/loss', 'main/accuracy', 'validation/main/loss', 'validation/main/accuracy', 'elapsed_time']),
               trigger=(1, 'epoch'))
# 图像形式保存输出
trainer.extend(extensions.PlotReport(['main/loss', 'validation/main/loss'], x_key='epoch', file_name='loss.png'),
               trigger=(1, 'epoch'))
trainer.extend(
    extensions.PlotReport(['main/accuracy', 'validation/main/accuracy'], x_key='epoch', file_name='accuracy.png'),
    trigger=(1, 'epoch'))

# ### 开始训练

# In[14]:


trainer.run()
