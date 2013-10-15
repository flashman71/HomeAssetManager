package org.spottedplaid.homeinv.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.util.Hashtable;

import org.spottedplaid.database.DbConstants;
import org.spottedplaid.database.DbOperations;
import org.spottedplaid.database.DbRecord;

///**
//* This software has NO WARRANTY.  It is available ÄS-IS, use at your own risk.
//* 
//* @author gary
//* @version 1.0
//* 
//* Contacts.java
//* (c) 2013 - Spotted Plaid Productions.
//* 
//* License - Can be copied, modified, and distributed with no fees and/or royalties.  If this is used it would be appreciated if
//*           credit were given, but it is not necessary.
//*
//*/

//
///* ***************************************************************
//Class:    Contacts
//Purpose:  User screen for maintaining Contacts objects
//***************************************************************  */

public class Contacts extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField jtxtFname;
	private JTextField jtxtLname;
	private JTextField jtxtHPhone;
	private JTextField jtxtMPhone;
	private JTextField jtxtEmail;
	private JTable jtabConts;
	private JButton jbtnAdd;
	private JButton jbtnMod;
	private JButton jbtnDel;
	private JComboBox<String> jcbCType;
	private DbRecord lDbRecord = new DbRecord();
	private DbOperations lDbOps = null;
    private int iSiteId = -1;
    private int iContId = -1;


	/**
	 * Create the frame.
	 */
	public Contacts(DbOperations _dbOps, int _iSiteId) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 832, 519);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		lDbOps = _dbOps;
		iSiteId = _iSiteId;
		
		JLabel lblContacts = new JLabel("Contacts");
		lblContacts.setHorizontalAlignment(SwingConstants.CENTER);
		lblContacts.setFont(new Font("Square721 BT", Font.BOLD, 18));
		
		jbtnAdd = new JButton("Add");
		jbtnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validateForm()==0)
				{
					Hashtable<Object,String> htAdd = new Hashtable<Object,String>();
					htAdd.put("first_name", jtxtFname.getText().toString());
					htAdd.put("last_name", jtxtLname.getText().toString());
					htAdd.put("contact_type", jcbCType.getSelectedItem().toString());
					htAdd.put("home_phone", jtxtHPhone.getText().toString());
					htAdd.put("mobile_phone", jtxtMPhone.getText().toString());
					htAdd.put("email", jtxtEmail.getText().toString());
					htAdd.put("site_id",Integer.toString(iSiteId));
					  iContId = lDbOps.insertRecord(DbConstants.S_CONTACT,htAdd);
					    if (iContId>0)
					    {
					    	DefaultTableModel jModel = (DefaultTableModel) jtabConts.getModel();
			            	 jModel.addRow(new Object[] {iSiteId, jtxtFname.getText().toString(),jtxtLname.getText().toString(),jcbCType.getSelectedItem().toString(),jtxtHPhone.getText().toString(),jtxtMPhone.getText().toString(),jtxtEmail.getText().toString()});
			            	 clearScreen();
					    }
				}
			}
		});
		
		jbtnMod = new JButton("Replace");
		jbtnMod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Hashtable<Object, String> htUpd = new Hashtable<Object,String>();
				int iRow = jtabConts.getSelectedRow();
				htUpd.put("first_name", jtxtFname.getText().toString());
				htUpd.put("last_name", jtxtLname.getText().toString());
				htUpd.put("contact_type", jcbCType.getSelectedItem().toString());
				htUpd.put("home_phone", jtxtHPhone.getText().toString());
				htUpd.put("mobile_phone", jtxtMPhone.getText().toString());
				htUpd.put("email", jtxtEmail.getText().toString());
				htUpd.put("site_id",Integer.toString(iSiteId));
				
				if (lDbOps.updateRecord(DbConstants.S_SITE, Integer.parseInt(jtabConts.getValueAt(iRow, 0).toString()), htUpd)>0)
				  {
			         jtabConts.setValueAt(jtxtFname.getText().toString(), iRow, 1);  
			         jtabConts.setValueAt(jtxtLname.getText().toString(), iRow, 2);
			         jtabConts.setValueAt(jcbCType.getSelectedItem().toString(), iRow, 3);
			         jtabConts.setValueAt(jtxtHPhone.getText().toString(), iRow, 4);
			         jtabConts.setValueAt(jtxtMPhone.getText().toString(), iRow, 5);
			         jtabConts.setValueAt(jtxtEmail.getText().toString(), iRow, 6);
			         clearScreen();
				  }
			}
		});
		jbtnMod.setEnabled(false);
		
		jbtnDel = new JButton("Delete");
		jbtnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int iRow = jtabConts.getSelectedRow();
				if (lDbOps.deleteRecord(DbConstants.S_SITE, Integer.parseInt(jtabConts.getValueAt(iRow, 0).toString()))==0)
				{
					DefaultTableModel rmModel = (DefaultTableModel) jtabConts.getModel();
					rmModel.removeRow(jtabConts.getSelectedRow());
					 clearScreen();
				}
				else
				{
					System.out.println("Siteform Delete Failed");
				}
			}
		});
		jbtnDel.setEnabled(false);
		
		JButton jbtnClear = new JButton("Clear");
		jbtnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearScreen();
				disableButtons();
			}
		});
		
		JButton jbtnExit = new JButton("Exit");
		jbtnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		
		JLabel lblFirstName = new JLabel("First Name");
		
		JLabel lblLastName = new JLabel("Last Name");
		
		jtxtFname = new JTextField();
		jtxtFname.setColumns(10);
		
		jtxtLname = new JTextField();
		jtxtLname.setColumns(10);
		
		JLabel lblContactType = new JLabel("Contact Type");
		
		jcbCType = new JComboBox<String>();
		
		lDbRecord.setRecordType(DbConstants.S_UDP);
		lDbRecord.setRecordName(DbConstants.S_CONTACT);
		ResultSet rsTypes = lDbOps.getRecords(lDbRecord);
		  try {
			while (rsTypes.next())
			  {
				 jcbCType.addItem(rsTypes.getString(1)); 
			  }
			jcbCType.addItem(UIConstants.getListDefault());
			jcbCType.setSelectedItem(UIConstants.getListDefault());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JLabel lblHomePhone = new JLabel("Home Phone");
		
		JLabel lblMobilePhone = new JLabel("Mobile Phone");
		
		jtxtHPhone = new JTextField();
		jtxtHPhone.setColumns(10);
		
		jtxtMPhone = new JTextField();
		jtxtMPhone.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		
		jtxtEmail = new JTextField();
		jtxtEmail.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(56)
					.addComponent(lblHomePhone)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(jtxtHPhone, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
						.addComponent(jcbCType, 0, 163, Short.MAX_VALUE)
						.addComponent(jtxtFname, 162, 162, 162))
					.addGap(23)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblMobilePhone)
						.addComponent(lblLastName)
						.addComponent(lblEmail))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(jtxtLname, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)
						.addComponent(jtxtEmail, GroupLayout.PREFERRED_SIZE, 336, GroupLayout.PREFERRED_SIZE)
						.addComponent(jtxtMPhone, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(101, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(339)
					.addComponent(lblContacts)
					.addGap(544))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(48)
					.addComponent(jbtnAdd, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(jbtnMod, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(jbtnDel, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(jbtnClear, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(jbtnExit, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addGap(331))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(56)
					.addComponent(lblContactType)
					.addContainerGap(851, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(56)
					.addComponent(lblFirstName)
					.addContainerGap(865, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(24)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 753, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(195, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblContacts)
					.addGap(33)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblFirstName)
								.addComponent(jtxtFname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblContactType)
								.addComponent(jcbCType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblLastName)
								.addComponent(jtxtLname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblEmail)
								.addComponent(jtxtEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblHomePhone)
						.addComponent(jtxtHPhone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMobilePhone)
						.addComponent(jtxtMPhone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(jbtnAdd)
						.addComponent(jbtnMod)
						.addComponent(jbtnDel)
						.addComponent(jbtnClear)
						.addComponent(jbtnExit))
					.addPreferredGap(ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 204, GroupLayout.PREFERRED_SIZE)
					.addGap(42))
		);
		
		jtabConts = new JTable();
		jtabConts.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "First Name", "Last Name", "Contact Type", "Home Phone", "Mobile", "Email"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, String.class, String.class, String.class, String.class, String.class
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
		jtabConts.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		scrollPane.setViewportView(jtabConts);
		contentPane.setLayout(gl_contentPane);
		
		loadContacts();
		
		ListSelectionModel rowContact = jtabConts.getSelectionModel();
		 

	    //Listener for client row change;
	    rowContact.addListSelectionListener(new ListSelectionListener() {

	        /// Fill the form values when a row is selected in the JTable
			@Override
			public void valueChanged(ListSelectionEvent e) {
			      ListSelectionModel lsmData = (ListSelectionModel)e.getSource();
	               if (!lsmData.isSelectionEmpty())
	               {
	                   int iRow = lsmData.getMinSelectionIndex();
	                   jtxtFname.setText(jtabConts.getValueAt(iRow,1).toString());
	                   jtxtLname.setText(jtabConts.getValueAt(iRow, 2).toString()); 
	                   jcbCType.setSelectedItem(jtabConts.getValueAt(iRow, 3).toString());	
	             	   jtxtHPhone.setText(jtabConts.getValueAt(iRow, 4).toString());
	            	   jtxtMPhone.setText(jtabConts.getValueAt(iRow, 5).toString());
	            	   jtxtEmail.setText(jtabConts.getValueAt(iRow, 6).toString());	
	            	     enableButtons();
	                 }
	            }

	    });
	}
	
	private void clearScreen()
	{
		jtxtFname.setText("");
		jtxtLname.setText("");
		jtxtHPhone.setText("");
		jtxtMPhone.setText("");
		jtxtEmail.setText("");
		jcbCType.setSelectedItem(UIConstants.getListDefault());
	}
	
	private void enableButtons()
	{
		jbtnMod.setEnabled(true);
		jbtnDel.setEnabled(true);
	}
	
	private void disableButtons()
	{
		jbtnMod.setEnabled(false);
		jbtnDel.setEnabled(false);
	}
	
	private void loadContacts()
	{
		lDbRecord.setRecordType(DbConstants.S_CONTACT);
		lDbRecord.setRecordName(DbConstants.S_INIT);
		lDbRecord.setInt1(iSiteId);
		ResultSet rsConts = lDbOps.getRecords(lDbRecord);
		 try {
			 DefaultTableModel jcontModel = (DefaultTableModel) jtabConts.getModel();
			 while (rsConts.next())
			 {
				 jcontModel.addRow(new Object[] {rsConts.getInt(1),rsConts.getString(2),rsConts.getString(3),rsConts.getString(4),rsConts.getString(5),rsConts.getString(6),rsConts.getString(7)});
			 }
			 disableButtons();
		 }
		 catch (SQLException se)
		 {
			 JOptionPane.showMessageDialog(null, "Contact - loadContact Failed [" + se.getMessage() + "]");
		 }
		
	}
	
	private int validateForm()
	{
		if (jtxtFname.getText().toString().length()==0||jtxtLname.getText().toString().length()==0)
		{
			JOptionPane.showMessageDialog(null, "Validation Failed - First Name and Last Name must be defined");
			 return -1;
		}
		
		if (jtxtHPhone.getText().toString().length()==0 && jtxtMPhone.getText().toString().length()==0)
		{
			JOptionPane.showMessageDialog(null, "Validation Failed - Home Phone or Mobile Phone must be defined");
			 return -1;
		}
		
		if (jcbCType.getSelectedItem().toString().equals(UIConstants.getListDefault()))
		{
			JOptionPane.showMessageDialog(null, "Validation Failed - Contact Type must be selected");
			 return -1;
		}
		
		if (jtxtEmail.getText().toString().length()==0)
		{
			JOptionPane.showMessageDialog(null, "Validation Failed - Email must be defined");
			 return -1;
		}
		
		return 0;
		
	}
}
