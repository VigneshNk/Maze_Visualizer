package MazeProject;

public class Point 
{
	int x;
    int y;
    double f;
    double g;
    double h;
    Point parent;

    public Point(int x, int y, Point parent) 
    {
        this.x = x;
        this.y = y;
        this.parent = parent;
    }
    
    public Point(int x, int y) 
    {
        this.x = x;
        this.y = y;
    }
    
    //Output X Coordinate
    public int getX() 
    {
  	  return this.x;
    }

    //Output Y Coordinate
    public int getY()
    {
  	  return this.y;
    }
    
    //Output F Cost
    public double getF()
    {
  	  return this.f;
    }
    
    //Output G Cost
    public double getG()
    {
  	  return this.g;
    }
    
    //Output H Cost
    public double getH()
    {
  	  return this.h;
    }

    //Output Parent Point
    public Point getParent() 
    {
        return this.parent;
    }
    
    //Set F Cost
    public void setF(double f)
    {
  	  this.f = f;
    }
    
    //Set G Cost
    public void setG(double g)
    {
  	  this.g = g;
    }
    
    //Set H Cost
    public void setH(double h)
    {
  	  this.h = h;
    }
    
    //Set Parent Point
    public void setParent(Point parent) 
    {
  	  this.parent = parent;
    }
}
