import re
from voice_ocean.sentence_define import SentenceDefine

# def cut_line(line):
#     # TODO 小句切分, 应该是ok的 可能会有错误
#     """
#     :param line:  读到的文件里的一行
#     :return:  返回一个list, 每一个元素都是切分好的小句子
#     """
#     sentences = re.split(pattern=r'[\u3000-\u301e\ufe10-\ufe19\ufe30-\ufe44\ufe50-\ufe6b\uff01-\uffee]', string=line)
#     sentences = [k for k in sentences if not re.match(pattern=r'[\u3000-\u301e\ufe10-\ufe19\ufe30-\ufe44\ufe50-\ufe6b\uff01-\uffee]', string=k)]
#     return sentences


def PrintSentences(sentences):
    """
    把所有句子打出来。
    :param sentences:
    :return:
    """
    for sentence in sentences:
        print(sentence.text, sentence.emotion, sentence.keyword, sentence.is_dialog, sentence.sound_effect, sentence.speech_file, sentence.bgm, sentence.role)


def cut_line(text: str):
    """将文章划分为句子。要求文本不能有换行。
    :return [sentence]"""

    # 单标点处理 如果之前不是单标点：加入，否则：不处理；如果之后不是回引号：断句，否则：不处理
    # 回引号处理 如果之前是单标点或逗号：加入、断句，否则：加入
    # 冒号处理 如果之后是前引号：加入、断句，否则：加入
    # 逗号处理 如果之后是前引号：找到下一个回引号，如果之前是单引号则断句，否则不断句

    # 断句标点
    stop_puns = ['。', '！', '？', '!', '?', '…']

    # 预处理：去除空格和换行，双省略号换成单省略号
    text = text.replace(' ', '').replace('\n', '').replace('　', '').replace('……', '…')

    result = []
    temp_text = ''
    for index in range(len(text)):
        cur = text[index]
        if cur in stop_puns:
            # 单标点+单标点
            if index > 0 and text[index - 1] not in stop_puns:
                temp_text += cur
            # 单标点+回引号
            if index < len(text)-1 and text[index + 1] != '”':
                result.append(temp_text)
                temp_text = ''
        elif cur == '”':
            temp_text += cur
            # 单标点+回引号
            if index > 0 and text[index - 1] in stop_puns + [',', '，']:
                result.append(temp_text)
                temp_text = ''
        elif cur in [':', '：']:
            temp_text += cur
            # 冒号+前引号
            if not(index < len(temp_text)-1 and text[index + 1] == '“'):
                result.append(temp_text)
                temp_text = ''
        elif cur in [',', '，']:
            temp_text += cur
            # 逗号+前引号
            if not(index < len(text)-1 and text[index + 1] != '“'):
                result.append(temp_text)
                temp_text = ''
        else:
            temp_text += cur

    if temp_text != '':
        result.append(temp_text)

    sentences = []

    for r in result:
        sentences.append(SentenceDefine(r))


    return sentences

# print([i.text for i in cut_line('一天，“我怎么怎么怎么”怎么。恩。')])

def del_punc(ori):
    ret = ""
    for char in ori:
        if re.match(pattern=r'[\u3000-\u301e\ufe10-\ufe19\ufe30-\ufe44\ufe50-\ufe6b\uff01-\uffee“”?]', string=char):
            continue
        ret += char
    return ret

