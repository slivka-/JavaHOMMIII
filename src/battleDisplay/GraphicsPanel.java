package battleDisplay;

import dataClasses.UnitType;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.Timer;
import javax.swing.*;

/**
 * @author slivka
 * Klasa rysuj�ca jednostki
 */
public class GraphicsPanel extends JPanel {

	private static final long serialVersionUID = 3688560623253859696L;
	private UnitType _unit;
	private int _unitSize;
	private int _x;
	private int _y;
	private List<BufferedImage> _idleFrames = new ArrayList<BufferedImage>();
	private Animate animate;
	private boolean facingLeft = false;
	private boolean active = false;

	public GraphicsPanel(UnitType unit, int x, int y, int unitSize)
	{

		this._x = x;
		this._y = y;
		this._unit = unit;
		this._unitSize = unitSize;

		SpriteLoader sl = new SpriteLoader(_unit._spriteName,_unit._townName,_unit._frameWidth,_unit._frameHeight);
		_idleFrames = sl.getFrames(_unit._idleFrames,0);
		animate = new Animate(_idleFrames);



		this.setBounds(_x-(_unit._frameWidth/2),_y-_unit._frameHeight, _unit._frameWidth, _unit._frameHeight+12);
		//this.addMouseListener(new MouseListener() {

		start();
		animate.start();
	}

	public void updateUnitSize(int newUnitSize)
	{
		_unitSize = newUnitSize;
	}

	public void flipFacing()
	{
		if(facingLeft)
		{
			facingLeft = false;
		}
		else
		{
			facingLeft = true;
		}
	}

	public void setAsActive()
	{
		active = true;
	}

	public void setAsNotActive()
	{
		active = false;
	}


	/**
	 * Metoda przesuwaj�ca jednostk� na now� pozycje
	 * @param path sciezka jednostki;
	 */

	private int pathPointCount = 0;
	private int animationCount = 0;

	public void movePanel(final ArrayList<Point> path)
	{
		pathPointCount = 0;
		animationCount = 0;
		final int endOfPath = path.size();
		Timer timer = new Timer(25, new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(pathPointCount+1 != endOfPath)
				{
					if (animationCount < 9)
					{
						float nextX = calculate1DProgress(path.get(pathPointCount).x, path.get(pathPointCount + 1).x, animationCount);
						float nextY = calculate1DProgress(path.get(pathPointCount).y, path.get(pathPointCount + 1).y, animationCount);
						nextMoveFrame(nextX, nextY);
						animationCount++;
					} else
					{
						float nextX = calculate1DProgress(path.get(pathPointCount).x, path.get(pathPointCount + 1).x, animationCount);
						float nextY = calculate1DProgress(path.get(pathPointCount).y, path.get(pathPointCount + 1).y, animationCount);
						nextMoveFrame(nextX, nextY);
						animationCount = 0;
						pathPointCount++;
					}
				}
				else
				{
					System.out.println("STOP");
					((Timer) e.getSource()).stop();
				}
			}
		});

		timer.setRepeats(true);
		timer.setCoalesce(true);
		timer.start();
	}

	private float calculate1DProgress(int from, int to, int frame)
	{
		int distance = to - from;
		float piece = distance/10;
		float next = from + (piece * frame);
		return next;
	}

	private void nextMoveFrame(float nextX, float nextY)
	{
		int nextZIndex = (int)((nextY-100)/43);
		this.setBounds((int)nextX-(_unit._frameWidth/2),(int)nextY-_unit._frameHeight, _unit._frameWidth, _unit._frameHeight+12);
		JLayeredPane j = (JLayeredPane)this.getParent();
		j.setLayer(this,nextZIndex);
		revalidate();
		repaint();
	}

	private void start()
	{
		Timer timer = new Timer(90, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				animate.update();
				revalidate();
				repaint();
			}
		});
		timer.setRepeats(true);
		timer.setCoalesce(true);
		timer.setInitialDelay(ThreadLocalRandom.current().nextInt(100,1000));
		timer.start();
	}


	@Override
	protected void paintComponent(Graphics g)
	{

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		if(!facingLeft)
		{
			g2.drawImage(animate.getSprite(), 0, 0, null);
		}
		else
		{
			BufferedImage image = animate.getSprite();
			AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
			tx.translate(-image.getWidth(null),0);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			image = op.filter(image, null);
			g2.drawImage(image, 0, 0, null);
		}
		//g2.drawRect(10,85,30,15);
		if(active)
		{
			g2.setColor(Color.yellow);
		}
		else
		{
			g2.setColor(Color.white);
		}

		g2.fillRect(10,_unit._frameHeight-3,30,15);
		g2.setColor(Color.black);
		g2.drawString(Integer.toString(_unitSize),20,_unit._frameHeight+9);
	}
}
