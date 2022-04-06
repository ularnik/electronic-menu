package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.TestOrder;

import java.util.List;

public class RandomOrderGeneratorTask implements Runnable{
    private List<Tablet> tablets;
    private int interval;

    @Override
    public void run() {
        try {
            while (true){
                int num = (int) (Math.random() * tablets.size());
                Tablet tablet = tablets.get(num);
                tablet.createTestOrder();
                Thread.sleep(interval);
            }
            
        }catch (InterruptedException e){

        }

    }

    public RandomOrderGeneratorTask(List<Tablet> tablets, int interval) {
        this.tablets = tablets;
        this.interval = interval;
    }
}
