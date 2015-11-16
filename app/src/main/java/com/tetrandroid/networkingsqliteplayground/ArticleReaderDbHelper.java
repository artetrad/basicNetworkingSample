package com.tetrandroid.networkingsqliteplayground;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tetrandroid.networkingsqliteplayground.ArticleReaderContract.ArticleEntry;

import java.util.ArrayList;
import java.util.List;

public class ArticleReaderDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ArticleReader.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String VARCHAR_TYPE = " VARCHAR(255)";
    private static final String TIMESTAMP_TYPE = " TIMESTAMP";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ArticleEntry.TABLE_NAME + " (" +
                    ArticleEntry._ID + " INTEGER PRIMARY KEY," +
                    ArticleEntry.COLUMN_NAME_ID + INTEGER_TYPE + COMMA_SEP +
                    ArticleEntry.COLUMN_NAME_TITLE + VARCHAR_TYPE + COMMA_SEP +
                    ArticleEntry.COLUMN_NAME_BODY + TEXT_TYPE + COMMA_SEP +
                    ArticleEntry.COLUMN_NAME_AUTHOR + VARCHAR_TYPE + COMMA_SEP +
                    ArticleEntry.COLUMN_NAME_DATE_PUBLISHED + TIMESTAMP_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ArticleEntry.TABLE_NAME;

    public ArticleReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void insertArticles(List<Article> articles) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete the previous values
        db.delete(ArticleEntry.TABLE_NAME, null, null);

        for (Article article : articles) {
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(ArticleEntry.COLUMN_NAME_ID, article.getId());
            values.put(ArticleEntry.COLUMN_NAME_TITLE, article.getTitle());
            values.put(ArticleEntry.COLUMN_NAME_BODY, article.getBody());
            values.put(ArticleEntry.COLUMN_NAME_AUTHOR, article.getAuthor());
            values.put(ArticleEntry.COLUMN_NAME_DATE_PUBLISHED, article.getPublishedDate());

            // Insert the new row, returning the primary key value of the new row
            db.insert(ArticleEntry.TABLE_NAME, null, values);
        }
    }

    public ArrayList<Article> readArticles() {
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                ArticleEntry.COLUMN_NAME_ID,
                ArticleEntry.COLUMN_NAME_TITLE,
                ArticleEntry.COLUMN_NAME_BODY,
                ArticleEntry.COLUMN_NAME_AUTHOR,
                ArticleEntry.COLUMN_NAME_DATE_PUBLISHED};

        // How you want the results sorted in the resulting Cursor
        String sortOrder = ArticleEntry.COLUMN_NAME_ID + " ASC";

        Cursor cursor = db.query(
                ArticleEntry.TABLE_NAME,                  // The table to query
                projection,                               // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        ArrayList<Article> articles = new ArrayList<>();

        while(cursor.moveToNext()){
            Article article = new Article();
            article.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ArticleEntry.COLUMN_NAME_ID)));
            article.setTitle(cursor.getString(cursor.getColumnIndex(ArticleEntry.COLUMN_NAME_TITLE)));
            article.setBody(cursor.getString(cursor.getColumnIndex(ArticleEntry.COLUMN_NAME_BODY)));
            article.setAuthor(cursor.getString(cursor.getColumnIndex(ArticleEntry.COLUMN_NAME_AUTHOR)));
            article.setPublishedDate(cursor.getString(cursor.getColumnIndex(ArticleEntry.COLUMN_NAME_DATE_PUBLISHED)));

            articles.add(article);
        }

        cursor.close();
        return articles;
    }
}


