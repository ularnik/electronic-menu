package com.javarush.task.task27.task2712.kitchen;

public enum Dish {
    FISH(25),
    STEAK(30),
    SOUP(15),
    JUICE(5),
    WATER(3);

    private int duration;

    Dish(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public static String allDishesToString(){
        StringBuilder sb = new StringBuilder();
        sb.append(Dish.values()[0]);
        for (int i = 1; i < Dish.values().length; i++) {
            sb.append(", ").append(Dish.values()[i]);
        }


        return sb.toString().trim();
    }
}
