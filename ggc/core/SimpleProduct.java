package ggc.core;

public class SimpleProduct extends Product {

    SimpleProduct(String id) {
        super(id);
        setIsSimpleProduct();
    }

    @Override
    public String toString() {
        return "" + getID() + "|" + Math.round(getMaxPrice()) + "|" + getTotalStock();
    }

}