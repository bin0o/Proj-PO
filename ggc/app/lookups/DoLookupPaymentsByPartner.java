package ggc.app.lookups;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.WarehouseManager;
//FIXME import classes
import ggc.core.exception.NoSuchPartnerException;

/**
 * Lookup payments by given partner.
 */
public class DoLookupPaymentsByPartner extends Command<WarehouseManager> {

  public DoLookupPaymentsByPartner(WarehouseManager receiver) {
    super(Label.PAID_BY_PARTNER, receiver);
    
    
    addStringField("iD", Message.requestPartnerKey());
    
  }

  @Override
  public void execute() throws CommandException {
    String iD=stringField("iD");
    try{
      _display.popup(_receiver.getPartnerPayments(iD));
    }catch(NoSuchPartnerException nspe){
      throw new UnknownPartnerKeyException(iD);
    }
  }
}
