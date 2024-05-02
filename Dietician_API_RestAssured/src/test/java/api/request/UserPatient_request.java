package api.request;



public class UserPatient_request {
    private int patientId;
    private int fileId;

    public UserPatient_request(int patientId, int fileId) {
        this.patientId = patientId;
        this.fileId = fileId;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getFileId() {
        return fileId;
    }
}
