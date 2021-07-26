package pro.kidss.model;

import java.util.ArrayList;

public class DataClass {
    private ArrayList<WhatsappModel> arrayList = new ArrayList<>();

    public void setArrayListItem(WhatsappModel whatsappModel) {
        arrayList.add( whatsappModel );
    }

    public ArrayList<WhatsappModel> getArrayList() {
        return arrayList;
    }
}
