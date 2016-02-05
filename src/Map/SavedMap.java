package Map;

import java.io.Serializable;

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
}
