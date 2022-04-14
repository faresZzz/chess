package model;


public class PionBlanc  extends Pion
{
	private int posInitiale;
	public PionBlanc(Couleur c,Coord coord) {
		// TODO Auto-generated constructor stub
		super(Couleur.BLANC, coord);
		this.posInitiale = this.getY();
	}
	
	@Override
	
	public boolean isMoveDiagOk(int xFinal, int yFinal) {
		// TODO Auto-generated method stub
		boolean moveDiag = false;
		if ((this.getY() - yFinal == 1) && Math.abs(this.getX() - xFinal) == 1)
		{
			moveDiag = true;
		}
		return moveDiag;
	}
	
	
	@Override
	protected boolean specifiqueMoveOk(int xFinal, int yFinal) {
		
		boolean speMove = false;
		if ( (this.getX() == xFinal ) && ( yFinal < this.getY() )) // si la position est bien sur la ligne du pion && en avant par rapport a sa position ( inverser pour pionBlanc)
		{
			if ((this.getY() == this.posInitiale ) && ( this.getY() - yFinal <= 2)) // cas 1 mov, droit d'avancer de 2 cases
			{
				speMove = true;
			}
			else if (this.getY() - yFinal == 1) // dans les autres cas on doit avancer de 1 ( reculer pour le cas d'un pion blanc par rapport a l'echiquier)
			{
				speMove = true;
			}
		}
		
			
		return speMove;
	}

	

	@Override
	public boolean isPawnPromotion(int xProm, int yProm) {
		
		return yProm == 0 ;
	}
	
	
}
