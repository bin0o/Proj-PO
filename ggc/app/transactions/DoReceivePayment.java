package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.UnknownTransactionKeyException;
import ggc.core.WarehouseManager;
//FIXME import classes
import ggc.core.exception.NoSuchTransactionException;

/**
 * Receive payment for sale transaction.
 */
public class DoReceivePayment extends Command<WarehouseManager> {

  public DoReceivePayment(WarehouseManager receiver) {
    super(Label.RECEIVE_PAYMENT, receiver);
    
    addIntegerField("iD", Message.requestTransactionKey());
  }

  @Override
  public final void execute() throws CommandException {

    int iD= integerField("iD");

    try{
      _receiver.payTransaction(iD);
    } catch (NoSuchTransactionException nste){
      throw new UnknownTransactionKeyException(iD);
    }
  }

}
