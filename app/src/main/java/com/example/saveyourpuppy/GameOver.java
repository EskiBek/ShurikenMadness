package com.example.saveyourpuppy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class GameOver extends AppCompatActivity {
    TextView pointsScored;
    TextView highestScore;
    SharedPreferences sharedPreferences;
    ImageView newPersonalBest;
    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        pointsScored = findViewById(R.id.scoredPoints);
        highestScore = findViewById(R.id.highestScore);
        newPersonalBest = findViewById(R.id.newHighestScore);

        int points = Objects.requireNonNull(getIntent().getExtras()).getInt("points");
        pointsScored.setText(""+points);

        sharedPreferences = getSharedPreferences("my_pref",0);
        int highest = sharedPreferences.getInt("highest",0);

        if (points > highest){
            newPersonalBest.setVisibility(View.VISIBLE);
            highest = points;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("highest",highest);
            editor.apply();

        }
        highestScore.setText("" + highest);
    }
    public void restartGame(View view){
        Intent intent = new Intent(GameOver.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void exitTheGame(View view){
        finish();
    }
}
