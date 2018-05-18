# 使用 Vokaturi库从wav，单声道文件中提取情绪
#
# For the sound file example.wav that comes with OpenVokaturi, the result should be:
#   Neutral: 0.760
#   Happy: 0.000
#   Sad: 0.238
#   Angry: 0.001
#   Fear: 0.000

import scipy.io.wavfile

from voice_ocean.util.voice_emotion_analysis import Vokaturi


def analysis_emotion(file_name):
    print("Loading util...")
    # Vokaturi.load("/home/embedded/Audink/voice_ocean/util/voice_emotion_analysis/lib-voice-emotion/Vokaturi_linux_arm64.o")
    Vokaturi.load("/home/embedded/Audink/voice_ocean/util/voice_emotion_analysis/OpenVokaturi-3-0-linux64.so")

    # print("Reading sound file...")/home/embedded/Audink/voice_ocean/util/voice_emotion_analysis/lib-voice-emotion/Vokaturi_linux64.o
    print(file_name)
    (sample_rate, samples) = scipy.io.wavfile.read(file_name)
    print("   sample rate %.3f Hz" % sample_rate)

    # print("Allocating Vokaturi sample array...")
    buffer_length = len(samples)
    print("   %d samples, %d channels" % (buffer_length, samples.ndim))
    c_buffer = Vokaturi.SampleArrayC(buffer_length)
    if samples.ndim == 1:  # mono
        c_buffer[:] = samples[:] / 32768.0
    else:  # stereo
        c_buffer[:] = 0.5 * (samples[:, 0] + 0.0 + samples[:, 1]) / 32768.0
    print('c_buffer',str(c_buffer))
    # print("Creating VokaturiVoice...")
    print('sample_rate',sample_rate)
    print('buffer_length',buffer_length)
    # 这里的buffer_length读东风破的时候为0
    voice = Vokaturi.Voice(sample_rate, buffer_length)

    # print("Filling VokaturiVoice with samples...")
    voice.fill(buffer_length, c_buffer)

    # print("Extracting emotions from VokaturiVoice...")
    quality = Vokaturi.Quality()
    emotionProbabilities = Vokaturi.EmotionProbabilities()
    voice.extract(quality, emotionProbabilities)
    emo = {'Neutral': emotionProbabilities.neutrality, }
    emo['Happy'] = emotionProbabilities.happiness
    emo['Sad'] = emotionProbabilities.sadness
    emo['Angry'] = emotionProbabilities.anger
    emo['Fear'] = emotionProbabilities.fear
    # if quality.valid:
        # print("Neutral: %.3f" % emotionProbabilities.neutrality)
        # print("Happy: %.3f" % emotionProbabilities.happiness)
        # print("Sad: %.3f" % emotionProbabilities.sadness)
        # print("Angry: %.3f" % emotionProbabilities.anger)
        # print("Fear: %.3f" % emotionProbabilities.fear)
    # else:
    #     print("Not enough loud")

    voice.destroy()
    if quality.valid:
        # 返回字典
        return emo
    else:
        #声音太小，
        return None

if __name__ == '__main__':
    pass
    # example
    # analysis_emotion('fear.wav')