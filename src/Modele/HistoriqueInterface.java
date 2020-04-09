package Modele;

public interface HistoriqueInterface <E>{
	
	public boolean peutAnnuler();
	public boolean peutRefaire();
	public void add(E obj);
	public E annuler();
	public void refaire();
	public void save();
	public E load(String fichier);

}
