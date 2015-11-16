package com.tetrandroid.networkingsqliteplayground;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ArticlesAdapter extends ArrayAdapter<Article> {

    public ArticlesAdapter(Context context, ArrayList<Article> articles) {
        super(context, 0, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Article article = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.article_list_item, parent, false);
        }
        // Lookup view for data population
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        TextView body = (TextView) convertView.findViewById(R.id.body);
        TextView author = (TextView) convertView.findViewById(R.id.author);

        // Populate the data into the template view using the data object
        String numberedTitle = article.getId()+ ". " +article.getTitle();
        title.setText(numberedTitle);
        date.setText(article.getPublishedDate());
        body.setText(article.getBody());
        String writtenByAuthor = "written by " + article.getAuthor();
        author.setText(writtenByAuthor);

        return convertView;
    }
}
