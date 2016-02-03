package battleDisplay;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by slivka on 21.01.2016.
 */
public class SpriteLoader
{
    private String _path = "assets\\units\\";
    private String _unitSpriteName;
    private String _unitTownName;
    private ArrayList<ArrayList<BufferedImage>> output;

    public SpriteLoader(String unitSpriteName,String unitTownName)
    {
        this._unitSpriteName = unitSpriteName;
        this._unitTownName = unitTownName;
        this.output = new ArrayList<ArrayList<BufferedImage>>();
        loadSpriteSheets();
    }

    private void loadSpriteSheets()
    {
        try
        {
            File folder = new File(_path+_unitTownName+"\\"+_unitSpriteName);
            for(File spritesheet : folder.listFiles())
            {
                if(!spritesheet.isDirectory())
                {
                    BufferedImage _sSheet = ImageIO.read(spritesheet);
                    String[] spriteInfo = spritesheet.getName().split("\\.")[0].split("\\;");
                    String[] widthAndHeight = spriteInfo[1].split("x");
                    System.out.println(widthAndHeight[0]+";"+widthAndHeight[1]);
                    int numOfFrames = Integer.parseInt(spriteInfo[2]);
                    getFrames(_sSheet,numOfFrames,Integer.parseInt(widthAndHeight[0]),Integer.parseInt(widthAndHeight[1]));
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public ArrayList<ArrayList<BufferedImage>> getAllFrames(){ return output;}

    private void getFrames(BufferedImage _spriteSheet, int numOfFrames, int _frameWidth, int _frameHeight)
    {
        ArrayList<BufferedImage> frames = new ArrayList<BufferedImage>();
        for (int i=0;i<numOfFrames;i++)
        {
            frames.add(_spriteSheet.getSubimage(i * _frameWidth, 0, _frameWidth, _frameHeight));
        }
        output.add(frames);
    }


}
