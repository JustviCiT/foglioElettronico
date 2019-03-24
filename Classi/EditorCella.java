import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

/**Definisce il renderer della cella per visualizzare il calcolo e la modifica 
  * della cella stessa*/
class EditorCella extends AbstractCellEditor implements TableCellEditor {
      
      db d;
      public EditorCella(db database){
            this.d=database;
      }
      
  JComponent component = new JTextField();
  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,int rowIndex, int vColIndex) {
      db c = new db(d.Dati()); 
      ((JTextField) component).setText(c.retDati(rowIndex,vColIndex));
      ((JTextField) component).setOpaque(false);
      ((JTextField) component).setBorder(BorderFactory.createEmptyBorder());

    return component;
  }

  public Object getCellEditorValue() {
    return ((JTextField) component).getText();
  }
}
