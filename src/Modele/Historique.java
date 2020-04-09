/*package Modele;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Stack;

public class Historique<E> implements HistoriqueInterface<E>  {
	Stack<E> memoire;
	String fichier;
    String rep="historique/";
	
	public Historique(String fichier) {
		this.fichier = rep+fichier;
		memoire = new Stack <>();
	}
	
	@Override
	public boolean peutAnnuler() {
		return !memoire.isEmpty();
	}


	@Override
	public boolean peutRefaire() {
		return !memoire.isEmpty();
	}

	@Override
	public E annuler() {
		if(peutAnnuler())
			return memoire.pop();
		return null;
	}


	@Override
	public void refaire() {
		if(peutAnnuler()){
			memoire.clear();
		}		
	}

	
	@Override
    public  void save(){
          FileOutputStream fileOut;
          try {
            fileOut = new FileOutputStream(fichier);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(memoire);
            out.close();
            fileOut.close();
            System.out.println("Enregistrer une partie");
            
          } catch (FileNotFoundException e) {
                 System.err.println("Le fichier "+fichier+"n'existe pas");
          } catch (IOException e) {
        	  System.err.println("Probleme d'enregistrement des donnees");
          }  
   }
    
	
    @Override
    public  E load(String fichier){
    	fichier = rep+ fichier;
        E  obj = null;
   
        try {
                FileInputStream fileIn = new FileInputStream(fichier);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                obj = (E) in.readObject();
                System.out.println("Charger les parties du fichier "+ fichier);
                
        } catch (FileNotFoundException e) {
        	System.err.println("Le fichier "+fichier+"n'existe pas");
        } catch (IOException e) {
        	System.err.println("Probleme du chargement des donnees");
        } catch (ClassNotFoundException e) {
        	System.err.println("Class non trouvee");
        }
        this.memoire = (Stack<E>) obj;
        return obj;
    }
    
    
 
    Jeu copy(Jeu aux,Jeu jeu) {    
			for(int i=0; i<20; i++) {
				for(int j=0; j<20; j++ ) {
					aux.enCours = jeu.enCours;
					aux.joueurCourant = jeu.joueurCourant;
					aux .piecesJ= jeu.piecesJ;
					aux.plateau= jeu.plateau;
					aux.pieces = jeu.pieces;
					aux.coord = jeu.coord;
					aux.plateauPiece= jeu.plateauPiece;
					aux.l = jeu.l;
					aux.fichierPieces= jeu.fichierPieces;
				}
			}
		return aux;
	}
    

	@Override
	public void add(E obj) {
		
		if(obj.getClass().getSimpleName().toString().compareTo("Jeu")==0) {
					Jeu jeu = new Jeu(20);
					obj=(E)copy(jeu,(Jeu)obj);
		}
		
		 memoire.push(obj);
	}


}
*/