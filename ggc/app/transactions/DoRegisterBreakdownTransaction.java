package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;
import ggc.app.exception.UnavailableProductException;

import ggc.core.WarehouseManager;

import ggc.core.exception.NoSuchPartnerException;
import ggc.core.exception.NoSuchProductException;
import ggc.core.exception.InsuficientProductQuantityException;

/**
 * Register order.
 */
public class DoRegisterBreakdownTransaction extends Command<WarehouseManager> {

  public DoRegisterBreakdownTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_BREAKDOWN_TRANSACTION, receiver);
    addStringField("partnerId", Message.requestPartnerKey());
    addStringField("productId", Message.requestProductKey());
    addIntegerField("quantity", Message.requestAmount());
  }

  @Override
  public final void execute() throws CommandException {
    
    String partnerId = stringField("partnerId");
    String productId = stringField("productId");
    int quantity = integerField("quantity");

    try{
      _receiver.registerBreakdown(_receiver.getPartner(partnerId), _receiver.getProduct(productId), quantity);
    } catch (NoSuchPartnerException e1){
      throw new UnknownPartnerKeyException(partnerId);
    } catch (NoSuchProductException e2){
      throw new UnknownProductKeyException(productId);
    } catch (InsuficientProductQuantityException e3){
      throw new UnavailableProductException(productId, quantity, e3.getAvailableQuantity());
    }

  }

}
