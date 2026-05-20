package controller;

import model.Film;
import model.FilmModel;

import java.util.List;

/**
 * FilmController - Controller layer dalam MVC
 * Menjadi jembatan antara View dan Model
 * Berisi logika bisnis aplikasi
 */
public class FilmController {

    // Controller menggunakan Model (dependency injection)
    private FilmModel filmModel;

    public FilmController() {
        this.filmModel = new FilmModel();
    }

    // ============================================================
    // CREATE
    // ============================================================
    /**
     * Menambahkan film baru ke database
     * @return true jika berhasil, false jika gagal
     * @throws IllegalArgumentException jika validasi gagal
     */
    public boolean tambahFilm(String judul, float alur, float penokohan, float akting) {
        // Validasi input
        validateInput(judul, alur, penokohan, akting);

        Film film = new Film(judul, alur, penokohan, akting);
        return filmModel.tambahFilm(film);
    }

    // ============================================================
    // READ
    // ============================================================
    /**
     * Mengambil semua data film dari database
     */
    public List<Film> getAllFilm() {
        return filmModel.getAllFilm();
    }

    /**
     * Mengambil film berdasarkan ID
     */
    public Film getFilmById(int id) {
        return filmModel.getFilmById(id);
    }

    // ============================================================
    // UPDATE
    // ============================================================
    /**
     * Memperbarui data film yang sudah ada
     * @return true jika berhasil, false jika gagal
     */
    public boolean updateFilm(int id, String judul, float alur, float penokohan, float akting) {
        // Validasi input
        validateInput(judul, alur, penokohan, akting);

        Film film = new Film(id, judul, alur, penokohan, akting, 0);
        return filmModel.updateFilm(film);
    }

    // ============================================================
    // DELETE
    // ============================================================
    /**
     * Menghapus film berdasarkan ID
     * @return true jika berhasil, false jika gagal
     */
    public boolean deleteFilm(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID film tidak valid!");
        }
        return filmModel.deleteFilm(id);
    }

    // ============================================================
    // Validasi Input (Logika Bisnis)
    // ============================================================
    private void validateInput(String judul, float alur, float penokohan, float akting) {
        if (judul == null || judul.trim().isEmpty()) {
            throw new IllegalArgumentException("Judul film tidak boleh kosong!");
        }
        if (alur < 0 || alur > 5) {
            throw new IllegalArgumentException("Nilai Alur harus antara 0 - 5!");
        }
        if (penokohan < 0 || penokohan > 5) {
            throw new IllegalArgumentException("Nilai Penokohan harus antara 0 - 5!");
        }
        if (akting < 0 || akting > 5) {
            throw new IllegalArgumentException("Nilai Akting harus antara 0 - 5!");
        }
    }
}
