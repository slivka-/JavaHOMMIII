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
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.Timer;

import javax.imageio.ImageIO;
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
	private List<AnimationFrame> _idleFrames = new ArrayList<AnimationFrame>();
	private Animate animate;
	private boolean facingLeft = false;
	private boolean active = false;

	public GraphicsPanel(UnitType unit, int x, int y, int unitSize)
	{

		this._x = x;
		this._y = y;
		this._unit = unit;
		this._unitSize = unitSize;

		SpriteLoader sl = new SpriteLoader(_unit._spriteName,_unit._frameWidth,_unit._frameHeight);
		_idleFrames = sl.getFrames(_unit._idleFrames,0);
		animate = new Animate(_idleFrames);



		this.setBounds(_x-(_unit._frameWidth/2),_y-_unit._frameHeight, _unit._frameWidth, _unit._frameHeight+12);
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("OK");
			}
		});
		start();
		animate.start();
	}
	/**
	 * Metoda odwracaj�ca grafike jednostki
	 */
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
	 * @param p nowa pozycja jednostki
	 */
	public void movePanel(Point p)
	{
		//System.out.println((p.y-100)/43);
		int nextZIndex = ((p.y-100)/43);

		this.setBounds(p.x-(_unit._frameWidth/2),p.y-_unit._frameHeight, _unit._frameWidth, _unit._frameHeight+12);
		JLayeredPane j = (JLayeredPane)this.getParent();
		j.setLayer(this,nextZIndex);
		System.out.println(this.getParent().getComponentZOrder(this));
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
