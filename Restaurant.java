package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.Waiter;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {
    private static final int ORDER_CREATING_INTERVAL = 100;
    private static final LinkedBlockingQueue<Order> ORDER_QUEUE = new LinkedBlockingQueue();

    public static void main(String[] args) {
        List<Tablet> tablets = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tablets.add(new Tablet(i+1));
        }
        for (Tablet t:tablets) {
            t.setQueue(ORDER_QUEUE);
        }
       

        Cook cook1 = new Cook("Наташа");
        cook1.setQueue(ORDER_QUEUE);
        Cook cook2 = new Cook("Артем");
        cook2.setQueue(ORDER_QUEUE);
        Waiter waiter = new Waiter();
        Waiter waiter1 = new Waiter();
        cook1.addObserver(waiter);
        cook2.addObserver(waiter1);
        Thread thread1 = new Thread(cook1);
        thread1.start();
        Thread thread2 = new Thread(cook2);
        thread2.start();



        Thread thread = new Thread(new RandomOrderGeneratorTask(tablets, ORDER_CREATING_INTERVAL));
        thread.start();
        try {
            Thread.sleep(1000);
            thread.interrupt();
            thread.join();
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        DirectorTablet directorTablet = new DirectorTablet();
        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();



    }
}
