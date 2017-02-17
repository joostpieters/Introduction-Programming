
public class Player extends Participant {

	protected String name;
	protected CPU com;
	protected int aantalBom;
	protected int timer;
	protected int posBom;
	protected boolean useBom;
	public Player(char symbol, Gameboard board, Game game, String name)
	{
		super(symbol, board, game);
		this.name = name;
		this.aantalBom = 1;
		this.timer = 0;
		useBom = false;
	}
	public void setCPU(CPU com)
	{
		this.com = com;
	}
	public String getName()
	{
		return name;
	}
	public void setTimer(int n)
	{
		timer = n;
	
	}

	public int getTimer()
	{
		return timer;
	}
	public int getAantalBom()
	{
		return aantalBom;
	}
	public void setPosBom(int n)
	{
		posBom = n;
		useBom = true;
	}
	public int getPosBom()
	{
		return posBom;
	}
	public boolean getUseBom()
	{
		return useBom;
	}
	public void setUseBom(boolean a)
	{
		//System.out.println(useBom);
		useBom = a;
	}
}
