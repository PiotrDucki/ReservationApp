package com.piotrducki.reservationApp.view;



import javax.swing.*;
import javax.swing.border.LineBorder;

import com.piotrducki.reservationApp.DataBase.DataBaseInterface;
import com.piotrducki.reservationApp.mode.Ticket;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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


    //user input is stored in chose.... fields

    private String chosenCinema = "";
    private String chosenDate = "";
    private String chosenMovie = "";
    private String chosenHour = "";
    private ArrayList<Ticket> choseTickets = new ArrayList<Ticket>();
    private String clientEmail = "";
    private int showTimeId;
    private int reservationId;
    private Point[] purchasedTickets;


    // seatsTable init with free seats (defoult 0)

    private int[][] seatsTable = new int[5][10];

    //gui components

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

    public ReservationView() throws SQLException
    {
        initComponents();
        createEvents();
    }

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


    private void initComponents() throws SQLException
    {
        initScrean();
        initChoseCineamComponents();
        initChoseDateComponents();
        initChoseMovieComponents();
        initChoseHourComponents();
        initSeatsPanel();
        initChoseSeatsComponents();
        initEnterEmailComponents();
        initMakeReservationComponents();
    }

    private void createEvents()
    {
        createEventButtonSetCinemaPressed();
        createEventButtonSetDatePressed();
        createEventButtonSetMoviePressed();
        createEventButtonSetHourPressed();
        createEventButtonAddSeatsPressed();
        createEventButtonMakeReservationPressed();
    }

    //////////////////////////////////////////////////////////
    // private metods for initing gui
    //////////////////////////////////////////////////////////

    private void initScrean()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 425, 720);
        setTitle("Reservation");
        setResizable(false);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
        setContentPane(contentPane);
        contentPane.setLayout(null);
    }


    private void initChoseCineamComponents()
    {
        try
        {
            comboBoxCinema = new JComboBox(DataBaseInterface.getCinemas());
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        comboBoxCinema.setBounds(34, 25, 202, 27);
        contentPane.add(comboBoxCinema);

        buttonSetCinema = new JButton("set");
        buttonSetCinema.setBounds(275, 24, 117, 29);
        contentPane.add(buttonSetCinema);

    }

    private void initChoseDateComponents()
    {
        java.util.Date currentDate = new java.util.Date();
        dateSpinner = new JSpinner(new SpinnerDateModel(currentDate, null, null, Calendar.DAY_OF_MONTH));
        JSpinner.DateEditor de_dateSpinner = new JSpinner.DateEditor(dateSpinner, "dd/MM/yy");
        dateSpinner.setEditor(de_dateSpinner);
        dateSpinner.setBounds(34, 81, 202, 26);
        contentPane.add(dateSpinner);

        buttonSetDate = new JButton("set");
        buttonSetDate.setBounds(275, 81, 117, 29);
        contentPane.add(buttonSetDate);

    }

    private void initChoseMovieComponents()
    {
        comboBoxMovie = new JComboBox();
        comboBoxMovie.setBounds(34, 134, 202, 27);
        contentPane.add(comboBoxMovie);

        buttonSetMovie = new JButton("set");
        buttonSetMovie.setBounds(275, 133, 117, 29);
        contentPane.add(buttonSetMovie);

    }

    private void initChoseHourComponents()
    {
        comboBoxHour = new JComboBox();
        comboBoxHour.setBounds(34, 188, 202, 27);
        contentPane.add(comboBoxHour);

        buttonSetHoure = new JButton("set");
        buttonSetHoure.setBounds(275, 187, 117, 29);
        contentPane.add(buttonSetHoure);

    }

    private void initSeatsPanel()
    {
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

    }

    private void initChoseSeatsComponents()
    {
        SpinnerModel smRow = new SpinnerNumberModel(1, 1, NUMER_OF_ROWS, 1); // default value,lower bound,upper
        // bound,increment by
        spinnerRow = new JSpinner(smRow);
        spinnerRow.setBounds(34, 470, 81, 26);
        contentPane.add(spinnerRow);

        SpinnerModel smSeatNumber = new SpinnerNumberModel(1, 1, NUMBER_OF_SEATS_IN_ROW, 1);
        spinnerSeatNumber = new JSpinner(smSeatNumber);
        spinnerSeatNumber.setBounds(127, 470, 85, 26);
        contentPane.add(spinnerSeatNumber);
        String[] discountList = {"none", "senior", "student"};
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

    }

    private void initEnterEmailComponents()
    {
        textFieldEmail = new JTextField();
        textFieldEmail.setBounds(127, 529, 242, 26);
        contentPane.add(textFieldEmail);
        textFieldEmail.setColumns(10);

        JLabel lableEmail = new JLabel("Email");
        lableEmail.setBounds(81, 534, 34, 16);
        contentPane.add(lableEmail);

    }

    private void initMakeReservationComponents()
    {
        buttonMakeReservation = new JButton("Make Reservation");
        buttonMakeReservation.setBounds(127, 592, 155, 68);
        contentPane.add(buttonMakeReservation);
    }


    //////////////////////////////////////////////////////////
    // private metods for creating events
    //////////////////////////////////////////////////////////


    private void createEventButtonSetCinemaPressed()
    {
        buttonSetCinema.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                chosenCinema = (String) comboBoxCinema.getSelectedItem();
                System.out.println(chosenCinema);
                resetDataInGui();
            }
        });
    }

    private void createEventButtonSetDatePressed()
    {
        buttonSetDate.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                chosenDate = dateFormat.format(dateSpinner.getValue());
                try
                {
                    comboBoxMovie.removeAllItems();
                    comboBoxHour.removeAllItems();
                    resetSeatPanel();
                    currentlyPlayedMovies = DataBaseInterface.getCurrentlyPlayedMovies(chosenDate, chosenCinema);
                    for (String movie : currentlyPlayedMovies)
                        comboBoxMovie.addItem(movie);
                } catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void createEventButtonSetMoviePressed()
    {
        buttonSetMovie.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                chosenMovie = (String) comboBoxMovie.getSelectedItem();
                System.out.println(chosenMovie);
                try
                {
                    comboBoxHour.removeAllItems();
                    resetSeatPanel();
                    movieHours = DataBaseInterface.getMovieHours(chosenDate, chosenMovie, chosenCinema);
                    for (String hour : movieHours)
                        comboBoxHour.addItem(hour);
                } catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void createEventButtonSetHourPressed()
    {
        buttonSetHoure.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                chosenHour = (String) comboBoxHour.getSelectedItem();
                try
                {
                    resetSeatPanel();
                    showTimeId = DataBaseInterface.getShowTimeId(chosenDate, chosenHour, chosenMovie, chosenCinema);
                    purchasedTickets = DataBaseInterface.getPurchasedTickets(showTimeId);
                    insetUnavailableSeats();
                    panelSeats.repaint();

                } catch (SQLException e1)
                {
                    e1.printStackTrace();
                }

            }
        });
    }

    private void createEventButtonAddSeatsPressed()
    {
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
                Ticket ticket = new Ticket(row, seatNumber, discount);
                choseTickets.add(ticket);
                seatsTable[row - 1][seatNumber - 1] = SEAT_IS_SELECTED;
                panelSeats.repaint();

            }
        });

    }

    private void createEventButtonMakeReservationPressed()
    {
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
                        JOptionPane.showMessageDialog(null, choseTickets.size() +
                                " tickets reserved successful ");
                    }
                } catch (SQLException e1)
                {
                    e1.printStackTrace();
                }

            }
        });

    }


    //////////////////////////////////////////////////////////
    // other private metods
    //////////////////////////////////////////////////////////


    private void resetDataInGui()
    {
        comboBoxMovie.removeAllItems();
        comboBoxHour.removeAllItems();
        spinnerRow.setValue(new Integer(1));
        spinnerSeatNumber.setValue(new Integer(1));
        textFieldEmail.setText("");
        resetSeatPanel();

    }

    private void resetSeatPanel()
    {
        for (int i = 0; i < NUMER_OF_ROWS; i++)
            for (int j = 0; j < NUMBER_OF_SEATS_IN_ROW; j++)
                seatsTable[i][j] = SEAT_IS_FREE;
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
