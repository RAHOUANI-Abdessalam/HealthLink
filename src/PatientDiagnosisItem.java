
import org.exolab.castor.types.DateTime;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author abdel
 */
public class PatientDiagnosisItem {
    private String id_patient;
    private String id_doctor;
    private String symptoms;    
    private String disease_name;
    private String medicaments;
    private String analysis;
    private String diagnosis;
    private String doctornote;
    private String date;

    public PatientDiagnosisItem(String id_patient, String id_doctor, String symptoms, String disease_name, String medicaments, String analysis, String diagnosis, String doctornote, String date) {
        this.id_patient = id_patient;
        this.id_doctor = id_doctor;
        this.symptoms = symptoms;
        this.disease_name = disease_name;
        this.medicaments = medicaments;
        this.analysis = analysis;
        this.diagnosis = diagnosis;
        this.doctornote = doctornote;
        this.date = date;
    }

    public String getId_patient() {
        return id_patient;
    }

    public void setId_patient(String id_patient) {
        this.id_patient = id_patient;
    }

    public String getId_doctor() {
        return id_doctor;
    }

    public void setId_doctor(String id_doctor) {
        this.id_doctor = id_doctor;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getDisease_name() {
        return disease_name;
    }

    public void setDisease_name(String disease_name) {
        this.disease_name = disease_name;
    }

    public String getMedicaments() {
        return medicaments;
    }

    public void setMedicaments(String medicaments) {
        this.medicaments = medicaments;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getDoctornote() {
        return doctornote;
    }

    public void setDoctornote(String doctornote) {
        this.doctornote = doctornote;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    
}
