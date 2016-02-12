package mapLogic;

import Map.MapObjects.Towns.Loch;
import Map.MapObjects.Towns.Town;
import Map.MapObjects.Towns.Zamek;
import Map.ReadyUnitTypes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

/**
 * Created by slivka on 12.02.2016.
 */
public class BuyUnitsDialog extends JFrame
{
    private MapGameController contr;
    private JButton unit1;
    private JButton unit2;
    private JButton unit3;
    private JButton unit4;
    private JLabel resources;

    private HashMap<String,Integer> res;

    public BuyUnitsDialog(MapGameController parent)
    {

        this.contr = parent;
        this.res = contr.playersList.get(contr.currentPlayerID).resources;
        this.setVisible(true);
        this.setSize(500, 500);
        this.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent windowEvent)
            {
                contr.playersList.get(contr.currentPlayerID).resources = res;
                parent.endOfTurn();
            }
        });
        this.setLayout(null);
        resources = new JLabel();
        resources.setVisible(true);
        resources.setBounds(20,20,450,50);
        resources.setText(res.toString());
        this.add(resources);

        unit1 = new JButton();
        unit1.setVisible(true);
        unit1.setBounds(20, 100, 150, 50);
        this.add(unit1);

        unit2 = new JButton();
        unit2.setVisible(true);
        unit2.setBounds(20, 200, 150, 50);
        this.add(unit2);

        unit3 = new JButton();
        unit3.setVisible(true);
        unit3.setBounds(20, 300, 150, 50);
        this.add(unit3);

        unit4 = new JButton();
        unit4.setVisible(true);
        unit4.setBounds(20,400,150,50);
        this.add(unit4);


        Town userTown = contr.playersList.get(contr.currentPlayerID).homeTown;
        if(userTown.getClass().equals(Loch.class))
        {
            setToDungeon();
        }
        else if(userTown.getClass().equals(Zamek.class))
        {
            setToCastle();
        }
        else
        {
            setToFortress();
        }
    }

    private void setToDungeon()
    {
        unit1.setText("Troglodyta");
        unit1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int g = res.get("Zloto");
                if(g>100)
                {
                    contr.playersList.get(contr.currentPlayerID).incUnit(ReadyUnitTypes.troglodyte);
                    res.put("Zloto",g-100);
                    resources.setText(res.toString());
                }
            }
        });
        unit2.setText("Piekielny troglodyta");
        unit2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int g = res.get("Zloto");
                int d = res.get("Drewno");
                if(g>300 && d> 10)
                {
                    contr.playersList.get(contr.currentPlayerID).incUnit(ReadyUnitTypes.infernaltroglodyte);
                    res.put("Zloto", g - 300);
                    res.put("Drewno",d-10);
                    resources.setText(res.toString());
                }
            }
        });
        unit3.setText("Minotaur");
        unit3.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int g = res.get("Zloto");
                int r = res.get("Ruda");
                if(g>500 && r>10)
                {
                    contr.playersList.get(contr.currentPlayerID).incUnit(ReadyUnitTypes.minotaur);
                    res.put("Zloto",g-500);
                    res.put("Ruda",r-10);
                    resources.setText(res.toString());
                }
            }
        });
        unit4.setText("Uberminotaur");
        unit4.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int g = res.get("Zloto");
                int s = res.get("Siarka");
                if(g>1000 && s>10)
                {
                    contr.playersList.get(contr.currentPlayerID).incUnit(ReadyUnitTypes.troglodyte);
                    res.put("Zloto",g-100);
                    res.put("Siarka",s-10);
                    resources.setText(res.toString());
                }
            }
        });
    }

    private void setToFortress()
    {

        unit1.setText("Ogr");
        unit1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int g = res.get("Zloto");
                if(g>100)
                {
                    contr.playersList.get(contr.currentPlayerID).incUnit(ReadyUnitTypes.ogre);
                    res.put("Zloto",g-100);
                    resources.setText(res.toString());
                }
            }
        });
        unit2.setText("Wilk");
        unit2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int g = res.get("Zloto");
                int d = res.get("Drewno");
                if(g>300 && d> 10)
                {
                    contr.playersList.get(contr.currentPlayerID).incUnit(ReadyUnitTypes.wolf);
                    res.put("Zloto",g-300);
                    res.put("Drewno",d-10);
                    resources.setText(res.toString());
                }
            }
        });
        unit3.setText("Hobgoblin");
        unit3.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int g = res.get("Zloto");
                int r = res.get("Ruda");
                if(g>500 && r>10)
                {
                    contr.playersList.get(contr.currentPlayerID).incUnit(ReadyUnitTypes.hobgoblin);
                    res.put("Zloto",g-500);
                    res.put("Ruda",r-10);
                    resources.setText(res.toString());
                }
            }
        });
        unit4.setText("Behemot");
        unit4.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int g = res.get("Zloto");
                int s = res.get("Krysztal");
                if(g>1000 && s>10)
                {
                    contr.playersList.get(contr.currentPlayerID).incUnit(ReadyUnitTypes.behemoth);
                    res.put("Zloto",g-100);
                    res.put("Krysztal",s-10);
                    resources.setText(res.toString());
                }
            }
        });
    }



    private void setToCastle()
    {
        unit1.setText("Pikinier");
        unit1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int g = res.get("Zloto");
                if(g>100)
                {
                    contr.playersList.get(contr.currentPlayerID).incUnit(ReadyUnitTypes.pikeman);
                    res.put("Zloto",g-100);
                    resources.setText(res.toString());
                }
            }
        });
        unit2.setText("Halabardier");
        unit2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int g = res.get("Zloto");
                int d = res.get("Drewno");
                if(g>300 && d> 10)
                {
                    contr.playersList.get(contr.currentPlayerID).incUnit(ReadyUnitTypes.halberdier);
                    res.put("Zloto",g-300);
                    res.put("Drewno",d-10);
                    resources.setText(res.toString());
                }
            }
        });
        unit3.setText("Miecznik");
        unit3.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int g = res.get("Zloto");
                int r = res.get("Ruda");
                if(g>500 && r>10)
                {
                    contr.playersList.get(contr.currentPlayerID).incUnit(ReadyUnitTypes.swordsman);
                    res.put("Zloto",g-500);
                    res.put("Ruda",r-10);
                    resources.setText(res.toString());
                }
            }
        });
        unit4.setText("Rycerz");
        unit4.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int g = res.get("Zloto");
                int s = res.get("Krysztal");
                if(g>1000 && s>10)
                {
                    contr.playersList.get(contr.currentPlayerID).incUnit(ReadyUnitTypes.crusader);
                    res.put("Zloto",g-100);
                    res.put("Klejnot",s-10);
                    resources.setText(res.toString());
                }
            }
        });
    }

}
