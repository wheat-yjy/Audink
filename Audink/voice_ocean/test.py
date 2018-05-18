import wave
import re
from textrank4zh import TextRank4Keyword, TextRank4Sentence
import codecs


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
    print("原文内容")
    print(sentences)
    fw = codecs.open(finalName + "output.txt", 'a', 'utf-8')
    fw.write(line)


textSummary("/home/embedded/test", "/home/embedded/")

file = open("/home/embedded/"+"output.txt")
print("摘要内容：")
for f in file.readlines():
    print(f)