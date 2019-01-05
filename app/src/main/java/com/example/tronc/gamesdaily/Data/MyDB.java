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
import com.example.tronc.gamesdaily.Models.Chat;
import com.example.tronc.gamesdaily.Models.Favoritos;
import com.example.tronc.gamesdaily.Models.Games;
import com.example.tronc.gamesdaily.Models.Mensage;
import com.example.tronc.gamesdaily.Models.Stores;
import com.example.tronc.gamesdaily.Models.StoresGames;
import com.example.tronc.gamesdaily.Models.User;

import java.util.List;

@Database(entities =  {Games.class, User.class, Stores.class, Favoritos.class, Avaliacao.class, Chat.class, Mensage.class, StoresGames.class}, version = 1)
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

        @Update
        void updateUser(User user);

        @Delete
        void deleteUser(User user);

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

        @Query("SELECT * FROM Games WHERE Acepted=:value")
        public List<Games> loadAllGamesAcepted(boolean value);

        @Query("SELECT * FROM Games WHERE Acepted=:value ORDER BY Rating DESC")
        public List<Games> loadAllGamesOrderScore(boolean value);

        @Query("SELECT * FROM Games WHERE Acepted=:value ORDER BY Name")
        public List<Games> loadAllGamesOrderName(boolean value);

        @Query("SELECT COUNT(id) FROM Games")
        public int getSizeGames();

        @Delete
        void deleteGame(Games game);

        @Update
        void updateGame(Games game);

        /**
         *  Funções que controlam o acesso a base de dados
         *  para a tabela Stores
         *
         **/
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        public void addStore(Stores store);

        @Query("SELECT COUNT(id) FROM Stores")
        public int getSizeStores();

        @Query("SELECT * FROM Stores WHERE username=:usernam")
        public List<Stores> getStoresByUser(String usernam);

        @Query("SELECT * FROM Stores")
        public List<Stores> loadAllStores();

        @Query("SELECT * FROM Stores WHERE acepted=:value")
        public List<Stores> loadAllStoresAcepted(boolean value);

        @Delete
        void deleteStore(Stores store);

        @Update
        void updateStores(Stores store);

        /**
         *  Funções que controlam o acesso a base de dados
         *  para a tabela Chats
         */
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        public void addChat(Chat chat);

        @Query("SELECT * FROM Chats WHERE id_game=:id")
        public List<Chat> loadAllChatsNormal(int id);

        @Query("SELECT COUNT(id) FROM Chats")
        public int getSizeChats();

        @Delete
        void deleteChat(Chat chat);

        /**
         *  Funções que controlam o acesso a base de dados
         *  para a tabela Mensagens
         */
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        public void addMensage(Mensage mensage);

        @Query("SELECT * FROM Mensagens WHERE id_chat=:id_chat")
        public List<Mensage> loadAllMensagens(int id_chat);

        @Query("SELECT COUNT(id) FROM Mensagens WHERE id_chat=:id_chat")
        public int getSizeMensagensChatEspecifico(int id_chat);

        /**
         *  Funções que controlam o acesso a base de dados
         *  para a tabela Avaliacao
         */
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        public void addAvaliacao(Avaliacao avaliacao);

        @Query("SELECT * FROM Avaliacao WHERE game_pk=:gameid")
        public List<Avaliacao> getGameRate(int gameid);

        /**
         *  Funções que controlam o acesso a base de dados
         *  para a tabela Favoritos
         */
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        public void addFavoritos(Favoritos favorito);

        @Query("SELECT * FROM Favoritos WHERE username=:name")
        public List<Favoritos> loadFavesByUser(String name);

        @Query("SELECT * FROM Favoritos WHERE games_id=:game")
        public List<Favoritos> loadFavesByGame(int game);

        @Query("SELECT * FROM Favoritos WHERE username = :username AND games_id = :id")
        public List<Favoritos> getIfExistFavorito(String username, int id);

        @Delete
        public void deleteFavoritos(Favoritos favoritos);

        /**
         *  Funções que controlam o acesso a base de dados
         *  para a tabela StoresGames
         */
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        public void addStoresGame(StoresGames games);

        @Query("SELECT * FROM StoresGames WHERE store_id=:id_store AND game_id=:id_game")
        public StoresGames getStoresGame(int id_game, int id_store);

        @Query("SELECT * FROM StoresGames WHERE store_id=:id")
        public List<StoresGames> loadSotresGamesByStore(int id);

        @Delete
        public void deleteStoresGames(StoresGames games);
    }
}
