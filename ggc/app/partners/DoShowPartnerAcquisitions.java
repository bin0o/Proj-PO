package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.WarehouseManager;
//FIXME import classes
import ggc.core.exception.NoSuchPartnerException;

/**
 * Show all transactions for a specific partner.
 */
class DoShowPartnerAcquisitions extends Command<WarehouseManager> {

  DoShowPartnerAcquisitions(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER_ACQUISITIONS, receiver);
    
    
    addStringField("iD", Message.requestPartnerKey());
  }

  @Override
  public void execute() throws CommandException {
    String iD= stringField("iD");

    try{
      _display.popup(_receiver.getPartnerAcquisitions(iD));
    }catch(NoSuchPartnerException nspe){
      throw new UnknownPartnerKeyException(iD);
    }
  }

}
