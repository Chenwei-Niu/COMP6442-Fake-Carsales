package com.example.buy.entity;

import android.text.TextUtils;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User extends LitePalSupport {

    @Column(unique = true, defaultValue = "0")
    private int id;
    private String email;
    private String password;
    private String name;
    private String sex;
    private String phone;
    /**
     * Relationships with other users, ids of people who are currently friended
     * Split with '-'
     */
    private String friendRelations = "";

    public List<String> getFriUserIdList(){
        List<String> result = new ArrayList<>();
        if(TextUtils.isEmpty(friendRelations)) {
            return result;
        }
        String[] userIds = friendRelations.split("-");
        return Arrays.asList(userIds);
    }

    public void doAddFri(int id){
        if(TextUtils.isEmpty(friendRelations)){
            friendRelations = String.valueOf(id);
        } else {
            friendRelations = friendRelations + "-" + id;
        }
    }

    public boolean hasThisFriends(int id){
        if(TextUtils.isEmpty(friendRelations)){
            return false;
        } else {
            for(String str : getFriUserIdList()){
                if(TextUtils.equals(str,String.valueOf(id))) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getFriendRelations() {
        return friendRelations;
    }

    public void setFriendRelations(String friendRelations) {
        this.friendRelations = friendRelations;
    }

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
