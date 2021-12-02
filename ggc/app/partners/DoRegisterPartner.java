package ggc.app.partners;

import ggc.app.exception.DuplicatePartnerKeyException;
import ggc.core.WarehouseManager;
//FIXME import classes
import ggc.core.exception.PartnerAlreadyExistsException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Register new partner.
 */
class DoRegisterPartner extends Command<WarehouseManager> {

  DoRegisterPartner(WarehouseManager receiver) {
    super(Label.REGISTER_PARTNER, receiver);
    addStringField("iD",Message.requestPartnerKey());
    addStringField("name",Message.requestPartnerName());
    addStringField("address",Message.requestPartnerAddress());
  }

  @Override
  public void execute() throws CommandException {
    String id = stringField("iD");
    String name = stringField("name");
    String address = stringField("address");
    try{
      _receiver.setPartner(name,address,id);
    } catch( PartnerAlreadyExistsException paexe){
      throw new DuplicatePartnerKeyException(id);
    }
  }
}