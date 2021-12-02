package ggc.core;

import java.io.Serializable;

public class Elite implements Status, Serializable {

    private static final long serialVersionUID = 2123334212192006L;

    @Override
    public double getPoints(int deadlineDay, Date now, double price, Partner partner) {
        double points = partner.getPoints();
        if (deadlineDay - now.getDays() > 0) {
            points += price * 10;
        } else if (now.getDays() - deadlineDay > 15) {
            partner.setStatus(new Selection());
            points = 0.25;
        }
        return points;
    }

    @Override
    public double valueOfPayment(int deadlineDay, Date now, int n, double price) {
        if (deadlineDay - now.getDays() >= n) {
            price *= 0.9;
        } else if (0 <= deadlineDay - now.getDays() && deadlineDay - now.getDays() < n) {
            price *= 0.9;
        } else if (0 < now.getDays() - deadlineDay && now.getDays() - deadlineDay <= n) {
            price *= 0.95;
        }
        return price;
    }

    @Override
    public String toString() {
        return "ELITE";
    }
}
