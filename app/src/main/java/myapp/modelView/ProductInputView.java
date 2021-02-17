package myapp.modelView;

public class ProductInputView extends ProductView {

    private Integer requestedQuantity;

    public ProductInputView(String symbol, String name, int quantity, Integer reqQuantity) {
        super(symbol, name, quantity);
        requestedQuantity=reqQuantity;
    }

    public void setRequestedQuantity(Integer val){
        requestedQuantity = val;
    }

    public Integer getRequestedQuantity() {
        return requestedQuantity;
    }
}
