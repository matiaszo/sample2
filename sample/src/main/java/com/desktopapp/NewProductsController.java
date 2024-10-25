package com.desktopapp;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import com.desktopapp.model.Product;

import jakarta.persistence.TypedQuery;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class NewProductsController {
    public static Scene CreateScene(Integer id) throws Exception{
            URL sceneUrl = NewProductsController.class.getResource("NewProductsScreen.fxml");
            FXMLLoader loader = new FXMLLoader(sceneUrl);
            Parent root = loader.load();
            Scene scene = new Scene(root);

            NewProductsController controller = loader.getController();
            controller.setId(id);

            controller.tableProducts.setEditable(true);
            
            controller.nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
            controller.priceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
            controller.quantityColumn.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
            controller.editColumn.setCellValueFactory(new PropertyValueFactory<>("editBtn"));
            controller.loadProducts();
            return scene;
    }

    @FXML
    protected TextField nameNewProduct;

    @FXML
    protected TextField priceNewProduct;

    @FXML
    protected TextField quantityNewProduct;

    @FXML
    protected TableView<Product> tableProducts;

    @FXML
    protected TableColumn<Product, String> nameColumn;

    @FXML
    protected TableColumn<Product, String> priceColumn;

    @FXML
    protected TableColumn<Product, String> quantityColumn;

    @FXML
    protected TableColumn<Product, String> editColumn;

    private Integer id;
    public void setId(Integer id){
        this.id = id;
    }

    @FXML
    public void createNewProduct(){
        String newProductName = nameNewProduct.getText();
        String newProductPrice = priceNewProduct.getText();
        String newProductQuantity = quantityNewProduct.getText();

        if(newProductName.length() == 0 || newProductPrice.length() == 0 || newProductQuantity.length() == 0){
            Alert alert = new Alert (AlertType.ERROR, "Insira o nome, preço e quantidade.", ButtonType.OK);
            alert.setHeaderText("Preencha todos os campos.");
            alert.showAndWait();
            return;
        }

        Product product = new Product();
        product.setName(newProductName);
        product.setPrice(newProductPrice);
        product.setQuantity(newProductQuantity);

        Context ctx = new Context();
        ctx.begin();
        ctx.save(product);
        ctx.commit();

        loadProducts();
    }

    @FXML 
    public void loadProducts(){
        Context ctx = new Context();

        TypedQuery<Product> query = ctx.createQuery(Product.class, "SELECT p from Product p");
        List<Product> products = query.getResultList();
        
        List<ProductAction> productsAction = products.stream()
        .map(product -> new ProductAction(product, this))
        .collect(Collectors.toList());

        tableProducts.setItems(FXCollections.observableArrayList(productsAction));
    }
    
    @FXML
    public void openEditProduct() throws Exception{
        TableView.TableViewSelectionModel selectionModel = tableProducts.getSelectionModel();
        if (selectionModel.isEmpty()) {
            Alert alert = new Alert (AlertType.ERROR, "Você precisa selecionar um produto!", ButtonType.OK);
            alert.setHeaderText("Selecione um produto.");
            alert.showAndWait();
            return;
        }

        Product selectedProduct = (Product) selectionModel.getSelectedItem();


        Stage crrStage = (Stage)nameNewProduct.getScene().getWindow();
        
        Scene newScene = EditProductController.CreateScene(selectedProduct, crrStage);
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.show();
    }

    @FXML
    public void openDeleteModal(){
        TableView.TableViewSelectionModel<Product> selectionModel = tableProducts.getSelectionModel();

        if (selectionModel.isEmpty()) {
            Alert alert = new Alert (AlertType.ERROR, "Você precisa selecionar um produto!", ButtonType.OK);
            alert.setHeaderText("Selecione um produto.");
            alert.showAndWait();
            return;
        }
        ProductAction selectedProduct = (ProductAction)selectionModel.getSelectedItem();
        Product anotherProduct = new Product();
        anotherProduct.setId(selectedProduct.getId());
        anotherProduct.setName(selectedProduct.getName());
        anotherProduct.setPrice(selectedProduct.getPrice());
        anotherProduct.setQuantity(selectedProduct.getQuantity());

        ButtonType btnOk = ButtonType.OK;
        ButtonType btnCancel = ButtonType.CANCEL;

        Alert alert = new Alert (AlertType.CONFIRMATION, "Tem certeza que deseja excluir o usuario?", btnOk, btnCancel);
        alert.showAndWait()
        .filter(btnType-> btnType.equals(ButtonType.OK))
        .ifPresent(res -> {
            Context ctx = new Context();
            ctx.begin();
            ctx.delete(anotherProduct);
            loadProducts();
        }
        );
    }

}
