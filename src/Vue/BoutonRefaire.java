package Vue;

import Modele.Jeu;
import Patterns.Observateur;
import javafx.scene.control.Button;


class BoutonRefaire extends Button implements Observateur{
    Jeu jeu;

    public BoutonRefaire(Jeu j) {
        jeu = j;
        setText("Refaire");
        jeu.ajouteObservateur(this);
    }
    
    
    @Override
    public void miseAJour() {}
}
