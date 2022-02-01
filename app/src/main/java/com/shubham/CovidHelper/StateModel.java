package com.shubham.CovidHelper;

public class StateModel {
    private String state;
    private int recover, death, scase;

    public StateModel() {
    }
    public StateModel(String state , int recover , int death , int scase)
    {
        this.state=state;
        this.recover=recover;
        this.death=death;
        this.scase=scase;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getRecover() {
        return recover;
    }

    public void setRecover(int recover) {
        this.recover = recover;
    }

    public int getDeath() {
        return death;
    }

    public void setDeath(int death) {
        this.death = death;
    }

    public int getScase() {
        return scase;
    }

    public void setScase(int scase) {
        this.scase = scase;
    }
}
