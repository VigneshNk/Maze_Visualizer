package Pathfinding;

import java.util.List;

public class DFS {
	public static int pathfind(int[][] maze, int x, int y, List<Integer> path, List<Integer> searchArea) {
		// Destination Node Found
		if (maze[y][x] == 9) {

			// Add Square to Path
			path.add(x);
			path.add(y);

			return 1;
		}

		if (maze[y][x] == 0) {
			maze[y][x] = 3;

			// Add Square to Search Area
			searchArea.add(x);
			searchArea.add(y);

			// Check Adjacent Squares
			int[] dx = { 1, 0, -1, 0 };
			int[] dy = { 0, 1, 0, -1 };

			for (int i = 0; i < 4; i++) {
				if (pathfind(maze, x + dx[i], y + dy[i], path, searchArea) == 1) {

					// Add Square to Path
					path.add(x);
					path.add(y);

					return 1;
				}
			}
		}

		return 0;
	}
}
