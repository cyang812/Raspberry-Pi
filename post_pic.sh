sudo fswebcam --save /home/pi/class/baiduyun/yeelink.jpg
curl --request POST --url xxxxx --data-binary @"/home/pi/class/baiduyun/yeelink.jpg" --header "U-Apikey: xxxxxxx"
cd /home/pi/class/baiduyun
var=$(date +%y.%m.%d_%H.%M.%S)
mv yeelink.jpg ${var}.jpg
bypy.py syncup
echo ok

