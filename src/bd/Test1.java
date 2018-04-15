package bd;

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

public class Test1
{
	
	
	public static void main(String[] args) throws SQLException
	{
		 ArrayList<String> filmy;
		 ArrayList<String> godziny;
		 String wybraneKino ;
		 String wybranyFilm;
		 String wybranaGodzina;
		  String obecnyCzas;
		 int idWybranySeans;
		 ArrayList<Integer> zajeteMiejsca;
		 int idKlienta;
		 int idRezerwacji;
		 ArrayList<Film> aktualnieGraneFilmy;
		 
		 @SuppressWarnings("deprecation")
			Date wybranaData =  new Date(118, 03, 10); //takie liczwy daja 2018-04-10
			System.out.println( wybranaData);
		 
		
		 aktualnieGraneFilmy = DataBaseInterface.getAktualnieGraneFilmy(wybranaData);
		 System.out.println(aktualnieGraneFilmy);
		 

		 
		 // nazwy kin z bazy danych
		ArrayList<String> nazwyKin = DataBaseInterface.getKinoNazwy();
		 wybraneKino = nazwyKin.get(0);
		 System.out.println(wybraneKino );
		
	
		
		// akutalnie grane filmy danego dnia w danym kinie
		filmy = DataBaseInterface.getTytylyGranychFilmow(wybranaData, wybraneKino );
		wybranyFilm = filmy.get(0);
		System.out.println(filmy);
		
		
		//godziny seansow danego filmu w danym kinie
		godziny =  DataBaseInterface.getGodzinySensowFilmu(wybranaData, wybranyFilm, wybraneKino);
		wybranaGodzina = godziny.get(0);
		System.out.println(godziny);
		
		
		//id wybranego seansu
		idWybranySeans =  DataBaseInterface.getSeansId(wybranaData, wybranaGodzina, wybranyFilm, wybraneKino);
		System.out.println(idWybranySeans);
		
		
		//zajete miesca pierwsza liczba oznacza rzad, druga nr siedzenia
		zajeteMiejsca = DataBaseInterface.getKupioneBilety(idWybranySeans);
		System.out.println(zajeteMiejsca.toString());
		
		
		
	
		// tworzymy date  
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		obecnyCzas = dateFormat.format(cal.getTime());
		
		
		
		//uzyskujemy id rezerwacji
		idRezerwacji = DataBaseInterface.dodajRezerwacje("pk@gmail.com", obecnyCzas, 5);
		System.out.println(idRezerwacji);
		
		
		//tworzymy bilety rzad, nr miejsca i ulga beda podane przez urzytkownika w odpowiednich polach,
		DataBaseInterface.dodajBilety(5, 5, idRezerwacji, idWybranySeans);
		
		
		
	}

}
