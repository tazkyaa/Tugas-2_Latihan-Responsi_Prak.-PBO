package model;

import database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * FilmModel - Model layer dalam MVC
 * Implementasi FilmDAO yang berinteraksi langsung dengan database via JDBC
 */
public class FilmModel implements FilmDAO {

    // ============================================================
    // CREATE - Tambah data film baru
    // ============================================================
    @Override
    public boolean tambahFilm(Film film) {
        String sql = "INSERT INTO film (judul, alur, penokohan, akting, nilai) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, film.getJudul());
            stmt.setFloat(2, film.getAlur());
            stmt.setFloat(3, film.getPenokohan());
            stmt.setFloat(4, film.getAkting());
            stmt.setFloat(5, film.hitungNilai());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Error tambahFilm: " + e.getMessage());
            return false;
        }
    }

    // ============================================================
    // READ - Ambil semua data film
    // ============================================================
    @Override
    public List<Film> getAllFilm() {
        List<Film> listFilm = new ArrayList<>();
        String sql = "SELECT * FROM film ORDER BY id_film";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Film film = new Film(
                    rs.getInt("id_film"),
                    rs.getString("judul"),
                    rs.getFloat("alur"),
                    rs.getFloat("penokohan"),
                    rs.getFloat("akting"),
                    rs.getFloat("nilai")
                );
                listFilm.add(film);
            }

        } catch (SQLException e) {
            System.err.println("Error getAllFilm: " + e.getMessage());
        }

        return listFilm;
    }

    // ============================================================
    // READ - Ambil film berdasarkan ID
    // ============================================================
    @Override
    public Film getFilmById(int id) {
        String sql = "SELECT * FROM film WHERE id_film = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Film(
                    rs.getInt("id_film"),
                    rs.getString("judul"),
                    rs.getFloat("alur"),
                    rs.getFloat("penokohan"),
                    rs.getFloat("akting"),
                    rs.getFloat("nilai")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error getFilmById: " + e.getMessage());
        }

        return null;
    }

    // ============================================================
    // UPDATE - Perbarui data film
    // ============================================================
    @Override
    public boolean updateFilm(Film film) {
        String sql = "UPDATE film SET judul=?, alur=?, penokohan=?, akting=?, nilai=? WHERE id_film=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, film.getJudul());
            stmt.setFloat(2, film.getAlur());
            stmt.setFloat(3, film.getPenokohan());
            stmt.setFloat(4, film.getAkting());
            stmt.setFloat(5, film.hitungNilai());
            stmt.setInt(6, film.getIdFilm());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Error updateFilm: " + e.getMessage());
            return false;
        }
    }

    // ============================================================
    // DELETE - Hapus data film berdasarkan ID
    // ============================================================
    @Override
    public boolean deleteFilm(int id) {
        String sql = "DELETE FROM film WHERE id_film = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Error deleteFilm: " + e.getMessage());
            return false;
        }
    }
}
