package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.UnavailableProductException;
import ggc.app.exception.UnknownProductKeyException;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.WarehouseManager;
import ggc.core.exception.InsuficientProductQuantityException;
import ggc.core.exception.NoSuchPartnerException;
//FIXME import classes
import ggc.core.exception.NoSuchProductException;

/**
 * 
 */
public class DoRegisterSaleTransaction extends Command<WarehouseManager> {

  public DoRegisterSaleTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_SALE_TRANSACTION, receiver);
    
    addStringField("partnerID", Message.requestPartnerKey());
    addIntegerField("deadlineDay", Message.requestPaymentDeadline());
    addStringField("productID", Message.requestProductKey());
    addIntegerField("quantity", Message.requestAmount());

  }

  @Override
  public final void execute() throws CommandException {
    String partnerID = stringField("partnerID");
    int deadlineDay = integerField("deadlineDay");
    String productID = stringField("productID");
    int quantity = integerField("quantity");


    try{
      _receiver.registerSaleByCredit(quantity,_receiver.getProduct(productID),_receiver.getPartner(partnerID),deadlineDay);
    }catch(NoSuchProductException e1){
        throw new UnknownProductKeyException(productID);
    }catch(NoSuchPartnerException e2){
      throw new UnknownPartnerKeyException(partnerID);
    }catch(InsuficientProductQuantityException nspqe){
      throw new UnavailableProductException(productID,quantity,nspqe.getAvailableQuantity());
    }
  }
}
