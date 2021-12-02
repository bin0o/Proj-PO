package ggc.core;

import java.io.Serializable;
import java.util.Comparator;

public abstract class Transaction implements Serializable {

    private static final long serialVersionUID = 2021091212163837538L;

    private int _id;
    private Date _paymentDate;
    private double _baseValue;
    private int _quantity;
    private Product _product;
    private Partner _partner;
    private boolean _isPaid;

    Transaction(int id, Date paymentDate, double baseValue, int quantity, Product product, Partner partner) {
        _id = id;
        _paymentDate = paymentDate;
        _baseValue = baseValue;
        _quantity = quantity;
        _product = product;
        _partner = partner;
    }

    int getID() {
        return _id;
    }

    Date getPaymentDate() {
        return _paymentDate;
    }

    void setPaymentDate(Date paymentDate) {
        _paymentDate = paymentDate;
    }

    double getBaseValue() {
        return _baseValue;
    }

    int getQuantity() {
        return _quantity;
    }

    Product getProduct() {
        return _product;
    }

    Partner getPartner() {
        return _partner;
    }

    void setIsPaid() {
        _isPaid = true;
    }

    public boolean isPaid() {
        return _isPaid;
    }

    public static Comparator<Transaction> getComparator() {
        return COMPARE_TRANSACTION;
    }

    private static final Comparator<Transaction> COMPARE_TRANSACTION = new Comparator<Transaction>() {

        @Override
        public int compare(Transaction t1, Transaction t2) {
            return t1.getID() - t2.getID();
        }
    };

}
