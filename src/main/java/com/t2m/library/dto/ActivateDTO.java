package com.t2m.library.dto;

public class ActivateDTO {

    private int id;
    private boolean activated;
    public ActivateDTO() {

    }
    public ActivateDTO(int id, boolean activated) {}
    public int getId() {
        return id;
    }
    public void setId(int id) {}
    public boolean isActivated() {return activated;}
    public void setActivated(boolean activated) {}

}
