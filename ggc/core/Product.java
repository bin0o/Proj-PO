package ggc.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Class Product implements a product.
 */
public abstract class Product implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 2021212121091006L;

    /** Product attributes. */
    private boolean _isSimpleProduct;
    private double _maxPrice;
    private double _minPrice;
    private String _id;
    private List<Batch> _batches = new ArrayList<>();
    private List<Partner> _observers = new ArrayList<>();

    /**
     * Product constructor.
     * 
     * @param id Product's id.
     */
    Product(String id) {
        _id = id;
    }

    boolean getIsSimpleProduct() {
        return _isSimpleProduct;
    }

    void setIsSimpleProduct() {
        _isSimpleProduct = true;
    }

    /** Returns product max price. */
    double getMaxPrice() {
        return _maxPrice;
    }

    /** Returns product min price. */
    double getMinPrice() {
        return _minPrice;
    }

    /** Returns product id. */
    String getID() {
        return _id;
    }

    /** Returns list of batches that have the product. */
    List<Batch> getBatches() {
        return _batches;
    }

    List<Batch> getBatchesOrderedbyPrice() {
        List<Batch> orderedBatches = new ArrayList<>(getBatches());
        Collections.sort(orderedBatches, Batch.getComparatorBatchByPrice());
        return orderedBatches;
    }

    

    List<Partner> getObservers() {
        return _observers;
    }

    /** Returns product total stock. */
    int getTotalStock() {
        int stock = 0;
        for (Batch b : _batches) {
            stock += b.getQuantity();
        }
        return stock;
    }

    /**
     * Sets max price.
     * 
     * @param price
     */
    void setMaxPrice(double price) {
        if (price > _maxPrice)
            _maxPrice = price;
    }

    void setMinPrice(double price) {
        if (price < _minPrice)
            _minPrice = price;
    }

    void updateMinPrice() {
        if (hasBatches())
            _minPrice = getBatchesOrderedbyPrice().get(0).getPrice();
    }

    /**
     * Sets product id.
     * 
     * @param id
     */
    void setID(String id) {
        _id = id;
    }

    /**
     * Adds a batch to product.
     * 
     * @param batch
     */
    void addBatch(Batch batch) {
        _batches.add(batch);
    }

    void removeBatch(Batch batch) {
        _batches.remove(batch);
    }

    /** Returns true if product has batches. */
    boolean hasBatches() {
        return !_batches.isEmpty();
    }

    boolean addObserver(Partner partner) {
        return _observers.add(partner);
    }

    boolean removeObserver(Partner partner) {
        return _observers.remove(partner);
    }

    void notifyObservers(Notification notification) {
        for (Observer obs : _observers) {
            obs.addNotification(notification);
        }
    }

    /** Override of equals method. Now represented by id. */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Product && _id.equals(((Product) obj).getID());
    }

    /** Override of hashCode method. Returns id hash code. */
    @Override
    public int hashCode() {
        return _id.hashCode();
    }

    /** Returns product comparator by ids. */
    public static Comparator<Product> getComparatorById() {
        return COMPARE_BY_ID;
    }

    /** Product comparator. */
    private static final Comparator<Product> COMPARE_BY_ID = new Comparator<Product>() {

        /**
         * Compares two products by their ids.
         * 
         * @param p1
         * @param p2
         */
        @Override
        public int compare(Product p1, Product p2) {
            return p1.getID().compareToIgnoreCase(p2.getID());
        }
    };
}
