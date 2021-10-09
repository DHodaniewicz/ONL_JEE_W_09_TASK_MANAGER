package pl.coderslab;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {
    public static void main(String[] args) {

        printMenu();

        String [][] testStrArr = getTaskList();

        for (int i = 0; i < testStrArr.length; i++) {
            System.out.println(Arrays.toString(testStrArr[i]));
        }


    }

    public static void printMenu() {
        String [] menuOptions = {"Please select an option: ", "add", "remove", "list", "exit"};
        System.out.println(ConsoleColors.BLUE + menuOptions[0] + ConsoleColors.RESET);
        for (int i = 1; i < menuOptions.length; i++) {
            System.out.println(menuOptions[i]);
        }
    }

    public static String[][] getTaskList() {

        String[][] taskList = new String[10][3];
        int listCounter = 0;
        Path path = Paths.get("tasks.csv");

        try (Scanner scanner = new Scanner(path)) {
            while (scanner.hasNextLine()) {
                taskList[listCounter] = scanner.nextLine().split(",");
                listCounter++;
            }
        } catch (IOException e) {
            System.out.println("Exception occured: " +e.getMessage());
        }
        return taskList;
    }



}



