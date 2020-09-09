package MazeProject;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

@SuppressWarnings("serial")
public class MazeMain extends JFrame implements ActionListener, MouseListener, MouseMotionListener 
{
	//Initialize Fonts
	public static Font ButtonFont = new Font("Comic Sans MS", Font.PLAIN, 17);
	public static Font HelpFont = new Font("Comic Sans MS", Font.BOLD, 28);

	//Initialize Button Color
	Color GeneratorButtonColor = new Color(71, 156, 252);
	Color PathFindButtonColor = new Color(243, 159, 14);
	Color NodeCreationButtonColor = new Color(167, 78, 255);

	Color PathColor = new Color(80, 80, 255);
	Color SearchAreaColor = new Color(255, 255, 111);

	//Maze Generation Buttons
	JButton mGen = new JButton("Manual Generation");
	JButton rDiv = new JButton("Recursive Division");
	JButton rKruskal = new JButton("Randomized Kruskal`s");
	JButton rPrim = new JButton("Randomized Prim`s");
	JButton wilson = new JButton("Wilson`s Algorithm");

	//Path Finding Buttons
	JButton dfs = new JButton("Depth Firth Search");
	JButton breadthFS = new JButton("Breadth First Search");
	JButton aStar = new JButton("A Star");

	//Node Creation Buttons
	JButton start = new JButton("Create Start Node");
	JButton destination = new JButton("Create Destination Node");

	//Help Button
	JButton help = new JButton("Instructions");

	//Variable to Select Mouse Operation
	int mouseOp = 3;

	//Toggle Manual Generation
	int manualToggle = 1;

	//Store Old Start and Destination Coordinates
	int startXold = 0;
	int startYold = 0;
	int destXold = 0;
	int destYold = 0;
	int noStart = 0;
	int noDest = 0;

	//Start and Destination Nodes Present
	int startPresent = 0;
	int destPresent = 0;

	//Array to Store Maze
	private int[][] maze = {
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };

	// 0 = empty
	// 1 = wall
	// 2 = start
	// 3 = searched
	// 7 = wilson trail junk
	// 9 = destination

	private final List<Integer> path = new ArrayList<Integer>();

	public MazeMain() 
	{
		//Main Menu Window Settings
		setTitle("Maze Simulation");
		setSize(1200, 700);
		setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		//Mouse Event Settings
		addMouseListener(this);
		addMouseMotionListener(this);

		//Clear Path Squares
		clearPath();

		//Call Components Method
		Components();
	}

	@Override
	public void paint(Graphics g) 
	{
		super.paint(g);

		//Size of Square
		int square_size = 25;

		//Grid Start Coordinate
		int start_x = 300;
		int start_y = 50;

		//Draw Maze
		for(int row = 0; row < maze.length; row++) 
		{
			for(int col = 0; col < maze[row].length; col++) 
			{
				Color color;

				//Empty Square
				if(maze[row][col] == 0) 
				{
					color = Color.WHITE;
				}
				// Wall
				else if(maze[row][col] == 1) 
				{
					color = Color.BLACK;
				}
				//Start Node
				else if(maze[row][col] == 2) 
				{
					color = Color.GREEN;
				}
				//Destination Node
				else if(maze[row][col] == 9) 
				{
					color = Color.RED;
				}
				//Wilson Trail Junk
				else if(maze[row][col] == 7) 
				{
					color = new Color(100, 100, 100);
				}
				//Searched Square
				else 
				{
					color = SearchAreaColor;
				}

				//Draw Square
				g.setColor(color);
				g.fillRect(square_size * col + start_x, square_size * row + start_y, square_size, square_size);
				g.setColor(Color.BLACK);
				g.drawRect(square_size * col + start_x, square_size * row + start_y, square_size, square_size);
			}
		}

		//Draw Path From Start to Destination
		for(int p = path.size() - 1; p >= 0; p -= 2) 
		{
			int pathX = path.get(p - 1);
			int pathY = path.get(p);

			//Uncover Start and Destination Node
			if(maze[pathY][pathX] != 2 && maze[pathY][pathX] != 9) 
			{
				//Add Delay In Between Path Drawing
				try 
				{
					Thread.sleep(110);
				} 
				catch(InterruptedException ex) 
				{
					Thread.currentThread().interrupt();
				}

				g.setColor(PathColor);
				g.fillRect(square_size * pathX + start_x, square_size * pathY + start_y, square_size, square_size);
				g.setColor(Color.BLACK);
				g.drawRect(square_size * pathX + start_x, square_size * pathY + start_y, square_size, square_size);
			}
		}

		//Clear Path List
		path.clear();
	}

	public void Components() 
	{
		//Recursive Backtracking Button Settings
		mGen.setBounds(25, 45, 250, 40);
		SetButtonSettings(mGen);
		SetButtonColour(mGen, GeneratorButtonColor, Color.BLACK);

		//Recursive Division Button Settings
		rDiv.setBounds(25, 95, 250, 40);
		SetButtonSettings(rDiv);
		SetButtonColour(rDiv, GeneratorButtonColor, Color.BLACK);

		//Randomized Kruskal Button Settings
		rKruskal.setBounds(25, 145, 250, 40);
		SetButtonSettings(rKruskal);
		SetButtonColour(rKruskal, GeneratorButtonColor, Color.BLACK);

		//Randomized Prim Button Settings
		rPrim.setBounds(25, 195, 250, 40);
		SetButtonSettings(rPrim);
		SetButtonColour(rPrim, GeneratorButtonColor, Color.BLACK);

		//Wilson Button Settings
		wilson.setBounds(25, 245, 250, 40);
		SetButtonSettings(wilson);
		SetButtonColour(wilson, GeneratorButtonColor, Color.BLACK);

		//Create Start Node Button Settings
		start.setBounds(25, 310, 250, 40);
		SetButtonSettings(start);
		SetButtonColour(start, NodeCreationButtonColor, Color.BLACK);

		//Create Destination Node Button Settings
		destination.setBounds(25, 360, 250, 40);
		SetButtonSettings(destination);
		SetButtonColour(destination, NodeCreationButtonColor, Color.BLACK);

		//Depth First Search Button Settings
		dfs.setBounds(25, 425, 250, 40);
		SetButtonSettings(dfs);
		SetButtonColour(dfs, PathFindButtonColor, Color.BLACK);

		//Breadth First Search Button Settings
		breadthFS.setBounds(25, 475, 250, 40);
		SetButtonSettings(breadthFS);
		SetButtonColour(breadthFS, PathFindButtonColor, Color.BLACK);

		//A Star Button Settings
		aStar.setBounds(25, 525, 250, 40);
		SetButtonSettings(aStar);
		SetButtonColour(aStar, PathFindButtonColor, Color.BLACK);

		//Help Button Settings
		help.setBounds(25, 590, 250, 40);
		SetButtonSettings(help);
		SetButtonColour(help, Color.PINK, Color.BLACK);

	}

	//@Override
	public void actionPerformed(ActionEvent event) 
	{

		if(event.getSource() == mGen) 
		{
			//Toggle Manual Generation On
			manualToggle = 1;

			//Set Mouse Operation to Wall
			mouseOp = 3;

			clearMaze();

			repaint();
		}

		if(event.getSource() == rDiv) 
		{
			//Toggle Manual Generation Off
			manualToggle = 0;

			//Default Mouse Operation
			mouseOp = 0;

			clearMaze();
			RecursiveDivision.Generate(maze, 1, 1, 33, 23);

			repaint();
		}

		if(event.getSource() == rKruskal) 
		{
			//Toggle Manual Generation Off
			manualToggle = 0;

			//Default Mouse Operation
			mouseOp = 0;

			clearMaze();
			RandomKruskal.RandomizedKruskal(maze, 35, 25);

			repaint();
		}

		if(event.getSource() == rPrim) 
		{
			//Toggle Manual Generation Off
			manualToggle = 0;

			//Default Mouse Operation
			mouseOp = 0;

			clearMaze();
			RandomPrim.RandomizedPrim(maze, 35, 25);

			repaint();
		}

		if(event.getSource() == wilson) 
		{
			//Toggle Manual Generation Off
			manualToggle = 0;

			//Default Mouse Operation
			mouseOp = 0;

			clearMaze();
			Wilson.move(maze, 35, 25);

			repaint();
		}

		if(event.getSource() == dfs) 
		{
			if(startPresent == 1 && destPresent == 1) 
			{
				maze[startYold][startXold] = 0;

				//Clear Path Squares
				clearPath();

				DFS.DepthFirstSearch(maze, startXold, startYold, path);

				maze[startYold][startXold] = 2;
				repaint();
			}
		}

		if(event.getSource() == breadthFS) 
		{
			if(startPresent == 1 && destPresent == 1)
			{
				//Clear Path Squares
				clearPath();

				maze[startYold][startXold] = 3;

				BFS.BreadthFirstSearch(maze, startXold, startYold, path);

				maze[startYold][startXold] = 2;
				repaint();
			}
		}

		if(event.getSource() == aStar) 
		{
			if(startPresent == 1 && destPresent == 1)
			{
				//Clear Path Squares
				clearPath();

				AStar.PathFind(maze, startXold, startYold, destXold, destYold, path);

				maze[startYold][startXold] = 2;
				repaint();
			}
		}

		if(event.getSource() == start) 
		{
			//Set Mouse Operation to Start Node
			mouseOp = 1;
		}

		if(event.getSource() == destination) 
		{
			//Set Mouse Operation to Destination Node
			mouseOp = 2;
		}

		if(event.getSource() == help) 
		{
			//Open Help Window
			new HelpWindow();
		}
	}

	public void CustomMaze(MouseEvent click) 
	{
		if(SwingUtilities.isLeftMouseButton(click)) 
		{
			//Get Coordinates of Mouse Click
			int xClick = click.getX();
			int yClick = click.getY();

			if(325 <= xClick && xClick <= 1150 && 75 <= yClick && yClick <= 650) 
			{
				//Convert Coordinates to Array Index
				int xI = ((xClick - 325) / 25) + 1;
				int yI = ((yClick - 75) / 25) + 1;

				//Add Start Node
				if(mouseOp == 1 && (maze[yI][xI] == 0 || maze[yI][xI] == 3)) 
				{
					if(noStart == 1) 
					{
						if(maze[startYold][startXold] == 2) 
						{
							//Delete Old Start Node
							maze[startYold][startXold] = 0;
						}
					} 
					else 
					{
						noStart = 1;
					}

					//Store New Start Node Coordinates
					startXold = xI;
					startYold = yI;

					//Create New Start Node
					maze[yI][xI] = 2;
					startPresent = 1;

					//Set MouseOp to Make Walls
					mouseOp = 3;
				}
				//Add Destination Node
				else if(mouseOp == 2 && (maze[yI][xI] == 0 || maze[yI][xI] == 3))
				{
					if(noDest == 1) 
					{
						if(maze[destYold][destXold] == 9)
						{
							//Delete Old Start Node
							maze[destYold][destXold] = 0;
						}
					}
					else 
					{
						noDest = 1;
					}

					//Store New Destination Node Coordinates
					destXold = xI;
					destYold = yI;

					//Create New Destination Node
					maze[yI][xI] = 9;
					destPresent = 1;

					//Set MouseOp to Make Walls
					mouseOp = 3;

				}
				//Add Maze Walls
				else if(mouseOp == 3 && manualToggle == 1 && (maze[yI][xI] == 0 || maze[yI][xI] == 3)) 
				{
					maze[yI][xI] = 1;
				}
			}
		} 
		else if(SwingUtilities.isRightMouseButton(click)) 
		{
			//Get Coordinates of Mouse Click
			int xClick = click.getX();
			int yClick = click.getY();

			if(325 <= xClick && xClick <= 1150 && 75 <= yClick && yClick <= 650) 
			{
				//Convert Coordinates to Array Index
				int xI = ((xClick - 325) / 25) + 1;
				int yI = ((yClick - 75) / 25) + 1;

				//Remove Maze Walls
				if (manualToggle == 1 && maze[yI][xI] == 1) 
				{
					maze[yI][xI] = 0;
				}
				//Remove Start Node
				else if (maze[yI][xI] == 2) 
				{
					maze[yI][xI] = 0;

					startPresent = 0;
				}
				//Remove Destination Node
				else if (maze[yI][xI] == 9) 
				{
					maze[yI][xI] = 0;

					destPresent = 0;
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent click) 
	{
		CustomMaze(click);
	}

	@Override
	public void mouseDragged(MouseEvent click) 
	{
		CustomMaze(click);
	}

	@Override
	public void mouseReleased(MouseEvent click)
	{
		if (mouseOp != 0) 
		{
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent click) {
	}

	@Override
	public void mousePressed(MouseEvent click) {
	}

	@Override
	public void mouseEntered(MouseEvent click) {
	}

	@Override
	public void mouseExited(MouseEvent click) {
	}

	public void SetButtonSettings(JButton button) 
	{
		button.setFont(ButtonFont);
		button.setBorderPainted(false);
		button.setOpaque(true);
		button.setContentAreaFilled(true);
		button.addActionListener(this);
		button.setVisible(true);
		add(button);
	}

	public void SetButtonColour(JButton button, Color bg, Color fg) 
	{
		button.setBackground(bg);
		button.setForeground(fg);
	}

	//Empty Maze
	public void clearMaze() 
	{
		for(int i = 0; i < 35; i++) 
		{
			for(int j = 0; j < 25; j++)
			{
				if(i == 0 || i == 34 || j == 0 || j == 24) 
				{
					maze[j][i] = 1;
				} 
				else
				{
					maze[j][i] = 0;
				}
			}
		}

		//No Start or Destination
		startPresent = 0;
		destPresent = 0;
	}

	//Clear Path Finding Squares
	public void clearPath() 
	{
		for(int i = 1; i < 34; i++) 
		{
			for(int j = 1; j < 24; j++) 
			{
				if(maze[j][i] != 0 && maze[j][i] != 1 && maze[j][i] != 2 && maze[j][i] != 9)
				{
					maze[j][i] = 0;
				}
			}
		}
	}

	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(new Runnable() 
		{
			@Override
			public void run() 
			{
				MazeMain view = new MazeMain();
				view.setVisible(true);
			}
		});
	}
}
