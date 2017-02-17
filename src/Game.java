
public class Game {

	private Gameboard board;
	private int aantalMoves;
	private int aantalPion;
	private int aantalAambeeld;
	private int aantalVerwijderen;

	private int row;
	private int column;


	public Game(int row, int column)
	{
		this.row = row;
		this.column = column;

		aantalMoves = 0;
		aantalPion = 0;
		aantalAambeeld = 0;
		aantalVerwijderen =0;
	}
	public void setBoard(Gameboard board)
	{
		this.board =board;
	}
	public void setAantalMoves(int n)
	{
		aantalMoves+=n;
	}
	public int getAantalMoves()
	{
		return aantalMoves;
	}
	public void setAantalVerwijderen(int n)
	{
		aantalVerwijderen = n;
	}
	public int getAantalVerwijderen()
	{
		return aantalVerwijderen;
	}
	public void setAantalAambeeld(int n, Player player, CPU com)
	{
		aantalAambeeld = n;
		player.setAantalAambeeld(n);
		com.setAantalAambeeld(n);
	}
	public int getAantalAambeeld()
	{
		return aantalAambeeld;
	}
	public boolean isFull() {
		if(aantalPion == board.getKolom()*board.getRij( )) return true;
		return false;
	}
	public boolean onRowOneDirection(int aantalOnRow, char move, int x, int y, int stepX, int stepY )
	{
		int count = 0;

		for(int i = (aantalOnRow-1)*(-1); i<aantalOnRow;i++)
		{
			if(x+i*stepX>=0 && x+i*stepX < board.getRij() && y+i*stepY>=0 && y+i*stepY <board.getKolom())
				if( (board.getBoard()[x+i*stepX][y+i*stepY]== move) ||  i==0  ) count++;
				else count = 0;
			if(count == aantalOnRow) return true;
		}
		 
		return false;
	}
	public boolean onRowAllDirection(int aantalOnRow, char move, int x, int y)
	{
		return onRowOneDirection(aantalOnRow, move, x, y,  1, 0) ||
			   onRowOneDirection(aantalOnRow, move, x, y,  0, 1) ||
			   onRowOneDirection(aantalOnRow, move, x, y,  1, 1) ||
			   onRowOneDirection(aantalOnRow, move, x, y,  1,-1);
	}

	public boolean endGame(int aantalOnRow, char move, int x, int y)
	{
		
		return onRowAllDirection(aantalOnRow, move, x, y);

	}


}
