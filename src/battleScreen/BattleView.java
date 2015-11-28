package battleScreen;

import java.util.Iterator;

import dataClasses.HeroInfo;
import dataClasses.UnitInfo;

public class BattleView {

	
	public void DrawBattleScreen(int terraintype)
	{
		
	}
	
	//player2 mo¿e byæ null
	public void PlaceUnits(HeroInfo player1, HeroInfo player2)
	{
		if(player2 == null)
		{
			Iterator<Integer> k = player1.getArmy().keySet().iterator();
			while(k.hasNext())
			{
				Integer key = k.next();
				UnitInfo u = player1.getArmy().get(key);
				System.out.println(key+"| L:"+u.unitSize+" type: "+u.unitType.name);
			}
		}
	}
}
