/**
 * @(#) Moniteur.java
 */
package FFSSM;

import java.time.LocalDate;
import java.util.*;

public class Moniteur extends Plongeur {

    private int numeroDiplome;
    private TreeSet<Embauche> embauches = new TreeSet<>(new Comparator<Embauche>() {
        @Override
        public int compare(Embauche o1, Embauche o2) {
            if (o1.getDebut().isBefore(o2.getDebut())){
                return 1;
            }
            return -1;
        }
    });

    public Moniteur(String numeroINSEE, String nom, String prenom, String adresse, String telephone, LocalDate naissance, int numeroDiplome) {
        super(numeroINSEE, nom, prenom, adresse, telephone, naissance);
        this.numeroDiplome = numeroDiplome;
    }

    /**
     * Si ce moniteur n'a pas d'embauche, ou si sa dernière embauche est terminée,
     * ce moniteur n'a pas d'employeur.
     * @return l'employeur actuel de ce moniteur sous la forme d'un Optional
     */
    public Optional<Club> employeurActuel() {
         Optional<Club> opC;
         if (embauches.isEmpty() || embauches.first().estTerminee()){
             opC = Optional.empty();
         }else{
             opC = Optional.of(embauches.first().getEmployeur());
         }
         return opC;
    }
    
    /**
     * Enregistrer une nouvelle embauche pour cet employeur
     * @param employeur le club employeur
     * @param debutNouvelle la date de début de l'embauche
     */
    public void nouvelleEmbauche(Club employeur, LocalDate debutNouvelle) {   
        embauches.add(new Embauche(debutNouvelle, this, employeur));
    }

    public TreeSet<Embauche> emplois() {
        return embauches;
    }

    public void terminerEmbauche(LocalDate fin){
        if (!embauches.first().estTerminee()) {
            embauches.first().setFin(fin);
        }
    }

}
