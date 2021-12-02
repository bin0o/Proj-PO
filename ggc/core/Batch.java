package ggc.core;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Class Batch implements a Batch.
 */
public class Batch implements Serializable {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202101212192006L;

    /** Batch attributes. */
    private double _price;
    private int _quantity;
    private Partner _partner;
    private Product _product;

    /**
     * Batch constructor.
     * 
     * @param price    Batch's price.
     * @param quantity Batch's quantity.
     * @param partner  Batch's partner.
     * @param product  Batch's product.
     */
    public Batch(double price, int quantity, Partner partner, Product product) {
        _price = price;
        _quantity = quantity;
        _partner = partner;
        _product = product;
    }

    /** Returns Batch price. */
    double getPrice() {
        return _price;
    }

    /** Returns Batch quantity. */
    int getQuantity() {
        return _quantity;
    }

    /** Returns Batch product. */
    Product getProduct() {
        return _product;
    }

    /** Returns Batch partner. */
    Partner getPartner() {
        return _partner;
    }

    /**
     * Sets Batch quantity.
     * 
     * @param quantity
     */
    void setQuantity(int quantity) {
        _quantity = quantity;
    }

    /** Override of the toString method. */
    @Override
    public String toString() {
        return "" + _product.getID() + "|" + _partner.getID() + "|" + Math.round(getPrice()) + "|" + getQuantity();
    }

    /** Returns Batch comparator. */
    public static Comparator<Batch> getComparatorBatch() {
        return COMPARE_BATCH;
    }

    /** Batch Comparator. */
    private static final Comparator<Batch> COMPARE_BATCH = new Comparator<Batch>() {

        /**
         * Compares two batches by their Product id, Partner id, Price and Quantity.
         * 
         * @param Batch b1
         * @param Batch b2
         */
        @Override
        public int compare(Batch b1, Batch b2) {

            if (b1.getProduct().getID().compareToIgnoreCase(b2.getProduct().getID()) != 0)
                return b1.getProduct().getID().compareToIgnoreCase(b2.getProduct().getID());

            else if (b1.getPartner().getID().compareToIgnoreCase(b2.getPartner().getID()) != 0)
                return b1.getPartner().getID().compareToIgnoreCase(b2.getPartner().getID());

            else if ((b1.getPrice() - b2.getPrice()) != 0)
                return (int) (b1.getPrice() - b2.getPrice());

            else
                return b1.getQuantity() - b2.getQuantity();
        }
    };

    /** Returns Batch comparator by price. */
    public static Comparator<Batch> getComparatorBatchByPrice() {
        return COMPARE_BATCH_BY_PRICE;
    }

    /** Batch Comparator. */
    private static final Comparator<Batch> COMPARE_BATCH_BY_PRICE = new Comparator<Batch>() {

        /**
         * Compares two batches by their Price.
         * 
         * @param Batch b1
         * @param Batch b2
         */
        @Override
        public int compare(Batch b1, Batch b2) {
            return (int) (b1.getPrice() - b2.getPrice());
        }
    };

}
