package userinterface;

import java.awt.EventQueue;
import java.io.PrintStream;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import simulation.Simulator;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfminArrivalTime;
	private JTextField tfmaxArrivalTime;
	private JTextField tfminServiceTime;
	private JTextField tfmaxServiceTime;
	private JTextField tfnoOfQueue;
	private JTextField tfsimulation;
	public static PrintStream log;
	public static JTextArea textArea2;
	public static JTextArea textArea3;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 612, 453);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMinimumArrivalTime = new JLabel("Min. arrival time:");
		lblMinimumArrivalTime.setBounds(10, 36, 117, 14);
		contentPane.add(lblMinimumArrivalTime);
		
		tfminArrivalTime = new JTextField();
		tfminArrivalTime.setBounds(149, 33, 44, 20);
		contentPane.add(tfminArrivalTime);
		tfminArrivalTime.setColumns(10);
		
		JLabel lblMaximumArrivalTime = new JLabel("Max. arrival time:");
		lblMaximumArrivalTime.setBounds(10, 59, 117, 14);
		contentPane.add(lblMaximumArrivalTime);
		
		tfmaxArrivalTime = new JTextField();
		tfmaxArrivalTime.setBounds(149, 56, 44, 20);
		contentPane.add(tfmaxArrivalTime);
		tfmaxArrivalTime.setColumns(10);
		
		JLabel lblMinimumServiceTime = new JLabel("Min. service time:");
		lblMinimumServiceTime.setBounds(10, 84, 117, 14);
		contentPane.add(lblMinimumServiceTime);
		
		tfminServiceTime = new JTextField();
		tfminServiceTime.setBounds(149, 81, 44, 20);
		contentPane.add(tfminServiceTime);
		tfminServiceTime.setColumns(10);
		
		JLabel lblMaximumServiceTime = new JLabel("Max. service time:");
		lblMaximumServiceTime.setBounds(10, 106, 117, 14);
		contentPane.add(lblMaximumServiceTime);
		
		tfmaxServiceTime = new JTextField();
		tfmaxServiceTime.setBounds(149, 103, 44, 20);
		contentPane.add(tfmaxServiceTime);
		tfmaxServiceTime.setColumns(10);
		
		JLabel lblNumberOfQueues = new JLabel("Number of queues:");
		lblNumberOfQueues.setBounds(10, 130, 117, 14);
		contentPane.add(lblNumberOfQueues);
		
		tfnoOfQueue = new JTextField();
		tfnoOfQueue.setBounds(149, 127, 44, 20);
		contentPane.add(tfnoOfQueue);
		tfnoOfQueue.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(29, 215, 540, 173);
		contentPane.add(scrollPane);
		

		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		PrintStream logy = new PrintStream(new CustomOutputStream(textArea));
		System.setOut(logy);
		
		System.setOut(logy);
		//System.setErr(logy);
		
		JLabel lblLogOfEvents = new JLabel("Log of events:");
		lblLogOfEvents.setBounds(29, 190, 81, 14);
		contentPane.add(lblLogOfEvents);
		
		JButton btnStart = new JButton("START");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				
				try {
					int simulationPer = Integer.parseInt(tfsimulation.getText());
					int minArrivalTime = Integer.parseInt(tfminArrivalTime.getText());
					int maxArrivalTime = Integer.parseInt(tfmaxArrivalTime.getText());
					int minServiceTime = Integer.parseInt(tfminServiceTime.getText());
					int maxServiceTime = Integer.parseInt(tfmaxServiceTime.getText());
					int noOfQueues = Integer.parseInt(tfnoOfQueue.getText());
					Simulator manager = new Simulator(minArrivalTime,maxArrivalTime,minServiceTime,maxServiceTime,noOfQueues,simulationPer);
					Thread newManager = new Thread(manager);
					newManager.start();
				} catch (NumberFormatException e) {
					System.out.println("Invalid input");
				}
			}

		});
		btnStart.setBounds(107, 156, 89, 23);
		contentPane.add(btnStart);
		
		JButton btnReset = new JButton("RESET");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clear();
			}
		});
		btnReset.setBounds(10, 156, 89, 23);
		contentPane.add(btnReset);
		
		JLabel lblSimulationTime = new JLabel("Simulation time:");
		lblSimulationTime.setBounds(10, 11, 100, 14);
		contentPane.add(lblSimulationTime);
		
		tfsimulation = new JTextField();
		tfsimulation.setBounds(149, 8, 44, 20);
		contentPane.add(tfsimulation);
		tfsimulation.setColumns(10);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(251, 6, 299, 76);
		contentPane.add(scrollPane_1);
		
		textArea2 = new JTextArea();
		scrollPane_1.setViewportView(textArea2);
		textArea2.setEditable(false);
		log = new PrintStream(new CustomOutputStream(textArea2));
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(251, 106, 299, 84);
		contentPane.add(scrollPane_2);
		
		textArea3 = new JTextArea();
		scrollPane_2.setViewportView(textArea3);
		textArea3.setEditable(false);
		
	
	}
	
	public void clear() {
		tfsimulation.setText("");
		tfminArrivalTime.setText("");
		tfmaxArrivalTime.setText("");
		tfminServiceTime.setText("");
		tfmaxServiceTime.setText("");
		tfnoOfQueue.setText("");
	}

}
