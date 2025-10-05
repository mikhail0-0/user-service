package org.example.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String email;

    private int age;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    public User(){

    }

    public User(String name, String email, int age){
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name){ this.name = name; }

    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email){ this.email = email; }

    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {this.age = age;}

    public Date getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String toString() {
        return "{" +
            " id: " + this.id +
            " name: " + this.name +
            " email: " + this.email +
            " age: " + this.age +
            " createdAt: " + this.createdAt +
        "}";
    }
}
