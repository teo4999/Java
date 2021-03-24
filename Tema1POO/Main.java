import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 *
 * @author Hossu Teodor-Ioan 325CB
 */

public class Main {
    public static void main(String[] args) throws IOException {
        int n;
        String[] linie = new String[6];
        Singur[] sng = new Singur[10];
        Familie[] fam = new Familie[10];
        Grup[] grp = new Grup[10];
        int sng_len = 0;
        int fam_len = 0;
        int grp_len = 0;
        int ver = 0;

        File input = new File("queue.in"); //fisier input
        Scanner sc = new Scanner(input);
        StringTokenizer str = new StringTokenizer(sc.nextLine());
        n = Integer.parseInt(str.nextToken());
        File out = new File("queue.out"); //fisier output
        FileWriter output = new FileWriter(out);
        PrintWriter printWriter = new PrintWriter(output);

        for(int i = 0; i < n; i++) { //citirea si adaugarea persoanelor
            str = new StringTokenizer(sc.nextLine());
            for (int j = 0; j < 6; j++) {
                linie[j] = str.nextToken(); //memorarea informatiilor intr-un vector
            }
            Persoana x = new Persoana();
            x.nume = linie[1];
            x.varsta = Integer.parseInt(linie[2]);
            x.bilet = linie[3];
            x.imbarcare_prioritara = Boolean.parseBoolean(linie[4]);
            x.nevoi_speciale = Boolean.parseBoolean(linie[5]);

            if (linie[0].charAt(0) == "s".charAt(0)) { //adaugarea unei persoane singure
                if (sng_len >= sng.length) {
                    Singur[] newVector = new Singur[sng.length * 2];
                    System.arraycopy(sng, 0, newVector, 0, sng.length);
                    sng = newVector;
                }
                sng[sng_len] = new Singur();
                sng[sng_len].setId(linie[0]);
                sng[sng_len].addPersoana(x);
                sng_len++;
            }

            if (linie[0].charAt(0) == "f".charAt(0)) { //adaugarea unei persoane intr-o familie
                for (int j = 0; j < fam.length; j++)
                    if ((fam[j] != null) && (fam[j].id.equals(linie[0]))) { //verifica daca familia specificata exista deja
                        fam[j].addPersoana(x);
                        ver++;
                    }
                if (ver == 0) { //creeaza o noua familie
                    if (fam_len >= fam.length) {
                        Familie[] newVector = new Familie[fam.length * 2];
                        System.arraycopy(fam, 0, newVector, 0, fam.length);
                        fam = newVector;
                    }
                    fam[fam_len] = new Familie();
                    fam[fam_len].setId(linie[0]);
                    fam[fam_len].addPersoana(x);
                    fam_len++;
                }
                ver = 0;
            }

            if (linie[0].charAt(0) == "g".charAt(0)) { //adaugarea unei persoane intr-un grup
                for (int j = 0; j <= grp_len; j++)
                    if ((grp[j] != null) && (grp[j].id.equals(linie[0]))) { //verifica daca grupul exista deja
                        grp[j].addPersoana(x);
                        ver++;
                    }
                if (ver == 0) { //creeaza un nou grup
                    if (grp_len >= grp.length) {
                        Grup[] newVector = new Grup[grp.length * 2];
                        System.arraycopy(grp, 0, newVector, 0, grp.length);
                        grp = newVector;
                    }
                    grp[grp_len] = new Grup();
                    grp[grp_len].setId(linie[0]);
                    grp[grp_len].addPersoana(x);
                    grp_len++;
                }
                ver = 0;
            }
        }
        MaxHeap Heap = new MaxHeap(n, printWriter); //initializare Heap
        while(sc.hasNext()) { //citeste comenzile si le executa
            str = new StringTokenizer(sc.nextLine());
            String comanda = str.nextToken();

            if(comanda.equals("insert")) { //inserare in Heap
                String id = str.nextToken();

                for(int i = 0; i < sng_len; i++)
                    if(sng[i].id.equals(id))
                        Heap.insert(sng[i], sng[i].getPrioritate()); //inserare persoana singura

                for(int i = 0; i < fam_len; i++)
                    if(fam[i].id.equals(id)) {
                        fam[i].index--;
                        Heap.insert(fam[i], fam[i].getPrioritate() + 10); //inserare familie
                    }

                for(int i = 0; i < grp_len; i++)
                    if(grp[i].id.equals(id)) {
                        grp[i].index--;
                        Heap.insert(grp[i], grp[i].getPrioritate() + 5); //inserare grup
                    }
            }

            if(comanda.equals("embark")) {
                Heap.embark();
            }

            if(comanda.equals("list")) {
                Heap.list();
                printWriter.print("\n");
            }

            if(comanda.equals("delete")) {
                String id = str.nextToken();
                String pers;

                if(str.hasMoreTokens()) { //daca este specificata o persoana anume care trebuie stearsa
                    pers = str.nextToken();
                }
                else
                    pers = null;

                for(int i = 0; i < sng_len; i++)
                    if(sng[i].id.equals(id))
                        Heap.delete(sng[i]); //sterge o persoana singura

                for(int i = 0; i < fam_len; i++)
                    if(fam[i].id.equals(id))
                        if(pers == null)
                            Heap.delete(fam[i]); //sterge o familie intreaga
                        else
                            Heap.delete(fam[i], pers); //sterge o persoana dintr-o familie

                for(int i = 0; i < grp_len; i++)
                    if(grp[i].id.equals(id))
                        if(pers == null)
                            Heap.delete(grp[i]); //sterge un grup intreg
                        else
                            Heap.delete(grp[i], pers); //sterge o persoana dintr-un grup
            }
        }

        Heap.closeFile();
    }
}