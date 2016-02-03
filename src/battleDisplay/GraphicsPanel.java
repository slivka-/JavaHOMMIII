package battleDisplay;

import dataClasses.UnitType;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
	private AnimateThread animateThread;
	private MoveThread moveThread;
	private ExecutorService ex;
	private boolean facingLeft = false;
	private boolean active = false;
	private BufferedImage currentFrame;
	private Point2D currentPosition;
	private boolean isAttack;
	private boolean isDead;
	private boolean animationFinished;


	public GraphicsPanel(UnitType unit, int x, int y, int unitSize)
	{

		this._unit = unit;
		this._unitSize = unitSize;
		currentPosition = new Point(x,y);
		this.isAttack = false;
		this.isDead = false;
		this.animationFinished = true;

		animateThread = new AnimateThread(_unit);
		ex = Executors.newFixedThreadPool(2);
		ex.execute(animateThread);


		currentFrame = animateThread.getFrame();



		this.setBounds(x-(currentFrame.getWidth()/2),y-currentFrame.getHeight(), currentFrame.getWidth(), currentFrame.getHeight()+12);
		//this.addMouseListener(new MouseListener() {

		startRefresh();
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

	public void movePanel(final ArrayList<Point> path)
	{
		moveThread = new MoveThread(path);
		ex.execute(moveThread);
		animationFinished = false;
	}

	public void attackUnit(final ArrayList<Point> path)
	{
		moveThread = new MoveThread(path);
		ex.execute(moveThread);
		isAttack = true;
		animationFinished = false;
	}

	public boolean isAnimationFinished()
	{
		return animationFinished;
	}

	public void unitDeath()
	{
		animateThread.changeAnimation(AnimationType.DEATH);
		JLayeredPane j = (JLayeredPane) this.getParent();
		j.setLayer(this, 1);
		isDead = true;
	}

	private void startRefresh()
	{
		Timer timer = new Timer(20, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				revalidate();
				repaint();
			}
		});
		timer.setRepeats(true);
		timer.setCoalesce(true);
		timer.start();
	}


	@Override
	protected void paintComponent(Graphics g)
	{

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		currentFrame = animateThread.getFrame();
		if(!facingLeft)
		{
			g2.drawImage(currentFrame, 0, 0, null);

		}
		else
		{
			BufferedImage image = currentFrame;
			AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
			tx.translate(-image.getWidth(null),0);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			image = op.filter(image, null);
			g2.drawImage(image, 0, 0, null);
		}
		this.setBounds((int)currentPosition.getX()-(currentFrame.getWidth()/2),(int)currentPosition.getY()-currentFrame.getHeight(), currentFrame.getWidth(), currentFrame.getHeight()+12);
		if(moveThread!=null)
		{
			if(moveThread.isRunning())
			{
				facingLeft = moveThread.isFacingLeft();
				int nextZIndex = (int) ((moveThread.getCurrentPosition().getY() - 100) / 43);
				currentPosition = moveThread.getCurrentPosition();
				JLayeredPane j = (JLayeredPane) this.getParent();
				j.setLayer(this, nextZIndex);
				animateThread.changeAnimation(AnimationType.MOVE);
			}
			else
			{
				moveThread = null;
				if(isAttack)
				{
					animateThread.changeAnimation(AnimationType.ATTACK);
					isAttack = false;
				}
				else
				{
					animateThread.changeAnimation(AnimationType.IDLE);
				}
				animationFinished = true;
			}
		}
		if(active)
		{
			g2.setColor(Color.yellow);
		}
		else
		{
			g2.setColor(Color.white);
		}

		if(!isDead)
		{
			g2.fillRect(10, currentFrame.getHeight() - 3, 30, 15);
			g2.setColor(Color.black);
			g2.drawString(Integer.toString(_unitSize), 20, currentFrame.getHeight() + 9);
		}
	}
}
