package battleScreen;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import battleDisplay.PathFinding;
import dataClasses.*;
import Pathfinding.FindPath;

import javax.swing.*;


//TODO: AI,zrobić koniec bitwy, ekran końcowy, panel z info(ew obrona i poddane sie)
/**
 * @author slivka
 * Klasa g��wna, kontroluje przep�yw informacji
 */
public class BattleController {

	private BattleInfo model;
	private BattleView view;

	
	private int width = 808;
	private int height = 664;

	//IDENTYFIKACJA GRACZA
	private UnitCommander me;
	public boolean isMyTurn = true;
	public boolean isBattleOver = false;
		
	public BattleController(int terrainType, HeroInfo Player1, HeroInfo Player2, UnitCommander me)
	{
		this.model = new BattleInfo();
		this.model.setPlayer1(Player1);
		this.model.setPlayer2(Player2);
		this.model.setTerraintype(terrainType);
		this.me = me;
		this.view = new BattleView(width,height,this);
	}
	
	public BattleController(int terrainType, HeroInfo Player1, HashMap<Integer, UnitInfo> Enemy)
	{
		this.model = new BattleInfo();
		this.model.setPlayer1(Player1);
		this.model.setCPUarmy(Enemy);
		this.model.setTerraintype(terrainType);
		this.view = new BattleView(width,height,this);
	}

	private void debugSetMe()
	{
		this.me = model.activeUnit.commander;
	}

//============================GET/SET==================================\\	
	
	public void SetSpectators(HeroInfo[] heroInfo)
	{
		model.setSpectators(heroInfo);
	}

	public UnitCommander getMe() { return this.me; }

	public boolean isNextMoveMine()
	{

		if(model.nextActiveUnit.commander.equals(me))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
//============================CONTROL==================================\\	
	/**
	 * Metoda inicjalizuj�ca bitwe
	 */
	public void BattleInit()
	{
		model.GenerateBattlefield();
		model.PalceUnits();
		
		view.DrawBattleScreen(model.getTerraintype(),model.getBattlefieldInfo());
		newTurn();
	}
	
	/**
	 * Przesuwa jednostke na wybrane pole
	 * @param targetCell pole na kt�re jednostka ma si� przesun��
	 */
	public void MoveUnit(Point targetCell)
	{
			view.clearUnitRange();
			Point startingPoint = model.activeUnit.currentPos;
			FindPath f = new FindPath(model.BattlefieldInfo, startingPoint, targetCell);
			ArrayList<Point> path = f.generatePath();
			System.out.println("===================");
			for (Point p : path)
			{
				System.out.println("X: " + p.x + ", Y:" + p.y);
			}
			model.BattlefieldInfo[model.activeUnit.currentPos.x][model.activeUnit.currentPos.y].contains = CellEntity.EMPTY;
			model.BattlefieldInfo[model.activeUnit.currentPos.x][model.activeUnit.currentPos.y].unit = null;
			model.BattlefieldInfo[targetCell.x][targetCell.y].contains = CellEntity.UNIT;
			model.BattlefieldInfo[targetCell.x][targetCell.y].unit = model.activeUnit;
			model.activeUnit.currentPos = targetCell;
			view.moveUnit(model.BattlefieldInfo[targetCell.x][targetCell.y], path);
			this.waitForAnimation(model.activeUnit,null);

	}

	public void AttackUnit(Point attackTarget, Point attackPosition)
	{

		view.clearUnitRange();
		Point startingPoint = model.activeUnit.currentPos;

		FindPath f = new FindPath(model.BattlefieldInfo, startingPoint, attackPosition);
		ArrayList<Point> path = f.generatePath();
		model.BattlefieldInfo[model.activeUnit.currentPos.x][model.activeUnit.currentPos.y].contains = CellEntity.EMPTY;
		model.BattlefieldInfo[model.activeUnit.currentPos.x][model.activeUnit.currentPos.y].unit = null;

		model.BattlefieldInfo[attackPosition.x][attackPosition.y].contains = CellEntity.UNIT;
		model.BattlefieldInfo[attackPosition.x][attackPosition.y].unit = model.activeUnit;

		model.activeUnit.currentPos = attackPosition;
		view.attackUnit(model.BattlefieldInfo[attackPosition.x][attackPosition.y], path);
		this.waitForAnimation(model.activeUnit,attackTarget);

	}

	private void endAttackTurn(Point attackTarget)
	{
		UnitInfo defender = model.BattlefieldInfo[attackTarget.x][attackTarget.y].unit;
		UnitInfo defenderAfterAttack = calculateDamage(model.activeUnit,defender);
		if(defenderAfterAttack != null)
		{
			model.BattlefieldInfo[attackTarget.x][attackTarget.y].unit = defenderAfterAttack;
		}
		else
		{
			defender.unitDeath();
			model.BattlefieldInfo[attackTarget.x][attackTarget.y].contains = CellEntity.EMPTY;
			model.BattlefieldInfo[attackTarget.x][attackTarget.y].unit = null;
			model.deleteUnitFromQueue(defender);
		}
		newTurn();
	}

	private void waitForAnimation(UnitInfo animatedUnit,Point attackTarget)
	{
		Timer timer = new Timer(20, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(animatedUnit.isAnimationFinished())
				{
					Timer t = (Timer)e.getSource();
					t.stop();
					if(attackTarget != null)
					{
						endAttackTurn(attackTarget);
					}
					else
					{
						newTurn();
					}
				}
			}
		});
		timer.setRepeats(true);
		timer.setCoalesce(true);
		timer.start();
	}

	private UnitInfo calculateDamage(UnitInfo attacker, UnitInfo defender)
	{
		int attackerDamage = attacker.unitSize * attacker.unitType._attack;
		int defenderHealth;
		String battleText;

		if(defender.woundedUnitHealth == 0)
		{
			defenderHealth = (defender.unitSize) * defender.unitType._health;
		}
		else
		{
			defenderHealth = ((defender.unitSize-1) * defender.unitType._health)+defender.woundedUnitHealth;
		}

		if((int)Math.floor((double) attackerDamage / (double) defender.Defending) >= defenderHealth)
		{
			battleText = "Oddzia\u0142 "+attacker.unitType._name+" zaatakowa\u0142 oddzia\u0142 "+defender.unitType._name+", niszcz\u0105c go.";
			view.setBattleText(battleText);
			return null;

		}
		else
		{
			UnitInfo output = defender;
			int remainingHealth = defenderHealth - (int)Math.floor((double) attackerDamage / (double) defender.Defending);
			int remainingUnits = (int)((double)remainingHealth/(double)defender.unitType._health);
			int woundedUnitHealth = remainingHealth % defender.unitType._health;
			output.updateUnitSize(remainingUnits);
			output.woundedUnitHealth = woundedUnitHealth;
			battleText = "<HTML>Oddzia\u0142 "+attacker.unitType._name+" zaatakowa\u0142 oddzia\u0142 "+defender.unitType._name+" zadaj\u0105c "+attackerDamage+" obra\u017Ce\u0144.<br/>Przy \u017Cyciu pozosta\u0142o "+remainingUnits+" jednostek.</HTML>";
			view.setBattleText(battleText);
			return output;
		}
	}

	private void newTurn()
	{
		if(!model.checkForEnd())
		{
			model.SetNextActiveUnit();
			ArrayList<Point> moveRange = model.getMoveRange();
			debugSetMe();
			if (isMyTurn)
			{
				view.drawUnitRange(moveRange);
			}
		}
		else
		{
			isBattleOver = true;
		}
	}

	public BattlefieldCell[][] getBattlefieldInfo()
	{
		return model.BattlefieldInfo;
	}

	public BattlefieldCell getBattlefieldCell(Point p)
	{
		try
		{
			return model.BattlefieldInfo[p.x][p.y];
		}
		catch (NullPointerException ex)
		{
			return null;
		}
	}

	public Point getActiveUnitLocation()
	{
		return model.activeUnit.currentPos;
	}

	public void battleFlee()
	{
			System.out.println("DZIAŁA");
	}

	public void unitDefend()
	{
		view.clearUnitRange();
		model.activeUnit.setDefending();

		view.setBattleText("Oddzia\u0142 "+model.activeUnit.unitType._name+" broni si\u0119. (Otrzymywane obra\u017Cenia -50%)");
		newTurn();
	}

	public BattleResult EndBattle()
	{
		if(isBattleOver)
		{
			view.endBattle();
			BattleResult r = new BattleResult();
			if(model.getPlayer1().getArmy().isEmpty())
			{
				r.looser = model.getPlayer1();
				r.looser.currentPosition = r.looser.townPosition;
				if(model.getPlayer2()!= null)
				{
					r.winner = model.getPlayer2();
					r.vsAI = false;
					return r;
				}
				else
				{
					r.winner = null;
					r.vsAI = true;
					return r;
				}
			}
			else if(model.getPlayer2()!=null)
			{
				r.looser = model.getPlayer2();
				r.winner = model.getPlayer1();
				r.vsAI = false;
				return r;
			}
			else
			{
				r.looser = null;
				r.winner = model.getPlayer1();
				r.vsAI = true;
				return r;
			}
		}
		return null;
	}
}
