package Controleur;

import Modele.Jeu;
import Modele.Piece;
import Modele.Position;


public abstract class Joueur {
	Jeu jeu;
	int num;

	Joueur(int n, Jeu j) {
		num = n;
		jeu = j;
	}

	int num() {
		return num;
	}

	boolean tempsEcoule() {
		return false;
	}
	boolean tempsEcouleNonPerdant() {
		return false;
	}
	boolean tempsEcouleMinimax() {return false;}

	boolean jeu(Position posPlateau, Position posPiece, Piece p) {
		return false;
	}

}