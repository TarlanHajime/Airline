package util.impl;

import util.impl.InputUtil;

public class MenuUtil {
    public static int entryMenu() {
        System.out.println(
                "[1] Buy ticket \n" +
                "[2] Show all airlines \n"
        );
        return InputUtil.inputRequiredInt("Choose option");
    }
}
