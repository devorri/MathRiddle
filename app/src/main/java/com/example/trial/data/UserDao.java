package com.example.trial.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void registerUser(User user);

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    User login(String email, String password);

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);

    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    User getUserById(int userId);

    @Query("UPDATE users SET totalSolved = :totalSolved, bestStreak = :bestStreak, totalCorrect = :totalCorrect, totalAttempted = :totalAttempted WHERE id = :userId")
    void updateStats(int userId, int totalSolved, int bestStreak, int totalCorrect, int totalAttempted);

    @Query("SELECT * FROM users ORDER BY totalSolved DESC")
    List<User> getAllUsersByScore();
}
