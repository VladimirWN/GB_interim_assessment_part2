package model.data;

public class Toy {

    private static int numberOfID;
    private int toyID;
    private String name;
    private int quantity;
    private float dropChance;

    public Toy(int toyID, String name, int quantity, float dropChance) {
        this.toyID = toyID;
        this.name = name;
        this.quantity = quantity;
        this.dropChance = dropChance;
    }

    public Toy() {
    }

    @Override
    public String toString() {
        return "Toy{" +
                "ID= " + toyID +
                ", Название= " + name + '\'' +
                ", Оставшееся количество= " + quantity +
                ", Шанс выпадения= " + dropChance +
                '}';
    }

    public static int newID() {
        return ++numberOfID;
    }

    public static int getNumberOfID() {
        return numberOfID;
    }

    public static void setNumberOfID(int numberOfID) {
        Toy.numberOfID = numberOfID;
    }

    public int getToyID() {
        return toyID;
    }

    public void setToyID(int toyID) {
        this.toyID = toyID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getDropChance() {
        return dropChance;
    }

    public void setDropChance(float dropChance) {
        this.dropChance = dropChance;
    }
}
