import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import project.ConnectionProvider;

// Custom ListCell implementation
public class CustomItemCell extends ListCell<ListDiagnosisItem> {
    private ListView<ListDiagnosisItem> listView;

    public CustomItemCell(ListView<ListDiagnosisItem> listView) {
        this.listView = listView;
    }

    @Override
    protected void updateItem(ListDiagnosisItem item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
            setOnMouseClicked(null);
        } else {
            setText(item.toString());
            setGraphic(null);

            // Check if the item is a prescription and make it clickable
            /*if (item.getPrescription() != null && !item.getPrescription().isEmpty()) {
                setOnMouseClicked(event -> {
                    // Code to execute when the prescription is clicked
                    // For example, fetching data from the database and printing it in a PDF
                    String prescription = item.getPrescription();
                    // Your code here...

                    // Example code to display a dialog with the prescription content
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Prescription");
                    alert.setHeaderText(null);
                    alert.setContentText("Prescription: " + prescription);
                    alert.showAndWait();
                });*/
            if (item.getPrescription_type() != null && !item.getPrescription_type().isEmpty()) {
                setOnMouseClicked(event -> {
                    // Code to execute when the prescription is clicked
                    // For example, fetching data from the database and printing it in a PDF
                    String prescriptionType = item.getPrescription_type();
                    System.err.println("Prescription Type: "+prescriptionType);
                    // Your code here...
                    if(prescriptionType.equals("medicament")){
                        try {
                                Connection con = ConnectionProvider.getCon();
                                try {
                                    new jasperPrescription(item.getId_diag(),con);
                                } catch (Exception e) {
                                    //System.out.println("Error in new jasperPrescription"+e.toString());
                                    JOptionPane.showMessageDialog(null,"Error in new jasper "+e.toString());
                                    }
                                }catch (Exception e) {
                                        JOptionPane.showMessageDialog(null,""+e.toString());
                                        System.out.println("Error in connection "+e.toString());
                            }
                    }else{
                        if(prescriptionType.equals("analyse")){
                            try {
                                Connection con = ConnectionProvider.getCon();
                                try {
                                    new jasperAnalysis(item.getId_diag(),con);
                                } catch (Exception e) {
                                    //System.out.println("Error in new jasperPrescription"+e.toString());
                                    JOptionPane.showMessageDialog(null,"Error in new jasper "+e.toString());
                                    }
                                }catch (Exception e) {
                                        JOptionPane.showMessageDialog(null,""+e.toString());
                                        System.out.println("Error in connection "+e.toString());
                            }
                            //open the result file if exist
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
                        }else{
                            if(prescriptionType.equals("bouth")){
                            try {
                                    Connection con = ConnectionProvider.getCon();
                                    try {
                                        new jasperPrescription(item.getId_diag(),con);
                                        new jasperAnalysis(item.getId_diag(),con);
                                    } catch (Exception e) {
                                        //System.out.println("Error in new jasperPrescription"+e.toString());
                                        JOptionPane.showMessageDialog(null,"Error in new jasper "+e.toString());
                                        }
                                }catch (Exception e) {
                                    JOptionPane.showMessageDialog(null,""+e.toString());
                                    System.out.println("Error in connection "+e.toString());
                                }
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
                            }else{
                                if(prescriptionType.equals("neither")){
                                
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Files");
                                    alert.setHeaderText(null);
                                    alert.setContentText("There is no file for this Diagnosis");
                                    alert.showAndWait();
                                }
                            }
                        }
                    }
                });
            } else {
                setOnMouseClicked(null);
            }
        }
    }
}