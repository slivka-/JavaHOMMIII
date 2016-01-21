package battleDisplay;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by slivka on 21.01.2016.
 */
public class SpriteLoader
{
    private BufferedImage _spriteSheet;
    private int _frameWidth;
    private int _frameHeight;
    private String _path = "assets\\units\\";
    private String _unitSpriteName;

    public SpriteLoader(String unitSpriteName, int frameWidth, int frameHeight)
    {

        this._frameWidth = frameWidth;
        this._frameHeight = frameHeight;
        this._unitSpriteName = unitSpriteName;
        loadSpriteSheet();
    }

    private void loadSpriteSheet()
    {
        try
        {
            //System.out.println(_path+_unitSpriteName+".png");
            //System.out.println(_path+_unitSpriteName+".png");
            this._spriteSheet = ImageIO.read(new File(_path+_unitSpriteName+".png"));
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public List<AnimationFrame> getFrames(int numOfFrames,int numOfRow)
    {
        List<AnimationFrame> frames = new ArrayList<AnimationFrame>();
        for (int i=0;i<numOfFrames;i++)
        {
            AnimationFrame frame = new AnimationFrame(_spriteSheet.getSubimage(i * _frameWidth, numOfRow * _frameHeight, _frameWidth, _frameHeight),1);
            frames.add(frame);

        }
        return frames;
    }


}
