package vue;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import controler.ChessGameControlers;
import controler.controlerLocal.ChessGameControler;
import model.Coord;
import model.Couleur;
import model.PieceIHM;
import model.Pieces;
import model.observable.ChessGame;
import tools.ChessImageProvider;
import tools.ChessPiecePos;
import tools.Introspection;
 
public class ChessGameGUI extends JFrame implements MouseListener, MouseMotionListener, Observer 
{
	JLayeredPane layeredPane;
	JPanel chessBoard;
	JLabel chessPiece;
	int xAdjustment;
	int yAdjustment;
	ChessGameControlers chessGameControler;
	int posPieceInitX;
	int posPieceInitY;
	int posPieceFinalX;
	int posPieceFinalY;
	Container parentContainer;

  
  
  public ChessGameGUI(ChessGameControlers controler)
  {
	  this.chessGameControler = controler;
	  Dimension boardSize = new Dimension(850, 850);
 
	  //  Use a Layered Pane for this this application
	  layeredPane = new JLayeredPane();
	  getContentPane().add(layeredPane);
	  layeredPane.setPreferredSize(boardSize);
	  layeredPane.addMouseListener(this);
	  layeredPane.addMouseMotionListener(this);
	
	  //Add a chess board to the Layered Pane 
	 
	  chessBoard = new JPanel();
	  layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
	  chessBoard.setLayout( new GridLayout(8, 8) );
	  chessBoard.setPreferredSize( boardSize );
	  chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);
 
	  for (int i = 0; i < 64; i++) {
		  JPanel square = new JPanel( new BorderLayout() );
		  chessBoard.add( square );
		 
		  int row = (i / 8) % 2;
		  if (row == 0)
			  square.setBackground( i % 2 == 0 ? Color.white : Color.black );
		  else
			  square.setBackground( i % 2 == 0 ? Color.black : Color.white );
	  }
 
	  //Add a few pieces to the board
 
	  for (int i = 0; i < ChessPiecePos.values().length; i++) {
		Couleur pieceCouleur = ChessPiecePos.values()[i].couleur;
		for (int j = 0; j < (ChessPiecePos.values()[i].coords).length; j++) {
			String className = ChessPiecePos.values()[i].nom;	// attention au chemin
			Coord pieceCoord = ChessPiecePos.values()[i].coords[j];
			JLabel piece = new JLabel( new ImageIcon(ChessImageProvider.getImageFile(className, pieceCouleur)) );
			JPanel panel = (JPanel)chessBoard.getComponent(pieceCoord.x + pieceCoord.y*8);
			panel.add(piece);
					}
		}
	}
 
  
  
		
  
  
 
  public void mousePressed(MouseEvent e)
  {
	  chessPiece = null;
	  Component c =  chessBoard.findComponentAt(e.getX(), e.getY());
	
	  if (c instanceof JPanel) 
	  return;
	  this.posPieceInitX = e.getX()  /100;
	  this.posPieceInitY = e.getY()  /100;
	  if (this.chessGameControler.isPlayerOK(new Coord(posPieceInitX, posPieceInitY))) 	// mauvais  point de vu MVC mais prefere laisser comme ca pour empecher de 
	  {  																				//	pouvoir selectionner une piece du mauvais jeu
	  
		  this.parentContainer = c.getParent();
		  Point parentLocation = parentContainer.getLocation();
		  xAdjustment = parentLocation.x - e.getX();
		  yAdjustment = parentLocation.y - e.getY();
		  chessPiece = (JLabel)c;
		  chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
		  chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
		  layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
		  
		  
	  }
  }
 
  //Move the chess piece around
  
  public void mouseDragged(MouseEvent me) 
  {
	  if (chessPiece == null) return;
	  chessPiece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
 }
 
  //Drop the chess piece back onto the chess board
 
  public void mouseReleased(MouseEvent e) 
  {
	  if(chessPiece == null) return;
 
	  chessPiece.setVisible(false);
	
	 
	  
	 

	  Component c =  chessBoard.findComponentAt(e.getX(), e.getY());
	  this.posPieceFinalX = e.getX()  /100;
	  this.posPieceFinalY = e.getY()  /100;
	  
	  chessGameControler.move(new Coord(this.posPieceInitX,this.posPieceInitY), new Coord(this.posPieceFinalX, this.posPieceFinalY));
	  
	  layeredPane.remove(chessPiece);
	 
	  
//	  if (c instanceof JLabel){
//		  Container parent = c.getParent();
//		  if (parent != null)
//		  parent.remove(0);
//		  parent.add( chessPiece );
//		  }
//		  else {
//		  Container parent = (Container)c;
//		  parent.add( chessPiece );
//		  }
//	  
//	  chessPiece.setVisible(true);
  }
 
  public void mouseClicked(MouseEvent e) {
//	  System.out.println("mouse clicked" + e);
	  // a ajouter : 
	  // quand on click sur une piece montre au joueur les cases accessibles pas ca piece
  
  }
  public void mouseMoved(MouseEvent e) {
//	  System.out.println("mouse moved");
 }
  public void mouseEntered(MouseEvent e){
//	  System.out.println("mouse entered");
  
  }
  public void mouseExited(MouseEvent e) {
//	  System.out.println("mouse exited");
  
  }
 
  @Override
  public void update(Observable o, Object arg) {
	  // TODO Auto-generated method stub
	  System.out.println("pos init (" +this.posPieceInitX +"," + this.posPieceInitY+ ")");
	  System.out.println("pos finale (" +this.posPieceFinalX +"," + this.posPieceFinalY+ ")");
	  System.out.println(chessGameControler.getMessage() + "\n");	// recuperation du message de l'echiquier
	  
	  List<PieceIHM> piecesIHM = (List<PieceIHM>) arg; // recuperation de la liste de pieces ihm
	  
	  System.out.println(piecesIHM + "\n\n");
	  // suppression de toutes les images des cases de l'echiqier
	  for (int i = 0; i < chessBoard.getComponents().length; i++) {
		Component component = chessBoard.getComponent(i);
		if (component instanceof JPanel)
		{
			
			JPanel c = (JPanel) component;
			if (c.getComponents().length > 0)
			{
				c.removeAll();
			}
			
		}
		
	}
	 
	  // replacement des images des pieces en fonction des pieces ihm que l'on a recuperer ( type, couleur, position)
	  for(PieceIHM pieceIhm : piecesIHM) 
	  {

			Couleur color = pieceIhm.getCouleur();
			
			String type = pieceIhm.getTypePiece();
			
			for(Coord coord : pieceIhm.getList()) {
				Coord pieceCoord = new Coord(coord.x, coord.y);
				JLabel piece = new JLabel( new ImageIcon(ChessImageProvider.getImageFile(type, color)));
				JPanel panel = (JPanel)chessBoard.getComponent(pieceCoord.x + pieceCoord.y*8);
				panel.add(piece);
		}
	  }
	  layeredPane.revalidate();

	  
  }
  
  public static void main(String[] args) 
  {
	  JFrame frame = new ChessGameGUI(new ChessGameControler(new ChessGame()));
	  frame.setDefaultCloseOperation(EXIT_ON_CLOSE );
	  
	  frame.pack();
	  frame.setResizable(true);
	  frame.setLocationRelativeTo( null );
	  frame.setVisible(true);
 }







}
