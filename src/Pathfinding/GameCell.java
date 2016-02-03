package Pathfinding;

import java.awt.*;
import java.util.ArrayList;

public class GameCell implements Comparable<GameCell>
{
    public boolean passable;
    public Point location;

    public int distanceFromStart;
    public int distanceToEnd;

    public GameCell N = null;
    public GameCell S = null;
    public GameCell E = null;
    public GameCell W = null;

    public GameCell parent;

    public ArrayList<GameCell> neighbours = new ArrayList<GameCell>();

    public GameCell(Point location, boolean passable, Point startingPoint, Point endingPoint)
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
    }


    private int calculateDistance(Point targetCell)
    {
        int distance = Math.abs(targetCell.x - location.x) + Math.abs(targetCell.y - location.y);
        return distance;
    }

    @Override
    public int compareTo(GameCell o)
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