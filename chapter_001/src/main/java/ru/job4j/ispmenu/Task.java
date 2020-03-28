package ru.job4j.ispmenu;

class Task implements ExecuteMenuItem {
    String name;

    public Task(String name) {
        this.name = name;
    }

    @Override
    public void executeItem() {
        System.out.println(this.name + " executed");
    }
}
