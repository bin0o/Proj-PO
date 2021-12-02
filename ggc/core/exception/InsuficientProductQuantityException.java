package ggc.core.exception;

public class InsuficientProductQuantityException extends Exception {
    int _availableQuantity;
    public InsuficientProductQuantityException(int availableQuantity){
        _availableQuantity = availableQuantity;
    }

    public int getAvailableQuantity(){
        return _availableQuantity;
    }
}
