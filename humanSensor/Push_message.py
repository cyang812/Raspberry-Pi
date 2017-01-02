import pycurl, json
import time
from StringIO import StringIO
#import RPi.GPIO as GPIO

#setup GPIO using Broadcom SOC channel numbering
#GPIO.setmode(GPIO.BCM)

# set to pull-up (normally closed position)
#GPIO.setup(23, GPIO.IN, pull_up_down=GPIO.PUD_UP)

#setup InstaPush variables
# add your Instapush Application ID
appID = "your Instapush Application ID"

# add your Instapush Application Secret
appSecret = "xxxx"
pushEvent = "xxxx"
pushMessage = "Door Opened!"

# use this to capture the response from our push API call
buffer = StringIO()

# use Curl to post to the Instapush API
c = pycurl.Curl()

# set API URL
c.setopt(c.URL, 'https://api.instapush.im/v1/post')

#setup custom headers for authentication variables and content type
c.setopt(c.HTTPHEADER, ['x-instapush-appid: ' + appID,
			'x-instapush-appsecret: ' + appSecret,
			'Content-Type: application/json'])


# create a dict structure for the JSON data to post
json_fields = {}

# setup JSON values
json_fields['event']=pushEvent
json_fields['trackers'] = {}
json_fields['trackers']['message']=pushMessage
#print(json_fields)
postfields = json.dumps(json_fields)

# make sure to send the JSON with post
c.setopt(c.POSTFIELDS, postfields)

# set this so we can capture the resposne in our buffer
c.setopt(c.WRITEFUNCTION, buffer.write)

# uncomment to see the post sent
#c.setopt(c.VERBOSE, True)


# setup an indefinite loop that looks for the door to be opened / closed
while True:

    #GPIO.wait_for_edge(23, GPIO.RISING)
    time.sleep(5)
    print("Door Opened!\n")

    # in the door is opened, send the push request
    c.perform()

    # capture the response from the server
    body= buffer.getvalue()

    # print the response
    print(body)

    # reset the buffer
    buffer.truncate(0)
    buffer.seek(0)

    # print when the door in closed
	#GPIO.wait_for_edge(23, GPIO.FALLING)
    time.sleep(10)
    print("Door Closed!\n")

# cleanup
c.close()
#GPIO.cleanup()
