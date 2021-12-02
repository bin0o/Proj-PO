package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.io.FileNotFoundException;
import java.io.IOException;

import ggc.app.exception.FileOpenFailedException;
import ggc.core.WarehouseManager;
//FIXME import classes
import ggc.core.exception.UnavailableFileException;

/**
 * Open existing saved state.
 */
class DoOpenFile extends Command<WarehouseManager> {

  /** @param receiver */
  DoOpenFile(WarehouseManager receiver) {
    super(Label.OPEN, receiver);
    // FIXME maybe add command fields
    addStringField("filename", Message.openFile());
  }

  @Override
  public final void execute() throws CommandException {
    String filename = stringField("filename");
    try {
      _receiver.load(filename);

    } catch (UnavailableFileException ufe) {
      throw new FileOpenFailedException(ufe.getFilename());

    } catch (ClassNotFoundException e) {
      throw new FileOpenFailedException(filename);

    } catch (FileNotFoundException e2) {
      throw new FileOpenFailedException(filename);

    } catch (IOException e3) {
      throw new FileOpenFailedException(filename);
    }
  }

}
