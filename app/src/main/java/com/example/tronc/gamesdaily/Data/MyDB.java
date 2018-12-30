package com.example.tronc.gamesdaily.Data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.Update;

import com.example.tronc.gamesdaily.Models.Avaliacao;
import com.example.tronc.gamesdaily.Models.Favoritos;
import com.example.tronc.gamesdaily.Models.Games;
import com.example.tronc.gamesdaily.Models.Stores;
import com.example.tronc.gamesdaily.Models.User;

import java.util.List;

@Database(entities =  {Games.class, User.class, Stores.class, Favoritos.class, Avaliacao.class}, version = 1)
public abstract class MyDB extends RoomDatabase {
    public abstract geralInterface geral();

    @Dao
    public interface geralInterface{

        /**
         * Funções que controlam o acesso a base de dados
         * para a tabela User
         */
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        public void addUser(User user);

        @Query("SELECT * FROM User")
        public List<User> loadAllUsers();

        @Query("SELECT * FROM User WHERE username=:user")
        public List<User> getUserByName(String user);

        @Query("DELETE FROM User WHERE username=:userName")
        public void deletUser(String userName);

        @Query("SELECT COUNT(username) FROM User")
        public int getSizeUsers();

        /**
         *  Funções que controlam o acesso a base de dados
         *  para a tabela Games
         */
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        public void addGame(Games games);

        @Query("SELECT * FROM Games WHERE id=:gameId")
        public Games getGamesById(int gameId);

        @Query("SELECT * FROM Games")
        public List<Games> loadAllGames();

        @Query("SELECT COUNT(id) FROM Games")
        public int getSizeGames();

        @Update
        public void deletGame(Games game);

        /**
         *
         *
         **/
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        public void addStore(Stores store);

        @Query("SELECT COUNT(id) FROM Stores")
        public int getSizeStores();

        @Query("SELECT * FROM Stores WHERE id=:storeId")
        public Stores getStoresById(int storeId);

        @Query("SELECT * FROM Stores")
        public List<Stores> loadAllStores();

        @Query("DELETE FROM Stores WHERE id = :idstore")
        void deleteStore(int idstore);

        /**
         *  Funções que controlam o acesso a base de dados
         *  para a tabela Favoritos
         */
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        public void addFavoritos(Favoritos favorito);

        @Query("SELECT * FROM Favoritos WHERE username=:name")
        public Favoritos loadFavesByUser(String name);

        /**
         *
         *
         **/
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        public void addAvaliacao(Avaliacao avaliacao);

        @Query("SELECT * FROM Avaliacao WHERE game_id=:gameid")
        public List<Avaliacao> getGameRate(int gameid);


    }
}
