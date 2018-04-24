

/* CREATE VIEW view_name AS
SELECT column1, column2, ...
FROM table_name
WHERE condition;
*/

create view repertuar AS
select kino.nazwa, seans.`data`, sala.liczba_miejsc, film.tytul, film.rezyser
from seansrepertuar
inner join film on seans.film_tytul = film.tytul
inner join sala on seans.sala_numer = sala.numer
inner join kino on sala.kino_nazwa = kino.nazwa;


select * from repertuar;
select distinct tytul from repertuar;
