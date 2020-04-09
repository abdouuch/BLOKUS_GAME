package Controleur;

import java.util.Hashtable;
import java.util.Random;
import Modele.Jeu;
import Modele.Piece;
import Modele.Position;


public class JoueurIA extends Joueur {
	Random r;

	Hashtable<Object[],Boolean> Seq;

	public JoueurIA(int n, Jeu j) {
		super(n, j);
		r = new Random();

		Seq = new Hashtable<>();
	}

	boolean tempsEcoule() {
		int bound;
		Position posPlateau;
		boolean trouve = false;


		if(jeu.coord.size()>0) {
			if ((bound = jeu.piecesJ[jeu.joueurCourant].size()) != 0) {

				while (!trouve) {

					posPlateau = jeu.coord.get(r.nextInt(jeu.coord.size()));

					num = r.nextInt(bound);

					jeu.setSelected(jeu.piecesJ[jeu.joueurCourant].get(num).getNum());

					Piece choix = jeu.pieceCourant;
					Position posPiece = getPosPiece(posPlateau, choix);
					if (!(posPiece.c == -1 || posPiece.l == -1)) {
						if (jeu.plateau.placerPossible(posPlateau, posPiece, choix, jeu.joueurCourant)) {
							jeu.piecesJ[jeu.joueurCourant].remove(jeu.pieces.get(choix.getNum()));
							jeu.plateauPiece[jeu.joueurCourant].enlevePiece(choix.getNum());
							jeu.jouer(posPlateau, posPiece, choix);

							trouve = true;
						}
					}

				}
			} else {
				System.out.println("Joueur " + jeu.joueurCourant + " ne peut plus jouer !");
				jeu.enCoursJ[jeu.joueurCourant]=false;
				jeu.setupNextJoueur();
			}

		} else {
			System.out.println("Joueur " + jeu.joueurCourant + " ne peut plus jouer !");
			jeu.enCoursJ[jeu.joueurCourant]=false;
			jeu.setupNextJoueur();
		}


		return trouve;
	}

	Position getPosPiece(Position posPl, Piece p) {
		// TODO // Modifier cette fonction en stockant les résultats dans une table de hachage
		// TODO // ATTENTION, IL SE PEUT QUE LA PIECE CHOISIE NE PUISSE PAS ETRE POSEE !!!
		Position pos;

		for(int tries = 0; tries < 8; tries++){
			for (int i = 0; i < p.taille; i++) {
				for (int j = 0; j < p.taille; j++) {
					pos = new Position(i, j);
					if (p.carres[i][j] == true) {
						if (jeu.plateau.placerPossible(posPl, pos, p, jeu.joueurCourant)) {
							return pos;

						}

					}

				}

			}

			int tourner = r.nextInt(4);
			int inverser = r.nextInt(2);
			for (int i = 0; i < tourner; i++) {
				p.retationDroite();
			}
			if(inverser == 0){
				p.Miroir();
			}
		}

		return new Position(-1, -1);
	}



	boolean tempsEcouleNonPerdant() {
		int bound;
		Position posPlateau;
		if(jeu.coord.size()>0) {
			posPlateau = jeu.coord.get(r.nextInt(jeu.coord.size()));
		}else{
			return false;
		}
		if((bound = jeu.piecesJ[jeu.joueurCourant].size()) != 0) {
			num = r.nextInt(bound);

			Piece choix = jeu.choixPiece(num);
			Position posPiece = closestToMiddle();
			//Position posPiece = nextPiece(posPlateau, choix);

			if (jeu.placerPossible(posPlateau, posPiece, choix)) {
				jeu.jouer(posPlateau, posPiece, choix);
				jeu.piecesJ[jeu.joueurCourant].remove(jeu.pieces.get(choix.getNum()));
				return true;
			}
		}
		return false;
	}


	/*Position nextPiece(Position posPl, Piece p){


	}*/

	Position closestToMiddle(){
		Position closest = basePos();

		for(Position pos : jeu.coord){
			if((jeu.joueurCourant == 1) && ((pos.l > closest.l) ||  ((pos.c > closest.c)))){
				closest = pos;
			}
			if((jeu.joueurCourant == 2) && ((pos.l > closest.l) || ((pos.c < closest.c)))){
				closest = pos;
			}
			if((jeu.joueurCourant == 3) && ((pos.l < closest.l) || ((pos.c < closest.c)))){
				closest = pos;
			}
			if((jeu.joueurCourant == 0) && ((pos.l < closest.l) || ((pos.c > closest.c)))){
				closest = pos;
			}
		}

		return closest;

	}

	Position basePos(){
		switch(jeu.joueurCourant){
			case 0:
				return new Position(jeu.plateau.taille()-1,0);
			case 1:
				return new Position(0,0);
			case 2:
				return new Position(0,jeu.plateau.taille()-1);
			case 3:
				return new Position(jeu.plateau.taille()-1,jeu.plateau.taille()-1);
		}
		return new Position(0,0);
	}

	boolean tempsEcouleMinimax(){
		int iandj [] = new int [2];

		int[][] p =  jeu.plateau.p;
		//nextStep(iandj, p);

		System.out.println("Chosen next step : " + iandj[0] + " " + iandj[1]);

		//plateau.jouer(iandj[0], iandj[1], piece);

		return true;

	}
/*
	void nextStep(int[] iandj, int[][] p){

		int Profondeur = MAXPROF;

		int player = plateau.joueurCourant;

		int values [] = Minimax(p,Profondeur, player);

		iandj[0] = values[1];
		iandj[1] = values[2];

	}

	int [] Minimax(int[][] p, int profondeur, int player){


		//System.out.println("IN IA : " + profondeur);

		List<int[]> allSteps = nextPossibleSteps(p);

		int nextScore=0;

		int finiteIJ [] = new int [2];
		int finiteScore = 0;

		if(profondeur == 0 || end(p)){
			finiteScore = evaluation(p, player);
		} else {
			if (player == 0) {
				int value = -10000;

				for (int[] iandj : allSteps) {
					//System.out.println("P0 "+iandj[0]+" "+iandj[1]);
					int[][] config = nextConfig(p, iandj, OtherPlayer(player));
					if (value < (nextScore = Minimax(config, profondeur - 1, OtherPlayer(player))[0]) ){
						value = nextScore;
						finiteIJ = iandj;
					}
				}
				finiteScore =  value;

			} else {
				int value = 10000;

				for (int[] iandj : allSteps) {
					//System.out.println("P1 "+iandj[0]+" "+iandj[1]);
					int[][] config = nextConfig(p, iandj, OtherPlayer(player));
					if (value > (nextScore = Minimax(config, profondeur - 1, OtherPlayer(player))[0])) {
						value = nextScore;
						finiteIJ = iandj;
					}
				}
				finiteScore = value;

			}
		}

		return new int[] {finiteScore, finiteIJ[0], finiteIJ[1]};
	}

	List<int[]> nextPossibleSteps(int [][] p){
		List<int[]> Steps = new ArrayList<int[]>();


		if(end(p)){
			return Steps;
		}

		for(int i=0; i<plateau.taille();i++){
			for(int j=0; j<plateau.taille();j++){
				if((p[i][j] == -1) && (!plateau.coupPoison(i, j))){
					Steps.add(new int[] {i, j});
				}
			}
		}

		return Steps;

	}

	int [][] nextConfig(int[][] p, int [] iandj, int OPlayer) {
		int[][] Config = new int[plateau.taille()][plateau.taille()];

		for (int i = 0 ;i<plateau.taille();i++){
			for(int j =0;j<plateau.taille();j++){
				if(!plateau.coupPoison(i, j)) {
					if ((i == iandj[0] && j == iandj[1]) && p[i][j] != 1) {
						if (OPlayer == 0) {
							Config[i][j] = 2;
						} else {
							Config[i][j] = 6;
						}

					} else if ((i >= iandj[0] && j >= iandj[1]) && p[i][j] != 1) {
						if(p[i][j] == 4 || p[i][j] == 8){
							Config[i][j] = 10;
						}else {
							if (OPlayer == 0) {
								Config[i][j] = 4;
							} else {
								Config[i][j] = 8;
							}
						}

					} else {
						Config[i][j] = p[i][j];
					}
				}
			}
		}

		return Config;



	}

	int evaluation(int [][] p, int player){
		int score = 0;

		if((player == 0) && (plateau.joueurCourant == 1)) {
			score = -10;
		} else if((player == 0) && (plateau.joueurCourant == 0)) {
			score = 10;
		} else if((player == 1) && (plateau.joueurCourant == 0)) {
			score = -10;
		} else if((player == 1) && (plateau.joueurCourant == 1)) {
			score = 10;
		}

		return score;
	}

	int OtherPlayer(int player){
		if(player == 0){
			return 1;
		} else {
			return 0;
		}
	}

	boolean end(int[][] p) {
		return ((p[0][1] != -1) && (p[1][0] != -1));

	}
*/

}
