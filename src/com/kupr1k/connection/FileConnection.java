package com.kupr1k.connection;

import java.io.File;
import java.io.IOException;

/**
 Create a file if it is not exist at the current user home directory or Return it if exists
 **/

public class FileConnection {

    public static File getFile() {

        String fileName = "users.txt";
        String absolutePath = "src" + File.separator+"resources" + File.separator + fileName;

        File file = new File(absolutePath);

        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("users.txt file created!");
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        return file;
    }
}
