package ru.job4j.ispmenu;

import java.util.Scanner;
import java.util.Stack;

class Main {
    public static void main(String[] args) {
        new Main().go();
    }

    void go() {
        System.out.println();

        MenuItem task = new MenuItem("0",
                "Задача ",
                null,
                new Task("Задача 0"));
        MenuItem task1 = new MenuItem("1",
                "Задача 1.",
                task,
                new Task("Задача 1."));
        MenuItem task11 = new MenuItem("11",
                "Задача 1.1.",
                task1,
                new Task("Задача 1.1."));
        MenuItem task111 = new MenuItem("111",
                "Задача 1.1.1.",
                task11,
                new Task("Задача 1.1.1."));
        MenuItem task112 = new MenuItem("112",
                "Задача 1.1.2.",
                task11,
                new Task("Задача 1.1.2."));

        MenuItem task12 = new MenuItem("12",
                "Задача 1.2.",
                task1,
                new Task("Задача 1.2."));

        MenuItem task2 = new MenuItem("2",
                "Задача 2.",
                task,
                new Task("Задача 2."));
        MenuItem task3 = new MenuItem("3",
                "Задача 3.",
                task,
                new Task("Задача 3."));
        MenuItem task31 = new MenuItem("31",
                "Задача 3.1.",
                task3,
                new Task("Задача 3.1."));

        //----------------------  вывод ---------------------------
        treeMenu(task);
        System.out.println();

        Scanner in = new Scanner(System.in);
        System.out.print("Input a number: ");
        String num = in.nextLine();
        in.close();

        MenuItem m = findInMenu(num, task);
        if (m != null) {
            m.execute.executeItem();
        }
    }

    MenuItem findInMenu(String num, MenuItem tree) {
        Stack<MenuItem> stack = new Stack<>();
        stack.push(tree);
        while (!stack.empty()) {
            MenuItem menuItem = stack.pop();
            if (menuItem.getNumberMenu().equals(num)) {
                return menuItem;
            }
            if (menuItem.getChildren() != null) {
                menuItem.getChildren().forEach(n -> stack.push(n));
            }
        }
        return null;
    }

    void treeMenu(MenuItem tree) {
        String name = tree.name;
        String q = namberparents(tree);
        System.out.print(q);
        System.out.println(name);
        if (tree.children != null) {
            tree.getChildren().forEach(n -> treeMenu(n));
        }
    }

    String namberparents(MenuItem item) {
        String n = "";
        while ((item = item.getParent()) != null) {
            n += "----";
        }
        return n;
    }
}



