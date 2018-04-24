package com.piotrducki.reservationApp.test;

import java.awt.Point;
import java.sql.Date;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import com.piotrducki.reservationApp.mode.DataBaseInterface;
import com.piotrducki.reservationApp.mode.Film;
import com.piotrducki.reservationApp.mode.Ticket;
public class Test1
{
	
	
	public static void main(String[] args) throws SQLException
	{
		 String[] filmy;
		 String[] godziny;
		 String wybraneKino ;
		 String wybranyFilm;
		 String wybranaGodzina;
		  String obecnyCzas;
		 int idWybranySeans;
		Point[] zajeteMiejsca;
		 int idKlienta;
		 int idRezerwacji;
		 ArrayList<Film> aktualnieGraneFilmy;
		 
		 				
		 
		 @SuppressWarnings("deprecation")
			Date wybranaData =  new Date(118, 03, 10); // 2018-04-10
			System.out.println( wybranaData);
		 
		
		 aktualnieGraneFilmy = DataBaseInterface.getAktualnieGraneFilmy(wybranaData);
		 System.out.println(aktualnieGraneFilmy);
		 

		 
		 // nazwy kin z bazy danych
		String[] nazwyKin = DataBaseInterface.getCinemas();
		 wybraneKino = nazwyKin[0];
		 System.out.println(wybraneKino );
		
	
		 System.out.println("2018-04-10" );
		// akutalnie grane filmy danego dnia w danym kinie
		filmy = DataBaseInterface.getCurrentlyPlayedMovies("2018-04-10", wybraneKino);
		wybranyFilm = filmy[0];
		System.out.println(Arrays.toString(filmy));
		
		
		//godziny seansow danego filmu w danym kinie
		godziny =  DataBaseInterface.getMovieHours("2018-04-10", wybranyFilm, wybraneKino);
		wybranaGodzina = godziny[0];
		System.out.println(Arrays.toString(godziny));
		
		
		//id wybranego seansu
		idWybranySeans =  DataBaseInterface.getShowTimeId("2018-04-10", wybranaGodzina, wybranyFilm, wybraneKino);
		System.out.println(idWybranySeans);
		
		
		//zajete miesca pierwsza liczba oznacza rzad, druga nr siedzenia
		zajeteMiejsca = DataBaseInterface.getPurchasedTickets(idWybranySeans);
		System.out.println(Arrays.toString(zajeteMiejsca));
		
		
		
	
		// tworzymy date  
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		obecnyCzas = dateFormat.format(cal.getTime());
		
		
		
		//uzyskujemy id rezerwacji
		idRezerwacji = DataBaseInterface.addReservation("pk@gmail.com", obecnyCzas, 5);
		System.out.println(idRezerwacji);
		
		
		//tworzymy bilety rzad, nr miejsca i ulga beda podane przez urzytkownika w odpowiednich polach,
		Ticket ticket = new Ticket(1, 5, 0);
		DataBaseInterface.addTickets(ticket, idRezerwacji, idWybranySeans);
		
		
		
	}

}
