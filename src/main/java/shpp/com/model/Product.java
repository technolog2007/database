package shpp.com.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Product {

    @NotNull
    @Size(min = 5, max = 20)
    private String name;
    @NotNull
    @Size(min = 5, max = 20)
    private String category;
    @NotNull
    private int categoryID;
    @NotNull
    private int price;

    public Product() {
        //
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public Product setCategory(String category) {
        this.category = category;
        return this;
    }

    public Product setCategoryID(int categoryID) {
        this.categoryID = categoryID;
        return this;
    }

    public Product setPrice(int price) {
        this.price = price;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", categoryID=" + categoryID +
                ", price=" + price +
                '}';
    }
}
