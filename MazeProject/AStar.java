package MazeProject;

import java.util.ArrayList;
import java.util.List;


public class AStar 
{
	//Store Points With Cost Values
	private static ArrayList<Point> heuristicPoints = new ArrayList<Point>();
	
    private static boolean ValidSquare(int [][]maze, int x, int y) 
    {
    	//Check of Coordinate is Valid in Maze
        if(x >= 1 && x < 34 && y >= 1 && y < 24 && (maze[y][x] == 0 || maze[y][x] == 9)) 
        {
            return true;
        }
        return false;
    }

    //Sort List of Points From Lowest to Highest
    private static void bubbleSort(ArrayList<Point> list)
    {
    	//Temporary Point For Swapping
    	Point temp;
    	
    	//Iterate over List
    	for(int i = 0; i < list.size() - 1; i++)
    	{
    		//Check if Point is Bigger
    		if(list.get(i).getF() > list.get(i + 1).getF())
    		{
    			//Swap Statements
    			temp = list.get(i);
				list.remove(i);
				list.add(i + 1, temp);
    		}
    	}
    	
    }
    
	private static Point AStarPath(int[][]maze, int xStart, int yStart, int xDest, int yDest)
	{
    	//Add Initial Start Position to Queue
    	heuristicPoints.add(new Point(xStart, yStart, null));
    	
    	//Initialize Costs
		double fcost = 0;
		double gcost = 0;
		double hcost = 0;
		
		while(!heuristicPoints.isEmpty())
		{
			//Sort Points
			bubbleSort(heuristicPoints);
			
			Point p = heuristicPoints.remove(0);
			
            //Base Case
            if(maze[p.y][p.x] == 9) 
            {
                //Destination Node Found
                return p;
            }
            
			//Check Adjacent Squares
			int [] dx = {0, 0, -1, 1};
			int [] dy = {-1, 1, 0, 0};
			
			for(int i = 0; i < 4; i++)
			{
				//Check Neighbors
				if(ValidSquare(maze, p.x + dx[i], p.y + dy[i])) 
				{
					//Position Has Been Checked
					maze[p.y][p.x] = 3;
					
					//Create New Point for Adjacent Square
					Point m = new Point(p.x  + dx[i], p.y  + dy[i], p);
					
					//Distance From Start Node
					gcost = Math.sqrt(Math.abs((p.x + dx[i]) - p.x) + Math.abs((p.y + dy[i]) - p.y));
					
					//Distance From End Node
					hcost = Math.sqrt(Math.abs((p.x + dx[i]) - xDest) + Math.abs((p.y + dy[i]) - yDest));
					
					//Calculate F Cost
					fcost = gcost + hcost;
					
					//Set G H F Costs
					m.setG(gcost);
					m.setH(hcost);
					m.setF(fcost);
					
	                //Add Point to List
	                heuristicPoints.add(m);
				}
			}
		}
		
		//No Path
		return null;
	}

    public static void PathFind(int[][]maze, int xStart, int yStart, int xDest, int yDest, List<Integer> path)
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
