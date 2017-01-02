cd /home/pi/class/DHT11/Adafruit_Python_DHT/examples
#pwd
#ls
sudo python AdafruitDHT.py 11 16 >/home/pi/class/DHT11/DHT11_Data.txt
#cat /home/pi/class/DHT11/DHT11_Data.txt
cd /home/pi/class/DHT11
sudo python Post_DHT11_data.py &
