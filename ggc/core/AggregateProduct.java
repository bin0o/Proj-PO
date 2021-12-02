package ggc.core;

public class AggregateProduct extends Product {

    private Recipe _recipe;

    AggregateProduct(String id) {
        super(id);
    }

    AggregateProduct(String id, Recipe recipe) {
        super(id);
        _recipe = recipe;
    }

    Recipe getRecipe() {
        return _recipe;
    }

    void setRecipe(Recipe recipe) {
        _recipe = recipe;
    }

    @Override
    public String toString() {
        return "" + getID() + "|" + Math.round(getMaxPrice()) + "|" + getTotalStock() + "|" + getRecipe();
    }
}