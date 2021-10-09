package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TaskManager {
    public static void main(String[] args) {

        printMenu();
        String [][] tasks = getTaskList();

        Scanner menuOption = new Scanner(System.in);
        String input;
        boolean loop = true;


        while (loop)
        {
            input = menuOption.nextLine();

            switch (input) {
                case "list":
                    printList(tasks);
                    break;
                case "exit":
                    System.out.println(ConsoleColors.RED + "Bye, bye.");
                    loop = false;
                    break;
                case "add":
                    tasks = addTask(tasks);
                    break;
                case "remove":
                    tasks = removeTask(tasks);
                    break;
                default:
                    System.out.println("Please select a correct option.");
            }
            printMenu();
        }


    }

    public static void printMenu() {
        String [] menuOptions = {"Please select an option: ", "add", "remove", "list", "exit"};
        System.out.println(ConsoleColors.BLUE + menuOptions[0] + ConsoleColors.RESET);
        for (int i = 1; i < menuOptions.length; i++) {
            System.out.println(i + " : " + menuOptions[i]);
        }
    }

    public static String[][] getTaskList() {

        String[][] taskList = new String[10][3];
        int listCounter = 0;
        Path path = Paths.get("tasks.csv");
        String tempStr;

        try (Scanner scanner = new Scanner(path)) {
            while (scanner.hasNextLine()) {
                taskList[listCounter] = scanner.nextLine().split(",");
                listCounter++;
            }
        } catch (IOException e) {
            System.out.println("Exception occured: " +e.getMessage());
        }

        String [][] taskListToReturn = new String[listCounter][3];

        for (int i = 0; i < taskListToReturn.length; i++) {
            for (int j = 0; j < 3; j++) {
                taskListToReturn[i][j] = taskList[i][j];
            }
        }
        return taskListToReturn;
    }

     public static void printList(String testStrArr[][]) {
         for (int i = 0; i < testStrArr.length; i++) {
             System.out.print(i + " : ");
             for (int j = 0; j <testStrArr[i].length; j++) {

                 System.out.print(testStrArr[i][j]);
             }
             System.out.println();
         }
     }

     public static String[][] addTask (String [][] inputList) {
        Scanner scanner = new Scanner(System.in);
        String [] addTask = new String[3];

         System.out.println("Please add task description");
         addTask [0] = scanner.nextLine();

         System.out.println("Please add task due date");
         String date = scanner.nextLine();
         while (isValidDate(date) == false) {
             date = scanner.nextLine();
         }

         addTask [1] = " " + date;


         System.out.println("Is your task important: true/false");
         String trueFalse = scanner.nextLine();

         while (!trueFalse.equals("true") && !trueFalse.equals("false")) {
             System.out.println("Please type valid statement: true/false");
             trueFalse = scanner.nextLine();
         }
         addTask [2] = " " + trueFalse;

         String [][] returnedList = Arrays.copyOf(inputList, inputList.length + 1);

         returnedList[returnedList.length - 1] = addTask;

         System.out.println("Task succesfully added");

         return returnedList;

     }

     public static boolean isValidDate (String inputDate) {
        String [] date = inputDate.split("-");

        try {
            if (date.length != 3) {
                System.out.println("Invalid date input");
                return false;
            } else if (!NumberUtils.isParsable(date[0]) || !(Integer.parseInt(date[0]) >= 2021)) {
                System.out.println("Invalid year");
                return false;
            } else if (!NumberUtils.isParsable(date[1]) || (Integer.parseInt(date[1]) <= 0) || (Integer.parseInt(date[1]) > 12)) {
                System.out.println("Invalid month");
                return false;
            } else if (!NumberUtils.isParsable(date[2]) || (Integer.parseInt(date[2]) <= 0) || (Integer.parseInt(date[2]) > 31)) {
                System.out.println("Invalid day");
                return false;
            } else {
                System.out.println("Podana data jest prawidlowa");
                return true;
            }
        } catch (NumberFormatException e) {
            System.out.println("Exception occured: " + e.getMessage());
            return false;
        }
     }

     public static String[][] removeTask (String [][] taskList) {
         String[][] taskListRemovedTask = new String[taskList.length - 1][3];
         Scanner scanner = new Scanner(System.in);

         System.out.println("Please specify which task do you want to delete: ");

         int taskIndex;

         try {
             taskIndex = scanner.nextInt();
             while (taskIndex <= 0 || taskIndex >= taskList.length) {
                 System.out.println("Task with specified value does not exist, try again:");
                 taskIndex = scanner.nextInt();
             }
             taskListRemovedTask = ArrayUtils.remove(taskList, taskIndex);
             System.out.println("Task " + taskIndex + " has been successfully removed");
             return taskListRemovedTask;
         } catch (InputMismatchException e) {
             System.out.println("Invalid format of input data! Input has to be an Integer");
             return taskList;
         }
     }

     public static boolean isInteger(String input) {

        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
     }



}



