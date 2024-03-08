package GUI;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

import org.example.Client;
import org.example.Coada;
import org.example.SimulationManager;

import javax.swing.*;
import java.util.List;
public class TableFrame extends JLabel {
    private  JPanel panel = new JPanel();
    private  JFrame frame = new JFrame();
    private static int max = 0;
    private static float aS=0;
    private static int hour =0;
    private static float avrWaiting;
    private static JScrollPane scrollPane = new JScrollPane();
    public TableFrame(){
        frame.setTitle("Log of events");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(500,500);

        scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(500,500));
        frame.add(scrollPane);
        frame.setVisible(true);
    }

    public void addTextArea(int sim_interval, int nr_queues, int time, List<Client> waitingClients, List<Coada> queues, float avrServ){
        int inc=0;
        if(time == 0){
            inc=1;
           aS=avrServ;
        }
        int sf=0;
        if(time == sim_interval-1){
            sf=1;
        }

        JTextArea area = new JTextArea(nr_queues,500);
        area.setEditable(false);

        area.append("Time: " + time + "\n");
        area.append("Waiting clients: ");

        for(int i=0;i<waitingClients.size();i++){
            area.append("(" + waitingClients.get(i).getId() + "," + waitingClients.get(i).getArrivalTime() + "," + waitingClients.get(i).getServiceTime() + ");");
        }
        int totalClienti=0;
        area.append("\n");
        for(int i=0;i<queues.size();i++){ //parcurg lista cu cozi
            avrWaiting+=queues.get(i).getAverageWaiting();
            int j=i+1;
            int clientiCoadaActuala=0;
            area.append("Queue " + j + ": ");
            if(queues.get(i).getNrClientiDinCoada() == 0){ //daca size la lista de clientii a cozii = 0 => closed
                area.append("closed\n");
            }
            else{ //daca size la lista de clienti a cozii !=0 => parcurgem lista de clienti a cozii si scriem clientii
                int sizeCoadaActuala = queues.get(i).getNrClientiDinCoada();
                for(int k=0;k<sizeCoadaActuala;k++){
                    clientiCoadaActuala++;
                    area.append("("+queues.get(i).getListClienti().get(k).getId()+ "," + queues.get(i).getListClienti().get(k).getArrivalTime() + "," + queues.get(i).getListClienti().get(k).getServiceTime() + ");");
                }
                area.append("\n");
            }
            totalClienti += clientiCoadaActuala;
        }

        if(totalClienti>max){
            max=totalClienti;
            hour=time;
        }

        if(sf==1) {
            avrWaiting=avrWaiting/nr_queues;
            area.append("Average waiting time: " +avrWaiting+ "\n");
            area.append("Average service time: " +aS+ "\n");
            area.append("Peak hour: " +hour+  "\n");
        }

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(area);
        panel.revalidate();
        panel.repaint();
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());

        try {
            FileWriter myWriter = new FileWriter("log.txt", true);
            if(inc==1){
                myWriter = new FileWriter("log.txt", false);
            }
            myWriter.write(area.getText());
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

