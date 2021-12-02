package ggc.core;

public interface Status {
    double getPoints(int deadlineDay,Date now, double price,Partner partner);
    double valueOfPayment(int deadlineDay,Date now,int n,double price);
}
