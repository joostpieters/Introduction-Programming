import java.util.ArrayList;


public class Participant 
{
	protected int aantalAambeeld;
	protected char symbol;
	protected ArrayList<int[]> moves; 
	protected int victories;
	protected Gameboard board;
	protected Game game;
	
	public Participant(char symbol, Gameboard board, Game game)
	{
		this.symbol = symbol;
		moves = new ArrayList<int[]>();
		victories =0;
		
		this.board = board;
		this.game = game;
		
	}
	public void setVictories()
	{
		victories++;
	}
	public int getVictories()
	{
		return victories;
	}
	public char getSymbol()
	{
		return symbol;
	}
	public void setAantalAambeeld(int n)
	{
		aantalAambeeld = n;
	}
	public int getAantalAambeeld()
	{
		return aantalAambeeld;
	}
	public void useAambeeld()
	{
		aantalAambeeld--;
	}
	public void setLastMove(int i, int j)
	{
		int[] lastMove = {i, j};
		moves.add(lastMove);
	}
	public int getLastMoveX()
	{
	
		return (moves.get(moves.size()-1))[0];
	}
	public int getLastMoveY()
	{
		return (moves.get(moves.size()-1))[1];
	}
}
