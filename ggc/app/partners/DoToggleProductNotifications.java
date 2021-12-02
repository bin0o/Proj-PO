package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;

import ggc.core.WarehouseManager;
import ggc.core.exception.NoSuchPartnerException;
import ggc.core.exception.NoSuchProductException;

/**
 * Toggle product-related notifications.
 */
class DoToggleProductNotifications extends Command<WarehouseManager> {

  DoToggleProductNotifications(WarehouseManager receiver) {
    super(Label.TOGGLE_PRODUCT_NOTIFICATIONS, receiver);

    addStringField("partnerId", Message.requestPartnerKey());
    addStringField("productId", Message.requestProductKey());
  }

  @Override
  public void execute() throws CommandException {
    
    String partnerId = stringField("partnerId");
    String productId = stringField("productId");

    try{
      _receiver.toggleProductNotification(_receiver.getPartner(partnerId), _receiver.getProduct(productId));
    } catch (NoSuchPartnerException e1) {
      throw new UnknownPartnerKeyException(partnerId);
    } catch (NoSuchProductException e2) {
        throw new UnknownProductKeyException(productId);
    }
  }
}
