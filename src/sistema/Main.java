/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primary_stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml")); 
        Scene scene = new Scene(root);   
        primary_stage.setScene(scene);
        primary_stage.setTitle("Las Aguilas");
        primary_stage.setResizable(false);
        primary_stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}