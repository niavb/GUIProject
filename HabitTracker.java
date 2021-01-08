package project;

import java.awt.*;
import javax.swing.*;

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
		
		//Table
		String[] days = { "", "1", "2", "3", "4", "5", "6", "7"};
		String[][] habits = { 
				{ "Habit 1" , "", "", "", "", "", "", ""}, 
				{ "Habit 2" , "", "", "", "", "", "", ""},
				{ "Habit 3" , "", "", "", "", "", "", ""}
				};
		JTable habitsTable = new JTable(habits, days) {
			public boolean isCellEditable(int data, int colums) {
				return false;
			}
		};
		leftPanel.add(new JScrollPane(habitsTable));
		
		
		//right side
		//layout
		rightPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		//drop menu
		String[] chooseHabit = { "HABIT 1","HABIT 2", "HABIT 3"};
	    JComboBox<String> cb = new JComboBox<String>(chooseHabit);
	    gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
	    rightPanel.add(cb, gbc);
		
		JButton doneButton = new JButton("Done");
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		rightPanel.add(doneButton, gbc);
		
		JButton missedButton = new JButton("Missed");
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		rightPanel.add(missedButton, gbc);
		
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
		

		frame.setVisible(true);

	}
	
	

}
