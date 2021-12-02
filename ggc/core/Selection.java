package ggc.core;

import java.io.Serializable;

public class Selection implements Status, Serializable {

    private static final long serialVersionUID = 2021012723180906L;

    @Override
    public double getPoints(int deadlineDay, Date now, double price, Partner partner) {
        double points = partner.getPoints();
        if (deadlineDay - now.getDays() > 0) {
            points += price * 10;
        } else if (now.getDays() - deadlineDay > 2) {
            partner.setStatus(new Normal());
            points = 0.10;
        }
        return points;
    }

    @Override
    public double valueOfPayment(int deadlineDay, Date now, int n, double price) {
        if (deadlineDay - now.getDays() >= n) {
            price *= 0.9;
        } else if (0 <= deadlineDay - now.getDays() && deadlineDay - now.getDays() < n) {
            if (deadlineDay - now.getDays() >= 2) {
                price *= 0.95;
            }
        } else if (0 < now.getDays() - deadlineDay && now.getDays() - deadlineDay <= n) {
            if (deadlineDay - now.getDays() < 0 && now.getDays() - deadlineDay > 1) {
                for (int i = 0; i < (now.getDays() - deadlineDay); i++) {
                    price *= 1.02;
                }

            }
        } else if (now.getDays() - deadlineDay > n) {
            for (int i = 0; i < (now.getDays() - deadlineDay); i++) {
                price *= 1.05;
            }
        }
        return price;
    }

    @Override
    public String toString() {
        return "SELECTION";
    }
}
