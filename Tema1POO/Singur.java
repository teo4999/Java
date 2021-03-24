/**
 *
 * @author Hossu Teodor-Ioan 325CB
 */

public class Singur extends Pasageri { //clasa persoanelor singure
    public Singur() { //initializare
        this.persoane = new Persoana[1];
        this.index = 0;
    }

    public  void setId(String x) { //setare id
        this.id = x;
    }

    public void addPersoana(Persoana x) { //adaugare persoana
        persoane[0] = x;
    }
}
