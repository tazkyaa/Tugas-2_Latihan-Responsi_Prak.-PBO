package model;

/**
 * BaseEntity - kelas abstrak sebagai parent
 * Bagian dari konsep OOP: Inheritance (parent class)
 */
public abstract class BaseEntity {
    protected int id;

    public BaseEntity() {}

    public BaseEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setiap entity harus bisa direpresentasikan sebagai String
     */
    @Override
    public abstract String toString();
}
