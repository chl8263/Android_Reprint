package com.example.choi.reprint;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.ajalt.reprint.core.AuthenticationFailureReason;
import com.github.ajalt.reprint.core.AuthenticationListener;
import com.github.ajalt.reprint.core.Reprint;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView)findViewById(R.id.finger);
        imageView.setColorFilter(Color.BLACK);
        Reprint.initialize(getApplicationContext());
        Reprint.authenticate(new AuthenticationListener() {
            @Override
            public void onSuccess(int moduleTag) {
                Toast.makeText(getApplicationContext(),"성공",Toast.LENGTH_SHORT).show();
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            imageView.setColorFilter(Color.GREEN);
                            sleep(2000);
                            imageView.setColorFilter(Color.BLACK);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }

            @Override
            public void onFailure(AuthenticationFailureReason failureReason, boolean fatal, CharSequence errorMessage, int moduleTag, int errorCode) {
                Toast.makeText(getApplicationContext(),"실패",Toast.LENGTH_SHORT).show();
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            imageView.setColorFilter(Color.RED);
                            sleep(2000);
                            imageView.setColorFilter(Color.BLACK);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
