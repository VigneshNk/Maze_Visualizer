package MazeProject;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

public class AStar 
{
	//Comparator for F Cost Field
	private static Comparator<Point> AStarSorter = Comparator.comparing(Point::getF);

	//Store Points With Cost Values Giving Priority to F Costs
	private static PriorityQueue<Point> heuristicPoints = new PriorityQueue<>(AStarSorter);

	//Check if Coordinate is Valid in Maze
	private static boolean ValidSquare(int[][] maze, int x, int y) 
	{
		if(x >= 1 && x < 34 && y >= 1 && y < 24 && (maze[y][x] == 0 || maze[y][x] == 9)) 
		{
			return true;
		}
		return false;
	}

	//Check if Point is Already in List
	private static boolean NonDuplicatePoint(int x, int y) 
	{
		Point temp;

		Iterator<Point> iterator = heuristicPoints.iterator();

		//Iterate Over Priority Queue
		while(iterator.hasNext()) 
		{
			temp = iterator.next();

			//Check if Point is Duplicate
			if(temp.getX() == x && temp.getY() == y) 
			{
				return false;
			}
		}

		//Point is Unique
		return true;
	}

	private static Point AStarPath(int[][] maze, int xStart, int yStart, int xDest, int yDest) 
	{
		//Add Initial Start Position to Queue
		heuristicPoints.add(new Point(xStart, yStart, null));

		//Initialize Costs
		int fcost = 0;
		int gcost = 0;
		int hcost = 0;

		while(!heuristicPoints.isEmpty()) 
		{
			Point p = heuristicPoints.poll();

			//Base Case
			if(maze[p.y][p.x] == 9) 
			{
				//Destination Node Found
				return p;
			}

			//Check Adjacent Squares
			int[] dx = { 0, 0, -1, 1 };
			int[] dy = { -1, 1, 0, 0 };

			for(int i = 0; i < 4; i++) 
			{
				//Check Neighbors
				if(ValidSquare(maze, p.x + dx[i], p.y + dy[i]) && NonDuplicatePoint(p.x + dx[i], p.y + dy[i])) 
				{
					//Position Has Been Checked
					maze[p.y][p.x] = 3;

					//Create New Point for Adjacent Square
					Point m = new Point(p.x + dx[i], p.y + dy[i], p);

					//Distance From Start Node
					gcost = Math.abs((p.x + dx[i]) - p.x) + Math.abs((p.y + dy[i]) - p.y);
					
					//Destination is Horizontal or Vertical From Point
					if(Math.abs((p.x + dx[i]) - xDest) == 0 || Math.abs((p.y + dy[i]) - yDest) == 0)
					{
						//Distance From End Node
						hcost = Math.abs((p.x + dx[i]) - xDest) + Math.abs((p.y + dy[i]) - yDest);
					}
					//Destination is Diagonal From Point
					else
					{
						//Distance From End Node
						hcost = 2 * (Math.abs((p.x + dx[i]) - xDest) + Math.abs((p.y + dy[i]) - yDest));
					}

					//Calculate F Cost
					fcost = gcost + hcost;

					//Set G H F Costs
					m.setF(fcost);

					//Add Point to List
					heuristicPoints.add(m);
				}
			}
		}

		// No Path
		return null;
	}

	public static void PathFind(int[][] maze, int xStart, int yStart, int xDest, int yDest, List<Integer> path)
	{
		//Conduct A Star
		Point p = AStarPath(maze, xStart, yStart, xDest, yDest);

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

		//Clear List
		heuristicPoints.clear();
	}
}
