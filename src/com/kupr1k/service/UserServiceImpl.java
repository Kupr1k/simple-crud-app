package com.kupr1k.service;

import com.kupr1k.model.User;
import com.kupr1k.connection.FileConnection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserServiceImpl implements UserService {

    File file = FileConnection.getFile();

    private List<User> users = new ArrayList<>();

    //load all users from the disk file
    @Override
    public List<User> loadAll() {
        try {
            Scanner reader = new Scanner(file);
            reader.useDelimiter(" , |\n");

            int id;
            String firstName, lastName, email, phoneNum, roles;

            while (reader.hasNext()) {
                id = Integer.parseInt(reader.next());
                reader.skip("[\r\n]*");
                firstName = reader.next();
                lastName = reader.next();
                email = reader.next();
                phoneNum = reader.next();
                roles = reader.next();
                users.add(new User(id, firstName, lastName, email, phoneNum, roles));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e);
        }
        return users;
    }

    //return all users
    @Override
    public List<User> getAll() {
        return users;
    }

    //find a user by id
    @Override
    public User get(int id) {
        return users.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
    }

    //add new user
    @Override
    public String save(User user) {
        int id = users.size() > 0 ? users.get(users.size() - 1).getId() + 1 : 1;
        user.setId(id);
        users.add(user);
        return "User [" + user.getId() + "] saved";
    }

    //edit an existing user
    @Override
    public String update(int id, String firstName, String lastName, String email, String phoneNum, String roles) {
        for (User user : users) {
            if (user.getId() == id) {
                if (!firstName.isEmpty())
                    user.setFirstName(firstName);
                if (!lastName.isEmpty())
                    user.setLastName(lastName);
                if (!email.isEmpty())
                    user.setEmail(email);
                if (!phoneNum.isEmpty())
                    user.setPhoneNum(phoneNum);
                if (!roles.isEmpty())
                    user.setRoles(roles);
                break;
            }
        }
        return "User [" + id + "] updated";
    }

    //remove an existing user
    @Override
    public String delete(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                users.remove(user);
                break;
            }
        }
        return "User [" + id + "] deleted";
    }

    //filter a list of users by id
    @Override
    public User get(List<User> users, int id) {
        return users.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
    }

    //Write the database of users to disk, upon exiting the application
    @Override
    public boolean save() {
        if (users.isEmpty() || !file.exists()) {
            return false;
        } else {
            PrintWriter writer;
            try {
                writer = new PrintWriter(file);

                for (User user : users) {
                    writer.println(user.toRecord());
                }
                writer.flush();
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return true;
        }
    }
}
