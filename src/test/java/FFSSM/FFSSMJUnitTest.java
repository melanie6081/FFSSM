package FFSSM;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

public class FFSSMJUnitTest {
    Club c1, c2;
    Moniteur m1, m2;
    Plongeur p1, p2;


    @BeforeEach
    public void setUp(){
        c1 = new Club(m1, "Océan", "09 60 17 09 81");
        c2 = new Club(m2, "Plage", "09 82 29 51 46");
        m2 = new Moniteur("1 99 10 60 270 175 49", "DURAND", "Loic", "14 Avenue de la Plage", "07 82 29 15 40", LocalDate.of(1999, 10, 20), 4);
        m1 = new Moniteur("2 02 04 60 260 185 69", "DUPONT", "Lea", "27 Rue des Coquillages", "06 82 60 15 20", LocalDate.of(2002, 04, 14), 5);
        p1 = new Plongeur("1 01 11 92 270 105 29", "RONF", "Pascal", "15 rue Camille", "06 60 17 03 59", LocalDate.of(2001, 11, 23), 3);
        p2 = new Plongeur("2 03 06 92 270 105 29", "RONF", "Magalie", "15 rue Camille", "06 60 17 03 59", LocalDate.of(2003, 06, 16), 3);
    }

    @Test
    public void testValiditeLicence(){
        p1.ajouteLicence("0949002D",LocalDate.of(2012,07,12), c1);
        assertFalse(p1.derniereLicence().estValide(LocalDate.now()), "La licence doit être invalide.");
        p1.ajouteLicence("0959002C",LocalDate.of(2022,06,10), c1);
        assertTrue(p1.derniereLicence().estValide(LocalDate.now()), "La licence doit être valide.");
        p2.ajouteLicence("0745006M",LocalDate.of(2021,11,23),c2);
        assertFalse(p2.derniereLicence().estValide(LocalDate.now()), "La licence doit être invalide.");

    }

    @Test
    public void testDerniereLicence(){
        p2.ajouteLicence("0756003L",LocalDate.of(2022,04,13), c2);
        Licence l = p2.derniereLicence();
        assertEquals("0756003L", l.getNumero(), "Le numéro doit être 0756003L");
        assertEquals(p2, l.getPossesseur(), "Le possesseur de cette licence doit être p2.");
        assertEquals(c2, l.getClub(), "Cette licence doit être rattachée au club c2, le club de la plage.");
        p2.ajouteLicence("0746007M",LocalDate.of(2021,05,12), c2);
        assertEquals(l, p2.derniereLicence(), "La dernière licence de Loic doit être celle de 2022.");
    }

    @Test
    public void testEmbauche(){
        m1.nouvelleEmbauche(c2,LocalDate.of(2022,06,10));
        assertEquals(Optional.of(c2), m1.employeurActuel(), "L'employeur de Léa Dupont doit être le club Plage");
        assertEquals(Optional.empty(), m2.employeurActuel(), "Durand Loic ne doit pas avoir d'employeur actuel.");
        assertFalse(m1.emplois().first().estTerminee(), "L'embauche de Léa Dupont ne doit pas être terminée.");
        m1.terminerEmbauche(LocalDate.now());
        assertTrue(m1.emplois().first().estTerminee(),"L'embauche de Léa Dupont doit être terminée.");
        m2.nouvelleEmbauche(c1,LocalDate.of(2020,07,11));
        assertEquals(null, m2.emplois().first().getFin(), "L'embauche ne doit pas avoir de fin." );
        m2.emplois().first().terminer(LocalDate.of(2022, 11, 03));
        assertTrue(m2.emplois().first().estTerminee(),"L'embauche doit être terminée.");
        Embauche e = new Embauche(LocalDate.now(), m1, c2);
        assertEquals(m1, e.getEmploye(), "L'employe concerné par cette embauche doit être Léa Dupont (m1).");
    }

    @Test
    public void testPlongee(){
        p2.ajouteLicence("0756003L",LocalDate.of(2022,04,13), c2);
        p1.ajouteLicence("0959002C",LocalDate.of(2020,06,10), c1);
        Site s = new Site("Epave du mont", "sortie débutants");
        Plongee pl = new Plongee(s,m1,LocalDate.now(), 20, 45);
        pl.ajouteParticipant(p2);
        assertTrue(pl.estConforme(), "La plongée doit être conforme.");
        pl.ajouteParticipant(p1);
        assertFalse(pl.estConforme(),"La plongée ne doit pas être conforme.");
        assertEquals(LocalDate.now(), pl.getDate(), "La date de la plongée doit être celle d'aujourd'hui.");
        c2.organisePlongee(pl);
        HashSet<Plongee> plongeeNC = new HashSet<>();
        plongeeNC.add(pl);
        assertEquals(plongeeNC, c2.plongeesNonConformes(), "La liste des plongées non conformes du club c2 ne doit comporter que la plongée pl.");
    }

}
