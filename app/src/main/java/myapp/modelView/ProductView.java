package myapp.modelView;

public class ProductView {
    private String symbol;
    private String name;
    private int quantity;

    public ProductView(String symbol, String name, int quantity) {
        this.symbol = symbol;
        this.name = name;
        this.quantity = quantity;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}
