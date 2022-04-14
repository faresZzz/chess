package model;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator.OfDouble;


public class Echiquier implements BoardGames
{
	
	private Jeu jeuCourant; 
	private Jeu jeuNonCourant; 

	private String message;
	
	public Echiquier()
	{
		this.jeuCourant = new Jeu(Couleur.BLANC);
		this.jeuNonCourant = new Jeu(Couleur.NOIR);
		
		this.message = "Initialisation des Jeux";
		
	}
	
	public void switchJoueur()
	{
		Jeu tempJeu = this.jeuCourant;
		this.jeuCourant = this.jeuNonCourant;
		this.jeuNonCourant = tempJeu;
//		this.setMessage("changement de joueur");
		
	}
	
	public List<PieceIHM> getPiecesIHM()
	{
		// envoir d'une copie des pieces (encapsulation)
		List<PieceIHM> piecesIhm = new ArrayList<PieceIHM>(this.jeuCourant.getPiecesIHM());
		piecesIhm.addAll(this.jeuNonCourant.getPiecesIHM());
		return piecesIhm;
	}
	
	private boolean coordValides(int xInit, int yInit, int xFinal, int yFinal) 
	{
		// ajout de cette fonction pour plus de lisibilite 
		// veirife uniquemement si les coordonné de deplacement sont valides
		// c.a.d : coord dans l'echiquier et coord finales differents de initiale
		boolean coordValide = false;
		if (  (xFinal != xInit) || (yFinal != yInit) )
		{
			if ( ( (0 <= xFinal)  && (xFinal <= 7) ) && 
				 ( (0 <= yFinal)  && (yFinal <= 7) ) )
			{
				coordValide = true;
			}
		}
		return coordValide;
	}
	

	public boolean isMoveOk(int xInit, int yInit, int xFinal, int yFinal)
	{
		boolean moveOK = false;
		
		if (this.coordValides(xInit, yInit, xFinal, yFinal)) // valide si les coodonnées sont valides dans le jeux
		{
			
			if(this.jeuCourant.isPieceHere(xInit, yInit)) // valide la presence d'une piece du jeu courant est au coord init
			{
//				
				// mouvement speciale de la prise en diag pour un pion, on verifie si il sagit d'un pion, si sa correspond a son algo && si il y u a un piece a capture
				// c'est tres moche de faire comme ca 
				if (this.jeuCourant.getPieceType(xInit, yInit) == "Pion")
				{ // si c'est un pion il faut verifier le deplacement speciale de la capture
					if (this.jeuCourant.isSpecialPionOk(xInit, yInit, xFinal, yFinal) && this.jeuNonCourant.isPieceHere(xFinal, yFinal))
					{
							moveOK = true;
					}
				}
				if (this.jeuCourant.isMoveOk(xInit, yInit, xFinal, yFinal)) // verifie si ca coorespond a l'algo de deplacement d'une piece
					// ce test est deja fait dans la fonction move de jeu
				{
					// manque verification d'une piece sur la trajectoire 
					
					if ( ! this.jeuCourant.isPieceHere(xFinal, yFinal)) // verifie si le jeux courant a  de pieces a l'endroit final
					{
						// aucune piece ou une piece de l'autre couleur a l'endroit final donc autorise
						moveOK = true;
					}
					else {
						
						// verifie le roque du roi pour le jeu courant
						// pour le moment on passe 
						moveOK = false;
						
					}
				}
				
			}
		}
		if (!moveOK)
		{
			this.setMessage("deplacement interdit");
		}
		
		return moveOK;			
		
	}
	
	@Override
	public boolean move(int xInit, int yInit, int xFinal, int yFinal) 
	{
		boolean moveEffective = false;
		String messageMAJ = "";
		if (this.isMoveOk(xInit, yInit, xFinal, yFinal) && this.jeuCourant.move(xInit, yInit, xFinal, yFinal)) // verifie que le move est acceptable && // si le move a bien ete effectuer
		{
			
			moveEffective = true;
			messageMAJ = "deplacement ok : ";

			// verifie la capture 
			Coord coordFinaleCoord = new Coord(xFinal, yFinal);
			boolean echecRoi = false; // getPossibleCapture
			boolean capture = this.jeuNonCourant.capture(xFinal, yFinal);
			if (capture) // verifie pour le jeu adverse si une piece a ete capture
			{
				messageMAJ += "avec capture";
			}
			else if (capture && echecRoi) // si la capture ou le roi est en echec
			{
				messageMAJ += "avec capture et echec Roi";
				this. jeuCourant.undoMove();
				this.jeuNonCourant.undoCapture();
				;
			}

			else if (echecRoi) {
				messageMAJ += "echec au Roi";
			}
			else {
				messageMAJ += "sans capture";	
			}
			
		}	
			
		this.setMessage(messageMAJ);
		return moveEffective;
	}

	@Override
	public boolean isEnd() {
		// TODO Auto-generated method stub
		// fonction appeler a chaque tour 
		// check si le roi adverse (jeuNonCourant) est en echec et s'il ne peut pas bouger et sortir de l'echec ou s'il n'y a pas de piece qui peut venir se mettre entre le 2
		// ou verifie match null (connais pas les conditions
		boolean finJeu = false;
		
		;
		return finJeu ;
	}
	
	
	private void setMessage(String nouvMessage)
	{
		this.message = nouvMessage;
	}
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return this.message;
	}

	@Override
	public Couleur getColorCurrentPlayer() {
		// TODO Auto-generated method stub
		return this.jeuCourant.getCouleur();
	}

	@Override
	public Couleur getPieceColor(int x, int y) {
		// TODO Auto-generated method stub
		return (this.jeuCourant.getPieceColor(x, y) != null) ? 
				this.jeuCourant.getPieceColor(x, y) : 
				this.jeuNonCourant.getPieceColor(x, y);
	}
	

	@Override
	public String toString() {
		return "Echiquier [jeuBlanc=" + jeuCourant.toString() + ", jeuNoir=" + jeuNonCourant.toString() + "]";
	}
	
	public static void main(String argv[]) 
	{
	
		
		Echiquier monechec = new Echiquier();
		monechec.switchJoueur();
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println(monechec.getPiecesIHM());
		
		
		
	}
}
