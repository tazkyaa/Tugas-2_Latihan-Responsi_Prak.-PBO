package model;

/**
 * Film - Model class yang merepresentasikan data film
 * Menerapkan OOP:
 *   - Inheritance: extends BaseEntity
 *   - Interface: implements IFilm
 *   - Encapsulation: semua field private, akses via getter/setter
 */
public class Film extends BaseEntity implements IFilm {

    // Encapsulation: field private
    private String judul;
    private float alur;
    private float penokohan;
    private float akting;
    private float nilai;

    // Constructor default
    public Film() {
        super();
    }

    // Constructor lengkap (tanpa id, untuk INSERT baru)
    public Film(String judul, float alur, float penokohan, float akting) {
        super();
        this.judul = judul;
        this.alur = alur;
        this.penokohan = penokohan;
        this.akting = akting;
        this.nilai = hitungNilai();
    }

    // Constructor lengkap (dengan id, untuk data dari DB)
    public Film(int idFilm, String judul, float alur, float penokohan, float akting, float nilai) {
        super(idFilm);
        this.judul = judul;
        this.alur = alur;
        this.penokohan = penokohan;
        this.akting = akting;
        this.nilai = nilai;
    }

    // ===== Implementasi Interface IFilm =====

    @Override
    public int getIdFilm() {
        return this.id;
    }

    @Override
    public void setIdFilm(int idFilm) {
        this.id = idFilm;
    }

    @Override
    public String getJudul() {
        return judul;
    }

    @Override
    public void setJudul(String judul) {
        this.judul = judul;
    }

    @Override
    public float getAlur() {
        return alur;
    }

    @Override
    public void setAlur(float alur) {
        this.alur = alur;
        this.nilai = hitungNilai(); // update nilai otomatis
    }

    @Override
    public float getPenokohan() {
        return penokohan;
    }

    @Override
    public void setPenokohan(float penokohan) {
        this.penokohan = penokohan;
        this.nilai = hitungNilai(); // update nilai otomatis
    }

    @Override
    public float getAkting() {
        return akting;
    }

    @Override
    public void setAkting(float akting) {
        this.akting = akting;
        this.nilai = hitungNilai(); // update nilai otomatis
    }

    @Override
    public float getNilai() {
        return nilai;
    }

    /**
     * Hitung Nilai Rating = (Alur Cerita + Penokohan + Akting) / 3
     */
    @Override
    public float hitungNilai() {
        return (alur + penokohan + akting) / 3;
    }

    @Override
    public String toString() {
        return String.format("Film[id=%d, judul=%s, alur=%.1f, penokohan=%.1f, akting=%.1f, nilai=%.5f]",
                id, judul, alur, penokohan, akting, nilai);
    }
}
