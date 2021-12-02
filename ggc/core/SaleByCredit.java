package ggc.core;

public class SaleByCredit extends Sale {
    private Date _deadline;
    private double _amountPaid;

    SaleByCredit(int id, Date paymentDate, double baseValue, int quantity, Product product, Partner partner,
            Date deadline, double amountPaid) {
        super(id, paymentDate, baseValue, quantity, product, partner);
        _deadline = deadline;
        _amountPaid = amountPaid;
    }

    Date getDeadline() {
        return _deadline;
    }

    double getAmountPaid() {
        return _amountPaid;
    }

    void setAmountPaid(double amountPaid) {
        _amountPaid = amountPaid;
    }

    @Override
    public String toString() {
        String SaleByCredit = "VENDA" + "|" + getID() + "|" + getPartner().getID() + "|" + getProduct().getID() + "|"
                + getQuantity() + "|" + Math.round(getBaseValue()) + "|" + Math.round(getAmountPaid()) + "|"
                + getDeadline().getDays();
        if (!isPaid())
            return SaleByCredit;
        return SaleByCredit + "|" + getPaymentDate().getDays();
    }
}
