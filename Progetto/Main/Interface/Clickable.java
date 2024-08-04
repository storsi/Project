package Progetto.Main.Interface;

import java.awt.event.MouseEvent;

public interface Clickable extends MouseInterface{

    void afterClick();
    
    @Override
    default void mouseClicked(MouseEvent e) {
        afterClick();
    }
}
