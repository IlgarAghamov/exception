package org.example;

import java.util.Scanner;

//import static org.example.UserDataApplication.processUserData;

public class Main {
    public static void main(String[] args) {
        UserDataApplication userDataApplication = new UserDataApplication();
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Введите данные (Фамилия , Имя , Отчество , датарождения , номер телефона , пол):");
            String userData = scanner.nextLine();

            userDataApplication.processUserData(userData);
            System.out.println("Данные успешно обработаны и записаны в файл.");
        } catch (InvalidDataFormatException | FileOperationException e) {
            System.err.println("Ошибка: " + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        } finally {
            scanner.close();
        }
    }
}