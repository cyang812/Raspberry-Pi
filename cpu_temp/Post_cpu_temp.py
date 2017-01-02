#!/usr/bin/env python
# -*- coding: utf-8 -*-
import requests
import json
import time

def main():
    fileRecord = open("result.txt", "w")
    fileRecord.write("connect to yeelink\n");
    fileRecord.close()
    while True:
        # 打开文件
        file = open("/sys/class/thermal/thermal_zone0/temp")
        #file = open("cpu_temp_test.txt")
        # 读取结果，并转换为浮点数
        temp = int(int(file.read())/1000)
       	#print(temp)
		# 关闭文件
        file.close()

        # 设备URI
        apiurl = 'your apiurl'
        # 用户密码, 指定上传编码为JSON格式
        apiheaders = {'U-ApiKey': 'your U-ApiKey', 'content-type': 'application/json'}
        # 字典类型数据，在post过程中被json.dumps转换为JSON格式字符串 {"value": 48.123}
        payload = {'value': temp}
        # 发送请求
        r = requests.post(apiurl, headers=apiheaders, data=json.dumps(payload))

        # 向控制台打印结果
        fileRecord = open("result.txt", "a")
        strTime = time.strftime('%Y-%m-%d:%H-%M-%S', time.localtime(time.time()))
        fileRecord.writelines(strTime + "\n")
        strTemp = "temp : %.1f" % temp + "\n"
        fileRecord.writelines(strTemp)
        fileRecord.writelines(str(r.status_code) + "\n")
        fileRecord.close()

        time.sleep(60)
        #print("ok")

if __name__ == '__main__':
    main()
