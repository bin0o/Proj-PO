package ggc.app.lookups;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
//FIXME import classes

/**
 * Lookup products cheaper than a given price.
 */
public class DoLookupProductBatchesUnderGivenPrice extends Command<WarehouseManager> {

  public DoLookupProductBatchesUnderGivenPrice(WarehouseManager receiver) {
    super(Label.PRODUCTS_UNDER_PRICE, receiver);
    
    addRealField("price", Message.requestPriceLimit());
  }

  @Override
  public void execute() throws CommandException {
    
    double price = realField("price");

    _display.addAll(_receiver.getBatchesUnderGivenPrice(price));
    _display.display();
  }

}
