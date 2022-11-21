package shpp.com.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shpp.com.util.MyException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MyFileLoader {

    private static final Logger logger = LoggerFactory.getLogger(MyFileLoader.class);
    private String line;

    private final List<String[]> products;
    private final List<String> streets;
    private final List<String> cities;

    private final List<String> category;

    public MyFileLoader() {
        this.products = new ArrayList<>();
        this.streets = new ArrayList<>();
        this.cities = new ArrayList<>();
        this.category = new ArrayList<>();
    }

    public void createInputDataFromFile(String fileName) throws MyException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
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
            logger.info("city : {}, is add successful!", line);
        } else if (fileName.contains("street")) {
            streets.add(line);
            logger.info("street : {}, is add successful!", line);
        } else if (fileName.contains("products")) {
            String[] product = line.split(", ");
            products.add(product);
            if (Integer.parseInt(product[0]) == (category.size() + 1)) {
                category.add(product[1]);
            }
            logger.info("product : {}, and category id {} - {} is add successful!", product[2], product[0], product[1]);
        } else {
            throw new MyException("ERROR! Please, rename files with input data, which they are consist " +
                    "\"cities\", \"street\", \"products\"");
        }
    }

    public List<String[]> getProducts() {
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
