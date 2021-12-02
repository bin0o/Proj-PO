package ggc.core;

import java.io.Serializable;

import ggc.core.exception.InvalidDayNumber;

public class Date implements Serializable {
    private static final long serialVersionUID = 20210919245546L;
    private int _days;

    Date() {
        // Empty.
    };

    Date(int days) {
        _days = days;
    }

    void add(int days) throws InvalidDayNumber {
        if (days > 0)
            _days += days;
        else
            throw new InvalidDayNumber();
    }

    int getDays() {
        return _days;
    }

    int difference(Date date) {
        return date.getDays() - getDays();
    }
}
