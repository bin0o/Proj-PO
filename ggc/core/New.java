package ggc.core;

public class New extends Notification {
    New(Product product, double price, DeliveryType type){
        super(product, price, type);
    }
    @Override
    public String toString(){
        return "NEW" + "|" + getProduct().getID() + "|" + Math.round(getPrice());
    }
}
