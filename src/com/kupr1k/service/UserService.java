package com.kupr1k.service;


import com.kupr1k.model.User;

import java.util.List;

public interface UserService {

    List<User> loadAll();

    List<User> getAll();

    User get(int id);

    String save(User user);

    String update(int id, String firstName, String lastName, String email, String phoneNum, String roles);

    String delete(int id);

    User get (List<User> users, int id);

    boolean save();
}
