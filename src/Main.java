import java.util.Scanner;


public class Main 
{

	public static int row = 6;
	public static int column = 7;
	public final static int NUMBER_ON_ROW = 4;
	public static int level;
	public static String name;
	public static int aantalAambeeld;
	public static int aantalVerwijder;

	public static int scoreCPU = 0;
	public static int scorePlayer = 0;
	public static int delay = 0;
	public static int j;
	
	public static void main(String[] args) 
	{	
		Scanner sc = new Scanner(System.in);

		System.out.print("\nMoeilijkheidsgraad (1/2/3): ");
		level = sc.nextInt();

		System.out.print("Aantal rijen: ");
		row = sc.nextInt();
		System.out.print("Aantal kolommen: ");
		column = sc.nextInt();


		System.out.print("Uw naam: ");
		name = sc.next();


		System.out.print("Aantal aambeelden: ");
		aantalAambeeld = sc.nextInt();
		System.out.print("Aantal te verwijderen schijven met aambeeld: ");
		aantalVerwijder = sc.nextInt();


		//Set Timer
		System.out.print("Geef de ontploffingsdelay: ");
		delay = sc.nextInt();
		System.out.println();

		do
		{
			Gameboard board = new Gameboard(row, column);
			Game game = new Game(row, column);

			board.setGame(game);
			game.setBoard(board);

			Player player = new Player('O', board, game, name);
			CPU com = new CPU('X', board, game, level);
			player.setCPU(com);
			com.setPlayer(player);
			
			board.setCPU(com);
			board.setPlayer(player);

			game.setAantalAambeeld(aantalAambeeld, player, com);
			game.setAantalVerwijderen(aantalVerwijder);

			player.setTimer(delay);

			int pos = 0;
			char aambeeld = 0;
			char bom = 0;

			while(true)
			{
				//Get info
				System.out.print("Computer: "+ scoreCPU+ " [");
				for(int i=0;i<com.getAantalAambeeld();i++)
				{
					System.out.print("#");
					if(i+1<com.getAantalAambeeld())System.out.print(", ");
				}
				System.out.println("]");

				System.out.print(player.getName()+": "+ scorePlayer + " [");
				for(int i=0;i<player.getAantalAambeeld();i++)
				{
					System.out.print("#");
					if(i+1<com.getAantalAambeeld())System.out.print(", ");
					//if(i+1<player.getAantalAambeeld())System.out.print(", ");
				}
				for(int i=0;i< player.aantalBom;i++) System.out.print(", *");
				System.out.println("]\n");



				//Player move
				aambeeld ='\0';
				if(player.getAantalAambeeld()>0)
				{
					System.out.print("Wilt u een aambeeld gebruiken? (Y/N) ");
					aambeeld = sc.next().toUpperCase().charAt(0);

					//Bom use
					if(aambeeld !='Y' && player.getAantalBom()>0)
					{
						System.out.print("Wilt u een bom gebruiken? (Y/N) ");
						bom = sc.next().toUpperCase().charAt(0);
						
					}
				}

				System.out.print("Kies een kolom: ");

				//Set element
				j =  sc.nextInt()-1;
				board.setElement(j, (aambeeld=='Y')?true:false, (bom =='Y')?true:false,player );
				
				
				if(bom =='Y') player.setPosBom(j);
				
				bom = 0;
				
				
				player.setLastMove(board.getLastX(), board.getLastY());

				//Timer--
				if(player.getUseBom()) player.setTimer(player.getTimer()-1);
				
				if(aambeeld!='Y')
				{
					if(game.endGame(NUMBER_ON_ROW, player.getSymbol(), player.getLastMoveX(), player.getLastMoveY())  )
					{

						displayBoard(board);
						displayWinner(board, com, player);
						scorePlayer++;

						break;
					}
				}
				if(game.isFull())
				{
					displayBoard(board);
					System.out.println("Computer (" + com.getSymbol() +") koos kolom: "+ (int)(pos+1));
					System.out.println("Exaquo");
					break;
				}



				//CPU move
				pos = com.setMove();
				board.setElement(pos, false , false, com);
				com.setLastMove(board.getLastX(), board.getLastY());

				//Timer--
				if(player.getUseBom()) player.setTimer(player.getTimer()-1);
				


				if(game.endGame(NUMBER_ON_ROW, com.getSymbol(), com.getLastMoveX(), com.getLastMoveY() ))
				{
					displayBoard(board);
					System.out.println("Computer (" + com.getSymbol() +") koos kolom: "+ (int)(pos+1));
					displayWinner(board, com, player);
					scoreCPU++;

					break;
				}
				if(game.isFull())
				{
					displayBoard(board);
					System.out.println("Computer (" + com.getSymbol() +") koos kolom: "+ (int)(pos+1));
					System.out.println("Exaquo");
					break;
				}


				displayBoard(board);
				System.out.println("Computer (" + com.getSymbol() +") koos kolom: "+ (int)(pos+1));
				System.out.println("-------------------------------");

			}

			System.out.print("Opnieuw spelen? (Y/N) ");
		}while(sc.next().toUpperCase().charAt(0) == 'Y');

		System.out.println("End");
		sc.close();
	}
	public static void displayBoard(Gameboard board)
	{
		char a[][] = board.getBoard();
		System.out.print(" ");
		for(int i=1;i<=board.getKolom();i++) System.out.print(" "+ i);
		System.out.println();
		for(int i =0; i<a.length;i++)
		{
			System.out.print((int)(i+1)+"|");

			for(int j=0; j< a[0].length;j++)
			{
				System.out.print(a[i][j]+ "|");
			}
			System.out.println();
		}
	}

	public static void displayWinner(Gameboard board, CPU com, Player player)
	{
		System.out.println();
		if(board.getLastX() == player.getLastMoveX() && board.getLastY()== player.getLastMoveY())
		{
			System.out.println("U wint met de laatst gekozen positie ("+ (int)(board.getLastX()+1) +", " + (int)(board.getLastY()+1)+")" ); 
		}
		else System.out.println("Computer wint met de laatst gekozen positie ("+ (int)(board.getLastX()+1) +", " + (int)(board.getLastY()+1)+")" ); 
	}

}


