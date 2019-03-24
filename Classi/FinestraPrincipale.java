import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableModel;

/**Main del programma */
public class FinestraPrincipale 
{
  public static void main (String args[]) 
   {
    /**Creo la base di dati*/
    db d =new Calcolo();
         
    /** Creo il modello, tabella e Pannello */
    TableModel dataModel = new Tabella(d);
    JTable t = new JTable(dataModel);
    Pannello PanTabella = new Pannello(d,t);
    
    /** creo oggetto della classe JFrame, imposto le dimensioni*/
    Frame fprima = new Frame("Foglio Elettronico - ",d,t);
    fprima.setBounds(300,150,900,500);
    Container cjf = fprima.getContentPane();
    cjf.setLayout(new BoxLayout(cjf, BoxLayout.Y_AXIS));
    cjf.add(PanTabella);
    /**Ascolto se si chiude la finesta chiudo l'applicazione, di default non faccio nulla*/
    fprima.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    fprima.addWindowListener(new CloseApp());
    fprima.pack();
    fprima.setVisible(true);
  }
  
}




    
    
    