package ggc.core;

import java.io.Serializable;

public abstract class Notification implements Serializable {

    private static final long serialVersionUID = 1874309192006L;

    private DeliveryType _type;
    private Product _product;
    private double _price;

    Notification(Product product, Double price, DeliveryType type) {
        _product = product;
        _price = price;
        _type = type;
    }

    Product getProduct() {
        return _product;
    }

    double getPrice() {
        return _price;
    }

    double getMaxPrice() {
        return getProduct().getMaxPrice();
    }

    DeliveryType getType(){
        return _type;
    }

}
