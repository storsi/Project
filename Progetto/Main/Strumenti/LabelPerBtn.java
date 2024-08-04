package Progetto.Main.Strumenti;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;


public abstract class LabelPerBtn extends JLabel implements MouseListener{

    protected boolean clickable;
    private long timeEntered;
    private Thread thread;
    private volatile boolean isActive, twoSeconHover;

    public LabelPerBtn() {

        setTextColor(Color.WHITE);
        addMouseListener(this);
        setThread();

        clickable = true;
    }

    public LabelPerBtn(Dimension dim) {

        setTextColor(Color.WHITE);
        setSize(dim);
        addMouseListener(this);
        setThread();

        clickable = true;
    }

    public LabelPerBtn(boolean clickable) {

        setTextColor(Color.WHITE);
        addMouseListener(this);
        setThread();

        this.clickable = clickable;
    }

    private void setThread() {
        
        thread = new Thread( ()-> {

            while(true) {
                
                synchronized(thread) {
                    try {
                        
                        thread.wait();
                        
                        if(isActive) thread.wait(800);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if(isActive) hover2sec();
            }

        });

        thread.start();
        deactiveThread();
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public void setSize(Dimension dim) {
        setPreferredSize(dim);
    }

    public void setTextColor(Color colore) {
        setForeground(colore);
    }

    public void setTwoSecondsHover(boolean twoSeconHover) {
        this.twoSeconHover = twoSeconHover;
    }

    private void activeThread() {
        
        synchronized(thread) {
            setIsActive(true);
            thread.notify();
        }
    }

    private void deactiveThread() {
        
        synchronized(thread) {
            setIsActive(false);
            exitedHover();
            
        }
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public abstract void onClick();
    public abstract void hover2sec();
    public abstract void exitedHover();
    public abstract void hover();

    @Override
    public void mouseClicked(MouseEvent e) {
        if(clickable) onClick();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(clickable) setCursor(new Cursor(Cursor.HAND_CURSOR));
        hover();

        if(twoSeconHover) activeThread();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(clickable) setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        if(twoSeconHover) deactiveThread();
    }
    
}
