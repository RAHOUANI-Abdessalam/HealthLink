
import java.sql.Timestamp;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author abdel
 */
public class TableAnalysis {
    int prescriptionNum,diagnosisId;
            /*SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dat = new Date();
            date.setText(dFormat.format(dat));*/
    String creationdate;
    String  status;

    public TableAnalysis(int prescriptionNum, int diagnosisId, String creationdate, String status) {
        this.prescriptionNum = prescriptionNum;
        this.diagnosisId = diagnosisId;
        this.creationdate = creationdate;
        this.status = status;
    }

    public int getPrescriptionNum() {
        return prescriptionNum;
    }

    public void setPrescriptionNum(int prescriptionNum) {
        this.prescriptionNum = prescriptionNum;
    }

    public int getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(int diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public String getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(String creationdate) {
        this.creationdate = creationdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

   
}
