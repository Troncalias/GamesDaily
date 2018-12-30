package com.example.tronc.gamesdaily.Interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.tronc.gamesdaily.Models.News;

import java.util.List;

@Dao
public interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addNews(News news);

    @Query("SELECT * FROM News WHERE id=:newsId")
    public News getNewsById(int newsId);

    @Query("SELECT * FROM News")
    public List<News> loadAllNews();
}
