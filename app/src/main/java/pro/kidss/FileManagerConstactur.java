package pro.kidss;

import java.util.ArrayList;

public class FileManagerConstactur {
    private ArrayList<DataModel> dataModels;

    public ArrayList<DataModel> getDataModels() {
        return dataModels;
    }

    public void setDataModels(ArrayList<DataModel> dataModels) {
        this.dataModels = dataModels;
    }

    public FileManagerConstactur(ArrayList<DataModel> dataModels) {
        this.dataModels = dataModels;
    }
}
