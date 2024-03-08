package org.example;

public class Client{
    private int id;
    private int arrivalTime;
    private int serviceTime;
    public int getId(){
        return id;
    }
    public int getArrivalTime(){
        return arrivalTime;
    }
    public int getServiceTime(){
        return serviceTime;
    }
    public void setId(int i){
        id=i;
    }
    public void setArrivalTime(int a){
        arrivalTime=a;
    }
    public void setServiceTime(int s){
        serviceTime=s;
    }

}
