/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import project.ConnectionProvider;

/**
 *
 * @author abdel
 */
public class FXMLLoginController implements Initializable {
    
    public static int idMedcin=0;    
    public static int idPharmacist=0;    
    public static int idLaboratery=0;


        
    @FXML
    private JFXTextField userNameField;

    @FXML
    private JFXPasswordField passwordField;
    
    
    private String passw,usrnam;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //you can make first connection
        /*
                try {
                    Connection con=ConnectionProvider.getCon();
                    Statement st = con.createStatement();
                    ResultSet rs= st.executeQuery("select *from medcin where id='"+1+"'");
                    if(rs.next()){
                        userNameField.setText(rs.getString(2));
                        usrnam = rs.getString(2);
                        passw=rs.getString(3);
                    }else{

                        Toast.makeText((Stage) userNameField.getScene().getWindow(), "No User", 1500, 500, 500);
                        
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,""+e.toString());
                }
                */
    }   
    
    @FXML
    void btnEntrerFromPass(ActionEvent event) {
        entrer();
    }

    @FXML
    void btnEntrerFromUser(ActionEvent event) {
        entrer();
    }

    @FXML
    void btnSignIn(ActionEvent event) {
        entrer();
    }

    @FXML
    void btnSignUp(ActionEvent event) {

    }

    @FXML
    void btnforgetPassword(ActionEvent event) {

    } 
     private void entrer(){
            if(userNameField.getText().isEmpty()){
            //errorMsg.show("Nom D'utilisateu est vide", 1500);
//            Alert alert = new Alert(AlertType.INFORMATION);
//            alert.setTitle("Se Connecter");
//            //alert.setHeaderText("SVP, Vérifier");
//            alert.setHeaderText(null);
//            alert.setContentText("Nom D'utilisateu est vide");
//            alert.showAndWait();
            String toastMsg = "User name is Empty !!";
            int toastMsgTime = 1500; //3.5 seconds
            int fadeInTime = 500; //0.5 seconds
            int fadeOutTime= 500; //0.5 seconds
            Toast.makeText((Stage) passwordField.getScene().getWindow(), toastMsg, toastMsgTime, fadeInTime, fadeOutTime);
            return;
        }
        if(passwordField.getText().isEmpty()){
            String toastMsg = "Password is Empty !!";
            int toastMsgTime = 1500; //3.5 seconds
            int fadeInTime = 500; //0.5 seconds
            int fadeOutTime= 500; //0.5 seconds
            Toast.makeText((Stage) passwordField.getScene().getWindow(), toastMsg, toastMsgTime, fadeInTime, fadeOutTime);
            return;
        }
        
         try {
                    Connection con=ConnectionProvider.getCon();
                    Statement st = con.createStatement();
                    ResultSet rsMed= st.executeQuery("select *from medcin where username='"+userNameField.getText()+"' and password='"+passwordField.getText()+"'");
      
                    if(rsMed.next()){
                        passw = rsMed.getString("password");
                        usrnam = rsMed.getString("username");
                        idMedcin = rsMed.getInt("id");
//                        System.out.println(usrnam);
//                        System.out.println(passw);
//                        if(passw.equals(passwordField.getText()) && usrnam.equals(userNameField.getText())){
                       //close the Login Frame "Stage"
//                        con.close();
                        System.out.println("connected with : "+userNameField.getText()+" and "+passwordField.getText());
                        //Creat a new Satge to Show the New frame "the MedGest"
                        Stage primaryStage =new Stage();
                        FXMLLoader loader =new FXMLLoader();
                        Parent root = loader.load(getClass().getResource("FXMLMedGest.fxml"));        
                        Scene scene = new Scene(root);

                        primaryStage.setTitle("MedGest");
                        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("images/logo.png")));
                        primaryStage.setScene(scene);
                        primaryStage.setMinHeight(720);
                        primaryStage.setMinWidth(1280);
                        primaryStage.setMaximized(true);
                        primaryStage.show();
                        
                        Stage stage = (Stage) passwordField.getScene().getWindow();
                        stage.close();
                        }else{
                                ResultSet rsPharma= st.executeQuery("select *from pharmacist where username='"+userNameField.getText()+"' and password='"+passwordField.getText()+"'");
                                if(rsPharma.next()){
                                    passw = rsPharma.getString("password");
                                    usrnam = rsPharma.getString("username");
                                    idPharmacist = rsPharma.getInt("id");
            //                        System.out.println(usrnam);
            //                        System.out.println(passw);
            //                        if(passw.equals(passwordField.getText()) && usrnam.equals(userNameField.getText())){
                                   //close the Login Frame "Stage"
            //                        con.close();
                                    System.out.println("connected with : "+userNameField.getText()+" and "+passwordField.getText());
                                    //Creat a new Satge to Show the New frame "the MedGest"
                                    Stage primaryStage =new Stage();
                                    FXMLLoader loader =new FXMLLoader();
                                    Parent root = loader.load(getClass().getResource("FXMLPharmaGest.fxml"));        
                                    Scene scene = new Scene(root);

                                    primaryStage.setTitle("PharmaGest");
                                    primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("images/logo.png")));
                                    primaryStage.setScene(scene);
                                    primaryStage.setMinHeight(720);
                                    primaryStage.setMinWidth(1280);
                                    primaryStage.setMaximized(true);
                                    primaryStage.show();

                                    Stage stage = (Stage) passwordField.getScene().getWindow();
                                    stage.close();
                                }else{
                                        ResultSet rsLab= st.executeQuery("select *from laboratery where username='"+userNameField.getText()+"' and password='"+passwordField.getText()+"'");
                                        if(rsLab.next()){
                                            passw = rsLab.getString("password");
                                            usrnam = rsLab.getString("username");
                                            idLaboratery = rsLab.getInt("id");
                    //                        System.out.println(usrnam);
                    //                        System.out.println(passw);
                    //                        if(passw.equals(passwordField.getText()) && usrnam.equals(userNameField.getText())){
                                           //close the Login Frame "Stage"
                    //                        con.close();
                                            System.out.println("connected with : "+userNameField.getText()+" and "+passwordField.getText());
                                            //Creat a new Satge to Show the New frame "the MedGest"
                                            Stage primaryStage =new Stage();
                                            FXMLLoader loader =new FXMLLoader();
                                            Parent root = loader.load(getClass().getResource("FXMLLabGest.fxml"));        
                                            Scene scene = new Scene(root);

                                            primaryStage.setTitle("LabGest");
                                            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("images/logo.png")));
                                            primaryStage.setScene(scene);
                                            primaryStage.setMinHeight(720);
                                            primaryStage.setMinWidth(1280);
                                            primaryStage.setMaximized(true);
                                            primaryStage.show();

                                            Stage stage = (Stage) passwordField.getScene().getWindow();
                                            stage.close();
                                     }else{
                                        String toastMsg = "                         Sorry \nThe Username or The Password is wrong";
                                        int toastMsgTime = 2500; //2.5 seconds
                                        int fadeInTime = 500; //0.5 seconds
                                        int fadeOutTime= 500; //0.5 seconds
                                        Toast.makeText((Stage) passwordField.getScene().getWindow(), toastMsg, toastMsgTime, fadeInTime, fadeOutTime);
                                     }
                                }
                        }
                    
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,""+e.toString());
                }
    }
       
}
