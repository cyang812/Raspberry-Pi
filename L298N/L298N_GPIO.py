#!/usr/bin/python
import RPi.GPIO as GPIO
import time

bit1 = 6
bit2 = 13
bit3 = 19
bit4 = 26

GPIO.setmode(GPIO.BCM)
time.sleep(1)

GPIO.setup(bit1, GPIO.OUT)
GPIO.output(bit1, GPIO.LOW)

GPIO.setup(bit2, GPIO.OUT)
GPIO.output(bit2, GPIO.HIGH)

GPIO.setup(bit3, GPIO.OUT)
GPIO.output(bit3, GPIO.LOW)

GPIO.setup(bit4, GPIO.OUT)
GPIO.output(bit4, GPIO.HIGH)

time.sleep(30)

GPIO.setup(bit1, GPIO.OUT)
GPIO.output(bit1, GPIO.LOW)

GPIO.setup(bit2, GPIO.OUT)
GPIO.output(bit2, GPIO.HIGH)

GPIO.setup(bit3, GPIO.OUT)
GPIO.output(bit3, GPIO.LOW)

GPIO.setup(bit4, GPIO.OUT)
GPIO.output(bit4, GPIO.HIGH)

GPIO.cleanup()
