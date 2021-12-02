package ggc.app.products;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.app.exception.UnknownPartnerKeyException;
//FIXME import classes
import ggc.core.exception.NoSuchPartnerException;

/**
 * Show batches supplied by partner.
 */
class DoShowBatchesByPartner extends Command<WarehouseManager> {

  DoShowBatchesByPartner(WarehouseManager receiver) {
    super(Label.SHOW_BATCHES_SUPPLIED_BY_PARTNER, receiver);
    
    addStringField("iD", Message.requestPartnerKey());

  }

  @Override
  public final void execute() throws CommandException {
    
    String iD=stringField("iD");

    try{
      _display.addAll(_receiver.getBatchesByPartner(iD));
      _display.display();
    }catch(NoSuchPartnerException nspe){
      throw new UnknownPartnerKeyException(iD);
    }
  }

}
