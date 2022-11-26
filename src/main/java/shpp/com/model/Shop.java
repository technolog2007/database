package shpp.com.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Shop {
    @NotNull
    @Pattern(regexp = "Epicenter №\\s+\\d{3}")
    private String name;
    @NotNull
    @Size(min = 10, max = 200)
    private String location;
    @Size(min = 4, max = 20)
    @NotNull
    private String city;

    public Shop() {
        //
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getCity() {
        return city;
    }

    public Shop setName(String name) {
        this.name = name;
        return this;
    }

    public Shop setLocation(String location) {
        this.location = location;
        return this;
    }

    public Shop setCity(String city) {
        this.city = city;
        return this;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
