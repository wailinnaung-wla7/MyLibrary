package com.example.mylibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {
    private static final String ALL_BOOKS_KEY = "all_books";
    private static final String ALREADY_READ_BOOKS = "already_read_books";
    private static final String WANT_TO_READ_BOOKS = "want_to_read_books";
    private static final String CURRENTLY_READING_BOOKS = "currently_reading_books";
    private static final String FAVORITE_BOOKS = "favorite_books";
    private static Utils instance;
    private SharedPreferences sharedPreferences;
    private Utils(Context context) {

        sharedPreferences = context.getSharedPreferences("alternate_db",Context.MODE_PRIVATE);

        if(getAllBooks() == null )
        {
            initData();
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        if(getAlreadyReadBooks() ==  null)
        {
            editor.putString(ALREADY_READ_BOOKS,gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }
        if(getWantToReadBooks() ==  null)
        {
            editor.putString(WANT_TO_READ_BOOKS,gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }
        if(getCurrentlyReadingBooks() ==  null)
        {
            editor.putString(CURRENTLY_READING_BOOKS,gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }
        if(getFavoriteBooks() ==  null)
        {
            editor.putString(FAVORITE_BOOKS,gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }
    }

    private void initData() {
        ArrayList<Book> books = new ArrayList<>();

        books.add(new Book(1,"1Q84","Haruki Murakami",1350,"https://cdn.iconscout.com/icon/free/png-512/avatar-371-456323.png"
                ,"a work of  maddening brilliance","Long Description"));
        books.add(new Book(2,"The Myth of Sisphyus","Albert Camus",250,"https://cdn.iconscout.com/icon/free/png-512/avatar-370-456322.png"
                ,"So, technically items may be re-arranged (like sorted, or moved around) and binding will not be necessary because items are not invalidated yet. This means onBindViewHolder() may NOT be called if items show the same data, but just their position/index in a list changes.",
                "Long Description"));

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(ALL_BOOKS_KEY,gson.toJson(books));
        editor.commit();
    }

    public static Utils getInstance(Context context) {
        if (instance == null)
        {
            instance = new Utils(context);
        }
        return instance;
    }

    public ArrayList<Book> getAllBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALL_BOOKS_KEY,null),type);
        return books;
    }

    public ArrayList<Book> getAlreadyReadBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALREADY_READ_BOOKS,null),type);
        Log.d("getData", "getAlreadyReadBooks: "+sharedPreferences.getString(ALREADY_READ_BOOKS,null));
        return books;
    }

    public ArrayList<Book> getWantToReadBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(WANT_TO_READ_BOOKS,null),type);
        return books;
    }

    public ArrayList<Book> getCurrentlyReadingBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(CURRENTLY_READING_BOOKS,null),type);
        return books;
    }

    public ArrayList<Book> getFavoriteBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(FAVORITE_BOOKS,null),type);
        return books;
    }

    public Book getBookById(int id)
    {
        ArrayList<Book> books = getAllBooks();
        if (books!=null){
            for (Book b: books) {
                if (b.getId() == id)
                {
                    return b;
                }
            }
        }
        return null;
    }
    public Boolean addToAlreadyRead(Book book)
    {
        ArrayList<Book> books = getAlreadyReadBooks();
        if (books != null)
        {
            if (books.add(book))
            {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(ALREADY_READ_BOOKS);
                editor.putString(ALREADY_READ_BOOKS,gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }
    public Boolean addToWantToRead(Book book)
    {
        ArrayList<Book> books = getWantToReadBooks();
        if (books != null)
        {
            if (books.add(book))
            {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(WANT_TO_READ_BOOKS);
                editor.putString(WANT_TO_READ_BOOKS,gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }
    public Boolean addToCurrentlyRead(Book book)
    {
        ArrayList<Book> books = getCurrentlyReadingBooks();
        if (books != null)
        {
            if (books.add(book))
            {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(CURRENTLY_READING_BOOKS);
                editor.putString(CURRENTLY_READING_BOOKS,gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }
    public Boolean addToFavorite(Book book)
    {
        ArrayList<Book> books = getFavoriteBooks();
        if (books != null)
        {
            if (books.add(book))
            {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(FAVORITE_BOOKS);
                editor.putString(FAVORITE_BOOKS,gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public Boolean removeFromAlreadyRead(Book book)
    {
        ArrayList<Book> books = getAlreadyReadBooks();
        Boolean isFoundInDb = false;
        if (books != null)
        {
            for (Book b : books)
            {
                if (b.getId() == book.getId())
                {
                    if (books.remove(b))
                    {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(ALREADY_READ_BOOKS);
                        editor.putString(ALREADY_READ_BOOKS,gson.toJson(books));
                        editor.commit();
                        Log.d("This", "This happen second: ");
                        return true;
                    }
                }
            }
        }

        return false;
    }
    public Boolean removeFromCurrentlyReading(Book book)
    {
        ArrayList<Book> books = getCurrentlyReadingBooks();
        if (books != null)
        {
            for (Book b : books)
            {
                if (b.getId() == book.getId())
                {
                    if (books.remove(b))
                    {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(CURRENTLY_READING_BOOKS);
                        editor.putString(CURRENTLY_READING_BOOKS,gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public Boolean removeFromWantToRead(Book book)
    {
        ArrayList<Book> books = getWantToReadBooks();
        if (books != null)
        {
            for (Book b : books)
            {
                if (b.getId() == book.getId())
                {
                    if (books.remove(b))
                    {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(WANT_TO_READ_BOOKS);
                        editor.putString(WANT_TO_READ_BOOKS,gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public Boolean removeFromFavorite(Book book)
    {
        ArrayList<Book> books = getFavoriteBooks();
        if (books != null)
        {
            for (Book b : books)
            {
                if (b.getId() == book.getId())
                {
                    if (books.remove(b))
                    {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(FAVORITE_BOOKS);
                        editor.putString(FAVORITE_BOOKS,gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
