package com.example.rsingh7.androidtesttheatro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etMovieName;
    Button btSearchMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMovieName = findViewById(R.id.etMovieName);
        btSearchMovie = findViewById(R.id.btSearchMovie);

        btSearchMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movieName = etMovieName.getText().toString();
                if(!movieName.equals("") ) {
                    Intent intent = new Intent(MainActivity.this, MovieList.class);
                    intent.putExtra("movieName", movieName);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this,"Please enter movie name",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
