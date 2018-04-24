select * from film where ograniczenia_wiekowe = 12;

select tytul as mojtytul, rezyser as mojrezyser
from film
where ograniczenia_wiekowe <= 16;

select * from film where ograniczenia_wiekowe = 12 and dlugosc < 105;

select * from bilet where seans_id = 12 and ulga = '20%';

/*
SELECT Orders.OrderID, Customers.CustomerName, Orders.OrderDate
FROM Orders
INNER JOIN Customers ON Orders.CustomerID=Customers.CustomerID;
*/

select seans.`data`, film.tytul, film.rezyser
from seans
inner join film on seans.film_tytul = film.tytul;

select seans.`data`, sala.liczba_miejsc, film.tytul, film.rezyser
from seans
inner join film on seans.film_tytul = film.tytul
inner join sala on seans.sala_numer = sala.numer;

select kino.nazwa, seans.`data`, sala.liczba_miejsc, film.tytul, film.rezyser
from seans
inner join film on seans.film_tytul = film.tytul
inner join sala on seans.sala_numer = sala.numer
inner join kino on sala.kino_nazwa = kino.nazwa;

-- SELECT COUNT(ProductID) AS NumberOfProducts FROM Products;

select count(*) as liczbaBiletow from bilet where ulga is not null;

select film.tytul, count(seans.id) as liczbaSeansow from seans
inner join film on seans.film_tytul = film.tytul
group by tytul;


select avg(pracownik.pensja) as sredniaPensja from pracownik;

select film.tytul, count(seans.id) as liczbaSeansow from seans
inner join film on seans.film_tytul = film.tytul
group by tytul
having count(seans.id) >3;

select distinct film_tytul  from seans; -- usuwanie duplikatow

select *
from sala
where liczba_miejsc > (
  select count(*) as liczbaBiletow
  from bilet
  where ulga is not null); -- podzapytanie


  /*
    podajemy adres -> wszsyscy klienci (imie, nazwisko), film(tytyl, dlugosc), kino
  */
