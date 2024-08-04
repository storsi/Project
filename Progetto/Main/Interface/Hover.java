package Progetto.Main.Interface;

import java.awt.event.MouseEvent;
import java.awt.Cursor;

public interface Hover extends MouseInterface{

    //Se non funziona controllare di aver richiamato il metodo setHoverThread()!
    
    default void setHoverThread() {

        Thread thread = new Thread(() -> {

            do{
                    
                synchronized(this) {
                    try {
                        this.wait();
                        wait(getMilliseconds());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(hoverActive());
                if(hoverActive()) hover();

            }while(true);
        });

        thread.start();
    }

    default void attivaHover() {
        
        synchronized(this) {
            this.notifyAll();
        }
    }

    /**
     * Restituisce il boolean che indica se l'hover è attivo o no
     * @return
     */
    boolean hoverActive();

    /**
     * Restituisce i millisecondi che deve aspettare prima che esegua l'azione del metodo hover()
     * @return
     */
    int getMilliseconds();

    /**
     * Metodo chiamato quando bisogna eseguire l'azione dell'hover (dopo il tempo indicato)
     */
    void hover();

    /**
     * Metodo chiamato quando il cursore entra nell'hover sull'oggetto
     */
    void inHover();

    /**
     * Metodo chiamato quando il cursore esce dall'hover sull'oggetto
     */
    void outHover();

    @Override
    default void mouseEntered(MouseEvent e) {
        e.getComponent().setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        inHover();
    }

    @Override
    default void mouseExited(MouseEvent e) {
        e.getComponent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        outHover();
    }
}
