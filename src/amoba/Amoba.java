package amoba;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Amoba extends JFrame implements ActionListener{

        private int lepesDb = 0;
        private boolean nyerte;
        private static int hossz = 3;
        private static int seged = hossz - 1;
        private JButton btGomb[][] = new JButton[hossz][hossz];
        private JButton btStart = new JButton("Új játék");
        private JLabel lbUzenet=new JLabel("1. lépés: X");
        private JLabel lbMeret=new JLabel("Méret: ");
        private final String[] felirat={"0", "X"}; 
        private boolean dontetlen = false;
        private final JComboBox cbMeret = new JComboBox(new String[] {"3*3", "4*4", "5*5", "6*6", "7*7", "8*8", "9*9", "10*10", "11*11"});
        private JPanel pnJatekTer=new JPanel(new GridLayout(hossz, hossz));
        private Font betu=new Font("Comic Sans MS", Font.BOLD, 280/hossz);
        private int utolsox, utolsoy; //globálisan felvszeük az utolsó sor és osztlop elemet
        
    public Amoba() {
        inicializal(hossz);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btAkt = (JButton)e.getSource();
        String jatekos;
        
        if(btAkt == btStart) {
            hossz = cbMeret.getSelectedIndex()+3;
            seged = hossz - 1;
            meretetAllit(hossz);
        }
        
        else  if (!nyerte)
            {
                if (btAkt.getText().equals(""))
                {
                    lepesDb++;
                    jatekos=felirat[(lepesDb+1)%2];
                    lbUzenet.setText((lepesDb+1)+". lépés: "+jatekos);
                    btAkt.setText(felirat[lepesDb%2]);
                    //btAkt.setEnabled(false);
                }
                if (hossz < 6 && lepesDb > (hossz-1)*2)
                {
                    ellenoriz();
                }
                else if (lepesDb > 8) {
                    String[] LocationString = btAkt.getName().split(","); // egy tömben, feldarabaolva eltároljuk a megálapított helyet
                    utolsox=  Integer.valueOf(LocationString[0]); //a tömb 0. eleme lesz a sor
                    utolsoy=  Integer.valueOf(LocationString[1]); // a tömb 1. eleme lesz az oszlop
                    ellenoriz_5();
                }
            }
//        }
    }

    private void meretetAllit(int hossz) {
        pnJatekTer.removeAll(); //gombok kiürítése
        pnJatekTer.setLayout(new GridLayout(hossz, hossz));
        betu=new Font("Comic Sans MS", Font.BOLD, 280/hossz);
        btGomb = new JButton[hossz][hossz];
        gombokLetrehozasa();
        revalidate();
        ujrakezd();
    }
    private void inicializal(int hossz) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe 2.0");
        setSize(700, 750);
//        setResizable(false);
        setLocationRelativeTo(this);
        JPanel pnAlap=new JPanel();
        pnAlap.add(btStart);
        pnAlap.add(lbMeret);
        pnAlap.add(cbMeret);
        btStart.addActionListener(this);
        pnAlap.add(lbUzenet);
        add(pnAlap, BorderLayout.NORTH);
        gombokLetrehozasa();
        add(pnJatekTer);
        setVisible(true);
        ujrakezd();
    }
    
    private void gombokLetrehozasa(){
        for (int i = 0; i < hossz; i++)
            {
                for (int j = 0; j < hossz; j++)
                {
                    btGomb[i][j] = new JButton();
                    btGomb[i][j].setName(i + "," + j); //megálapítjuk a helyét(sor, osztlop) az aktuáliskattintásnak
                    btGomb[i][j].setFont(betu);
                    btGomb[i][j].addActionListener(this);
                    pnJatekTer.add(btGomb[i][j]);
                    
                }
            }
    }
    
    private void ujrakezd() {
        nyerte = false;
        lepesDb = 0;
            for (int i = 0; i < hossz; i++)
            {
                for (int j = 0; j < hossz; j++)
                {
                    btGomb[i][j].setText("");
                }
            }
        lbUzenet.setText((lepesDb+1)+". lépés: X");
        dontetlen = false;
    }

    private void ellenoriz() {
              
        //jobbra le átló
        jobbraLeAtlo();
           
        //balra le átló
        balraLeAtlo();
                    
        //soron belüli ell...
        soronBelul();
                        
        //oszlopon belüli ell.
        oszloponBelul();
            
        //döntetlen
        dontetlenE();
    }

    private void ellenoriz_5() {
        //jobbra le átló
        jelKeresJobbraLe("X");
        jelKeresJobbraLe("0");
        //balra le átló
        jelKeresBalraLe("X");
        jelKeresBalraLe("0");

        //soron belüli ell...
        jelKeresSoronBelul();
        //oszlopon belüli ell.
        jelKeresOszloponBelul();
        //döntetlen
        dontetlenE();
    }
    
    private void nyerteskiir(String nyert) {
        nyerte = true;
        JOptionPane.showMessageDialog(this, "Nyert az "+nyert+" jellel játszó játékos "+lepesDb+" lépésben!");
    }

    private void oszloponBelul() {
        int sor;
        int oszlop = 0;
        
        if (!nyerte)
            {
                while (oszlop <= seged && !nyerte)
                {
                    sor = 0;
                    while (sor < seged && btGomb[sor][oszlop].getText().equals(btGomb[sor + 1][oszlop].getText()) && !btGomb[0][oszlop].getText().equals(""))
                    {
                        sor++;
                    }
                    if (sor == seged)
                    {
                        nyerteskiir(btGomb[0][oszlop].getText());
                    }
                    oszlop++;
                }
            }
    }

    private void soronBelul() {
        int oszlop;
        int sor = 0;
        if (!nyerte)   {
            while (sor <= seged && !nyerte)     {
                    oszlop = 0;
                    while (oszlop < seged && btGomb[sor][oszlop].getText().equals(btGomb[sor][oszlop + 1].getText()) && !btGomb[sor][0].getText().equals(""))
                    {
                        oszlop++;
                    }
                    if (oszlop == seged)
                    {
                        nyerteskiir(btGomb[sor][0].getText());
                    }
                    sor++;
                }
        }
    }

    private void jobbraLeAtlo() {
        int i = 0;
        if (!nyerte && !btGomb[2][2].getText().equals(""))
            {
                while (i < seged && btGomb[i][i].getText().equals(btGomb[i + 1][i + 1].getText()))
                {
                    i++;
                }
                if (i == seged)
                {
                    nyerteskiir(btGomb[2][2].getText());
                }
            }
    }

    private void balraLeAtlo() {
        int i = 0;
        if (!nyerte && !btGomb[seged][0].getText().equals("")){
            while (i < seged && btGomb[i][seged - i].getText().equals(btGomb[i + 1][seged - i-1].getText()))
                {
                    i++;
                }
            if (i == seged)
                {
                    nyerteskiir(btGomb[seged][0].getText());
                }
        }
    }

    private void dontetlenE() {
        if (lepesDb == hossz*hossz && !nyerte && !dontetlen)
            {
                JOptionPane.showMessageDialog(this, "Döntetlen!");
                dontetlen = true;
            }
    }

    private void jelKeresJobbraLe(String jel) {
        int db; // ha eléri az 5-t, van nyertes
        int oszlop;
        int oszlopS;
        int sor = hossz - 5;    //az utolsó értelmes hely a keresésre
        int sorS;
        while (!nyerte && sor > -1)  {
            oszlop = 0;
            oszlopS = 0;
            while(!nyerte && oszlopS < hossz - 4){
                sorS = sor; //segédsor
                db = 0; //vizsgálat előtt nullázunk
                while (oszlopS < hossz - 4 && !btGomb[sorS][oszlopS].getText().equals(jel))   {
                        oszlopS++; //megkeressük az első ilyen jelet
                }
                oszlop = oszlopS;
                if (oszlop < hossz - 4) //hatékonysági beállítás
                    {
                    while (sorS < hossz && oszlop < hossz && btGomb[sorS][oszlop].getText().equals(jel))     {
                        sorS++; //le
                        oszlop++; //jobbra
                        db++;   //amíg egyenlő, lépünk előre, növeljük a db-t
                    }
                    if (db > 4)  {
                            nyerteskiir(jel);
                        }
                    }
                oszlopS++;
            }
            sor--;
        }
    }
    
    private void jelKeresBalraLe(String jel) {
        int db; 
        int oszlop;
        int oszlopS;
        int sor = hossz - 5;
        int sorS;
        int hosszs=3;
        while (!nyerte && sor > -1)  {
            oszlop = seged;
            oszlopS = seged;
            while(!nyerte && oszlopS > hosszs){
                sorS = sor; 
                db = 0; 
                while (oszlopS > hosszs && !btGomb[sorS][oszlopS].getText().equals(jel))   {
                        oszlopS--;
                }
                oszlop = oszlopS;
                if (oszlopS > hosszs)
                    {
                    while (sorS < hossz && oszlop > -1 && btGomb[sorS][oszlop].getText().equals(jel))     {
                        sorS++; 
                        oszlop--;
                        db++;
                    }
                    if (db > 4)  {
                            nyerteskiir(jel);
                        }
                    }
                oszlopS--;
            }
            sor--;
        }
    }
    
    private void jelKeresSoronBelul() {
        
    }

    private void jelKeresOszloponBelul() {
        String utolsoKarakter = btGomb[utolsox][utolsoy].getText(); // megállapítja hogy o v. x volt az utolsó karakter
        int i = 1; // indexelés szempontjából felveszünk egy i változót
        while(utolsox + i <= hossz-1 && !nyerte && btGomb[utolsox + i][utolsoy].getText() == utolsoKarakter ){ //ez nézi lefele hogy meg van-e 5 jel egymás alatt
            
            if(i >= 4){ //vizsgálja hogy tényleg 5 db jel van-e egymás alatt
                    nyerteskiir(utolsoKarakter);// ez mondja meg hogy volt-e nyertes
                    }
            i++;
        }
        
        i = -1;
        while(utolsox + i >= 0 && !nyerte && btGomb[utolsox + i][utolsoy].getText() == utolsoKarakter ){ //ez nézi felfele hogy meg van-e 5 jel egymás felett
            
            if(i <= -4){ //vizsgálja hogy tényleg 5 db jel van-e egymás felett
                    nyerteskiir(utolsoKarakter);// ez mondja meg hogy volt-e nyertes
                    }
            i--;
        }              
    }

    public static void main(String[] args) {
         Amoba amoba = new Amoba();
    }
    
}
