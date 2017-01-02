import RPi.GPIO as GPIO
import time

bit_in = 17
bit_out = 27

def init():
    GPIO.setwarnings(False)
    GPIO.setmode(GPIO.BCM)
    GPIO.setup(bit_in,GPIO.IN)
    GPIO.setup(bit_out,GPIO.OUT)
#pass
def beep():
    while GPIO.input(bit_in):
        GPIO.output(bit_out,True)
        time.sleep(0.5)
        GPIO.output(bit_out,False)
        time.sleep(0.5)
        #print 'beep'
def detct():
    for i in range(1,101):
    #while True:
        if GPIO.input(bit_in) == True:
            print time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time()))+" Waring:Someone is Closing!"
            beep()
        else:
            GPIO.output(bit_out,False)
            print time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time()))+" Not anybody!"
        time.sleep(2)
time.sleep(5)
init()
detct()
GPIO.cleanup()
