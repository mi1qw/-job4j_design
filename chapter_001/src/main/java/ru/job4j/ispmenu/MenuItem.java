package ru.job4j.ispmenu;

import java.util.ArrayList;
import java.util.List;

public class MenuItem {
    String name;
    String numberMenu;
    MenuItem parent;
    ExecuteMenuItem execute;
    List<MenuItem> children;

    public MenuItem(String numberMenu, String name, MenuItem parent, ExecuteMenuItem execute) {
        this.name = name;
        this.numberMenu = numberMenu;
        this.parent = parent;
        this.execute = execute;
        children = new ArrayList<>();
        if (this.parent != null) {
            this.parent.children.add(this);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumberMenu(String numberMenu) {
        this.numberMenu = numberMenu;
    }

    public void setChildren(List<MenuItem> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public String getNumberMenu() {
        return numberMenu;
    }

    public MenuItem getParent() {
        return parent;
    }

    public ExecuteMenuItem getExecute() {
        return execute;
    }

    public List<MenuItem> getChildren() {
        return children;
    }
}

