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
import java.util.Date;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
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
public class FXMLPharmaGestController implements Initializable {
    
    @FXML
    private Label labelPatientIdentificationID;
    
    @FXML
    private JFXTextField firstNameFieldID;

    @FXML
    private JFXTextField familyNameFieldID;

    @FXML
    private JFXTextField ageFieldID;
    
    @FXML
    private JFXTextField patientIdField;

    @FXML
    private FontAwesomeIconView back_arrow_ID;
    
    @FXML
    private JFXButton btnSaleId;

    @FXML
    private TableView<TablePrescription> tablePrescription;

    @FXML
    private TableColumn<TablePrescription, Integer> cellPrescriptionNumberID;

    @FXML
    private TableColumn<TablePrescription, Integer> cellDiagnosisID;

    @FXML
    private TableColumn<TablePrescription, String> cellCreationDateID;

    @FXML
    private TableColumn<TablePrescription, String> cellSaleDate;
    
    ObservableList<TablePrescription> prescriptionList = FXCollections.observableArrayList();


    /**
     * Initialises the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    

    @FXML
    void searchByPatientID(ActionEvent event) {
        fillPrescriptionTable();
    }
    
    @FXML
    void searchByFamilyName(ActionEvent event) {
          String patientFirstName = firstNameFieldID.getText();          
          String patientFamilyName = familyNameFieldID.getText();
          if(!patientFirstName.isEmpty() && !patientFamilyName.isEmpty()){
                    try {
                    Connection con=ConnectionProvider.getCon();
                    String query = "SELECT * FROM patients WHERE first_name LIKE CONCAT(?,'%') AND family_name LIKE CONCAT(?,'%')";
                    PreparedStatement st = con.prepareStatement(query);
                    st.setString(1, patientFirstName);
                    st.setString(2, patientFamilyName);
                    ResultSet rs= st.executeQuery();
                    if(rs.next()){
                        firstNameFieldID.setText(rs.getString(3));
                        familyNameFieldID.setText(rs.getString(4));
                        ageFieldID.setText(rs.getString(6));
                        patientIdField.setText(rs.getString(2));
                        patientIdField.requestFocus();
                        labelPatientIdentificationID.setText("Patient identified");
                        labelPatientIdentificationID.setTextFill(Color.valueOf("#00FF0B"));
                        
                        firstNameFieldID.setEditable(false);                    
                        familyNameFieldID.setEditable(false);
                        ageFieldID.setEditable(false);
                        
                    }else{
                        firstNameFieldID.setText("");
                        familyNameFieldID.setText("");
                        ageFieldID.setText("");
                        
                        labelPatientIdentificationID.setText("Patient not Identified");
                        labelPatientIdentificationID.setTextFill(Color.valueOf("#ff0000"));

                        Toast.makeText((Stage) firstNameFieldID.getScene().getWindow(), "Patient not found", 1500, 500, 500);
                        
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,""+e.toString());
                }
          }else{
              Toast.makeText((Stage) firstNameFieldID.getScene().getWindow(), "Please fill the first name and the family name", 1500, 500, 500);
          }
    }

    @FXML
    void searchByFirstName(ActionEvent event) {
          String patientFirstName = firstNameFieldID.getText();          
          String patientFamilyName = familyNameFieldID.getText();
          if(!patientFirstName.isEmpty() && !patientFamilyName.isEmpty()){
                    try {
                    Connection con=ConnectionProvider.getCon();
                    String query = "SELECT * FROM patients WHERE first_name LIKE CONCAT(?,'%') AND family_name LIKE CONCAT(?,'%')";
                    PreparedStatement st = con.prepareStatement(query);
                    st.setString(1, patientFirstName);
                    st.setString(2, patientFamilyName);
                    ResultSet rs= st.executeQuery();
                    if(rs.next()){
                        firstNameFieldID.setText(rs.getString(3));
                        familyNameFieldID.setText(rs.getString(4));
                        ageFieldID.setText(rs.getString(6));
                        patientIdField.setText(rs.getString(2));
                        patientIdField.requestFocus();
                        labelPatientIdentificationID.setText("Patient identified");
                        labelPatientIdentificationID.setTextFill(Color.valueOf("#00FF0B"));
                        
                        firstNameFieldID.setEditable(false);                    
                        familyNameFieldID.setEditable(false);
                        ageFieldID.setEditable(false);
                        
                    }else{
                        firstNameFieldID.setText("");
                        familyNameFieldID.setText("");
                        ageFieldID.setText("");
                        
                        labelPatientIdentificationID.setText("Patient not Identified");
                        labelPatientIdentificationID.setTextFill(Color.valueOf("#ff0000"));

                        Toast.makeText((Stage) firstNameFieldID.getScene().getWindow(), "Patient not found", 1500, 500, 500);
                        
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,""+e.toString());
                }
          }else{
              Toast.makeText((Stage) firstNameFieldID.getScene().getWindow(), "Please fill the first name and the family name", 1500, 500, 500);
          }
    }
    
    @FXML
    void btnCheckInStock(ActionEvent event) {
        if(tablePrescription.getSelectionModel().isEmpty()){
            Toast.makeText((Stage) firstNameFieldID.getScene().getWindow(), "Select Prescription to Check", 1500, 500, 500);
            return;
        }
        int selectedID = tablePrescription.getSelectionModel().getSelectedIndex();
        int prescriptionID = tablePrescription.getItems().get(selectedID).getPrescriptionNum();
        int pharmaID = FXMLLoginController.idPharmacist;
        
          try {
                    Connection con=ConnectionProvider.getCon();
                    //String query = ;
                    Statement st = con.createStatement();
                    //st.setInt(1, prescriptionID);
                    //st.setInt(2, pharmaID);
                    ResultSet rs= st.executeQuery("SELECT m.id, m.id_pharmacist, m.label, m.quantity,\n" +
                                                            "md_in_pr.prescription_id, md_in_pr.medecament_id,\n" +
                                                            "pr.id, pr.diagnosis_report_id\n" +
                                                            "FROM medicaments m\n" +
                                                            "JOIN medecaments_in_prescription md_in_pr ON m.id = md_in_pr.medecament_id\n" +
                                                            "JOIN prescriptions pr ON md_in_pr.prescription_id = pr.id\n" +
                                                            "WHERE  pr.id = '"+prescriptionID+"' and m.id_pharmacist = '"+pharmaID+"'");
                    String medlost = "This medicaments are out of stock :";
                    boolean exist = true;
                    while(rs.next()){
                        System.err.println("quantity :"+rs.getInt("m.quantity")+"\n");
                        if(rs.getInt("m.quantity")<=0){
                            medlost=medlost+"\n- "+rs.getString("m.label");
                            exist = false;
                        }
                    }
                    if(exist){
                        //Toast.makeText((Stage) firstNameFieldID.getScene().getWindow(), "ALL medicaments exist", 1500, 500, 500);
                        JOptionPane.showMessageDialog(null,"ALL medicaments exist You can sale");
                        btnSaleId.setDisable(false);
                    }else{
                        //Toast.makeText((Stage) firstNameFieldID.getScene().getWindow(), ""+medlost, 1500, 500, 500);
                        JOptionPane.showMessageDialog(null,""+medlost);
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,""+e.toString());
                }
    }

    @FXML
    void btnPrintPrescription(ActionEvent event) {
        if(tablePrescription.getSelectionModel().isEmpty()){
            Toast.makeText((Stage) firstNameFieldID.getScene().getWindow(), "Select Prescription to print", 1500, 500, 500);
            return;
        }
        int selectedID = tablePrescription.getSelectionModel().getSelectedIndex();
        int diagnosisID = tablePrescription.getItems().get(selectedID).getDiagnosisId();
        try {
        Connection con = ConnectionProvider.getCon();
        try {
            new jasperPrescription(diagnosisID,con);
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
        btnreset();
    }
    public void btnreset(){
        patientIdField.setText("");
        firstNameFieldID.setText("");
        familyNameFieldID.setText("");
        ageFieldID.setText("");
        btnSaleId.setDisable(true);

        tablePrescription.getItems().clear();

        labelPatientIdentificationID.setText("Patient not Identified");
        labelPatientIdentificationID.setTextFill(Color.valueOf("#ff0000"));
    }

    @FXML
    void btnSale(ActionEvent event) {
        if(tablePrescription.getSelectionModel().isEmpty()){
            Toast.makeText((Stage) firstNameFieldID.getScene().getWindow(), "Select Prescription to Sale", 1500, 500, 500);
            return;
        }
        int selectedID = tablePrescription.getSelectionModel().getSelectedIndex();
        int prescription_id = tablePrescription.getItems().get(selectedID).getPrescriptionNum();
        String prescription_sale_date = tablePrescription.getItems().get(selectedID).getSaledate();
        int pharmaID = FXMLLoginController.idPharmacist;
        if(prescription_sale_date != null && !prescription_sale_date.isEmpty()){
            Toast.makeText((Stage) firstNameFieldID.getScene().getWindow(), "Prescription allready saled", 1500, 500, 500);
            return;
        }
        tablePrescription.getItems().clear();
        try {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            st.executeUpdate("update prescriptions set sale_date= CURRENT_TIMESTAMP, pharma_sale_id='"+String.valueOf(pharmaID)+"' where id='"+prescription_id+"'");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Prescription");
            alert.setHeaderText(null);
            alert.setContentText("Saled successfully");
            alert.showAndWait();
            
            fillPrescriptionTable();

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
    
    public void fillPrescriptionTable(){
        //if(!tablePrescription.getSelectionModel().isEmpty()){
            tablePrescription.getItems().clear();
        //}
        if(patientIdField.getText().isEmpty()){
           Toast.makeText((Stage) patientIdField.getScene().getWindow(), "Enter patient ID Please !", 1500, 500, 500);
           return;
        }
        String idCardNumber = patientIdField.getText();
        int a=0;
        try {
            Connection con= ConnectionProvider.getCon();
            String sql = "SELECT pr.id, pr.diagnosis_report_id, pr.created_date, pr.sale_date, " +
                         "dr.id, dr.patient_id,  " +
                         "p.id, p.id_card_number, p.first_name, p.family_name, p.age " +
                         "FROM prescriptions pr " +
                         "JOIN diagnosis_reports dr ON pr.diagnosis_report_id = dr.id " +
                         "JOIN patients p ON dr.patient_id = p.id " +
                         "WHERE p.id_card_number = ?";
            
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, idCardNumber);
            
            // Execute the query
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                
                prescriptionList.add(new TablePrescription(rs.getInt("pr.id"), rs.getInt("pr.diagnosis_report_id"), String.valueOf(rs.getTimestamp("pr.created_date")),
                        rs.getString("pr.sale_date")));
                System.out.println(rs.getInt("pr.id")+" "+rs.getInt("pr.diagnosis_report_id")+" "+String.valueOf(rs.getTimestamp("pr.created_date"))+" "+rs.getString("pr.sale_date")+"\n");
                if(a==0){          
                    labelPatientIdentificationID.setText("Patient identified");
                    labelPatientIdentificationID.setTextFill(Color.valueOf("#00FF0B"));
                    
                    firstNameFieldID.setText(rs.getString("p.first_name"));                    
                    familyNameFieldID.setText(rs.getString("p.family_name"));                    
                    ageFieldID.setText(rs.getString("p.age"));
                    
                    firstNameFieldID.setEditable(false);                    
                    familyNameFieldID.setEditable(false);
                    ageFieldID.setEditable(false);
                    
                    a=1;
                }
            }
            
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null,""+e.toString());
        }
        
        cellPrescriptionNumberID.setCellValueFactory(new PropertyValueFactory<TablePrescription, Integer>("prescriptionNum"));
        cellDiagnosisID.setCellValueFactory(new PropertyValueFactory<TablePrescription, Integer>("diagnosisId"));
        cellCreationDateID.setCellValueFactory(new PropertyValueFactory<TablePrescription, String>("creationdate"));
        cellSaleDate.setCellValueFactory(new PropertyValueFactory<TablePrescription, String>("saledate"));
        tablePrescription.setItems(prescriptionList);
    }
    
}
