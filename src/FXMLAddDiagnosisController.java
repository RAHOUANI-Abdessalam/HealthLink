/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Optional;
import javafx.animation.RotateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import project.ConnectionProvider;

/**
 * FXML Controller class
 *
 * @author abdel
 */
public class FXMLAddDiagnosisController implements Initializable {
    
    @FXML
    private Label id_patient_label;

    @FXML
    private FontAwesomeIconView back_arrow_ID;

    @FXML
    private JFXTextField diseaseTextFieldId;

    @FXML
    private JFXTextArea symptomsTextAreaId;

    @FXML
    private JFXComboBox<String> medicamentComboBoxId;

    @FXML
    private JFXTextArea medicamentsTextAreaId;

    @FXML
    private JFXComboBox<String> analysisComboBoxId;

    @FXML
    private JFXTextArea analysesTextAreaId;

    @FXML
    private JFXTextArea diagnosisTextAreaId;
    
    @FXML
    private JFXTextField medicamentQuantityId;
    
    @FXML
    private JFXButton btnPrintPrescriptionID;

    @FXML
    private JFXButton btnPrintAnalysisID;

    @FXML
    private JFXButton btnNextAppointmentID;

    @FXML
    private HBox hboxNextAppointmentId;
    
    @FXML
    private VBox vboxNextAppointmentID;
    
    @FXML
    private JFXDatePicker nextAppointmentDatePickerId;
    
    String selectedMedicament,selectedAnalys;
    
    private final ObservableList<String> medicaments = FXCollections.observableArrayList(
    "Medicament 1",
    "Medicament 2",
    "Medicament 3",
    "Medicament 4",
    "Medicament 5",
    "Medicament 6"
    );
        private final ObservableList<String> analysis = FXCollections.observableArrayList(
    "Analyse 1",
    "Analyse 2",
    "Analyse 3",
    "Analyse 4",
    "Analyse 5",
    "Analyse 6"
    );

    /**
     * Initialises the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        id_patient_label.setText(FXMLMedGestController.patientIdCardNumber);
        
        medicamentComboBoxId.setItems(medicaments);
        analysisComboBoxId.setItems(analysis);
        medicamentsTextAreaId.setText("");
        analysesTextAreaId.setText("");
        //medicamentQuantityId.setText("1");
        medicamentQuantityId.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                        medicamentQuantityId.setText(oldValue);
                    }
            }
        });

    }    

    @FXML
    void btnConfirm(ActionEvent event) {
        if(diseaseTextFieldId.getText().isEmpty() && symptomsTextAreaId.getText().isEmpty() && medicamentsTextAreaId.getText().isEmpty() && analysesTextAreaId.getText().isEmpty() && diagnosisTextAreaId.getText().isEmpty() ){
            Toast.makeText((Stage) symptomsTextAreaId.getScene().getWindow(), "Fill At least one field", 1500, 500, 500);
            return;
        }
        String presType = "";
        if(!medicamentsTextAreaId.getText().isEmpty() && !analysesTextAreaId.getText().isEmpty()){
            presType = "bouth";
        }else{
            if(!medicamentsTextAreaId.getText().isEmpty()){
                presType = "medicament";
            }else{
                if(!analysesTextAreaId.getText().isEmpty()){
                 presType = "analyse";   
                }else{
                    presType = "neither";
                }
            }
        }
        try {
            Connection con = ConnectionProvider.getCon();
            PreparedStatement st = con.prepareStatement("INSERT INTO diagnosis_reports (patient_id, medcin_id, symptoms, disease_name, medicaments, analysis, diagnosis, doctor_note, diagnosis_date, prescription_type) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ? , CURRENT_TIMESTAMP , ?)");

            
            // Set the values for the new diagnosis report
            st.setInt(1, Integer.valueOf(FXMLMedGestController.patientId));
            st.setInt(2, Integer.valueOf(FXMLLoginController.idMedcin));
            st.setString(3, symptomsTextAreaId.getText());
            st.setString(4, diseaseTextFieldId.getText());
            st.setString(5, medicamentsTextAreaId.getText());
            st.setString(6, analysesTextAreaId.getText());
            st.setString(7, diagnosisTextAreaId.getText());
            st.setString(8, "INSERT_DOCTOR_NOTE_HERE");
            st.setString(9, presType);

            // Execute the query
            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                btnNextAppointmentID.setDisable(false);
                btnPrintAnalysisID.setDisable(false);
                btnPrintPrescriptionID.setDisable(false);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ADD Diagnosis");
                alert.setHeaderText(null);
                alert.setContentText("Diagnosis Added");
                alert.showAndWait();
                btnPrintAnalysisID.setDisable(false);
                btnPrintAnalysisID.setDisable(false);
                btnNextAppointmentID.setDisable(false);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ADD diagnosis");
                alert.setHeaderText(null);
                alert.setContentText("Faild to Add Diagnosis");
                alert.showAndWait();
            }
            //Toast.makeText((Stage) raisonScocialField.getScene().getWindow(), "Clien Numéro "+id+" a été Ajouté avec succès", 2000, 500, 500);

            //btnReinitialiser();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,""+e.toString());
            System.out.println("Error in connection"+e.toString());
        }

    }

    @FXML
    void btnNextAppointment(ActionEvent event) {
        hboxNextAppointmentId.setVisible(true);
        vboxNextAppointmentID.setVisible(true);
    }

    @FXML
    void btnPrintAnalysis(ActionEvent event) {
        if(medicamentsTextAreaId.getText().isEmpty()){
            Toast.makeText((Stage) symptomsTextAreaId.getScene().getWindow(), "There Is No Analysis in The Prescription", 1500, 500, 500);
            return;
        }
        int lastDiagnosisId = 0;
        try{   
                Connection con = ConnectionProvider.getCon();
                // Create a statement
                Statement statement = con.createStatement();
                // Execute the query
                String sql = "SELECT id FROM diagnosis_reports ORDER BY id DESC LIMIT 1";
                ResultSet resultSet = statement.executeQuery(sql);
                // Retrieve the last ID
                lastDiagnosisId = 0;
                if (resultSet.next()) {
                    lastDiagnosisId = resultSet.getInt("id");
                }
                // Close the result set and statement
                resultSet.close();
                statement.close();

                // Print the last ID
                System.out.println("Last diagnosis report ID: " + lastDiagnosisId);
                // Close the connection
                con.close();
            } catch (Exception e) {
            JOptionPane.showMessageDialog(null,""+e.toString());
            System.out.println("Error in connection"+e.toString());
        }
        try {
                Connection con = ConnectionProvider.getCon();
                try {
                    new jasperAnalysis(lastDiagnosisId,con);
                } catch (Exception e) {
                    //System.out.println("Error in new jasperPrescription"+e.toString());
                    JOptionPane.showMessageDialog(null,"Error in new jasper "+e.toString());
                }
              //  new jasperPrescription (nFacture,con);

//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Facture");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Facture N°"+String.valueOf(nFacture)+ " Enregistrée dans C:/Users/PC_nom");
//
//                    alert.showAndWait();
            }catch (Exception e) {
                    JOptionPane.showMessageDialog(null,""+e.toString());
                    System.out.println("Error in connection "+e.toString());
            }
    }

    @FXML
    void btnPrintPrescription(ActionEvent event) {
        if(medicamentsTextAreaId.getText().isEmpty()){
            Toast.makeText((Stage) symptomsTextAreaId.getScene().getWindow(), "There Is No Medicament in The Prescription", 1500, 500, 500);
            return;
        }
        int lastDiagnosisId = 0;
        try{   
                Connection con = ConnectionProvider.getCon();
                // Create a statement
                Statement statement = con.createStatement();
                // Execute the query
                String sql = "SELECT id FROM diagnosis_reports ORDER BY id DESC LIMIT 1";
                ResultSet resultSet = statement.executeQuery(sql);
                // Retrieve the last ID
                lastDiagnosisId = 0;
                if (resultSet.next()) {
                    lastDiagnosisId = resultSet.getInt("id");
                }
                // Close the result set and statement
                resultSet.close();
                statement.close();

                // Print the last ID
                System.out.println("Last diagnosis report ID: " + lastDiagnosisId);
                // Close the connection
                con.close();
            } catch (Exception e) {
            JOptionPane.showMessageDialog(null,""+e.toString());
            System.out.println("Error in connection"+e.toString());
        }
        try {
                Connection con = ConnectionProvider.getCon();
                try {
                    new jasperPrescription(lastDiagnosisId,con);
                } catch (Exception e) {
                    //System.out.println("Error in new jasperPrescription"+e.toString());
                    JOptionPane.showMessageDialog(null,"Error in new jasper "+e.toString());
                }
            }catch (Exception e) {
                    JOptionPane.showMessageDialog(null,""+e.toString());
                    System.out.println("Error in connection "+e.toString());
            }

    }

    @FXML
    void btnReset(ActionEvent event) {
        diseaseTextFieldId.setText("");
        symptomsTextAreaId.setText("");
        medicamentsTextAreaId.setText("");
        analysesTextAreaId.setText("");
        diagnosisTextAreaId.setText("");
        nextAppointmentDatePickerId.setValue(null);

    }
    
    @FXML
    void selectMedicamentKeyPressedComboBox(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            selectedMedicament = medicamentComboBoxId.getSelectionModel().getSelectedItem();
            medicamentQuantityId.requestFocus();
        }
    }
    
    @FXML
    void addMedicamentToTextArea(ActionEvent event) {
        if (selectedMedicament != null) {
            if(medicamentQuantityId.getText().isEmpty()){
                if(medicamentsTextAreaId.getText().isEmpty()){
                    medicamentsTextAreaId.setText(selectedMedicament+"                                          Qte: X1"+medicamentQuantityId.getText()+"\n");
                }else{
                    medicamentsTextAreaId.setText(medicamentsTextAreaId.getText()+"\n"+selectedMedicament+"                                          Qte: X1"+medicamentQuantityId.getText()+"\n");
                }
            }else{
                if(medicamentsTextAreaId.getText().isEmpty()){
                    medicamentsTextAreaId.setText(selectedMedicament+"                                          Qte: X"+medicamentQuantityId.getText()+"\n");
                }else{
                    medicamentsTextAreaId.setText(medicamentsTextAreaId.getText()+"\n"+selectedMedicament+"                                          Qte: X"+medicamentQuantityId.getText()+"\n");
                }
            }
            medicamentQuantityId.setText("");
            //medicamentsTextAreaId.appendText(selectedMedicament + "\n");
        }else{
            Toast.makeText((Stage) medicamentsTextAreaId.getScene().getWindow(), "Please Select Medecament first", 1500, 500, 500);
        }
    }
    
    @FXML
    void selectAnalysisKeyPressedComboBox(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            // Enter button was pressed
            selectedAnalys = analysisComboBoxId.getSelectionModel().getSelectedItem();
                if (selectedAnalys != null) {
                    if(analysesTextAreaId.getText().isEmpty()){
                        analysesTextAreaId.setText(selectedAnalys);
                    }else{
                        analysesTextAreaId.setText(analysesTextAreaId.getText()+"\n"+selectedAnalys);
                    }
                //medicamentsTextAreaId.appendText(selectedMedicament + "\n");
            }
        }
    }
    
    @FXML
    void backArrowReturn(MouseEvent event) throws IOException {
                // Create a RotateTransition for the animation
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), back_arrow_ID);
        rotateTransition.setByAngle(360); // Rotate 360 degrees
        rotateTransition.play(); // Start the animation

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Fermer");
        //alert.setHeaderText("SVP, Vérifier");
        alert.setHeaderText(null);
        alert.setContentText("Voulez vous Vraiment fermer");

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
            Stage stage = (Stage) analysesTextAreaId.getScene().getWindow();
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
    
    @FXML
    void btnAddNextAppointment(ActionEvent event) throws SQLException {
        int lastDiagnosisId = 0;
        try{   
                Connection con = ConnectionProvider.getCon();
                // Create a statement
                Statement statement = con.createStatement();
                // Execute the query
                String sql = "SELECT id FROM diagnosis_reports ORDER BY id DESC LIMIT 1";
                ResultSet resultSet = statement.executeQuery(sql);
                // Retrieve the last ID
                lastDiagnosisId = 0;
                if (resultSet.next()) {
                    lastDiagnosisId = resultSet.getInt("id");
                }
                // Close the result set and statement
                resultSet.close();
                statement.close();

                // Print the last ID
                System.out.println("Last diagnosis report ID: " + lastDiagnosisId);
                // Close the connection
                con.close();
            } catch (Exception e) {
            JOptionPane.showMessageDialog(null,""+e.toString());
            System.out.println("Error in connection"+e.toString());
        }

        LocalDate selectedDate1 = nextAppointmentDatePickerId.getValue();
        System.err.println(selectedDate1.toString());
        
         try {
            // Prepare the SQL query
            Connection con = ConnectionProvider.getCon();

            String sql = "INSERT INTO next_appointments (diagnosis_report_id, next_appointment_date, doctor_note) " +
                         "VALUES (?, ?, ?)";

            PreparedStatement statement = con.prepareStatement(sql);

            // Set the values for the new next appointment
            statement.setInt(1, lastDiagnosisId);
            
            // Convert selectedDate to SQL date format
            String selectedDate = selectedDate1.toString(); // Example selected date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = dateFormat.parse(selectedDate);
            java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
            
            statement.setDate(2, sqlDate);
            statement.setString(3, "NOTE HERE");

            // Execute the query
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("New next appointment inserted successfully.");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("NEXT Appointment");
                alert.setHeaderText(null);
                alert.setContentText("Apointment Added");
                alert.showAndWait();
            } else {
                System.out.println("Failed to insert new next appointment.");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("NEXT Appointment");
                alert.setHeaderText(null);
                alert.setContentText("Faild to Add Appointment");
                alert.showAndWait();
            }

            // Close the statement and connection
            statement.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,""+e.toString());
            System.out.println("Error in connection"+e.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void btnEditNextAppointment(ActionEvent event) throws ParseException {
                int lastNextAppointmentId = 0;
        try{   
                Connection con = ConnectionProvider.getCon();
                // Create a statement
                Statement statement = con.createStatement();
                // Execute the query
                String sql = "SELECT id FROM next_appointments ORDER BY id DESC LIMIT 1";
                ResultSet resultSet = statement.executeQuery(sql);
                // Retrieve the last ID
                lastNextAppointmentId = 0;
                if (resultSet.next()) {
                    lastNextAppointmentId = resultSet.getInt("id");
                }
                // Close the result set and statement
                resultSet.close();
                statement.close();

                // Print the last ID
                System.out.println("Last Next Appointment ID: " + lastNextAppointmentId);
                // Close the connection
                con.close();
            } catch (Exception e) {
            JOptionPane.showMessageDialog(null,""+e.toString());
            System.out.println("Error in connection"+e.toString());
        }
        LocalDate selectedDate1 = nextAppointmentDatePickerId.getValue();
        String newDate = selectedDate1.toString(); // Example selected date
        try {
                Connection con = ConnectionProvider.getCon();
                // Create a prepared statement
                String sql = "UPDATE next_appointments\n" +
                                    "SET next_appointment_date = ? \n" +
                                    "WHERE id ="+lastNextAppointmentId+"";
                PreparedStatement statement = con.prepareStatement(sql);

                // Set the new date value
                statement.setString(1, newDate);

                // Execute the update
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("New next appointment edited successfully.");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("EDIT NEXT Appointment");
                    alert.setHeaderText(null);
                    alert.setContentText("NEW Apointment Added");
                    alert.showAndWait();
                } else {
                    System.out.println("Failed to insert new next appointment.");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("EDIT NEXT Appointment");
                    alert.setHeaderText(null);
                    alert.setContentText("Faild to EDIT the Appointment");
                    alert.showAndWait();
                }

                statement.close();
                con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,""+e.toString());
            System.out.println("Error in connection"+e.toString());
        }
    }

    
}
