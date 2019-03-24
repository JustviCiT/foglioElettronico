import java.awt.*;
import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.table.*;

/**Modifica il valore della prima colonna consentendo l'allineamento centrale 
  * il colore di sfondo e il carattere in grassetto*/
public class CellMod extends JLabel implements TableCellRenderer {
  public static final DefaultTableCellRenderer Drend = new DefaultTableCellRenderer();

  public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column) {
    Component renderer = Drend.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    ((JLabel) renderer).setOpaque(true);
    Color background;
    
      if (column == 0) {
        renderer.setFont(new Font("SansSerif", Font.BOLD, 12));
        ((JLabel)renderer).setHorizontalAlignment(JLabel.CENTER);
        background = new Color(238, 238, 238);
      } else {
        background = null;
        ((JLabel)renderer).setHorizontalAlignment(JLabel.LEFT);
      }
    renderer.setBackground(background);
    return renderer;
  }
  
}