package Pathfinding;

import Map.MapObjects.TerrainPassability;
import Map.Tile;

import java.awt.*;

public class GameMap
{
    private Point startLocation;
    private Point endLocation;

    private GameCell[][] map;

    public GameMap(Tile[][] battlefieldInfo, Point startLocation, Point endLocation)
    {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        map = new GameCell[battlefieldInfo.length][battlefieldInfo[0].length];
        loadMap(battlefieldInfo);
    }

    private void loadMap(Tile[][] battlefieldInfo)
    {
        for(int i=0;i< battlefieldInfo.length ;i++)
        {
            for (int j = 0; j < battlefieldInfo[0].length; j++)
            {
                if(battlefieldInfo[i][j].getOccupied())
                {
                    map[i][j] = new GameCell(new Point(i,j),false,startLocation,endLocation);
                }
                else
                {
                    map[i][j] = new GameCell(new Point(i,j),true,startLocation,endLocation);
                }
            }
        }

        for (int i=0;i<battlefieldInfo.length;i++)
        {
            for (int j=0;j<battlefieldInfo[0].length;j++)
            {
                int thisX = i;
                int thisY = j;

                if(thisY-1>=0)
                {
                    map[i][j].N = map[i][j-1];
                }
                if(thisY+1<=battlefieldInfo[0].length-1)
                {
                    map[i][j].S = map[i][j+1];
                }
                if(thisX+1<=battlefieldInfo.length-1)
                {
                    map[i][j].E = map[i+1][j];
                }
                if(thisX-1>=0)
                {
                    map[i][j].W = map[i-1][j];
                }
                map[i][j].updateNeighbours();
            }
        }
    }

    public GameCell getCell(Point location)
    {
        return map[location.x][location.y];
    }
}
