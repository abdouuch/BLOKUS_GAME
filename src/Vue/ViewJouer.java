package Vue;
import static blokus.Framework.app;

import Controleur.ControleurMediateur;
import Modele.Jeu;
import Modele.Plateau;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ViewJouer extends View {
	
	public ViewJouer(Jeu j) {
		super(j);
	}
	ViewParametre vp;
	double largeurCase, hauteurCase;
	double largeurCasePiece, hauteurCasePiece;
	double largeurCaseAffiche, hauteurCaseAffiche;
	public int joueurCourant = jeu.joueurCourant;
	public VBox Score;
	private Button jouerBtn;
	private Button retourBtn;
	private Button quitBtn;
	private Button recommencerBtn;
	private Button miroirBtn;
	private Button tourneBtn;
	public Button[] joueur;
	public HBox JoueurBtn ;
	public Label joueur0;
	public Label joueur1;
	public Label joueur2;
	public Label joueur3;


	
	private Canvas canPlateau;
	private Canvas canPiece;
	private Canvas canAffiche;
	private Canvas canScore;
	
	private AnchorPane panePlateau;
	private AnchorPane panePiece;
	private AnchorPane paneAffiche;
	private AnchorPane paneScore;
	
	VBox gPlateau ;
    VBox gAffiche ;
    VBox gPiece;
	
	@Override
	public void onLaunch() {	
		vp = (ViewParametre) app.getView("Parametre");
		ControleurMediateur c = new ControleurMediateur(jeu,this,vp);
		
	
		canPlateau = new Canvas(450, 400);
		panePlateau = new AnchorPane(canPlateau);
		panePlateau.setPrefSize(450, 400);
		joueur = new Button[4];
		
		jouerBtn = new Button("Jouer");
		jouerBtn.setOnAction((event)->{
			
			if(jouerBtn.getText().equalsIgnoreCase("Jouer")) {
				jouerBtn.setText("Stop");
				jeu.enCours = true;
			}
			else if(jouerBtn.getText().equalsIgnoreCase("Stop")) {
				jouerBtn.setText("Jouer");
				jeu.enCours=false;
			}
			miseAJour();
		});
		
		recommencerBtn = new Button("Recommencer");
		recommencerBtn.setOnAction((event)->{
			jeu.refaire();
			jeu.initScores_Joueurs_Jeu();
			jouerBtn.setText("Jouer");
			jeu.enCours = false ;
			joueurCourant = jeu.joueurCourant;
			miseAJour();
			
		});
		retourBtn = new Button("Retour");
		retourBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Dialog<ButtonType> dialog = new Dialog<ButtonType>();
				dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
				dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);
				Button yes = (Button)dialog.getDialogPane().lookupButton(ButtonType.YES);
				yes.setText("Oui");
				Button no = (Button)dialog.getDialogPane().lookupButton(ButtonType.NO);
				no.setText("Non");
				
				yes.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						jeu.refaire();
						jeu.initScores_Joueurs_Jeu();
						jouerBtn.setText("Jouer");
						joueurCourant = jeu.joueurCourant;
						app.gotoView("Parametre");
						miseAJour();
					}
				});
				no.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						dialog.close();
						
					}
				});
				
				dialog.setContentText("Voulez vous aller à la page Paramètre ?");
				dialog.show();
				
			}
		});
        quitBtn = new Button("Quit");
        quitBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Dialog<ButtonType> dialog = new Dialog<ButtonType>();
				dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
				dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);
				Button yes = (Button)dialog.getDialogPane().lookupButton(ButtonType.YES);
				yes.setText("Oui");
				Button no = (Button)dialog.getDialogPane().lookupButton(ButtonType.NO);
				no.setText("Non");
				
				yes.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						app.exit();
						
					}
				});
				no.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						dialog.close();
						
					}
				});
				
				dialog.setContentText("Voulez vous terminer le jeu ?");
				dialog.show();
				
			}
		});
        miroirBtn = new Button("Miroir");
        miroirBtn.setOnAction((event)->{
        	jeu.plateauAffiche.Miroir();
        	jeu.pieceCourant.Miroir();
        	miseAJour();
        });
        
        tourneBtn = new Button("Tourner");
        tourneBtn.setOnAction((event)->{
        	jeu.plateauAffiche.retationGauche();
        	jeu.pieceCourant.retationGauche();
        	miseAJour();
        });
        
        HBox actionBtn = new HBox();
        actionBtn.getChildren().addAll(miroirBtn,tourneBtn);
        actionBtn.setSpacing(20);
        actionBtn.setAlignment(Pos.CENTER);
        
        HBox pageBtn = new HBox();
        pageBtn.getChildren().addAll(retourBtn,quitBtn);
        pageBtn.setSpacing(20);
        pageBtn.setAlignment(Pos.CENTER);
      
        HBox JoueurBtn = new HBox();
	        joueur[0] = new Button("Joueur1");
	        joueur[0].setOnAction((event)->{
	        	joueurCourant = 0;
	        	miseAJour();
	        });
	        joueur[1] = new Button("Joueur2");
	        joueur[1].setOnAction((event)->{
	        	joueurCourant = 1;
	        	miseAJour();
	        });
	        joueur[2] = new Button("Joueur3");
	        joueur[2].setOnAction((event)->{
	        	joueurCourant = 2;
	        	miseAJour();
	        });
	        joueur[3] = new Button("Joueur4");
	        joueur[3].setOnAction((event)->{
	        	joueurCourant = 3;
	        	miseAJour();
	        });
       
	    JoueurBtn.getChildren().addAll(joueur[0],joueur[1],joueur[2],joueur[3]);
	    
	    
	    Score = new VBox();
	    Label score = new Label("Score");
	    
	    Score.getChildren().add(score);
	    Score.setPadding(new Insets(20));
	    Score.setSpacing(20.0);
	    score.setAlignment(Pos.TOP_CENTER);
	    System.out.println("vj nbjoueur " + vp.nbJoueur);
	
			    joueur0 = new Label(joueur[0].getText() + ": ");
				joueur1 = new Label(joueur[1].getText() + ": ");
				joueur2 = new Label(joueur[2].getText() + ": ");
				joueur3 = new Label(joueur[3].getText() + ": ");
				Score.getChildren().addAll(joueur0,joueur1,joueur2,joueur3);
		   
        canPiece = new Canvas(450, 200);
        panePiece = new AnchorPane(canPiece);
        panePiece.setPrefSize(450, 200);
        
        canAffiche = new Canvas(200,200);
        paneAffiche = new AnchorPane(canAffiche);
        paneAffiche.setPrefSize(200,200);
        
        canScore = new Canvas(200,200);
        paneScore = new AnchorPane(canScore);
        paneScore.getChildren().add(Score);
        
        gPlateau = new VBox();
        gPiece = new VBox();
        gAffiche = new VBox();
        
        BorderPane Pane = new BorderPane();
	    
        gPlateau.getChildren().add(panePlateau);
        gPiece.getChildren().addAll(JoueurBtn,panePiece);
        gAffiche.getChildren().addAll(jouerBtn,paneScore,actionBtn,paneAffiche,recommencerBtn,pageBtn);
        gAffiche.setSpacing(20);
        gAffiche.setAlignment(Pos.CENTER);
        
        getPane().setPadding(new Insets(10));
        
        Pane.setTop(gPlateau);
        Pane.setBottom(gPiece);
        getPane().setLeft(Pane);
        getPane().setRight(gAffiche);
		
        jeu.ajouteObservateur(this);
        miseAJour();
       
   	 	
        canPlateau.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				try {
					c.initAffiche();
					c.clicSouris(e.getX(), e.getY());
					jeu.pieceCourant = null;
				}catch(ArrayIndexOutOfBoundsException exception) {
					System.out.println("select une piece!");
				}catch(NullPointerException exception) {
					System.out.println("null pointer!");
				}
			}
		});
        
        canPiece.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				c.selectPiece(e.getX(), e.getY());
			}
		});
        
        canAffiche.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				c.PieceAffiche(e.getX(), e.getY());
			}
		});

        canAffiche.setOnDragDetected(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				canAffiche.startFullDrag();
				System.out.println("detect");
				
			}
		});
        
        canAffiche.setOnMouseDragEntered(new EventHandler<MouseDragEvent>() {

			@Override
			public void handle(MouseDragEvent e) {
				System.out.println("drag");
				c.PieceAffiche(e.getX(), e.getY());
				
			}
		});
        
        canPlateau.setOnMouseDragReleased(new EventHandler<MouseDragEvent>() {

			@Override
			public void handle(MouseDragEvent e) {
				try {
					System.out.println("released");
					c.initAffiche();
					c.clicSouris(e.getX(), e.getY());
					jeu.pieceCourant = null;
				}catch(ArrayIndexOutOfBoundsException exception) {
					System.out.println("select une piece!");
				}catch(NullPointerException exception) {
					System.out.println("select une piece!");
				}
				
				
			}
		});
        new AnimationTimer() {
			@Override
			public void handle(long now) {
					c.tictac();
			}
		}.start();
	}

	
	@Override 
	public void miseAJour() {
	            double lignes = plateau.taille();
	            double colonnes = plateau.taille();
	            largeurCase = largeurPlateau() / colonnes;
	            hauteurCase = hauteurPlateau() / lignes;
	
	            GraphicsContext gPlateau = canPlateau.getGraphicsContext2D();
	            gPlateau.clearRect(0, 0, largeurPlateau(), hauteurPlateau());
	            // Grille
	            for (int i=0; i<lignes;i++) {
	                for (int j=0; j<colonnes;j++) {
	                    gPlateau.setFill(Color.LIGHTGRAY);
	                    gPlateau.fillRect(j*largeurCase, i*hauteurCase, largeurCase, hauteurCase);
	
	                }
	            }
	            for (int i=0; i<lignes+1;i++) {
	                gPlateau.strokeLine(0, i*hauteurCase, largeurPlateau(), i*hauteurCase);
	            }
	            for (int i=0; i<colonnes+1;i++) {
	                gPlateau.strokeLine(i*largeurCase, 0, i*largeurCase, hauteurPlateau());
	            }
	            // Coups
	            draw(hauteurCase, largeurCase, plateau, gPlateau);
	            
	            //canPiece
	            largeurCasePiece = largeurPiece() / 23;
	            hauteurCasePiece = hauteurPiece() / 12;
	            
	            GraphicsContext gPiece = canPiece.getGraphicsContext2D();
	            line(gPiece,largeurPiece(),hauteurPiece());
	            draw(hauteurCasePiece, largeurCasePiece, plateauPiece[joueurCourant], gPiece);
	            
	
	            //canAffiche
	            largeurCaseAffiche = largeurAffiche() / 5;
	            hauteurCaseAffiche = hauteurAffiche() / 5;
	            GraphicsContext gAffiche = canAffiche.getGraphicsContext2D();
	            
	            line(gAffiche,largeurAffiche(),hauteurAffiche());
	            draw(hauteurCaseAffiche, largeurCaseAffiche, plateauAffiche, gAffiche);
	            
	            
	            //canScore
	            GraphicsContext gScore = canScore.getGraphicsContext2D();
	            line(gScore,largeurAffiche(),hauteurAffiche());
	            
	            if(vp.nbJoueur==4) {
		            joueur0.setText(joueur[0].getText() + ": " + jeu.Score[0]);
		    		joueur1.setText(joueur[1].getText() + ": " + jeu.Score[1]);
		    		joueur2.setText(joueur[2].getText() + ": " + jeu.Score[2]);
		    		joueur3.setText(joueur[3].getText() + ": " + jeu.Score[3]);
	            }
	            if(vp.nbJoueur==2) {
		            joueur0.setText(joueur[0].getText() + ": " + (jeu.Score[0]+jeu.Score[2]));
		    		joueur1.setText(joueur[1].getText() + ": " + (jeu.Score[1]+jeu.Score[3]));
		    		
	            }
	           
	}

	public void Init_vuejouer() {
		canPlateau = new Canvas(450, 400);
		panePlateau = new AnchorPane(canPlateau);
		panePlateau.setPrefSize(450, 400);
		joueur = new Button[4];
		
		jouerBtn = new Button("Jouer");
		jouerBtn.setOnAction((event)->{
			
			if(jouerBtn.getText().equalsIgnoreCase("Jouer")) {
				jouerBtn.setText("Stop");
				jeu.enCours = true;
			}
			else if(jouerBtn.getText().equalsIgnoreCase("Stop")) {
				jouerBtn.setText("Jouer");
				jeu.enCours=false;
			}
			miseAJour();
		});
		
		recommencerBtn = new Button("Recommencer");
		recommencerBtn.setOnAction((event)->{
			jeu.refaire();
			jouerBtn.setText("Jouer");
			jeu.enCours = false ;
			joueurCourant = jeu.joueurCourant;
			miseAJour();
			
		});
		retourBtn = new Button("Retour");
		retourBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Dialog<ButtonType> dialog = new Dialog<ButtonType>();
				dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
				dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);
				Button yes = (Button)dialog.getDialogPane().lookupButton(ButtonType.YES);
				yes.setText("Oui");
				Button no = (Button)dialog.getDialogPane().lookupButton(ButtonType.NO);
				no.setText("Non");
				
				yes.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						jeu.refaire();
						System.out.println("jeu.joueurCourant " + jeu.joueurCourant);
						joueurCourant = jeu.joueurCourant;
						app.gotoView("Parametre");
						miseAJour();
					}
				});
				no.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						dialog.close();
						
					}
				});
				
				dialog.setContentText("Voulez vous aller à la page Paramètre ?");
				dialog.show();
				
			}
		});
        quitBtn = new Button("Quit");
        quitBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Dialog<ButtonType> dialog = new Dialog<ButtonType>();
				dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
				dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);
				Button yes = (Button)dialog.getDialogPane().lookupButton(ButtonType.YES);
				yes.setText("Oui");
				Button no = (Button)dialog.getDialogPane().lookupButton(ButtonType.NO);
				no.setText("Non");
				
				yes.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						app.exit();
						
					}
				});
				no.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						dialog.close();
						
					}
				});
				
				dialog.setContentText("Voulez vous terminer le jeu ?");
				dialog.show();
				
			}
		});
        miroirBtn = new Button("Miroir");
        miroirBtn.setOnAction((event)->{
        	jeu.plateauAffiche.Miroir();
        	jeu.pieceCourant.Miroir();
        	miseAJour();
        });
        
        tourneBtn = new Button("Tourner");
        tourneBtn.setOnAction((event)->{
        	jeu.plateauAffiche.retationGauche();
        	jeu.pieceCourant.retationGauche();
        	miseAJour();
        });
        
        HBox actionBtn = new HBox();
        actionBtn.getChildren().addAll(miroirBtn,tourneBtn);
        actionBtn.setSpacing(20);
        actionBtn.setAlignment(Pos.CENTER);
        
        HBox pageBtn = new HBox();
        pageBtn.getChildren().addAll(retourBtn,quitBtn);
        pageBtn.setSpacing(20);
        pageBtn.setAlignment(Pos.CENTER);
      
        HBox JoueurBtn = new HBox();
	        joueur[0] = new Button("Joueur1");
	        joueur[0].setOnAction((event)->{
	        	joueurCourant = 0;
	        	miseAJour();
	        });
	        joueur[1] = new Button("Joueur2");
	        joueur[1].setOnAction((event)->{
	        	joueurCourant = 1;
	        	miseAJour();
	        });
	        joueur[2] = new Button("Joueur3");
	        joueur[2].setOnAction((event)->{
	        	joueurCourant = 2;
	        	miseAJour();
	        });
	        joueur[3] = new Button("Joueur4");
	        joueur[3].setOnAction((event)->{
	        	joueurCourant = 3;
	        	miseAJour();
	        });
       
	    JoueurBtn.getChildren().addAll(joueur[0],joueur[1],joueur[2],joueur[3]);
	    
	    
	    VBox Score = new VBox();
	    Label score = new Label("Score");
	    
	    Score.getChildren().add(score);
	    Score.setPadding(new Insets(20));
	    Score.setSpacing(20.0);
	    score.setAlignment(Pos.TOP_CENTER);
	    System.out.println("vj nbjoueur " + vp.nbJoueur);
	    try {
		    if(vp.nbJoueur==4) {
			    joueur0 = new Label(joueur[0].getText() + ": ");
				Score.getChildren().add(joueur0);
				joueur1 = new Label(joueur[1].getText() + ": ");
				Score.getChildren().add(joueur1);
				joueur2 = new Label(joueur[2].getText() + ": ");
				Score.getChildren().add(joueur2);
				joueur3 = new Label(joueur[3].getText() + ": ");
				Score.getChildren().add(joueur3);
		    }
		    if(vp.nbJoueur==2) {
			    joueur0 = new Label(joueur[0].getText() + ": ");
				Score.getChildren().add(joueur0);
				joueur1 = new Label(joueur[1].getText() + ": ");
				Score.getChildren().add(joueur1);
		    }
	    }catch(NullPointerException e) {
	    	System.out.println("vj: pas de nbjoueur");
	    }
        canPiece = new Canvas(450, 200);
        panePiece = new AnchorPane(canPiece);
        panePiece.setPrefSize(450, 200);
        
        canAffiche = new Canvas(200,200);
        paneAffiche = new AnchorPane(canAffiche);
        paneAffiche.setPrefSize(200,200);
        
        canScore = new Canvas(200,200);
        paneScore = new AnchorPane(canScore);
        paneScore.getChildren().add(Score);
        
        gPlateau = new VBox();
        gPiece = new VBox();
        gAffiche = new VBox();
        
        BorderPane Pane = new BorderPane();
	    
        gPlateau.getChildren().add(panePlateau);
        gPiece.getChildren().addAll(JoueurBtn,panePiece);
        gAffiche.getChildren().addAll(jouerBtn,paneScore,actionBtn,paneAffiche,recommencerBtn,pageBtn);
        gAffiche.setSpacing(20);
        gAffiche.setAlignment(Pos.CENTER);
        
        getPane().setPadding(new Insets(10));
        
        Pane.setTop(gPlateau);
        Pane.setBottom(gPiece);
        getPane().setLeft(Pane);
        getPane().setRight(gAffiche);

	}
	/*@Override
	public void redimension() {
		System.out.println("redimensionner");
        
        InvalidationListener listener = new InvalidationListener() {
			@Override
			public void invalidated(Observable observable) {
				miseAJour();	
			}
		};
        	 canAffiche.widthProperty().addListener(listener);
        	 canAffiche.heightProperty().addListener(listener); 
        	 canPiece.widthProperty().addListener(listener);
        	 canPiece.heightProperty().addListener(listener); 
        	 canPlateau.widthProperty().addListener(listener);
        	 canPlateau.heightProperty().addListener(listener); 
	}*/
	
	void draw(double hauteur, double largeur, Plateau p, GraphicsContext g) {
		Color color = null;
		
		for (int i=0; i<p.p.length; i++) {
            for (int j=0; j<p.p[0].length; j++) {
               if(p==plateau) {
            	   switch (p.valeur(i, j)) {
	            	   case -1:
	                       break;
	                   case 0:
	                       g.setFill(Color.DARKOLIVEGREEN);
	                       g.fillRect(j*largeurCase, i*hauteurCase, largeurCase, hauteurCase);
	                       break;
	                   case 1:
	                       g.setFill(Color.DARKSLATEBLUE);
	                       g.fillRect(j*largeurCase, i*hauteurCase, largeurCase, hauteurCase);
	                       break;
	                   case 2:
	                       g.setFill(Color.YELLOW);
	                       g.fillRect(j*largeurCase, i*hauteurCase, largeurCase, hauteurCase);
	                       break;
	                   case 3:
	                       g.setFill(Color.INDIANRED);
	                       g.fillRect(j*largeurCase, i*hauteurCase, largeurCase, hauteurCase);
	                       break;
	                   case 8:
	                       g.setFill(Color.LIGHTGREEN);
	                       g.fillRect(j*largeurCase, i*hauteurCase, largeurCase, hauteurCase);
	                       break;

            	   }
               }
               if(p==plateauPiece[joueurCourant]) {
            	   switch (joueurCourant) {
		       	   		case 0:
		       	   			color = Color.DARKOLIVEGREEN;
		       	   			break;
		       	   		case 1:
		       	            color = Color.DARKSLATEBLUE;
		       	            break;
		       	        case 2:
		       	            color = Color.YELLOW;
		       	            break;
		       	        case 3:
		       	             color = Color.INDIANRED;
		       	            break;
		       		}
            	   switch (p.valeur(i, j)) {
            	   		case -1:
            	   			break;
            	   		case -2:
		            	   	g.setFill(Color.LIGHTGREY);
		            	   	g.fillRect(j*largeur, i*hauteur, largeur, hauteur);
		            	   	g.strokeLine(j*largeur, i*hauteur, (j+1)*largeur, i*hauteur);
			           	   	g.strokeLine(j*largeur, i*hauteur, j*largeur, (i+1)*hauteur);
			           	   	g.strokeLine(j*largeur, (i+1)*hauteur, (j+1)*largeur, (i+1)*hauteur);
			           	   	g.strokeLine((j+1)*largeur, i*hauteur, (j+1)*largeur, (i+1)*hauteur);
			           	   	break;
            	   		default:
            	   			g.setFill(color);
		            	   	g.fillRect(j*largeur, i*hauteur, largeur, hauteur);
		            	   	g.strokeLine(j*largeur, i*hauteur, (j+1)*largeur, i*hauteur);
			           	   	g.strokeLine(j*largeur, i*hauteur, j*largeur, (i+1)*hauteur);
			           	   	g.strokeLine(j*largeur, (i+1)*hauteur, (j+1)*largeur, (i+1)*hauteur);
			           	   	g.strokeLine((j+1)*largeur, i*hauteur, (j+1)*largeur, (i+1)*hauteur);
			           	   	break;
		            	   	
            	   }     
            	    
               }
               if(p==plateauAffiche && p.valeurB(i, j)) {
            	   switch (jeu.joueurCourant) {
		       	   		case 0:
		       	   			color = Color.DARKOLIVEGREEN;
		       	   			break;
		       	   		case 1:
		       	            color = Color.DARKSLATEBLUE;
		       	            break;
		       	        case 2:
		       	            color = Color.YELLOW;
		       	            break;
		       	        case 3:
		       	             color = Color.INDIANRED;
		       	            break;
		       		}
            	    g.setFill(color);
	           	   	g.fillRect(j*largeur, i*hauteur, largeur, hauteur);
	           	   	g.strokeLine(j*largeur, i*hauteur, (j+1)*largeur, i*hauteur);
	           	   	g.strokeLine(j*largeur, i*hauteur, j*largeur, (i+1)*hauteur);
	           	   	g.strokeLine(j*largeur, (i+1)*hauteur, (j+1)*largeur, (i+1)*hauteur);
	           	   	g.strokeLine((j+1)*largeur, i*hauteur, (j+1)*largeur, (i+1)*hauteur);
               }
            }
        }
	}
	
	double largeurPlateau() {
		return canPlateau.getWidth();
	}

	double hauteurPlateau() {
		return canPlateau.getHeight();
	}
	
	double largeurPiece() {
		return canPiece.getWidth();
	}

	double hauteurPiece() {
		return canPiece.getHeight();
	}
	
	double largeurAffiche() {
		return canAffiche.getWidth();
	}

	double hauteurAffiche() {
		return canAffiche.getHeight();
	}

	public double largeurCase() {
		return largeurCase;
	}

	public double hauteurCase() {
		return hauteurCase;
	}

	public double largeurCasePiece() {
		return largeurCasePiece;
	}

	public double hauteurCasePiece() {
		return hauteurCasePiece;
	}	


	public double largeurCaseAffiche() {
		return largeurCaseAffiche;
	}

	public double hauteurCaseAffiche() {
		return hauteurCaseAffiche;
	}


	
}
