package ggc.core;

import java.io.Serializable;

public class Normal implements Status, Serializable {

    private static final long serialVersionUID = 202101223617006L;

    @Override
    public double getPoints(int deadlineDay, Date now, double price, Partner partner) {
        if (deadlineDay - now.getDays() > 0) {
            return price * 10;
        }
        return 0;
    }

    @Override
    public double valueOfPayment(int deadlineDay, Date now, int n, double price) {
        if (deadlineDay - now.getDays() >= n) {
            price -=price* 0.1;
        }

        else if (now.getDays() - deadlineDay > 0 && now.getDays() - deadlineDay <= n) {
            for (int i = 0; i < (now.getDays() - deadlineDay); i++)
                price +=price* 0.05;
        }

        else if (now.getDays() - deadlineDay > n) {
            for (int i = 0; i < (now.getDays() - deadlineDay); i++) {
                price +=price* 0.1;
            }
        }
        return price;
    }

    @Override
    public String toString() {
        return "NORMAL";
    }
}
