package MazeProject;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFS 
{
	private static Queue<Point> points = new LinkedList<Point>();

	//Check if Coordinate is Valid in Maze
	private static boolean ValidSquare(int[][] maze, int x, int y) 
	{
		
		if(x >= 1 && x < 34 && y >= 1 && y < 24 && (maze[y][x] == 0 || maze[y][x] == 9)) 
		{
			return true;
		}
		return false;
	}

	//Check if Point is Already in Queue
	private static boolean NonDuplicatePoint(int x, int y) 
	{
		for(Point temp : points) 
		{
			if(temp.getX() == x && temp.getY() == y) 
			{
				//Duplicate Point
				return false;
			}
		}

		//Point is Unique
		return true;
	}

	private static Point FindPath(int[][] maze, int x, int y) 
	{
		//Add Initial Start Position to Queue
		points.add(new Point(x, y, null));

		while(!points.isEmpty()) 
		{
			Point p = points.remove();

			//Base Case
			if(maze[p.y][p.x] == 9) 
			{
				//Destination Node Found
				return p;
			}

			//Check Adjacent Squares
			int[] dx = { 1, 0, -1, 0 };
			int[] dy = { 0, 1, 0, -1 };

			for(int i = 0; i < 4; i++) 
			{
				//Check Neighbors
				if(ValidSquare(maze, p.x + dx[i], p.y + dy[i]) && NonDuplicatePoint(p.x + dx[i], p.y + dy[i])) 
				{
					//Position Has Been Checked
					maze[p.y][p.x] = 3;

					points.add(new Point(p.x + dx[i], p.y + dy[i], p));
				}
			}
		}

		//No Path
		return null;
	}

	public static void BreadthFirstSearch(int[][] maze, int x, int y, List<Integer> path) 
	{
		//Conduct BFS
		Point p = FindPath(maze, x, y);

		if(p != null) 
		{
			//Back Track Parent Nodes
			while(p.getParent() != null) 
			{
				//Add Parent to Path
				path.add(p.x);
				path.add(p.y);

				//Next Parent
				p = p.getParent();
			}
		}

		//Clear Queue
		points.clear();
	}
}
