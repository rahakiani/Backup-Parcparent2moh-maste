package pro.kidss.model;

public class WhatsappModel {
    private String path;
    private String FileName;

    public WhatsappModel(String path, String fileName) {
        this.path = path;
        FileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public String getFileName() {
        return FileName;
    }

    @Override
    public String toString() {
        return "{" +
                "FileName='" + FileName + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
