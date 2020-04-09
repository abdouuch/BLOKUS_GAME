package Vue;
import static blokus.Framework.app;

import Modele.Jeu;
import javafx.scene.control.Button;

public class ViewAide extends View{
	public ViewAide(Jeu j) {
		super(j);
	}

	private Button retourBtn;
	
	@Override
	public void onLaunch() {
		retourBtn = new Button("Retour");
		retourBtn.setOnAction((event)->{
			app.gotoView("Menu");
		});
		getPane().setCenter(retourBtn);
	}

	@Override
	public void miseAJour() {
		
	}

	/*@Override
	public void redimension() {
		// TODO Auto-generated method stub
		
	}*/
	
}
