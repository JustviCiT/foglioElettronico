import javax.swing.table.AbstractTableModel; 

/**Classe Tabella che definisce il modello*/
public class Tabella extends AbstractTableModel {
  static final long serialVersionUID = -1L;
  db d;
  
  /*Costruttore*/
  public Tabella (db data) {
    this.d=data;
  }
  
  /**Ritorna il numero delle colonne*/
  public int getColumnCount() { return d.retCol(); } 
  
  /**Ritorna il numero delle Righe*/
  public int getRowCount() { return d.retRig();} 
  
  /**Ritorna il valore della cella*/
  public Object getValueAt(int row, int col)  {  
    if (col==0) return (Integer)row+1; 
    return d.retDati(row,col);  
  } 
  
  /*Ritorna il nome della colonna */
  public String getColumnName(int col) { 
    return d.retColNome(col); 
  } 
  
  /** ritorna il tipo dei valori, viene fatto col +1 per mantenere 
    * invariata la prima colonna che contiene i numeri di riga*/
  public Class getColumnClass(int col) { 
    return getValueAt(0, col+1).getClass();
  }
  
  /* Setta i valori sulla tabella*/
  public void setValueAt(Object value, int row, int col) {
    d.writedata((String)value,row,col);
    //fireTableCellUpdated(row, col);
    fireTableDataChanged();
  }
  
  /* Specifica se le celle sono editabili*/
  public boolean isCellEditable(int row, int col) 
  { 
    if (col==0)  return false; 
    return true;
  } 

} 