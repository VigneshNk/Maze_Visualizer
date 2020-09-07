package MazeProject;

import java.util.ArrayList;
import java.util.Random;


public class RandomKruskal 
{
	private static class Edge
	{
        Point middle;
        int start;
        int end;

        public Edge(Point middle, int start, int end) 
        {
            this.middle = middle;
            this.start = start;
            this.end = end;
        }
	}
	
	private static class Graph
	{
	
		int nodes;
		ArrayList <Edge> allEdges = new ArrayList<>();
    
		Graph(int vertices) 
		{
			this.nodes = vertices;
		}
    
		private void addEdge(Point middle, int start, int end) 
		{
			Edge edge = new Edge(middle, start, end);
        	allEdges.add(edge); //add to total edges
		}
    
    	private void KruskalTree(int[][]maze)
    	{
    		Random random = new Random();

    		//Set of Parent Connections
    		int [] parent = new int[nodes];

    		//Make Set of Nodes
    		makeSet(parent);
        
    		int index = 0;
        
    		while(index < nodes - 1)
        	{
    			//Get Random Edge From Graph
                Edge edge = allEdges.remove(random.nextInt(allEdges.size()));
                
                //Check if Edge Creates a Cycle
                int x_set = find(parent, edge.start);
                int y_set = find(parent, edge.end);
                
                if(x_set != y_set)
                {
                	//Add Edge to Maze
                	maze[edge.middle.y][edge.middle.x] = 0;
                    
                	//Join Nodes in Set
                    union(parent, x_set, y_set);
                    
                    index++;
                }
        	}
    	}

	
    	private void makeSet(int [] parent)
    	{
    		//Create Set of Unconnected Nodes
    		for (int i = 0; i < nodes; i++) 
    		{
    			parent[i] = i;
    		}
    	}
    
    	private int find(int [] parent, int vertex)
    	{
    		//Chain of Parent Pointers
        	if(parent[vertex] != vertex)
        	{
        		//Find Parent of Vertex
        		return find(parent, parent[vertex]);
        	}
        	else
        	{
        		//Parent Found
        		return vertex;
        	}
    	}
    
    	//Connect X And Y
    	private void union(int [] parent, int x, int y)
    	{
    		//Find Parent of X
    		int x_parent = find(parent, x);
    		
    		//Find Parent of Y
    		int y_parent = find(parent, y);
    		
    		//Set X as Parent of Y
    		parent[y_parent] = x_parent;
    	}

	}
	
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
	
	//Create Cells
	private static void CreateNodes(int[][]maze, int x, int y)
	{
		for(int i = 1; i < y; i += 2)
		{
			for(int j = 1; j < x; j += 2)
			{
				maze[i][j] = 0;
			}
		}
	}
	
	public static void RandomizedKruskal(int[][]maze, int x, int y)
	{
		//Block Entire Maze
		WallMaze(maze, x, y);
		
		//New Graph
		Graph graph = new Graph(204);
		
		int count = 0;
		
		for(int j = 1; j <= 23; j += 2)
		{
			for(int i = 1; i <= 33; i += 2)
			{
				if(i != 33)
				{
					//Add Edge to Point on Right
					graph.addEdge(new Point(i + 1, j), count, count + 1);
				}
				
				if(j != 23)
				{
					//Add Edge to Point Below
					graph.addEdge(new Point(i, j + 1), count, count + 17);
				}

				count ++;
			}
		}
		
		//Create Cells
		CreateNodes(maze, x, y);
		
		//Produce Minimal Spanning Tree
		graph.KruskalTree(maze);
	}
}
