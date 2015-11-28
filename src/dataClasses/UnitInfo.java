package dataClasses;

//informacje o oddziale
public class UnitInfo {
	public int unitSize;
	public UnitType unitType;
	
	public UnitInfo(int size, UnitType type)
	{
		this.unitSize = size;
		this.unitType = type;
	}
}
