package shpp.com.util;

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
    private static final String CONFIG = "config/";
    private static final String IDEA_RESOURCES = "src/main/resources/";

    /**
     * The constructor initializes the input lists
     */
    public MyFileLoader() {
        this.products = new ArrayList<>();
        this.streets = new ArrayList<>();
        this.cities = new ArrayList<>();
        this.category = new ArrayList<>();
    }

    /**
     * The method selects the reader file depending on how the program is launched (from IDEA or *.jar)
     * @param fileName - file name
     * @return - valid FileReader
     */
    private FileReader getFileReader(String fileName) {
        try {
            return new FileReader(CONFIG + fileName);
        } catch (FileNotFoundException e) {
            try {
                return new FileReader(IDEA_RESOURCES + fileName);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException("ERROR! File not found! Please input correct file name!");
            }
        }
    }

    /**
     * The method reads data from the file and writes them to the corresponding list
     * @param fileName - file name
     * @throws MyException -
     */
    public void createInputDataFromFile(String fileName) throws MyException {
        try (BufferedReader br = new BufferedReader(getFileReader(fileName))) {
            while ((line = br.readLine()) != null) {
                changer(fileName);
            }
        } catch (IOException e) {
            throw new MyException(e.toString());
        }
    }

    /**
     * The method parses and records the input data for forming store and product objects in the appropriate list.
     * @param fileName - file name
     * @throws MyException -
     */
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

    /**
     * The method returns a list of inputs for creating products
     * @return - input list
     */
    public List<List<String>> getProducts() {
        return products;
    }
    /**
     * The method returns a list of input data for forming store objects
     * @return - input list
     */
    public List<String> getStreets() {
        return streets;
    }
    /**
     * The method returns a list of input data for forming store objects
     * @return - input list
     */
    public List<String> getCities() {
        return cities;
    }
    /**
     * The method returns a list of input data for forming store objects
     * @return - input list
     */
    public List<String> getCategory() {
        return category;
    }
}
