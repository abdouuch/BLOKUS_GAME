package Vue;
import static blokus.Framework.app;

import Controleur.ControleurMediateur;
import Controleur.Joueur;
import Controleur.JoueurHumain;
import Controleur.JoueurIA;
import Modele.Jeu;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ViewParametre extends View{
	public ViewParametre(Jeu j) {
		super(j);
	}

	ViewJouer vj;
	ControleurMediateur c;
	private Canvas can;
	private AnchorPane pane;
	VBox boiteJeu;
	private Button retourBtn;
	private Button commencerBtn;
	private Button Jeu4Btn;
	private Button Jeu2Btn;
	public ChoiceBox<String>[] Joueur;
	public int nbJoueur=4;
	public Joueur[] joueurs;
	public Button[] joueur;
	public String nom;
	TextField text0;
	TextField text1;
	TextField text2;
	TextField text3;
	HBox JoueurBtn = new HBox();

	@Override
	public void onLaunch() {
		miseAJour();
	}

	void login(ControleurMediateur c) {	
		if(nbJoueur==4) {
			c.setNom(text0);
			c.valide(joueur[0],0);
			c.setNom(text1);
			c.valide(joueur[1],1);
			c.setNom(text2);
			c.valide(joueur[2],2);
			c.setNom(text3);
			c.valide(joueur[3],3);
		}
		else {
			c.setNom(text0);
			c.valide(joueur[0], 0);
			c.setNom(text1);
			c.valide(joueur[1], 2);
		}
		
	}

	void changeModele(ChoiceBox<String> c, int order) {
		c.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            	 if(newValue.equalsIgnoreCase("Humain")) {
                 	joueurs[order] = new JoueurHumain(order, jeu);
                 }else {
                 	joueurs[order] = new JoueurIA(order, jeu); 
                 }
            }
        });
	}

	
	@Override
	public void miseAJour() {
		vj = (ViewJouer) app.getView("Jouer");
		c = new ControleurMediateur(jeu,vj,this);
		
		can = new Canvas(600,200);
		pane = new AnchorPane(can);
		joueurs = new Joueur[4];
		joueur = new Button[4];
		
		joueurs[0] = new JoueurHumain(0, jeu);
		joueurs[1] = new JoueurHumain(1, jeu);
		joueurs[2] = new JoueurHumain(2, jeu);
		joueurs[3] = new JoueurHumain(3, jeu);
		
		HBox JeuBtn = new HBox();
		Jeu4Btn = new Button("Jeu A 4");
		Jeu4Btn.setOnAction((event)->{
			nbJoueur = 4;
			c.modifScore(4);
			miseAJour();
		});
		Jeu2Btn = new Button("Jeu A 2");
		Jeu2Btn.setOnAction((event)->{
			nbJoueur = 2;
			c.modifScore(2);
			miseAJour();
		});
		JeuBtn.getChildren().addAll(Jeu4Btn,Jeu2Btn);
		
		joueurs[0] = new JoueurHumain(0, jeu);
		retourBtn = new Button("Retour");
		retourBtn.setOnAction((event)->{
			app.gotoView("Menu");
		});
		commencerBtn = new Button("Commencer");
		commencerBtn.setOnAction((event)->{
			app.gotoView("Jouer");
		});
		Joueur = new ChoiceBox[4];
		for(int i=0;i<4;i++) {
			Joueur[i] = new ChoiceBox<String>(FXCollections.observableArrayList("Humain", "Robot simple", "Robot Intelligent","Robot Excellent"));
		    Joueur[i].getSelectionModel().selectFirst();	    
		}
		
		Label j0 = new Label("Joueur 0 (vert) :   ");
		if(nbJoueur == 2)
			j0.setText("Joueur 0 (vert et jaune) :   ");
		text0 = new TextField();
		text0.setPromptText("Entrez le nom de joueur :(moins de 7 caractères)");
		text0.setFocusTraversable(false);
		joueur[0] = new Button("OK");
		HBox J0 = new HBox(j0,Joueur[0],text0,joueur[0]);
		J0.setSpacing(10);
		
		Label j1 = new Label("Joueur 1 (bleu) :   ");
		if(nbJoueur == 2)
			j1.setText("Joueur 1 (bleu et rouge) :   ");
		text1 = new TextField();
		text1.setPromptText("Entrez le nom de joueur :(moins de 7 caractères)");
		text1.setFocusTraversable(false);
		joueur[1] = new Button("OK");
		HBox J1 = new HBox(j1,Joueur[1],text1,joueur[1]);
		J1.setSpacing(10);
		
		Label j2 = new Label("Joueur 2 (jaune) : ");
		text2 = new TextField();
		text2.setPromptText("Entrez le nom de joueur :(moins de 7 caractères)");
		text2.setFocusTraversable(false);
		joueur[2] = new Button("OK");
		HBox J2 = new HBox(j2,Joueur[2],text2,joueur[2]);
		J2.setSpacing(10);
		
		Label j3 = new Label("Joueur 3 (rouge) : ");
		text3 = new TextField();
		text3.setPromptText("Entrez le nom de joueur :(moins de 7 caractères)");
		text3.setFocusTraversable(false);
		joueur[3] = new Button("OK");
		HBox J3 = new HBox(j3,Joueur[3],text3,joueur[3]);
		J3.setSpacing(10);
		
		VBox joueurbox = new VBox();
		if(nbJoueur == 4) {
			joueurbox.getChildren().addAll(J0,J1,J2,J3);
		}
		else if(nbJoueur == 2) {
			joueurbox.getChildren().addAll(J0,J1);
		}
		//joueurbox.setAlignment(Pos.CENTER);
		joueurbox.setPadding(new Insets(10));
		joueurbox.setSpacing(20);
		pane.getChildren().add(joueurbox);
		
		boiteJeu = new VBox();
		boiteJeu.getChildren().addAll(JeuBtn,pane);
		boiteJeu.setAlignment(Pos.CENTER);
		HBox box = new HBox(retourBtn,commencerBtn);
		box.setAlignment(Pos.BOTTOM_CENTER);//居中对齐
		box.setSpacing(100);
		getPane().setBottom(box);
		getPane().setCenter(boiteJeu);
		getPane().setPadding(new Insets(50));
		
		GraphicsContext gJeu = can.getGraphicsContext2D();
		line(gJeu,can.getWidth(),can.getHeight());
		
		for(int i = 0;i < 4;i++) {
			if(nbJoueur==4)
				changeModele(Joueur[i], i);
			else if(nbJoueur==2 && i<2) {
				changeModele(Joueur[i], i);
				changeModele(Joueur[i], i+2);
			}
			else if(nbJoueur==2 && i>=2) {
			}
		}
	
		login(c);


	}

	/*@Override
	public void redimension() {
		// TODO Auto-generated method stub
		
	}*/
}
