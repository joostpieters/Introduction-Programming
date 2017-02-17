import java.util.ArrayList;
import java.util.Arrays;

public class CPU extends Participant{
	
	public final static int NUMBER_ON_ROW = 4;
	protected Player player;
	protected int level;
	protected ArrayList<Integer> list;

	public CPU(char symbol, Gameboard board, Game game, int level)
	{
		super(symbol, board, game);
		this.level = level;
		list = new ArrayList<Integer>(Arrays.asList(0,1,3,2,3,4));
	}
	public void setPlayer(Player player)
	{
		this.player = player;
	}
	public int setMove()
	{
		if(level == 1)
		{
			ArrayList<Integer> possibleMoves = board.getPossibleMoves();
			int range = possibleMoves.size();
			return possibleMoves.get( (int)(Math.random()*range)  );

		}
		else if(level ==2)
		{


			for(int i : board.getPossibleMoves())
			{

				if(  game.onRowAllDirection(NUMBER_ON_ROW, this.symbol, board.getActualPos()[i]-1, i)  ) return i;
			}

			ArrayList<Integer> possibleMoves = board.getPossibleMoves();
			int range = possibleMoves.size();
			return possibleMoves.get( (int)(Math.random()*range)  );

		}	
		else if(level ==3)
		{
			ArrayList<Integer> scores = new ArrayList<Integer>();
			//char[][] cloneBoard = Arrays.copyOf(board.getBoard(), board.getBoard().length);

			int scoreMax = Integer.MIN_VALUE;
			int posMaxScore = 0;
			for(int i : board.getPossibleMoves())
			{
				scores.add(0);

				scores.set(scores.size()-1, scores.get(scores.size()-1) + aantalSchijvenAllDirection(NUMBER_ON_ROW, this.symbol, board.getActualPos()[i]-1, i, 0)*150);
				scores.set(scores.size()-1, scores.get(scores.size()-1) + aantalSchijvenAllDirection(NUMBER_ON_ROW-1, this.symbol, board.getActualPos()[i]-1, i, 1)*10);
				scores.set(scores.size()-1, scores.get(scores.size()-1) + aantalSchijvenAllDirection(NUMBER_ON_ROW-2, this.symbol, board.getActualPos()[i]-1, i, 2)*1);

				if(game.getAantalMoves() != 0)
				{
					
					scores.set(scores.size()-1, scores.get(scores.size()-1) + aantalSchijvenAllDirection(NUMBER_ON_ROW-1, player.symbol, player.getLastMoveX(), player.getLastMoveY(), 1)* (-100) );
					scores.set(scores.size()-1, scores.get(scores.size()-1) + aantalSchijvenAllDirection(NUMBER_ON_ROW-2, player.symbol, player.getLastMoveX(), player.getLastMoveY(), 2)* (-10) );
				}
				//System.out.println(i+" : " + scores.get(scores.size()-1));
				if(scoreMax < scores.get(scores.size()-1))
				{
					scoreMax = scores.get(scores.size()-1);
					posMaxScore = i;
				}
			}

			//System.out.println(posMaxScore+" ");
			return posMaxScore;
		}
		else
		{
			return 0;
		}
	}
	public int aantalSchijvenOneDirection(int aantalOnRow, char move, int x, int y, int stepX, int stepY, int spaces )
	{
		int count = 0;
		int freespaces =0;

		for(int i = (aantalOnRow+spaces)*(-1); i<=aantalOnRow+spaces;i++)
		{
			if(i<=(aantalOnRow)*(-1) || i >= aantalOnRow )
			{
				if(x+i*stepX>=0 && x+i*stepX < board.getRij() && y+i*stepY>=0 && y+i*stepY <board.getKolom())
					if((board.getBoard()[x+i*stepX][y+i*stepY]== '_')) freespaces++;
			}
			else
			{
				if(x+i*stepX>=0 && x+i*stepX < board.getRij() && y+i*stepY>=0 && y+i*stepY <board.getKolom())
					if( (board.getBoard()[x+i*stepX][y+i*stepY]== move) ||  i==0  ) count++;
					else if((board.getBoard()[x+i*stepX][y+i*stepY]== '_')) freespaces++;
					else count = 0;
			}
			if(count >= aantalOnRow && freespaces >= spaces) return 1;

		}

		return 0;
	}
	public int aantalSchijvenAllDirection(int aantalOnRow, char move, int x, int y, int spaces)
	{
		return  aantalSchijvenOneDirection(aantalOnRow, move, x, y,  1, 0, spaces) +
				aantalSchijvenOneDirection(aantalOnRow, move, x, y,  0, 1, spaces) +
				aantalSchijvenOneDirection(aantalOnRow, move, x, y,  1, 1, spaces) +
				aantalSchijvenOneDirection(aantalOnRow, move, x, y,  1,-1, spaces);
	}
}
