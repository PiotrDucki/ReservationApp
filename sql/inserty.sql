
-- FILMY

INSERT INTO film (tytul, dlugosc, rezyser, data_produkcji, kategoria, ograniczenia_wiekowe)
VALUES ('Passengers', 116, 'Morten Tyldum', '2016-12-25', 'Thriller, Sci-Fi', 16);

INSERT INTO film (tytul, dlugosc, rezyser, data_produkcji, kategoria, ograniczenia_wiekowe)
VALUES ('Mother!', 121, 'Darren Aronofsky', '2017-11-03', 'Drama, Horror', 16);

INSERT INTO film (tytul, dlugosc, rezyser, data_produkcji, kategoria, ograniczenia_wiekowe)
VALUES ('Requiem dor a Dream', 102, 'Darren Aronofsky', '2010-03-16', 'Drama', 16);

INSERT INTO film (tytul, dlugosc, rezyser, data_produkcji, kategoria, ograniczenia_wiekowe)
VALUES ('Joy', 124, 'David O. Russell', '2016-01-08', 'Drama', 16);

INSERT INTO film (tytul, dlugosc, rezyser, data_produkcji, kategoria, ograniczenia_wiekowe)
VALUES ('Silver Linings Playbook', 122, 'David O. Russell', '2013-02-08', 'Comedy, Drama', 12);

INSERT INTO film (tytul, dlugosc, rezyser, data_produkcji, kategoria, ograniczenia_wiekowe)
VALUES ('War Dogs', 114, 'Todd Phillips', '2016-08-19', 'Comedy, Crime, Drama', 12);

INSERT INTO film (tytul, dlugosc, rezyser, data_produkcji, kategoria, ograniczenia_wiekowe)
VALUES ('PG-13', 96, 'Clint Eastwood', '2016-12-02', 'Biography, Drama', 12);

INSERT INTO film (tytul, dlugosc, rezyser, data_produkcji, kategoria, ograniczenia_wiekowe)
VALUES ('Snowden', 134, 'Oliver Stone', '2016-11-18', 'Biography, Drama', 12);

INSERT INTO film (tytul, dlugosc, rezyser, data_produkcji, kategoria, ograniczenia_wiekowe)
VALUES ('Burnt', 101, 'John Welles', '2015-10-23', 'Comedy, Drama', 12);

INSERT INTO film (tytul, dlugosc, rezyser, data_produkcji, kategoria, ograniczenia_wiekowe)
VALUES ('Love Simon', 110, 'Greg Berlanti', '2018-06-15', 'Comedy, Romance', 12);

	-- ADRES
INSERT INTO adres (id, ulica, nr_domu, miasto, kod_pocztowy, wojewodztwo)
VALUES (1234, 'Akacjowa', 5, 'Warszawa', '03188', 'Mazowieckie');

	-- KINO
INSERT INTO kino (nazwa, liczba_sal, adres_id)
VALUES ('Cinema City Bialoleka', 4, 1234);

	-- SALA
INSERT INTO sala (numer, liczba_miejsc, kino_nazwa)
VALUES  (1, 20,'Cinema City Bialoleka'),
		(2, 30,'Cinema City Bialoleka'),
		(3, 15,'Cinema City Bialoleka'),
		(4, 10,'Cinema City Bialoleka');

	-- SEANS
INSERT INTO seans (id, `data`, sala_numer, cena, film_tytul, sala_kino_nazwa, dlugosc_reklam)
VALUES  (1,'2018-04-10 10:00:00', 1, 20, 'Burnt', 'Cinema City Bialoleka', 10 ),
		(2,'2018-04-10 12:00:00', 1, 20, 'Burnt', 'Cinema City Bialoleka', 10 ),
		(3,'2018-04-10 14:00:00', 1, 20, 'Burnt', 'Cinema City Bialoleka', 10 ),
        (4,'2018-04-10 10:00:00', 2, 20, 'Snowden', 'Cinema City Bialoleka', 10 ),
        (5,'2018-04-10 13:00:00', 2, 20,'Snowden', 'Cinema City Bialoleka', 10 ),
        (6,'2018-04-10 16:00:00', 2, 20, 'Snowden', 'Cinema City Bialoleka', 10 ),
        (7,'2018-04-10 10:00:00', 3, 20, 'Joy', 'Cinema City Bialoleka', 10 ),
        (8,'2018-04-10 12:30:00', 3, 20, 'Joy', 'Cinema City Bialoleka', 10 ),
        (9,'2018-04-10 15:00:00', 3, 20, 'Joy', 'Cinema City Bialoleka', 10 ),
        (10,'2018-04-10 17:30:00', 3, 20, 'Joy', 'Cinema City Bialoleka', 10 ),
        (11,'2018-04-10 10:00:00', 4, 20, 'Mother!', 'Cinema City Bialoleka', 10 ),
        (12,'2018-04-10 13:00:00', 4, 20, 'Mother!', 'Cinema City Bialoleka', 10 ),
        (13,'2018-04-10 16:00:00', 4, 20, 'Mother!', 'Cinema City Bialoleka', 10 );

 	-- KLIENT
INSERT INTO klient(id, imie, nazwisko, nr_telefonu, mail)
VALUES  (1, 'Zofia', 'Dabrowska', 500409039, 'zofia@gmail.com'),
		(2, 'Piotr', 'Ducki', 550409023, 'ducu@gmail.com'),
        (3, 'Zuzia', 'Kowalska', 545609039, 'zuzia@gmail.com'),
        (4, 'Ania', 'Nowak', 512345039, 'a.nowak@gmail.com'),
        (5, 'Rafal', 'Antoniewicz', 500408802, 'rafal@gmail.com'),
        (6, 'Robert', 'Kupisz', 506187222, 'kupisz@gmail.com'),
        (7, 'Paulina', 'Koska', 605123452, 'pk@gmail.com');

	-- REZERWACJA
INSERT INTO rezerwacja (id, data_rezerwacji, liczba_biletow, klient_id)
VALUES  (1, '2018-04-5 10:30:12', 3, 1),
		(2, '2018-04-6 11:30:27', 1, 2),
        (3, '2018-04-5 12:31:20', 3, 3),
        (4, '2018-04-5 13:33:30', 1, 4),
        (5, '2018-04-5 15:45:12', 2, 5),
        (6, '2018-04-5 16:12:35', 1, 6),
        (7, '2018-04-5 17:55:56', 2, 7);


	-- BILET
INSERT INTO bilet (id, nr_siedzenia, rzad, ulga, rezerwacja_id, seans_id)
VALUES  (1, 1, 2,  '25%', 1, 1),
		(2, 2, 2, '25%', 1, 1),
        (3, 3, 2, '25%', 1, 1),
        (4, 1, 1,  '20%', 2, 4),
        (5, 1, 2, '20%', 3, 4),
        (6, 2, 2, '25%', 3, 4),
        (7, 3, 2, '20%', 3, 4),
        (8, 1, 2, '25%', 4, 12),
        (9, 2, 2, '20%', 5, 12);

INSERT INTO bilet (id, nr_siedzenia, rzad, rezerwacja_id, seans_id)
VALUES  (10, 3, 2, 5, 12),
        (11, 1, 1, 6, 8),
        (12, 2, 1, 7, 8),
        (13, 3, 1, 7, 8);

INSERT INTO bilet (id, nr_siedzenia, rzad, seans_id)
VALUES  (14, 4, 2, 12),
        (15, 4, 1, 8),
        (16, 5, 1, 8),
        (17, 1, 2,  8);

INSERT INTO bilet (id, nr_siedzenia, rzad, ulga, seans_id)
VALUES  (18, 4, 2, '25%', 1),
		(19, 5, 2, '25%', 1),
        (20, 1, 1, '25%', 1),
        (21, 2, 1, '20%', 4),
        (22, 4, 2, '20%', 4);


 	-- PRACOWNIK
INSERT INTO pracownik (pesel, imie, nazwisko, data_urodzenia, nr_telefonu, plec, pensja)
VALUES  ('9605070974', 'Andrzej', 'Nowak', '96-05-07', 506176234, 'M', 1500 ),
		('9504120975', 'Marta', 'Gniazdowska', '95-04-12', 576176234, 'K', 2500 ),
        ('8912240945', 'Tomasz', 'Nowak', '89-12-24', 606176508, 'M', 3500 );

	-- ETAT
INSERT INTO etat (kino_nazwa, pesel, wymiar_godzinowy)
VALUES  ('Cinema City Bialoleka', '9605070974', 80),
		('Cinema City Bialoleka', '9504120975', 120),
		('Cinema City Bialoleka', '8912240945', 160);
