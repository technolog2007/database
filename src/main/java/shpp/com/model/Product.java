package shpp.com.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Product {

    @NotNull
    @Size(min = 5, max = 25)
    private String name;
    @Max(value = 3000000)
    @Min(value = 0)
    private int categoryID;

    @Min(value = 0)
    private double price;

    public Product() {
        // it's empty
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public Product setCategoryID(int categoryID) {
        this.categoryID = categoryID;
        return this;
    }

    public Product setPrice(double price) {
        this.price = price;
        return this;
    }

    public String getName() {
        return name;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public double getPrice() {
        return price;
    }
    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", categoryID=" + categoryID +
                ", price=" + price +
                '}';
    }
}
