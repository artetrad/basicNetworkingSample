package com.tetrandroid.networkingsqliteplayground;

import android.provider.BaseColumns;

public final class ArticleReaderContract {

    // To prevent accidental class instantiation
    public ArticleReaderContract() {
    }

    // Articles SQLite table
    public static abstract class ArticleEntry implements BaseColumns{
        public static final String TABLE_NAME = "articles";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_BODY = "body";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_DATE_PUBLISHED = "date_published";
    }
}