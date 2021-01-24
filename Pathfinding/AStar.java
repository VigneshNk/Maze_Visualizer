package Pathfinding;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import Visualization.Point;

public class AStar {
	// Comparator for F Cost Field
	private static Comparator<Point> AStarSorter = Comparator.comparing(Point::getF);

	// Store Points With Cost Values Giving Priority to F Costs
	private static PriorityQueue<Point> heuristicPoints = new PriorityQueue<>(AStarSorter);

	// Check if Coordinate is Valid in Maze
	private static boolean ValidSquare(int[][] maze, int x, int y) {
		if (y >= 1 && y < maze.length - 1 && x >= 1 && x < maze[y].length - 1 && (maze[y][x] == 0 || maze[y][x] == 9)) {
			return true;
		}

		return false;
	}

	// Check if Point is Already in List
	private static boolean NonDuplicatePoint(int x, int y) {
		Point temp;

		Iterator<Point> iterator = heuristicPoints.iterator();

		// Iterate Over Priority Queue
		while (iterator.hasNext()) {
			temp = iterator.next();

			// Check if Point is Duplicate
			if (temp.getX() == x && temp.getY() == y) {
				return false;
			}
		}

		// Point is Unique
		return true;
	}

	private static Point AStarPath(int[][] maze, int xStart, int yStart, int xDest, int yDest,
			List<Integer> searchArea) {
		// Add Initial Start Position to Queue
		heuristicPoints.add(new Point(xStart, yStart, null));

		// Initialize Costs
		int fcost = 0;
		int gcost = 0;
		int hcost = 0;

		// Adjacent Square Offsets
		int[] dx = { 1, 0, -1, 0 };
		int[] dy = { 0, 1, 0, -1 };

		while (!heuristicPoints.isEmpty()) {
			Point p = heuristicPoints.poll();

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

					// Create New Point for Adjacent Square
					Point m = new Point(p.getX() + dx[i], p.getY() + dy[i], p);

					// Distance From Start Node
					gcost = Math.abs((p.getX() + dx[i]) - p.getX()) + Math.abs((p.getY() + dy[i]) - p.getY());

					// Destination is Horizontal or Vertical From Point
					if (Math.abs((p.getX() + dx[i]) - xDest) == 0 || Math.abs((p.getY() + dy[i]) - yDest) == 0) {

						// Distance From End Node
						hcost = Math.abs((p.getX() + dx[i]) - xDest) + Math.abs((p.getY() + dy[i]) - yDest);
					}

					// Destination is Diagonal From Point
					else {
						// Distance From End Node
						hcost = 2 * (Math.abs((p.getX() + dx[i]) - xDest) + Math.abs((p.getY() + dy[i]) - yDest));
					}

					// Calculate F Cost
					fcost = gcost + hcost;

					// Set G H F Costs
					m.setF(fcost);

					// Add Point to List
					heuristicPoints.add(m);
				}
			}
		}

		// No Path
		return null;
	}

	public static void pathfind(int[][] maze, int xStart, int yStart, int xDest, int yDest, List<Integer> path,
			List<Integer> searchArea) {
		// Conduct A Star
		Point p = AStarPath(maze, xStart, yStart, xDest, yDest, searchArea);

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

		// Clear List
		heuristicPoints.clear();
	}
}
