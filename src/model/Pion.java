package model;


public abstract  class Pion extends AbstractPiece implements Pions{
	
	
	
	public Pion(Couleur couleur ,Coord coord) {
		// TODO Auto-generated constructor stub
		super(couleur, coord);
		
	}
	
	// probleme de generalisation puisque le mouvement generique ( avancement du pion ) differere avec la couleur
	abstract protected  boolean  specifiqueMoveOk(int xFinal, int yFinal);
	
	public final boolean isMoveOk(int xFinal, int yFinal)
	{
		boolean moveValide = false;
		if (specifiqueMoveOk(xFinal, yFinal) )
		{
			moveValide = true;
		}
		return moveValide;
	}

	
}
