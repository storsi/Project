package Progetto.Main.Interface;

public interface Animated {

    default void setThread() {

        Thread thread =new Thread(() -> {
            do{

                if(!isActive()) {
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
            notify();
        }
    }
    
    boolean isActive();
    void anima();
}
