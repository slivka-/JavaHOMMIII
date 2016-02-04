package Pathfinding;

import dataClasses.BattlefieldCell;
import dataClasses.CellEntity;

import java.awt.*;


/**
 * Created by slivka on 23.01.2016.
 */
public class BattlefieldMap
{
    private Point startLocation;
    private Point endLocation;

    private MapCell[][] map = new MapCell[14][10];

    public BattlefieldMap(BattlefieldCell[][] battlefieldInfo, Point startLocation, Point endLocation)
    {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        loadMap(battlefieldInfo);
    }

    private void loadMap(BattlefieldCell[][] battlefieldInfo)
    {
        for(int i=0;i<14;i++)
        {
            for (int j = 0; j < 10; j++)
            {
                if(battlefieldInfo[i][j].contains == CellEntity.OBSTACLE || battlefieldInfo[i][j].contains == CellEntity.UNIT)
                {
                    map[i][j] = new MapCell(new Point(i,j),false,startLocation,endLocation);
                }
                else
                {
                    map[i][j] = new MapCell(new Point(i,j),true,startLocation,endLocation);
                }
            }
        }

        for (int i=0;i<14;i++)
        {
            for (int j=0;j<10;j++)
            {
                int thisX = i;
                int thisY = j;

                if(thisY-1>=0)
                {
                    map[i][j].N = map[i][j-1];
                }
                if(thisY+1<=9)
                {
                    map[i][j].S = map[i][j+1];
                }
                if(thisX+1<=13)
                {
                    map[i][j].E = map[i+1][j];
                }
                if(thisX-1>=0)
                {
                    map[i][j].W = map[i-1][j];
                }
                /*
                if(thisY-1>=0 && thisX+1<=13)
                {
                    map[i][j].NE = map[i+1][j-1];
                }
                if(thisY-1>=0 && thisX-1>=0)
                {
                    map[i][j].NW = map[i-1][j-1];
                }
                if(thisY+1<=9 && thisX+1<=13)
                {
                    map[i][j].SE = map[i+1][j+1];
                }
                if(thisY+1<=9 && thisX-1>=0)
                {
                    map[i][j].SW = map[i-1][j+1];
                }
                */
                map[i][j].updateNeighbours();
            }
        }
    }

    public MapCell getCell(Point location)
    {
        return map[location.x][location.y];
    }
}
