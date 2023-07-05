/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.RotateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import project.ConnectionProvider;

/**
 * FXML Controller class
 *
 * @author abdel
 */
public class FXMLMedGestController implements Initializable {
    
    @FXML
    private FontAwesomeIconView back_arrow_ID;
    
    public static String patientIdCardNumber = "0";
    public static int patientId = 0;
    
    @FXML
    private JFXTextField patientIdField;
    
    @FXML
    private VBox vBoxPatientHistoryID;
        
    @FXML
    private ListView<ListDiagnosisItem> myListViewID;
    
    
    @FXML
    private JFXButton btnAddDiagId;

    


    /**
     * Initialises the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    

    @FXML
    void btnAddADiagnosis(ActionEvent event) throws IOException {
        
        Stage primaryStage =new Stage();
        FXMLLoader loader =new FXMLLoader();
        Parent root = loader.load(getClass().getResource("FXMLAddDiagnosis.fxml"));        

        Scene scene = new Scene(root);
        primaryStage.setTitle("Add Diagnosis");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("images/logo.png")));
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(720);
        primaryStage.setMinWidth(1280);
        primaryStage.setMaximized(true);
        //primaryStage.setResizable(false);
        primaryStage.show();
        
        //to close the old window
        //Stage stage = (Stage) myListViewID.getScene().getWindow();
        //stage.close();

    }
    
    @FXML
    void btnPrescriptionRecipe(ActionEvent event) {
        int idSelected = myListViewID.getSelectionModel().getSelectedIndex();
        myListViewID.getItems().remove(idSelected);
    }

    @FXML
    void btnAnalysisRecipe(ActionEvent event) {

    }
    

    @FXML
    void btnNextAppointment(ActionEvent event) {

    }


    @FXML
    void searchByPatientID(ActionEvent event) {
        if(patientIdField.getText().isEmpty()){
           Toast.makeText((Stage) patientIdField.getScene().getWindow(), "Enter patient ID Please !", 1500, 500, 500);
           return;
        }
        btnAddDiagId.setDisable(true);
        String idCardNumber = patientIdField.getText();
        myListViewID.getItems().clear();
        try {
            Connection con= ConnectionProvider.getCon();
            String sql = "SELECT p.id, p.id_card_number, p.first_name, p.family_name, p.phone_numer, p.age, " +
                         "dr.id, dr.symptoms, dr.disease_name, dr.medicaments, " +
                         "dr.analysis, dr.diagnosis, dr.doctor_note, dr.diagnosis_date, dr.prescription_type, " +
                         "m.phone_number, m.first_name, m.family_name, m.specialty " +
                         "FROM patients p " +
                         "JOIN diagnosis_reports dr ON p.id = dr.patient_id " +
                         "JOIN medcin m ON dr.medcin_id = m.id " +
                         "WHERE p.id_card_number = ?";
            
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, idCardNumber);
            
            // Execute the query
            ResultSet rs = statement.executeQuery();
            
            if(rs.next()){
                                // Retrieve patient information
                patientId = rs.getInt("p.id");
                patientIdCardNumber = rs.getString("p.id_card_number");
                String firstName = rs.getString("p.first_name");
                String familyName = rs.getString("p.family_name");
                String phoneNumber = rs.getString("p.phone_numer");
                String age = rs.getString("p.age");

                // Retrieve diagnosis report information
                int diagnosisReportId = rs.getInt("dr.id");
                String symptoms = rs.getString("dr.symptoms");
                String diseaseName = rs.getString("dr.disease_name");
                String medicaments = rs.getString("dr.medicaments");
                String analysis = rs.getString("dr.analysis");
                String diagnosis = rs.getString("dr.diagnosis");
                String doctorNote = rs.getString("dr.doctor_note");
                Timestamp diagnosisDate = rs.getTimestamp("dr.diagnosis_date");
                String prescriptionType = rs.getString("dr.prescription_type");

                // Retrieve medcin information
                String medcinPhoneNumber = rs.getString("m.phone_number");
                String medcimFirstName = rs.getString("m.first_name");
                String medcinFamilyName = rs.getString("m.family_name");                
                String medcinSpecialty = rs.getString("m.specialty");
                
                ListDiagnosisItem listDiagnosisItem = new ListDiagnosisItem(diagnosisReportId,patientId,diagnosisDate.toString(), "the patient visited doctor "+medcimFirstName+" "+medcinFamilyName+" specialist in "+medcinSpecialty+" because of the following symptoms :\n"+symptoms+" \n" +
                        "the doctor diagnosed the disease "+diseaseName+" and gave him the following treatment :\n"+medicaments+"", medicaments,analysis,medcinPhoneNumber,prescriptionType);
                myListViewID.getItems().add(listDiagnosisItem);
                btnAddDiagId.setDisable(false);
                    
                while(rs.next()){
                    // Retrieve patient information
                     patientId = rs.getInt("p.id");
                     patientIdCardNumber = rs.getString("p.id_card_number");
                     firstName = rs.getString("p.first_name");
                     familyName = rs.getString("p.family_name");
                     phoneNumber = rs.getString("p.phone_numer");
                     age = rs.getString("p.age");

                    // Retrieve diagnosis report information
                     diagnosisReportId = rs.getInt("dr.id");
                     symptoms = rs.getString("dr.symptoms");
                     diseaseName = rs.getString("dr.disease_name");
                     medicaments = rs.getString("dr.medicaments");
                     analysis = rs.getString("dr.analysis");
                     diagnosis = rs.getString("dr.diagnosis");
                     doctorNote = rs.getString("dr.doctor_note");
                     diagnosisDate = rs.getTimestamp("dr.diagnosis_date");
                     prescriptionType = rs.getString("dr.prescription_type");

                    // Retrieve medcin information
                     medcinPhoneNumber = rs.getString("m.phone_number");
                     medcimFirstName = rs.getString("m.first_name");
                     medcinFamilyName = rs.getString("m.family_name");                
                     medcinSpecialty = rs.getString("m.specialty");

                    // Print the retrieved data
                    /*
                    System.out.println("Patient ID: " + patientId);
                    System.out.println("First Name: " + firstName);
                    System.out.println("Family Name: " + familyName);
                    System.out.println("Phone Number: " + phoneNumber);
                    System.out.println("Age: " + age);
                    System.out.println("Diagnosis Report ID: " + diagnosisReportId);
                    System.out.println("Symptoms: " + symptoms);
                    System.out.println("Disease Name: " + diseaseName);
                    System.out.println("Medicaments: " + medicaments);
                    System.out.println("Analysis: " + analysis);
                    System.out.println("Diagnosis: " + diagnosis);
                    System.out.println("Doctor Note: " + doctorNote);
                    System.out.println("Diagnosis Date: " + diagnosisDate);
                    System.out.println("Medcin Phone Number: " + medcinPhoneNumber);
                    */

                    listDiagnosisItem = new ListDiagnosisItem(diagnosisReportId,patientId,diagnosisDate.toString(), "the patient visited doctor "+medcimFirstName+" "+medcinFamilyName+" specialist in "+medcinSpecialty+" because of the following symptoms :\n"+symptoms+" \n" +
                        "the doctor diagnosed the disease "+diseaseName+" and gave him the following treatment :\n"+medicaments+"", medicaments,analysis,medcinPhoneNumber,prescriptionType);
                    myListViewID.getItems().add(listDiagnosisItem);

                }
            
                    // Set custom cell factory
                    myListViewID.setCellFactory(new Callback<ListView<ListDiagnosisItem>, ListCell<ListDiagnosisItem>>() {
                        @Override
                        public ListCell<ListDiagnosisItem> call(ListView<ListDiagnosisItem> param) {
                            return new CustomItemCell(param);
                        }
                    });
                    //myListViewID.setStyle("-fx-background-color: #BBC6C8; -fx-background-radius: 20;");
                    //myListViewID.setEditable(false);

            }else{
                Toast.makeText((Stage) patientIdField.getScene().getWindow(), "patient ID not exist", 1500, 500, 500);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,""+e.toString());
        }

    }
        @FXML
    void backArrowReturn(MouseEvent event) throws IOException {
                // Create a RotateTransition for the animation
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), back_arrow_ID);
        rotateTransition.setByAngle(360); // Rotate 360 degrees
        rotateTransition.play(); // Start the animation

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Close");
        //alert.setHeaderText("SVP, Vérifier");
        alert.setHeaderText(null);
        alert.setContentText("Are You Sure You Want To Close");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            /*
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
            */
            Stage primaryStage =new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Login");
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("images/logo.png")));
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            primaryStage.show();
            Stage stage = (Stage) patientIdField.getScene().getWindow();
            stage.close();   
        } else {
            // ... user chose CANCEL or closed the dialog
            alert.close();
        }
    }
    @FXML
    void backArrowMouseEnterd(MouseEvent event) {
        back_arrow_ID.setScaleX(1.2);
        back_arrow_ID.setScaleY(1.2);
        back_arrow_ID.setOpacity(0.8);
    }
    @FXML
    void backArrowMouseExited(MouseEvent event) {
        back_arrow_ID.setScaleX(1);
        back_arrow_ID.setScaleY(1);
        back_arrow_ID.setOpacity(1);
    }
}
