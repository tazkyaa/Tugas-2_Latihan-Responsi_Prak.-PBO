package model;

import java.util.List;

/**
 * FilmDAO - Interface untuk operasi CRUD ke database
 * Memisahkan logika akses data dari logika bisnis (sesuai MVC)
 */
public interface FilmDAO {
    boolean tambahFilm(Film film);
    List<Film> getAllFilm();
    Film getFilmById(int id);
    boolean updateFilm(Film film);
    boolean deleteFilm(int id);
}
