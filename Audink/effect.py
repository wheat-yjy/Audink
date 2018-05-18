import jieba

stopw = [line.strip() for line in open("/home/embedded/Audink/newneed/stop_word").readlines()]
cues = ["响", "声", "叫"]
effect_list = []
effect_file = open('/home/embedded/Audink/newneed/effect')
for line in effect_file.readlines():
    line = line.replace("\n", "")
    words = line.split(' ')

    effect_list.extend(words)
effect_list = effect_list[:len(effect_list)]


def cues_with_dataset(ws):
    dz = []
    for w in ws:
        for z in w:
            dz.append(z)

    k_cue = list(set(cues) & set(dz))
    if len(k_cue) == 0:
        return None
    return k_cue


def effect(sentences):
    if sentences is None:
        return

    for sen in sentences:
        ek = single_effect(sen.text)
        if ek is not None:
            sen.set_effect_key(ek)
            print(sen.keyword, "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
            sen.set_sound_effect(getFile(ek))
            print(sen.keyword, "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB")



def single_effect(text):
    words = jieba.cut(text, cut_all=False)
    words = list(set(words) - set(stopw))
    k_cues = cues_with_dataset(words)
    print(words, k_cues)
    if k_cues is not None:
        for kc in k_cues:
            contain_kc_word = [word for word in words if kc in word][0]
            if len(contain_kc_word) > len(kc):
                ckcw = contain_kc_word.replace(kc, '')
                for z in ckcw:
                    print(z, effect_list)
                    effect = list(set(z) & set(effect_list))
                    if len(effect) > 0:
                        print(effect, words, "上面")
                        return effect[0]
            if len(contain_kc_word) == len(kc):
                words.remove(kc)
                for word in words:
                    effect = list(set(word) & set(effect_list))
                    if len(effect) > 0:
                        print(effect, words, "下面")
                        return effect[0]
    if k_cues is None:
        for word in words:
            effect = list(set(word) & set(effect_list))
            if len(effect) > 0:
                print(effect, words, "再下面")
                return effect[0]
        for word in words:
            for z in word:
                effect = list(set(z) & set(effect_list))
                if len(effect) > 0:
                    print(effect, words, "最最下面")
                    return effect[0]


def getFile(ek):
    return "/home/embedded/Audink/newneed/effects/" + ek + ".mp3"
