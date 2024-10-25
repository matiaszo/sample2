package com.desktopapp;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import java.util.ArrayList;



public class PhisicsController implements Initializable{
    public static Scene CreateScene() throws Exception
    {
        URL sceneUrl = PhisicsController.class.getResource("PhisicsScreen.fxml");
        FXMLLoader loader = new FXMLLoader(sceneUrl);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        return scene;
    }
    private ArrayList<Body> bodies = new ArrayList<>();
    private ArrayList<Spring> springs = new ArrayList<>();
    
    final double Loss = 0.99;

    @FXML
    private VBox box;

    @FXML
    private Canvas canvas;

    @FXML
    private AnchorPane anchor;

    final double T = 0.03;
    final double G = 980;

    Timer timer = new Timer();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        int ballsQtt = 30;

        for(int i = 0; i < ballsQtt; i++){
            if (i < ballsQtt / 2) {
                bodies.add(new Body(i, i, 20, i, i, 10, 0, 0, 0, 0));        
            }else{
                bodies.add(new Body(i, i, 800, i, i, 10, 0, 0, 0, 0));
            }
        }

        for(int i = 0; i < bodies.size() - 1; i++){
            for(int j = i + 1; j < bodies.size(); j++){
                Body b1 = bodies.get(i);
                Body b2 = bodies.get(j);
                springs.add(new Spring(0, 120, 0, b1, b2));
            }
        }
    

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run(){
                draw();
                box.requestLayout();
            }
        }, 30, 30);
    }

    public void calcVelY(Body body){
        double v = body.getV0Y() + G * T + body.getaY();
        body.setvY(v);
    }

    public void calcVelX(Body body){
        double v = body.getV0X() + body.getaX();
        body.setvX(v);
    }

    public void calcPosY(Body body){
        body.setPosY(body.getPosY() + (body.getvY() * T));

        if (body.getPosY() > (canvas.getHeight() - body.getD())) {
            body.setvY(body.getvY() * - Loss);
            body.setPosY(canvas.getHeight() - body.getD());
        }
    }

    public void calcPosX(Body body){
        body.setPosX(body.getPosX() + (body.getvX() * T));

        if (body.getPosX() > (canvas.getWidth() - body.getD())) {
            body.setvX(body.getvX() * - Loss);
            body.setPosX(canvas.getWidth() - body.getD());
        }

        if (body.getPosX() < 0) {
            body.setvX(body.getvX() * - Loss);
            body.setPosX(0);
        }
    }

    public void setSpringX(Spring spring){
        double a = Math.pow((spring.getB1().getPosX() - spring.getB2().getPosX()), 2);
        double b = Math.pow((spring.getB1().getPosY() - spring.getB2().getPosY()), 2);

        double c = Math.sqrt(a + b);

        spring.setX(c - spring.getLenght());

    }

    public void setForce(Spring spring){
        double k = spring.getK() * - 1;
        double x = spring.getX();
        spring.setForce(k * x);
    }

    public void setAcceleration(Spring spring){
        double accelerationB1 = spring.getForce() / spring.getB1().getMass();
        double accelerationB2 = spring.getForce() / spring.getB2().getMass();

        double co = spring.getB1().getPosY() - spring.getB2().getPosY();
        double ca = spring.getB1().getPosX() - spring.getB2().getPosX();

        double angle = Math.atan2(co, ca);

        double sinAngle = Math.sin(angle);
        double cosAngle = Math.cos(angle);
        
        double aX1 = accelerationB1 * cosAngle;
        double aY1 = accelerationB1 * sinAngle;

        double aX2 = accelerationB2 * cosAngle;
        double aY2 = accelerationB2 * sinAngle;

        spring.getB1().setaX(aX1);
        spring.getB1().setaY(aY1);

        spring.getB2().setaX(aX2 * -1);
        spring.getB2().setaY(aY2* -1);
    }

    private void draw() {


        for (Spring spring : springs) {
            setSpringX(spring);
            setForce(spring);
            setAcceleration(spring);
        }

        for (Body body : bodies) {

            body.setV0Y(body.getvY());
            body.setV0X(body.getvX());

            calcVelY(body);
            calcPosY(body);
            calcVelX(body);
            calcPosX(body);
        }
       
        GraphicsContext g = canvas.getGraphicsContext2D();

        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
        g.setFill(Color.BLUE);

        for (Body body : bodies) { 
            g.fillArc(body.getPosX(), body.getPosY(), body.getD(), body.getD(), 0f, 360, ArcType.ROUND);
        }
        
        
        for(int i = 0; i < bodies.size() - 1; i++){
            for(int j = i + 1; j < bodies.size(); j++){
                Body b1 = bodies.get(i);
                Body b2 = bodies.get(j);

                g.strokeLine(b1.getPosX() + b1.getD() / 2, b1.getPosY() + b1.getD() / 2, b2.getPosX() + b2.getD() / 2 , b2.getPosY() + b2.getD() / 2);
            }
        }
    }
}
