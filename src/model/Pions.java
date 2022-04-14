package model;

public interface Pions {
	public boolean isMoveDiagOk(int xFinal, int yFinal);
	public boolean isPawnPromotion(int xProm, int yProm);
}
