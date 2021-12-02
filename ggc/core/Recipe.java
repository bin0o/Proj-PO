package ggc.core;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable {
    private static final long serialVersionUID = 86234128121242006L;

    private double _alpha;
    private List<Component> _components = new ArrayList<>();

    Recipe(double alpha, List<Component> components) {
        _alpha = alpha;
        _components = components;
    }

    double getAlpha() {
        return _alpha;
    }

    void setAlpha(double alpha) {
        _alpha = alpha;
    }

    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < _components.size(); i++) {
            res += _components.get(i);
            if (i < _components.size() - 1) {
                res += "#";
            }
        }
        return res;
    }
}
