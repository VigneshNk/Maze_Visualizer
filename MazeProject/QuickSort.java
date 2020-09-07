package MazeProject;

import java.util.ArrayList;

public class QuickSort {
	
	public static int AStarPartition(ArrayList<Point> list, int low, int high) 
    { 
		//Get Pivot Value
        double pivot = list.get(high).getF(); 
        
        //Index of Minimum Element
        int i = low - 1;  
        
    	//Temporary Point For Swapping
    	Point temp;
        
    	//Sort Sub Array Around Pivot
        for (int j = low; j < high; j++) 
        { 
            //Current Element Smaller Than Pivot
            if (list.get(j).getF() < pivot) 
            { 
                i++; 
  
                //Swap Statements
                temp = list.get(i); 
                list.set(i, list.get(j));
                list.set(j, temp);
            } 
        } 
  
        //Swap Statements
        temp = list.get(i + 1); 
        list.set(i + 1, list.get(high));
        list.set(high, temp);
        
        //Output Partition
        return i + 1; 
    } 
	
	public static int DijkstraPartition(ArrayList<Point> list, int low, int high) 
    { 
		//Get Pivot Value
        Point pivot = list.get(high);  
        
        //Index of Minimum Element
        int i = low - 1;
        
    	//Temporary Point For Swapping
    	Point temp;
        
    	//Sort Sub Array Around Pivot
        for(int j = low; j < high; j++) 
        { 
        	//Current Element Smaller Than Pivot
            if((int)list.get(j).getG() < (int)pivot.getG()) 
            { 
                i++; 
  
                //Swap Statements
                temp = list.get(i); 
                list.set(i, list.get(j));
                list.set(j, temp);
            } 
        } 
  
        //Swap Statements
        temp = list.get(i + 1); 
        list.set(i + 1, list.get(high));
        list.set(high, temp);
  
        //Output Partition
        return i + 1; 
    } 
	
    public static void sort(ArrayList<Point> list, int low, int high, int type) 
    { 
        if (low < high) 
        { 
        	int pi;
        	
        	//Get Index of Partition
        	if(type == 1)
        	{
        		
        		pi = AStarPartition(list, low, high); 
        	}
        	else
        	{
        		pi = DijkstraPartition(list, low, high); 
        		System.out.println("ddd");
        	}
        	
        	//Recursively Sort Left of Partition
            sort(list, low, pi - 1, type); 
            
            //Recursively Sort Right of Partition
            sort(list, pi + 1, high, type); 
        } 
    } 
    
    public static ArrayList<Point> DijkstraQuickSort(ArrayList<Point> list)
    {
    	//Base Case
    	if (list.isEmpty()) 
    	{
            return list;
    	}
    	
    	//Sorted List to Return
        ArrayList<Point> sorted;
        
        //Points Smaller Than Pivot
        ArrayList<Point> smaller = new ArrayList<Point>();
        
        //Points Larger Than Pivot
        ArrayList<Point> greater = new ArrayList<Point>();
    
        
        //First Point Used as Pivot
        Point pivot = list.get(0);
        
        //Temporary Point For Swapping
        Point temp;
        
        for(int i = 1; i < list.size(); i++)
        {
        	temp = list.get(i);
        	
        	//Current Element Smaller Than Pivot
        	if(temp.getG() < pivot.getG())
        	{
                smaller.add(temp);
        	}
            else
            {
                greater.add(temp);
            }
        }
        
        //Recursively Sort Left of Pivot
        smaller = DijkstraQuickSort(smaller);

        //Recursively Sort Right of Pivot
        greater = DijkstraQuickSort(greater);
        
        //Add Sub Lists Together
        smaller.add(pivot);
        smaller.addAll(greater);
        sorted = smaller;

        return sorted;
    }
    
    public static ArrayList<Point> AStarQuickSort(ArrayList<Point> list)
    {
    	//Base Case
    	if (list.isEmpty()) 
    	{
            return list;
    	}
    	
    	//Sorted List to Return
        ArrayList<Point> sorted;
        
        //Points Smaller Than Pivot
        ArrayList<Point> smaller = new ArrayList<Point>();
        
        //Points Larger Than Pivot
        ArrayList<Point> greater = new ArrayList<Point>();
    
        //First Point Used as Pivot
        Point pivot = list.get(0);
        
        //Temporary Point For Swapping
        Point temp;
        
        for(int i = 1; i < list.size(); i++)
        {
        	temp = list.get(i);
        	
        	//Current Element Smaller Than Pivot
        	if(temp.getF() < pivot.getF())
        	{
                smaller.add(temp);
        	}
            else
            {
                greater.add(temp);
            }
        }
        
        //Recursively Sort Left of Pivot
        smaller = AStarQuickSort(smaller);

        //Recursively Sort Right of Pivot
        greater = AStarQuickSort(greater);
        
        //Add Sub Lists Together
        smaller.add(pivot);
        smaller.addAll(greater);
        sorted = smaller;

        return sorted;
    }
}
