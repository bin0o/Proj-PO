package ggc.core;

import java.io.Serializable;

public class Component implements Serializable {
    private static final long serialVersionUID = 202109099006L;

    private Product _product;
    private int _quantity;

    Component(Product product, int quantity) {
        _product = product;
        _quantity = quantity;
    }

    @Override
    public String toString() {
        return "" + _product.getID() + ":" + _quantity;
    }
}
