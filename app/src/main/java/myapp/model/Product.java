package myapp.model;

import com.google.gson.annotations.SerializedName;

public class Product {

    private final int id;
    private final int quantity;

    @SerializedName("productname")
    private final String name;

    @SerializedName("productsymbol")
    private final String symbol;

    public Product(int quantity, String name, String symbol) {
        this.id = -1;
        this.quantity = quantity;
        this.name = name;
        this.symbol = symbol;
    }

    public Product(int id, int quantity, String name, String symbol) {
        this.id = id;
        this.quantity = quantity;
        this.name = name;
        this.symbol = symbol;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() { return quantity; }

    public String getName() { return name; }

    public String getSymbol() { return symbol; }
}
