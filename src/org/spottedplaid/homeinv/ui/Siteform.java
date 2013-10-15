package org.spottedplaid.homeinv.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

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
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.spottedplaid.database.DbConstants;
import org.spottedplaid.database.DbOperations;
import org.spottedplaid.database.DbRecord;

public class Siteform extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField jtxtSitename;
	private JTextField jtxtSitedesc;
	private JTextField jtxtSiteacr;
	private JTextField jtxtSiteaddr;
	private JTextField jtxtSiteaddr2;
	private JTextField jtxtSitecity;
	private JTextField jtxtSitezip;
	private DbOperations l_dbOps = null;
	private DbRecord l_DbRecord = new DbRecord();
	private int iSiteId = 0;
	private JTable table;
	private JButton btnContact = null;
	private JButton btnAttachments = null;
	private JButton btnAdd = null;
	private JButton btnReplace = null;
	private JButton btnDelete = null;
	private JButton btnClear = null;
	
	/**
	 * Create the frame.
	 */
	public Siteform(DbOperations _sqlOps) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 869, 488);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		l_dbOps = _sqlOps;
		
		JLabel lblSiteSetup = new JLabel("Property Setup");
		lblSiteSetup.setFont(new Font("Ebrima", Font.BOLD, 18));
		lblSiteSetup.setHorizontalAlignment(SwingConstants.CENTER);
		lblSiteSetup.setVerticalAlignment(SwingConstants.TOP);
		
		JLabel lblSiteName = new JLabel("Site Name");
		
		jtxtSitename = new JTextField();
		jtxtSitename.setColumns(10);
		
		JLabel lblDescription = new JLabel("Description");
		
		jtxtSitedesc = new JTextField();
		jtxtSitedesc.setColumns(10);
		
		JLabel lblAcreage = new JLabel("Acreage");
		
		jtxtSiteacr = new JTextField();
		jtxtSiteacr.setColumns(10);
		
		JLabel lblAddress = new JLabel("Address");
		
		jtxtSiteaddr = new JTextField();
		jtxtSiteaddr.setColumns(10);
		
		JLabel lblAddress_1 = new JLabel("Address 2");
		
		jtxtSiteaddr2 = new JTextField();
		jtxtSiteaddr2.setColumns(10);
		
		JLabel lblCity = new JLabel("City");
		
		jtxtSitecity = new JTextField();
		jtxtSitecity.setColumns(10);
		
		JLabel lblState = new JLabel("State");
		
		final JComboBox<String> jcbSitestate = new JComboBox<String>();
		
		l_DbRecord.setRecordType(DbConstants.S_UDP);
		l_DbRecord.setRecordName(DbConstants.S_SITE);
		ResultSet rsStates = _sqlOps.getRecords(l_DbRecord);

		try {
			while (rsStates.next())
			 {
				 jcbSitestate.addItem(rsStates.getString(1));
				 
			 }
		} catch (SQLException e1) {
			System.out.println("Siteform states(main), SQLException [" + e1.getMessage() + "]");
			e1.printStackTrace();
		}
		
		JLabel lblZipcode = new JLabel("Zipcode");
		
		jtxtSitezip = new JTextField();
		jtxtSitezip.setColumns(10);
		
		 btnContact = new JButton("Contacts");
		 btnContact.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		Contacts contacts = new Contacts(l_dbOps,iSiteId);
		 		contacts.setVisible(true);
		 	}
		 });
		
		 btnAttachments = new JButton("Attachments");
		
		 btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("DEBUG->Add button, calling validateForm");
				if (validateForm()==0)
				{
					Hashtable<Object, String> htSite = new Hashtable<Object, String>();
					htSite.put("site_name", jtxtSitename.getText().toString());
					htSite.put("description", jtxtSitedesc.getText().toString());
					htSite.put("acreage", jtxtSiteacr.getText().toString());
					htSite.put("address", jtxtSiteaddr.getText().toString());
					htSite.put("address2", jtxtSiteaddr2.getText().toString());
					htSite.put("city", jtxtSitecity.getText().toString());
					htSite.put("state", jcbSitestate.getSelectedItem().toString());
					htSite.put("zipcode", jtxtSitezip.getText().toString());
		             iSiteId = l_dbOps.insertRecord(DbConstants.S_SITE, htSite);
			           System.out.println("DEBUG->Add button, back from insertRecord");
					if (iSiteId>0)
		             {
		            	 DefaultTableModel jModel = (DefaultTableModel) table.getModel();
		            	 jModel.addRow(new Object[] {iSiteId, jtxtSitename.getText().toString(),jtxtSitedesc.getText().toString(),jtxtSiteacr.getText().toString(),jtxtSiteaddr.getText().toString(),jtxtSiteaddr2.getText().toString(),jtxtSitecity.getText().toString(),jcbSitestate.getSelectedItem().toString(),jtxtSitezip.getText().toString()});
		            	 clearScreen();
		             }
					else
					{
						System.out.println("Site Add Failed");
					}
				}
			}
		});
		
		btnReplace = new JButton("Replace");
		btnReplace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                Hashtable<Object, String> htUpd = new Hashtable<Object,String>();
				int iRow = table.getSelectedRow();
				htUpd.put("site_name", jtxtSitename.getText().toString());
				htUpd.put("description", jtxtSitedesc.getText().toString());
				htUpd.put("acreage", jtxtSiteacr.getText().toString());
				htUpd.put("address", jtxtSiteaddr.getText().toString());
				htUpd.put("address2", jtxtSiteaddr2.getText().toString());
				htUpd.put("city", jtxtSitecity.getText().toString());
				htUpd.put("state", jcbSitestate.getSelectedItem().toString());
				htUpd.put("zipcode", jtxtSitezip.getText().toString());
				  if (l_dbOps.updateRecord(DbConstants.S_SITE, Integer.parseInt(table.getValueAt(iRow, 0).toString()), htUpd)>0)
				  {
			         table.setValueAt(jtxtSitename.getText().toString(), iRow, 1);  
			         table.setValueAt(jtxtSitedesc.getText().toString(), iRow, 2);
			         table.setValueAt(jtxtSiteacr.getText().toString(), iRow, 3);
			         table.setValueAt(jtxtSiteaddr.getText().toString(), iRow, 4);
			         table.setValueAt(jtxtSiteaddr2.getText().toString(), iRow, 5);
			         table.setValueAt(jtxtSitecity.getText().toString(), iRow, 6);
			         table.setValueAt(jcbSitestate.getSelectedItem().toString(), iRow, 7);
			         table.setValueAt(jtxtSitezip.getText().toString(), iRow, 8);
			         clearScreen();
				  }
			}
		});
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int iRow = table.getSelectedRow();
				if (l_dbOps.deleteRecord(DbConstants.S_SITE, Integer.parseInt(table.getValueAt(iRow, 0).toString()))==0)
				{
					DefaultTableModel rmModel = (DefaultTableModel) table.getModel();
					rmModel.removeRow(table.getSelectedRow());
					 clearScreen();
				}
				else
				{
					System.out.println("Siteform Delete Failed");
				}
			}
		});
		
		btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 clearScreen();
			}
		});
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		            setVisible(false);
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblSiteSetup, GroupLayout.PREFERRED_SIZE, 429, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(21)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblSiteName)
								.addComponent(lblAddress)
								.addComponent(lblCity))
							.addGap(15)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(btnAdd)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnReplace)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnDelete)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnClear)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnExit)
									.addPreferredGap(ComponentPlacement.RELATED))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(jtxtSitename, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
										.addComponent(jtxtSiteaddr, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
										.addComponent(jtxtSitecity, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE))
									.addGap(18)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblDescription)
										.addComponent(lblAddress_1)
										.addComponent(lblState))
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(6)
											.addComponent(jcbSitestate, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
											.addGap(18)
											.addComponent(lblZipcode)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(jtxtSitezip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(jtxtSitedesc, GroupLayout.PREFERRED_SIZE, 237, GroupLayout.PREFERRED_SIZE)
												.addComponent(jtxtSiteaddr2, 188, 188, 188))
											.addGap(18)
											.addComponent(lblAcreage)
											.addGap(15)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_contentPane.createSequentialGroup()
													.addGap(2)
													.addComponent(btnContact))
												.addComponent(btnAttachments)
												.addComponent(jtxtSiteacr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 795, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(89, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblSiteSetup)
					.addGap(50)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSiteName)
						.addComponent(jtxtSitename, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDescription)
						.addComponent(jtxtSitedesc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblAcreage)
						.addComponent(jtxtSiteacr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(jtxtSiteaddr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblAddress)
								.addComponent(lblAddress_1)
								.addComponent(jtxtSiteaddr2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblCity)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
									.addComponent(jtxtSitecity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblState)
									.addComponent(jcbSitestate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblZipcode)
									.addComponent(jtxtSitezip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGap(17)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btnExit)
								.addComponent(btnClear)
								.addComponent(btnDelete)
								.addComponent(btnReplace)
								.addComponent(btnAdd)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(38)
							.addComponent(btnContact)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnAttachments)))
					.addGap(33)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(145, Short.MAX_VALUE))
		);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Site Name", "Description", "Acreage", "Address", "Address 2", "City", "State", "Zip Code"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				true, true, true, true, true, true, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);
		loadSites();
		
		 ListSelectionModel rowSM = table.getSelectionModel();
		 

		    //Listener for client row change;
		    rowSM.addListSelectionListener(new ListSelectionListener() {

		        /// Fill the form values when a row is selected in the JTable
				@Override
				public void valueChanged(ListSelectionEvent e) {
				      ListSelectionModel lsmData = (ListSelectionModel)e.getSource();
		               if (!lsmData.isSelectionEmpty())
		               {
		                   int iRow = lsmData.getMinSelectionIndex();
		                   iSiteId = Integer.parseInt(table.getValueAt(iRow, 0).toString());
		                   jtxtSitename.setText(table.getValueAt(iRow,1).toString());
		                   jtxtSitedesc.setText(table.getValueAt(iRow, 2).toString());            
		             	   jtxtSiteacr.setText(table.getValueAt(iRow, 3).toString());
		            	   jtxtSiteaddr.setText(table.getValueAt(iRow, 4).toString());
		            	   jtxtSiteaddr2.setText(table.getValueAt(iRow, 5).toString());
		            	   jtxtSitecity.setText(table.getValueAt(iRow, 6).toString());
		            	   jcbSitestate.setSelectedItem(table.getValueAt(iRow, 7).toString());
		            	   jtxtSitezip.setText(table.getValueAt(iRow, 8).toString());	
		            	     enableButtons();
		                 }
		            }

		    });

		
	}

  private int validateForm()
  {
	  int iReturn = 0;
	  
	  if (jtxtSitename.getText().toString().equals("")||jtxtSitedesc.getText().toString().equals("")||jtxtSiteacr.getText().toString().equals("")||jtxtSiteaddr.getText().toString().equals("")||jtxtSitecity.getText().toString().equals("")||jtxtSitezip.getText().toString().equals(""))
	  {
		  System.out.println("DEBUG->validateForm, one of the fields has bad data");
		  iReturn = -1;
	  }
	  
	  return iReturn;
  }
  
  private void clearScreen()
  {
	  jtxtSitename.setText("");
	  jtxtSitedesc.setText("");
	  jtxtSiteacr.setText("");
	  jtxtSiteaddr.setText("");
	  jtxtSiteaddr2.setText("");
	  jtxtSitecity.setText("");
	  jtxtSitezip.setText("");
	   table.clearSelection();
	    disableButtons();
  }
  
  private void disableButtons()
  {
	  btnContact.setEnabled(false);
	  btnAttachments.setEnabled(false);
	  btnReplace.setEnabled(false);
	  btnDelete.setEnabled(false);
  }
  
  private void enableButtons()
  {
	  btnContact.setEnabled(true);
	  btnAttachments.setEnabled(true);
	  btnReplace.setEnabled(true);
	  btnDelete.setEnabled(true);
  }
  
  private void loadSites()
  {
	  System.out.println("DEBUG->loadSites, In the fucking routine");
    l_DbRecord.setRecordType(DbConstants.S_SITE);
    l_DbRecord.setRecordName(DbConstants.S_INIT);
     ResultSet rsSites = l_dbOps.getRecords(l_DbRecord);
     DefaultTableModel jtabModel = null;
     
     try {
         jtabModel = (DefaultTableModel) table.getModel();
    	 while (rsSites.next())
          {
    		 System.out.println("DEBUG->loadSites, processing fucking data");
            String sId = rsSites.getString(1);
            String sName = rsSites.getString(2);
            String sDesc = rsSites.getString(3);
            jtabModel.addRow(new Object[] {sId,sName,sDesc, rsSites.getString(4), rsSites.getString(5), rsSites.getString(6), rsSites.getString(7), rsSites.getString(8), rsSites.getString(9)});
          }
         disableButtons(); 

     }
     catch (SQLException se)
     {
    	System.out.println("Warning->Siteform loadSites SQLException [" + se.getMessage() + "]"); 
     }
     
     
  }
  
}
