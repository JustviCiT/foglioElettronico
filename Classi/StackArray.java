import java.awt.*;
import java.util.StringTokenizer;
import java.util.Stack;

/**Stack usato per la valutazione e conversione delle stringhe */
public class StackArray
{
  final static int MaxDim = 100;
  private String[] elementi;
  private int pos;
  
  /**Crea uno stack di dimensioni MaxDim*/
  public StackArray(){
    elementi = new String[MaxDim];
    pos= -1;
  }
  
  /**true= lo stack e' vuoto, false altrimenti*/
  public boolean isEmpty(){
    return (pos ==-1);
  }
  
  /**Inserisce la stringa nello stack*/
  public void push (String o){
    elementi[++pos] = o;
  }
  
  /**Prende l'elemento in cima allo stack*/
  public String pop(){
    String tmp = elementi[pos];
    pos--;
    return tmp;
  }

}