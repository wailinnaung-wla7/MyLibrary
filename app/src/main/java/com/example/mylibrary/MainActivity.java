package com.example.mylibrary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnAllBooks,btnAdyRead,btnWishList,btnCurrentlyReading,btnFavorites,btnAbout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        btnAllBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AllBooksActivity.class);
                startActivity(intent);
            }
        });

        btnAdyRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AlreadyReadBookActivity.class);
                startActivity(intent);
            }
        });

        btnWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,WantToReadBookActivity.class);
                startActivity(intent);
            }
        });

        btnCurrentlyReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CurrentlyReadBookActivity.class);
                startActivity(intent);
            }
        });

        btnFavorites.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,FavoriteBookActivity.class);
            startActivity(intent);
        });

        btnAbout.setOnClickListener(view -> {
            AlertDialog.Builder builder =new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.app_name));
            builder.setMessage("Designed & Developed with Sweat by WLA at example.org\n"+
                    "Check my github for more awesome application");
            builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.setPositiveButton("Visit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //TODO: Show the website
                    Intent intent = new Intent(MainActivity.this,WebsiteActivity.class);
                    intent.putExtra("url","https://github.com/wailinnaung-wla7");
                    startActivity(intent);
                }
            });
            builder.create().show();
        });

        Utils.getInstance(this);
    }

    private void initViews() {
        btnAllBooks = findViewById(R.id.btnAllBooks);
        btnCurrentlyReading = findViewById(R.id.btnCurrentRead);
        btnAdyRead = findViewById(R.id.btnAdyRead);
        btnWishList = findViewById(R.id.btnWishList);
        btnFavorites = findViewById(R.id.btnFavorites);
        btnAbout = findViewById(R.id.btnAbout);
    }
}