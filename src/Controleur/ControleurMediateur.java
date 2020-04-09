package Controleur;

import Modele.Jeu;
import Modele.Position;
import Vue.ViewJouer;
import Vue.ViewParametre;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ControleurMediateur {
    Jeu jeu;
    ViewJouer vjouer;
    ViewParametre vpara;
    int joueurCourant;
    final int lenteurAttente = 50;
    int decompte;

    public ControleurMediateur(Jeu j, ViewJouer vj, ViewParametre vp) {
        jeu = j;
        vjouer = vj;
        vpara = vp;

    }

    public void modifScore(int nb) {
        vjouer.Score.getChildren().clear();
        if(nb == 4)
            vjouer.Score.getChildren().addAll(vjouer.joueur0,vjouer.joueur1,vjouer.joueur2,vjouer.joueur3);
        else if(nb == 2)
            vjouer.Score.getChildren().addAll(vjouer.joueur0,vjouer.joueur1);
    }
    public void setNom(TextField t) {
        t.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                t.setText(newValue);
                vpara.nom = newValue;
            }
        });
    }

    public void valide(Button b,int order) {
        b.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                vjouer.joueur[order].setText(vpara.nom);
                if(vpara.nbJoueur==2)
                    vjouer.joueur[order+2].setText(vpara.nom);

            }
        });
    }
    public void redimensionnement() {
        vjouer.miseAJour();
    }

    public void clicSouris(double x, double y) {
        int l = (int) (y / vjouer.hauteurCase());
        int c = (int) (x / vjouer.largeurCase());

        System.out.println("lPlateau = " + l);
        System.out.println("cPlateau = " + c);
        Position posPlateau = new Position(l,c);
        Position posPiece = new Position(jeu.PosPieceL,jeu.PosPieceC);
        System.out.println("pieceCorant " + jeu.pieceCourant.getNum());
        if (vpara.joueurs[joueurCourant].jeu(posPlateau,posPiece,jeu.pieceCourant)) {
            jeu.plateauPiece[vjouer.joueurCourant].enlevePiece(jeu.pieceCourant.getNum());
            vjouer.joueurCourant = jeu.joueurCourant;
            vjouer.miseAJour();
            changeJoueur();

        }
    }

    public void selectPiece(double x, double y) {
        int l = (int) (y / vjouer.hauteurCasePiece());
        int c = (int) (x / vjouer.largeurCasePiece());
        if(jeu.plateauPiece[jeu.joueurCourant].valeur(l,c)>=0 && vjouer.joueurCourant==jeu.joueurCourant) {
            jeu.setSelected(jeu.plateauPiece[jeu.joueurCourant].valeur(l,c));
            jeu.plateauAffiche.PlacerPiece(jeu.pieceCourant);
            vjouer.miseAJour();
        }
    }

    public void PieceAffiche(double x, double y) {
        int l = (int) (y / vjouer.hauteurCaseAffiche());
        int c = (int) (x / vjouer.largeurCaseAffiche());

        System.out.println("lAffiche = " + l);
        System.out.println("cAffiche = " + c);
        jeu.setPieceL(l);
        jeu.setPieceC(c);
    }

    public void initAffiche() {
        jeu.plateauAffiche.initPlateauAffiche();
        vjouer.miseAJour();
    }
    void changeJoueur() {
        joueurCourant = (joueurCourant + 1) % vpara.joueurs.length;
        decompte = lenteurAttente;
    }

       /* public boolean choisirNiveau(String niveau){
            if(!niv.equals(niveau)){
                System.out.println("Vous avez choisit " + niveau);
                niv=niveau;
            }
            switch(niveau){
                case "Easy":return joueurs[joueurCourant].tempsEcoule();
                case "Medium":return joueurs[joueurCourant].tempsEcouleNonPertant();
                case "Hard": return joueurs[joueurCourant].tempsEcouleMinimax();
                default:return false;
            }
        }*/
       /* public void basculeIA(boolean value) {
        	jeuAutomatique = value;
        	System.out.println("jeuautomatique " + jeuAutomatique);
        	//f.changeBoutonIA(value);
            if (jeuAutomatique)
            	joueurs[1] = new JoueurIA(1, jeu);
            else
            	joueurs[1] = new JoueurHumain(1, jeu);
        }*/


    public void tictac() {
		boolean b;

		if (jeu.enCours()) {
			if (decompte == 0) {
				if((b = vpara.joueurs[joueurCourant].tempsEcoule())) { // TODO // Pouvoir changer la difficulté -> Biyun
					System.out.println("joueur " + joueurCourant + " " + b);
					vjouer.joueurCourant = jeu.joueurCourant;
					vjouer.miseAJour();
					changeJoueur();
				} else {
                    if(vpara.joueurs[joueurCourant].jeu.enCoursJ[joueurCourant]) {
                        System.out.println("On vous attend, joueur " + joueurCourant);
                        decompte = lenteurAttente;
                    } else {
                        vpara.joueurs[joueurCourant].jeu.setupNextJoueur();
                        vjouer.joueurCourant = jeu.joueurCourant;

                        changeJoueur();
					}
                    vjouer.miseAJour();
				}
			} else {
				decompte--;
			}
		}
	}
}

