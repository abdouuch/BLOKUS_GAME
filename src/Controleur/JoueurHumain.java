package Controleur;
import Modele.Jeu;
import Modele.Piece;
import Modele.Position;

import java.util.Random;

public class JoueurHumain extends Joueur {
	public JoueurHumain(int n, Jeu p) {
		super(n, p);
	}
	boolean jeu(Position posPlateau,Position posPiece,Piece choix) {
		if(jeu.coord.size()>0) {
			if (jeu.placerPossible(posPlateau, posPiece, choix)) {
				jeu.piecesJ[jeu.joueurCourant].remove(jeu.pieces.get(choix.getNum()));
				jeu.jouer(posPlateau, posPiece, choix);

				return true;
			}
		} else {
			System.out.println("Joueur " + jeu.joueurCourant + " ne peut plus jouer !");
			jeu.enCoursJ[jeu.joueurCourant]=false;
			jeu.updateEncours();
			jeu.setupNextJoueur();
		}
		return false;
	}
}