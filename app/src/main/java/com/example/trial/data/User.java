package com.example.trial.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;
    
    public String firstName;
    public String lastName;
    public String studentId;
    public String age;
    public String email;
    public String password;
    public int progress; // Highest stage cleared (0-7)

    public User(String firstName, String lastName, String studentId, String age, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentId = studentId;
        this.age = age;
        this.email = email;
        this.password = password;
        this.progress = 0;
    }
}
