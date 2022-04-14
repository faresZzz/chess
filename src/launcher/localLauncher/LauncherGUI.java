package launcher.localLauncher;

import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import controler.ChessGameControlers;
import controler.controlerLocal.ChessGameControler;
import model.observable.ChessGame;
import vue.ChessGameGUI;



/**
 * @author francoise.perrin
 * Lance l'exécution d'un jeu d'échec en mode graphique.
 * La vue (ChessGameGUI) observe le modèle (ChessGame)
 * les échanges passent par le contrôleur (ChessGameControlers)
 * 
 */
public class LauncherGUI{

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ChessGame model;	
		ChessGameControler controler;
		JFrame frame;	
		Dimension dim;
	
		dim = new Dimension(1000, 1000);
		
		model = new ChessGame();	
		controler = new ChessGameControler(model);
		
		frame = new ChessGameGUI(controler);
		model.addObserver((Observer) frame);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(100, -100);
		frame.setPreferredSize(dim);
		frame.pack();
		frame.setVisible(true);
	}

}
