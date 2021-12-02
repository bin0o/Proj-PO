package ggc.app.main;

import java.io.IOException;

import ggc.core.WarehouseManager;
//FIXME import classes
import ggc.core.exception.MissingFileAssociationException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Save current state to file under current name (if unnamed, query for name).
 */
class DoSaveFile extends Command<WarehouseManager> {

  /** @param receiver */
  DoSaveFile(WarehouseManager receiver) {
    super(Label.SAVE, receiver);
  }

  @Override
  public final void execute() throws CommandException {
    //FIXME implement command and create a local Form
    
    try {
      _receiver.save();
    } catch (MissingFileAssociationException e) {
        String filename= Form.requestString(Message.newSaveAs());
        try {
          _receiver.saveAs(filename);
        } catch (MissingFileAssociationException | IOException e1) {
            e1.printStackTrace();
        }
      }
      catch(IOException e2){
        e2.printStackTrace();
      }
  }

}
