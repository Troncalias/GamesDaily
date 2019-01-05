package com.example.tronc.gamesdaily.Data;

import android.app.ListActivity;
import com.example.tronc.gamesdaily.Models.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Class que permite registar pela ultima vez o formato das News que a aplicação recebe dad API
 */
public class List_News extends ListActivity {
    private List<News> list_news = new ArrayList<News>();

    public List_News() {
    }
    public List<News> getList_news() {
        return list_news;
    }

}