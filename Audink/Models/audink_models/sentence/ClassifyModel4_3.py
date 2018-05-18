
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


# In[2]:


# el1 -----------------------------------------------------
# class RnnClassify(Chain):
#     def __init__(self):
#         super(RnnClassify, self).__init__()
#         with self.init_scope():
#             self.l_input = L.LSTM(None, 64)
#             self.l_merge = L.Linear(None, 64)
            
#     def reset_state(self):
#         self.l2.reset_state()
        
#     def __call__(self, x):
#         sentences = x
#         h = F.relu(F.dropout(self.l_input(sentences), 0.5))
#         h = F.relu(F.dropout(self.l_merge(h), 0.5))
#         return h

# el2 -----------------------------------------------------
# 增加隐藏层神经元数量
# class RnnClassify(Chain):
#     def __init__(self):
#         super(RnnClassify, self).__init__()
#         with self.init_scope():
#             self.l_input = L.LSTM(None, 64)
#             self.l_merge = L.Linear(None, 128)
            
#     def reset_state(self):
#         self.l2.reset_state()
        
#     def __call__(self, x):
#         sentences = x
#         h = F.relu(F.dropout(self.l_input(sentences), 0.5))
#         h = F.relu(F.dropout(self.l_merge(h), 0.5))
#         return h

# el3 -----------------------------------------------------
# 加一层隐藏层
# class RnnClassify(Chain):
#     def __init__(self):
#         super(RnnClassify, self).__init__()
#         with self.init_scope():
#             self.l_input = L.LSTM(None, 64)
#             self.l_merge = L.Linear(None, 128)
#             self.l_merge2 = L.Linear(None, 64)
            
#     def reset_state(self):
#         self.l2.reset_state()
        
#     def __call__(self, x):
#         sentences = x
#         h = F.relu(F.dropout(self.l_input(sentences), 0.5))
#         h = F.relu(F.dropout(self.l_merge(h), 0.5))
#         h = F.relu(F.dropout(self.l_merge2(h), 0.5))
#         return h

# el4 -----------------------------------------------------
# 加一层隐藏层并增加神经元
# class RnnClassify(Chain):
#     def __init__(self):
#         super(RnnClassify, self).__init__()
#         with self.init_scope():
#             self.l_input = L.LSTM(None, 128)
#             self.l_merge = L.Linear(None, 512)
#             self.l_merge2 = L.Linear(None, 64)
            
#     def reset_state(self):
#         self.l2.reset_state()
        
#     def __call__(self, x):
#         sentences = x
#         h = F.relu(F.dropout(self.l_input(sentences), 0.5))
#         h = F.relu(F.dropout(self.l_merge(h), 0.5))
#         h = F.relu(F.dropout(self.l_merge2(h), 0.5))
#         return h
    
# el5 -----------------------------------------------------
# 继续增加神经元
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
    
# el6 -----------------------------------------------------
# 使用两层LSTM
# class RnnClassify(Chain):
#     def __init__(self):
#         super(RnnClassify, self).__init__()
#         with self.init_scope():
#             self.l_input = L.LSTM(None, 128)
#             self.l_merge = L.LSTM(None, 128)
#             self.l_merge2 = L.Linear(None, 512)
            
#     def reset_state(self):
#         self.l2.reset_state()
        
#     def __call__(self, x):
#         sentences = x
#         h = F.relu(F.dropout(self.l_input(sentences), 0.5))
#         h = F.relu(F.dropout(self.l_merge(h), 0.5))
#         h = F.relu(F.dropout(self.l_merge2(h), 0.5))
#         return h
    
# el7 -----------------------------------------------------
# 使用纯线性层
# class RnnClassify(Chain):
#     def __init__(self):
#         super(RnnClassify, self).__init__()
#         with self.init_scope():
#             self.l_input = L.Linear(None, 2048)
#             self.l_merge = L.Linear(None, 1024)
#             self.l_merge2 = L.Linear(None, 512)
            
#     def reset_state(self):
#         self.l2.reset_state()
        
#     def __call__(self, x):
#         sentences = x
#         h = F.relu(F.dropout(self.l_input(sentences), 0.5))
#         h = F.relu(F.dropout(self.l_merge(h), 0.5))
#         h = F.relu(F.dropout(self.l_merge2(h), 0.5))
#         return h


# In[3]:


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


# ### 导入数据

# In[50]:


# 停用词
stop_words = ['的','了','一', '在', '和', '到', '一件','一个','一些','一天','一次','一片','一边','一面', 
              '去', '着', '那', '有个', '有', '里','距','于','让','其','上','下','这','个', '起', '给']
stop_words2 = ['我','你','他','她','它','我们','你们','他们','她们','它们','喵','汪','二','三','四','五',
               '六','七','八','九','十','零','百','千','万','亿','又','是','也','使','地','中','前','后']


# In[51]:


maxSequence = 50
wordDim = 64
sentenceBatch = 1

# 将句子分词并处理标点和英文词。问号转为呢，感叹号转为啊，其他去掉。
def cut_and_excute(sentence):
    words = psg.cut(sentence)
    words = [i.word for i in words if (i.flag != 'eng' and i.flag != 'x' and i.word not in stop_words) or (i.flag == 'x' and (i.word == '？' or i.word == '！'))]
    for i in range(len(words)):
        if words[i] == '？':
            words[i] = '呢'
        if words[i] == '！':
            words[i] = '啊'
    if len(words)>50:
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
    for sentence in sentences:
        result.append(cutted_sentence_to_vec(wordVector, cut_and_excute(sentence), maxSequence, wordDim))
    return np.array(result, dtype=np.float32)


# 将xml文件转换为[(np[sentence], tag)]
def xml_to_list(wordVector, file):
    result = []
    temp_sentence = [np.array([[0]*wordDim]*maxSequence, dtype=np.float32)]*(sentenceBatch-1)
    
    print(file)
    
    root = ET.parse(file).getroot()
    i = 0
    for sentence in root.findall('sentence'):
        cutted = cutted_sentence_to_vec(wordVector, cut_and_excute(sentence.get('text')))
        temp_sentence.append(cutted)
        tag = 0
        if sentence.get('emotion') == 'happiness':
            tag = 1
        if sentence.get('emotion') == 'sadness':
            tag = 2
        if sentence.get('emotion') == 'surprise':
            tag = 3
        if sentence.get('emotion') == 'fear':
            tag = 4
        if sentence.get('emotion') == 'anger':
            tag = 5
        if sentence.get('emotion') == 'disgust':
            tag = 6
        result.append((np.array(temp_sentence[i:],dtype=np.float32), tag))
        i += 1
    return result
        

# 准备数据集
def prepare_dataset(wordVector):
    trainFiles = ['NovelDataset/train/' + f for f in listdir('NovelDataset/train') if isfile(join('NovelDataset/train/',f))]
    testFiles = ['NovelDataset/test/' + f for f in listdir('NovelDataset/test') if isfile(join('NovelDataset/test/',f))]
    trainDataset = []
    for file in trainFiles:
        trainDataset += xml_to_list(wordVector, file)
    testDataset = []
    for file in testFiles:
        testDataset += xml_to_list(wordVector, file)
    
    return trainDataset, testDataset


# 词数统计
def statistic_words():
    trainFiles = ['NovelDataset/train/' + f for f in listdir('NovelDataset/train') if isfile(join('NovelDataset/train/',f))]
    lens = []
    cuts = []
    for file in trainFiles:
        root = ET.parse(file).getroot()
        for sentence in root.findall('sentence'):
            cutted = cut_and_excute(sentence.get('text'))
            lens.append(len(cutted))
            if len(cutted)>50:
                cuts.append([cut for cut in cutted])
            
    # 分词测试
    print(cuts)
    
    # 查看图
    import matplotlib.pyplot as plt
    get_ipython().run_line_magic('matplotlib', 'inline')
    plt.hist(lens, 50)
    plt.xlabel('Sequence Length')
    plt.ylabel('Frequency')
    plt.axis([0, 200, 0, 1250])
    plt.show()


# In[ ]:


# 定义新损失函数
def power_adjust(tag):
    if tag == 0:
        return 3.0
    elif tag == 1:
        return 6.0
    elif tag == 4:
        return 15.0
    elif tag == 6:
        return 21.0
    else:
        return 10.0


def my_softmax_cross_entropy(x, t):
    weight = np.array([[power_adjust(tag)] for tag in t], dtype=np.float32)
    return F.softmax_cross_entropy(x * weight, t)


# ### 导入词向量

# In[ ]:


globalWordVector = gensim.models.KeyedVectors.load_word2vec_format('../news_12g_baidubaike_20g_novel_90g_embedding_64.bin', binary=True)


# ### 构建数据集

# In[ ]:


# 导入数据集
train, test = prepare_dataset(globalWordVector)


# In[ ]:


batchsize = 2048
train_iter = iterators.SerialIterator(train, batchsize)
test_iter = iterators.SerialIterator(test, batchsize, False, False)


# ### 准备训练

# In[ ]:


max_epoch = 3000
model = L.Classifier(SixClassClassify(), lossfun=my_softmax_cross_entropy)
# model = L.Classifier(SixClassClassify())
optimizer = optimizers.Adam()
optimizer.setup(model)
updater = training.StandardUpdater(train_iter, optimizer)
trainer = training.Trainer(updater, (max_epoch, 'epoch'), out='classify_result_3')


# ### 训练器扩展

# In[ ]:


# 收集每一次迭代的loss和accuracy并保存
trainer.extend(extensions.LogReport(trigger=(1, 'epoch')))
# 保存trainer（包括Updater和Optimizer）
# trainer.extend(extensions.snapshot(filename='snapshot_epoch-{.updater.epoch}'), trigger=(10, 'epoch'))
# 保存部分trainer（仅model？）
trainer.extend(extensions.snapshot_object(model.predictor, filename='model_epoch-{.updater.epoch}'), trigger=triggers.MaxValueTrigger('validation/main/accuracy',(1,'epoch')))
# 保存计算图
trainer.extend(extensions.dump_graph('main/loss'))
# 评估
trainer.extend(extensions.Evaluator(test_iter, model), trigger=(1, 'epoch'))
# 打印输出
trainer.extend(extensions.PrintReport(['epoch', 'main/loss', 'main/accuracy', 'validation/main/loss', 'validation/main/accuracy', 'elapsed_time']), trigger=(1, 'epoch'))
# 图像形式保存输出
trainer.extend(extensions.PlotReport(['main/loss', 'validation/main/loss'], x_key='epoch', file_name='loss.png'), trigger=(1, 'epoch'))
trainer.extend(extensions.PlotReport(['main/accuracy', 'validation/main/accuracy'], x_key='epoch', file_name='accuracy.png'), trigger=(1, 'epoch'))


# ### 开始训练

# In[ ]:


trainer.run()

