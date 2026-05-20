package view;

import controller.FilmController;
import model.Film;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * FilmView - View layer dalam MVC
 * Menampilkan antarmuka grafis menggunakan Java Swing
 * Berinteraksi dengan Controller, tidak langsung ke Model
 */
public class FilmView extends JFrame {

    // ===== Komponen UI =====
    private JTable tabelFilm;
    private DefaultTableModel tableModel;

    private JTextField txtJudul;
    private JTextField txtAlur;
    private JTextField txtPenokohan;
    private JTextField txtAkting;

    private JButton btnTambah;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;

    // ID film yang sedang dipilih (untuk update/delete)
    private int selectedId = -1;

    // Controller (MVC: View berkomunikasi ke Controller)
    private FilmController controller;

    // ============================================================
    // Constructor
    // ============================================================
    public FilmView() {
        controller = new FilmController();
        initComponents();
        loadData();
    }

    // ============================================================
    // Inisialisasi komponen UI
    // ============================================================
    private void initComponents() {
        setTitle("Manajemen Data Film");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setSize(780, 500);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(245, 245, 245));

        // ===== Panel Kiri: Tabel =====
        String[] kolom = {"ID", "Judul", "Alur", "Penokohan", "Akting", "Nilai"};
        tableModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tabel tidak bisa diedit langsung
            }
        };

        tabelFilm = new JTable(tableModel);
        tabelFilm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelFilm.setRowHeight(22);
        tabelFilm.getTableHeader().setBackground(new Color(70, 130, 180));
        tabelFilm.getTableHeader().setForeground(Color.WHITE);
        tabelFilm.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        // Sembunyikan kolom ID dari tampilan tapi tetap ada di model
        tabelFilm.getColumnModel().getColumn(0).setMinWidth(0);
        tabelFilm.getColumnModel().getColumn(0).setMaxWidth(0);
        tabelFilm.getColumnModel().getColumn(0).setWidth(0);

        JScrollPane scrollPane = new JScrollPane(tabelFilm);
        scrollPane.setPreferredSize(new Dimension(430, 420));
        scrollPane.setBorder(BorderFactory.createTitledBorder("Data Film"));

        add(scrollPane, BorderLayout.CENTER);

        // ===== Panel Kanan: Form Input =====
        JPanel panelForm = new JPanel();
        panelForm.setLayout(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Input Data Film"));
        panelForm.setBackground(Color.WHITE);
        panelForm.setPreferredSize(new Dimension(280, 420));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        // Label dan field Judul
        gbc.gridy = 0;
        panelForm.add(new JLabel("Judul"), gbc);
        gbc.gridy = 1;
        txtJudul = new JTextField();
        txtJudul.setPreferredSize(new Dimension(230, 28));
        panelForm.add(txtJudul, gbc);

        // Label dan field Alur
        gbc.gridy = 2;
        panelForm.add(new JLabel("Alur (0-5)"), gbc);
        gbc.gridy = 3;
        txtAlur = new JTextField();
        txtAlur.setPreferredSize(new Dimension(230, 28));
        panelForm.add(txtAlur, gbc);

        // Label dan field Penokohan
        gbc.gridy = 4;
        panelForm.add(new JLabel("Penokohan (0-5)"), gbc);
        gbc.gridy = 5;
        txtPenokohan = new JTextField();
        txtPenokohan.setPreferredSize(new Dimension(230, 28));
        panelForm.add(txtPenokohan, gbc);

        // Label dan field Akting
        gbc.gridy = 6;
        panelForm.add(new JLabel("Akting (0-5)"), gbc);
        gbc.gridy = 7;
        txtAkting = new JTextField();
        txtAkting.setPreferredSize(new Dimension(230, 28));
        panelForm.add(txtAkting, gbc);

        // Tombol Tambah
        gbc.gridy = 8;
        gbc.insets = new Insets(12, 8, 4, 8);
        btnTambah = createButton("Tambah", new Color(60, 179, 113));
        panelForm.add(btnTambah, gbc);

        // Tombol Update
        gbc.gridy = 9;
        gbc.insets = new Insets(4, 8, 4, 8);
        btnUpdate = createButton("Update", new Color(70, 130, 180));
        panelForm.add(btnUpdate, gbc);

        // Tombol Delete
        gbc.gridy = 10;
        btnDelete = createButton("Delete", new Color(220, 80, 80));
        panelForm.add(btnDelete, gbc);

        // Tombol Clear
        gbc.gridy = 11;
        btnClear = createButton("Clear", new Color(150, 150, 150));
        panelForm.add(btnClear, gbc);

        add(panelForm, BorderLayout.EAST);

        // ===== Event Listeners =====
        setupListeners();
    }

    // ============================================================
    // Buat tombol dengan warna custom
    // ============================================================
    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setPreferredSize(new Dimension(230, 32));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ============================================================
    // Setup event listeners untuk semua tombol dan tabel
    // ============================================================
    private void setupListeners() {

        // Klik baris tabel -> isi form
        tabelFilm.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tabelFilm.getSelectedRow();
                if (row >= 0) {
                    selectedId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                    txtJudul.setText(tableModel.getValueAt(row, 1).toString());
                    txtAlur.setText(tableModel.getValueAt(row, 2).toString());
                    txtPenokohan.setText(tableModel.getValueAt(row, 3).toString());
                    txtAkting.setText(tableModel.getValueAt(row, 4).toString());
                }
            }
        });

        // Tombol TAMBAH
        btnTambah.addActionListener(e -> {
            try {
                String judul = txtJudul.getText().trim();
                float alur = Float.parseFloat(txtAlur.getText().trim());
                float penokohan = Float.parseFloat(txtPenokohan.getText().trim());
                float akting = Float.parseFloat(txtAkting.getText().trim());

                boolean success = controller.tambahFilm(judul, alur, penokohan, akting);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Data Movie Berhasil Ditambahkan",
                            "Message", JOptionPane.INFORMATION_MESSAGE);
                    clearForm();
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menambahkan data!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Nilai Alur, Penokohan, dan Akting harus berupa angka!",
                        "Input Error", JOptionPane.WARNING_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Validasi Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Tombol UPDATE
        btnUpdate.addActionListener(e -> {
            if (selectedId == -1) {
                JOptionPane.showMessageDialog(this, "Pilih film yang ingin diupdate dari tabel!",
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                String judul = txtJudul.getText().trim();
                float alur = Float.parseFloat(txtAlur.getText().trim());
                float penokohan = Float.parseFloat(txtPenokohan.getText().trim());
                float akting = Float.parseFloat(txtAkting.getText().trim());

                boolean success = controller.updateFilm(selectedId, judul, alur, penokohan, akting);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Data Movie Berhasil Diupdate",
                            "Message", JOptionPane.INFORMATION_MESSAGE);
                    clearForm();
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal mengupdate data!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Nilai Alur, Penokohan, dan Akting harus berupa angka!",
                        "Input Error", JOptionPane.WARNING_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Validasi Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Tombol DELETE
        btnDelete.addActionListener(e -> {
            if (selectedId == -1) {
                JOptionPane.showMessageDialog(this, "Pilih film yang ingin dihapus dari tabel!",
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int konfirmasi = JOptionPane.showConfirmDialog(this,
                    "Yakin ingin menghapus film ini?", "Konfirmasi Delete",
                    JOptionPane.YES_NO_OPTION);

            if (konfirmasi == JOptionPane.YES_OPTION) {
                boolean success = controller.deleteFilm(selectedId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Data Movie Berhasil Dihapus",
                            "Message", JOptionPane.INFORMATION_MESSAGE);
                    clearForm();
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menghapus data!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Tombol CLEAR
        btnClear.addActionListener(e -> clearForm());
    }

    // ============================================================
    // Load data dari database ke tabel
    // ============================================================
    private void loadData() {
        tableModel.setRowCount(0); // hapus semua baris
        List<Film> listFilm = controller.getAllFilm();
        for (Film film : listFilm) {
            tableModel.addRow(new Object[]{
                film.getIdFilm(),
                film.getJudul(),
                film.getAlur(),
                film.getPenokohan(),
                film.getAkting(),
                String.format("%.5f", film.getNilai())
            });
        }
    }

    // ============================================================
    // Clear form input
    // ============================================================
    private void clearForm() {
        txtJudul.setText("");
        txtAlur.setText("");
        txtPenokohan.setText("");
        txtAkting.setText("");
        selectedId = -1;
        tabelFilm.clearSelection();
    }

    // ============================================================
    // Main entry point
    // ============================================================
    public static void main(String[] args) {
        // Jalankan di EDT (Event Dispatch Thread) untuk thread safety Swing
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // Gunakan LAF default jika gagal
            }
            new FilmView().setVisible(true);
        });
    }
}
