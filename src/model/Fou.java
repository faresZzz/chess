package model;

public class Fou extends AbstractPiece
{
		
	public Fou(Couleur couleur ,Coord coord)
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
		return ret;
	}
}
