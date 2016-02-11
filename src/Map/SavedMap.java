package Map;

import Map.MapObjects.MapObject;
import Map.MapObjects.Towns.Forteca;
import Map.MapObjects.Towns.Loch;
import Map.MapObjects.Towns.Town;
import Map.MapObjects.Towns.Zamek;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by slivka on 05.02.2016.
 */
public class SavedMap implements Serializable
{
    private MapSize _mapSize;
    private Tile[][] _cells;

    public SavedMap(MapSize size, Tile[][] cells)
    {
        this._mapSize = size;
        this._cells = cells;
    }

    public MapSize get_mapSize()
    {
        return _mapSize;
    }

    public Tile[][] get_cells()
    {
        return _cells;
    }

    public ArrayList<MapObject> getTownsOnMap()
    {
        ArrayList<MapObject> output = new ArrayList<>();
        for(Tile[] tRow: _cells)
        {
            for(Tile t : tRow)
            {
                if(t.getMapObject()!=null)
                {
                    if(t.getMapObject().getClass().equals(Loch.class) || t.getMapObject().getClass().equals(Zamek.class) || t.getMapObject().getClass().equals(Forteca.class))
                    {
                        boolean isPresent = false;
                        for (MapObject town : output)
                        {
                            if (t.getMapObject().equals(town))
                            {
                                isPresent = true;
                            }
                        }
                        if (!isPresent)
                        {
                            output.add(t.getMapObject());
                        }
                    }
                }
            }
        }
        return output;
    }
}
