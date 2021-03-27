package com.hummo.hummigo.medicine;

public class MedicineHelper {

    String medicinename;
    String medicinedesc;
    int medicinepriority;


    public MedicineHelper() {

    }

    public MedicineHelper(String medicinename, String medicinedesc, int medicinepriority) {
        this.medicinename = medicinename;
        this.medicinedesc = medicinedesc;
        this.medicinepriority = medicinepriority;
    }



    public  String getMedicinename() {
        return medicinename;
    }

    public void setMedicinename(String medicinename) {
        this.medicinename = medicinename;
    }

    public String getMedicinedesc() {
        return medicinedesc;
    }

    public void setMedicinedesc(String medicinedesc) {
        this.medicinedesc = medicinedesc;
    }

    public int getMedicinepriority() {
        return medicinepriority;
    }

    public void setMedicinepriority(int medicinepriority) {
        this.medicinepriority = medicinepriority;
    }
}

