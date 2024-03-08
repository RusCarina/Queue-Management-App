package org.example;

import java.util.*;
import java.util.concurrent.BlockingQueue;

public class Coada implements Runnable {
    private List<Client> clienti;
    private int waitingPeriod;
    public Coada() {
        clienti = new ArrayList<Client>();
        waitingPeriod = 0;
        Thread thr=new Thread(this);
        thr.start();
    }

    public synchronized void addClientToQueue(Client clientNew) {
        clienti.add(0, clientNew);
        waitingPeriod += clientNew.getServiceTime();
    }

    public float getAverageWaiting(){
        float n=0;
        for(int i=0;i<clienti.size()-1;i++){
            n+=clienti.get(i).getServiceTime();
        }
        return n;
    }

    public void run() {
        try {
            while (true) {
                synchronized (this) {
                    if (!clienti.isEmpty()) {
                        clienti.get(clienti.size() - 1).setServiceTime(clienti.get(clienti.size() - 1).getServiceTime() + 1);
                        while (clienti.get(clienti.size() - 1).getServiceTime() > 0) {
                            if (clienti.get(clienti.size() - 1).getServiceTime() == 1) {
                                clienti.remove(clienti.get(clienti.size() - 1));
                            } else
                                clienti.get(clienti.size() - 1).setServiceTime(clienti.get(clienti.size() - 1).getServiceTime() - 1);
                            waitingPeriod--;
                            try {
                                wait(1000);

                            } catch (Exception e) {
                            }
                        }
                    }
                }
            }
        }catch (Exception e){}
    }

    public List<Client> getListClienti(){
        return clienti;
    }
    public int getNrClientiDinCoada(){
        return clienti.size();
    }
    public int getWaitingPeriod(){
        return waitingPeriod;
    }

}
