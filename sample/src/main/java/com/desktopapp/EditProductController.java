package com.desktopapp;

import java.net.URL;
import java.util.List;

import com.desktopapp.model.Product;

import jakarta.persistence.TypedQuery;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditProductController {
    public static Scene CreateScene(Product product, Stage oldStage) throws Exception{
            URL sceneUrl = NewProductsController.class.getResource("EditProductScreen.fxml");
            FXMLLoader loader = new FXMLLoader(sceneUrl);
            Parent root = loader.load();
            Scene scene = new Scene(root);

            EditProductController controller = loader.getController();


        controller.setProduct(product);
        controller.setOldStage(oldStage);

        controller.nameProductEdit.setText(product.getName());
        controller.priceProductEdit.setText(product.getPrice());
        controller.quantityProductEdit.setText(product.getQuantity());

        return scene;
    }

    private Stage oldStage;
    public void setOldStage(Stage oldScene) {
        this.oldStage = oldScene;
    }
    

    @FXML
    protected TextField nameProductEdit;

    @FXML
    protected TextField priceProductEdit;

    @FXML
    protected TextField quantityProductEdit;


    private Product product;
    public void setProduct(Product user) {
        this.product = user;
    }

    @FXML
    public void commitChanges() throws Exception{
        String newName = nameProductEdit.getText();
        String newPrice = priceProductEdit.getText();
        String newQuantity = quantityProductEdit.getText();

        product.setName(newName);
        product.setPrice(newPrice);
        product.setQuantity(newQuantity);

        Context ctx = new Context();

        ctx.begin();
        ctx.update(product);
        
        oldStage.close();

        Stage crrstage = (Stage)nameProductEdit.getScene().getWindow();
        Scene newScene = NewProductsController.CreateScene(5 + 1);

        crrstage.setScene(newScene);
    }


    
}
