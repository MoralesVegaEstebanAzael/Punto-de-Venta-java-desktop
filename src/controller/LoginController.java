package controller;

import conexion.Conexion;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;


public class LoginController implements Initializable {
    
    ObservableList<String> cargoList = FXCollections.observableArrayList("administrador","vendedor");
    @FXML private TextField tfUsuario;
    @FXML private TextField tfPassword;
    @FXML private VBox etiq_error;
    @FXML private ComboBox<String> cargo; 
    ErrorController er = new ErrorController();
    Conexion cc = new Conexion();
    Connection con = cc.conexion();
    @FXML
    private VBox hboxRecuperar;
    @FXML
    private JFXButton btIngresar;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
       tfUsuario.requestFocus();
       cargo.setValue("administrador");
       cargo.setItems(cargoList);
       cargo.setStyle("-fx-font: 14px \"Roboto\";");
       cargo.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(2),javafx.geometry.Insets.EMPTY)));
       
    }
    @FXML 
    public void detectaTecla(KeyEvent evt){
        etiq_error.setVisible(false);
    }
    @FXML 
    public void iniciar(Event evento) throws IOException{
        init(evento);
    }
    
    @FXML
    private void initKey(KeyEvent event) throws MalformedURLException, IOException {
        if (event.getCode().equals(KeyCode.ENTER)){
            String user_name = tfUsuario.getText();
        String password = tfPassword.getText();
        Statement stmt;
        ResultSet rs;
        try{ 
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id_e,puesto FROM aguilas.empleado WHERE (username  = '"+ user_name +"' and contrasena ='"+ password +"')");   
            if(rs!=null){
                if(rs.next()){
                    if(cargo.getSelectionModel().getSelectedItem().equalsIgnoreCase(rs.getString("puesto"))){
                        ((Node)  (event.getSource())).getScene().getWindow().hide();
                        SistemaController.user_n = user_name;    // pasar el usuario 
                        SistemaController.llave_id_empleado = rs.getInt("id_e"); //pasar el id_empleado
                        
                        //URL url = Paths.get("C:\\Users\\Azael\\Documents\\Sistema\\src\\view\\sistema.fxml").toUri().toURL();
                       // Parent root = FXMLLoader.load(url);
                        Parent root = FXMLLoader.load(getClass().getResource("/view/sistema.fxml"));
                        Stage stage= new Stage();
                        stage.setResizable(false);
                        stage.setScene(new Scene(root));
                        stage.show();
                    }else{
                      er.msgError("Tipo de usuario incorrecto");
                    }
                }else{
                    tfUsuario.setText("");
                    tfPassword.setText("");
                    etiq_error.setVisible(true);   
                }
            }
        }catch(SQLException e){
            message("Error", e.getMessage());
        }
        }
    }
    
    public void init(Event evento) throws MalformedURLException, IOException{
        String user_name = tfUsuario.getText();
        String password = tfPassword.getText();
        Statement stmt;
        ResultSet rs;
        try{ 
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT puesto FROM aguilas.empleado WHERE (username  = '"+ user_name +"' and contrasena ='"+ password +"')");   
            if(rs!=null){
                if(rs.next()){
                    if(cargo.getSelectionModel().getSelectedItem().equalsIgnoreCase(rs.getString("puesto"))){
                        ((Node)  (evento.getSource())).getScene().getWindow().hide();
                        SistemaController.user_n = user_name;
                       // URL url = Paths.get("C:\\Users\\Azael\\Documents\\Sistema\\src\\view\\sistema.fxml").toUri().toURL();
                        //Parent root = FXMLLoader.load(url);
                        Parent root = FXMLLoader.load(getClass().getResource("/view/sistema.fxml"));
                        Stage stage= new Stage();
                        stage.setResizable(false);
                        stage.setScene(new Scene(root));
                        stage.show();
                    }else{
                      er.msgError("Tipo de usuario incorrecto");
                    }
                }else{
                    tfUsuario.setText("");
                    tfPassword.setText("");
                    etiq_error.setVisible(true);   
                }
            }
        }catch(SQLException e){
            message("Error", e.getMessage());
        }
    }
    @FXML 
    public void recuperar_contrasenia(MouseEvent evt){
         try{ 
            Parent root = FXMLLoader.load(getClass().getResource("/view/view_recup_contrasenia.fxml"));
            Stage stage= new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.show();
        }catch(IOException e){}
    }
    public void message(String error,String s) {
        Alert alert = new Alert(AlertType.ERROR);
       
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.initStyle(StageStyle.UTILITY);
        alert.showAndWait();
    }

    
    
}
