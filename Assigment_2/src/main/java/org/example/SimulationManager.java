package org.example;

import java.util.*;
import GUI.TableFrame;
public class SimulationManager implements Runnable{
    private int nr_clienti;
    private int nr_queues;
    private int sim_interval;
    private int min_arr;
    private int max_arr;
    private int min_ser;
    private int max_ser;
    private static List<Client> listaClienti;
    private List<Coada> listaQueues;
    private TableFrame tableFrame;

    public SimulationManager(String a, String b, String c, String d, String e, String f, String g){

        nr_clienti=Integer.parseInt(a);
        nr_queues=Integer.parseInt(b);
        sim_interval=Integer.parseInt(c);
        min_arr=Integer.parseInt(d);
        max_arr=Integer.parseInt(e);
        min_ser=Integer.parseInt(f);
        max_ser=Integer.parseInt(g);

        listaQueues = new ArrayList<Coada>();
        for(int i=0;i<nr_queues;i++){
            Coada coada = new Coada();
            listaQueues.add(coada);
        }
        listaClienti = new ArrayList<Client>();
        listaClienti=generateRandomClient();
        tableFrame = new TableFrame();
    }

    public float getAverageService(){
        float n = 0;
        for(int i=0;i<listaClienti.size();i++){
            n+=listaClienti.get(i).getServiceTime();
        }
        n=n/nr_clienti;
        return n;
    }

    public List<Client> generateRandomClient(){
        List<Client> lista = new ArrayList<Client>();
        Random ra = new Random();
        Random rs = new Random();
        for(int i=0;i<nr_clienti;i++){
            Client c = new Client();
            c.setArrivalTime(ra.nextInt(max_arr-min_arr+1)+min_arr);
            c.setServiceTime(rs.nextInt(max_ser-min_ser+1)+min_ser);
            lista.add(c);
        }
        Collections.sort(lista,new ComparatorArrival()); //sortare in functie de arrival time
        int id=1;
        for(int i=0;i<lista.size();i++){
            lista.get(i).setId(id);
            id++;
        }
        for(int i=0;i<lista.size();i++){
            System.out.println(lista.get(i).getId()+" , "+lista.get(i).getArrivalTime()+" , "+lista.get(i).getServiceTime());
        }
        return lista;
    }
    @Override
    public void run(){
        synchronized (this) {
            int currentTime = 0;
            while (currentTime < sim_interval) {
                int i = 0;
                int size = listaClienti.size();
                while (i < size) {
                    if (listaClienti.get(i).getArrivalTime() == currentTime) {
                        addClient(listaClienti.get(i));
                        listaClienti.remove(listaClienti.get(i));
                        size--;
                    } else i++;
                }
                tableFrame.addTextArea(sim_interval, nr_queues, currentTime, listaClienti, listaQueues,this.getAverageService());
                currentTime++;
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
            }
        }
    }
    public void addClient(Client newClient){
        int min = listaQueues.get(0).getWaitingPeriod();
        int minQueue = 0;
        for(int i=0;i<listaQueues.size();i++){
            if(listaQueues.get(i).getWaitingPeriod()<min){
                min = listaQueues.get(i).getWaitingPeriod();
                minQueue = i;
            }
        }
        listaQueues.get(minQueue).addClientToQueue(newClient);
    }
}

class ComparatorArrival implements Comparator<Client>{
    @Override
    public int compare(Client a, Client b){
        return a.getArrivalTime()-b.getArrivalTime();
    }
}