package tech.cyang.smarthome;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ContorlLed extends AppCompatActivity {

    private static final String TAG = "ContorlLed";

    private static final int DHT11_temp_sensor = 1;
    private static final int DHT11_hum_sensor = 2;
    private static final int CPU_temp_sensor = 3;
    private static final int Led_status = 4;
    private static final int GPU_temp_sensor = 5;

    private static final String DHT11_temp_url = "";
    private static final String DHT11_hum_url = "";
    private static final String CPU_temp_url = "";
    private static final String GPU_temp_url = "";

    TextView text_led_status;
    TextView responseText;
    Switch modify_led_status;
    TextView text_temp;
    TextView text_hum;
    TextView text_cpu_temp;
    TextView text_gpu_temp;

    int DHT11_temp_value;
    int DHT11_hum_value;
    int CPU_temp_value;
    int GPU_temp_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contorl_led);
        Button get_led_status = (Button) findViewById(R.id.get_led_status);
        Button get_sensor_status = (Button) findViewById(R.id.get_sensor_status);
        modify_led_status = (Switch) findViewById(R.id.switch_led);
        text_led_status = (TextView) findViewById(R.id.led_status);
        responseText = (TextView) findViewById(R.id.response_text);
        text_temp = (TextView) findViewById(R.id.text_temp_data_2);
        text_hum = (TextView) findViewById(R.id.text_hum_data_2);
        text_cpu_temp = (TextView) findViewById(R.id.text_cpu_temp);
        text_gpu_temp = (TextView) findViewById(R.id.text_gpu_temp);

        get_led_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get url data
                GETRequestWithHttpURLConnection(Led_status);
                Toast.makeText(ContorlLed.this, "test", Toast.LENGTH_SHORT).show();
            }
        });

        get_sensor_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get data
                GETRequestWithHttpURLConnection_temp(DHT11_temp_sensor,DHT11_temp_url);
                GETRequestWithHttpURLConnection_temp(DHT11_hum_sensor,DHT11_hum_url);
                GETRequestWithHttpURLConnection_temp(CPU_temp_sensor,CPU_temp_url);
                GETRequestWithHttpURLConnection_temp(GPU_temp_sensor,GPU_temp_url);
            }
        });

        modify_led_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    changeLedStatus(true);
                    Toast.makeText(ContorlLed.this, "on", Toast.LENGTH_SHORT).show();
                } else {
                    changeLedStatus(false);
                    Toast.makeText(ContorlLed.this, "off", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //get data
    private void GETRequestWithHttpURLConnection(final int device_id){

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try{
                    URL url = new URL("");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        response.append(line);
                    }
                    showResponse(device_id,response.toString());
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (reader != null){
                        try {
                            reader.close();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    if (connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    //show info about on or off
    private void showResponse(final int device_id, final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText(response);
                //text_led_status.setText("on");
                int i;
                i = parseJSONWithJSONObject(device_id,response);
                Log.d(TAG, "run: "+i);
                if (i == 1){
                    text_led_status.setText("开");
                    modify_led_status.setChecked(true);
                }else if (i == 0){
                    text_led_status.setText("关");
                    modify_led_status.setChecked(false);
                }else {
                    text_led_status.setText("错误");
                }
            }
        });
    }

    //get info about json response data
    private int parseJSONWithJSONObject(int device_id,String jsonData){
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            Log.d(TAG, "parseJSONWithJSONObject: "+"this");
            String timestamp = jsonObject.getString("timestamp");
            String value = jsonObject.getString("value");
            int i = jsonObject.getInt("value");
            Log.d(TAG, "parseJSONWithJSONObject: "+"i= "+i);
            Log.d(TAG, "parseJSONWithJSONObject: "+timestamp);
            Log.d(TAG, "parseJSONWithJSONObject: "+value);

            if (device_id == Led_status){
                if (i == 1){
                    return 1;
                }else {
                    return 0;
                }
            }else {
                Log.d(TAG, "parseJSONWithJSONObject: "+"device_id"+device_id+"value"+i);
                return i;
            }
        }catch (Exception e){
            Log.d(TAG, "parseJSONWithJSONObject: "+"error");
            e.printStackTrace();
            return 3;
        }
    }

    // make info about status
    private void changeLedStatus(boolean b){
        String jsonDataOn = "{\n" +
                "  \"timestamp\":\"2016-12-22T09:34:14\",\n" +
                "  \"value\":1\n" +
                "}";
        String jsonDataOff = "{\n" +
                "  \"timestamp\":\"2016-12-22T09:34:14\",\n" +
                "  \"value\":0\n" +
                "}";
        if (b){
            GETRequestWithHttpURLConnection(jsonDataOn);
        }else {
            GETRequestWithHttpURLConnection(jsonDataOff);
        }
    }

    // post info
    private void GETRequestWithHttpURLConnection(final String jsonData){

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedWriter writer = null;
                try{
                    URL url = new URL("");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("U-ApiKey","");
                    connection.setRequestProperty("Content-Length","52");
                    connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                    connection.setDoOutput(true);
                    connection.getOutputStream().write(jsonData.getBytes());
                    String message = connection.getResponseMessage();
                    Log.d(TAG, "run: "+"response_meaasge"+message);
                }catch (Exception e){
                    e.printStackTrace();
                    Log.d(TAG, "run: "+"error");
                }
            }
        }).start();
    }

    //get data
    private void GETRequestWithHttpURLConnection_temp(final int sensor_id,final String sensor_url){

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try{
                    URL url = new URL(sensor_url);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        response.append(line);
                    }
                    showResponse_info(sensor_id,response.toString());
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (reader != null){
                        try {
                            reader.close();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    if (connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    //show info about on or off
    private void showResponse_info(final int sensor_id, final String response){

        //int value1;
        //int value2;
        //int value3;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (sensor_id){
                    case DHT11_temp_sensor:
                        DHT11_temp_value = parseJSONWithJSONObject(DHT11_temp_sensor,response);
                        text_temp.setText(String.valueOf(DHT11_temp_value));
                        //text_temp.setText(parseJSONWithJSONObject(DHT11_temp_sensor,response));
                        //text_temp.setText(response);
                        break;
                    case DHT11_hum_sensor:
                        DHT11_hum_value = parseJSONWithJSONObject(DHT11_hum_sensor,response);
                        text_hum.setText(String.valueOf(DHT11_hum_value));
                        //text_hum.setText(parseJSONWithJSONObject(DHT11_hum_sensor,response));
                        //text_hum.setText(response);
                        break;
                    case CPU_temp_sensor:
                        CPU_temp_value = parseJSONWithJSONObject(CPU_temp_sensor,response);
                        text_cpu_temp.setText(String.valueOf(CPU_temp_value));
                        //text_cpu_temp.setText(parseJSONWithJSONObject(CPU_temp_sensor,response));
                        //text_cpu_temp.setText(response);
                        break;
                    case GPU_temp_sensor:
                        GPU_temp_value = parseJSONWithJSONObject(GPU_temp_sensor,response);
                        text_gpu_temp.setText(String.valueOf(GPU_temp_value));
                        break;
                }
            }
        });
    }
}

