/**Struttura della lista usata per le ricerche nel foglio*/
public class Lista{
      private Node node;
      public Lista(){
            node=null;
      }
      
      /**vero= la lista e' vuota , falso altrimenti*/
      public boolean isEmpty(){
            return (node == null);
      }
      
      /**ripulisce la lista*/
      public void clear(){
            node=null;
      }
      /**Se rc = true restituisco la riga ,false altrimenti*/
      public int restituisci(int num,boolean rc){
            Node tmp = node;
            if(isEmpty()) return 0;
            if (num > length() ) return 0;
            for (int i=0;i< length();i++){
                  if (i==num) break;
                  tmp=tmp.next;    
            }
            if (rc) return tmp.info1;
            else return tmp.info2;
      }
      
      /**inserisce in testa alla lista*/
      public void insert (int o,int u){
           node = new Node(o,u,node);
      }
      
     /**inserisce in coda alla lista */
      public void insertfine (int o,int u){
            if(isEmpty())
                  insert(o,u);
            else{
                  Node t = node;
                  while(t.next != null)
                        t=t.next;
                  Node tmp = new Node(o,u,t.next);
                  t.Pros(tmp);
                  }
      }
      
     /**ritorna la lunghezza della lista */
      public int length(){
            Node tmp = node;
            int cont=0;
            while(tmp != null)
            {     tmp = tmp.next;
                  cont++;
            }
            return cont;
      }
      
      /**stampa la lista usato per debug*/
      public void toing(){
            for(Node l = node; l != null; l = l.next){
                  System.out.print(l.info1+" "+l.info2);
                  System.out.print(" --> ");
            }
            System.out.println(" // ");
      }

}