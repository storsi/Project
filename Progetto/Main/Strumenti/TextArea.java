package Progetto.Main.Strumenti;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

import Progetto.Main.Global;

public class TextArea extends JTextArea implements KeyListener{

    private int limit;
    
    public TextArea(Font font) {
        changeFont(font);
        setWrapStyleWord(true);
        setLineWrap(true);
    }

    public TextArea(Font font, Dimension dim) {
        changeFont(font);
        changeSize(dim);
        setWrapStyleWord(true);
        setLineWrap(true);

        setBorder(BorderFactory.createLineBorder(Global.COLORE_PRIMA_BARRA, 3));
    }

    public void setCharacterLimit(int limit) {
        this.limit = limit;

        addKeyListener(this);
    }

    public void changeFont(Font font) {
        setFont(font);
    }

    public void changeSize(Dimension dim) {
        setPreferredSize(dim);
    }

    public void changeColor(Color background, Color accessories) {
        if(background != null) {
            setOpaque(true);
            setBackground(background);
        }

        if(accessories != null) {
            setCaretColor(accessories);
            setForeground(accessories);
        }
    }

    public void createBorder(Color colore, int thickness) {
        setBorder(BorderFactory.createLineBorder(colore, thickness));
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(getText().length() >= limit) {
            e.consume();
            setText(getText().substring(0, limit));
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
