package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Button;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;





public class MainActivity extends AppCompatActivity {

    private TextView timerText;
    private Button startButton, stopButton, holdButton, resetButton;
    private Handler handler;
    private boolean isStopped = false, isHeld = false;
    private long startTime, elapsedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerText = findViewById(R.id.timer_text);
        startButton = findViewById(R.id.start_button);
        stopButton = findViewById(R.id.stop_button);
        holdButton = findViewById(R.id.hold_button);
        resetButton = findViewById(R.id.reset_button);

        handler = new Handler();

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStopped) {
                    startTime = System.currentTimeMillis() - elapsedTime;
                } else {
                    startTime = System.currentTimeMillis();
                }
                handler.postDelayed(updateTimer, 0);
                isStopped = false;
                isHeld = false;
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                holdButton.setEnabled(true);
                resetButton.setEnabled(true);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(updateTimer);
                isStopped = true;
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                holdButton.setEnabled(false);
                resetButton.setEnabled(true);
            }
        });

        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHeld) {
                    handler.postDelayed(updateTimer, 0);
                    isHeld = false;
                    holdButton.setText("Hold");
                    startButton.setEnabled(false);
                    stopButton.setEnabled(true);
                    resetButton.setEnabled(true);
                } else {
                    handler.removeCallbacks(updateTimer);
                    isHeld = true;
                    holdButton.setText("Resume");
                    startButton.setEnabled(true);
                    stopButton.setEnabled(false);
                    resetButton.setEnabled(false);
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(updateTimer);
                timerText.setText("00:00:00.000");
                isStopped = true;
                isHeld = false;
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                holdButton.setEnabled(false);
                resetButton.setEnabled(false);
            }
        });

    }

    private Runnable updateTimer = new Runnable() {
        @Override
        public void run() {
            elapsedTime = System.currentTimeMillis() - startTime;
            int minutes = (int) (elapsedTime / 60000);
            int seconds = (int) ((elapsedTime / 1000) % 60);
            int milliseconds = (int) (elapsedTime % 1000);
            String time = String.format("%02d:%02d:%03d", minutes, seconds, milliseconds);
            timerText.setText(time);
            handler.postDelayed(this, 0);
        }
    };

}
