package myapp.modelView;

public class EmployeeView {
    private String symbol;
    private String name;
    private String surname;

    public EmployeeView(String sym, String name, String sname) {
        this.symbol = sym;
        this.name = name;
        this.surname = sname;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
