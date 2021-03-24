import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Camera {
    private String nume;
    private String device_id;
    private int suprafata;
    private Map<Integer, List<Bucket>> temperaturi; //mapare temperaturi in bucket-uri de o ora
    private DecimalFormat df2 = new DecimalFormat("#.00");
    private int lastHour;
    private long lastStamp;
    private FileWriter output;

    public Camera(FileWriter output) { //declarare camera
        this.nume = null;
        this.device_id = null;
        this.suprafata = 0;
        this.temperaturi = new HashMap(24);
        this.lastHour = 0;
        this.lastStamp = 0;
        this.output = output;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public int getSuprafata() {
        return suprafata;
    }

    public void setSuprafata(int suprafata) {
        this.suprafata = suprafata;
    }

    public int getLastHour() {
        return lastHour;
    }

    public void setLastHour(int lastHour) {
        this.lastHour = lastHour;
    }

    public long getLastStamp() {
        return lastStamp;
    }

    public void setLastStamp(long lastStamp) {
        this.lastStamp = lastStamp;
    }

    public void addTemperature(long stamp, double temperature, long initial_stamp) {
        int xh = (int) ((initial_stamp - stamp) / 3600); //calculare ora din timestamp

        if(stamp > this.getLastStamp()) { //verifica daca e ultima ora la care s-au adaugat temperaturi
            this.setLastHour(xh);
            this.setLastStamp(stamp);
        }

        Bucket bucket = new Bucket(stamp, temperature); //creare bucket nou

        if(temperaturi.containsKey(xh)) { //verifica daca exista deja ora in HashMap
            temperaturi.get(xh).add(bucket); //adauga temperatura cu timestamp-ul ei
            ordering(xh); //reordonare in bucket dupa temperaturi sau stergere duplicate
        }
        else {
            List<Bucket> list = new ArrayList<>(1); //creeaza o noua lista pentru un bucket
            list.add(bucket); //adauga in lista temperatura cu timestamp-ul ei
            temperaturi.put(xh, list); //o adauga in HashMap
        }
    }

    public void ordering(int xh) { //ordonare dupa temperaturi in ordine crescatoare
        for(int i = 0; i < temperaturi.get(xh).size(); i++) {
            try {
                for(int j = 0; j < temperaturi.get(xh).size(); j++) {
                    if((temperaturi.get(xh).get(j) != null) &&
                            (temperaturi.get(xh).get(i).getTemp() < temperaturi.get(xh).get(j).getTemp())) {
                        Bucket aux = new Bucket(0, 0);
                        aux.copy(temperaturi.get(xh).get(i));
                        temperaturi.get(xh).get(i).copy(temperaturi.get(xh).get(j));
                        temperaturi.get(xh).get(j).copy(aux);
                    }
                }
            }
            catch (IndexOutOfBoundsException e) {
                break;
            }
        }

        for(int i = 0; i < temperaturi.get(xh).size(); i++) { //stergere duplicate
            try {
                for(int j = i + 1; j < temperaturi.get(xh).size(); j++) {
                    if (temperaturi.get(xh).get(i).getTemp() == temperaturi.get(xh).get(j).getTemp()) {
                        temperaturi.get(xh).remove(j);
                    }
                }
            }
            catch (IndexOutOfBoundsException e) {
                break;
            }
        }
    }

    public double MediePonderata(int suprafata, int time) { //calculare medie ponderata a camerei
        double x = (double) this.getSuprafata() / (double) suprafata;
        return temperaturi.get(time).get(0).getTemp() * x;
    }

    public void list(long start, long stop, long initial_stamp) throws IOException { //listare camera cu temperaturi

        int xh = (int) ((initial_stamp - start) / 3600); //ora de start
        int yh = (int) ((initial_stamp - stop) / 3600); //ora de stop

        for(int i = yh; i < xh; i++) {
            for(int j = 0; j < temperaturi.get(i).size(); j++) {
                try {
                    if(temperaturi.get(i).get(j).getStamp() >= start &&
                            temperaturi.get(i).get(j).getStamp() <= stop) { //verificare daca se afla in interval
                        output.write(df2.format(temperaturi.get(i).get(j).getTemp()) + " "); //afisare
                    }
                }
                catch (IndexOutOfBoundsException e) {
                }
            }
        }
    }
}
