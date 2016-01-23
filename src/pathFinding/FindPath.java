package pathFinding;

import dataClasses.BattlefieldCell;

import java.awt.*;
import java.util.*;

/**
 * Created by slivka on 23.01.2016.
 */
public class FindPath
{
    private BattlefieldMap map;
    private Point startPoint;
    private Point endPoint;

    private ArrayList<MapCell> closedList;
    private openList _openList;

    private MapCell endingCell;

    public FindPath(BattlefieldCell[][] battlefieldInfo, Point startPoint, Point endPoint)
    {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        map = new BattlefieldMap(battlefieldInfo,startPoint,endPoint);
        closedList = new ArrayList<MapCell>();
        _openList = new openList();
    }

    public ArrayList<Point> generatePath()
    {
        getPath();
        return reconstructPath(endingCell);
    }

    private void getPath()
    {
        closedList.clear();
        _openList.clear();
        _openList.add(map.getCell(startPoint));

        while (_openList.size() != 0)
        {
            MapCell current = _openList.getFirst();
            if(current.location.x == endPoint.x && current.location.y == endPoint.y)
            {
                endingCell = current;
            }

            _openList.remove(current);
            closedList.add(current);

            for(MapCell c :current.neighbours)
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

    private ArrayList<Point> reconstructPath(MapCell c)
    {
        Stack<MapCell> st = new Stack<MapCell>();
        ArrayList<Point> output = new ArrayList<Point>();
        MapCell current = c;
        while (current.parent != null)
        {
            st.push(current);
            System.out.println("ALG: "+current.location);
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
        private ArrayList<MapCell> list = new ArrayList<MapCell>();

        public MapCell getFirst()
        {
            return list.get(0);
        }

        public void add(MapCell c)
        {
            list.add(c);
            Collections.sort(list);
        }

        public void remove(MapCell c)
        {
            list.remove(c);
        }

        public int size()
        {
            return list.size();
        }

        public boolean contains(MapCell c)
        {
            return list.contains(c);
        }

        public void clear()
        {
            list.clear();
        }
    }
}
