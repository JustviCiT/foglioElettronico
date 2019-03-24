import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

/**Pannello generale che contiene la tabella */
public class Pannello extends JPanel{

  static final long serialVersionUID = -1L;
  JTable t;
  db da;
  
  public Pannello(db Dati,JTable table) { 
    this.da = Dati;
    this.t=table;
   
   /**Posiziono la tabella, setto la dimensione delle colonne
     * aggiungo la tabella ad uno scrollpane e non permetto 
     * lo spostamento delle colonne*/
    setLayout(new BorderLayout());
    t.getColumnModel().getColumn(0).setPreferredWidth(67);
    JScrollPane scrollpane = new JScrollPane(t);
    t.getTableHeader().setReorderingAllowed(false);

    /** Personalizzazione Colonna dei numeri e aggiunta
      * ascoltatore del mouse*/
    t.setDefaultRenderer(Object.class, new CellMod()); 
    EvenTopo mlistener = new EvenTopo(t,da);
    t.addMouseListener(mlistener);
    
    /**Permette di non adattarsi creando cosi la barra orizzontale */
    t.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    add(scrollpane); 
  }
} 