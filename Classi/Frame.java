import javax.swing.*;
import java.awt.event.KeyEvent;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;

/**Frame con tabella e menu*/
public class Frame extends JFrame implements ActionListener {
  static final long serialVersionUID = -1L;
  JMenu fileMenu;
  JMenuItem menuItem,subMenu;
  db d;
  JTable t;
  boolean aperto = false, exists;
  String tito;
  String nomefoglio="Foglio1.dat";
  String nomefoglioprec="Foglio1.dat";
  
  /** creo la barra dei menu e aggiungo le relative finestre*/
  private JMenuBar menuBar = new JMenuBar(); 
  Finestre F = new Finestre();
  
  /**Costruttore che prende il titolo della finestra la base di dati
    * e la tabella */
  public Frame(String titolo,db Dati,JTable tabella) {
    this.d=Dati;
    this.t=tabella;
    this.tito=titolo;
    final Finestre F=new Finestre(d,t);
    CloseApp C = new CloseApp();
    setTitle(tito+nomefoglio);
    setJMenuBar(menuBar); 
    
    /**prima etichetta*/
    fileMenu = new JMenu("File");
    menuBar.add(fileMenu);
    
    /**Menu Item che chiede conferma se il file non è stato salvato , 
      * cancella i dati e aggiorna la tabella*/
      menuItem = new JMenuItem("Nuovo...");
      menuItem.setMnemonic(KeyEvent.VK_N);
      menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
      fileMenu.add(menuItem);
      menuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
              /** Ferma l'editing in corso della cella se necessario*/
              if (t.isEditing()) t.getCellEditor().stopCellEditing();   
              /** Se la finestra ricerca non è gia aperta */
              if (!aperto) {
                    if (d.salvayea()) {
                          nomefoglio="Foglio1.dat";
                          setTitle(tito+nomefoglio); 
                          nomefoglioprec=nomefoglio;
                          d.clearall();
                          t.repaint();}
                    else {
                          if (F.FinestraConferma("Sei sicuro di aprire un nuovo file senza salvare ?")==JOptionPane.YES_OPTION)  
                          {
                                nomefoglio="Foglio1.dat";    
                                setTitle(tito+nomefoglio); 
                                nomefoglioprec=nomefoglio;
                                d.clearall();
                                t.repaint();
                          }else{
                                nomefoglio=F.FinestraSave("Salva con nome...");
                                if (nomefoglio.equals("")) nomefoglio=nomefoglioprec;
                                else {
                                      nomefoglioprec=nomefoglio;
                                      setTitle(tito+nomefoglio);
                                }
                                setTitle(tito+nomefoglio);   
                          }
                    }
              }
            }
      }); 
      
      
    /** Menu Item che apre una finestra per i file */
      menuItem = new JMenuItem("Apri");
      menuItem.setMnemonic(KeyEvent.VK_D);
      menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,ActionEvent.CTRL_MASK));
      fileMenu.add(menuItem);
      menuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                 /** Ferma l'editing in corso della cella se necessario*/
                 if (t.isEditing()) t.getCellEditor().stopCellEditing();
                  /** Se la finestra ricerca non è gia aperta */
                 if (!aperto) {
                       if (d.salvayea()) {
                             nomefoglio=F.FinestraOpen("Apri...");
                             if (nomefoglio.equals("")) nomefoglio=nomefoglioprec;
                             else {
                                   nomefoglioprec=nomefoglio;
                                   setTitle(tito+nomefoglio);
                             }
                             setTitle(tito+nomefoglio);
                       }else {
                             if (F.FinestraConferma("Sei Sicuro di voler aprire un file senza salvare ?") ==JOptionPane.YES_OPTION) {
                                   nomefoglio=F.FinestraOpen("Apri...");
                                   if (nomefoglio.equals("")) nomefoglio=nomefoglioprec;
                                   else {
                                         nomefoglioprec=nomefoglio;
                                         setTitle(tito+nomefoglio);
                                   }
                                   setTitle(tito+nomefoglio);
                             } 
                 }
                 }
            }
      });

      /** Menu Item che apre una finestra per la ricerca all'interno 
        * della tabella */
      menuItem = new JMenuItem("Ricerca...");
      menuItem.setMnemonic(KeyEvent.VK_R);
      menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,ActionEvent.CTRL_MASK));
      fileMenu.add(menuItem);
      menuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
              /** Ferma l'editing in corso della cella se necessario*/
              if (t.isEditing()) t.getCellEditor().stopCellEditing(); 
              if (!aperto) {
                    F.FinestraRicerca().addWindowListener(new WindowAdapter() {
                          public void windowClosing(WindowEvent e) {
                                aperto=false;
                          }
                          public void windowOpened(WindowEvent e) {
                                aperto=true;
                          }
              });}
              }
      }); 
      
      /** Menu Item che salva la tabella se non è gia stata salvata */
      fileMenu.addSeparator();
      menuItem = new JMenuItem("Salva");
      menuItem.setMnemonic(KeyEvent.VK_S);
      menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
      fileMenu.add(menuItem);
      menuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
              /** Ferma l'editing in corso della cella se necessario*/
              if (t.isEditing()) t.getCellEditor().stopCellEditing();
               /** Se la finestra ricerca non è gia aperta */
              if (!aperto) {
               /**Se il file è gia stato salvato */
                    if (d.salvayea()) return;
                    else {
                          File ff= new File("..\\"+nomefoglio);
                          exists = (ff).exists();
                          if (exists) 
                                F.Save(ff);
                          else {
                                nomefoglio=F.FinestraSave("Salva...");
                                if (nomefoglio.equals("")) nomefoglio=nomefoglioprec;
                                else {
                                      nomefoglioprec=nomefoglio;
                                      setTitle(tito+nomefoglio);
                                }
                                setTitle(tito+nomefoglio); 
                          }
                    }
              }
            }});
      
      /** Menu Item che salva il file con la scelta del nome del file */
      menuItem = new JMenuItem("Salva con nome...");
      menuItem.setMnemonic(KeyEvent.VK_B);
      menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,ActionEvent.CTRL_MASK));
      fileMenu.add(menuItem);
      menuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                 /** Ferma l'editing in corso della cella se necessario*/
                 if (t.isEditing()) t.getCellEditor().stopCellEditing();
                 /** Se la finestra ricerca non è gia aperta */
                 if (!aperto) {
                       nomefoglio=F.FinestraSave("Salva con nome...");
                       if (nomefoglio.equals("")) nomefoglio=nomefoglioprec;
                       else {
                             nomefoglioprec=nomefoglio;
                             setTitle(tito+nomefoglio);
                       }
                       setTitle(tito+nomefoglio); 
                 }   
            }
      });
    /*Fine Menu*/
  }

  public void actionPerformed(ActionEvent e) {
    if (F.FinestraConferma("Sei sicuro di voler chiudere senza salvare ")== JOptionPane.YES_OPTION)  
       System.exit(0) ;
  }
}
