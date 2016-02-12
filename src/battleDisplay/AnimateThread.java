package battleDisplay;

import dataClasses.UnitType;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by slivka on 21.01.2016.
 */
public class AnimateThread implements Runnable
{
    private int currentFrame;
    private boolean stopped;
    private ArrayList<ArrayList<BufferedImage>> frames;
    private SpriteLoader loader;
    private int currentAnimation; //0- attack, 1- death, 2- idle, 3- move;
    private int lastAnimation;
    private int idleWait;

    public AnimateThread(UnitType type) {

        this.stopped = false;
        this.currentFrame = 0;
        this.currentAnimation = 2;
        this.lastAnimation = 2;
        this.idleWait = 0;
        this.loader = new SpriteLoader(type._spriteName,type._townName);
        this.frames = this.loader.getAllFrames();

    }

        public void start() {
            if (!stopped) {
                return;
            }

            if (frames.get(currentAnimation).size() == 0) {
                return;
            }

            stopped = false;
        }

        private void stop() {
            if (frames.get(currentAnimation).size() == 0) {
                return;
            }

            stopped = true;
        }

        public void restart() {
            if (frames.get(currentAnimation).size() == 0) {
                return;
            }

            stopped = false;
            currentFrame = 0;
        }

        public void reset() {
            this.stopped = true;
            this.currentFrame = 0;
        }

        public BufferedImage getFrame()
        {
            return frames.get(currentAnimation).get(currentFrame);
        }

        public void changeAnimation(AnimationType type)
        {
            switch (type)
            {
                case ATTACK:
                    currentAnimation = 0;
                    break;

                case DEATH:
                    currentAnimation = 1;
                    break;

                case IDLE:
                    currentAnimation = 2;
                    break;

                case MOVE:
                    currentAnimation = 3;
                    break;
            }
            if(lastAnimation != currentAnimation)
            {
                lastAnimation = currentAnimation;
                this.restart();
            }
        }

     @Override
    public void run()
    {

        while (!Thread.currentThread().isInterrupted())
        {
            try
            {
                if (!stopped)
                {
                    currentFrame += 1;

                    if (currentFrame > frames.get(currentAnimation).size() - 1)
                    {
                        currentFrame = 0;
                        if(currentAnimation == 0)
                        {
                            currentAnimation = 2;
                            this.restart();
                        }
                        else if(currentAnimation == 1)
                        {
                            this.stop();
                            this.currentFrame = frames.get(currentAnimation).size()-1;
                        }
                        else if(currentAnimation == 2)
                        {
                            Random rnd = new Random();
                            idleWait = 20+rnd.nextInt(25);
                            for(int i=0;i<idleWait;i++)
                            {
                                if(currentAnimation!=2)
                                {
                                    break;
                                }
                                else
                                {
                                    Thread.sleep(100);
                                }
                            }
                        }


                    } else if (currentFrame < 0)
                    {
                        currentFrame = frames.get(currentAnimation).size() - 1;
                    }
                    Thread.sleep(100);
                    //System.out.println(currentFrame);
                }
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

    }



}
