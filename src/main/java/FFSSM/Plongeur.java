package FFSSM;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.TreeSet;

public class Plongeur extends Personne{
	private int niveau;
    private TreeSet<Licence> licences = new TreeSet<>(new Comparator<Licence>() {
        @Override
        public int compare(Licence o1, Licence o2) {
            if (o1.getDelivrance().isBefore(o2.getDelivrance())){
                return 1;
            }else{
                return -1;
            }
        }
    });

    public Plongeur(String numeroINSEE, String nom, String prenom, String adresse, String telephone, LocalDate naissance){
        super(numeroINSEE, nom, prenom, adresse, telephone, naissance);
    }
    public Plongeur(String numeroINSEE, String nom, String prenom, String adresse, String telephone, LocalDate naissance, int niveau){
        this(numeroINSEE, nom, prenom, adresse, telephone, naissance);
        this.niveau = niveau;
    }

    public void ajouteLicence(String numero, LocalDate delivrance, Club c){
        licences.add(new Licence( this, numero, delivrance, c ));
    }

    public Licence derniereLicence(){
        return licences.first();
    }
}
