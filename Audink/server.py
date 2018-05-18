from wsgiref.simple_server import make_server
import urllib.parse
from xml.dom.minidom import parse
import xml.dom.minidom
import effect
import data_process
import data_load
import voice_ocean.pyvoice as vp
import voice_ocean.mainproc as mp
import voice_ocean.fileprocess as fip
import voice_ocean.Recommend as recommend
import os
import re
import sys
import EmtAndBgm
import codecs
from textrank4zh import TextRank4Keyword, TextRank4Sentence
try:
    reload(sys)
    sys.setdefaultencoding('utf-8')
except:
    pass


stopw = [line.strip() for line in open("newneed/stop_word").readlines()]


def textSummary(fileName, finalName):
    text = codecs.open(fileName, 'r', 'utf-8').read()
    tr4w = TextRank4Keyword()

    tr4w.analyze(text=text, lower=True, window=2)  # py2中text必须是utf8编码的str或者unicode对象，py3中必须是utf8编码的bytes或者str对象
    #   关键词
    tr4s = TextRank4Sentence()
    tr4s.analyze(text=text, lower=True, source='all_filters')
    sentences = {}
    for item in tr4s.get_key_sentences(num=50):
        if len(item.sentence) not in sentences.keys():
            sentences[len(item.sentence)] = []
            sentences[len(item.sentence)].append(item.sentence)
            continue
        sentences[len(item.sentence)].append(item.sentence)

    key_sentences = []
    for i in range(3):
        key = max(sentences.keys())
        #         print(sentences[key])
        key_sentences.append(sentences[key][0])
        sentences.pop(key)

    text = re.split(pattern=r'[\u3000-\u301e\ufe10-\ufe19\ufe30-\ufe44\ufe50-\ufe6b\uff01-\uffee]', string=text)

    ks = []
    for te in text:
        for k in key_sentences:
            if str(te) in str(k) and str(k) not in ks:
                ks.append(k)

    line = ''
    for k in ks:
        line += k + " "

    fw = codecs.open(finalName + "output.txt", 'a', 'utf-8')
    fw.write(line)


def process_conference(resolutionFile, fileName, finalName):
    DOMTree = xml.dom.minidom.parse(resolutionFile)

    root = DOMTree.documentElement
    dialogues = root.getElementsByTagName('dialogue')

    nameIndex = 0
    names = {}
    contents = {}
    dialogs = []
    for dialogue in dialogues:
        nameNode = dialogue.getElementsByTagName('name')[0]
        name = nameNode.childNodes[0].data
        if name not in names.values():
            nameIndex += 1
            names[nameIndex] = name
            contents[nameIndex] = ''
        contentNode = dialogue.getElementsByTagName('content')[0]
        content = contentNode.childNodes[0].data
        index = [i for i in contents.keys() if names[i] == name][0]
        contents[index] += content
        dialogs.append(content)

    file = data_load.load_text(fileName)
    lines = file.readlines()
    paras = []
    for line in lines:
        para = data_process.cut_line(line)

        for every_sentence in para:
            for k in contents.keys():
                ck = data_process.del_punc(contents[k])
                est = data_process.del_punc(every_sentence.text)
                if est in ck:
                    every_sentence.set_role(k)
            # TODO 这里记得要把每一句转成 SentenceDefine, 然后放进sentences 目前已经写了,后期别忘

        paras.append(para)

    return paras


def application(environ, start_response):

    path = environ['PATH_INFO']
    print(path)
    start_response('200 OK', [('Content-Type', 'text/html')])

    request_body_size = int(environ['CONTENT_LENGTH'], 0)
    print(request_body_size, 1)
    requset_body = environ['wsgi.input'].read(request_body_size)
    print(requset_body, 2)
    requset_body = requset_body.decode("ascii")
    print(requset_body, 3)
    post_data = urllib.parse.unquote(requset_body)
    post_data = post_data.split("&")

    if path == "/audink/python/txt":

        fileName = ''  # 文档
        resolutionFile = ''  # 指代消解
        for i in post_data:
            if i.startswith("txtPosition"):
                fileName = i[12:]
                print(fileName)
            if i.startswith("xmlPosition"):
                resolutionFile = i[12:]
                print(resolutionFile)

        finalName = fileName[:-10]  # fileName[:-10]  # 最终生成的文件名

        print(fileName, finalName, resolutionFile)

        textSummary(fileName, finalName)  # 文本摘要

        paras = process_conference(resolutionFile, fileName, finalName)  # 句子切分完毕

        sentences = EmtAndBgm.article_to_read_sentences(paras)  # 情感和背景乐

        effect.effect(sentences)  # 音效
        data_process.PrintSentences(sentences)
        fileNameIndex = 0
        for sentence in sentences:
            fileNameIndex += 1
            data_load.TTS(sentence, result_name="output" + str(fileNameIndex))

        vp.add_backmusic_and_effect(sentences, outdir=finalName + "output" + ".mp3")

        return [b'<h1>word to music</h1>']

    if path == "/audink/python/audio":

        audio_file = ''

        for i in post_data:
            if i.startswith("audioPositon"):
                audio_file = i[15:]

        print(audio_file)

        sentences = mp.voice_process(audio_file)

        sentences = EmtAndBgm.houqi_emotion(sentences)

        effect.effect(sentences)

        vp.add_backmusic_and_effect(sentences)

        # # 5.删除文件夹
        print('删除',os.path.join(audio_file, 'emot'))
        fip.rm_dir(os.path.join(audio_file, 'emot'))

        data_process.PrintSentences(sentences)

        return [b'<h1>music to music</h1>']

    if path == "/audink/python/recommend":

        userid = -1
        for i in post_data:
            if i.startswith("userid"):
                userid = int(i[15:])

        Item = recommend.ItemBasedCF()
        Item.computeItemSimilarity()
        recommedDic = Item.Recommend(userid)
        print(type(recommedDic))

        return [b'<h1>recommend:' + recommedDic.keys + '</h1>']

    return [b'<h1>Hello WEB!</h1>']


def start_server():
    httpd = make_server("", 8000, application)
    print("start serving")
    httpd.serve_forever()


if __name__ == '__main__':
    start_server()
