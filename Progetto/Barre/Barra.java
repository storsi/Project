package Progetto.Barre;

import javax.swing.JPanel;

import Progetto.Main.Interface.Animated;
import Progetto.Main.Interface.Clickable;

import java.awt.Color;
import java.awt.LayoutManager;

public abstract class Barra extends JPanel implements Animated, Clickable{

    private boolean animazioneAttiva;

    public Barra(Color background, LayoutManager layout, boolean animated) {
        
        super(layout);
        super.setBackground(background);
        

        animazioneAttiva = false;
        if(animated) setAnimationThread();
    }
    
    protected abstract void setUp();
    public abstract void btnIconClicked(BtnIcon btn);
    protected abstract void animaBarra();

    @Override
    public void afterClick() {
        animazioneAttiva = true;
        attivaThread();
    }

    public void stopAnimation() {
        animazioneAttiva = false;
    }

    @Override
    public void anima() {
        animaBarra();
    }

    @Override
    public boolean animationActive() {
        return animazioneAttiva;
    }
} 