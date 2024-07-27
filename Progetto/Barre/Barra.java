package Progetto.Barre;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.LayoutManager;

public abstract class Barra extends JPanel{

    private final int FPS = 60;
    private long startTime, currentTime;
    private Thread animazione;
    private boolean animazioneAttiva;

    public Barra(Color background, LayoutManager layout, boolean animated) {
        
        super(layout);
        super.setBackground(background);

        stopAnimation();
        if(animated) animationTick();
    }
    
    protected abstract void setUp();
    public abstract void btnIconClicked(BtnIcon btn);
    protected abstract void anima();

    public void startAnimation() {
        synchronized(animazione) {
            animazione.notify();
            animazioneAttiva = true;
        }
    }

    public void stopAnimation() {
        animazioneAttiva = false;
    }

    private void animationTick() {
        startTime = System.currentTimeMillis();

        animazione = new Thread(() -> {
            do {

                if(!animazioneAttiva) {
                    
                    synchronized(animazione) {
                        try {
                            animazione.wait();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                
                currentTime = System.currentTimeMillis();

                if(currentTime - startTime >= (1000 / FPS)) {
                    startTime = currentTime;
                    anima();
                }
            } while(true);
        });

        animazione.start();
    }
} 