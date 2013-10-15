package org.spottedplaid.homeinv.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.spottedplaid.database.DbOperations;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

///**
//* This software has NO WARRANTY.  It is available ÄS-IS, use at your own risk.
//* 
//* @author gary
//* @version 1.0
//* 
//* Mainform.java
//* (c) 2013 - Spotted Plaid Productions.
//* 
//* License - Can be copied, modified, and distributed with no fees and/or royalties.  If this is used it would be appreciated if
//*           credit were given, but it is not necessary.
//*
//*/

//
///* ***************************************************************
//Class:    Mainform
//Purpose:  User screen for maintaining application objects
//***************************************************************  */

public class Mainform extends JFrame {

	private JPanel contentPane;
	private DbOperations l_Operations = null;


	/**
	 * Create the frame.
	 */
	public Mainform(DbOperations _sqlOps) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 645, 536);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		l_Operations = _sqlOps;
		
		JLabel lblNewLabel = new JLabel("Home Asset Manager");
		lblNewLabel.setFont(new Font("Kalinga", Font.BOLD, 18));
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton btnSite = new JButton("");
		btnSite.setIcon(new ImageIcon(Mainform.class.getResource("/icons/Property.png")));
		btnSite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Siteform siteform = new Siteform(l_Operations);
				siteform.setVisible(true);
			}
		});
		
		JButton btnBuildings = new JButton("");
		btnBuildings.setIcon(new ImageIcon(Mainform.class.getResource("/icons/Buildings.png")));
		
		JButton btnRooms = new JButton("");
		btnRooms.setIcon(new ImageIcon(Mainform.class.getResource("/icons/Rooms.png")));
		
		JButton btnItems = new JButton("");
		btnItems.setIcon(new ImageIcon(Mainform.class.getResource("/icons/Items.png")));
		
		JButton btnReports = new JButton("");
		btnReports.setIcon(new ImageIcon(Mainform.class.getResource("/icons/Reports.png")));
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		button.setIcon(new ImageIcon(Mainform.class.getResource("/icons/Exit.png")));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(130)
							.addComponent(lblNewLabel))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(47)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(button, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(btnSite, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
											.addGap(18)
											.addComponent(btnBuildings, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(btnItems, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
											.addGap(18)
											.addComponent(btnReports, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)))
									.addGap(1)
									.addGap(18)
									.addComponent(btnRooms, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap(171, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(9)
					.addComponent(lblNewLabel)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnRooms)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btnSite)
								.addComponent(btnBuildings))
							.addGap(30)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btnReports)
								.addComponent(btnItems))))
					.addGap(69)
					.addComponent(button)
					.addGap(41))
		);
		gl_contentPane.linkSize(SwingConstants.VERTICAL, new Component[] {btnSite, btnRooms});
		gl_contentPane.linkSize(SwingConstants.VERTICAL, new Component[] {btnBuildings, btnItems});
		gl_contentPane.linkSize(SwingConstants.HORIZONTAL, new Component[] {btnSite, btnBuildings});
		contentPane.setLayout(gl_contentPane);
	}
}
