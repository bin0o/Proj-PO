package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.InvalidDateException;
import ggc.core.WarehouseManager;
import ggc.core.exception.InvalidDayNumber;

//FIXME import classes

/**
 * Advance current date.
 */
class DoAdvanceDate extends Command<WarehouseManager> {

  DoAdvanceDate(WarehouseManager receiver) {
    super(Label.ADVANCE_DATE, receiver);
    //FIXME add command fields

    addIntegerField("numberDays", Message.requestDaysToAdvance());
  }

  @Override
  public final void execute() throws CommandException {
    //FIXME implement command

    Integer numberDays= integerField("numberDays");

    try{
      _receiver.advanceDate(numberDays);
    }catch(InvalidDayNumber idn){
      throw new InvalidDateException(numberDays);
    }
  }

}
