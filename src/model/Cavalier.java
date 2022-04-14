package model;

public class Cavalier extends AbstractPiece{
	
	
	public Cavalier(Couleur couleur ,Coord coord) {
		// TODO Auto-generated constructor stub
		super(couleur, coord);
	}
	@Override
	public boolean isMoveOk(int xFinal, int yFinal) {
		// TODO Auto-generated method stub
		boolean ret = false;
		if (( Math.abs( xFinal - getX() ) + Math.abs( yFinal- getY()) == 3 ) &&
			( Math.abs( xFinal - getX() ) > 0 && Math.abs( yFinal - getY() ) > 0 ))
		{
			ret = true;
		}
		return ret;
	}
}
