package com.endymion.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 注意layout的文件名不能与其它模块中的一样，否则在产品模式下，可能会将其它模块的layout作为布局
        setContentView(R.layout.weather_activity_main);
        Toast.makeText(this, "weather", Toast.LENGTH_SHORT).show();
        TextView textView = findViewById(R.id.txt);
        Log.w("Weather", String.valueOf(textView == null));
    }
}
