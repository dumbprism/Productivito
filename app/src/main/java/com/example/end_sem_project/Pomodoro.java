package com.example.end_sem_project;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Pomodoro extends AppCompatActivity {

    private ProgressBar circularProgressBar;
    private TextView timerTextView;
    private Button startButton, pauseButton, restartButton;
    private static final long START_TIME_IN_MILLIS = 1500000; // 25 minutes in milliseconds

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = START_TIME_IN_MILLIS;
    private boolean timerRunning;
    private MediaPlayer alarmSound;  // MediaPlayer for the alarm sound

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);

        circularProgressBar = findViewById(R.id.circularProgressBar);
        timerTextView = findViewById(R.id.timerTextView);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        restartButton = findViewById(R.id.restartButton);

        circularProgressBar.setMax((int) START_TIME_IN_MILLIS / 1000);
        updateTimerText();

        startButton.setOnClickListener(view -> startTimer());
        pauseButton.setOnClickListener(view -> pauseTimer());
        restartButton.setOnClickListener(view -> restartTimer());

        // Initialize MediaPlayer with alarm sound
        alarmSound = MediaPlayer.create(this, R.raw.pomodoro_alarm);
    }

    private void startTimer() {
        if (!timerRunning) {
            countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeftInMillis = millisUntilFinished;
                    int progress = (int) (timeLeftInMillis / 1000);
                    circularProgressBar.setProgress(progress);
                    updateTimerText();
                }

                @Override
                public void onFinish() {
                    timerRunning = false;
                    timeLeftInMillis = 0;
                    updateTimerText();
                    circularProgressBar.setProgress(0);

                    // Play alarm sound when timer finishes
                    playAlarmSound();
                }
            }.start();
            timerRunning = true;
        }
    }

    private void playAlarmSound() {
        if (alarmSound != null) {
            alarmSound.start();
        }
    }

    private void pauseTimer() {
        if (timerRunning) {
            countDownTimer.cancel();
            timerRunning = false;
        }
    }

    private void restartTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        timeLeftInMillis = START_TIME_IN_MILLIS;
        timerRunning = false;
        circularProgressBar.setProgress((int) START_TIME_IN_MILLIS / 1000);
        updateTimerText();

        // Stop the alarm sound if it’s playing
        if (alarmSound != null && alarmSound.isPlaying()) {
            alarmSound.stop();
            alarmSound.prepareAsync();  // Reset the MediaPlayer to be ready for the next use
        }
    }

    private void updateTimerText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        timerTextView.setText(timeLeftFormatted);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Release the MediaPlayer resource to avoid memory leaks
        if (alarmSound != null) {
            alarmSound.release();
            alarmSound = null;
        }
    }
}
