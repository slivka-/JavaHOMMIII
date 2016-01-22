package battleDisplay;

import dataClasses.BattlefieldCell;
import dataClasses.CellEntity;

import java.awt.*;
import java.util.ArrayList;


/**
 * Created by slivka on 22.01.2016.
 */
public class PathFinding
{
    private static class pathTile
    {
        public Point _p;
        public Boolean _passable;

        public pathTile(Point p,Boolean passable)
        {
            _p = p;
            _passable = passable;
        }
    }

    private static BattlefieldCell[][] _battlefieldInfo;

    public static ArrayList<Point> findPath(Point startingPoint, Point endingPoint, BattlefieldCell[][] battlefieldInfo)
    {
        _battlefieldInfo = battlefieldInfo;
        ArrayList<pathTile> path = lineFind(startingPoint.x,startingPoint.y,endingPoint.x,endingPoint.y);
        ArrayList<ArrayList<pathTile>> fixes = generatePathFixes(path);
        ArrayList<Point> fixedPath = fixPath(path, fixes);
        return fixedPath;
    }

    private static  ArrayList<pathTile> lineFind(int x,int y,int x2, int y2) {
        int w = x2 - x ;
        int h = y2 - y ;
        int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0 ;
        if (w<0) dx1 = -1 ; else if (w>0) dx1 = 1 ;
        if (h<0) dy1 = -1 ; else if (h>0) dy1 = 1 ;
        if (w<0) dx2 = -1 ; else if (w>0) dx2 = 1 ;
        int longest = Math.abs(w) ;
        int shortest = Math.abs(h) ;
        if (!(longest>shortest)) {
            longest = Math.abs(h) ;
            shortest = Math.abs(w) ;
            if (h<0) dy2 = -1 ; else if (h>0) dy2 = 1 ;
            dx2 = 0 ;
        }
        int numerator = longest >> 1 ;
        ArrayList<pathTile> path = new ArrayList<pathTile>();
        for (int i=0;i<=longest;i++) {
            if(_battlefieldInfo[x][y].contains == CellEntity.OBSTACLE)
            {
                path.add(new pathTile(new Point(x,y),false));
            }
            else
            {
                path.add(new pathTile(new Point(x,y),true));
            }
            numerator += shortest ;
            if (!(numerator<longest)) {
                numerator -= longest ;
                x += dx1 ;
                y += dy1 ;
            } else {
                x += dx2 ;
                y += dy2 ;
            }
        }
        return path;
    }

    private static ArrayList<Point> fixPath(ArrayList<pathTile> path, ArrayList<ArrayList<pathTile>> fixes)
    {
        ArrayList<Point> fixedPath = new ArrayList<Point>();
        int fixCount = 0;
        for (pathTile p :path)
        {
            if(p._passable)
            {

                fixedPath.add(new Point(p._p.y,p._p.x));
            }
            else
            {

                ArrayList<pathTile> fix = fixes.get(fixCount);
                for(int i = 0;i<fix.size()-1;i++)
                {
                    fixedPath.add(new Point(fix.get(i)._p.y,fix.get(i)._p.x));
                }
                fixCount++;
            }
        }
        return fixedPath;
    }

    private static ArrayList<ArrayList<pathTile>> generatePathFixes(ArrayList<pathTile> path)
    {
        ArrayList<ArrayList<pathTile>> arrOfFixes = new ArrayList<ArrayList<pathTile>>();

        int iterator = 0;
        for(pathTile z : path)
        {
            if(!z._passable)
            {
                //System.out.println("X:" + path.get(iterator - 1)._p.y + ", Y:" + path.get(iterator - 1)._p.x + ", passable:" + path.get(iterator - 1)._passable);
                //System.out.println("X:" + path.get(iterator)._p.y + ", Y:" + path.get(iterator)._p.x + ", passable:" + path.get(iterator)._passable);
                //System.out.println("X:" + path.get(iterator + 1)._p.y + ", Y:" + path.get(iterator + 1)._p.x + ", passable:" + path.get(iterator + 1)._passable);

                ArrayList<pathTile> pass1 = new ArrayList<pathTile>();
                ArrayList<pathTile> pass2 = new ArrayList<pathTile>();

                Point newStartPoint = new Point(path.get(iterator-1)._p.x,path.get(iterator-1)._p.y);
                int numOfObstacles = 1;
                while(!path.get(iterator+numOfObstacles)._passable)
                {
                    numOfObstacles++;
                }
                Point newEndPoint = new Point(path.get(iterator+numOfObstacles)._p.x,path.get(iterator+1)._p.y);

                if(z._p.x == newStartPoint.x)
                {
                    //nad albo pod
                    if(path.get(iterator-1)._p.x<=12)
                    {
                        newStartPoint.setLocation(path.get(iterator - 1)._p.x + 1, path.get(iterator - 1)._p.y + (z._p.y - newStartPoint.y));
                        pass1 = lineFind(newStartPoint.x, newStartPoint.y, newEndPoint.x, newEndPoint.y);
                    }

                    if(path.get(iterator-1)._p.x>=1)
                    {
                        newStartPoint.setLocation(path.get(iterator - 1)._p.x - 1, path.get(iterator - 1)._p.y + (z._p.y - newStartPoint.y));
                        pass2 = lineFind(newStartPoint.x, newStartPoint.y, newEndPoint.x, newEndPoint.y);
                    }

                }
                else if(z._p.y == newStartPoint.y)
                {
                    //z prawej lub lewej
                    if(path.get(iterator-1)._p.y<=8)
                    {
                        newStartPoint.setLocation(path.get(iterator - 1)._p.x + (z._p.x - newStartPoint.x), path.get(iterator - 1)._p.y + 1);
                        pass1 = lineFind(newStartPoint.x, newStartPoint.y, newEndPoint.x, newEndPoint.y);
                    }
                    if(path.get(iterator-1)._p.y>=1)
                    {
                        newStartPoint.setLocation(path.get(iterator - 1)._p.x + (z._p.x - newStartPoint.x), path.get(iterator - 1)._p.y - 1);
                        pass2 = lineFind(newStartPoint.x, newStartPoint.y, newEndPoint.x, newEndPoint.y);
                    }
                }
                else
                {
                    if((int)Math.signum(z._p.x - newStartPoint.x)>0)
                    {
                        //Z LEWEJ
                        if((int)Math.signum(z._p.y - newStartPoint.y)>0)
                        {
                            //z góry
                            if(path.get(iterator-1)._p.x<=12)
                            {
                                newStartPoint.setLocation(path.get(iterator - 1)._p.x + 1, path.get(iterator - 1)._p.y);
                                pass1 = lineFind(newStartPoint.x, newStartPoint.y, newEndPoint.x, newEndPoint.y);
                            }
                            if(path.get(iterator-1)._p.y<=8)
                            {
                                newStartPoint.setLocation(path.get(iterator - 1)._p.x, path.get(iterator - 1)._p.y + 1);
                                pass2 = lineFind(newStartPoint.x, newStartPoint.y, newEndPoint.x, newEndPoint.y);
                            }
                        }
                        else
                        {
                            //z dołu
                            if(path.get(iterator-1)._p.x<=12)
                            {
                                newStartPoint.setLocation(path.get(iterator - 1)._p.x + 1, path.get(iterator - 1)._p.y);
                                pass1 = lineFind(newStartPoint.x, newStartPoint.y, newEndPoint.x, newEndPoint.y);
                            }
                            if(path.get(iterator-1)._p.y>=1)
                            {
                                newStartPoint.setLocation(path.get(iterator - 1)._p.x, path.get(iterator - 1)._p.y - 1);
                                pass2 = lineFind(newStartPoint.x, newStartPoint.y, newEndPoint.x, newEndPoint.y);
                            }
                        }
                    }
                    else
                    {
                        //Z PRAWEJ
                        if((int)Math.signum(z._p.y - newStartPoint.y)>0)
                        {
                            //z góry
                            if(path.get(iterator-1)._p.x>=1)
                            {
                                newStartPoint.setLocation(path.get(iterator - 1)._p.x - 1, path.get(iterator - 1)._p.y);
                                pass1 = lineFind(newStartPoint.x, newStartPoint.y, newEndPoint.x, newEndPoint.y);
                            }

                            if(path.get(iterator-1)._p.y<=8)
                            {
                                newStartPoint.setLocation(path.get(iterator - 1)._p.x, path.get(iterator - 1)._p.y + 1);
                                pass2 = lineFind(newStartPoint.x, newStartPoint.y, newEndPoint.x, newEndPoint.y);
                            }
                        }
                        else
                        {
                            //z dołu
                            if(path.get(iterator-1)._p.x>=1)
                            {
                                newStartPoint.setLocation(path.get(iterator - 1)._p.x - 1, path.get(iterator - 1)._p.y);
                                pass1 = lineFind(newStartPoint.x, newStartPoint.y, newEndPoint.x, newEndPoint.y);
                            }

                            if(path.get(iterator-1)._p.y>=1)
                            {
                                newStartPoint.setLocation(path.get(iterator - 1)._p.x, path.get(iterator - 1)._p.y - 1);
                                pass2 = lineFind(newStartPoint.x, newStartPoint.y, newEndPoint.x, newEndPoint.y);
                            }
                        }
                    }
                }
                boolean doSecond = false;

                for(int i=0;i<pass1.size();i++)
                {
                    if(!pass1.get(i)._passable)
                    {
                        doSecond = true;
                        break;
                    }
                    if(i==pass1.size()-1)
                    {
                        arrOfFixes.add(pass1);
                    }
                }
                if(doSecond)
                {
                    for (int i = 0; i < pass2.size(); i++)
                    {
                        if (!pass2.get(i)._passable)
                        {
                            break;
                        }
                        if (i == pass2.size() - 1)
                        {
                            arrOfFixes.add(pass2);
                        }
                    }
                }
            }
            iterator++;
        }
        return arrOfFixes;
    }
}
