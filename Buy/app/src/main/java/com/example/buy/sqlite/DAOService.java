package com.example.buy.sqlite;

import com.example.buy.entity.User;
import com.example.buy.utils.Utils;

import org.litepal.LitePal;

import java.util.List;

public class DAOService {
    private static DAOService DAOService;

    private User user = new User();

    private DAOService() {
    }

    public static DAOService getInstance() {
        if (null == DAOService) {
            DAOService = new DAOService();
        }
        return DAOService;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }


    //login
    public boolean login(String username, String password) {
        List<User> all = LitePal.where("email = ? and password = ?",username,password).find(User.class);
        if (Utils.isNotEmpty(all)) {
            setUser(all.get(0));
            return true;
        }
        return false;
    }

    //register
    public String register(User user) {
        // check
        List<User> all = LitePal.where("email=?",user.getEmail()).find(User.class);
        // if exists
        if (Utils.isNotEmpty(all)) {
            return "already has this email";
        } else {
            // insert info
            return user.save() ? "" : "register failed";
        }
    }

    /**
     * update
     */
    public void updateUserInfo(User user){
        user.update(user.getId());
    }


    public  boolean hasUser(){
        return null != LitePal.findFirst(User.class);
    }

}