package com.desktopapp;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.desktopapp.model.User;

import jakarta.persistence.TypedQuery;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainController {

    public static Scene CreateScene(Integer id) throws Exception
    {
        URL sceneUrl = MainController.class.getResource("MainScreen.fxml");
        FXMLLoader loader = new FXMLLoader(sceneUrl);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        MainController controller = loader.getController();
        controller.setId(id);
        return scene;
    }

    private Integer id;
    public void setId(Integer id){
        this.id = id;
    }

    @FXML
    protected void initialize(URL location, ResourceBundle Resources){
        this.btnLog.setText(id.toString());
    }

    @FXML
    protected Button btnLog;

    @FXML
    protected Button btnOut;

    @FXML
    protected TextField loginField;

    @FXML
    protected TextField pwText;

    @FXML
    protected PasswordField pwField;

    @FXML
    protected CheckBox checkBox;


    @FXML
    public void sendData(MouseEvent e) throws Exception{
        Context ctx = new Context();

        TypedQuery<User> query = ctx.createQuery(User.class, "SELECT u from User u WHERE u.name = :name");
        query.setParameter("name", loginField.getText());
        List<User> users = query.getResultList();

        
        String userPw = "";

        if (checkBox.isSelected()) {
            userPw = pwText.getText();
        }else{
            userPw = pwField.getText();
        }   

        if(users.isEmpty()){
            Alert alert = new Alert (AlertType.ERROR, "Usuario não encontrado!!", ButtonType.OK);
            alert.showAndWait();
            return;
        }else{
            User user = users.get(0);
            if (checkBox.isSelected()){
                if(!pwText.getText().equals(user.getPassword())){
                    Alert alert = new Alert(
                    AlertType.ERROR,
                    "Senha incorreta!",
                    ButtonType.OK
                );
                alert.showAndWait();
                return;
                }
    
            }else{
                if(!pwField.getText().equals(user.getPassword())){
                    Alert alert = new Alert(
                    AlertType.ERROR,
                    "Senha incorreta!",
                    ButtonType.OK
                );
                alert.showAndWait();
                return;
                }
            }

        }
        User user = users.get(0);

        long userId = user.getId();

        Stage crrStage = (Stage) btnLog.getScene().getWindow();
        Scene newScene = WelcomeController.CreateScene(id + 1, userId);
        crrStage.setScene(newScene);
    }

    @FXML
    public void changeVisualization(MouseEvent e){
        if (checkBox.isSelected()) {
            pwText.setText(pwField.getText());
            pwText.setVisible(true);
            pwField.setVisible(false);
            return;
        }
        pwField.setText(pwText.getText());
        pwField.setVisible(true);
        pwText.setVisible(false);
    }

    @FXML
    public void setLoginData(InputMethodEvent e) throws Exception{
        
    }

    @FXML
    public void getOut(MouseEvent event) throws Exception{

        Stage crrStage = (Stage) btnOut.getScene().getWindow();
        Scene crrScene = btnOut.getScene();
        
        Stage newStage = new Stage();
        Scene newScene = ButtonController.CreateScene(id + 1,crrScene);
        newStage.setScene(newScene);
        newStage.show();
    }
}
