package util.impl;

import java.util.Scanner;

public class InputUtil {
    public static int inputRequiredInt(String title) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(title);

        return scanner.nextInt();
    }

    public static String inputRequiredString(String title) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(title);

        return scanner.nextLine();
    }

    public static double inputRequiredDouble(String title) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(title);

        return scanner.nextDouble();
    }

    public static char inputRequiredChar(String title) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(title);

        return scanner.next().charAt(0);
    }
}
