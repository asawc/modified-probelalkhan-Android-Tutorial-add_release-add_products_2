package myapp.model;

public class Employee {
    private final int id;
    private final String symbol;
    private final String name;
    private final String surname;

    public Employee(int id, String symbol, String name, String surname) {
        this.id = id;
        this.symbol = symbol;
        this.name = name;
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
