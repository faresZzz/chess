package model;

public class Reine extends AbstractPiece
{
	public Reine(Couleur couleur ,Coord coord) 
	{
		// TODO Auto-generated constructor stub
		super(couleur, coord);
	}
	@Override
	public boolean isMoveOk(int xFinal, int yFinal) 
	{
		// TODO Auto-generated method stub
		boolean ret = false;
		if ( Math.abs(xFinal - this.getX()) == Math.abs(yFinal -this.getY())) 
		{
			ret = true;
		}
		else if ( Math.abs(xFinal - this.getX()) <= 1 && 
				  Math.abs(yFinal- this.getY()) <= 1) 
		{
			ret = true;
		}
		else if ( (xFinal != this.getX() && yFinal == this.getY()) || ((yFinal != this.getY() && xFinal == this.getX())) ){
			ret = true;
		}
		return ret;
	}
}
