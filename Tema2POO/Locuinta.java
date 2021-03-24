import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Locuinta {
    private int nr_camere;
    private ArrayList<Camera> camere;
    private double temperatura;
    private long stamp;
    private FileWriter output;

    //declarare locuinta
    public Locuinta(int nr_camere, double temperatura, long stamp, FileWriter output) throws IOException {
        this.nr_camere = nr_camere;
        this.camere = new ArrayList<>(nr_camere);
        this.temperatura = temperatura;
        this.stamp = stamp;
        this.output = output;
    }

    public void addCamera(Camera camera) { //adaugare camera
        camere.add(camera);
    }

    public long getStamp() { //obtinere timestamp de referinta
        return stamp;
    }

    public void setTemperatura(double temperatura) { //setare temperatura
        this.temperatura = temperatura;
    }

    public void observe(String device_id, long stamp, double temperature) { //adaugare de temperaturi observate
        for(int i = 0; i < nr_camere; i++) {
            if(camere.get(i).getDevice_id().equals(device_id)) {
                camere.get(i).addTemperature(stamp, temperature, this.getStamp());
            }
        }
    }

    public void trigger_heat(double temperatura) throws IOException {
        int suprafata = 0;
        double sum = 0;

        for(int i = 0; i < nr_camere; i++) {
            suprafata += camere.get(i).getSuprafata(); //obtinere suprafata locuinta
        }

        for(int i = 0; i < nr_camere; i++) { //calculare de medii ponderate pe fiecare camera
            sum += camere.get(i).MediePonderata(suprafata, camere.get(i).getLastHour());
        }

        //verifica daca media ponderata a tuturor camerelor e mai mica decat temperatura de referinta
        if(sum < temperatura) {
            output.write("YES\n");
        }
        else {
            output.write("NO\n");
        }
    }

    public void list(String nume, long start, long stop) throws IOException { //listare camera
        for(int i = 0; i < nr_camere; i++) {
            if(camere.get(i).getNume().equals(nume)) {
                output.write(camere.get(i).getNume() + " ");
                camere.get(i).list(start, stop, this.getStamp()); //listare temperaturile camerei
                output.write("\n");
            }
        }
    }
}
