package com.example.buy.entity;

import android.text.TextUtils;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class User extends LitePalSupport implements Serializable {

    @Column(unique = true, defaultValue = "0")
    private int id;
    private String email;
    private String password;
    private String name;
    private String sex;
    private String phone;
    private ArrayList<Car> onSaleCars = new ArrayList<>();
    private ArrayList<Car> favoriteCars = new ArrayList<>();
    /**
     * Relationships with other users, ids of people who are currently friended
     * Split with '-'
     * @Author Zice Yan
     */
    private String friendRelations = "";
    /**
     * The address where the image is stored in the phone
     * @Author Zice Yan
     */
    private String picUrl = "";

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

    public ArrayList<Car> getFavoriteCars() {
        return favoriteCars;
    }

    public void setFavoriteCars(ArrayList<Car> favoriteCars) {
        this.favoriteCars = favoriteCars;
    }

    public ArrayList<Car> getOnSaleCars() {
        return onSaleCars;
    }

    public void setOnSaleCars(ArrayList<Car> onSaleCars) {
        this.onSaleCars = onSaleCars;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", phone='" + phone + '\'' +
//                ", onSaleCars=" + onSaleCars +
//                ", favoriteCars=" + favoriteCars +  this line causes the bug since recursion of Car and User
                ", friendRelations='" + friendRelations + '\'' +
                ", picUrl='" + picUrl + '\'' +
                '}';
    }

}
