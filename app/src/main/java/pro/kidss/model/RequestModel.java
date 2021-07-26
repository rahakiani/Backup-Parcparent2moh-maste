package pro.kidss.model;

public class RequestModel {
    private String fileName;

    public RequestModel(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
