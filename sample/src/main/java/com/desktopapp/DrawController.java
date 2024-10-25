// package com.desktopapp;

// import javafx.fxml.FXML;
// import javafx.fxml.FXMLLoader;
// import javafx.fxml.Initializable;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.scene.canvas.Canvas;
// import javafx.scene.input.MouseEvent;
// import javafx.scene.layout.AnchorPane;
// import javafx.scene.layout.VBox;
// import javafx.scene.paint.Color;
// import javafx.scene.shape.ArcType;

// import java.net.URL;
// import java.util.ResourceBundle;
// import java.util.ArrayList;


// public class DrawController implements Initializable{
//         public static Scene CreateScene() throws Exception
//     {
//         URL sceneUrl = DrawController.class.getResource("CanvaScreen.fxml");
//         FXMLLoader loader = new FXMLLoader(sceneUrl);
//         Parent root = loader.load();
//         Scene scene = new Scene(root);
        
//         DrawController controller = loader.getController();
//         // controller.setId(id);
//         return scene;
//     } 
    
//     @FXML
//     private AnchorPane anchor;

//     @FXML
//     private Canvas canva;

//     @FXML
//     private VBox vBox;

//     private ArrayList<Float> values = new ArrayList<>();
//     private ArrayList<Color> colors = new ArrayList<>();
//     private int selected = -1;

//     public void add(Float value, Color color){
//         this.values.add(value);
//         this.colors.add(color);
//     }

//     @Override
//     public void initialize(URL arg0, ResourceBundle arg1){
//         add(60f, Color.GREEN);
//         add(40f, Color.BLUE);
//         add(50f, Color.RED);
//         // add(4f, Color.ROSYBROWN);
//         draw();
//     }

//     @FXML
//     private void interact(MouseEvent e){
//         double widht = canva.getWidth();
//         double height = canva.getHeight();
//         double total = values.stream().reduce(0f, (a, x) -> a + x);

//         double cx = widht / 2;
//         double cy = height / 2;

//         double disx = e.getX() - cx;
//         double disy = e.getY() - cy;

//         double angle = 180 * Math.atan2(disy, -disx) / Math.PI + 180;

//         double distance = Math.sqrt(disx * disx + disy * disy);

//         if (distance > widht / 2) {
//             selected = -1;
//             draw();
//             vBox.requestLayout();
//             return;
//         }

//         double currentArc = 0;
//         for(int i = 0; i < values.size(); i++){
//             Float value = values.get(i);
//             double arc = 360 * value / total;
//             double initialAngle = currentArc;
//             double finalAngle = currentArc + arc;
//             currentArc = finalAngle;

            
//             if (angle > initialAngle && angle < finalAngle) {
//                 selected = i;
//             }

//             currentArc+=arc;
//         }
//         draw();
//         vBox.requestLayout();
//     }
//     @FXML
//     private void pressed(MouseEvent e){

//     }
//     @FXML
//     private void released(MouseEvent e){

//     }

//     private void draw(){
//         var g = canva.getGraphicsContext2D();

//         double widht = canva.getWidth();
//         double height = canva.getHeight();
//         double total = values.stream().reduce(0f, (a, x) -> a + x);

//         double currentArc = 0;

//         for(int i = 0; i < values.size(); i++){
//             Float value = values.get(i);
//             Color color = colors.get(i);

//             double arc = 360 * value / total;
//             if (selected == i) {
//                 color = new Color(0.6 * color.getGreen() + 0.4, 0.6 * color.getBlue() + 0.4, 0.6 * color.getRed() + 0.4, 1f);
//             }

//             g.setFill(color);
//             g.fillArc(0, 0, widht, height, currentArc, arc, ArcType.ROUND);

//             currentArc+=arc;
//         }
//     }

    
// }

