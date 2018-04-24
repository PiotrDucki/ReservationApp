package com.piotrducki.reservationApp.mode;

import java.sql.Date;

public class Film
{
	private String tytul;
	private float dlugosc;
	private String rezyser;
	private String kategoria;
	private float ocena;
	private int ograniczeniaWiekowe;

	@Override
	public String toString()
	{
		return "Film [tytul=" + tytul + ", dlugosc=" + dlugosc + ", rezyser=" + rezyser + ", kategoria=" + kategoria
				+ ", ocena=" + ocena + ", ograniczeniaWiekowe=" + ograniczeniaWiekowe + "] \n";
	}

	public Film(String tytul, float dlugosc, String rezyser, String kategoria, float ocena, int ograniczeniaWiekowe)
	{
		super();
		this.tytul = tytul;
		this.dlugosc = dlugosc;
		this.rezyser = rezyser;
		this.kategoria = kategoria;
		this.ocena = ocena;
		this.ograniczeniaWiekowe = ograniczeniaWiekowe;
	}

	public String getTytul()
	{
		return tytul;
	}

	public void setTytul(String tytul)
	{
		this.tytul = tytul;
	}

	public float getDlugosc()
	{
		return dlugosc;
	}

	public void setDlugosc(float dlugosc)
	{
		this.dlugosc = dlugosc;
	}

	public String getRezyser()
	{
		return rezyser;
	}

	public void setRezyser(String rezyser)
	{
		this.rezyser = rezyser;
	}

	public String getKategoria()
	{
		return kategoria;
	}

	public void setKategoria(String kategoria)
	{
		this.kategoria = kategoria;
	}

	public float getOcena()
	{
		return ocena;
	}

	public void setOcena(float ocena)
	{
		this.ocena = ocena;
	}

	public int getOgraniczeniaWiekowe()
	{
		return ograniczeniaWiekowe;
	}

	public void setOgraniczeniaWiekowe(int ograniczeniaWiekowe)
	{
		this.ograniczeniaWiekowe = ograniczeniaWiekowe;
	}

}
