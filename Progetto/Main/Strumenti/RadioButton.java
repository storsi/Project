package Progetto.Main.Strumenti;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import java.awt.Color;
import java.awt.event.ActionListener;

import Progetto.Main.Global;
import Progetto.Main.Interface.Hover;

public class RadioButton extends JRadioButton implements Hover{
    
    /**
     * Valore intero destinato all'interfaccia Hover (Progetto.Main.Interface.Hover) ed indica il tempo che
     * il thread deve aspettare prima di richiamare la funzione hover()
     */
    private final int MILLISECONDS_FOR_HOVER = 800;
    /**
     * Valore intero che identifica univocamente il RadioButton. Verrà utilizzato principalmente per 
     * identificarli nell'array della classe CreaTabella (Progetto.DBM.CreaTabella)
     */
    private int indice;

    /**
     * Istanza della classe Informazione (Progetto.Main.Strumenti.Informazione) che permetterà di mostrare
     * a schermo un PopUp che spieghi lo scopo di ogni RadioButton
     */
    private Informazione info;

    /**
     * Rispettivamente:
     * - Stringa che indica ciò che l'utente vedrà quando dovrà scegliere il RadioButton
     * - Stringa che indica il testo che verrà inserito nella variabile info
     */
    private String text, messaggio;

    /**
     * Rispettivamente:
     * - Variabile booleana con lo scopo di tenere traccia dei RadioButton che necessitano di aggiungere 
     *   ulteriori informazioni riguardante la caratteristica da loro scelta per la colonna. Attivo (quando true)
     *   disattivo (quando false)
     * - Variabile booleana con lo scopo di gestire l'interfaccia Hover (Progetto.Main.Interface.Hover) che Permette
     *   di attivare (quando true) o disattivarla (quando false)
     */
    private boolean needWord, hoverActive;

    /**
     * Costruttore che inizializza le variabili principali, come info a cui è stato già assegnata la stringa
     * "messaggio" e la variabile booleana hoverActive inizializzata a false
     * @param indice
     * @param text
     * @param messaggio
     * @param needWord
     */
    public RadioButton(int indice, String text, String messaggio, boolean needWord, ActionListener al) {
        /**
         * Assegnazione delle variabili dei parametri alle variabili della classe
         */
        this.indice = indice;
        this.text = text;
        this.messaggio = messaggio;
        this.needWord = needWord;

        /**
         * Si crea l'istanza della classe Informazione (Progetto.Main.Strumenti.Informazione) ed inserisco il 
         * suo messaggio 
         */
        this.info = new Informazione();
        this.info.setTesto(this.messaggio);

        /**
         * Inizializzo a false la variabile booleana hoverActive poichè va attivata solo quando si vuole attivare
         * il thread dell'interfaccia Hover (Progetto.Main.Interfase.Hover) e quindi quando il cursore passa
         * sopra questo RadioButton
         */
        this.hoverActive = false;

        /**
         * Setto l'actionListener
         */
        addActionListener(al);

        setUpRadioButton();
    }

    /**
     * Costruttore destinato a tutti i RadioButton che devono essere contenuti in un ButtonGroup
     * @param indice
     * @param text
     * @param messaggio
     * @param bg
     */
    public RadioButton(int indice, String text, String messaggio, ButtonGroup bg, ActionListener al) {
        /**
         * Assegnazione delle variabili dei parametri alle variabili della classe
         */
        this.indice = indice;
        this.text = text;
        this.messaggio = messaggio;
        
        /**
         * Possedendo come parametro ButtonGroup questo costruttore è destinato ai RadioButton
         * per la tipologia, quindi needWord è di default a false poichè non c'è mai bisogno
         * di aggiungere informazioni
         */
        this.needWord = false;

        /**
         * Si crea l'istanza della classe Informazione (Progetto.Main.Strumenti.Informazione) ed inserisco il 
         * suo messaggio 
         */
        this.info = new Informazione();
        this.info.setTesto(this.messaggio);

        /**
         * Inizializzo a false la variabile booleana hoverActive poichè va attivata solo quando si vuole attivare
         * il thread dell'interfaccia Hover (Progetto.Main.Interfase.Hover) e quindi quando il cursore passa
         * sopra questo RadioButton
         */
        this.hoverActive = false;

        /*
         * Aggiungo il RadioButton al ButtonGroup
         */
        bg.add(this);

        /**
         * Setto l'actionListener
         */
        addActionListener(al);

        setUpRadioButton();
    }

    /**
     * Funzione richiamata subito dal costruttore per poter creare e setuppure tutte le caratteristiche 
     * del RadioButton
     */
    private void setUpRadioButton() {
        
        setText(text);
        setFont(Global.FONT_PICCOLO);
        setOpaque(false);
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setHoverThread();
        addMouseListener(this);
        
        /**
         * Per scelta personale ho inserito che il RadioButton di indice 0 (Quello per la tipologia INTEGER)
         * sia di default già selezionato. in questo modo si evitano dei controlli quando si va a creare la
         * colonna
         */
        if(indice == 0) seleziona();
        else deseleziona();
    }

    /**
     * Funzione che ritorna il valore dell'indice del RadioButton
     * @return il valore dell'indice del RadioButton
     */
    public int getIndice() {
        return indice;
    }

    /**
     * Funzione che ritorna il valore del testo del RadioButton
     * @return il valore del testo del RadioButton
     */
    public String getText() {
        return text;
    }

    /**
     * Funzione che ritorna il valore della variabile booleana needWord che indica se il RadioButton necessiti
     * di ulteriori informazioni quando selezionato
     * @return il valore della variabile booleana needWord
     */
    public boolean needWord() {
        return needWord;
    }

    /**
     * Funzione che va richiamata quando si seleziona un RadioButton
     */
    public void seleziona() {
        /**
         * Questa funzione è richiamata poichè ci sono dei casi in cui è necessaria la sua presenza (come
         * per la selezione di default del RadioButton INTEGER quando creato), ma per gli altri casi è
         * fuorviante
         */
        setSelected(true);

        /**
         * Qui cambiamo l'icona per inserire quella adeguata al fatto che è stata selezionata
         */
        setIcon(Global.getIcon("RadioButtonSelected.png", 10));
    }

    /**
     * Funzione che va richiamata quando si deseleziona un RadioButton
     */
    public void deseleziona() {

        /**
         * Qui cambiamo l'icona per inserire quella adeguata al fatto che è stata deselezionata
         */
        setIcon(Global.getIcon("RadioButtonNotSelected.png", 10));
    }

    /**
     * Funzione che serve ad abilitare questo RadioButton e quindi permettere all'utente di selezionarlo
     */
    public void abilita() {
        /**
         * Si abilita il radioButton
         */
        setEnabled(true);

        /**
         * Si modifica l'icona che si vedrà di fianco al testo del RadioButton
         */
        setIcon(Global.getIcon("RadioButtonNotSelected.png", 10));
    }

    /**
     * Funzione che serve a disabilitare questo RadioButton e quindi impedisce all'utente di selezionarli
     */
    public void disabilita() {
        /**
         * Si "deseleziona" il RadioButton in ogni caso (anche se non era selezionato)
         */
        setSelected(false);
        /**
         * Si disabilita il radioButton
         */
        setEnabled(false);
    }

    /**
     * Funzione richiamata dall'interfaccia Hover (Progetto.Strumenti.Hover) per permettere al suo thread
     * di eseguire la funzione hover()
     * @return il valore della variabile booleana hoverActive
     */
    @Override
    public boolean hoverActive() {
        return hoverActive;
    }

    /**
     * Funzione richiamata dall'interfaccia Hover (Progetto.Strumenti.Hover) per capire quanto tempo
     * dall'inizio dell'hover il thread deve richiamare la funzione hover()
     * @return il valore della variabile intera MILLISECONDS_FOR_HOVER
     */
    @Override
    public int getMilliseconds() {
        return MILLISECONDS_FOR_HOVER;
    }

    /**
     * Funzione che ha lo scopo di eseguire le azioni desiderate durante l'hover
     */
    @Override
    public void hover() {
        /**
         * Rendiamo visibile l'istanza info
         */
        info.mostra();

        /**
         * Impostiamo questa variabile a false poichè altriemnti il thread dell'interfaccia 
         * Hover (Progetto.Main.Interface.Hover) continuerebbe a richiamare questa funzione
        */
        hoverActive = false;
    }

    /**
     * Funzione richiamata appena il cursore passa sopra questo RadioButton
     */
    @Override
    public void inHover() {
        /**
         * Assegnamo alla variabile il valore true poichè così il thread dell'interfaccia Hover
         * (Progetto.Main.Interface.Hover) può richiamare la funzione hover()
         */
        hoverActive = true;

        /**
         * Funzione della classe Informazione (Progetto.Main.Strumenti.Informazione) che, se non già trovato,
         * trova la classe genitore Panel (Progetto.Main.Panel) in modo da inserire l'elemento info
         */
        info.getPanel(this);

        /**
         * Viene richiamata la funzione dell'interfaccia Hover (Progetto.Main.Interface.Hover) che notificherà
         * il suo thread
         */
        attivaHover();
    }

    /**
     * Funzione richiamata appena il cursore esce dall'hover su questo RadioButton
     */
    @Override
    public void outHover() {
        /**
         * Impostiamo questa variabile a false poichè altriemnti il thread dell'interfaccia 
         * Hover (Progetto.Main.Interface.Hover) continuerebbe a richiamare questa funzione
        */
        hoverActive = false;

        /**
         * Rendiamo invisibile l'istanza della classe Informazione (Progetto.Main.Strumenti.Informazione)
         */
        info.nascondi();
    }
}
