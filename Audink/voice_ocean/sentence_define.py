class SentenceDefine(object):
    def __init__(self, text):
        self.text = text
        self.emotion = {}  # 情感标签，string
        self.keyword = None
        self.sound_effect = None  # 声效，无音效就是０，会变成与音效相关的关键字，最后成为音效文件名,也就是发给逸初最后的形态
        self.is_dialog = None  # 对话和旁白的区分 0接上一句 1旁白 2对话
        self.speech_file = ''  # 语音合成的文件名带路径
        self.bgm = ''  # 发给逸初的时候是
        self.role = 0  # 没角色就是0 ,有角色就是 1 2 3

    def set_effect_key(self, key):
        self.keyword = key

    def set_emotion(self, emotion):
        self.emotion = emotion

    def set_sound_effect(self, sound_effect):
        self.sound_effect = sound_effect

    def set_dialog(self, diadiadia):
        self.is_dialog = diadiadia

    def set_speechFile(self, file_name):
        self.speech_file = file_name

    def set_bgm(self, bgmusic):
        self.bgm = bgmusic

    def set_role(self, rol):
        self.role = rol
