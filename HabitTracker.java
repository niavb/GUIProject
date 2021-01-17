package project;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.event.*;

public class HabitTracker {

	public static void main(String[] args) {

		JFrame frame = new JFrame("Habit Tracker");
		frame.setSize(500, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// main containers and Layouts
		JPanel mainPanel = new JPanel();
		frame.add(mainPanel);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

		JPanel leftPanel = new JPanel();
		mainPanel.add(leftPanel);
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

		JPanel rightPanel = new JPanel();
		mainPanel.add(rightPanel);
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

		// left side
		JLabel habitTrackerLabel = new JLabel("Here is your progress: ");
		leftPanel.add(habitTrackerLabel);

		// Table
		DefaultTableModel habitsTableModel = new DefaultTableModel();
		JTable habitsTable = new JTable(habitsTableModel) {
			public boolean isCellEditable(int data, int colums) {
				return false;
			}
		};
		
		habitsTable.setRowSelectionAllowed(false);
		//disable column moving
		habitsTable.getTableHeader().setReorderingAllowed(false);

		habitsTableModel.addColumn("Days");
		for (int i = 0; i < 7; i++) {
			habitsTableModel.addColumn(String.valueOf(i + 1));
		}

		habitsTableModel.insertRow(0, new Object[] { "Habits" });
		leftPanel.add(new JScrollPane(habitsTable));

		// right side
		// layout
		rightPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		// drop menu
		String[] chooseHabit = { "Choose habit." };
		JComboBox<String> chooseHabitDropMenu = new JComboBox<String>(chooseHabit);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		rightPanel.add(chooseHabitDropMenu, gbc);

		JButton doneButton = new JButton("Done");
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		rightPanel.add(doneButton, gbc);
		
		doneButton.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent e) {
				
				String chosenHabit = chooseHabitDropMenu.getSelectedItem().toString();
				for(int i=0; i<habitsTableModel.getRowCount(); i++) {
					if(habitsTable.getValueAt(i, 0)==chosenHabit) {
						for(int j=0; j<habitsTableModel.getColumnCount()-1; j++) {
							if(habitsTable.getValueAt(i, j+1)==null) {
								habitsTableModel.setValueAt("done", i, j+1);
								break;
							}
						}
						break;
					}
				}
				
			}
		});

		JButton missedButton = new JButton("Missed");
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		rightPanel.add(missedButton, gbc);
		
		missedButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String chosenHabit = chooseHabitDropMenu.getSelectedItem().toString();
				for(int i=0; i<habitsTableModel.getRowCount(); i++) {
					if(habitsTable.getValueAt(i, 0)==chosenHabit) {
						for(int j=0; j<habitsTableModel.getColumnCount()-1; j++) {
							if(habitsTable.getValueAt(i, j+1)==null) {
								habitsTableModel.setValueAt("missed", i, j+1);
								break;
							}
						}
						break;
					}
				}
				
			}
		});

		JTextField addHabit = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		rightPanel.add(addHabit, gbc);

		JButton addButton = new JButton("Add habit");
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		rightPanel.add(addButton, gbc);
		
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String habitToAdd = addHabit.getText();
				habitsTableModel.insertRow(habitsTableModel.getRowCount(), new Object[] {habitToAdd});
				chooseHabitDropMenu.addItem(habitToAdd);
				addHabit.setText(null);
				
			}
		});

		frame.setVisible(true);

	}

}
