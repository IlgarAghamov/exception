package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class UserDataApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Введите данные (Фамилия Имя Отчество датарождения номертелефона пол):");
            String userData = scanner.nextLine();

            processUserData(userData);
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

    static void processUserData(String userData)
            throws InvalidDataFormatException, FileOperationException {
        String[] dataParts = userData.split("\\s+");

        if (dataParts.length != 6) {
            throw new InvalidDataFormatException("Неверное количество данных. Требуется 6 значений.");
        }

        String lastName = dataParts[0];
        String firstName = dataParts[1];
        String middleName = dataParts[2];
        String birthDateString = dataParts[3];
        String phoneNumberString = dataParts[4];
        String gender = dataParts[5];

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date birthDate = dateFormat.parse(birthDateString);

            // Проверка формата номера телефона
            long phoneNumber = Long.parseLong(phoneNumberString);

            // Создаем строку для записи в файл
            String fileContent = String.format("%s %s %s %s %d %s",
                    lastName, firstName, middleName, dateFormat.format(birthDate), phoneNumber, gender);

            // Записываем в файл
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(lastName + ".txt", true))) {
                writer.write(fileContent);
                writer.newLine();
            } catch (IOException e) {
                throw new FileOperationException("Ошибка при записи в файл.", e);
            }

        } catch (ParseException | NumberFormatException e) {
            throw new InvalidDataFormatException("Ошибка парсинга даты или номера телефона.");
        }
    }
}