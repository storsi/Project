package Progetto.Main.Interface;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;


public interface MouseInterface extends MouseListener{
    
    @Override
    default void mouseClicked(MouseEvent e) {
        
    }

    @Override
    default void mousePressed(MouseEvent e) {
        
    }

    @Override
    default void mouseReleased(MouseEvent e) {
        
    }

    @Override
    default void mouseEntered(MouseEvent e) {
        
    }

    @Override
    default void mouseExited(MouseEvent e) {
        
    }
}
