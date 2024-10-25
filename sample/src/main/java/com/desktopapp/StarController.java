// package com.desktopapp;

// import java.util.ResourceBundle;

// import javafx.fxml.FXML;
// import javafx.fxml.FXMLLoader;
// import javafx.fxml.Initializable;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.scene.canvas.Canvas;
// import javafx.scene.layout.AnchorPane;
// import javafx.scene.layout.VBox;
// import java.util.Timer;
// import java.util.TimerTask;
// import java.net.URL;

// public class StarController implements Initializable {
//      public static Scene CreateScene() throws Exception
//     {
//         URL sceneUrl = StarController.class.getResource("CanvaScreen.fxml");
//         FXMLLoader loader = new FXMLLoader(sceneUrl);
//         Parent root = loader.load();
//         Scene scene = new Scene(root);
        
//         StarController controller = loader.getController();
//         // controller.setId(id);
//         return scene;
//     } 
    
//     Timer timer = new Timer();

//     @Override
//     public void initialize(URL arg, ResourceBundle arg1){
//         timer.scheduleAtFixedRate(new TimerTask() {
//             @Override
//             public void run(){
//                 rotation += Math.PI / 20;
//                 draw();
//                 vBox.requestLayout();
//             }
//         }, 50, 50);
//     }

//     @FXML
//     private AnchorPane anchor;

//     @FXML
//     private Canvas canva;

//     @FXML
//     private VBox vBox;

//     @FXML
//     public void interact(){

//     }

//     @FXML
//     public void pressed(){
        
//     }

//     @FXML
//     public void released(){

//     }

    

//     double rotation = 0;
//     private void draw(){
//         var g = canva.getGraphicsContext2D();

        
//         double [] xs = new double[10];
//         double [] ys = new double[10];
//         double theta = 0f;
        
//         for(int i = 0; i < 10; i++){
//             double rho = i % 2 == 0 ? 200: 70;
            
//             xs[i] = rho * Math.cos(theta);
//             ys[i] = rho * Math.sin(theta);
            
//             theta += 2 * Math.PI / 10;
//         }
        
//         for(int i = 0; i < 10; i++){
//             var x = xs[i];
//             var y = ys[i];
            
//             xs[i] = x * Math.cos(rotation) + y * Math.sin(rotation);
//             ys[i] = x * Math.sin(rotation) - y * Math.cos(rotation);
//         }
//         for(int i = 0; i < 10; i++){
//             xs[i] += canva.getWidth() / 2;
//             ys[i] += canva.getHeight() / 2;
//         }
//         g.clearRect(0, 0, canva.getWidth(), canva.getHeight());
//         g.fillPolygon(xs, ys, 10);
//     }
// }
