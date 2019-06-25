package com.baseinfotech.juscep.model;


public class EquipmentsRowData {
    private Equipment equipment;
    private String days;
    private String quantity;

    public EquipmentsRowData(){
        days = "";
        quantity = "";
        equipment = null;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
