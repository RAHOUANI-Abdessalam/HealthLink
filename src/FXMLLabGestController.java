/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import project.ConnectionProvider;

/**
 * FXML Controller class
 *
 * @author abdel
 */
public class FXMLLabGestController implements Initializable {
    
    @FXML
    private JFXTextField patientIdField;

    @FXML
    private FontAwesomeIconView back_arrow_ID;
    
    @FXML
    private FontAwesomeIconView checkMarkID;
    
    @FXML
    private ProgressBar progressBarID;

    @FXML
    private JFXTextField firstNameFieldID;

    @FXML
    private JFXTextField familyNameFieldID;

    @FXML
    private JFXTextField ageFieldID;

    @FXML
    private Label labelPatientIdentificationID;

    @FXML
    private TableView<TableAnalysis> tablePrescription;

    @FXML
    private TableColumn<TableAnalysis, Integer> cellPrescriptionNumberID;

    @FXML
    private TableColumn<TableAnalysis, Integer> cellDiagnosisID;

    @FXML
    private TableColumn<TableAnalysis, String> cellCreationDateID;

    @FXML
    private TableColumn<TableAnalysis, String> cellStatusID;

    @FXML
    private VBox vboxDragDropID;

    @FXML
    private Label LabelDragDropID;

    @FXML
    private JFXButton btnConfirmID;
    
    File file;
    int prescription_id;
    
    ObservableList<TableAnalysis> prescriptionList = FXCollections.observableArrayList();


    /**
     * Initialises the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    void btnAddResultsFile(ActionEvent event) {
        try {
           Connection conn = ConnectionProvider.getCon();
           // Prepare the SQL statement
           String query = "SELECT analysis_results FROM analysis WHERE diagnosis_report_id = 10";
           PreparedStatement statement = conn.prepareStatement(query);

           // Execute the SQL statement
           ResultSet rs = statement.executeQuery();

           if (rs.next()) {
               // Read the file data from the result set
               InputStream inputStream = rs.getBinaryStream("analysis_results");

               // Create a temporary file to store the data
               File tempFile = File.createTempFile("temp", ".pdf");

               // Write the data to the temporary file
               FileOutputStream outputStream = new FileOutputStream(tempFile);
               byte[] buffer = new byte[4096];
               int bytesRead;
               while ((bytesRead = inputStream.read(buffer)) != -1) {
                   outputStream.write(buffer, 0, bytesRead);
               }
               outputStream.close();

               // Open the file preview using a suitable application
               Desktop.getDesktop().open(tempFile);
           }

           // Close resources
           rs.close();
           statement.close();
           conn.close();
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "" + e.toString());
           System.out.println("Error in connection: " + e.toString());
       }
    }

    @FXML
    void btnConfirm(ActionEvent event) {
        if(tablePrescription.getSelectionModel().isEmpty()){
            Toast.makeText((Stage) firstNameFieldID.getScene().getWindow(), "Select Prescription To add the result file please", 1500, 500, 500);
            return;
        }
        if(!progressBarID.isVisible()){
            Toast.makeText((Stage) firstNameFieldID.getScene().getWindow(), "Add The Result file please", 1500, 500, 500);
            return;
        }
        
        storeFileToDB(file);
        tablePrescription.getItems().clear();
        progressBarID.setVisible(false);
        checkMarkID.setVisible(false);
        fillPrescriptionTable();
        Toast.makeText((Stage) firstNameFieldID.getScene().getWindow(), "file uploaded successfuly in the DataBase", 1500, 500, 500);
        btnConfirmID.setDisable(true);
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
            new jasperAnalysis(diagnosisID,con);
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
        btnConfirmID.setDisable(true);
        progressBarID.setVisible(false);
        checkMarkID.setVisible(false);
        LabelDragDropID.setText("Drag and Drop the Results file");

        tablePrescription.getItems().clear();

        labelPatientIdentificationID.setText("Patient not Identified");
        labelPatientIdentificationID.setTextFill(Color.valueOf("#ff0000"));
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
    void searchByPatientID(ActionEvent event) {
        fillPrescriptionTable();
    }
    
    @FXML
    void handleDragDropped(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        boolean success = false;
    
        if (dragboard.hasFiles()) {
            // Assuming you want to handle only one file at a time
            file = dragboard.getFiles().get(0);
            // Check if the dropped file is a PDF
            if (file.getName().endsWith(".pdf") || file.getName().endsWith(".PDF")) {
                // TODO: Handle the PDF file here
                uploadFileProgress(file);
                success = true;


            }
            System.err.println("Prblm in dropping the file");
        }

        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    void handleDragOver(DragEvent event) {
        if(tablePrescription.getSelectionModel().isEmpty()){
            Toast.makeText((Stage) firstNameFieldID.getScene().getWindow(), "Select Prescription To add the result file please", 1500, 500, 500);
            //JOptionPane.showMessageDialog(null,"Select a Prescription first");
            return;
        }
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY);
        }
        LabelDragDropID.setText("Drop the File !");
        int selectedID = tablePrescription.getSelectionModel().getSelectedIndex();
        prescription_id = tablePrescription.getItems().get(selectedID).getPrescriptionNum();
        System.err.println("Selected Prescription ID : "+prescription_id);
        event.consume();
    }

    private void uploadFileProgress(File file) {
        progressBarID.setVisible(true);
        // Simulating upload process with a loop
        double progress = 0.0;
        LabelDragDropID.setText("Uploading in progress...");
        while (progress < 1.0) {
            // Update progress bar
            progressBarID.setProgress(progress);

            // Simulate time delay for demonstration purposes
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Update progress
            progress += 0.1;
        }

        // Upload completed
        progressBarID.setProgress(1.0);
        LabelDragDropID.setText("File uploaded");
        btnConfirmID.setDisable(false);
        checkMarkID.setVisible(true);
    }
    
    private void storeFileToDB(File file){
        try {
               Connection conn = ConnectionProvider.getCon();
               // Prepare the SQL statement
               String query = "UPDATE analysis SET finished_date= CURRENT_TIMESTAMP, analysis_results = ? WHERE id ='"+prescription_id+"'";
               PreparedStatement statement = conn.prepareStatement(query);

               // Read the file contents
               InputStream inputStream = new FileInputStream(file);

               // Set the file data parameter
               statement.setBlob(1, inputStream);

               // Execute the SQL statement
               statement.executeUpdate();

               // Close resources
               inputStream.close();
               statement.close();
               conn.close();

               // File stored successfully
               LabelDragDropID.setText("File uploaded in the database");
               btnConfirmID.setDisable(false);


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,""+e.toString());
            System.out.println("Error in connection"+e.toString());
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
        String status = "wating";
        try {
            Connection con= ConnectionProvider.getCon();
            String sql = "SELECT pr.id, pr.diagnosis_report_id, pr.created_date, pr.finished_date, " +
                         "dr.id, dr.patient_id,  " +
                         "p.id, p.id_card_number, p.first_name, p.family_name, p.age " +
                         "FROM analysis pr " +
                         "JOIN diagnosis_reports dr ON pr.diagnosis_report_id = dr.id " +
                         "JOIN patients p ON dr.patient_id = p.id " +
                         "WHERE p.id_card_number = ?";
            
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, idCardNumber);
            
            // Execute the query
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                
                if(String.valueOf(rs.getDate("pr.finished_date"))!="null"){
                    status = "Done in : "+rs.getString("pr.finished_date");
                }
                
                prescriptionList.add(new TableAnalysis(rs.getInt("pr.id"), rs.getInt("pr.diagnosis_report_id"), String.valueOf(rs.getTimestamp("pr.created_date")),
                        status));
                System.out.println(rs.getInt("pr.id")+" "+rs.getInt("pr.diagnosis_report_id")+" "+String.valueOf(rs.getTimestamp("pr.created_date"))+" "+status+"\n");
                status="wating";
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
        
        cellPrescriptionNumberID.setCellValueFactory(new PropertyValueFactory<TableAnalysis, Integer>("prescriptionNum"));
        cellDiagnosisID.setCellValueFactory(new PropertyValueFactory<TableAnalysis, Integer>("diagnosisId"));
        cellCreationDateID.setCellValueFactory(new PropertyValueFactory<TableAnalysis, String>("creationdate"));
        cellStatusID.setCellValueFactory(new PropertyValueFactory<TableAnalysis, String>("status"));
        tablePrescription.setItems(prescriptionList);
    }

}
