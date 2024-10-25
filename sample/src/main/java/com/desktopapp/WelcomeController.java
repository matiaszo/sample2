package com.desktopapp;

import java.net.URL;

import com.desktopapp.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class WelcomeController {
    public static Scene CreateScene(Integer id, Long userId) throws Exception
    {
        URL sceneUrl = WelcomeController.class.getResource("WelcomeScreen.fxml");
        FXMLLoader loader = new FXMLLoader(sceneUrl);
        Parent root = loader.load();
        Scene scene = new Scene(root);

        WelcomeController controller = loader.getController();
        controller.setId(id);

        Context ctx = new Context();
        User user = ctx.find(User.class, userId);
        
        controller.setUserName(user.getName());
        controller.setUserPw(user.getPassword());
        controller.setData();
        return scene;
    }

    private Integer id;
    public void setId(Integer id){
        this.id = id;
    }
    private String userNameData;
    public void setUserName(String userNameData){
        this.userNameData = userNameData;
    }

    private String userPwData;
    public void setUserPw(String userPwData){
        this.userPwData = userPwData;
    }

    public void setData(){
        userName.setText(userNameData);
        userPw.setText(userPwData);
    }

    @FXML
    protected Label userName;


    @FXML
    protected Label userPw;

    @FXML
    protected PasswordField pwNewUser;

    @FXML
    protected TextField nameNewUser;

    @FXML
    protected TextField ageField;

    @FXML
    protected TextField userNameField;

    @FXML
    protected TextField pwField;

    @FXML
    protected Slider slider;

    @FXML
    protected Button createUserBtn;

    @FXML
    protected Button goToproductsBtn;

    
    
    @FXML
    public void createNewUser(ActionEvent event){

        String newUserName = userNameField.getText();

        String newUserPw = pwField.getText();

        User user = new User();
        user.setName(newUserName);
        user.setPassword(newUserPw);


        Context ctx = new Context();
        ctx.begin();
        ctx.save(user);
        ctx.commit();
    }

    @FXML
    public void goToProducts(ActionEvent event) throws Exception{
        Stage crrStage = (Stage)goToproductsBtn.getScene().getWindow();
        Scene newScene = NewProductsController.CreateScene(id + 1);
        crrStage.setScene(newScene);
    }


    @FXML 
    public void setAge(){

        float userAgeNumber = 0;
        try {
            userAgeNumber = Float.parseFloat(ageField.getText());
            
        } catch (Exception e) {
            userAgeNumber = 0;
        }
        
        slider.setValue(userAgeNumber);
    }

    @FXML
    public void setFieldAge(){
        int value = ((int)slider.getValue());
        
        ageField.setText(String.valueOf(value));
    }
}
