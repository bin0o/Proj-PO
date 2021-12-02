package ggc.core;

public class Bargain extends Notification {

    Bargain(Product product, Double price, DeliveryType type) {
        super(product, price, type);
    }

    @Override
    public String toString() {
        return "BARGAIN" + "|" + getProduct().getID() + "|" + Math.round(getPrice());
    }
}
