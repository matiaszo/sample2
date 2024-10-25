package com.desktopapp;

import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ButtonController {
    public static Scene CreateScene(Integer id, Scene mainScene)throws Exception{
        URL sceneUrl = MainController.class.getResource("ButtonScreen.fxml");
        FXMLLoader loader = new FXMLLoader(sceneUrl);
        Parent root = loader.load();
        Scene scene = new Scene(root);

        ButtonController controller = loader.getController();
        controller.setId(id);
        controller.setMainScene(mainScene);
        controller.idScene.setText(String.valueOf(id));

        return scene;
    }

    @FXML
    protected Button buttonClick;

    @FXML
    protected Label idScene;

    private Integer id;
    public void setId(Integer id){
        this.id = id;
    }

    private Scene mainScene;
    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }
    

    @FXML
    public void changeScreen(ActionEvent event) throws Exception{
        Stage crrStage = (Stage) buttonClick.getScene().getWindow();

        Stage newStage = new Stage();
        Scene newScene = MainController.CreateScene(id + 1);
        newStage.setScene(newScene);
        newStage.show();
    }

}
