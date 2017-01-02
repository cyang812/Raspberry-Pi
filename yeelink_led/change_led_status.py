#!/usr/bin/env python
# -*- coding: utf-8 -*-
import RPi.GPIO as GPIO
import json

LED = 20 #BCM pin

def init():
	GPIO.setwarnings(False)
	GPIO.setmode(GPIO.BCM)
	GPIO.setup(LED,GPIO.OUT)
		#print("init")	

def open():
	#with open(‘/home/pi/class/yeelink/led_status.json’,'r') as f:
	f = file('led_status.json')
	jsonData = json.load(f)
	led_status = jsonData["value"]
	#print("open")
	return led_status

def main():
	init()
	led_status = open()
	#print("start")
	try:
		while True:
			if led_status:
				#print(1)
				GPIO.output(LED,GPIO.HIGH)
			else:
				#print(0)
				GPIO.output(LED,GPIO.LOW)
	except:
		print("except")
	GPIO.cleanup()

if __name__ == '__main__':
	main()
