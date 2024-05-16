package com.example.saveyourpuppy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Bitmap> characters;
    private int currentCharacterIndex = 0;
    private ImageView characterImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        /* blokada wygaszania ekranu */
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        characterImageView = findViewById(R.id.character);

        prepareCharacters();

        /* przycisk do zmiany postaci */

        Button changeCharacterButton = findViewById(R.id.change_character_button);
        changeCharacterButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        characterImageView.setImageBitmap(characters.get(currentCharacterIndex));
                        CharacterManager.currentCharacter = characters.get(currentCharacterIndex);

                        /* zwiększanie indeksu i zabezpieczenie (jeśli indeks jest większy od rozmiaru tablicy - wraca na początek) */
                        currentCharacterIndex = (currentCharacterIndex + 1) % characters.size();
                    }
                }
        );

    }

    /* uruchomienie gry przez zmianę widoku */

    public void startGame(View view) {
        GameView gameView = new GameView(this);
        setContentView(gameView);
    }

    /* przygotowanie postaci */

    public void prepareCharacters(){
        characters = new ArrayList<>();
        characters.add(BitmapFactory.decodeResource(getResources(), R.drawable.ninja1));
        characters.add(BitmapFactory.decodeResource(getResources(), R.drawable.ninja2));
        characters.add(BitmapFactory.decodeResource(getResources(), R.drawable.ninja3));
        characters.add(BitmapFactory.decodeResource(getResources(), R.drawable.ninja4));
        characters.add(BitmapFactory.decodeResource(getResources(), R.drawable.puppy));
        characters.add(BitmapFactory.decodeResource(getResources(), R.drawable.ninja0));
    }
}