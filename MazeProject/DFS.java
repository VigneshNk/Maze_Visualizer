package MazeProject;

import java.util.List;

public class DFS 
{
	public static int DepthFirstSearch(int[][] maze, int x, int y, List<Integer> path) 
	{
		//Destination Node Found
		if(maze[y][x] == 9) 
		{
			//Add Square to Path
			path.add(x);
			path.add(y);
			
			return 1;
		}

		if(maze[y][x] == 0) 
		{
			maze[y][x] = 3;

			//Check Adjacent Squares
			int[] dx = { 1, 0, -1, 0 };
			int[] dy = { 0, 1, 0, -1 };

			for(int i = 0; i < 4; i++) 
			{
				if(DepthFirstSearch(maze, x + dx[i], y + dy[i], path) == 1) 
				{
					//Add Square to Path
					path.add(x);
					path.add(y);

					return 1;
				}
			}
		}

		return 0;
	}
}
