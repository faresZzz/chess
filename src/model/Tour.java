package model;

public class Tour extends AbstractPiece{
	
	public Tour(Couleur couleur ,Coord coord) 
	{
		// TODO Auto-generated constructor stub
		super(couleur, coord);
	}
	@Override
	public boolean isMoveOk(int xFinal, int yFinal) 
	{
		// TODO Auto-generated method stub
		boolean ret = false;
		if ( yFinal == this.getY()  || xFinal == this.getX() )
		{
			ret = true;
		}
		return ret;
	}

}
