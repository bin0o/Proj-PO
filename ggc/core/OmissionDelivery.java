package ggc.core;

import java.io.Serializable;

public class OmissionDelivery implements Serializable, DeliveryType {

    private static final long serialVersionUID = 1874362626206L; 

    @Override
    public void sendNewNotification(Product product, double price) {
        Notification notification = new New(product, price, this);
        product.notifyObservers(notification);
    }

    @Override
    public void sendBargainNotification(Product product, double price) {
        Notification notification = new Bargain(product, price, this);
        product.notifyObservers(notification);
    }

}