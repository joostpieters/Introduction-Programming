import java.util.ArrayList;

public class Gameboard {

	private int rij;
	private int kolom;
	private char grid[][];
	private int lastX;
	private int lastY;
	private int actualPos[];
	private ArrayList<Integer> possibleMoves;
	private Game game;
	private CPU com;
	private Player player;

	public Gameboard(int rij, int kolom)
	{

		this.rij = rij;
		this.kolom = kolom;
		lastX = 0;
		lastY = 0;
		grid = new char[rij][kolom];
		actualPos = new int [kolom];
		possibleMoves = new ArrayList<Integer>();


		for(int i=0;i<rij; i++)
		{
			for(int j = 0;j<kolom;j++)
			{
				grid[i][j]='_';

				if(i==0)
				{
					actualPos[j]=rij;
					possibleMoves.add(j);
				}
			}
		}

	}
	public void setCPU(CPU com)
	{
		this.com = com;
	}
	public void setPlayer(Player player)
	{
		this.player = player;
	}
	public int[] getActualPos()
	{
		return actualPos;
	}
	public void setGame(Game game)
	{
		this.game = game;
	}
	public int getLastX()
	{
		return lastX;
	}
	public int getLastY()
	{
		return lastY;
	}
	public void setElement(int j, boolean useAambeeld, boolean useBom, Participant participant)
	{
		//System.out.println(player.getTimer()+" - "+player.getUseBom());
		
		if( player.getTimer() ==0 && player.getUseBom())
		{
			
			player.setUseBom(false);
			int i;
			for(i =0; i<rij-1 - actualPos[player.getPosBom()] ;i++)
			{
				if(rij-1-i>= 0 && rij-1-i<rij  && rij-2-i>=0 && rij-2-i<rij)
				{
					grid[rij-1-i][player.getPosBom()] = grid[rij-2-i][player.getPosBom()];
	
				}
			}
			
			actualPos[player.getPosBom()] =actualPos[player.getPosBom()] +1;
		}

		char element = participant.getSymbol();
		if(useAambeeld && participant.getAantalAambeeld()>0)
		{
			if(j>=0 && j< kolom)
			{
				participant.useAambeeld();
				int x = game.getAantalVerwijderen();
				for(int i=0; i < x;i++)
				{
					if(actualPos[j]<rij)
					{
						grid[actualPos[j]++][j]='_';
						game.setAantalMoves(-1);
					}
					else break;
				}
				grid[ --actualPos[j] ][j]='#';
				game.setAantalMoves(1);

				lastX = actualPos[j];
				lastY = j;
			}
		}
		else if(!useAambeeld && !useBom)
		{	
			if(isValidMove(j))
			{
				actualPos[j]--;
				grid[actualPos[j]][j]=element;

				if(actualPos[j]==0) possibleMoves.remove((Object)j);

				game.setAantalMoves(1);

				lastX = actualPos[j];
				lastY = j;
			}
			
		}
		//Bom
		else
		{
			if(  ((Player)(participant)).aantalBom >0)
			{
				((Player)(participant)).aantalBom --;
				grid[rij-1][j] = '*';
				if(grid[rij-2][j]=='_')
				{
					actualPos[j]--;
				}

				game.setAantalMoves(1);
				lastX = actualPos[j];
				lastY = j;

				player.setUseBom(true);
			

			}
			
		}
		
	
	}
	public ArrayList<Integer> getPossibleMoves()
	{
		return possibleMoves;
	}
	public char getElement(int i, int j)
	{
		return grid[i][j];
	}
	public char[][] getBoard()
	{
		return grid;
	}
	public int getRij()
	{
		return rij;
	}
	public int getKolom()
	{
		return kolom;
	}
	public boolean isEmpty(int i, int j)
	{
		if(i>=0 && i<rij && j >=0 && j<kolom)
		{
			if(grid[i][j]== '_') return true;
			else return false;

		}
		else return false;
	}
	public boolean isValidMove(int j)
	{
		if(j>=0 && j< kolom && actualPos[j]>0) return true;
		return false;
	}
}