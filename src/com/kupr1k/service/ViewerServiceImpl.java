package com.kupr1k.service;

import com.kupr1k.model.User;

import java.util.List;
import java.util.Scanner;

public class ViewerServiceImpl implements ViewerService {

    UserService userService = new UserServiceImpl();
    private List<User> users;
    private Scanner sc = new Scanner(System.in);

    //Load the database of users from disk
    @Override
    public void load() {
        users = userService.loadAll();
        if (users.size() > 0)
            System.out.println("Loaded [%d] users into the file.\n" + users.size());
        else
            System.out.println("There aren't any users available to load.");
    }

    //Display app options
    @Override
    public int viewOptions() {

        StringBuilder sb = new StringBuilder();

        sb.append("\n======== Users ========\n\n");
        sb.append("\t1) View all users\n");
        sb.append("\t2) Add a user\n");
        sb.append("\t3) Edit a user\n");
        sb.append("\t4) Remove a user\n");
        sb.append("\t5) Save and exit\n");
        sb.append("\t6) Exit without save\n\n");
        sb.append("Choose [1-6]: ");

        System.out.println(sb);

        String selection = sc.next();

        while (!selection.matches("[1-6]")) {
            System.out.println("Please choose correct choise [1-9]: ");
            selection = sc.next();
        }

        int choise = Integer.parseInt(selection);

        return choise;
    }

    @Override
    public void viewAll() {
        StringBuilder sb = new StringBuilder();
        System.out.println("\n===== View Users =====\n");
        if (users.size() > 0) {
            for (User user : users) {
                sb.append("\t[" + user.getId() + "] " + user.getFirstName() + " " + user.getLastName() + "\n");
            }
            System.out.println(sb);
            System.out.println("To view details enter the user ID, to return press <Enter>.");
            sc.nextLine();
            System.out.print("\nUser ID: ");
            String id = sc.nextLine();
            while (!id.isEmpty()) {
                while (id.matches("[^\\d]+")) {
                    System.out.print("\tPlease enter correct user id; to return press <Enter>.\n\n");
                    System.out.print("User ID: ");
                    id = sc.nextLine();
                }
                if (!id.isEmpty()) {
                    User user = userService.get(users, Integer.parseInt(id));

                    if (user != null) {
                        System.out.print("\n\tID: " + user.getId());
                        System.out.print("\n\tFirst name: " + user.getFirstName());
                        System.out.print("\n\tLast name: " + user.getLastName());
                        System.out.print("\n\tEmail: " + user.getEmail());
                        System.out.print("\n\tPhone number: " + user.getPhoneNum());
                        System.out.print("\n\tRoles : " + user.getRoles() + "\n");

                    } else {
                        System.out.println("\tThere is no user with ID [" + id + "] in the listed users.");
                    }

                    System.out.println("\nTo view details enter the user ID, to return press <Enter>.");
                    System.out.print("\nUser ID: ");
                    id = sc.nextLine();
                }
            }
        } else {
            System.out.println("\tThere aren't any users available.");
        }
    }

    @Override
    public void addUser() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===== Add an User =====\n");
        sb.append("\nPlease enter the following information:\n\n");
        System.out.print(sb);
        sc.nextLine();
        System.out.print("\tFirst name: ");
        String firstName = sc.nextLine();
        System.out.print("\tLast name: ");
        String lastName = sc.nextLine();
        System.out.print("\tEmail: ");
        String email = sc.nextLine();
        System.out.println("\tPhone number: ");
        String phoneNum = sc.nextLine();
        System.out.println("\tRoles: ");
        String roles = sc.nextLine();
        String status = userService.save(new User(firstName, lastName, email, phoneNum, roles));
        System.out.println("\n" + status);
    }

    @Override
    public void editUser() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===== Edit an User =====\n\n");
        for (User user : users) {
            sb.append("\t[" + user.getId() + "] " + user.getFirstName() + " " + user.getLastName() + "\n");
        }
        sb.append("\nEnter the user ID of the user you want to edit; to return press <Enter>.\n\n");
        System.out.print(sb);
        sc.nextLine();
        System.out.print("User ID: ");
        String id = sc.nextLine();
        while (id.matches("[^\\d]+") && !id.isEmpty()) {
            System.out.print("\tPlease enter correct user id; to return press <Enter>.\n\n");
            System.out.print("User ID: ");
            id = sc.nextLine();
        }
        if (!id.isEmpty()) {
            // sc.nextLine();
            User oldUser = userService.get(Integer.parseInt(id));
            if (oldUser != null) {
                System.out.print("\nInput the following information. To leave a field unchanged, hit <Enter>.\n\n");

                System.out.print("\tFirst name: [" + oldUser.getFirstName() + "]: ");
                String firstName = sc.nextLine();
                System.out.print("\tLast name: [" + oldUser.getLastName() + "]: ");
                String lastName = sc.nextLine();
                System.out.print("\tEmail: [" + oldUser.getEmail() + "]: ");
                String email = sc.nextLine();
                System.out.print("\tPhone number: [" + oldUser.getPhoneNum() + "]: ");
                String phoneNum = sc.nextLine();
                System.out.print("\tRoles: [" + oldUser.getRoles() + "]: ");
                String roles = sc.nextLine();

                if (firstName.isEmpty() && lastName.isEmpty() && email.isEmpty() && phoneNum.isEmpty() && roles.isEmpty()) {
                    System.out.println("\nThere are not any changes for user with ID [" + id + "]");
                } else {
                    String status = userService.update(oldUser.getId(), firstName, lastName, email, phoneNum, roles);
                    System.out.println("\n" + status);
                }
            } else {
                System.out.println("\tThere is no user with ID [" + id + "]");
            }
        }
    }

    @Override
    public void removeUser() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===== Remove an User =====\n");
        for (User user : users) {
            sb.append("\t[" + user.getId() + "] " + user.getFirstName() + " " + user.getLastName() + "\n");
        }
        sb.append("\nEnter the user ID of the user you want to remove; to return press <Enter>.\n\n");
        System.out.print(sb);
        sc.nextLine();
        System.out.print("User ID: ");
        String id = sc.nextLine();
        while (id.matches("[^\\d]+") && !id.isEmpty()) {
            System.out.print("\tPlease enter correct user id; to return press <Enter>.\n\n");
            System.out.print("User ID: ");
            id = sc.nextLine();
        }
        if (!id.isEmpty()) {
            User user = userService.get(Integer.parseInt(id));
            if (user != null) {
                System.out.print("\tAre you sure you want to remove " + user.getFirstName() + " " + user.getLastName()
                        + "? \n\tEnter y for confirmation; to return press <Enter>: ");

                String title = sc.nextLine();
                if (!title.isEmpty() && title.equalsIgnoreCase("y")) {
                    String status = userService.delete(user.getId());
                    System.out.println("\n" + status);
                }
            } else {
                System.out.println("\tThere is no user with ID [" + id + "]");
            }
        }
    }

    @Override
    public void saveAndExit() {
        if (userService.save()) {
            System.out.println("\nUser list saved.");
            System.exit(0);
        } else
            System.out.println("Sorry, Something has error while saving.");
    }
}
