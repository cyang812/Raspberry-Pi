package tech.cyang.smarthome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button_get_data = (Button) findViewById(R.id.get_data);
        Button button_contorl_led = (Button) findViewById(R.id.button_contorl_led);
        final WebView camera_pic = (WebView) findViewById(R.id.camera_pic);
        //final TextView show_temp = (TextView) findViewById(R.id.text_temp_data);
        //final TextView show_hum = (TextView) findViewById(R.id.text_hum_data);

        
        camera_pic.loadUrl("");

        button_get_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get pic data
                //show the pic
                camera_pic.loadUrl("");
                //show_hum.setText("12");
                //show_temp.setText("34");
                Toast.makeText(MainActivity.this,"success",Toast.LENGTH_SHORT).show();
            }
        });

        button_contorl_led.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ContorlLed.class);
                startActivity(intent);
            }
        });
    }
}
