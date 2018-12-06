package com.example.tronc.gamesdaily.Data;

import android.app.ListActivity;

import com.example.tronc.gamesdaily.Models.News;

import java.util.ArrayList;
import java.util.List;


public class List_News extends ListActivity {

    private List<News> list_news = new ArrayList<News>();

    public List_News(){
        News news = new News(1, "NEWS 1", "20/10/2018 10:00", "noticia numero 1");
        this.list_news.add(news);
        news = new News(2, "NEWS 2", "20/10/2018 10:00", "noticia numero 2");
        this.list_news.add(news);
    }

    public List<News> getList_news(){
        return list_news;
    }

    public void setList_news(ArrayList<News> list_news){
        this.list_news = list_news;
    }

    public News search(int id){
        News trues = null;
        for (int i=0; i<this.list_news.size(); i++){
            if(this.list_news.get(i).getId() == id){
                trues = this.list_news.get(i);
            }
        }
        return trues;
    }


}