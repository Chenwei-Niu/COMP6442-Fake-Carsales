package com.example.buy.sqlite;

import com.example.buy.entity.Message;
import com.example.buy.entity.User;
import com.example.buy.utils.Utils;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SQLiteDAOImpl implements SQLiteDAO {
    private static SQLiteDAOImpl sqliteDAOImpl;

    private User user = new User();

    private SQLiteDAOImpl() {
    }

    public static SQLiteDAOImpl getInstance() {
        if (null == sqliteDAOImpl) {
            sqliteDAOImpl = new SQLiteDAOImpl();
        }
        return sqliteDAOImpl;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }


    /**
     * Login
     * @param username
     * @param password
     * @return
     * @Author Zice Yan
     */
    public boolean login(String username, String password) {
        List<User> all = LitePal.where("email = ? and password = ?",username,password).find(User.class);
        if (Utils.isNotEmpty(all)) {
            setUser(all.get(0));
            return true;
        }
        return false;
    }

    /**
     * Rigister
     * @param user
     * @return
     * @Author Zice Yan
     */
    public String register(User user) {
        // check
        List<User> all = LitePal.where("email=?",user.getEmail()).find(User.class);
        // if email exits
        if (Utils.isNotEmpty(all)) {
            return "already has this email";
        } else {
            // insert info
            return user.save() ? "" : "register failed";
        }
    }

    /**
     * update User info
     * @Author Zice Yan
     */
    public void updateUserInfo(User user){
        user.update(user.getId());
    }


    public  boolean hasUser(){
        return null != LitePal.findFirst(User.class);
    }

    /**
     * check friends info
     * @Author Zice Yan
     */
    public List<User> searchFriends(){
        List<User> result = new ArrayList<>();
        for(String str : getUser().getFriUserIdList()){
            result.add(LitePal.find(User.class,Integer.parseInt(str)));
        }
        return result;
    }

    /**
     * add friends
     * @Author Zice Yan
     */
    public void addFriend(int id){
        // A add B
        getUser().doAddFri(id);
        getUser().save();
        // B add A
        User user = LitePal.find(User.class,id);
        user.doAddFri(getUser().getId());
        user.save();
    }

    /**
     * Check user information according to email name
     * @Author Zice Yan
     */
    public List<User> searchFriends(String key){
        return LitePal.where("email like ?","%" + key + "%").find(User.class);
    }

    /**
     * Query the list of messages corresponding to the sender and the recipient
     * @Author Zice Yan
     */
    public List<Message> searchAllMessage(int receiveUserId){
        int sendUserId = getUser().getId();
        List<Message> list1 = LitePal.where("sendUserId = ? and receiveUserId = ? ",String.valueOf(sendUserId),String.valueOf(receiveUserId)).find(Message.class);
        List<Message> list2 = LitePal.where("sendUserId = ? and receiveUserId = ? ",String.valueOf(receiveUserId),String.valueOf(sendUserId)).find(Message.class);
        List<Message> result = new ArrayList<>();
        result.addAll(list1);
        result.addAll(list2);
        Collections.sort(result);
        return result;
    }



}