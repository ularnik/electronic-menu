package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.LinkedBlockingQueue;

public class Cook extends Observable implements Runnable{
    private String name;
    private boolean busy;
    private LinkedBlockingQueue<Order> queue = new LinkedBlockingQueue<>();

    public Cook(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }



    public void startCookingOrder(Order order){
        busy = true;
        ConsoleHelper.writeMessage("Start cooking - " + order.toString());
        try {
            Thread.sleep(order.getTotalCookingTime()*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setChanged();
        notifyObservers(order);
        CookedOrderEventDataRow cookedOrderEventDataRow = new CookedOrderEventDataRow(order.getTablet().toString(), name, order.getTotalCookingTime() * 60, order.getDishes());
        StatisticManager.getInstance().register(cookedOrderEventDataRow);

        busy=false;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true){
                if (!queue.isEmpty()){

                        if (!this.isBusy()){
                            this.startCookingOrder(queue.take());
                        }
                }
                Thread.sleep(10);
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
