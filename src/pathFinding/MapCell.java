package Pathfinding;

import java.awt.*;
import java.util.*;

/**
 * Created by slivka on 23.01.2016.
 */
public class MapCell implements Comparable<MapCell>
{
    public boolean passable;
    public Point location;

    public int distanceFromStart;
    public int distanceToEnd;

    public MapCell N = null;
    public MapCell S = null;
    public MapCell E = null;
    public MapCell W = null;

    /*
    public MapCell NE = null;
    public MapCell NW = null;
    public MapCell SE = null;
    public MapCell SW = null;
    */
    public MapCell parent;

    public ArrayList<MapCell> neighbours = new ArrayList<MapCell>();

    public MapCell(Point location, boolean passable, Point startingPoint, Point endingPoint)
    {
        this.passable = passable;
        this.location = location;
        distanceFromStart = calculateDistance(startingPoint);
        distanceToEnd = calculateDistance(endingPoint);
    }

    public void updateNeighbours()
    {
        if(N!=null)
        {
            neighbours.add(N);
        }
        if(S!=null)
        {
            neighbours.add(S);
        }
        if(E!=null)
        {
            neighbours.add(E);
        }
        if(W!=null)
        {
            neighbours.add(W);
        }
        /*
        if(NE!=null)
        {
            neighbours.add(NE);
        }
        if(NW!=null)
        {
            neighbours.add(NW);
        }
        if(SE!=null)
        {
            neighbours.add(SE);
        }
        if(SW!=null)
        {
            neighbours.add(SW);
        }
        */
    }


    private int calculateDistance(Point targetCell)
    {
        int distance = Math.abs(targetCell.x - location.x) + Math.abs(targetCell.y - location.y);
        return distance;
    }

    @Override
    public int compareTo(MapCell o)
    {
        if (distanceToEnd < o.distanceToEnd)
        {
            return -1;
        }
        else if (distanceToEnd > o.distanceToEnd)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
}
