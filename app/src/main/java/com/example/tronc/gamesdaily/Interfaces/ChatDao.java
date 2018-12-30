package com.example.tronc.gamesdaily.Interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.tronc.gamesdaily.Models.Chat;

import java.util.List;

@Dao
public interface ChatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addChatsDao(Chat chat);

    @Query("SELECT * FROM Chats WHERE id=:chatId")
    public Chat getChatById(int chatId);

    @Query("SELECT * FROM Chats")
    public List<Chat> loadAllChats();
}
