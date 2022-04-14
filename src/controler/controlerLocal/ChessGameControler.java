package controler.controlerLocal;

import model.Coord;
import model.observable.ChessGame;
import controler.AbstractChessGameControler;

/**
 * @author francoise.perrin
 * 
 *         Ce controleur local précise comment empêcher un joueur à qui ce n'est pas le tour 
 *         de jouer, de déplacer une image de pièce sur le damier
 *
 */
public class ChessGameControler extends AbstractChessGameControler implements Runnable{
	
	public ChessGameControler(ChessGame chessGame) {
		super(chessGame);
	}

	/* (non-Javadoc)
	 * @see controler.AbstractChessGameControler#isPlayerOK(model.Coord)
	 * 
	 * cette méthode vérifie que la couleur de la pièce que l'utilisateur
	 * tente de déplacer est bien celle du jeu courant
	 * la vue se servira de cette information pour empêcher tout déplacement sur le damier
	 */
	@Override
	public boolean isPlayerOK(Coord initCoord) 
	{
		boolean playerOK = false;
		if (this.chessGame.getColorCurrentPlayer() == this.chessGame.getPieceColor(initCoord.x, initCoord.y) ) 
		{
			playerOK = true;
		}
		return playerOK;
		// ToDo
	}
	
	/* (non-Javadoc)
	 * @see controler.AbstractChessGameControler#endMove(model.Coord, model.Coord, java.lang.String)
	 * 
	 * Pas d'action supplémentaire dans un controleur local en fin de move
	 */
	@Override
	protected void endMove(Coord initCoord, Coord finalCoord, String promotionType) 
	{
		// utilise pour le jeu multijoueur ?
		// on envoit les parametres a l'autre joueur ?
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// ????????
	}

	
}
