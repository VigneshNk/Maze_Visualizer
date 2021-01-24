package MazeGeneration;

public class RecursiveDivision {
	public static void generate(int[][] maze, int startX, int startY, int endX, int endY) {
		// Calculate Length and Width of Section
		int dx = endX - startX;
		int dy = endY - startY;

		if (dx < 2 || dy < 2) {
			// Base Case
			return;
		}

		if (dy > dx) {
			// X Coordinate of Hole in Wall
			int xp = (int) (Math.random() * (endX - startX + 1) + startX);

			// Y Coordinate of Wall
			int yp = (int) (Math.random() * (endY - 1 - (startY + 1) + 1) + (startY + 1));

			// Create Passage if Cross Section
			if (maze[yp][startX - 1] == 0) {
				xp = startX;

			} else if (maze[yp][endX + 1] == 0) {
				xp = endX;
			}

			// Create Wall
			for (int i = startX; i <= endX; i++) {
				if (i != xp) {
					maze[yp][i] = 1;
				}
			}

			// Above Wall
			generate(maze, startX, startY, endX, yp - 1);

			// Below Wall
			generate(maze, startX, yp + 1, endX, endY);

		} else {
			// Y Coordinate of Hole in Wall
			int yp = (int) (Math.random() * (endY - startY + 1) + startY);

			// X Coordinate of Wall
			int xp = (int) (Math.random() * (endX - 1 - (startX + 1) + 1) + (startX + 1));

			// Create Passage if Cross Section
			if (maze[startY - 1][xp] == 0) {
				yp = startY;

			} else if (maze[endY + 1][xp] == 0) {
				yp = endY;
			}

			// Create Wall
			for (int i = startY; i <= endY; i++) {
				if (i != yp) {
					maze[i][xp] = 1;
				}
			}

			// Left of Wall
			generate(maze, startX, startY, xp - 1, endY);

			// Right of Wall
			generate(maze, xp + 1, startY, endX, endY);
		}
	}
}
