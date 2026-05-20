package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DatabaseConnection - Mengelola koneksi ke MySQL menggunakan JDBC
 * Menggunakan Singleton Pattern agar hanya ada satu koneksi aktif
 */
public class DatabaseConnection {

    // Konfigurasi koneksi - sesuaikan dengan setup lokal Anda
    private static final String HOST     = "localhost";
    private static final String PORT     = "3306";
    private static final String DATABASE = "movie_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = ""; // ganti sesuai password MySQL Anda

    private static final String URL =
            "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE
            + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    private static Connection connection = null;

    // Private constructor - Singleton
    private DatabaseConnection() {}

    /**
     * Mendapatkan instance koneksi (Singleton)
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Load driver MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("Koneksi ke database berhasil!");
            } catch (ClassNotFoundException e) {
                throw new SQLException("MySQL JDBC Driver tidak ditemukan: " + e.getMessage());
            }
        }
        return connection;
    }

    /**
     * Menutup koneksi
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Koneksi database ditutup.");
            } catch (SQLException e) {
                System.err.println("Error menutup koneksi: " + e.getMessage());
            }
        }
    }
}
