package ggc.app.transactions;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.ArrayList;
import java.util.List;

import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;

import ggc.core.WarehouseManager;
import ggc.core.exception.NoSuchPartnerException;
import ggc.core.exception.NoSuchProductException;

/**
 * Register order.
 */
public class DoRegisterAcquisitionTransaction extends Command<WarehouseManager> {

  public DoRegisterAcquisitionTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_ACQUISITION_TRANSACTION, receiver);

    addStringField("partnerID", Message.requestPartnerKey());
    addStringField("productID", Message.requestProductKey());
    addRealField("price", Message.requestPrice());
    addIntegerField("quantity", Message.requestAmount());
  }

  @Override
  public final void execute() throws CommandException {

    String partnerID = stringField("partnerID");
    String productID = stringField("productID");
    double price = realField("price");
    int quantity = integerField("quantity");

    try {
      if (!_receiver.productExistsID(productID)) {

        Form answer = new Form("answer");
        answer.addStringField("answer", Message.requestAddRecipe());
        answer.parse();

        if (answer.stringField("answer").equals("n")) {
          _receiver.setSimpleProduct(productID);
        } else {
          Form componentsForm = new Form("components");
          componentsForm.addIntegerField("numberOfComponents", Message.requestNumberOfComponents());
          componentsForm.addRealField("alpha", Message.requestAlpha());
          componentsForm.parse();

          Form componentForm = new Form("component");
          componentForm.addStringField("id", Message.requestProductKey());
          componentForm.addIntegerField("quantity", Message.requestAmount());

          List<String> componentsId = new ArrayList<>();
          List<Integer> componentsQuantity = new ArrayList<>();

          for (int i = 0; i < componentsForm.integerField("numberOfComponents"); i++) {
            componentForm.parse();

            try {
              _receiver.getProduct(componentForm.stringField("id"));
              componentsId.add(componentForm.stringField("id"));
              componentsQuantity.add(componentForm.integerField("quantity"));
            } catch (NoSuchProductException e3) {
              throw new UnknownProductKeyException(componentForm.stringField("id"));
            }
          }
          _receiver.setAggregateProduct(productID, componentsForm.integerField("numberOfComponents"),
              componentsForm.realField("alpha"), componentsId, componentsQuantity);
        }
      }
      _receiver.registerAcquisition(price, quantity, _receiver.getProduct(productID), _receiver.getPartner(partnerID));
    } catch (NoSuchPartnerException e1) {
      throw new UnknownPartnerKeyException(partnerID);
    } catch (NoSuchProductException e2) {
      throw new UnknownProductKeyException(productID);
    }
  }
}