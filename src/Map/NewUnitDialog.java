package Map;

import Map.MapObjects.Army;
import dataClasses.UnitCommander;
import dataClasses.UnitInfo;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;
import java.util.HashMap;

/**
 * Created by slivka on 10.02.2016.
 */
public class NewUnitDialog
{
    private JComponent[] components;
    private JComboBox unitsSelectionBox0;
    private JComboBox unitsSelectionBox1;
    private JComboBox unitsSelectionBox2;
    private JComboBox unitsSelectionBox3;
    private JComboBox unitsSelectionBox4;

    private JFormattedTextField unitNumberFiled0;
    private JFormattedTextField unitNumberFiled1;
    private JFormattedTextField unitNumberFiled2;
    private JFormattedTextField unitNumberFiled3;
    private JFormattedTextField unitNumberFiled4;




    public NewUnitDialog() {
        NumberFormat intFormat = NumberFormat.getIntegerInstance();
        NumberFormatter numFormater = new NumberFormatter(intFormat);
        numFormater.setValueClass(Integer.class);
        numFormater.setAllowsInvalid(false);

        unitsSelectionBox0 = new JComboBox(ReadyUnitTypes.getNames());
        unitsSelectionBox1 = new JComboBox(ReadyUnitTypes.getNames());
        unitsSelectionBox2 = new JComboBox(ReadyUnitTypes.getNames());
        unitsSelectionBox3 = new JComboBox(ReadyUnitTypes.getNames());
        unitsSelectionBox4 = new JComboBox(ReadyUnitTypes.getNames());

        unitNumberFiled0 = new JFormattedTextField(numFormater);
        unitNumberFiled1 = new JFormattedTextField(numFormater);
        unitNumberFiled2 = new JFormattedTextField(numFormater);
        unitNumberFiled3 = new JFormattedTextField(numFormater);
        unitNumberFiled4 = new JFormattedTextField(numFormater);

        components = new JComponent[] {
                new JLabel("Nowa armia"),
                unitsSelectionBox0,
                new JLabel("liczba jednostek"),
                unitNumberFiled0,
                unitsSelectionBox1,
                new JLabel("liczba jednostek"),
                unitNumberFiled1,
                unitsSelectionBox2,
                new JLabel("liczba jednostek"),
                unitNumberFiled2,
                unitsSelectionBox3,
                new JLabel("liczba jednostek"),
                unitNumberFiled3,
                unitsSelectionBox4,
                new JLabel("liczba jednostek"),
                unitNumberFiled4,

        };
    }

    public Army showDialog() {
        int choice = JOptionPane.showConfirmDialog(null, components, "Nowa armia", JOptionPane.YES_NO_OPTION);
        HashMap<Integer, UnitInfo> army = new HashMap<>();
        String name = (String)unitsSelectionBox0.getSelectedItem();
        if(!name.equals("empty"))
        {
            army.put(1,new UnitInfo(Integer.parseInt(unitNumberFiled0.getText()),ReadyUnitTypes.getUnitTypeByName(name), UnitCommander.FREEARMY));
        }
        name = (String)unitsSelectionBox1.getSelectedItem();
        if(!name.equals("empty"))
        {
            army.put(2,new UnitInfo(Integer.parseInt(unitNumberFiled1.getText()),ReadyUnitTypes.getUnitTypeByName(name), UnitCommander.FREEARMY));
        }
        name = (String)unitsSelectionBox2.getSelectedItem();
        if(!name.equals("empty"))
        {
            army.put(3,new UnitInfo(Integer.parseInt(unitNumberFiled2.getText()),ReadyUnitTypes.getUnitTypeByName(name), UnitCommander.FREEARMY));
        }
        name = (String)unitsSelectionBox3.getSelectedItem();
        if(!name.equals("empty"))
        {
            army.put(4,new UnitInfo(Integer.parseInt(unitNumberFiled3.getText()),ReadyUnitTypes.getUnitTypeByName(name), UnitCommander.FREEARMY));
        }
        name = (String)unitsSelectionBox4.getSelectedItem();
        if(!name.equals("empty"))
        {
            army.put(5,new UnitInfo(Integer.parseInt(unitNumberFiled4.getText()),ReadyUnitTypes.getUnitTypeByName(name), UnitCommander.FREEARMY));
        }
        Army output = new Army(army);
        if (choice == JOptionPane.YES_OPTION) {
            return output;
        }
        else
        {
            return null;
        }
    }
}
