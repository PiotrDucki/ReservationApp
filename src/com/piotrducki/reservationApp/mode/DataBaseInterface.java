package com.piotrducki.reservationApp.mode;

import java.util.Date;

import java.awt.Point;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import com.piotrducki.reservationApp.mode.Ticket;

public class DataBaseInterface
{
	
	// zapytanie do wyswietlania repertuaru 

	public static ArrayList<Film> getAktualnieGraneFilmy(Date data) throws SQLException
	{
		ArrayList<Film> filmy = new ArrayList<Film>();
		String tytul;
		float dlugosc;
		String rezyser;
		String kategoria;
		float ocena;
		int ograniczeniaWiekowe;

		String query = ("select film_tytul, rezyser, dlugosc, kategoria, ocena, ograniczenia_wiekowe from seans s, film f "
				+ "	where s.film_tytul = f.tytul and date(s.data) = \"" + data + "\"" + "		group by f.tytul;");
		ResultSet rs = DataBase.getInstance().query(query);
		while (rs.next())
		{
			tytul = rs.getString("film_tytul");
			dlugosc = rs.getFloat("dlugosc");
			rezyser = rs.getString("rezyser");
			kategoria = rs.getString("kategoria");
			ocena = rs.getFloat("ocena");
			ograniczeniaWiekowe = rs.getInt("ograniczenia_wiekowe");
			Film film = new Film(tytul, dlugosc, rezyser, kategoria, ocena, ograniczeniaWiekowe);
			filmy.add(film);
		}
		return filmy;
	}

	
	// zapytania do obslugi rezerwacii

	
	
	public static String[] getCinemas() throws SQLException
	{
		ArrayList<String> nazwy = new ArrayList<String>();
		String query = ("select nazwa from kino;");

		ResultSet rs = DataBase.getInstance().query(query);
		while (rs.next())
		{
			String nazwa = rs.getString("nazwa");
			nazwy.add(nazwa);
		}
		String[] tablicaNazw = nazwy.toArray(new String [nazwy.size()]);
		return tablicaNazw ;
	}

	public static String[] getCurrentlyPlayedMovies(String date, String nazwaKina) throws SQLException
	{

		ArrayList<String> filmy = new ArrayList<String>();
		String query = ("select film_tytul from seans s where date(s.data) =  \"" + date + "\""
				+ "and s.sala_kino_nazwa = \"" + nazwaKina + "\" group by s.film_tytul ");

		ResultSet rs = DataBase.getInstance().query(query);
		while (rs.next())
		{
			String film = rs.getString("film_tytul");
			filmy.add(film);
		}
		String[] tablicaFilmy = filmy.toArray( new String[filmy.size()]);	
		
		return tablicaFilmy;
	}
	public static String[] getMovieHours(String data, String tytulFilmu, String nazwaKina)
			throws SQLException
	{

		ArrayList<String> godziny = new ArrayList<String>();
		String query = (" select DATE_FORMAT(data,'%H:%i') czas from seans s where date(s.data)  =    \"" + data + "\""
				+ "and s.sala_kino_nazwa = \"" + nazwaKina + "\"" + "and s.film_tytul =     \"" + tytulFilmu + "\"");
		ResultSet rs = DataBase.getInstance().query(query);
		while (rs.next())
		{
			String godzina = rs.getString("czas");
			godziny.add(godzina);
		}
		
		String[] tablicaGodziny= godziny.toArray(new String [godziny.size()]);
		return tablicaGodziny;
	}

	public static int getShowTimeId(String dzien, String godzina, String tytulFilmu, String nazwaKina) throws SQLException
	{
		String data = (dzien+ " " + godzina + ":00");
		int seansId = 0;
		String query = (" select id from seans  where data  =    \"" + data + "\"" + "and sala_kino_nazwa = \""
				+ nazwaKina + "\"" + "and film_tytul =     \"" + tytulFilmu + "\"");

		ResultSet rs = DataBase.getInstance().query(query);
		if (rs.next())
		{
			seansId = rs.getInt("id");
		}
		return seansId;
	}

	public static Point[] getPurchasedTickets(int seansId) throws SQLException
	{
		ArrayList<Point> bilety = new ArrayList<Point>();
		int rzad, nr_siedzenia;
		String query = ("select nr_siedzenia, rzad from bilet where seans_id = " + seansId + " ;");

		ResultSet rs = DataBase.getInstance().query(query);
		while (rs.next())
		{
			rzad = rs.getInt("rzad");
			nr_siedzenia = rs.getInt("nr_siedzenia");
			bilety.add(new Point(rzad, nr_siedzenia));
			
		}
		Point [] tablicaBiletow= bilety.toArray(new Point[bilety.size()]);
		
		
		
		return tablicaBiletow;
	}
	public static boolean checkIfCustumerExists(String mail) throws SQLException
	{
		int klientId = -1;
		String query = ("select id from klient  where mail =  \"" + mail + "\"");
		ResultSet rs = DataBase.getInstance().query(query);
		if (rs.next())
			klientId = rs.getInt("id");
		if(klientId != -1)
			return true;
		else
			return false;
	}

	public static int addReservation(String mail, String data, int liczbaBiletow) throws SQLException
	{
		int rezerwacjaId = 0;
		int klientId = DataBaseInterface.getCustomerId(mail);
		String insertQuery = ("INSERT INTO rezerwacja (data_rezerwacji, liczba_biletow, klient_id)  VALUES ( \"" + data
				+ "\", \"" + liczbaBiletow + "\", \"" + klientId + "\");");
		DataBase.getInstance().insert(insertQuery);

		String query = ("SELECT LAST_INSERT_ID();");
		ResultSet rs = DataBase.getInstance().query(query);
		if (rs.next())
			rezerwacjaId = rs.getInt("LAST_INSERT_ID()");

		return rezerwacjaId;
	}

	public static void addTickets(Ticket ticket, int rezerwacjaId, int seansId)
			throws SQLException
	{
		int nrSiedzenia = ticket.getSeatNumber();
		int rzad = ticket.getRowNumber();
		int ulga = ticket.getDiscount();
		String insertQuery;
		if(ulga == 0)
			 insertQuery = ("INSERT INTO bilet (nr_siedzenia, rzad, rezerwacja_id, seans_id)  VALUES ( \""
					+ nrSiedzenia + "\", \"" + rzad + "\", \"" + rezerwacjaId + "\", \"" + seansId + "\"   );");
		else if(ulga == 20)
			insertQuery = ("INSERT INTO bilet (nr_siedzenia, rzad, ulga, rezerwacja_id, seans_id)  VALUES ( \""
					+ nrSiedzenia + "\", \"" + rzad + "\",  '20%' ,\"" + rezerwacjaId + "\", \"" + seansId
					+ "\"   );");
		else
			insertQuery = ("INSERT INTO bilet (nr_siedzenia, rzad, ulga, rezerwacja_id, seans_id)  VALUES ( \""
				+ nrSiedzenia + "\", \"" + rzad + "\",  '25%' ,\"" + rezerwacjaId + "\", \"" + seansId
				+ "\"   );");
		
		DataBase.getInstance().insert(insertQuery);
	}

	
	private static int getCustomerId(String mail) throws SQLException
	{
		int klientId = 0;
		String query = ("select id from klient  where mail =  \"" + mail + "\"");
		ResultSet rs = DataBase.getInstance().query(query);
		if (rs.next())
			klientId = rs.getInt("id");

		return klientId;
	}

}
