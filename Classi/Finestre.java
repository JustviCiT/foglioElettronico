import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFileChooser;
import java.awt.Component;
import java.awt.BorderLayout;
import javax.swing.filechooser.FileFilter;


/**Contiene tutte le finestre del menu e relative finestre d'errore */
public class Finestre{
      JFileChooser fileChooser;
      db d;
      JTable t;
      File selectedFile;
      
      public Finestre(){};
      public Finestre(db Dati,JTable tabella){
            this.d = Dati;
            this.t = tabella;
      }
      
      /**Richiamata solo quando c'è un errore*/
      public void FinestraErrore(String Tipo){
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame,
                                          Tipo,
                                          "Errore!",
                                          JOptionPane.ERROR_MESSAGE); 
      }
      
      /**Richiamata per conferme*/
      public int FinestraConferma(String Tipo){
            JFrame frame = new JFrame();
            int n=JOptionPane.showConfirmDialog(
                                                frame,
                                                Tipo,
                                                "Sei Sicuro ?",
                                                JOptionPane.YES_NO_OPTION);
            return n;
      }
      
      public JFrame FinestraRicerca(){
            JFrame f;
            Container cjf;
            JPanel p;
            Font flabel,ftext;
            final JLabel lb,res; 
            final JTextField txt;
            final JButton button1,button2;
           
            f= new JFrame("Ricerca . . . ");
            cjf = f.getContentPane();
            f.setAlwaysOnTop(true); 
            f.setBounds(500,400,285,160);
            f.setResizable(false);
            p = new JPanel();
            lb = new JLabel(" Cosa vuoi cercare ? ");
            flabel = new Font("Helvetica",Font.PLAIN,14);
            ftext = new Font("Helvetica",Font.PLAIN,16);
            res = new JLabel("             ");
            res.setFont(flabel);
            f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            txt = new JTextField(20);
            txt.setFont(ftext);
            button1 = new JButton("<- Precedente ");
            button2 = new JButton("Successivo ->");
            /**/
            ActionListener actionListener = new ActionListener() {
                  int contatore=0;
                  int riga,colonna;
                  public void actionPerformed(ActionEvent actionEvent) {
                        if (t.isEditing()) t.getCellEditor().stopCellEditing();    
                        Lista l= d.Cerca(txt.getText());
                        int lunghezza= l.length();
                        Component da = (Component) actionEvent.getSource();
                     
                        if (da == button1) {
                              if (l.isEmpty()) res.setText("Non Trovato");
                              else {
                                    contatore--;
                                    if (contatore <0) contatore=0;
                                    res.setText("Trovato !");
                                    riga=l.restituisci(contatore,true);
                                    colonna=l.restituisci(contatore,false);
                                    t.changeSelection(riga, colonna, false, false);
                                    t.requestFocus();
                              }
                              
                            
                        }
                        if (da == button2){
                              if (l.isEmpty())  res.setText("Non Trovato");
                              else {
                                    contatore++;
                                    if (contatore > lunghezza-1 )  contatore = --lunghezza; 
                                    res.setText("Trovato !");
                                    riga=l.restituisci(contatore,true);
                                    colonna=l.restituisci(contatore,false);
                                    t.changeSelection(riga, colonna, false, false);
                                    t.requestFocus();
                              }
                         }
                        l.clear();
                  }
            };
            
            button1.addActionListener(actionListener);
            button2.addActionListener(actionListener);
            p.add(lb);
            p.add(txt);
            p.add(button1);
            p.add(button2);
            p.add(res);
            cjf.add(p);
            f.setVisible(true);
            return f;
      }
      
      /** Viene richiamato ogni volta viene cliccato il menu Apri.. */
      public String FinestraOpen(String who) {
            fileChooser = new JFileChooser(new File(".."));
            fileChooser.addChoosableFileFilter(new MyFilter());
            fileChooser.setDialogTitle(who);
            int valore = fileChooser.showOpenDialog(null);
            if (valore == JFileChooser.APPROVE_OPTION) {       
                  selectedFile = fileChooser.getSelectedFile();
                  /*PARTE APRI*/ 
                  try{
                        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(selectedFile));
                        d.Dati( (String[][]) ois.readObject() );
                        t.repaint();
                        ois.close();
                  }catch (IOException e) {FinestraErrore("Errore Di Lettura del file "+e);}
                  catch (ClassNotFoundException e) {FinestraErrore("Classe non trovata "+e);}
                  return selectedFile.getName();
            }
            return "";
      }
      
      /** Viene richiamato ogni volta che c'è bisogno di salvare*/
      public String FinestraSave(String who) {
            ObjectOutputStream Estensione;
            String nomignolo="";
            fileChooser = new JFileChooser(new File(".."));
            fileChooser.addChoosableFileFilter(new MyFilter());
            fileChooser.setDialogTitle(who);
            int valore = fileChooser.showSaveDialog(null);
            if (valore == JFileChooser.APPROVE_OPTION) {
                  selectedFile = fileChooser.getSelectedFile();
                  nomignolo = Save(selectedFile);
                  if (nomignolo.endsWith(".dat"))
                        return nomignolo;
                  else
                        return nomignolo+".dat";
            }
            return "";
      }
      
      /**Salvataggio su file generalizzato*/
      public String Save(File nome) {
            ObjectOutputStream Estensione;
                  try{
                        if (nome.getName().endsWith(".dat")){
                              Estensione=new ObjectOutputStream(new FileOutputStream(nome));
                        }else{
                              Estensione=new ObjectOutputStream(new FileOutputStream(nome+".dat"));}
                        Estensione.writeObject(d.Dati());
                        /* indica se il file è stato salvato*/
                        Estensione.close();
                  }catch (IOException e) {FinestraErrore("Errore Di scrittura "+e);} 
                  return nome.getName();
      }
      
      /**Filtro che serve per visualizzare solo i file .dat */
      class MyFilter extends FileFilter {
            public boolean accept(File file) {
                  String filename = file.getName();
                  return filename.endsWith(".dat")||file.isDirectory();
            }
            public String getDescription()  {
                  return "*.dat";
            }
            
      }
}