package ggc.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.List;

import ggc.core.exception.BadEntryException;
import ggc.core.exception.ImportFileException;
import ggc.core.exception.InvalidDayNumber;
import ggc.core.exception.UnavailableFileException;
import ggc.core.exception.MissingFileAssociationException;
import ggc.core.exception.InsuficientProductQuantityException;
import ggc.core.exception.NoSuchPartnerException;
import ggc.core.exception.NoSuchProductException;
import ggc.core.exception.NoSuchTransactionException;
import ggc.core.exception.PartnerAlreadyExistsException;

/** Façade for access. */
public class WarehouseManager {

  /** Name of file storing current warehouse. */
  private String _filename = "";

  /** The wharehouse itself. */
  private Warehouse _warehouse = new Warehouse();

  public Date getDate() {
    return _warehouse.getDate();
  }

  public int displayDate() {
    return _warehouse.displayDate();
  }

  public void advanceDate(int numberDays) throws InvalidDayNumber {
    _warehouse.advanceDate(numberDays);
  }

  public int getAvailableBalance() {
    return _warehouse.getAvailableBalance();
  }

  public int getAccountingBalance() {
    return _warehouse.getAccountingBalance();
  }

  public Partner getPartner(String id) throws NoSuchPartnerException {
    return _warehouse.getPartner(id);
  }

  public Collection<Partner> getPartners() {
    return _warehouse.getPartners();
  }

  public Collection<Transaction> getPartnerPayments(String iD) throws NoSuchPartnerException {
    return _warehouse.getPartnerPayments(iD);
  }

  public String showPartnerNotifications(Partner partner) {
    return _warehouse.showPartnerNotifications(partner);
  }

  public boolean productExistsID(String id) {
    return _warehouse.productExistsID(id);
  }

  public Product getProduct(String id) throws NoSuchProductException {
    return _warehouse.getProduct(id);
  }

  public Collection<Product> getProducts() {
    return _warehouse.getProducts();
  }

  public Collection<Batch> getBatchesByPartner(String id) throws NoSuchPartnerException {
    return _warehouse.getBatchesByPartner(id);
  }

  public Collection<Batch> getBatchesByProduct(String id) throws NoSuchProductException {
    return _warehouse.getBatchesByProduct(id);
  }

  public Collection<Batch> getBatchesUnderGivenPrice(double price) {
    return _warehouse.getBatchesUnderGivenPrice(price);
  }

  public Collection<Batch> getBatches() {
    return _warehouse.getBatches();
  }

  public void setPartner(String name, String address, String id) throws PartnerAlreadyExistsException {
    _warehouse.setPartner(name, address, id);
  }

  public void setSimpleProduct(String id) {
    _warehouse.setSimpleProduct(id);
  }

  public void setAggregateProduct(String id, int numberOfComponents, double alpha, List<String> componentsId,
      List<Integer> componentsQuantity) {
    _warehouse.setAggregateProduct(id, numberOfComponents, alpha, componentsId, componentsQuantity);
  }

  public Transaction getTransaction(int iD) throws NoSuchTransactionException {
    return _warehouse.getTransaction(iD);
  }

  public Collection<Sale> getPartnerSales(String iD) throws NoSuchPartnerException {
    return _warehouse.getPartnerSales(iD);
  }

  public Collection<Acquisition> getPartnerAcquisitions(String iD) throws NoSuchPartnerException {
    return _warehouse.getPartnerAcquisitions(iD);
  }

  public void registerAcquisition(Double baseValue, int quantity, Product product, Partner partner) {
    _warehouse.registerAcquisition(baseValue, quantity, product, partner);
  }

  public void registerSaleByCredit(int quantity, Product product, Partner partner, int deadlineDay)
      throws InsuficientProductQuantityException {
    _warehouse.registerSaleByCredit(quantity, product, partner, deadlineDay);
  }

  public void registerBreakdown(Partner partner , Product product, int quantity) throws InsuficientProductQuantityException{
    _warehouse.registerBreakdown(partner, product, quantity);
  }

  public void payTransaction(int iD) throws NoSuchTransactionException {
    _warehouse.payTransaction(iD);
  }

  public void toggleProductNotification(Partner partner, Product product) {
    _warehouse.toggleProductNotification(partner, product);
  }

  /**
   * @@throws IOException
   * @@throws FileNotFoundException
   * @@throws MissingFileAssociationException
   */
  public void save() throws IOException, FileNotFoundException, MissingFileAssociationException {
    if (_filename.equals(""))
      throw new MissingFileAssociationException();
    try (ObjectOutputStream obOut = new ObjectOutputStream(new FileOutputStream(_filename))) {
      obOut.writeObject(_warehouse);
    }
  }

  /**
   * @@param filename
   * @@throws MissingFileAssociationException
   * @@throws IOException
   * @@throws FileNotFoundException
   */
  public void saveAs(String filename) throws MissingFileAssociationException, FileNotFoundException, IOException {
    _filename = filename;
    save();
  }

  /**
   * @@param filename
   * @@throws UnavailableFileException
   * @throws IOException
   * @throws FileNotFoundException
   */
  public void load(String filename)
      throws UnavailableFileException, ClassNotFoundException, FileNotFoundException, IOException {

    if (filename == null)
      throw new UnavailableFileException(filename);
    try (ObjectInputStream abIn = new ObjectInputStream(new FileInputStream(filename))) {
      _warehouse = (Warehouse) abIn.readObject();
      _filename = filename;
    }
  }

  /**
   * @param textfile
   * @throws ImportFileException
   */
  public void importFile(String textfile) throws ImportFileException {
    try {
      _warehouse.importFile(textfile);
    } catch (IOException | BadEntryException bae) {
      throw new ImportFileException(textfile, bae);
    }
  }
}