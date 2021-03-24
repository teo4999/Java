/**
 *
 * @author Hossu Teodor-Ioan 325CB
 */

public class Familie extends Pasageri { //clasa familiilor

    public Familie() { //initializare
        this.persoane = new Persoana[10];
        this.index = 0;
        this.id = "";
    }

    public  void setId(String x) { //setare id
        this.id = x;
    }

    public void addPersoana(Persoana x) { //adaugare perosana
        if (index >= persoane.length) { //marirea vectorului de persoane, daca este cazul
            Persoana[] newVector = new Persoana[persoane.length * 2];
            System.arraycopy(persoane, 0, newVector, 0, persoane.length);
            newVector[index] = x;
            persoane = newVector;
        }
        else
            persoane[index] = x;
        index++;
    }
}