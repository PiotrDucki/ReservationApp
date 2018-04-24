package com.piotrducki.reservationApp.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

//import java.sql.Date;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.piotrducki.reservationApp.mode.DataBaseInterface;
import com.piotrducki.reservationApp.mode.Ticket;

import java.lang.Object;

public class ReservationView extends JFrame
{
	private final int NUMER_OF_ROWS = 5;
	private final int NUMBER_OF_SEATS_IN_ROW = 10;
	
	private String[] currentlyPlayedMovies;
	private String[] movieHours;

	private String chosenCinema = "";
	private String chosenDate = "";
	private String chosenMovie = "";
	private String chosenHour = "";
	private int showTimeId;
	private Point[] purchasedTickets;
	private ArrayList<Ticket> choseTickets = new ArrayList<Ticket>();
	private String clientEmail = "";
	private int reservationId;

	private Object[][] data =
	{
			{ '-', '-', '-', '-', '-', '-', '-', '-', '-', '-' },
			{ '-', '-', '-', '-', '-', '-', '-', '-', '-', '-' },
			{ '-', '-', '-', '-', '-', '-', '-', '-', '-', '-' },
			{ '-', '-', '-', '-', '-', '-', '-', '-', '-', '-' },
			{ '-', '-', '-', '-', '-', '-', '-', '-', '-', '-' }

	};;;

	private JPanel contentPane;
	private JComboBox comboBoxCinema;
	private JSpinner dateSpinner;
	private JComboBox comboBoxMovie;
	private JButton buttonSetCinema;
	private JButton buttonSetDate;
	private JButton buttonSetMovie;
	private JComboBox comboBoxHour;
	private JButton buttonSetHoure;
	private JTable tableSeets;
	private JSpinner spinnerRow;
	private JSpinner spinnerSeatNumber;
	private JComboBox comboBoxDiscount;
	private JButton buttonAdd;
	private JTextField textFieldEmail;
	private JButton buttonMakeReservation;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					ReservationView frame = new ReservationView();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public ReservationView() throws SQLException
	{
		initComponets();
		createEvents();
	}

	//////////////////////////////////////////////////////////
	//
	//////////////////////////////////////////////////////////

	private void initComponets() throws SQLException
	{
		// init screan

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 425, 720);
		setTitle("Reservation");

		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// chose cinema

		comboBoxCinema = new JComboBox(DataBaseInterface.getCinemas());
		comboBoxCinema.setBounds(34, 25, 202, 27);
		contentPane.add(comboBoxCinema);

		buttonSetCinema = new JButton("set");
		buttonSetCinema.setBounds(275, 24, 117, 29);
		contentPane.add(buttonSetCinema);

		// chose date

		java.util.Date todaysDate = new java.util.Date();
		dateSpinner = new JSpinner(new SpinnerDateModel(todaysDate, null, null, Calendar.DAY_OF_MONTH));
		JSpinner.DateEditor de_dateSpinner = new JSpinner.DateEditor(dateSpinner, "dd/MM/yy");
		dateSpinner.setEditor(de_dateSpinner);
		dateSpinner.setBounds(34, 81, 202, 26);
		contentPane.add(dateSpinner);

		buttonSetDate = new JButton("set");
		buttonSetDate.setBounds(275, 81, 117, 29);
		contentPane.add(buttonSetDate);

		// chose movie

		comboBoxMovie = new JComboBox();
		comboBoxMovie.setBounds(34, 134, 202, 27);
		contentPane.add(comboBoxMovie);

		buttonSetMovie = new JButton("set");
		buttonSetMovie.setBounds(275, 133, 117, 29);
		contentPane.add(buttonSetMovie);

		// chose hour

		comboBoxHour = new JComboBox();
		comboBoxHour.setBounds(34, 188, 202, 27);
		contentPane.add(comboBoxHour);

		buttonSetHoure = new JButton("set");
		buttonSetHoure.setBounds(275, 187, 117, 29);
		contentPane.add(buttonSetHoure);

		Object[] columnNames =
		{ " 1", " 2", " 3", " 4", " 5", " 6", " 7", " 8", " 9", "10" };

		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		tableSeets = new JTable(model)
		{

			private static final long serialVersionUID = 1L;

			@Override
			public Class getColumnClass(int column)
			{
				return Character.class;
			}
		};
		model.fireTableDataChanged();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(tableSeets);

		JPanel panel = new JPanel(new BorderLayout());
		panel.setSize(320, 100);
		panel.setLocation(50, 250);
		panel.add(scrollPane, BorderLayout.CENTER);
		getContentPane().add(panel);

		// chose seats

		SpinnerModel smRow = new SpinnerNumberModel(1, 1, NUMER_OF_ROWS, 1); // default value,lower bound,upper
																			// bound,increment by
		spinnerRow = new JSpinner(smRow);
		spinnerRow.setBounds(25, 430, 81, 26);
		contentPane.add(spinnerRow);

		SpinnerModel smSeatNumber = new SpinnerNumberModel(1, 1, NUMBER_OF_SEATS_IN_ROW, 1);
		spinnerSeatNumber = new JSpinner(smSeatNumber);
		spinnerSeatNumber.setBounds(118, 430, 85, 26);
		contentPane.add(spinnerSeatNumber);
		String[] discountList =
		{ "none", "senior", "student" };
		comboBoxDiscount = new JComboBox(discountList);
		comboBoxDiscount.setBounds(215, 431, 120, 27);

		contentPane.add(comboBoxDiscount);

		buttonAdd = new JButton("add");
		buttonAdd.setBounds(347, 430, 55, 29);
		contentPane.add(buttonAdd);

		JLabel lblRow = new JLabel("row");
		lblRow.setBounds(32, 402, 61, 16);
		contentPane.add(lblRow);

		JLabel lblSeatNumber = new JLabel("seat ");
		lblSeatNumber.setBounds(124, 402, 81, 16);
		contentPane.add(lblSeatNumber);

		JLabel lblDiscount = new JLabel("discount");
		lblDiscount.setBounds(220, 402, 61, 16);
		contentPane.add(lblDiscount);

		// enter email

		textFieldEmail = new JTextField();
		textFieldEmail.setBounds(118, 489, 242, 26);
		contentPane.add(textFieldEmail);
		textFieldEmail.setColumns(10);

		JLabel lableEmail = new JLabel("Email");
		lableEmail.setBounds(72, 494, 34, 16);
		contentPane.add(lableEmail);

		// make reservation

		buttonMakeReservation = new JButton("Make Reservation");
		buttonMakeReservation.setBounds(126, 579, 155, 68);
		contentPane.add(buttonMakeReservation);

	}

	private void createEvents()
	{

		// set cinema

		buttonSetCinema.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				chosenCinema = (String) comboBoxCinema.getSelectedItem();
				System.out.println(chosenCinema);
			}
		});

		// set date

		buttonSetDate.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				chosenDate = dateFormat.format(dateSpinner.getValue());
				System.out.println(chosenDate);
				try
				{
					currentlyPlayedMovies = DataBaseInterface.getCurrentlyPlayedMovies(chosenDate, chosenCinema);
					comboBoxMovie.removeAllItems();
					comboBoxHour.removeAllItems();
					for (String movie : currentlyPlayedMovies)
						comboBoxMovie.addItem(movie);

				} catch (SQLException e1)
				{
					e1.printStackTrace();
				}
			}
		});

		// set movie

		buttonSetMovie.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				chosenMovie = (String) comboBoxMovie.getSelectedItem();
				System.out.println(chosenMovie);
				try
				{
					movieHours = DataBaseInterface.getMovieHours(chosenDate, chosenMovie, chosenCinema);
					comboBoxHour.removeAllItems();
					for (String hour : movieHours)
						comboBoxHour.addItem(hour);
				} catch (SQLException e1)
				{
					e1.printStackTrace();
				}
			}
		});

		// set hour

		buttonSetHoure.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				chosenHour = (String) comboBoxHour.getSelectedItem();
				System.out.println(chosenHour);
				try
				{
					showTimeId = DataBaseInterface.getShowTimeId(chosenDate, chosenHour, chosenMovie, chosenCinema);
					System.out.println(showTimeId);
					purchasedTickets = DataBaseInterface.getPurchasedTickets(showTimeId);
					System.out.println(Arrays.toString(purchasedTickets));
					insetPurchasedTicketsData();

				} catch (SQLException e1)
				{
					e1.printStackTrace();
				}

			}
		});

		// add seet

		buttonAdd.addActionListener(new ActionListener()
		{
			int row, seatNumber, discount = 0;

			public void actionPerformed(ActionEvent e)
			{
				row = (int) spinnerRow.getValue();
				seatNumber = (int) spinnerSeatNumber.getValue();
				for (Point ticket : purchasedTickets)
				{
					if (ticket.x == row && ticket.y == seatNumber)
					{
						JOptionPane.showMessageDialog(null, "ticket not available");
						return;
					}
				}
				for (Ticket ticket : choseTickets)
				{
					if (ticket.getRowNumber() == row && ticket.getSeatNumber() == seatNumber)
					{
						JOptionPane.showMessageDialog(null, "ticket already chosen");
						return;
					}
				}
				switch ((int) comboBoxDiscount.getSelectedIndex())
				{
				case 0:
					discount = 0;
					break;
				case 1:
					discount = 20;
					break;
				case 2:
					discount = 25;
					break;
				default:
					discount = 0;
					break;
				}
				System.out.println(row);
				System.out.println(seatNumber);
				System.out.println(discount);
				Ticket ticket = new Ticket(row, seatNumber, discount);
				choseTickets.add(ticket);
				JOptionPane.showMessageDialog(null, "ticket " + ticket + " added");
			}
		});

		// make reservation

		buttonMakeReservation.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				clientEmail = textFieldEmail.getText();
				try
				{
					if (!DataBaseInterface.checkIfCustumerExists(clientEmail))
						JOptionPane.showMessageDialog(null, "customer not found");
					else
					{
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Calendar cal = Calendar.getInstance();
						String currentDateTime = dateFormat.format(cal.getTime());

						reservationId = DataBaseInterface.addReservation(clientEmail, currentDateTime,
								choseTickets.size());
						for (Ticket ticket : choseTickets)
							DataBaseInterface.addTickets(ticket, reservationId, showTimeId);

						resetDataInGui();
						JOptionPane.showMessageDialog(null, choseTickets.size() + " tickets reserved successful ");

					}
				} catch (SQLException e1)
				{
					e1.printStackTrace();
				}

			}
		});
	}

	private void resetDataInGui()
	{
		comboBoxMovie.removeAllItems();
		comboBoxDiscount.removeAllItems();
		comboBoxHour.removeAllItems();
		spinnerRow.setValue(new Integer(1));
		spinnerSeatNumber.setValue(new Integer(1));
		textFieldEmail.setText("");

	}

	private void insetPurchasedTicketsData()
	{
		for (Point ticket : purchasedTickets)
		{
			data[ticket.x - 1][ticket.y - 1] = 'X';
		}

	}
}
