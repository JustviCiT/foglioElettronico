import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

/**Questo modulo si occupa della chiusura dell'applicazione*/
class CloseApp extends WindowAdapter {
  Finestre F= new Finestre();
  JTable t;

  public void windowClosing(WindowEvent e) {
        if (F.FinestraConferma("Sei Sicuro di voler chiudere l'applicazione?") == JOptionPane.YES_OPTION)
              System.exit(0);
      
     }  
    public void windowClosed(WindowEvent e) {}
    public void windowOpened(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
}