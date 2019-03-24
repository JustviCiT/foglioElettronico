/**Struttura di un nodo , usato con la lista per le ricerche
      info1 indica la riga , info2 indica la colonna*/
class Node{
      int info1;
      int info2;
      Node next;
      public Node(){};
      
      /**crea semplicemente un nuovo nodo */
      public Node(int o,int u){
            this.info1= o;
            this.info2= u;
            this.next = null;
      }
      
      /**crea un nuovo nodo e inserisce le informazioni */
      public Node(int o,int u, Node n){
            this.info1=o;
            this.info2=u;
            next = n; 
      }
      
      /**prossimo nodo*/
      public void Pros(Node n){
            next = n;
      }
      
}