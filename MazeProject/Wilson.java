package MazeProject;

import java.util.ArrayList;
import java.util.Random;


public class Wilson {
	
	//Store Start Position For Random Walk
	private static int nextX;
	private static int nextY;
	
	//Set Entire Maze to Walls
	private static void WallMaze(int[][]maze, int x, int y)
	{
		for(int i = 0; i < y; i++)
		{
			for(int j = 0; j < x; j++)
			{
				maze[i][j] = 1;
			}
		}
	}

	public static void move(int[][]maze, int x, int y)
	{
		//Block Entire Maze
		WallMaze(maze, x, y);
		
		//Start Position
		maze[y - 2][1] = 0;
		
		//Next Start Position
		nextX = 3;
		nextY = y - 2;
		
		int foundNextStart = 1; // 0 is Null, 1 is True
		
		while(foundNextStart != 0)
		{
			//Conduct Random Walk
			erasedLoopWalk(maze, nextX, nextY);
			
			//Find Next Start Position
			foundNextStart = NextStart(maze, nextX, nextY);
		}
	}
	
	private static void erasedLoopWalk(int[][]maze, int x, int y) 
	{
		//Store Trail of Spaces For Random Walk
		ArrayList<Point> walkTrail = new ArrayList<Point>();
		
		//Store Random Sequence of Moves
		ArrayList<Integer> moves = new ArrayList<Integer>();  
		
		Random rand = new Random();
		
		//Random Walk Start Position
		walkTrail.add(new Point(x, y));
		
		//Initial Place Holder Move
		moves.add(-1);
		
		while(true)
		{
			//decide candidates based on borders and previous move
			ArrayList<Integer> candidates = new ArrayList<Integer>();
			
			//Get Previous Move
			int prev = moves.get(moves.size()-1);
			
			//Left of Square is Possible
			if(x-2 > 0 && prev != 1)
			{
				candidates.add(0);
			}
			//Right of Square is Possible
			if(x+2 < 34 && prev != 0)
			{
				candidates.add(1);
			}
			//Above of Square is Possible
			if(y-2 > 0 && prev != 3) 
			{
				candidates.add(2);
			}
			//Below of Square is Possible
			if(y+2 < 24 && prev != 2) 
			{
				candidates.add(3);
			}

			//Randomly Choose Next Move
			int direction = candidates.get(rand.nextInt(candidates.size()));
			
			for(int i = 0; i < 2; i++)
			{
				//Choose Direction
				if(direction == 0)
				{
					x--;
				}
				if(direction == 1) 
				{
					x++;
				}
				if(direction == 2) 
				{
					y--;
				}
				if(direction == 3) 
				{
					y++;
				}
				
				//Add New Move to Trail
				walkTrail.add(new Point(x, y));
				
				//Add Trail to Maze
				maze[y][x] = 7;
				
				//look for loops and erase back if found
				boolean failedWalk = false;
				
				//Check if Random Walk Failed
				if(maze[y][x + 1] == 7 && direction != 0)
				{
					x++;
					failedWalk = true;
				}
				else if(maze[y][x - 1] == 7 && direction != 1)
				{
					x--;
					failedWalk = true;
				}
				else if(maze[y + 1][x] == 7 && direction != 2)
				{
					y++;
					failedWalk = true;
				}
				else if(maze[y - 1][x] == 7 && direction != 3)
				{
					y--;
					failedWalk = true;
				}
				
				//Random Walk Failed
				if(failedWalk)
				{
					Point trail_start = new Point(x, y);
					
					//Get Index of Trail Collision
					int retrace = walkTrail.indexOf(trail_start);
					
					//Delete Trail After Collision
					for(int j = retrace + 1; j < walkTrail.size(); j++)
					{
						Point p = walkTrail.get(j);
						
						//Add Wall Back
						maze[p.y][p.x] = 1;
					}
					
					//Reset Trail and Moves to Collision Point
					walkTrail.subList(retrace + 1, walkTrail.size()).clear();
					moves.subList(retrace / 2 + 1, moves.size()).clear();

					break;
				}
				
				//Random Walk Successful
				if (i == 0 && (maze[y][x-1] == 0 || maze[y][x+1] == 0 || maze[y-1][x] == 0 || maze[y+1][x] == 0)) 
				{

					for(Point p : walkTrail)
					{
						maze[p.y][p.x] = 0;
					}
					
					//Break Infinite Loop
					return;
				}
				
			}
			
			moves.add(direction);
		}
	}
	
	private static int NextStart(int[][]maze, int x, int y)
	{
		int wallCount = 0;
		
		int NewPosition = 1;
		
		for(int j = 23; j >= 1; j--)
		{
			for(int i = 1; i <= 33; i++)
			{
				//Start Checking at End of Last Random Walk
				if(NewPosition == 1)
				{
					j = y;
					i = x;
					NewPosition = 0;
				}
				
				//Count Number of Adjacent Walls
				wallCount = 0;
				
				//Check Adjacent Squares
				int [] dx = {-1, 0, 1, -1, 1, -1, 0, 1};
				int [] dy = {-1, -1, -1, 0, 0, 1, 1, 1};
				
				//Check if Adjacent Square for Wall
				for(int k = 0; k < 8; k++)
				{
					if(maze[j + dy[k]][i + dx[k]] == 1)
					{
						wallCount++;
					}
				}
				
				//Set New Start Position
				if(wallCount == 8)
				{
					nextY = j;
					nextX = i;
					return 1;
				}
			}
		}
		
		//No New Start Position
		return 0;
		
	}
}

