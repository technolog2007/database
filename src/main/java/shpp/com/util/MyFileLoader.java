package shpp.com.util;

import shpp.com.app.MyApp;
import shpp.com.repo.ConnectToDB;
import shpp.com.util.MyException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyFileLoader {

    private String line;

    private final List<List<String>> products;
    private final List<String> streets;
    private final List<String> cities;
    private final List<String> category;

    public MyFileLoader() {
        this.products = new ArrayList<>();
        this.streets = new ArrayList<>();
        this.cities = new ArrayList<>();
        this.category = new ArrayList<>();
    }

    private FileReader getFileReader(String fileName) {
        try {
            return new FileReader("config/" + fileName);
        } catch (FileNotFoundException e) {
            try {
                return new FileReader("src/main/resources/" + fileName);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void createInputDataFromFile(String fileName) throws MyException {
        try (BufferedReader br = new BufferedReader(getFileReader(fileName))) {
            while ((line = br.readLine()) != null) {
                changer(fileName);
            }
        } catch (FileNotFoundException e) {
            throw new MyException("ERROR! File not found! Please input correct file name!");
        } catch (IOException e) {
            throw new MyException(e.toString());
        }
    }

    private void changer(String fileName) throws MyException {
        if (fileName.contains("cities")) {
            cities.add(line);
        } else if (fileName.contains("street")) {
            streets.add(line);
        } else if (fileName.contains("products")) {
            List<String> product = Arrays.asList(line.split(", "));
            products.add(product);
            if (Integer.parseInt(product.get(0)) == (category.size() + 1)) {
                category.add(product.get(1));
            }
        } else {
            throw new MyException("ERROR! Please, rename files with input data, which they are consist " +
                    "\"cities\", \"street\", \"products\"");
        }
    }

    public List<List<String>> getProducts() {
        return products;
    }

    public List<String> getStreets() {
        return streets;
    }

    public List<String> getCities() {
        return cities;
    }

    public List<String> getCategory() {
        return category;
    }
}
