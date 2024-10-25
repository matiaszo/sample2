package com.desktopapp;

import com.desktopapp.model.User;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{
    public static void main(String[] args) 
    {
        User user = new User();
        user.setName("newUser");
        user.setPassword("pass");

        Context ctx = new Context();
        ctx.begin();
        ctx.save(user);
        ctx.commit();

        launch(args);
    } 
    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        Scene scene = NewProductsController.CreateScene(1);
        primaryStage.setScene(scene);
        primaryStage.show();    
    }
}
