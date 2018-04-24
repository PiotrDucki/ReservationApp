package com.piotrducki.reservationApp.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

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

import com.piotrducki.reservationApp.DataBase.DataBaseInterface;
import com.piotrducki.reservationApp.mode.Ticket;

import java.lang.Object;

public class ReservationView extends JFrame
{
	private final int NUMER_OF_ROWS = 5;
	private final int NUMBER_OF_SEATS_IN_ROW = 10;

	private final int SEAT_SQUARE_SIZE = 25;
	private final int SPACE_BEETWEN_SEATS = 5;

	private final int SEAT_IS_FREE = 0;
	private final int SEAT_IS_SELECTED = 1;
	private final int SEAT_IS_UNAVAILABLE = 2;

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

	// init with free seats
	private int[][] seatsTable = new int[5][10];

	private JPanel contentPane;
	private JComboBox comboBoxCinema;
	private JSpinner dateSpinner;
	private JComboBox comboBoxMovie;
	private JButton buttonSetCinema;
	private JButton buttonSetDate;
	private JButton buttonSetMovie;
	private JComboBox comboBoxHour;
	private JButton buttonSetHoure;
	private JSpinner spinnerRow;
	private JSpinner spinnerSeatNumber;
	private JComboBox comboBoxDiscount;
	private JButton buttonAdd;
	private JTextField textFieldEmail;
	private JButton buttonMakeReservation;
	private JPanel panelSeats;

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

	public ReservationView() throws SQLException
	{

		initComponets();
		createEvents();
	}

	private void initComponets() throws SQLException
	{
		//////////////////////////////////////////////////////////
		// init screan
		//////////////////////////////////////////////////////////

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 425, 720);
		setTitle("Reservation");

		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//////////////////////////////////////////////////////////
		// chose cinema
		//////////////////////////////////////////////////////////

		comboBoxCinema = new JComboBox(DataBaseInterface.getCinemas());
		comboBoxCinema.setBounds(34, 25, 202, 27);
		contentPane.add(comboBoxCinema);

		buttonSetCinema = new JButton("set");
		buttonSetCinema.setBounds(275, 24, 117, 29);
		contentPane.add(buttonSetCinema);

		//////////////////////////////////////////////////////////
		// chose date
		//////////////////////////////////////////////////////////

		java.util.Date todaysDate = new java.util.Date();
		dateSpinner = new JSpinner(new SpinnerDateModel(todaysDate, null, null, Calendar.DAY_OF_MONTH));
		JSpinner.DateEditor de_dateSpinner = new JSpinner.DateEditor(dateSpinner, "dd/MM/yy");
		dateSpinner.setEditor(de_dateSpinner);
		dateSpinner.setBounds(34, 81, 202, 26);
		contentPane.add(dateSpinner);

		buttonSetDate = new JButton("set");
		buttonSetDate.setBounds(275, 81, 117, 29);
		contentPane.add(buttonSetDate);

		//////////////////////////////////////////////////////////
		// chose movie
		//////////////////////////////////////////////////////////

		comboBoxMovie = new JComboBox();
		comboBoxMovie.setBounds(34, 134, 202, 27);
		contentPane.add(comboBoxMovie);

		buttonSetMovie = new JButton("set");
		buttonSetMovie.setBounds(275, 133, 117, 29);
		contentPane.add(buttonSetMovie);

		//////////////////////////////////////////////////////////
		// chose hour
		//////////////////////////////////////////////////////////

		comboBoxHour = new JComboBox();
		comboBoxHour.setBounds(34, 188, 202, 27);
		contentPane.add(comboBoxHour);

		buttonSetHoure = new JButton("set");
		buttonSetHoure.setBounds(275, 187, 117, 29);
		contentPane.add(buttonSetHoure);

		//////////////////////////////////////////////////////////
		// display seats
		//////////////////////////////////////////////////////////

		panelSeats = new JPanel()
		{
			@Override
			public void paintComponent(Graphics g)
			{
				for (int i = 0; i < NUMER_OF_ROWS; i++)
					for (int j = 0; j < NUMBER_OF_SEATS_IN_ROW; j++)
					{
						switch (seatsTable[i][j])
						{
						case SEAT_IS_FREE:
							g.setColor(Color.GREEN);
							break;
						case SEAT_IS_SELECTED:
							g.setColor(Color.YELLOW);
							break;
						case SEAT_IS_UNAVAILABLE:
							g.setColor(Color.RED);
							break;
						default:
							break;
						}
						g.fillRect(SPACE_BEETWEN_SEATS + j * (SPACE_BEETWEN_SEATS + SEAT_SQUARE_SIZE),
								SPACE_BEETWEN_SEATS + i * (SPACE_BEETWEN_SEATS + SEAT_SQUARE_SIZE), SEAT_SQUARE_SIZE,
								SEAT_SQUARE_SIZE);
					}
			}
		};

		panelSeats.setBounds(59, 257, 300, 150);
		contentPane.add(panelSeats);

		//////////////////////////////////////////////////////////
		// chose seats
		//////////////////////////////////////////////////////////

		SpinnerModel smRow = new SpinnerNumberModel(1, 1, NUMER_OF_ROWS, 1); // default value,lower bound,upper
																				// bound,increment by
		spinnerRow = new JSpinner(smRow);
		spinnerRow.setBounds(34, 470, 81, 26);
		contentPane.add(spinnerRow);

		SpinnerModel smSeatNumber = new SpinnerNumberModel(1, 1, NUMBER_OF_SEATS_IN_ROW, 1);
		spinnerSeatNumber = new JSpinner(smSeatNumber);
		spinnerSeatNumber.setBounds(127, 470, 85, 26);
		contentPane.add(spinnerSeatNumber);
		String[] discountList =
		{ "none", "senior", "student" };
		comboBoxDiscount = new JComboBox(discountList);
		comboBoxDiscount.setBounds(224, 471, 120, 27);

		contentPane.add(comboBoxDiscount);

		buttonAdd = new JButton("add");
		buttonAdd.setBounds(356, 470, 55, 29);
		contentPane.add(buttonAdd);

		JLabel lblRow = new JLabel("row");
		lblRow.setBounds(41, 442, 61, 16);
		contentPane.add(lblRow);

		JLabel lblSeatNumber = new JLabel("seat ");
		lblSeatNumber.setBounds(133, 442, 81, 16);
		contentPane.add(lblSeatNumber);

		JLabel lblDiscount = new JLabel("discount");
		lblDiscount.setBounds(229, 442, 61, 16);
		contentPane.add(lblDiscount);

		//////////////////////////////////////////////////////////
		// enter email
		//////////////////////////////////////////////////////////

		textFieldEmail = new JTextField();
		textFieldEmail.setBounds(127, 529, 242, 26);
		contentPane.add(textFieldEmail);
		textFieldEmail.setColumns(10);

		JLabel lableEmail = new JLabel("Email");
		lableEmail.setBounds(81, 534, 34, 16);
		contentPane.add(lableEmail);

		//////////////////////////////////////////////////////////
		// make reservation
		//////////////////////////////////////////////////////////

		buttonMakeReservation = new JButton("Make Reservation");
		buttonMakeReservation.setBounds(127, 592, 155, 68);
		contentPane.add(buttonMakeReservation);

	}

	private void createEvents()
	{

		//////////////////////////////////////////////////////////
		// set cinema
		//////////////////////////////////////////////////////////

		buttonSetCinema.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				chosenCinema = (String) comboBoxCinema.getSelectedItem();
				System.out.println(chosenCinema);
			}
		});

		//////////////////////////////////////////////////////////
		// set date
		//////////////////////////////////////////////////////////

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

		//////////////////////////////////////////////////////////
		// set movie
		//////////////////////////////////////////////////////////

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

		//////////////////////////////////////////////////////////
		// set hour
		//////////////////////////////////////////////////////////

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
					insetUnavailableSeats();
					panelSeats.repaint();

				} catch (SQLException e1)
				{
					e1.printStackTrace();
				}

			}
		});

		//////////////////////////////////////////////////////////
		// add seat
		//////////////////////////////////////////////////////////

		buttonAdd.addActionListener(new ActionListener()
		{
			int row, seatNumber, discount = 0;

			public void actionPerformed(ActionEvent e)
			{
				row = (int) spinnerRow.getValue();
				seatNumber = (int) spinnerSeatNumber.getValue();
				for (Point ticket : purchasedTickets)
				{
					if (ticket.x == seatNumber && ticket.y == row)
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
				seatsTable[row - 1][seatNumber - 1] = SEAT_IS_SELECTED;
				JOptionPane.showMessageDialog(null, "ticket " + ticket + " added");
				panelSeats.repaint();

			}
		});

		//////////////////////////////////////////////////////////
		// make reservation
		//////////////////////////////////////////////////////////

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
		for (int[] seatRow : seatsTable)
			for (int seat : seatRow)
				seat = SEAT_IS_FREE;
		panelSeats.repaint();

	}

	private void insetUnavailableSeats()
	{
		for (Point ticket : purchasedTickets)
		{
			seatsTable[(int) (ticket.getY() - 1)][(int) (ticket.getX() - 1)] = SEAT_IS_UNAVAILABLE;
		}

	}
}
