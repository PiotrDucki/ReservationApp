package bd;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.xml.crypto.Data;

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

	
	
	public static ArrayList<String> getKinoNazwy() throws SQLException
	{
		ArrayList<String> nazwy = new ArrayList<String>();
		String query = ("select nazwa from kino;");

		ResultSet rs = DataBase.getInstance().query(query);
		while (rs.next())
		{
			String nazwa = rs.getString("nazwa");
			nazwy.add(nazwa);
		}
		return nazwy;
	}

	public static ArrayList<String> getTytylyGranychFilmow(Date data, String nazwaKina) throws SQLException
	{

		ArrayList<String> filmy = new ArrayList<String>();
		String query = ("select film_tytul from seans s where date(s.data) =  \"" + data + "\""
				+ "and s.sala_kino_nazwa = \"" + nazwaKina + "\" group by s.film_tytul ");

		ResultSet rs = DataBase.getInstance().query(query);
		while (rs.next())
		{
			String film = rs.getString("film_tytul");
			filmy.add(film);
		}
		return filmy;
	}

	public static ArrayList<String> getGodzinySensowFilmu(Date data, String tytulFilmu, String nazwaKina)
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
		return godziny;
	}

	public static int getSeansId(Date dzien, String godzina, String tytulFilmu, String nazwaKina) throws SQLException
	{
		String data = (dzien.toString() + " " + godzina + ":00");
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

	public static ArrayList<Integer> getKupioneBilety(int seansId) throws SQLException
	{
		ArrayList<Integer> bilety = new ArrayList<Integer>();
		int rzad, nr_siedzenia;
		String query = ("select nr_siedzenia, rzad from bilet where seans_id = " + seansId + " ;");

		ResultSet rs = DataBase.getInstance().query(query);
		while (rs.next())
		{
			rzad = rs.getInt("rzad");
			nr_siedzenia = rs.getInt("nr_siedzenia");
			bilety.add(rzad);
			bilety.add(nr_siedzenia);
		}
		return bilety;
	}

	private static int getKlientId(String mail) throws SQLException
	{
		int klientId = 0;
		String query = ("select id from klient  where mail =  \"" + mail + "\"");
		ResultSet rs = DataBase.getInstance().query(query);
		if (rs.next())
			klientId = rs.getInt("id");

		return klientId;
	}

	public static int dodajRezerwacje(String mail, String data, int liczbaBiletow) throws SQLException
	{
		int rezerwacjaId = 0;
		int klientId = DataBaseInterface.getKlientId(mail);
		String insertQuery = ("INSERT INTO rezerwacja (data_rezerwacji, liczba_biletow, klient_id)  VALUES ( \"" + data
				+ "\", \"" + liczbaBiletow + "\", \"" + klientId + "\");");
		DataBase.getInstance().insert(insertQuery);

		String query = ("SELECT LAST_INSERT_ID();");
		ResultSet rs = DataBase.getInstance().query(query);
		if (rs.next())
			rezerwacjaId = rs.getInt("LAST_INSERT_ID()");

		return rezerwacjaId;
	}

	public static void dodajBilety(int nrSiedzenia, int rzad, int ulga, int rezerwacjaId, int seansId)
			throws SQLException
	{
		String insertQuery = ("INSERT INTO bilet (nr_siedzenia, rzad, ulga, rezerwacja_id, seans_id)  VALUES ( \""
				+ nrSiedzenia + "\", \"" + rzad + "\", \"" + ulga + "\",\"" + rezerwacjaId + "\", \"" + seansId
				+ "\"   );");
		DataBase.getInstance().insert(insertQuery);
	}

	public static void dodajBilety(int nrSiedzenia, int rzad, int rezerwacjaId, int seansId) throws SQLException
	{
		String insertQuery = ("INSERT INTO bilet (nr_siedzenia, rzad, rezerwacja_id, seans_id)  VALUES ( \""
				+ nrSiedzenia + "\", \"" + rzad + "\", \"" + rezerwacjaId + "\", \"" + seansId + "\"   );");
		DataBase.getInstance().insert(insertQuery);
	}

}
