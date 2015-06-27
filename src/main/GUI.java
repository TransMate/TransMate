package main;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import languageprocessors.Speaker;

/**
 * 
 * The Graphical User Interface
 *
 */
public class GUI extends JFrame {
	private static final long serialVersionUID = -7165198102900294392L;
	private static String language1, language2;
	private static boolean record1, record2;
	private String topMessage = "", bottomMessage = "";
	private String[] languages = new String[] { "Choose a Language", "Catalan",
			"Chinese", "Danish", "Dutch", "English", "Finnish", "French",
			"German", "Italian", "Japanese", "Korean", "Norwegian", "Polish",
			"Portuguese", "Russian", "Spanish", "Swedish" };

	/**
	 * Creates new form GUI
	 */
	public GUI() {
		initComponents();

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initComponents() {
		this.setTitle("TransMate");
		this.setResizable(false);
		topLanguages = new JComboBox();
		bottomLanguages = new JComboBox();
		topPanel = new JScrollPane();
		topTextArea = new JTextArea("Press Record to begin!");
		bottomPanel = new JScrollPane();
		bottomTextArea = new JTextArea("Press Record to begin!");
		topRecord = new JToggleButton("RECORD");
		bottomRecord = new JToggleButton("RECORD");
		repeat = new JButton("REPEAT");

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		topLanguages.setModel(new DefaultComboBoxModel(languages));

		topLanguages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				language1 = (String) topLanguages.getSelectedItem();
			}
		});

		bottomLanguages.setModel(new DefaultComboBoxModel(languages));

		bottomLanguages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				language2 = (String) bottomLanguages.getSelectedItem();
			}
		});

		topTextArea.setEditable(false);
		topTextArea.setColumns(20);
		topTextArea.setRows(5);
		topTextArea.setLineWrap(true);
		topPanel.setViewportView(topTextArea);

		bottomTextArea.setEditable(false);
		bottomTextArea.setColumns(20);
		bottomTextArea.setRows(5);
		bottomTextArea.setLineWrap(true);
		bottomPanel.setViewportView(bottomTextArea);

		topRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (language1 == null || language2 == null) {
					displayText("Select languages first!",
							"Select languages first!");
					topRecord.setSelected(false);
					return;
				}
				record1 = !record1;
			}
		});

		bottomRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (language1 == null || language2 == null) {
					displayText("Select languages first!",
							"Select languages first!");
					bottomRecord.setSelected(false);
					return;
				}
				record2 = !record2;
			}
		});

		repeat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Speaker.repeat();
			}
		});

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(40, 40, 40)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING,
												false)
												.addComponent(bottomPanel)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		bottomLanguages,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.RELATED,
																		318,
																		Short.MAX_VALUE)
																.addComponent(
																		bottomRecord))
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		topLanguages,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.RELATED,
																		GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(
																		topRecord))
												.addGroup(
														layout.createSequentialGroup()
																.addGap(225,
																		225,
																		225)
																.addComponent(
																		repeat))
												.addComponent(topPanel))
								.addContainerGap(40, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(47, 47, 47)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(
														topLanguages,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(topRecord))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(topPanel,
										GroupLayout.PREFERRED_SIZE, 175,
										GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18)
								.addComponent(repeat)
								.addGap(18, 18, 18)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(
														bottomLanguages,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(bottomRecord))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(bottomPanel,
										GroupLayout.PREFERRED_SIZE, 155,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap(70, Short.MAX_VALUE)));

		pack();
	}
	
	//set messages, but wait for GUI to be available to update
	public void queueMessage(String topTarget, String bottomTarget) {
		topMessage = topTarget;
		bottomMessage = bottomTarget;
	}
	
	//update text (has to be static)
	private static void displayText(String topTarget, String bottomTarget) {
		topTextArea.setText(topTarget);
		bottomTextArea.setText(bottomTarget);
	}
	
	//get input from GUI
	public static ArrayList<Object> getInformation() {

		ArrayList<Object> information = new ArrayList<Object>();

		information.add(language1);
		information.add(record1);
		information.add(language2);
		information.add(record2);

		return information;
	}

	//make GUI window visible and start GUI updating thread
	public void init() {
		//set the Nimbus look and feel 
		try {
			for (UIManager.LookAndFeelInfo info : UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException ex) {
			queueMessage("Oops. Something went wrong.", "Oops. Something went wrong.");
		}

		//create and display the form 
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new GUI().setVisible(true);
			}
		});

		//run thread separate of the EDT to create a dynamic java GUI
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					if (!topMessage.equals("") && !bottomMessage.equals("")) {
						displayText(topMessage, bottomMessage);
					}
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

	}

	@SuppressWarnings("rawtypes")
	private static JComboBox topLanguages;
	@SuppressWarnings("rawtypes")
	private JComboBox bottomLanguages;
	private JScrollPane topPanel;
	private JScrollPane bottomPanel;
	private static JTextArea topTextArea;
	private static JTextArea bottomTextArea;
	private JToggleButton topRecord;
	private JToggleButton bottomRecord;
	private JButton repeat;
}
