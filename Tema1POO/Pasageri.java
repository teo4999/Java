/**
 *
 * @author Hossu Teodor-Ioan 325CB
 */

public abstract class Pasageri { //clasa abstracta mostenita de Singur, Familie si Grup
    Persoana[] persoane;
    String id;
    int index = 0;

    public int getPrioritate() { //calcularea prioritatii fiecarei persoane in parte
        int prioritate = 0;
        for(int i = 0; i <= index; i++) {
            if(persoane[i].varsta < 2)
                prioritate += 20;
            if((persoane[i].varsta >= 2) && (persoane[i].varsta < 5))
                prioritate += 10;
            if((persoane[i].varsta >= 5) && (persoane[i].varsta <= 10))
                prioritate += 5;
            if(persoane[i].varsta >= 60)
                prioritate += 15;
            if(persoane[i].bilet.equals("b"))
                prioritate += 35;
            if(persoane[i].bilet.equals("p"))
                prioritate += 20;
            if(persoane[i].imbarcare_prioritara == true)
                prioritate += 30;
            if(persoane[i].nevoi_speciale == true)
                prioritate += 100;
        }
        return prioritate;
    }
}
