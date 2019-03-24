import javax.swing.*;
import java.awt.*;
import java.awt.Point;
import java.awt.event.*;
import javax.swing.table.*;

/**Definisce cosa fare con il click del mouse rendendo possibile la modifica 
  * con EditorCella*/
public class EvenTopo extends JLabel implements MouseListener {
  JTable t;
  db da;
  int clicks=0;
  
  public EvenTopo(JTable tab,db d){
    this.t=tab;
    this.da = d;
    
  }

    public void mousePressed(MouseEvent e) {
     Point p = e.getPoint();
      int row = t.rowAtPoint(p);
      int col = t.columnAtPoint(p);
      clicks = e.getClickCount();
      if (row == -1 || col == -1 || col == 0) { return;}
      else{  if (clicks>1) { t.setDefaultEditor(Object.class, new EditorCella(da));}
            else if (t.isEditing()) t.getCellEditor().stopCellEditing();
      }
    }

    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}

   
}