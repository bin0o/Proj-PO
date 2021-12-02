package ggc.app.products;


import ggc.app.exception.UnknownProductKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
//FIXME import classes
import ggc.core.exception.NoSuchProductException;

/**
 * Show all products.
 */
class DoShowBatchesByProduct extends Command<WarehouseManager> {

  DoShowBatchesByProduct(WarehouseManager receiver) {
    super(Label.SHOW_BATCHES_BY_PRODUCT, receiver);
    addStringField("iD", Message.requestProductKey());
  }

  @Override
  public final void execute() throws CommandException {
    String iD = stringField("iD");
    try{
      _display.addAll(_receiver.getBatchesByProduct(iD));
      _display.display();
    }catch(NoSuchProductException nspe){
      throw new UnknownProductKeyException(iD);
    }
  }

}
