package Progetto.Main.Strumenti;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Label extends JLabel{
    
    public Label(String text, boolean adaptSizeToText, Font font) {
        setText(text);
        setFont(font);

        if(adaptSizeToText) setPreferredSize(new Dimension(text.length() * font.getSize(), font.getSize()));
    }

    public Label(String text, Font font, Dimension dim, Color foreground) {
        setText(text);
        setFont(font);
        setPreferredSize(dim);
        setForeground(foreground);
        setHorizontalAlignment(SwingConstants.CENTER);
    }
}
