package Progetto.DBM;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.ButtonGroup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.LayoutManager;

import Progetto.Barre.BtnText;
import Progetto.Barre.BtnText.Tipologia;
import Progetto.Main.Global;
import Progetto.Main.Strumenti.BarraDiSeparazione;
import Progetto.Main.Strumenti.DropdownMenu;
import Progetto.Main.Strumenti.Label;
import Progetto.Main.Strumenti.Panel;
import Progetto.Main.Strumenti.RadioButton;
import Progetto.Main.Strumenti.TextArea;

public class CreaTabella extends JPanel implements ActionListener{

    //VARIABILI GENERALI
    /**
     * Inizializzazione dei valori che indicheranno la dimensione del panel generale (la classe). GENERAL_HEIGHT è leggermente più 
     * piccolo perchè è presente il Label che descrive la scena che è alto esattamente (int)(Global.AVVISO_HEIGHT * 0.1)
     */
    private final int GENERAL_WIDTH = (int)(Global.FRAME_WIDTH * 0.7);
    private final int GENERAL_HEIGHT = (int)(Global.FRAME_HEIGHT * 0.72);

    /**
     * Inizializzazione dei valori che indicheranno la dimensione dei sottopanels che conterranno
     * le colonne già create e lo spazio per crearle
     */
    private final int PANEL_WIDTH = (int)(GENERAL_WIDTH * 0.46);
    private final int PANEL_HEIGHT = (int)(GENERAL_HEIGHT * 0.9);

    /**
     * Inizializzazione dei valori che indicheranno la dimensione dei Labels all'interno dei Panels.
     * LABEL_HEIGHT verrà poi ridimensionato a seconda delle necessità
     */
    private final int LABEL_WIDTH = PANEL_WIDTH;
    private final int LABEL_HEIGHT = 30;

    /**
     * Rispettivamente:
     * - Il colore destinato a tutti i background dei Panel
     * - Il colore destinato alle scritte di tutte le Label
     */
    private final Color BACKGROUND_COLOR = Global.COLORE_AVVISO;
    private final Color FOREGROUND_COLOR = Color.WHITE;

    /**
     * Spessore delle istanze della classe BarraDiSeparazione (Progetto.Main.Strumenti.BarraDiSeparazione)
     */
    private final int BDS_WIDTH = (int)(LABEL_WIDTH * 0.1);

    /**
     * Rispettivamente:
     * - Diensione del panel generale (la classe) che è composto dai valori di GENERAL_WIDTH e GENERAL_HEIGHT
     * - Dimensione dei sottopanels che è composto dai valori di PANEL_WIDTH e PANEL_HEIGHT
     * - Dimesnione dei sottopanels che conterranno gli elementi per il raggiungimento del loro scopo
     * - Dimensione dei Label che indicheranno la sezione
     * - Dimensione del TextArea utile per l'inserimento del nome della colonna
     * - Dimesnione della BarraDiSeparazione che separa il titolo con il sottopanel
     * - Dimensione del Panel che conterrà il BtnText che creerà la tabella
     * - Dimensione dei Panels che conterranno le informazioni per la creazione della colonnas
    */
    private Dimension 
    dim_generale, dim_pannelli, dim_sottopannelli, dim_label, dim_creaTabella;

    private BarraDiSeparazione bds;


    //********************************** VARIABILI CREA_COLONNA **********************************//
    
    private final int SOTTOPANNELLI_HEIGHT = (int)(PANEL_HEIGHT * 0.69);

    private Sottopannello sp_creaColonna, sp_default, sp_check, sp_collate;

    /**
     * Rispettivamente:
     * - Il sottopanel che si occuperà di creare le colonne. Al suo interno conterrà la label lbl_creaColonna
     *   ed un ulteriore panel che conterrà i RadioButtons per scegliere le caratteristiche della colonna
     * - Panel che conterrà i pulsanti per la Creazione della colonna, per il reset delle informazioni e per proseguire nel processo
     */
    private Panel pnl_creaColonna, pnl_creaColonnaBtnFinali;

    /**
     * Rispettivamente:
     * - Pulsante con lo scopo di confermare la creazione della colonna, di inserire la colonna nel Panel 
     *   per le colonne già create e di resettare successivamente le sue informazioni, preparando 
     *   pnl_creaColonnaInformazioni per una nuova colonna
     * - Pulsante con lo scopo di resettare tutte le informazioni che abbiamo inserito sulla colonna corrente
     *   senza però crearla
     */
    private BtnText btn_creaColonna, btn_resetColonna;

    //********************************* VARIABILI MOSTRA_COLONNE *********************************//
    /**
     * Rispettivamente:
     * - Il Panel che conterrà tutti gli elementi necessari a mostrare le colonne già create
     * - Il Panel figlio del primo che conterrà l'elenco delle colonne create
     * - Panel che conterrà il pulsante per la creazione della tabella (utile per riempire tutta la lunghezza del Panel
     *   genitore per inserire correttamente il bds_colonneBassa)
     */
    private JPanel pnl_colonne, pnl_mostraColonne, pnl_creaTabella;

    /*
     * Label che indicherà la sezione. Mostrerà la scritta: "COLONNE"
     */
    private JLabel lbl_colonne;

    /**
     * Barra che separa il titolo (lbl_colonne) con il Panel (pnl_mostraColonne)
     */
    

    //********************************* VARIABILI CREA_TABELLA *********************************//

    /**
     * Istanza della classe BtnText (Progetto.Barre.BtnText) che, appunto, creerà la tabella
     */
    private BtnText btn_creaTabella;
    
    public CreaTabella() {
        dim_generale = new Dimension(GENERAL_WIDTH, GENERAL_HEIGHT);
        dim_pannelli = new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
        dim_sottopannelli = new Dimension(PANEL_WIDTH, SOTTOPANNELLI_HEIGHT);
        dim_label = new Dimension(LABEL_WIDTH, LABEL_HEIGHT);
        dim_creaTabella = new Dimension(PANEL_WIDTH, (int)(PANEL_HEIGHT * 0.2));

        bds = new BarraDiSeparazione(BDS_WIDTH, FOREGROUND_COLOR, true);

        setPreferredSize(dim_generale);
        setBackground(BACKGROUND_COLOR);
        setOpaque(false);
        setLayout(Global.FL_L_15_10);
        
        /*
         * Creazione del Panel dedicato alla creazione delle colonne
         */
        setUpCreazioneColonne();

        add(pnl_creaColonna);
        
        /*
         * Creazione del Panel dedicato alle colonne già create
         */
        setUpColonne();

        add(pnl_colonne);

        setVisible(false);

    }

    private void setUpColonne() {
        /**
         * Creazione del Panel che conterrà tutti gli elementi per mostrare le colonne create
         */
        pnl_colonne = new JPanel(Global.FL_C_10_10);
        pnl_colonne.setPreferredSize(dim_pannelli);
        pnl_colonne.setBackground(BACKGROUND_COLOR);

        lbl_colonne = new JLabel("COLONNE:", SwingConstants.CENTER);
        lbl_colonne.setPreferredSize(dim_label);
        lbl_colonne.setForeground(FOREGROUND_COLOR);
        lbl_colonne.setFont(Global.FONT_GRANDE);

        pnl_mostraColonne = new JPanel(Global.FL_C_10_10);
        pnl_mostraColonne.setPreferredSize(dim_sottopannelli);
        pnl_mostraColonne.setBackground(BACKGROUND_COLOR);

        pnl_creaTabella = new JPanel(Global.FL_C_10_10);
        pnl_creaTabella.setPreferredSize(dim_creaTabella);
        pnl_creaTabella.setBackground(BACKGROUND_COLOR);

        btn_creaTabella = new BtnText(Tipologia.CREA_TABELLA, this, "CREA TABELLA");
        btn_creaTabella.disableButton();

        pnl_creaTabella.add(btn_creaTabella);

        pnl_colonne.add(lbl_colonne);
        pnl_colonne.add(bds);
        pnl_colonne.add(Box.createVerticalStrut(30));
        pnl_colonne.add(pnl_mostraColonne);
        pnl_colonne.add(bds.clone());
        pnl_colonne.add(pnl_creaTabella);

    }

    private void setUpCreazioneColonne() {

        //Creazione dei Pannelli e Sottopannelli
        pnl_creaColonna = new Panel(Global.FL_C_10_10, dim_pannelli, BACKGROUND_COLOR);
        
        sp_creaColonna = new Sottopannello(Sottopannello.Tipologia.GENERALE, dim_sottopannelli, BACKGROUND_COLOR, Global.FL_C_10_10, this);
        sp_default = new Sottopannello(Sottopannello.Tipologia.DEFAULT, dim_sottopannelli, BACKGROUND_COLOR, Global.FL_C_10_10, this);
        sp_check = new Sottopannello(Sottopannello.Tipologia.CHECK, dim_sottopannelli, BACKGROUND_COLOR, Global.FL_C_10_10, this);
        sp_collate = new Sottopannello(Sottopannello.Tipologia.COLLATE, dim_sottopannelli, BACKGROUND_COLOR, Global.FL_C_10_10, this);
        
        pnl_creaColonnaBtnFinali = new Panel(Global.FL_C_10_10, dim_creaTabella, BACKGROUND_COLOR);

        //Creazione dei BtnTexts
        btn_creaColonna = new BtnText(Tipologia.CREA_COLONNA, this, "PROSEGUI");
        btn_resetColonna = new BtnText(Tipologia.RESET_COLONNA, this, "RESET");
        btn_resetColonna.disableButton();

        pnl_creaColonnaBtnFinali.add(btn_resetColonna);
        pnl_creaColonnaBtnFinali.add(btn_creaColonna);

        pnl_creaColonna.add(new Label("CREA COLONNE:", Global.FONT_GRANDE, dim_label, FOREGROUND_COLOR));
        pnl_creaColonna.add(bds.clone());
        pnl_creaColonna.add(Box.createVerticalStrut(30));
        pnl_creaColonna.add(sp_creaColonna);
        pnl_creaColonna.add(sp_default);
        pnl_creaColonna.add(sp_check);
        pnl_creaColonna.add(sp_collate);
        pnl_creaColonna.add(bds.clone());
        pnl_creaColonna.add(pnl_creaColonnaBtnFinali);
    }

    public void creaTabella() {
        //TODO popolare la funzione per la creazione della tabella
    }

    public void reset() {

        //TODO reset button
    }

    public void prosegui() {

        if(sp_creaColonna.isVisible()) {
            
            sp_creaColonna.setVisible(false);
            sp_default.setVisible(true);

        } else if(sp_default.isVisible()) {

            sp_default.setVisible(false);
            sp_check.setVisible(true);

        } else if(sp_check.isVisible()) {

            sp_check.setVisible(false);
            sp_collate.setVisible(true);

        } else {

            System.out.println("Creazione colonna avvenuta");

        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
}

class Sottopannello extends Panel {

    public enum Tipologia {
        GENERALE,
        DEFAULT,
        CHECK,
        COLLATE
    }

    private enum RadioNamesTyps {
        INTEGER,
        REAL,
        TEXT,
        BLOB
    }

    private enum RadioNamesBeh {
        PRIMARY_KEY,
        UNIUE,
        NOT_NULL,
        AUTO_INCREMENT,
        DEFAULT,
        CHECK,
        COLLATE
    }

    //Variabili final
    private final Color BACKGROUND_COLOR = Global.COLORE_AVVISO;
    private final Color FOREGROUND_COLOR = Color.WHITE;

    private final int GENERAL_WIDTH = (int)(Global.FRAME_WIDTH * 0.7);
    private final int GENERAL_HEIGHT = (int)(Global.FRAME_HEIGHT * 0.72);
    private final int PANEL_WIDTH = (int)(GENERAL_WIDTH * 0.46);
    private final int PANEL_HEIGHT = (int)(GENERAL_HEIGHT * 0.9);
    private final int LABEL_HEIGHT = 30;

    private final int MAX_CHARACTERS_LIMIT_TEXT_AREA = 23;

    //Variabili normali
    private CreaTabella ct;

    private Dimension dim_label, dim_textArea, dim_infoPanel;

    private TextArea ta_nomeColonna, ta_default;
    private Panel pnl_nomeColonna, pnl_tipoRadioButton, pnl_carattRadioButton, pnl_default, pnl_recapColonna;

    private RadioButton[] radioButtonsTyps, radioButtonsBeh;
    private ButtonGroup bg;

    private static String[] descRadioButtons = {
        "Permette l'inserimento dei soli valori numerici interi. <br> <br> Esclude: collate", 
        "Permette l'inserimento di numeri anche con una parte decimale, con un massimo di 2 cifre dopo la virgola. <br> <br> Esclude: collate | autoincrement", 
        "Rappresenta stringhe di caratteri ed include spazi, simboli, numeri (trattati come caratteri) e punteggiatura. <br> <br> Esclude: autoincrement",
        "È usato per memorizzare dati binari senza alcuna conversione o interpretazione, come immagini, file audio, file video e file binari. <br> <br> Esclude: collate | autoincrement | default",
        "Impone l'inserimento di un record unico e non nullo rispetto agli altri elementi della colonna. <br> <br> Impone: Unique | Not Null <br> Uso: tutte le tipologie",
        "Assicura che tutti i record di una colonna siano unici, senza duplicati. <br> <br> Impone: nessuna imposizione <br> Uso: tutte le tipologie",
        "Impedisce a questa colonna di possedere valori nulli. Ogni riga ha, quindi, un valore. <br> <br> Impone: Nessuna imposizione <br> Uso: tutte le tipologie",
        "Genera automaticamente valori sequenziali unici per la colonna. Ogni nuovo record riceve un valore maggiore del precedente. <br> <br> Impone: Primary Key <br> Uso: Integer",
        "Specifica un valore predefinito per una colonna se non viene fornito alcun valore durante l'inserimento di un record. <br> <br> Impone: nessuna imposizione <br> Uso: Integer | Real | Text",
        "impone un vincolo personalizzato su una colonna, assicurando che i dati inseriti soddisfino una determinata condizione <br> <br> Impone: nessuna imposizione <br> Uso: tutte le tipologie",
        "definisce la modalità di confronto delle stringhe per una colonna di tipo \"TEXT\", influenzando l'ordinamento e le ricerche. <br> <br> Impone: nessuna imposizione <br> Uso: text"
    };

    //Costruttore di Sottopannello visibile
    public Sottopannello(Tipologia tip, Dimension dim, Color bg, LayoutManager layout, CreaTabella ct) {
        super(layout, dim, bg);

        this.ct = ct;
        this.dim_label = new Dimension(PANEL_WIDTH, LABEL_HEIGHT);
        this.dim_textArea = new Dimension((int)(PANEL_WIDTH * 0.6), LABEL_HEIGHT);
        this.dim_infoPanel = new Dimension(PANEL_WIDTH, (int)(PANEL_HEIGHT * 0.178));

        switch (tip) {
            case GENERALE: setupGenerale();
            break;
            case DEFAULT: setupDefault();
            break;
            case CHECK: setupCheck();
            break;
            case COLLATE: setupCollate();
            break;
        }
    }

    //Funzioni per la creazione del sottopannello Generale
    private void setupGenerale() {

        this.pnl_nomeColonna = new Panel(Global.FL_C_0_0, dim_infoPanel, BACKGROUND_COLOR);
        this.pnl_tipoRadioButton = pnl_nomeColonna.clone();
        this.pnl_carattRadioButton = pnl_nomeColonna.clone();

        //Setup label e text area per l'inserimento del nome delle colonne
        pnl_nomeColonna.add(new Label("NOME COLONNA:", Global.FONT_MEDIO, dim_label, FOREGROUND_COLOR));

        ta_nomeColonna = new TextArea(Global.FONT_MEDIO, dim_textArea);
        ta_nomeColonna.setCharacterLimit(MAX_CHARACTERS_LIMIT_TEXT_AREA);

        pnl_nomeColonna.add(ta_nomeColonna);
        
        //Setup radioButtons per la tipologia e le caratteristiche delle colonne
        setupRadioButtons();


        add(pnl_nomeColonna);
        add(pnl_tipoRadioButton);
        add(pnl_carattRadioButton);
    }
    private void setupRadioButtons() {
        radioButtonsTyps = new RadioButton[RadioNamesTyps.values().length];
        radioButtonsBeh = new RadioButton[RadioNamesBeh.values().length];

        bg = new ButtonGroup();

        //Setup RadioButtons per la tipologia delle colonne
        pnl_tipoRadioButton.add(new Label("CARATTERISTICA:", Global.FONT_MEDIO, dim_label, FOREGROUND_COLOR));

        for(int i = 0; i < radioButtonsTyps.length; i++) {
            radioButtonsTyps[i] = new RadioButton(i, RadioNamesTyps.values()[i].toString().replace('_', ' '), descRadioButtons[i], bg, ct);

            pnl_tipoRadioButton.add(radioButtonsTyps[i]);
        }

        //Setup RadioButtons per le caratteristiche delle colonne
        pnl_carattRadioButton.add(new Label("TIPOLOGIA:", Global.FONT_MEDIO, dim_label, FOREGROUND_COLOR));

        for(int i = 0; i < radioButtonsBeh.length; i++) {
            if(i >= 4) radioButtonsBeh[i] = new RadioButton(i, RadioNamesBeh.values()[i].toString().replace('_', ' '), descRadioButtons[i + radioButtonsTyps.length], true, ct);
            else radioButtonsBeh[i] = new RadioButton(i, RadioNamesBeh.values()[i].toString().replace('_', ' '), descRadioButtons[i + radioButtonsTyps.length], false, ct);

            pnl_carattRadioButton.add(radioButtonsBeh[i]);
        }
    }


    //Funzioni per la creazione del sottopannello Default
    private void setupDefault() {

        setVisible(false);

        pnl_default = new Panel(Global.FL_C_0_0, dim_infoPanel, BACKGROUND_COLOR);
        
        ta_default = new TextArea(Global.FONT_MEDIO, dim_textArea);
        ta_default.setCharacterLimit(MAX_CHARACTERS_LIMIT_TEXT_AREA);

        pnl_default.add(new Label("VALORE DEFAULT", Global.FONT_MEDIO, dim_label, FOREGROUND_COLOR));
        pnl_default.add(ta_default);

        add(pnl_default);
    }

    //Funzioni per la creazione del sottopannello Check
    private void setupCheck() {
        
        setVisible(false);
    }

    //Funzioni per la creazione del sottopannello Collate
    private void setupCollate() {
        setVisible(false);

        add(new DropdownMenu(null, dim_label));
    }
}
