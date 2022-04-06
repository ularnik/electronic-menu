package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.AdvertisementManager;
import com.javarush.task.task27.task2712.ad.NoVideoAvailableException;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.TestOrder;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.io.IOException;
import java.util.Observable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablet {
    private static Logger logger = Logger.getLogger(Tablet.class.getName());
    private final int number;
    private LinkedBlockingQueue<Order> queue;

    public Tablet(int number) {
        this.number = number;
    }

    public Order createOrder(){
        Order order = null;
        try {
            order = new Order(this);
            if (order.isEmpty()){
                return null;
            }
            queue.add(order);
            AdvertisementManager am = new AdvertisementManager(order.getTotalCookingTime()*60);

            am.processVideos();

        }catch (IOException e){
            logger.log(Level.SEVERE, "Console is unavailable.");
        }catch (NoVideoAvailableException n){
            logger.log(Level.INFO, "No video is available for the order " + order);
        }
        return order;
    }

    public void createTestOrder(){
        Order order = null;
        try {
            order = new TestOrder(this);
            queue.add(order);
            AdvertisementManager am = new AdvertisementManager(order.getTotalCookingTime()*60);

            am.processVideos();

        }catch (IOException e){
            logger.log(Level.SEVERE, "Console is unavailable.");
        }catch (NoVideoAvailableException n){
            logger.log(Level.INFO, "No video is available for the order " + order);
        }
    }



    @Override
    public String toString() {
        return "Tablet{" +
                "number=" + number +
                '}';
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }
}
