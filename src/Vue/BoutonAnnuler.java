package Vue;

import Modele.Jeu;
import Patterns.Observateur;
import javafx.scene.control.Button;


public class BoutonAnnuler extends Button implements Observateur{
    Jeu jeu;

    public BoutonAnnuler(Jeu j) {
        jeu = j;
        setText("Annuler");
        jeu.ajouteObservateur(this);
    }
    
    
    @Override
    public void miseAJour() {}
    
}
