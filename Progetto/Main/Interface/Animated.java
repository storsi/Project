package Progetto.Main.Interface;

import java.awt.event.MouseEvent;

public interface Animated extends MouseInterface{

    default void setAnimationThread() {

        Thread thread =new Thread(() -> {
            do{

                if(!animationActive()) {
                    synchronized(this) {
                        try {
                            this.wait();

                            
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                
                anima();

            }while(true);
        });

        thread.start();
    }

    default void attivaThread() {

        synchronized(this) {
            notifyAll();
        }
    }
    
    boolean animationActive();
    void anima();
}
