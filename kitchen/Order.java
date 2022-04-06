package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.Tablet;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class Order {
    private final Tablet tablet;
    protected List<Dish> dishes;

    public Order(Tablet tablet) throws IOException {
        this.tablet = tablet;
        initDishes();
        ConsoleHelper.writeMessage(toString());
    }

    public int getTotalCookingTime(){
        int totalCookingTime = 0;
        for (Dish d:dishes) {
            totalCookingTime += d.getDuration();
        }
        return totalCookingTime;
    }

    @Override
    public String toString() {
        String order = "";
        if (dishes.size() != 0){
            order = "Your order: " + dishes + " of " + tablet.toString() + ", cooking time " + getTotalCookingTime() + "min";
        }
        return order;
    }

    public boolean isEmpty(){
        return dishes.isEmpty();
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    protected void initDishes() throws IOException {
        dishes = ConsoleHelper.getAllDishesForOrder();
    }

    public Tablet getTablet() {
        return tablet;
    }
}
