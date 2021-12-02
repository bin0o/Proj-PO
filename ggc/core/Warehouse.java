package ggc.core;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ggc.core.exception.BadEntryException;
import ggc.core.exception.ImportFileException;
import ggc.core.exception.InsuficientProductQuantityException;
import ggc.core.exception.InvalidDayNumber;
import ggc.core.exception.NoSuchPartnerException;
import ggc.core.exception.NoSuchProductException;
import ggc.core.exception.NoSuchTransactionException;
import ggc.core.exception.PartnerAlreadyExistsException;

/**
 * Class Warehouse implements a warehouse.
 */
public class Warehouse implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202109192006L;
  private Date _date = new Date();
  private int _nextTransactionId = 0;
  private double _availableBalance;
  private double _accountingBalance;

  private Map<String, Product> _products = new HashMap<>();
  private Map<String, Partner> _partners = new HashMap<>();

  private Map<Integer, Transaction> _transactions = new HashMap<>();

  Warehouse() {
    // Empty.
  };

  // --------------------------- DATE METHODS ---------------------------

  void advanceDate(int numberDays) throws InvalidDayNumber {
    _date.add(numberDays);
  }

  Date getDate() {
    return _date;
  }

  int displayDate() {
    return _date.getDays();
  }

  // --------------------------- PARTNER METHODS ---------------------------

  Partner getPartner(String id) throws NoSuchPartnerException {
    if (!_partners.containsKey(id.toLowerCase()))
      throw new NoSuchPartnerException();
    return _partners.get(id.toLowerCase());
  }

  Collection<Partner> getPartners() {
    List<Partner> orderedPartners = new ArrayList<>(_partners.values());

    Collections.sort(orderedPartners, Partner.getComparatorById());
    return orderedPartners;
  }

  Collection<Transaction> getPartnerPayments(String iD) throws NoSuchPartnerException {
    List<Transaction> partnerPayments = new ArrayList<>(getPartner(iD).getPaidSales());

    Collections.sort(partnerPayments, Transaction.getComparator());
    return partnerPayments;
  }

  String showPartnerNotifications(Partner partner) {
    return partner.showNotifications();
  }

  void setPartner(String name, String address, String id) throws PartnerAlreadyExistsException {
    if (_partners.containsKey(id.toLowerCase())) {
      throw new PartnerAlreadyExistsException();
    }
    Partner partner = new Partner(name, address, id);
    _partners.put(id.toLowerCase(), partner);
    setObserver(partner);
  }

  // --------------------------- PRODUCT METHODS ---------------------------

  boolean productExistsID(String id) {
    return _products.containsKey(id.toLowerCase());
  }

  boolean productExists(Product product) {
    return _products.containsKey(product.getID().toLowerCase());
  }

  boolean verifyProductHasQuantity(Product product, int quantity) throws InsuficientProductQuantityException {
    if (product.getTotalStock() < quantity) {
      throw new InsuficientProductQuantityException(product.getTotalStock());
    }
    return true;
  }

  Product getProduct(String id) throws NoSuchProductException {
    if (!_products.containsKey(id.toLowerCase()))
      throw new NoSuchProductException();

    return _products.get(id.toLowerCase());
  }

  Collection<Product> getProducts() {
    List<Product> orderedProducts = new ArrayList<>(_products.values());

    Collections.sort(orderedProducts, Product.getComparatorById());
    return orderedProducts;
  }

  void setProduct(Product product) {
    _products.put(product.getID().toLowerCase(), product);
    addObservers(product);
  }

  void setSimpleProduct(String productId) {
    Product sp = new SimpleProduct(productId);
    setProduct(sp);
  }

  void setAggregateProduct(String id, int numberOfComponents, double alpha, List<String> componentsId,
      List<Integer> componentsQuantity) {

    ArrayList<Component> recipeComponents = new ArrayList<>();

    for (int i = 0; i < numberOfComponents; i++) {
      Component component = new Component(_products.get(componentsId.get(i).toLowerCase()), componentsQuantity.get(i));
      recipeComponents.add(component);
    }

    Recipe recipe = new Recipe(alpha, recipeComponents);

    Product ap = new AggregateProduct(id, recipe);
    setProduct(ap);

  }

  // --------------------------- BATCH METHODS ---------------------------

  List<Batch> getBatches() {
    Collection<Product> products = getProducts();
    List<Batch> orderedBatches = new ArrayList<>();
    for (Product p : products) {
      if (p.hasBatches()) {
        orderedBatches.addAll(p.getBatches());
      }
    }
    Collections.sort(orderedBatches, Batch.getComparatorBatch());
    return orderedBatches;
  }

  List<Batch> getBatchesUnderGivenPrice(double price) {
    List<Batch> batchesBelowGivenPrice = new ArrayList<>();

    for (Batch batch : getBatches()) {
      if (batch.getPrice() < price) {
        batchesBelowGivenPrice.add(batch);
      }
    }
    return batchesBelowGivenPrice;
  }

  List<Batch> getBatchesByPartner(String id) throws NoSuchPartnerException {
    if (!_partners.containsKey(id.toLowerCase())) {
      throw new NoSuchPartnerException();
    }
    List<Batch> orderedBatches = _partners.get(id.toLowerCase()).getBatches();
    Collections.sort(orderedBatches, Batch.getComparatorBatch());
    return orderedBatches;
  }

  List<Batch> getBatchesByProduct(String id) throws NoSuchProductException {
    if (!_products.containsKey(id.toLowerCase())) {
      throw new NoSuchProductException();
    }
    List<Batch> orderedBatches = _products.get(id.toLowerCase()).getBatches();
    Collections.sort(orderedBatches, Batch.getComparatorBatch());
    return orderedBatches;
  }

  void addBatchToProduct(Batch batch) {
    Product product = batch.getProduct();
    product.addBatch(batch);
  }

  void addBatchToPartner(Batch batch) {
    Partner partner = batch.getPartner();
    partner.addBatch(batch);
  }

  void removeBatchfromProduct(Batch batch) {
    Product product = batch.getProduct();
    product.removeBatch(batch);
  }

  void removeBatchfromPartner(Batch batch) {
    Partner partner = batch.getPartner();
    partner.removeBatch(batch);
  }

  // --------------------------- NOTIFICATION METHODS ---------------------------

  void sendNewNotification(Product product, double price) {
    DeliveryType omission = new OmissionDelivery();
    omission.sendNewNotification(product, price);
  }

  void sendBargainNotification(Product product, double price) {
    DeliveryType omission = new OmissionDelivery();
    omission.sendBargainNotification(product, price);
  }

  void toggleProductNotification(Partner partner, Product product) {
    if (product.getObservers().contains(partner))
      product.removeObserver(partner);
    else
      product.addObserver(partner);
  }

  // --------------------------- OBSERVERS METHODS ---------------------------

  void setObserversNotification(Batch batch) {
    if (productExists(batch.getProduct()) && batch.getProduct().getTotalStock() == 0) {
      sendNewNotification(batch.getProduct(), batch.getPrice());
    } else if (batch.getPrice() < batch.getProduct().getMinPrice()) {
      sendBargainNotification(batch.getProduct(), batch.getPrice());
    }
  }

  void addObservers(Product product) {
    for (Partner partner : getPartners()) {
      product.addObserver(partner);
    }
  }

  void setObserver(Partner partner) {
    for (Product product : getProducts()) {
      product.addObserver(partner);
    }
  }

  // --------------------------- BALANCE METHODS ---------------------------

  int getAvailableBalance() {
    return (int) Math.round(_availableBalance);
  }

  int getAccountingBalance() {
    return (int) Math.round(_accountingBalance);
  }

  void updateAvailableBalance(double value) {
    _availableBalance += value;
  }

  void updateAccountingBalance(double value) {
    _accountingBalance += value;
  }

  // --------------------------- TRANSACTION METHODS ---------------------------

  Transaction getTransaction(int iD) throws NoSuchTransactionException {
    if (!_transactions.containsKey(iD))
      throw new NoSuchTransactionException();
    else if (_transactions.get(iD) instanceof SaleByCredit) {
      SaleByCredit transaction = (SaleByCredit) _transactions.get(iD);
      transaction.setAmountPaid(getAmountPaid(transaction.getDeadline().getDays(), getDate(),
          transaction.getBaseValue(), transaction.getPartner(), transaction.getProduct()));
      return transaction;
    }

    return _transactions.get(iD);
  }

  List<Transaction> getTransactions() {
    List<Transaction> transactions = new ArrayList<>(_transactions.values());
    return transactions;
  }

  List<Sale> getPartnerSales(String iD) throws NoSuchPartnerException {
    return getPartner(iD).getSales();
  }

  List<Acquisition> getPartnerAcquisitions(String iD) throws NoSuchPartnerException {
    return getPartner(iD).getAcquisitions();
  }

  void registerAcquisition(Double baseValue, int quantity, Product product, Partner partner) {
    product.updateMinPrice();

    if (product.getTotalStock() != 0 && baseValue < product.getMinPrice()) {
      sendBargainNotification(product, baseValue);
    }

    else if (product.getMaxPrice() != 0 && product.getTotalStock() == 0) {
      sendNewNotification(product, baseValue);
    }

    // Regista batch no produto
    Batch batch = new Batch(baseValue, quantity, partner, product);
    product.addBatch(batch);
    partner.addBatch(batch);
    product.setMaxPrice(baseValue);

    // Regista compra

    double acquisitionValue = baseValue * quantity;
    Date copyCurrentDate = new Date(_date.getDays());
    Transaction acquisition = new Acquisition(getNextTransactionId(), copyCurrentDate, acquisitionValue, quantity,
        product, partner);

    partner.addAcquisition((Acquisition) acquisition);
    _transactions.put(getNextTransactionId(), acquisition);
    setNextTransactionId();
    updateAvailableBalance(-acquisitionValue);
    updateAccountingBalance(-acquisitionValue);
  }

  int getSaleBaseValue(Product product, int quantity) {

    int updatedQuantity = 0;
    int baseValue = 0;

    while (updatedQuantity < quantity) {
      List<Batch> batchesOrderedByPrice = new ArrayList<>(product.getBatchesOrderedbyPrice());
      if (updatedQuantity + batchesOrderedByPrice.get(0).getQuantity() > quantity) {
        double value = batchesOrderedByPrice.get(0).getPrice() * (quantity - updatedQuantity);
        baseValue += value;
        // Update Batch Quantity
        product.getBatchesOrderedbyPrice().get(0)
            .setQuantity(product.getBatchesOrderedbyPrice().get(0).getQuantity() - (quantity - updatedQuantity));
        break;
      } else {
        double value = batchesOrderedByPrice.get(0).getPrice() * (batchesOrderedByPrice.get(0).getQuantity());
        baseValue += value;
        updatedQuantity += batchesOrderedByPrice.get(0).getQuantity();
        // Erase Batch
        removeBatchfromProduct(batchesOrderedByPrice.get(0));
        removeBatchfromPartner(batchesOrderedByPrice.get(0));
      }

    }
    return baseValue;
  }

  double getAmountPaid(int deadlineDay, Date date, double baseValue, Partner partner, Product product) {
    double amountPaid = 0;
    if (product.getIsSimpleProduct()) {
      amountPaid = partner.getStatus().valueOfPayment(deadlineDay, date, 5, baseValue);
    } else {
      amountPaid = partner.getStatus().valueOfPayment(deadlineDay, date, 3, baseValue);
    }
    return amountPaid;
  }

  void registerSaleByCredit(int quantity, Product product, Partner partner, int deadlineDay)
      throws InsuficientProductQuantityException {
    if (!verifyProductHasQuantity(product, quantity))
      throw new InsuficientProductQuantityException(product.getTotalStock());
    double amountPaid;
    double baseValue = getSaleBaseValue(product, quantity);

    amountPaid = getAmountPaid(deadlineDay, getDate(), baseValue, partner, product);

    Date copyCurrentDate = new Date(_date.getDays());

    Transaction saleByCredit = new SaleByCredit(getNextTransactionId(),copyCurrentDate, baseValue, quantity, product,
        partner, new Date(deadlineDay), amountPaid);
    product.updateMinPrice();
    partner.addSale((Sale) saleByCredit);
    _transactions.put(getNextTransactionId(), saleByCredit);
    setNextTransactionId();
    updateAccountingBalance(amountPaid);

  }

  void registerBreakdown(Partner partner, Product product, int quantity) throws InsuficientProductQuantityException {
    if (!verifyProductHasQuantity(product, quantity))
      throw new InsuficientProductQuantityException(product.getTotalStock());

  }

  void payTransaction(int iD) throws NoSuchTransactionException {
    if (!_transactions.containsKey(iD))
      throw new NoSuchTransactionException();
    else if (_transactions.get(iD) instanceof SaleByCredit && !_transactions.get(iD).isPaid()) {
      double amountPaid = 0;
      SaleByCredit transaction = (SaleByCredit) _transactions.get(iD);
      Partner partner = transaction.getPartner();
      int deadlineDay = transaction.getDeadline().getDays();
      Date now = new Date(_date.getDays());
      Double price = transaction.getBaseValue();
      Product product = transaction.getProduct();
      transaction.setPaymentDate(now);

      amountPaid = getAmountPaid(deadlineDay, now, price, partner, product);
      transaction.setAmountPaid(amountPaid);

      partner.setPoints(partner.getStatus().getPoints(deadlineDay, now, price, partner));
      partner.updateStatus();
      transaction.setIsPaid();
      updateAvailableBalance(amountPaid);
    }
  }

  int getNextTransactionId() {
    return _nextTransactionId;
  }

  void setNextTransactionId() {
    _nextTransactionId++;
  }

  // -----------------------------------------------------------------------------------

  /**
   * @param txtfile filename to be loaded.
   * @throws IOException
   * @throws BadEntryException
   * @throws ImportFileException
   */
  void importFile(String txtfile) throws IOException, BadEntryException, ImportFileException {
    try {
      Parser parser = new Parser(this);
      parser.parseFile(txtfile);
    } catch (IOException | BadEntryException e1) {
      throw new ImportFileException(txtfile, e1);
    }
  }
}
