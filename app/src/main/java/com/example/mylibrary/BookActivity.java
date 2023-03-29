package com.example.mylibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {
    public static final String BOOK_ID_KEY = "bookId";
    private TextView txtBookName, txtAuthor, txtPages, txtDesc;
    private Button btnAddToCurrentlyRead, btnAddToWantToRead, btnAddToAlreadyRead, btnAddToFavorite;
    private ImageView bookImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        initViews();

        Intent intent = getIntent();
        if (intent != null) {
            int bookId = intent.getIntExtra(BOOK_ID_KEY, -1);
            if (bookId != -1) {
                Book incomingBook = Utils.getInstance(this).getBookById(bookId);
                if (null != incomingBook) {
                    setData(incomingBook);

                    handleAlreadyRead(incomingBook);
                    handleWantToRead(incomingBook);
                    handleCurrentlyRead(incomingBook);
                    handleFavorite(incomingBook);
                }
            }
        }
    }

    private void handleCurrentlyRead(Book book) {
        ArrayList<Book> currentlyReadingBooks = Utils.getInstance(this).getCurrentlyReadingBooks();

        Boolean existsInCurrentlyReadingBooks = false;
        for (Book b: currentlyReadingBooks)
        {
            if (b.getId() == book.getId()) {
                existsInCurrentlyReadingBooks = true;
            }
        }
        if (existsInCurrentlyReadingBooks)
        {
            btnAddToCurrentlyRead.setEnabled(false);
        }
        else {
            btnAddToCurrentlyRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utils.getInstance(BookActivity.this).addToCurrentlyRead(book)){
                        Toast.makeText(BookActivity.this, "Currently Reading Book Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this,CurrentlyReadBookActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(BookActivity.this, "Something wrong.Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void handleFavorite(Book book) {
        ArrayList<Book> favoriteBooks = Utils.getInstance(this).getFavoriteBooks();

        Boolean existsInFavoriteBooks = false;
        for (Book b: favoriteBooks)
        {
            if (b.getId() == book.getId()) {
                existsInFavoriteBooks = true;
            }
        }
        if (existsInFavoriteBooks)
        {
            btnAddToFavorite.setEnabled(false);
        }
        else {
            btnAddToFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utils.getInstance(BookActivity.this).addToFavorite(book)){
                        Toast.makeText(BookActivity.this, "Favorite Book Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this,FavoriteBookActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(BookActivity.this, "Something wrong.Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void handleWantToRead(Book book) {
        ArrayList<Book> wantToReadBooks = Utils.getInstance(this).getWantToReadBooks();

        Boolean existsInWantToReadBooks = false;
        for (Book b: wantToReadBooks)
        {
            if (b.getId() == book.getId()) {
                existsInWantToReadBooks = true;
            }
        }
        if (existsInWantToReadBooks)
        {
            btnAddToWantToRead.setEnabled(false);
        }
        else {
            btnAddToWantToRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utils.getInstance(BookActivity.this).addToWantToRead(book)){
                        Toast.makeText(BookActivity.this, "Want To Read Book Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this,WantToReadBookActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(BookActivity.this, "Something wrong.Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * Enable and Disable button
     * Add the book to already Read Book ArrayList
     * @param book
     */
    private void handleAlreadyRead(Book book) {
        ArrayList<Book> alreadyReadBooks = Utils.getInstance(this).getAlreadyReadBooks();

        Boolean existsInAlreadyReadBooks = false;
        for (Book b: alreadyReadBooks)
        {
            if (b.getId() == book.getId()) {
                existsInAlreadyReadBooks = true;
            }
        }
        if (existsInAlreadyReadBooks)
        {
            btnAddToAlreadyRead.setEnabled(false);
        }
        else {
            btnAddToAlreadyRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utils.getInstance(BookActivity.this).addToAlreadyRead(book)){
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this,AlreadyReadBookActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(BookActivity.this, "Something wrong.Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void setData(Book book) {
        txtBookName.setText(book.getName());
        txtAuthor.setText(book.getAuthor());
        txtPages.setText(String.valueOf(book.getPages()));
        txtDesc.setText(book.getLongDesc());

        Glide.with(this)
                .asBitmap()
                .load(book.getImageUrl())
                .into(bookImage);
    }

    private void initViews() {
        txtBookName = findViewById(R.id.txtBookName);
        txtAuthor = findViewById(R.id.txtAuthorName);
        txtPages = findViewById(R.id.txtPages);
        txtDesc = findViewById(R.id.txtLongDesc);

        btnAddToCurrentlyRead = findViewById(R.id.btnCurrentRead);
        btnAddToWantToRead = findViewById(R.id.btnWantToRead);
        btnAddToAlreadyRead = findViewById(R.id.btnAlreadyRead);
        btnAddToFavorite = findViewById(R.id.btnFavorites);

        bookImage = findViewById(R.id.bookImg);
    }
}