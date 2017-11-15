package tibet.paljor.assignment2_sqlite;

/**
 * Created by Nova on 2017-11-12.
 */

public class Product {
    private String name ="DefaultName";
    private String description = "Default description";
    private double price = 0.0;

    public Product(String name, String description, double price){
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){

        return description;
    }

    public double getPrice(){
        return price;
    }

    public String toString(){
        return name + " , " + description + ", " + price;
    }
}
