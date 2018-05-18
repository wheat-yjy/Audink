import sqlite3
import logging


conn = sqlite3.connect('testDB.db')
c = conn.cursor()


def insert(id, fileName, filePath):
    command = "INSERT INTO SOUND_EFFECT (ID,NAME,PATH) \
      VALUES ("+str(id)+', '+"'"+fileName+"', "+"'"+filePath+"' )"
    print(command, "             ", id)
    c.execute(command)
    conn.commit()


def LIKE(key):
    command = "SELECT NAME FROM SOUND_EFFECT WHERE NAME LIKE '"+"% "+key+" %"+"'"
    logging.info(command)
    c.execute(command)
    conn.commit()


# 插入完了 理论上说应该没用了
# filePaths = glob.glob("../intel/sound_effect/*/*/*.wav")
# print(len(filePaths))
# for i in range(len(filePaths)):
#     name = filePaths[i].split('/')[-1]
#     if "'" in name:
#         name = name.replace("'", "")
#         filePaths[i] = filePaths[i].replace("'", "")
#     insert(i, name, filePaths[i])

# CREATE TABLE SOUND_EFFECT
# (ID INT PRIMARY KEY    NOT NULL,
# NAME           TEXT    NOT NULL,
# PATH           TEXT    NOT NULL);