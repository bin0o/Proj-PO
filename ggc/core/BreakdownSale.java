package ggc.core;

import java.util.ArrayList;
import java.util.List;

public class BreakdownSale extends Sale {

    private List<Batch> _batches = new ArrayList<>();

    BreakdownSale(int id, Date paymentDate, double baseValue, int quantity, Product product, Partner partner) {
        super(id, paymentDate, baseValue, quantity, product, partner);
    }

    public List<Batch> getBatches() {
        return _batches;
    }
}
