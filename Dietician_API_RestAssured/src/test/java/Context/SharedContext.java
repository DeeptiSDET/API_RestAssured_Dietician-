package Context;

public class SharedContext {

    private static int patientIdMissingFile;
    private static int patientIdValid;
    private static String existingPhoneNumber = null;
    private static String existingDateOfBirth = null;
    private static int fileId = 0;
    private static String existingEmail = null;

    public static int getPatientIdValid(){
        return patientIdValid;

    }    
    

    public static void setPatientIdValid(int patientId) {
        patientIdValid = patientId;
    }

    
    public static int getPatientIdMissingFile() {
        return patientIdMissingFile;
    }

    public static void setPatientIdMissingFile(int patientId) {
        patientIdMissingFile = patientId;
    }

    public static String getExistingPhoneNumber() {
        return existingPhoneNumber;
    }

    public  static void setExistingPhoneNumber(String phoneNumber) {
        existingPhoneNumber = phoneNumber;
    }
    public static String getDateOfBirth() {
        return existingDateOfBirth;
    }

    public  static void setDateOfBirth(String dateOfBirth) {
        existingDateOfBirth = dateOfBirth;
    }

    public static int getFileId() {
        return fileId;
    }

    public  static void setFileId(int fileIdValue) {
        fileId = fileIdValue;
    }

    public static String getExistingEmail() {
        return existingEmail;
    }

    public static void setExistingEmail(String email) {
        existingEmail = email;
    }
}

    
