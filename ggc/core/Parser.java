package ggc.core;

import java.io.IOException;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.Serializable;

import ggc.core.exception.BadEntryException;
import ggc.core.exception.NoSuchPartnerException;
import ggc.core.exception.NoSuchProductException;
import ggc.core.exception.PartnerAlreadyExistsException;

public class Parser implements Serializable {
  private static final long serialVersionUID = 2210992006L;

  private Warehouse _store;

  public Parser(Warehouse w) {
    _store = w;
  }

  void parseFile(String filename) throws IOException, BadEntryException {
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      String line;

      while ((line = reader.readLine()) != null)
        parseLine(line);
    }
  }

  private void parseLine(String line) throws BadEntryException, BadEntryException {
    String[] components = line.split("\\|");

    switch (components[0]) {
    case "PARTNER":
      parsePartner(components, line);
      break;
    case "BATCH_S":
      parseSimpleProduct(components, line);
      break;

    case "BATCH_M":
      parseAggregateProduct(components, line);
      break;

    default:
      throw new BadEntryException("Invalid type element: " + components[0]);
    }
  }

  // PARTNER|id|nome|endereço
  private void parsePartner(String[] components, String line) throws BadEntryException {
    if (components.length != 4)
      throw new BadEntryException("Invalid partner with wrong number of fields (4): " + line);

    String id = components[1];
    String name = components[2];
    String address = components[3];

    // register partner with id, name, address in _store;
    try {
      _store.setPartner(name, address, id);
    } catch (PartnerAlreadyExistsException paee) {
      throw new BadEntryException(id, paee);
    }
  }

  // BATCH_S|idProduto|idParceiro|prec ̧o|stock-actual
  private void parseSimpleProduct(String[] components, String line) throws BadEntryException {
    if (components.length != 5)
      throw new BadEntryException("Invalid number of fields (4) in simple batch description: " + line);

    String idProduct = components[1];
    String idPartner = components[2];
    double price = Double.parseDouble(components[3]);
    int stock = Integer.parseInt(components[4]);

    try {
      _store.getProduct(idProduct);
    } catch (NoSuchProductException nspe) {
      Product sp = new SimpleProduct(idProduct);
      _store.setProduct(sp);
    }

    try {
      Partner partner = _store.getPartner(idPartner);
      Product product = _store.getProduct(idProduct);

      // add batch with price, stock and partner to product
      Batch batch = new Batch(price, stock, partner, product);

      _store.addBatchToProduct(batch);
      _store.addBatchToPartner(batch);
      product.setMaxPrice(price);
      product.setMinPrice(price);

      _store.setObserversNotification(batch);

    } catch (NoSuchProductException nspe) {
      throw new BadEntryException(idProduct, nspe);
    } catch (NoSuchPartnerException nspe) {
      throw new BadEntryException(idPartner, nspe);
    }
  }

  // BATCH_M|idProduto|idParceiro|prec
  // ̧o|stock-actual|agravamento|componente-1:quantidade-1#...#componente-n:quantidade-n
  private void parseAggregateProduct(String[] components, String line) throws BadEntryException {
    if (components.length != 7)
      throw new BadEntryException("Invalid number of fields (7) in aggregate batch description: " + line);

    String idProduct = components[1];
    String idPartner = components[2];
    double price = Double.parseDouble(components[3]);
    int stock = Integer.parseInt(components[4]);

    try {
      _store.getProduct(idProduct);
    } catch (NoSuchProductException nspe) {
      ArrayList<Product> products = new ArrayList<>();
      ArrayList<Integer> quantities = new ArrayList<>();

      for (String component : components[6].split("#")) {
        String[] recipeComponent = component.split(":");
        try {
          products.add(_store.getProduct(recipeComponent[0]));
        } catch (NoSuchProductException nspe1) {
          throw new BadEntryException(recipeComponent[0], nspe);
        }
        quantities.add(Integer.parseInt(recipeComponent[1]));
      }

      // register in _store aggregate product with idProduct,
      // aggravation=Double.parseDouble(components[5])
      // and recipe given by products and quantities);

      ArrayList<Component> recipeComponents = new ArrayList<>();

      for (int i = 0; i < products.size(); i++) {
        Component component = new Component(products.get(i), quantities.get(i));
        recipeComponents.add(component);
      }

      Double alpha = Double.parseDouble(components[5]);
      Recipe recipe = new Recipe(alpha, recipeComponents);

      Product ap = new AggregateProduct(idProduct, recipe);
      _store.setProduct(ap);
    }
    try {
      Product product = _store.getProduct(idProduct);
      Partner partner = _store.getPartner(idPartner);

      // add batch with price, stock and partner to product
      Batch batch = new Batch(price, stock, partner, product);
      _store.addBatchToProduct(batch);
      _store.addBatchToPartner(batch);
      product.setMaxPrice(price);
      product.setMinPrice(price);

      // Send Notification

      _store.setObserversNotification(batch);

    } catch (NoSuchProductException nspe) {
      throw new BadEntryException(idProduct, nspe);
    } catch (NoSuchPartnerException nspe) {
      throw new BadEntryException(idPartner, nspe);
    }
  }

}
