package model;

/**
 * Interface IFilm - mendefinisikan kontrak untuk operasi data film
 * Bagian dari konsep OOP: Interface
 */
public interface IFilm {
    int getIdFilm();
    String getJudul();
    float getAlur();
    float getPenokohan();
    float getAkting();
    float getNilai();

    void setIdFilm(int idFilm);
    void setJudul(String judul);
    void setAlur(float alur);
    void setPenokohan(float penokohan);
    void setAkting(float akting);

    /**
     * Hitung nilai rata-rata (alur + penokohan + akting) / 3
     */
    float hitungNilai();
}
