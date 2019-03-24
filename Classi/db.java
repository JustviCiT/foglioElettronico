import java.awt.*;
import javax.swing.*;
/**Base di dati del programma*/

public class db {
     private int MassimeColonne = 100;
     private int MassimeRighe = 200;
     private String[] columnNames = new String[MassimeColonne];
     private String[][] Dati = new String[MassimeRighe][MassimeColonne+1]; 
     /**Definisce se è stato salvato o meno il foglio*/
     boolean salvato = false;
     
     /**costruttore che prende i Dati*/
     public db(String [][] da){
           this.Dati = da;
     }
     
     /**secondo costruttore che iniziallizza l'array che contiene i nomi delle 
       * colonne e inizializza la tabella*/
     public db(){
        char let= 'A';
        int j=0;
        for (int i=1; i < MassimeColonne; i++){
        if (i>26) {
           if (columnNames[i-1].endsWith("Z")) { let= 'A';j++;}
           columnNames[i]= columnNames[j]+Character.toString(let);
          let++;}
         else {columnNames[i]= Character.toString(let);      let++;}
          } 
         clearall();
     }
     
     /**Utilizzato per la scrittura dei dati da file*/
     public String [][] Dati(){
       salvato = true;
      return Dati;
     }
     
     /**Utilizzato per la lettura dei dati su file*/
     public void Dati(String[][] dat) {
       Dati=dat;
       salvato = true;
     }
     
     /**Definisce se il file è salvato o meno*/
     public boolean salvayea(){
       return salvato;
     }
     
     /** Ritorna il numero di colonne*/
     public int retCol() {
       return MassimeColonne;
     }
     
     /** Ritorna il numero di righe*/
     public int retRig() {
       return MassimeRighe;
     }
     
     /** Cerca la stringa @param cosa, e inserisce in una lista il
       * numero di riga e colonna se la sottostringa cercata corrisponde */
     public Lista Cerca(String cosa){
        cosa = cosa.toLowerCase();
        boolean meccia=false;
        Lista l = new Lista();
        if (cosa.equals("")) return l;
       for(int i=0; i<MassimeRighe;i++){
            for(int j=1; j<MassimeColonne; j++){
                  meccia = retDati(i,j).toLowerCase().matches("(.*)"+cosa+"(.*)");
                     if (meccia){ 
                           l.insertfine(i,j); 
                     }
                 }
       }
          return l;
     }
     
     /**Inizializza tutta la tabella*/
     public void clearall(){
          for(int i=0; i<MassimeRighe;i++){
            for(int j=1; j<MassimeColonne; j++){
              Dati[i][j]="";}
          }
          salvato=false;
     }
     
     /**Ritorna i dati di una certa riga , colonna*/
     public String retDati(int row,int col) {
       return Dati[row][col];
     }
     
     /**Ritorna il nome della colonna*/
     public String retColNome(int col){
      return columnNames[col];
     }
     
     /**Ritorna il numero relativo alla colonna se non esiste ritorna -1*/
     public int retColNum(String col){
       for(int i=1; i<MassimeColonne;i++) {     if (columnNames[i].equals(col.toUpperCase())) return i;}
       return -1;
     }
     
     /**Scrive i dati in una riga , colonna*/
     public void writedata(String dato,int riga,int conna){
       Dati[riga][conna]=dato;
       salvato = false;
     }

}