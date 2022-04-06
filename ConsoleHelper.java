package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleHelper {
    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message){
        System.out.println(message);
    }

    public static String readString() throws IOException {
        return bufferedReader.readLine();
    }
    

    public static List<Dish> getAllDishesForOrder() throws IOException {
        List<Dish> allDishesForOrder = new ArrayList<>();
        ConsoleHelper.writeMessage("Пожалуйста, выберите блюдо из списка:" + Dish.allDishesToString() + "\n или введите 'exit', чтобы завершить заказ");
        while (true){
            String text = ConsoleHelper.readString();
            if (text.equals("exit")) break;
            try {
                Dish dish = Dish.valueOf(text);
                allDishesForOrder.add(dish);
                writeMessage("Блюдо добавлено в заказ.");
            }catch (Exception e){
                writeMessage("Такое блюдо не найдено, выберите еще раз.");
            }
        }
        return allDishesForOrder;

    }
}
