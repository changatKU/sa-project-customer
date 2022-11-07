package ku.cs.strategy;

import ku.cs.models.Customer;

import java.util.ArrayList;
import java.util.HashMap;

public class CurrentObject {
    private static String customer;
    private static HashMap<String, Integer> products = new HashMap<>();

    public static String getCustomer(){
        return customer;
    }

    public static void setCustomer(String username){
        customer = username;
    }

    public static void addProduct(String p_id, int quantity){
        if(products.get(p_id) != null){
            products.replace(p_id, products.get(p_id) + quantity);
            return;
        }
        products.put(p_id, quantity);
    }

    public static void clearProduct(){
        products = new HashMap<>();
    }

    public static HashMap<String, Integer> getProducts() {
        return products;
    }
}
