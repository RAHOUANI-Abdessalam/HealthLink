/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author abdel
 */
public class ListDiagnosisItem {
    private int id_diag,id_patient;
    private String date;
    private String diagnosis;
    private String prescription;
    private String analysis;
    private String doctorContact,prescription_type;

    public ListDiagnosisItem(int id_diag, int id_patient, String date, String diagnosis, String prescription, String analysis, String doctorContact, String prescription_type) {
        this.id_diag = id_diag;
        this.id_patient = id_patient;
        this.date = date;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.analysis = analysis;
        this.doctorContact = doctorContact;
        this.prescription_type = prescription_type;
    }

    public int getId_diag() {
        return id_diag;
    }

    public void setId_diag(int id_diag) {
        this.id_diag = id_diag;
    }

    public int getId_patient() {
        return id_patient;
    }

    public void setId_patient(int id_patient) {
        this.id_patient = id_patient;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getDoctorContact() {
        return doctorContact;
    }

    public void setDoctorContact(String doctorContact) {
        this.doctorContact = doctorContact;
    }

    public String getPrescription_type() {
        return prescription_type;
    }

    public void setPrescription_type(String prescription_type) {
        this.prescription_type = prescription_type;
    }

    
    
        // toString method
    @Override
    public String toString() {
        return "Date: " + date +
               "\nDiagnosis: " + diagnosis +
               //"\nPrescription: \n" + prescription +
               "\nRequested Analysis: \n\n" + analysis +
               "\nDoctor contact: " + doctorContact;
    }
    
}
