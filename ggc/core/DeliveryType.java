package ggc.core;

public interface DeliveryType {
    void sendNewNotification(Product product, double price);
    void sendBargainNotification(Product product, double price);
}