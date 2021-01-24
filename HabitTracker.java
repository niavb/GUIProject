package project;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class HabitTracker extends JFrame {

	// store table in file
	public static void storeTableModel(DefaultTableModel model) {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream("savedProgress"));
			oos.writeObject(model.getDataVector());
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// store the ArrayList with habits from drop menu (JComboBox) in file
	public static void storeDropMenuHabits(ArrayList<String> al) {
		try {
			FileWriter fw = new FileWriter("savedDropMenuHabits");
			for (String habit : al)
				fw.write(habit + System.lineSeparator());
			fw.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	// method for the action of buttons DONE and MISSED
	public static void checkingButtionsAction(String check, JComboBox<String> cb, DefaultTableModel model) {
		String chosenHabit = cb.getSelectedItem().toString();
		// going through every row in the first column of the table
		for (int i = 0; i < model.getRowCount(); i++) {
			if (model.getValueAt(i, 0).equals(chosenHabit)) {
				// going through every cell in the selected row
				for (int j = 0; j < model.getColumnCount() - 1; j++) {
					// finding the first empty cell of the row
					if (model.getValueAt(i, j + 1) == null) {
						model.setValueAt(check, i, j + 1);
						break;
					}
				}
				break;
			}
		}
		storeTableModel(model);
	}

	public static void main(String[] args) {

		JFrame frame = new JFrame("Habit Tracker");
		frame.setSize(1550, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// main containers and Layouts
		JPanel mainPanel = new JPanel();
		frame.add(mainPanel);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

		// using as border between tablePanel and the left frame
		JPanel leftBorderPanel = new JPanel();
		mainPanel.add(leftBorderPanel);
		leftBorderPanel.setBackground(Color.LIGHT_GRAY);
		leftBorderPanel.setPreferredSize(new Dimension(10, 300));

		JPanel tablePanel = new JPanel();
		mainPanel.add(tablePanel);
		tablePanel.setPreferredSize(new Dimension(1300, 300));
		tablePanel.setBackground(Color.LIGHT_GRAY);
		tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));

		// using as border between tablePanel and rightPanel
		JPanel rightBorderPanel = new JPanel();
		mainPanel.add(rightBorderPanel);
		rightBorderPanel.setBackground(Color.LIGHT_GRAY);
		rightBorderPanel.setPreferredSize(new Dimension(10, 300));

		JPanel rightPanel = new JPanel();
		mainPanel.add(rightPanel);
		rightPanel.setBackground(Color.WHITE);
		rightPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

		// left side

		// using as upper border of table
		JLabel habitTrackerLabel = new JLabel(" ");
		tablePanel.add(habitTrackerLabel);

		// Table
		
		// using DefaultTableModel for its methods like addRow(), addColumn()...
		DefaultTableModel habitsTableModel = new DefaultTableModel();
		JTable habitsTable = new JTable(habitsTableModel) {

			// make the table cells not editable from user (only from buttons in rightPanel)
			public boolean isCellEditable(int data, int colums) {
				return false;
			}

			// set color of missed/done cells
			public Component prepareRenderer(TableCellRenderer r, int row, int column) {
				Component cell = super.prepareRenderer(r, row, column);
				Object value = getModel().getValueAt(row, column);
				if (value != null) {
					if (value.equals("missed")) {
						cell.setBackground(Color.red);
					} else if (value.equals("done")) {
						cell.setBackground(Color.green);
					} else {
						cell.setBackground(Color.white);
					}
				} else {
					cell.setBackground(Color.white);
				}
				return cell;
			}

		};

		// disables selecting whole row when certain cell is clicked on
		habitsTable.setRowSelectionAllowed(false);
		// disables column moving
		habitsTable.getTableHeader().setReorderingAllowed(false);

		habitsTableModel.addColumn("Days");
		for (int i = 0; i < 31; i++) {
			habitsTableModel.addColumn(String.valueOf(i + 1));
		}

		habitsTableModel.insertRow(0, new Object[] { "Habits" });
		tablePanel.add(new JScrollPane(habitsTable));

		// get stored table from file
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream("savedProgress"));
			Vector data = (Vector) ois.readObject();
			Vector<Object> cols = new Vector<>();
			cols.add("Days");
			for (int i = 1; i < habitsTableModel.getColumnCount(); i++) {
				cols.add(i);
			}
			habitsTableModel.setDataVector(data, cols);
		} catch (Exception e) {
		}

		// using as down border of table
		JLabel label = new JLabel(" ");
		tablePanel.add(label);

		// right side

		// for layout setting
		rightPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);

		JLabel checkLabel = new JLabel("Choose habit to check: ");
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		rightPanel.add(checkLabel, gbc);

		// drop-down menu
		ArrayList<String> dropDownMenuHabits = new ArrayList<String>();
		// get stored habits from file "savedDropMenuHabits" to the ArrayList
		try {
			BufferedReader reader = new BufferedReader(new FileReader("savedDropMenuHabits"));
			String line;
			while ((line = reader.readLine()) != null) {
				dropDownMenuHabits.add(line);
			}
			reader.close();
		} catch (Exception e) {
		}
		JComboBox<String> dropDownMenu = new JComboBox<String>();
		// add ArrayList to drop menu (combo box)
		for (int i = 0; i < dropDownMenuHabits.size(); i++) {
			dropDownMenu.addItem(dropDownMenuHabits.get(i));
		}
		// set layout
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		rightPanel.add(dropDownMenu, gbc);

		JButton doneButton = new JButton("Done");
		gbc.gridx = 2;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		rightPanel.add(doneButton, gbc);

		doneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkingButtionsAction("done", dropDownMenu, habitsTableModel);
			}
		});

		JButton missedButton = new JButton("Missed");
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		rightPanel.add(missedButton, gbc);

		missedButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkingButtionsAction("missed", dropDownMenu, habitsTableModel);
			}
		});

		JLabel addLabel = new JLabel("Write habit to add: ");
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		rightPanel.add(addLabel, gbc);

		JTextField addHabit = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		rightPanel.add(addHabit, gbc);

		JButton addButton = new JButton("Add habit");
		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		rightPanel.add(addButton, gbc);

		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String habitToAdd = addHabit.getText();
				habitsTableModel.insertRow(habitsTableModel.getRowCount(), new Object[] { habitToAdd });
				dropDownMenu.addItem(habitToAdd);
				dropDownMenuHabits.add(habitToAdd);
				addHabit.setText(null);
				storeTableModel(habitsTableModel);
				storeDropMenuHabits(dropDownMenuHabits);

			}
		});

		frame.setVisible(true);

	}

}
