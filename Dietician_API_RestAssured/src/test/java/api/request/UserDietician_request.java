package api.request;

public class UserDietician_request {
    private int patientId;
    private int fileId;

    public UserDietician_request(int patientId, int fileId) {
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
