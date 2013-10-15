package org.spottedplaid.homeinv.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.spottedplaid.crypto.*;
import org.spottedplaid.database.DbOperations;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;

///**
//* This software has NO WARRANTY.  It is available ÄS-IS, use at your own risk.
//* 
//* @author gary
//* @version 1.0
//* 
//* EntryScreen.java
//* (c) 2013 - Spotted Plaid Productions.
//* 
//* License - Can be copied, modified, and distributed with no fees and/or royalties.  If this is used it would be appreciated if
//*           credit were given, but it is not necessary.
//*
//*/

//
///* ***************************************************************
//Class:    EntryScreen
//Purpose:  Initial screen for the user, passphrase is entered and access
//          is granted from this point
//***************************************************************  */

public class EntryScreen extends JFrame {

	private JPanel contentPane;
	private JButton jbtnCont;
	private DbOperations l_operations;
	private JPasswordField jtxtPass;
	private Crypto l_Crypto = new Crypto();
	private static EntryScreen frame = null;
	private JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new EntryScreen();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EntryScreen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 420, 496);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblPassphrase = new JLabel("Passphrase");
		
		jbtnCont = new JButton("Continue");
		jbtnCont.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String sReturn = l_Crypto.verifyKey(UIConstants.S_BASE_KEYFILE, new String(jtxtPass.getPassword()), UIConstants.S_ENC_METHOD); 
				if (sReturn.equals("Success"))
				{
				   l_operations = new DbOperations(UIConstants.S_BASE_DB_FILE);
				   Mainform mainform = new Mainform(l_operations);
				   mainform.setVisible(true);
				    frame.setVisible(false);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Failed - Unable to open password file [" + sReturn + "]");
				}
			}
		});
		jbtnCont.setEnabled(false);
       		
		JButton jbtnClose = new JButton("Close");
		jbtnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		       System.exit(NORMAL);
			}
		});
		
		jtxtPass = new JPasswordField();
		jtxtPass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String sPass = new String(jtxtPass.getPassword());
				if (sPass.length() > 0)
				{
					enableButtons();
				}
				else
				{
					disableButtons();
				}
			}
		});
		
		label = new JLabel("");
		label.setIcon(new ImageIcon(EntryScreen.class.getResource("/icons/HAM_Main.png")));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(78)
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(jbtnCont)
									.addGap(18)
									.addComponent(jbtnClose))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblPassphrase)
									.addGap(33)
									.addComponent(jtxtPass, GroupLayout.PREFERRED_SIZE, 259, GroupLayout.PREFERRED_SIZE)
									.addGap(9)))))
					.addContainerGap(51, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(36)
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 290, GroupLayout.PREFERRED_SIZE)
					.addGap(47)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPassphrase)
						.addComponent(jtxtPass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(jbtnCont)
						.addComponent(jbtnClose))
					.addContainerGap(46, Short.MAX_VALUE))
		);
		

		
		contentPane.setLayout(gl_contentPane);
		disableButtons();
	}
	
	public void disableButtons()
	{
		jbtnCont.setEnabled(false);
	}
	
	public void enableButtons()
	{
		jbtnCont.setEnabled(true);
	}
}
