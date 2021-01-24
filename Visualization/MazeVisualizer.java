package Visualization;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.ArrayList;
import java.util.List;

// Import Pathfinding Algorithms
import Pathfinding.*;

// Import Maze Generation Algorithms
import MazeGeneration.*;

@SuppressWarnings("serial")
public class MazeVisualizer extends JFrame implements ActionListener, MouseListener, MouseMotionListener {

	// Initialize Fonts
	private static final Font BUTTON_FONT = new Font("Comic Sans MS", Font.PLAIN, 17);

	// Initialize Button Color
	private static final Color GENERATOR_BUTTON_COLOR = new Color(71, 156, 252);
	private static final Color PATH_FIND_BUTTON_COLOR = new Color(243, 159, 14);
	private static final Color NODE_BUTTON_COLOR = new Color(167, 78, 255);

	// Initialize Square Color
	private static final Color PATH_COLOR = new Color(255, 254, 106);
	private static final Color SEARCH_AREA_COLOR = new Color(145, 166, 255);
	private static final Color WALL_COLOR = new Color(49, 47, 47);
	private static final Color START_COLOR = new Color(54, 206, 54);
	private static final Color DESTINATION_COLOR = new Color(231, 29, 54);

	// Maze Generation Buttons
	private JButton mGen = new JButton("Manual Generation");
	private JButton rDiv = new JButton("Recursive Division");
	private JButton rKruskal = new JButton("Randomized Kruskal`s");
	private JButton rPrim = new JButton("Randomized Prim`s");
	private JButton wilson = new JButton("Wilson`s Algorithm");

	// Path Finding Buttons
	private JButton dfs = new JButton("Depth Firth Search");
	private JButton breadthFS = new JButton("Breadth First Search");
	private JButton aStar = new JButton("A Star");

	// Node Creation Buttons
	private JButton start = new JButton("Create Start Node");
	private JButton destination = new JButton("Create Destination Node");

	// Help Button
	private JButton help = new JButton("Instructions");

	// Variable to Select Mouse Operation
	private int mouseOp = 3;

	// Toggle Manual Generation
	private int manualToggle = 1;

	// Store Old Start and Destination Coordinates
	private int startXold = 0;
	private int startYold = 0;
	private int destXold = 0;
	private int destYold = 0;
	private int noStart = 0;
	private int noDest = 0;

	// Start and Destination Nodes Present
	private int startPresent = 0;
	private int destPresent = 0;

	private List<Integer> path = new ArrayList<Integer>();

	private List<Integer> searchArea = new ArrayList<Integer>();

	private HelpWindow window;

	private static int state = 0;

	private MazeVisualizer() {
		// Main Menu Window Settings
		setTitle("Maze Visualizer");
		setSize(1200, 700);
		setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		// Mouse Event Settings
		addMouseListener(this);
		addMouseMotionListener(this);

		// Initialize Maze
		Maze.clearMaze();

		// Clear Path Squares
		Maze.clearPath();

		// Call Components Method
		Components();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		// Size of Square
		int square_size = 25;

		// Grid Start Coordinate
		int start_x = 300;
		int start_y = 50;

		// Draw Maze
		for (int row = 0; row < Maze.maze.length; row++) {
			for (int col = 0; col < Maze.maze[row].length; col++) {

				Color squareColour;

				// Wall
				if (Maze.maze[row][col] == 1) {
					squareColour = WALL_COLOR;
				}

				// Start Node
				else if (Maze.maze[row][col] == 2) {
					squareColour = START_COLOR;
				}

				// Destination Node
				else if (Maze.maze[row][col] == 9) {
					squareColour = DESTINATION_COLOR;
				}

				// Empty Square
				else {
					squareColour = Color.WHITE;
				}

				// Draw Square
				g.setColor(squareColour);
				g.fillRect(square_size * col + start_x, square_size * row + start_y, square_size, square_size);
				g.setColor(Color.BLACK);
				g.drawRect(square_size * col + start_x, square_size * row + start_y, square_size, square_size);
			}
		}

		// Draw Search Area
		for (int p = 0; p < searchArea.size(); p += 2) {
			int searchX = searchArea.get(p);
			int searchY = searchArea.get(p + 1);

			// Uncover Start and Destination Node
			if (Maze.maze[searchY][searchX] != 2 && Maze.maze[searchY][searchX] != 9) {

				// Add Delay In Between Search Area Drawing
				try {
					Thread.sleep(20);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}

				g.setColor(SEARCH_AREA_COLOR);
				g.fillRect(square_size * searchX + start_x, square_size * searchY + start_y, square_size, square_size);
				g.setColor(Color.BLACK);
				g.drawRect(square_size * searchX + start_x, square_size * searchY + start_y, square_size, square_size);
			}
		}

		// Draw Path From Start to Destination
		for (int p = path.size() - 1; p >= 0; p -= 2) {
			int pathX = path.get(p - 1);
			int pathY = path.get(p);

			// Uncover Start and Destination Node
			if (Maze.maze[pathY][pathX] != 2 && Maze.maze[pathY][pathX] != 9) {

				// Add Delay In Between Path Drawing
				try {
					Thread.sleep(90);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}

				g.setColor(PATH_COLOR);
				g.fillRect(square_size * pathX + start_x, square_size * pathY + start_y, square_size, square_size);
				g.setColor(Color.BLACK);
				g.drawRect(square_size * pathX + start_x, square_size * pathY + start_y, square_size, square_size);
			}
		}

		clearSearchLists();
	}

	private void Components() {
		// Recursive Backtracking Button Settings
		mGen.setBounds(25, 45, 250, 40);
		SetButtonSettings(mGen);
		SetButtonColour(mGen, GENERATOR_BUTTON_COLOR, Color.BLACK);

		// Recursive Division Button Settings
		rDiv.setBounds(25, 95, 250, 40);
		SetButtonSettings(rDiv);
		SetButtonColour(rDiv, GENERATOR_BUTTON_COLOR, Color.BLACK);

		// Randomized Kruskal Button Settings
		rKruskal.setBounds(25, 145, 250, 40);
		SetButtonSettings(rKruskal);
		SetButtonColour(rKruskal, GENERATOR_BUTTON_COLOR, Color.BLACK);

		// Randomized Prim Button Settings
		rPrim.setBounds(25, 195, 250, 40);
		SetButtonSettings(rPrim);
		SetButtonColour(rPrim, GENERATOR_BUTTON_COLOR, Color.BLACK);

		// Wilson Button Settings
		wilson.setBounds(25, 245, 250, 40);
		SetButtonSettings(wilson);
		SetButtonColour(wilson, GENERATOR_BUTTON_COLOR, Color.BLACK);

		// Create Start Node Button Settings
		start.setBounds(25, 310, 250, 40);
		SetButtonSettings(start);
		SetButtonColour(start, NODE_BUTTON_COLOR, Color.BLACK);

		// Create Destination Node Button Settings
		destination.setBounds(25, 360, 250, 40);
		SetButtonSettings(destination);
		SetButtonColour(destination, NODE_BUTTON_COLOR, Color.BLACK);

		// Depth First Search Button Settings
		dfs.setBounds(25, 425, 250, 40);
		SetButtonSettings(dfs);
		SetButtonColour(dfs, PATH_FIND_BUTTON_COLOR, Color.BLACK);

		// Breadth First Search Button Settings
		breadthFS.setBounds(25, 475, 250, 40);
		SetButtonSettings(breadthFS);
		SetButtonColour(breadthFS, PATH_FIND_BUTTON_COLOR, Color.BLACK);

		// A Star Button Settings
		aStar.setBounds(25, 525, 250, 40);
		SetButtonSettings(aStar);
		SetButtonColour(aStar, PATH_FIND_BUTTON_COLOR, Color.BLACK);

		// Help Button Settings
		help.setBounds(25, 590, 250, 40);
		SetButtonSettings(help);
		SetButtonColour(help, Color.PINK, Color.BLACK);
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		if (event.getSource() == mGen) {
			// Toggle Manual Generation On
			manualToggle = 1;

			// Set Mouse Operation to Wall
			mouseOp = 3;

			Maze.clearMaze();

			clearSearchLists();

			// No Start or Destination
			startPresent = 0;
			destPresent = 0;

			repaint();

			CheckHelpWindow();
		}

		if (event.getSource() == rDiv) {
			// Toggle Manual Generation Off
			manualToggle = 0;

			// Default Mouse Operation
			mouseOp = 0;

			Maze.clearMaze();

			clearSearchLists();

			// No Start or Destination
			startPresent = 0;
			destPresent = 0;

			RecursiveDivision.generate(Maze.maze, 1, 1, Maze.getWidth() - 2, Maze.getHeight() - 2);

			repaint();

			CheckHelpWindow();
		}

		if (event.getSource() == rKruskal) {
			// Toggle Manual Generation Off
			manualToggle = 0;

			// Default Mouse Operation
			mouseOp = 0;

			Maze.clearMaze();

			clearSearchLists();

			// No Start or Destination
			startPresent = 0;
			destPresent = 0;

			RandomKruskal.generate(Maze.maze, Maze.getWidth(), Maze.getHeight());

			repaint();

			CheckHelpWindow();
		}

		if (event.getSource() == rPrim) {
			// Toggle Manual Generation Off
			manualToggle = 0;

			// Default Mouse Operation
			mouseOp = 0;

			Maze.clearMaze();

			clearSearchLists();

			// No Start or Destination
			startPresent = 0;
			destPresent = 0;

			RandomPrim.generate(Maze.maze, Maze.getWidth(), Maze.getHeight());

			repaint();

			CheckHelpWindow();
		}

		if (event.getSource() == wilson) {
			// Toggle Manual Generation Off
			manualToggle = 0;

			// Default Mouse Operation
			mouseOp = 0;

			Maze.clearMaze();

			clearSearchLists();

			// No Start or Destination
			startPresent = 0;
			destPresent = 0;

			Wilson.generate(Maze.maze, Maze.getWidth(), Maze.getHeight());

			repaint();

			CheckHelpWindow();
		}

		if (event.getSource() == dfs) {
			if (startPresent == 1 && destPresent == 1) {
				Maze.maze[startYold][startXold] = 0;

				// Clear Path Squares
				Maze.clearPath();

				clearSearchLists();

				DFS.pathfind(Maze.maze, startXold, startYold, path, searchArea);

				Maze.maze[startYold][startXold] = 2;

				repaint();
			}

			CheckHelpWindow();
		}

		if (event.getSource() == breadthFS) {
			if (startPresent == 1 && destPresent == 1) {
				// Clear Path Squares
				Maze.clearPath();

				clearSearchLists();

				Maze.maze[startYold][startXold] = 3;

				BFS.pathfind(Maze.maze, startXold, startYold, path, searchArea);

				Maze.maze[startYold][startXold] = 2;

				repaint();
			}

			CheckHelpWindow();
		}

		if (event.getSource() == aStar) {
			if (startPresent == 1 && destPresent == 1) {
				// Clear Path Squares
				Maze.clearPath();

				clearSearchLists();

				AStar.pathfind(Maze.maze, startXold, startYold, destXold, destYold, path, searchArea);

				Maze.maze[startYold][startXold] = 2;

				repaint();
			}

			CheckHelpWindow();
		}

		if (event.getSource() == start) {
			// Set Mouse Operation to Start Node
			mouseOp = 1;

			CheckHelpWindow();
		}

		if (event.getSource() == destination) {
			// Set Mouse Operation to Destination Node
			mouseOp = 2;

			CheckHelpWindow();
		}

		if (event.getSource() == help) {
			// Open Help Window
			if (state == 0) {
				window = new HelpWindow();
				state = 1;
			}

			// Close Help Window
			else {
				CheckHelpWindow();
			}
		}
	}

	private void clearSearchLists() {
		// Clear Path List
		path.clear();

		// Clear Search Area List
		searchArea.clear();
	}

	// Close Help Window if Open
	private void CheckHelpWindow() {
		if (state == 1) {
			window.Dispose();
		}
	}

	public static void setHelpWindowState(int num) {
		state = num;
	}

	private void CustomMaze(MouseEvent click) {

		if (state == 1) {
			window.Dispose();
			state = 0;
		}

		if (SwingUtilities.isLeftMouseButton(click)) {

			// Get Coordinates of Mouse Click
			int xClick = click.getX();
			int yClick = click.getY();

			if (325 <= xClick && xClick <= 1150 && 75 <= yClick && yClick <= 650) {

				// Convert Coordinates to Array Index
				int xI = ((xClick - 325) / 25) + 1;
				int yI = ((yClick - 75) / 25) + 1;

				// Add Start Node
				if (mouseOp == 1 && (Maze.maze[yI][xI] == 0 || Maze.maze[yI][xI] == 3)) {
					if (noStart == 1) {

						if (Maze.maze[startYold][startXold] == 2) {
							// Delete Old Start Node
							Maze.maze[startYold][startXold] = 0;
						}

					} else {
						noStart = 1;
					}

					// Store New Start Node Coordinates
					startXold = xI;
					startYold = yI;

					// Create New Start Node
					Maze.maze[yI][xI] = 2;
					startPresent = 1;

					// Set MouseOp to Make Walls
					mouseOp = 3;
				}

				// Add Destination Node
				else if (mouseOp == 2 && (Maze.maze[yI][xI] == 0 || Maze.maze[yI][xI] == 3)) {
					if (noDest == 1) {

						if (Maze.maze[destYold][destXold] == 9) {
							// Delete Old Start Node
							Maze.maze[destYold][destXold] = 0;
						}

					} else {
						noDest = 1;
					}

					// Store New Destination Node Coordinates
					destXold = xI;
					destYold = yI;

					// Create New Destination Node
					Maze.maze[yI][xI] = 9;
					destPresent = 1;

					// Set MouseOp to Make Walls
					mouseOp = 3;

				}

				// Add Maze Walls
				else if (mouseOp == 3 && manualToggle == 1 && (Maze.maze[yI][xI] == 0 || Maze.maze[yI][xI] == 3)) {
					Maze.maze[yI][xI] = 1;
				}
			}

		} else if (SwingUtilities.isRightMouseButton(click)) {
			// Get Coordinates of Mouse Click
			int xClick = click.getX();
			int yClick = click.getY();

			if (325 <= xClick && xClick <= 1150 && 75 <= yClick && yClick <= 650) {
				// Convert Coordinates to Array Index
				int xI = ((xClick - 325) / 25) + 1;
				int yI = ((yClick - 75) / 25) + 1;

				// Remove Maze Walls
				if (manualToggle == 1 && Maze.maze[yI][xI] == 1) {
					Maze.maze[yI][xI] = 0;
				}

				// Remove Start Node
				else if (Maze.maze[yI][xI] == 2) {
					Maze.maze[yI][xI] = 0;

					startPresent = 0;
				}

				// Remove Destination Node
				else if (Maze.maze[yI][xI] == 9) {
					Maze.maze[yI][xI] = 0;

					destPresent = 0;
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent click) {
		CustomMaze(click);
	}

	@Override
	public void mouseDragged(MouseEvent click) {
		CustomMaze(click);
	}

	@Override
	public void mouseReleased(MouseEvent click) {
		if (mouseOp != 0) {
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

	private void SetButtonSettings(JButton button) {
		button.setFont(BUTTON_FONT);
		button.setBorderPainted(true);
		button.setFocusPainted(false);
		button.setOpaque(true);
		button.setContentAreaFilled(true);
		button.setVisible(true);
		button.addActionListener(this);
		add(button);
	}

	private void SetButtonColour(JButton button, Color bg, Color fg) {
		button.setBackground(bg);
		button.setForeground(fg);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				// Set UI to Cross Platform
				try {
					UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}

				MazeVisualizer view = new MazeVisualizer();
				view.setVisible(true);
			}
		});
	}
}
