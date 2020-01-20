package com.example.countdown;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    CountDownTimer countD;
    long milliLeft=0;

    public void timerStart(final int time, final TextView textView, final SeekBar seekBar){
        countD = new CountDownTimer(time*1000, 1000) {
            int minute = time/60;
            int second = time%60;

            @Override
            public void onTick(long millisUntilFinished) {

                milliLeft = millisUntilFinished;
                int progress = (int) milliLeft/1000;
                seekBar.setProgress(progress);
                String[] seconds = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};
                String[] minutes = {"00", "01", "02", "03"};
                if(second==0){
                    minute--;
                    second = 59;
                }
                else{
                    second--;
                }
                if(second==0 && minute==0)
                    textView.setText("00 : 00");
                else
                    textView.setText(minutes[minute] + " : " + seconds[second]);
            }

            @Override
            public void onFinish() {
                textView.setText("00 : 00");
                MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.finish);
                mediaPlayer.start();
            }
        };
        countD.start();
    }

    public void timerPause(){
        if(countD!=null)
            countD.cancel();
    }

    public void timerResume(View view){
        TextView textView = findViewById(R.id.textView);
        SeekBar seekBar = findViewById(R.id.seekBar);
        Button button = findViewById(R.id.button);
        if(button.getText().equals("Resume")) {
            timerStart((int) milliLeft / 1000, textView, seekBar);
            button.setText("Pause");
        }
        else{
            timerPause();
            button.setText("Resume");
        }
    }

    public void timerReset(View view){
        milliLeft = 0;
        timerPause();
        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setProgress(0);
        Button button = findViewById(R.id.button);
        button.setText("Resume");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(180);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int minute, second, time;
            TextView textView = (TextView) findViewById(R.id.textView);
            Button button = findViewById(R.id.button);
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String[] seconds = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};
                String[] minutes = {"00", "01", "02", "03"};
                time = progress;
                minute = time/60;
                second = time%60;
                textView.setText(minutes[minute] + " : " + seconds[second]);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                timerPause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                milliLeft = seekBar.getProgress()*1000;
                button.setText("Resume");
            }
        });
    }
}


