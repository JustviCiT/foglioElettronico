import java.util.Stack;
import java.awt.*;
import javax.swing.*;
import java.util.StringTokenizer;

/**Estende DB permette la valutazione delle celle */
public class Calcolo extends db{
  private int num;
  private String valore;
  private int conta=0;
  
  /**La classe stack rappresenta oggetti in una struttura LiFO
    *dove è possibile fare operazioni di push,pop*/       
  StackArray op = new StackArray();
  
  public Calcolo(){super();}
  
  /**Prende la stringa iniziale e la valuta facendo prima la conversione 
    * postfissa , vengono valutate solo stringhe che iniziano per =*/
  public String calc(String v){
    this.valore = "";
    if (v==null || v.equals("")) return valore;
    else {
      v = v.trim();
      try{
        if (v.startsWith("=")) return valuta(convPost(v.substring(1)));
      }catch (Exception e){return"ERR";}
    }
    return v;
  }
  
  /**Aggiorna la stringa*/ 
  public void refval(String v){
     this.valore=this.valore+v+" ";
  }
  
  /**Calcola la priorita in base all'operatore immesso*/
  public int priorita(String val){
    int pri=0;
    if (val.equals("*") || val.equals("/") || val.equals("^")) 
      pri=2;
    else { if (val.equals("+") || val.equals("-")) pri = 1;}
    return pri;
  }
  
  /** la funzione converte la stringa in una stringa Postfissa facile 
   * da analizzare per un calcolatore.
   * StringTokenizer permette di esaminare la stringa in input e dividerla in token,
   * ogni token termina con uno dei caratteri nel secondo argomento
   * il terzo argomento permette di considerare anche i delimitatori come 
   * caratteri utili*/
  public String convPost(String v){  
    
    StringTokenizer token = new StringTokenizer(v, "()+-/*^",true);
    
    while(token.hasMoreTokens())
    {
      String str = token.nextToken().trim();
      if(str.equals("+") || str.equals("-"))
      {
        gotOper(str, 1);
      }
      else if(str.equals("/")|| str.equals("*")|| str.equals("^"))
      {
        gotOper(str, 2);
      }
      else if(str.equals(")"))
      {
        gotParen();
      }
      else if(str.equals("("))
      {
        op.push(str);
      }
      else
      {
        refval(str);
      }
    }
    while (!op.isEmpty()) {
      refval(op.pop());
    }
    return this.valore;
  }
  
  /** Decido la sorte dell'operatore se la priorita dell'operatore sullo stack
    * e' maggiore uguale lo faccio uscire finche non ne trovo uno minore 
    * o finche non finisce lo stack*/
  public void gotOper(String opThis, int prec) {
    while (!op.isEmpty()) {
      String oTop = op.pop();
      if (oTop.equals("(")) {
        op.push(oTop);
        break;
      }//operatore
      else { if (priorita(oTop) < prec) { 
        op.push(oTop); 
        break;
      } else{refval(oTop);}
      } 
    }
    op.push(opThis);
  }
  /**Estraggo elementi dallo stack finche non trovo una parentesi " ( " 
    * e aggiorno la stringa*/
  public void gotParen(){ 
    while (!op.isEmpty()) {
      String chx = (String) op.pop();
      if (chx.equals("(")) break;
      else
        refval(chx); 
    }
  }
  
  /**Controllo se la stringa immessa e' un numero */
  public boolean checkNum(String in) {
    try {
      Double.parseDouble(in);
    } catch (NumberFormatException ex) {return false;
    }       
    return true;
  } 
  
  /**Valuto la stringa manipolata da convpost, se non è possibile valutarla 
    * ritorna ERR in tutti i casi*/
  public String valuta(String v){
    double risultato=0;
    int riga=retRig();
    int col=retCol();
    String colonna= null;
    StringTokenizer token2 = new StringTokenizer(v, " +-/*^",true);
    StackArray opval = new StackArray();
    
    while(token2.hasMoreTokens())
    {
      String str = token2.nextToken();
      //System.out.print(str+"-");
      if(str.equals(" ")|| str.equals("")) { 
        continue;
      }
      else if(str.equals("+"))
      {
        double parte2 = Double.parseDouble(opval.pop());
        double parte1 = Double.parseDouble(opval.pop());
        risultato = parte1 + parte2;
        opval.push(Double.toString(risultato));
      }
      else if(str.equals("-") )
      {
        double parte2 = Double.parseDouble(opval.pop());
        double parte1 = Double.parseDouble(opval.pop());
        risultato = parte1 - parte2;
        opval.push(Double.toString(risultato));
      }
      else if(str.equals("*"))
      {
        double parte2 = Double.parseDouble(opval.pop());
        double parte1 = Double.parseDouble(opval.pop());
        risultato = parte1 * parte2;
        opval.push(Double.toString(risultato));
      }
      else if(str.equals("/") )
      {
        double parte2 = Double.parseDouble(opval.pop());
        double parte1 = Double.parseDouble(opval.pop());
        risultato = parte1 / parte2;
        opval.push(Double.toString(risultato));
      }
      else if(str.equals("^"))
      {
        double parte2 = Double.parseDouble(opval.pop());
        double parte1 = Double.parseDouble(opval.pop());
        risultato = Math.pow(parte1,parte2);
        opval.push(Double.toString(risultato));
      }
      else
      {
        /**controlli delle colonne*/
        if(checkNum(str)) opval.push(str);
        else {
            int i=1;
            while(i<str.length()){
                 if (checkNum(str.substring(i))) {
                    colonna=str.substring(0,i);
                    riga=Integer.parseInt(str.substring(i));
                    break;
                   }else i++;
          }//fine while
          
          /**controllo se la colonna esiste*/
          col=retColNum(colonna);
          if (col==-1) return "ERR";
          else{
            if (riga<=retRig()) {
              conta++;
              if(conta <0) conta= 0;
              if(conta >150) {conta=0; return "ERR";}
              str=(String) retDati(--riga,col);
              if (checkNum(str))  {opval.push(str);conta--;}
              else { return "ERR";}
            }
            else return "ERR";  
          }//fine else esterno
        } 
      }//fine ultimo else
    }//fine while generale
    return opval.pop();
  }//fine valuta

  /**Ritorna i dati di una certa riga e colonna con il calcolo */
  public String retDati(int row,int col) {
    String temp = calc(super.retDati(row,col));
    if (temp.endsWith(".0")) return temp.substring(0,temp.length()-2);
    else return temp;
  } 
}




