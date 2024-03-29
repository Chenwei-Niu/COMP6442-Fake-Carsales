package com.example.buy.sqlite;

import com.example.buy.entity.Message;
import com.example.buy.entity.User;

import java.util.List;

/**
 * This is the SQLite DAO interface,
 * concrete DAOImpl should implement this interface to support the Database functionalities
 * @author Chenwei Niu , Zice Yan
 */
public interface SQLiteDAO {
    void setUser(User user);

    User getUser();

    boolean login(String username, String password);

    String register(User user);

    void updateUserInfo(User user);

    boolean hasUser();

    List<User> searchFriends();

    void addFriend(int id);

    List<User> searchFriends(String key);


    List<Message> searchAllMessage(int receiveUserId);
}
