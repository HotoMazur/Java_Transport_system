package com.example.transport.utils;

import model.User;

import java.io.*;
import java.util.List;

public class RW {
    private static final String filenameDriver = "driver.txt";
    private static final String filenameManager = "manager.txt";
    public static void writeToFile(List<User> drivers, List<User> managers) {
        try {
            if (drivers != null) {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filenameDriver));
                objectOutputStream.writeObject(drivers);
                objectOutputStream.close();
            } else {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filenameManager));
                objectOutputStream.writeObject(managers);
                objectOutputStream.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<User> readFromFile (String type){
        List<User> users = null;
        try {
            String fileName = type.equals("D") ? filenameDriver : filenameManager;
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName));
            users = (List<User>) objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return users;
    }
}
