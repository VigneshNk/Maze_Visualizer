package Pathfinding;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import Visualization.Point;

public class BFS {
	private static Queue<Point> points = new LinkedList<Point>();

	// Check if Coordinate is Valid in Maze
	private static boolean ValidSquare(int[][] maze, int x, int y) {
		if (y >= 1 && y < maze.length - 1 && x >= 1 && x < maze[y].length - 1 && (maze[y][x] == 0 || maze[y][x] == 9)) {
			return true;
		}

		return false;
	}

	// Check if Point is Already in Queue
	private static boolean NonDuplicatePoint(int x, int y) {
		for (Point temp : points) {
			if (temp.getX() == x && temp.getY() == y) {
				// Duplicate Point
				return false;
			}
		}

		// Point is Unique
		return true;
	}

	private static Point FindPath(int[][] maze, int x, int y, List<Integer> searchArea) {
		// Add Initial Start Position to Queue
		points.add(new Point(x, y, null));

		// Adjacent Square Offsets
		int[] dx = { 1, 0, -1, 0 };
		int[] dy = { 0, 1, 0, -1 };

		while (!points.isEmpty()) {
			Point p = points.remove();

			// Base Case
			if (maze[p.getY()][p.getX()] == 9) {
				// Destination Node Found
				return p;
			}

			// Position Has Been Checked
			maze[p.getY()][p.getX()] = 3;

			// Add Square to Search Area
			searchArea.add(p.getX());
			searchArea.add(p.getY());

			for (int i = 0; i < 4; i++) {
				// Check Neighbors
				if (ValidSquare(maze, p.getX() + dx[i], p.getY() + dy[i])
						&& NonDuplicatePoint(p.getX() + dx[i], p.getY() + dy[i])) {

					// Add New Point to Search Queue
					points.add(new Point(p.getX() + dx[i], p.getY() + dy[i], p));
				}
			}
		}

		// No Path
		return null;
	}

	public static void pathfind(int[][] maze, int x, int y, List<Integer> path, List<Integer> searchArea) {
		// Conduct BFS
		Point p = FindPath(maze, x, y, searchArea);

		if (p != null) {
			// Back Track Parent Nodes
			while (p.getParent() != null) {

				// Add Parent to Path
				path.add(p.getX());
				path.add(p.getY());

				// Next Parent
				p = p.getParent();
			}
		}

		// Clear Queue
		points.clear();
	}
}
