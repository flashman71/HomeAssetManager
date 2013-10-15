package org.spottedplaid.homeinv.ui;

import java.awt.Color;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import org.spottedplaid.database.DbConstants;
import org.spottedplaid.database.DbOperations;
import org.spottedplaid.database.DbRecord;

public class Building extends JFrame {

	private JPanel contentPane;
	private JTextField jtxtTitle;
	private JTextField jtxtDesc;
	private JTextField jtxtRooms;
	private JTextField jtxtSqft;
	private JTable jtabBldg;
	private DbOperations lDbOps;
	private DbRecord l_DbRecord = new DbRecord();
	private JButton btnAdd;
	private JButton btnReplace;
	private JButton btnDelete;
	private JButton btnClear;
	private JButton btnExit;


	/**
	 * Create the frame.
	 */
	public Building(DbOperations _dbOps) {
		lDbOps = _dbOps;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 803, 557);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 51, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblBuildings = new JLabel("Buildings");
		lblBuildings.setFont(new Font("Square721 BT", Font.BOLD, 18));
		
		JLabel lblTitle = new JLabel("Name");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		jtxtTitle = new JTextField();
		jtxtTitle.setColumns(10);
		
		jtxtDesc = new JTextField();
		jtxtDesc.setColumns(10);
		
		JLabel lblOfRooms = new JLabel("# of Rooms");
		lblOfRooms.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		jtxtRooms = new JTextField();
		jtxtRooms.setColumns(10);
		
		JLabel lblTotalSquareFeet = new JLabel("Total Square Feet");
		lblTotalSquareFeet.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		jtxtSqft = new JTextField();
		jtxtSqft.setColumns(10);
		
		JLabel lblType = new JLabel("Type");
		lblType.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JComboBox<String> jcbType = new JComboBox<String>();
		l_DbRecord.setRecordType(DbConstants.S_UDP);
		l_DbRecord.setRecordName(DbConstants.S_BUILDING);
		ResultSet rsBType = lDbOps.getRecords(l_DbRecord);

		try {
			while (rsBType.next())
			 {
				 jcbType.addItem(rsBType.getString(1));
				 
			 }
			jcbType.addItem(UIConstants.getListDefault());
			jcbType.setSelectedItem(UIConstants.getListDefault());
		} catch (SQLException e1) {
			System.out.println("Building types(main), SQLException [" + e1.getMessage() + "]");
			e1.printStackTrace();
		}
		
		JLabel lblAssociatedProperty = new JLabel("Associated Property");
		lblAssociatedProperty.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JComboBox<String> jcbSite = new JComboBox<String>();
		l_DbRecord.setRecordType(DbConstants.S_SITE);
		l_DbRecord.setRecordName(DbConstants.S_INIT);
		ResultSet rsSite = lDbOps.getRecords(l_DbRecord);
		String sSiteValue = "";

		try {
			while (rsSite.next())
			 {
				sSiteValue = rsSite.getString(1) + "-" + rsSite.getString(2);
				 jcbSite.addItem(sSiteValue);
				 
			 }
			jcbSite.addItem(UIConstants.getListDefault());
			jcbSite.setSelectedItem(UIConstants.getListDefault());
		} catch (SQLException e1) {
			System.out.println("Building properties(main), SQLException [" + e1.getMessage() + "]");
			e1.printStackTrace();
		}
		
		btnAdd = new JButton("Add");
		
		btnReplace = new JButton("Replace");
		
		btnDelete = new JButton("Delete");
		
		btnClear = new JButton("Clear");
		
		btnExit = new JButton("Exit");
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(337)
							.addComponent(lblBuildings))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(57)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblDescription)
										.addComponent(lblTitle)
										.addComponent(lblOfRooms))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(jtxtTitle, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE)
											.addGap(18)
											.addComponent(lblAssociatedProperty)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(jcbSite, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE))
										.addComponent(jtxtDesc, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(jtxtRooms, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
											.addGap(18)
											.addComponent(lblTotalSquareFeet)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(jtxtSqft, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addGap(18)
											.addComponent(lblType)
											.addGap(10)
											.addComponent(jcbType, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE))))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(btnAdd)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnReplace)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnDelete)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnClear)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnExit))
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 637, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(83, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(32)
					.addComponent(lblBuildings)
					.addGap(42)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTitle)
						.addComponent(jtxtTitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblAssociatedProperty)
						.addComponent(jcbSite, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDescription)
						.addComponent(jtxtDesc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblOfRooms)
						.addComponent(jtxtRooms, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTotalSquareFeet)
						.addComponent(jtxtSqft, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblType)
						.addComponent(jcbType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAdd)
						.addComponent(btnReplace)
						.addComponent(btnDelete)
						.addComponent(btnClear)
						.addComponent(btnExit))
					.addGap(33)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(41, Short.MAX_VALUE))
		);
		
		jtabBldg = new JTable();
		jtabBldg.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Name", "Description", "# of Rooms", "Square Feet", "Type", "Property ID"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, Integer.class, Long.class, String.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		jtabBldg.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		scrollPane.setViewportView(jtabBldg);
		contentPane.setLayout(gl_contentPane);
	}

}
