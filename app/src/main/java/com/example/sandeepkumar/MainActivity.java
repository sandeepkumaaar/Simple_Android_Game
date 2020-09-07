package com.example.sandeepkumar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView tvScore;
    private View viewRed, viewRedGrey, viewBlue, viewBlueGrey, viewYellow, viewYellowGrey, viewGreen, viewGreenGrey;
    private int playerScore = 0;
    private Timer currentTimer;
    private boolean isNotClick = false;
    private boolean isOneTimeClicked = false;
    static int mLastRandomNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvScore = findViewById(R.id.tv_Score);
        viewRed = findViewById(R.id.view_red);
        viewRedGrey = findViewById(R.id.view_redGrey);
        viewBlue = findViewById(R.id.view_blue);
        viewBlueGrey = findViewById(R.id.view_blueGrey);
        viewYellow = findViewById(R.id.view_yellow);
        viewYellowGrey = findViewById(R.id.view_yellowGrey);
        viewGreen = findViewById(R.id.view_green);
        viewGreenGrey = findViewById(R.id.view_greenGrey);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Click on Start button to start the game");
        builder.setPositiveButton("Start", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startTimer();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void startTimer() {

        currentTimer = null;
        currentTimer = new Timer();

        currentTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showRandomView(getRandomBetweenRange(1,4));
                    }
                });
            }
        }, 0, 1000);
    }

    public static int getRandomBetweenRange(int min, int max) {
        int currentRandomNumber = (int) ((Math.random() * ((max - min) + 1)) + min);
        if (mLastRandomNumber == currentRandomNumber)
            return getRandomBetweenRange(1, 4);
        else {
            mLastRandomNumber = currentRandomNumber;
            return currentRandomNumber;
        }
    }

    private void showRandomView(int random) {
        if (isNotClick) {
            gameOver();
            return;
        }
        isNotClick = true;
        isOneTimeClicked = false;
        showVisibility();
        if (random == 1) {
            viewRedGrey.setVisibility(View.VISIBLE);
            clickOnGrey(viewRedGrey);
        } else if (random == 2) {
            viewBlueGrey.setVisibility(View.VISIBLE);
            clickOnGrey(viewBlueGrey);
        } else if (random == 3) {
            viewYellowGrey.setVisibility(View.VISIBLE);
            clickOnGrey(viewYellowGrey);
        } else if (random == 4) {
            viewGreenGrey.setVisibility(View.VISIBLE);
            clickOnGrey(viewGreenGrey);
        }
        clickOnOthers(viewRed);
        clickOnOthers(viewBlue);
        clickOnOthers(viewYellow);
        clickOnOthers(viewGreen);
    }

    private void clickOnGrey(final View currentView) {

        currentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOneTimeClicked) {
                    playerScore++;
                    tvScore.setText(String.valueOf(playerScore));
                    isNotClick = false;
                    isOneTimeClicked = true;
                }
            }
        });
    }

    private void clickOnOthers(final View currentView) {
        currentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameOver();
            }
        });
    }

    private void showVisibility() {
        viewRed.setVisibility(View.VISIBLE);
        viewBlue.setVisibility(View.VISIBLE);
        viewYellow.setVisibility(View.VISIBLE);
        viewGreen.setVisibility(View.VISIBLE);
        viewGreen.setVisibility(View.VISIBLE);
        viewRedGrey.setVisibility(View.GONE);
        viewBlueGrey.setVisibility(View.GONE);
        viewYellowGrey.setVisibility(View.GONE);
        viewGreenGrey.setVisibility(View.GONE);
    }

    private void gameOver() {

        try {
            currentTimer.cancel();
        } catch (Exception ex) {
            Log.d("TAG", "gameOver: " + ex.getMessage());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Over. Your score is : " + playerScore);
        builder.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                playerScore = 0;
                tvScore.setText("0");
                isNotClick = false;
                startTimer();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
}