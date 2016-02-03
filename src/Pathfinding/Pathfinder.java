package Pathfinding;


import Map.Tile;

import java.awt.*;
import java.util.*;



public class Pathfinder
{
    private GameMap map;
    private Point startPoint;
    private Point endPoint;

    private ArrayList<GameCell> closedList;
    private openList _openList;

    private GameCell endingCell;

    private Tile[][] bInfo;

    public Pathfinder(Tile[][] battlefieldInfo, Point startPoint, Point endPoint)
    {

        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.bInfo = battlefieldInfo;
        map = new GameMap(battlefieldInfo,startPoint,endPoint);
        closedList = new ArrayList<GameCell>();
        _openList = new openList();
    }

    public ArrayList<Point> generatePath()
    {
        getPath();
        ArrayList<Point> cellPath = reconstructPath(endingCell);
        ArrayList<Point> paintPath = new ArrayList<Point>();
        for(Point p :cellPath)
        {
            paintPath.add(bInfo[p.x][p.y].getDrawingPoint());
        }
        return paintPath;
    }

    private void getPath()
    {
        closedList.clear();
        _openList.clear();
        _openList.add(map.getCell(startPoint));

        while (_openList.size() != 0)
        {
            GameCell current = _openList.getFirst();
            if(current.location.x == endPoint.x && current.location.y == endPoint.y)
            {
                endingCell = current;
            }

            _openList.remove(current);
            closedList.add(current);

            for(GameCell c :current.neighbours)
            {
                boolean isBetter;

                if(closedList.contains(c))
                {
                    continue;
                }
                if (c.passable)
                {
                    if(!_openList.contains(c))
                    {
                        _openList.add(c);
                        isBetter = true;
                    }
                    else if(c.distanceFromStart < current.distanceFromStart)
                    {
                        isBetter = true;
                    }
                    else
                    {
                        isBetter = false;
                    }
                    if(isBetter)
                    {
                        c.parent = current;
                    }
                }
            }
        }
    }

    private ArrayList<Point> reconstructPath(GameCell c)
    {
        Stack<GameCell> st = new Stack<GameCell>();
        ArrayList<Point> output = new ArrayList<Point>();
        GameCell current = c;
        while (current.parent != null)
        {
            st.push(current);
            current = current.parent;
        }
        output.add(startPoint);
        while (st.size()!=0)
        {
            output.add(st.pop().location);
        }
        return output;
    }

    private class openList
    {
        private ArrayList<GameCell> list = new ArrayList<GameCell>();

        public GameCell getFirst()
        {
            return list.get(0);
        }

        public void add(GameCell c)
        {
            list.add(c);
            Collections.sort(list);
        }

        public void remove(GameCell c)
        {
            list.remove(c);
        }

        public int size()
        {
            return list.size();
        }

        public boolean contains(GameCell c)
        {
            return list.contains(c);
        }

        public void clear()
        {
            list.clear();
        }
    }
}