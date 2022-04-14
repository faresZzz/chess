package model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BooleanSupplier;

import javax.swing.DefaultBoundedRangeModel;

import tools.ChessPiecesFactory;
import tools.ChessSinglePieceFactory;
import tools.Introspection;

import java.util.List;


public class Jeu 
{
//	 stack to keep trace of moves
	private Deque<Coord> lastPieceFinalPos;
	private Deque<Coord> lastPieceInitPos;
	
//	stack to keep track of pieces captured
	private Deque<Coord> coordLastPieceCapture;
	private Deque<Integer> listPieceCapture;
	
	private List<Pieces> listePiece;
	
	
	public Jeu(Couleur couleur) {
		listePiece =  ChessPiecesFactory.newPieces(couleur);
		this.lastPieceFinalPos = new ArrayDeque<Coord>();
		this.lastPieceInitPos = new ArrayDeque<Coord>();
		this.listPieceCapture = new ArrayDeque<Integer>();
		this.coordLastPieceCapture = new ArrayDeque<Coord>();
	}
	
	private Pieces findPiece(int posX, int posY)
	{
		Pieces pieceRetoune = null;
		for (Pieces piece: this.listePiece)
		{
			if ( piece.getX() == posX && piece.getY() == posY)
			{
				pieceRetoune =  piece;
			}
		}
		return pieceRetoune;
	}
	
	public boolean isPieceHere(int x, int y)
	{
		return this.findPiece(x, y) == null ? false : true;
	}
	
	public boolean isSpecialPionOk(int xInit, int yInit, int xFinal, int yFinal)
	{
		boolean ret = false;
		Pieces piece = this.findPiece(xInit, yInit);
		if(piece != null)
		{
			if (piece instanceof Pions )
			{
				
				ret =  ((Pions) piece).isMoveDiagOk(xInit, yInit);
			}
		}
		
		return ret;
	}
	public boolean isMoveOk(int xInit, int yInit, int xFinal, int yFinal )
	{
		boolean movValide = false;
		Pieces piece = this.findPiece(xInit, yInit);
		if(piece != null)
		{
			movValide = piece.isMoveOk(xFinal, yFinal);
		}
		
		return movValide;
	}
	
	public boolean move(int xInit, int yInit, int xFinal, int yFinal)
	{
		boolean movEffectue = false;
		if (this.isPieceHere(xInit, yInit))
		{
			Pieces pieces = this.findPiece(xInit, yInit);
			if(this.isMoveOk(xInit, yInit, xFinal, yFinal))
			{
				movEffectue = pieces.move(xFinal, yFinal);
				if (movEffectue)
				{
					// enregistrement du dernier mouvement effectuer pour pouvoir le undo si besoin 
					this.lastPieceInitPos.push( new Coord(xInit, yInit) );
					this.lastPieceFinalPos.push( new Coord(xFinal, yFinal));
				}
				
			}
		}
		
	
		return movEffectue;
	}
	
	public void setPossibleCapture()
	{
		; // Si une capture d'une pièce de l'autre jeu est possible met à jour 1 booléen
		// pas compris puisque pas acces au piece de l'autre jeu dans la classe jeu
	}
	
	public boolean capture(int xCatch, int yCatch)
	{
		boolean capture = false;
		Pieces pieceCapture = this.findPiece(xCatch, yCatch);
		if (pieceCapture != null)
		{
			Coord coordPiece = new Coord(pieceCapture.getX(), pieceCapture.getY());
			capture = pieceCapture.capture();
			if (capture)
			{
				this.listPieceCapture.push(this.listePiece.indexOf(pieceCapture));
				this.coordLastPieceCapture.push(coordPiece);
			}
			
		}
		return capture;
	}
	
	public Couleur getPieceColor(int x, int y)
	{
		Couleur colorPiece = null;
		Pieces piece = this.findPiece(x, y);
		if (piece != null)
		{
			colorPiece = piece.getCouleur();
			
		}
		
		return colorPiece;
	}
	
	public String getPieceType(int x, int y)
	{
		String typePiece = null;
		Pieces piece = this.findPiece(x, y);
		if (piece != null)
		{
			typePiece = piece.getClass().getSimpleName();
			
		}
		
		return typePiece;
	}
	
	public Couleur getCouleur()
	{
		return this.listePiece.get(0).getCouleur();
		
	}
	
	/**
	 * @return une vue de la liste des pièces en cours
	 * ne donnant que des accès en lecture sur des PieceIHM
	 * (type piece + couleur + liste de coordonnées)
	 */
	public List<PieceIHM> getPiecesIHM()
	{ 
		 PieceIHM newPieceIHM = null; 
		 List<PieceIHM> list = new LinkedList<PieceIHM>(); 
		 
		 for (Pieces piece : this.listePiece)
		 { 
			 boolean existe = false; 
			 // si le type de piece existe déjà dans la liste de PieceIHM
			 // ajout des coordonnées de la pièce dans la liste de Coord de ce type 
			 // si elle est toujours en jeu (x et y != -1)
			 for ( PieceIHM pieceIHM : list)
			 { 
				 if ((pieceIHM.getTypePiece()).equals(piece.getClass().getSimpleName()))
				 { 
					 existe = true; 
					 if (piece.getX() != -1)
					 { 
						pieceIHM.add(new Coord(piece.getX(), piece.getY())); 
					 } 
				 } 
			 }
			 
			 // sinon, création d'une nouvelle PieceIHM si la pièce est toujours en jeu
			 if (! existe) 
			 { 
				 if (piece.getX() != -1)
				 { 
					newPieceIHM = new PieceIHM(piece.getClass().getSimpleName(), 
					piece.getCouleur()); 
					 newPieceIHM.add(new Coord(piece.getX(), piece.getY())); 
					 list.add(newPieceIHM); 
				 } 
			 } 
		 }	
		 
		 return list; 
	 }
	
	public void setCatling()
	{
		//TODO met a jour un booleen pour activer l'hypothese d'un roque du roi
		// tester si le roi est a sa position initiale
		// tester si la tour est a sa position initiale
		// tester si une piece se trouve entre les 2
		// comprend pas ce que cela fait puisque void donc boolean attribue de la classe ??
	}
	
	public void undoMove()
	{
		if (this.lastPieceFinalPos.peek() != null && this.lastPieceInitPos.peek() != null) // si un mouvement a deja ete enregistre pour le jeu
		{
			// recuperation des coord du dernier mouvement 
			Coord initialesCoord = this.lastPieceInitPos.pop(); 
			Coord finalesCoord = this.lastPieceFinalPos.pop();
			
			if (this.isPieceHere(finalesCoord.x, finalesCoord.y)) // on verifie qu'il y a bien un piece a cette position 
			{
				Pieces pieces = this.findPiece(finalesCoord.x, finalesCoord.y); 
				pieces.move(initialesCoord.x, initialesCoord.y); // on fait le mouvement inverse
			}
		}
		// limitation si on a une promotion d'un pion puisque il est supprimer de la liste des pieces
		// captures puisque toutes les pieces sont a la meme pos finale 
	}
	
	public void undoCapture()
	{
		// si l'on veux enlever la derniere capture, on doit undo le dernier move puis replacer la piece a sa position d'origine
		if (this.listPieceCapture.peek() != null && this.coordLastPieceCapture.peek() != null)
		{
			
			Pieces pieceCapture = this.listePiece.get(this.listPieceCapture.pop());
			Coord coordPiece = this.coordLastPieceCapture.pop();
			pieceCapture.move(coordPiece.x, coordPiece.y);
		}
	}
	
	public boolean isPawnPromotion(int xFinal, int yFinal)
	{
		//TODO return true si l'on est dans le cas d'un promotion de pion
		boolean isPromotion = false;
		if (this.isPieceHere(xFinal, yFinal) && (this.findPiece(xFinal, yFinal) instanceof Pions ) ) // test instance of pas top mais sur interface
		{
			Pions pionPieces = (Pions) this.findPiece(xFinal, yFinal);
			
			isPromotion =  pionPieces.isPawnPromotion(xFinal, yFinal);
		
		}
		return isPromotion;
	}
	
	public boolean pawnPromotion(int xFinal, int yFinal, String type)
	{
		boolean promotionValid = false;
		if(this.isPawnPromotion(xFinal, yFinal))
		{
			Pieces pionPieces = this.findPiece(xFinal, yFinal);
			
			promotionValid = this.listePiece.remove((Object)pionPieces);
			if (promotionValid)
			{
				this.listePiece.add( ChessSinglePieceFactory.newPiece(pionPieces.getCouleur(), type, xFinal, yFinal));	
			}
		}
		return promotionValid;
	}
	
	public Coord getKingCoord()
	{
		Coord kingCoord = new Coord( 0,0);
		for (Pieces piece: this.listePiece)
		{

			if (this.getPieceType(piece.getX(), piece.getY()) == "Roi")
				kingCoord.x = piece.getX();
				kingCoord.y = piece.getY();
		}
		
		return kingCoord;
	}
	
	@Override
	public String toString() {
		String ret = "Jeu : ";
		for (Pieces pieces : listePiece) {
			ret += pieces.toString() +"\n";
			
		}
		return ret;
	}
	public static void main(String argv[]) {
		Jeu jeu = new Jeu(Couleur.NOIR);
		System.out.println(jeu);
	}
}
