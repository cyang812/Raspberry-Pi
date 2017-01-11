#!/usr/bin/env python
# -*- coding: utf-8 -*-
import commands
import time
import requests
import json


def get_cpu_temp():
    tempFile = open( "/sys/class/thermal/thermal_zone0/temp" )
    cpu_temp = tempFile.read()
    tempFile.close()
    return float(cpu_temp)/1000
    # Uncomment the next line if you want the temp in Fahrenheit
    #return float(1.8*cpu_temp)+32
 
def get_gpu_temp():
    gpu_temp = commands.getoutput( '/opt/vc/bin/vcgencmd measure_temp' ).replace( 'temp=', '' ).replace( '\'C', '' )
    return  float(gpu_temp)
    # Uncomment the next line if you want the temp in Fahrenheit
    # return float(1.8* gpu_temp)+32

def post_temp(apiurl,temp):
    #apiurl = 'http://api.yeelink.net/v1.0/device/353458/sensor/398547/datapoints'
    # 用户密码, 指定上传编码为JSON格式
    apiheaders = {'U-ApiKey': 'dc30cb055fc3d9ccc470b5867ef84f8a', 'content-type': 'application/json'}
    # 字典类型数据，在post过程中被json.dumps转换为JSON格式字符串 {"value": 48.123}
    payload = {'value': temp}
    # 发送请求
    return_info = requests.post(apiurl, headers=apiheaders, data=json.dumps(payload))


def main():
    # print "CPU temp: ", str(get_cpu_temp())
    # print "GPU temp: ", str(get_gpu_temp())
    cpu_temp_url = 'http://api.yeelink.net/v1.0/device/353448/sensor/399419/datapoints'
    gpu_temp_url = 'http://api.yeelink.net/v1.0/device/353448/sensor/399419/datapoints'
    cpu_temp = get_cpu_temp()
    gpu_temp = get_gpu_temp()
    post_temp(cpu_temp_url,cpu_temp)
    time.sleep(15)
    post_temp(gpu_temp_url,gpu_temp)


if __name__ == '__main__':
    main()