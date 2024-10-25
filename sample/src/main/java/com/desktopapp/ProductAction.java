package com.desktopapp;

import com.desktopapp.model.Product;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ProductAction extends Product{
    private Button editBtn;

    public void setEditBtn(Button editBtn) {
        this.editBtn = editBtn;
    }

    public Button getEditBtn() {
        return editBtn;
    }

    public ProductAction(Product product, NewProductsController controller) {
        this.setId(product.getId());
        this.setName(product.getName());
        this.setPrice(product.getPrice());
        this.setQuantity(product.getQuantity());

        this.editBtn = new Button("Edit");

        this.editBtn.setOnAction((ActionEvent event) -> {
     
        try {
            Stage crrStage = (Stage)editBtn.getScene().getWindow();
            Scene newScene = EditProductController.CreateScene(product, crrStage);
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        });
    }

    
}
