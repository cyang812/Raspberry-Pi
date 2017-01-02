#!/usr/bin/env python
# -*- coding: utf-8 -*-
import requests
import json
import time

def main():
    fileRecord = open("post_result.txt", "w")
    fileRecord.write("connect to yeelink\n");
    fileRecord.close()
    while True:
        # 打开文件
        #file = open("/sys/class/thermal/thermal_zone0/temp")
        file = open("DHT11_Data.txt")
        # 读取结果，并转换为浮点数
        buff = file.read().split("+,+")
        temp = buff[0]
        hum = buff[1].split("\n")[0]
        #print(buff)
        #print(temp)
        #print(hum)

        # 关闭文件
        file.close()

        # 温度设备URI
        apiurl = 'your apiurl'
        # 用户密码, 指定上传编码为JSON格式
        apiheaders = {'U-ApiKey': 'your U-ApiKey', 'content-type': 'application/json'}
        # 字典类型数据，在post过程中被json.dumps转换为JSON格式字符串 {"value": 48.123}
        payload = {'value': temp}
        # 发送请求
        r_temp = requests.post(apiurl, headers=apiheaders, data=json.dumps(payload))

        time.sleep(15) #60S 数据上传的最小间隔为10秒

        # 湿度设备URI
        apiurl = 'your apiurl'
        # 用户密码, 指定上传编码为JSON格式
        apiheaders = {'U-ApiKey': 'your U-ApiKey', 'content-type': 'application/json'}
        # 字典类型数据，在post过程中被json.dumps转换为JSON格式字符串 {"value": 48.123}
        payload = {'value': hum}
        # 发送请求
        r_hum = requests.post(apiurl, headers=apiheaders, data=json.dumps(payload))

        # 向控制台打印结果
        fileRecord = open("post_result.txt", "a")
        strTime = time.strftime('%Y-%m-%d:%H-%M-%S', time.localtime(time.time()))

        fileRecord.writelines(strTime + "\n")
        strTemp = "temp : %.1f" %float(temp) + "\n"
        fileRecord.writelines(strTemp)
        fileRecord.writelines(str(r_temp.status_code) + "\n")

        fileRecord.writelines(strTime + "\n")
        strHum = "temp : %.1f" %float(hum) + "\n"
        fileRecord.writelines(strHum)
        fileRecord.writelines(str(r_hum.status_code) + "\n")

        fileRecord.close()

        #print(r_temp)
        #print(r_hum)
        time.sleep(60)
        #print("ok")

if __name__ == '__main__':
    main()
