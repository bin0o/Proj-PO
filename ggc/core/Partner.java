package ggc.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

public class Partner implements Serializable, Observer {
    private static final long serialVersionUID = 212102121219192006L;

    private String _name;
    private String _address;
    private String _id;
    private Status _status;
    private double _points;

    private List<Batch> _batches = new ArrayList<>();
    private List<Notification> _notifications = new ArrayList<>();
    private Map<Integer, Transaction> _transactions = new HashMap<>();
    private List<Acquisition> _acquisitions = new ArrayList<>();
    private List<Sale> _sales = new ArrayList<>();

    Partner(String name, String address, String id) {
        _name = name;
        _address = address;
        _id = id;
        _status = new Normal();
    }

    String getName() {
        return _name;
    }

    String getAddress() {
        return _address;
    }

    String getID() {
        return _id;
    }

    void setStatus(Status status) {
        _status = status;
    }

    double getPoints() {
        return _points;
    }

    double getValueAcquisition() {
        double value = 0;
        for (Acquisition acquisition : _acquisitions) {
            value += acquisition.getBaseValue();
        }
        return value;
    }

    double getValueSalesMade() {
        double value = 0;
        for (Sale sale : _sales) {
            value += sale.getBaseValue();
        }
        return value;
    }

    double getValuePaidSales() {
        double value = 0;
        for (Sale sale : _sales) {
            if (sale.isPaid())
                value += sale.getBaseValue();
        }
        return value;
    }

    int getQuantity(Product product) {
        int quantity = 0;
        for (Batch batch : _batches) {
            if (batch.getProduct().equals(product))
                quantity += batch.getQuantity();
        }
        return quantity;
    }

    void updateStatus() {
        Status normal = new Normal();
        Status selection = new Selection();
        Status elite = new Elite();

        if (_points < 2000) {
            _status = normal;
        } else if (_points > 2000 && _points < 25000) {
            _status = selection;
        } else {
            _status = elite;
        }
    }

    Status getStatus() {
        return _status;
    }

    void setPoints(double points) {
        _points = points;
    }

    void addBatch(Batch b) {
        _batches.add(b);
    }

    void removeBatch(Batch b) {
        _batches.remove(b);
    }

    List<Batch> getBatches() {
        return _batches;
    }

    List<Notification> getNotifications() {
        return _notifications;
    }

    String showNotifications() {
        Iterator<Notification> iter = _notifications.iterator();
        String message = "";
        while (iter.hasNext()) {
            message += iter.next();
            
            if (iter.hasNext())
                message += "\r\n";
        }
        _notifications.clear();
        return message;
    }

    List<Acquisition> getAcquisitions() {
        return _acquisitions;
    }

    void addAcquisition(Acquisition acquisition) {
        _acquisitions.add(acquisition);
    }

    List<Sale> getSales() {
        return _sales;
    }

    void addSale(Sale sale) {
        _sales.add(sale);
    }

    List<Transaction> getPaidSales() {
        List<Transaction> paidSales = new ArrayList<>();

        for (Sale sale : _sales) {
            if (sale.isPaid())
                paidSales.add(sale);
        }
        return paidSales;
    }

    @Override
    public void addNotification(Notification notification) {
        _notifications.add(notification);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Partner && _id.equals(((Partner) obj).getID());
    }

    @Override
    public int hashCode() {
        return _id.hashCode();
    }

    @Override
    public String toString() {
        return "" + getID() + "|" + getName() + "|" + getAddress() + "|" + getStatus() + "|" + Math.round(getPoints())
                + "|" + Math.round(getValueAcquisition()) + "|" + Math.round(getValueSalesMade()) + "|"
                + Math.round(getValuePaidSales());
    }

    public static Comparator<Partner> getComparatorById() {
        return COMPARE_PARTNER;
    }

    private static final Comparator<Partner> COMPARE_PARTNER = new Comparator<Partner>() {
        @Override
        public int compare(Partner p1, Partner p2) {
            return p1.getID().compareToIgnoreCase(p2.getID());
        }
    };

}
