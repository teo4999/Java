import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        int nr; //nr camere
        double temperatura; //temperatura dorita
        long time; //timestamp-ul de referinta

        File input = new File("therm.in"); //fisier input
        Scanner sc = new Scanner(input);
        StringTokenizer str = new StringTokenizer(sc.nextLine());
        nr = Integer.parseInt(str.nextToken()); //citirea numarului de camere
        temperatura = Double.parseDouble(str.nextToken()); //citirea temperaturii globale
        time = Long.parseLong(str.nextToken()); //citirea timestamp-ului de referinta

        File out = new File("therm.out"); //fisier output
        FileWriter output = new FileWriter(out);

        Locuinta loc = new Locuinta(nr, temperatura, time, output); //declarare locuinta

        for(int i = 0; i < nr; i++) { //adaugarea camerelor
            Camera cam = new Camera(output);
            str = new StringTokenizer(sc.nextLine());
            cam.setNume(str.nextToken());
            cam.setDevice_id(str.nextToken());
            cam.setSuprafata(Integer.parseInt(str.nextToken()));
            loc.addCamera(cam);
        }

        while(sc.hasNext()) { //citeste comenzile si le executa
            str = new StringTokenizer(sc.nextLine());
            String comanda = str.nextToken();

            if (comanda.equals("OBSERVE")) {
                String device_id = str.nextToken(); //citire id
                long stamp = Long.parseLong(str.nextToken()); //citire timestamp
                double temperature = Double.parseDouble(str.nextToken()); //citire temperatura
                if(stamp <= time) { //verifica daca acest timestamp este mai mare decat cel de referinta
                    loc.observe(device_id, stamp, temperature); //daca nu este, executa comanda
                }
            }

            else if (comanda.equals("TRIGGER")) { //trigger heat
                loc.trigger_heat(temperatura);
            }

            else if (comanda.equals("TEMPERATURE")) {
                double temperature = Double.parseDouble(str.nextToken()); //citeste temperatura noua globala
                temperatura = temperature; //schimba temperatura
                loc.setTemperatura(temperatura); //schimba temperatura si pentru locuinta
            }

            else if (comanda.equals("LIST")) {
                String nume = str.nextToken(); //nume camera
                long start = Long.parseLong(str.nextToken()); //timestamp de start
                long stop = Long.parseLong(str.nextToken()); //timestamp de stop
                loc.list(nume, start, stop);
            }
        }

        output.close(); //inchidere fisier output
    }
}
