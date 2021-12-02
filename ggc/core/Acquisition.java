package ggc.core;

public class Acquisition extends Transaction {

    Acquisition(int id, Date paymentDate, double baseValue, int quantity, Product product, Partner partner) {
        super(id, paymentDate, baseValue, quantity, product, partner);
    }

    @Override
    public String toString() {
        return "COMPRA" + "|" + getID() + "|" + getPartner().getID() + "|" + getProduct().getID() + "|" + getQuantity()
                + "|" + Math.round(getBaseValue()) + "|" + getPaymentDate().getDays();
    }

}
