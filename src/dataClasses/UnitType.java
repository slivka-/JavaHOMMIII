package dataClasses;

import java.util.Random;

/**
 * @author slivka
 * Klasa przechowywuj�ca informacje o jednostce
 */
public class UnitType {
	public String _name;
	public String _spriteName;
	public String _townName;
	public int initiative;
	public int _idleFrames;
	public int _attackFrames;
	public int _moveFrames;
	public int _deathFrames;
	public int _frameWidth;
	public int _frameHeight;

	
	public UnitType(String name, String spriteName,String townName,int frameWidth, int frameHeight, int idleFrames, int attackFrames, int moveFrames, int deathFrames)
	{
		this._name = name;
		this._spriteName = spriteName;
		this._townName = townName;
		Random rnd = new Random();
		this.initiative = rnd.nextInt(10);
		this._attackFrames = attackFrames;
		this._idleFrames = idleFrames;
		this._moveFrames = moveFrames;
		this._deathFrames = deathFrames;
		this._frameHeight = frameHeight;
		this._frameWidth = frameWidth;
	}
}
