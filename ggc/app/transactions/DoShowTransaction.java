package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.UnknownTransactionKeyException;
import ggc.core.WarehouseManager;
import ggc.core.exception.NoSuchTransactionException;

/*
 * Show specific transaction.
 */
public class DoShowTransaction extends Command<WarehouseManager> {

  public DoShowTransaction(WarehouseManager receiver) {
    super(Label.SHOW_TRANSACTION, receiver);
    addIntegerField("iD", Message.requestTransactionKey());
  }

  @Override
  public final void execute() throws CommandException {

    int iD = integerField("iD");
  
    try{
      _display.popup(_receiver.getTransaction(iD));
    } catch( NoSuchTransactionException nstid){
        throw new UnknownTransactionKeyException(iD);
    }
  }
}
