package ggc.app.partners;

import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.WarehouseManager;
//FIXME import classes
import ggc.core.exception.NoSuchPartnerException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Show partner.
 */
class DoShowPartner extends Command<WarehouseManager> {

  DoShowPartner(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER, receiver);
    addStringField("iD", Message.requestPartnerKey());
  }

  @Override
  public void execute() throws CommandException {
    String iD= stringField("iD");
    try{
    _display.popup(_receiver.getPartner(iD));
    _display.popup(_receiver.showPartnerNotifications(_receiver.getPartner(iD)));
    }catch(NoSuchPartnerException nspe){
      throw new UnknownPartnerKeyException(iD);
    }
  }
}
