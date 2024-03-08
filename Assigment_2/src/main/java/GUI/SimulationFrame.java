package GUI;

import org.example.SimulationManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
public class SimulationFrame extends JFrame{
    private JTextField t_clients = new JTextField(15);
    private JTextField t_queues = new JTextField(15);
    private JTextField t_interval = new JTextField(15);
    private JTextField t_min_arr= new JTextField(15);
    private JTextField t_max_arr= new JTextField(15);
    private JTextField t_min_ser= new JTextField(15);
    private JTextField t_max_ser= new JTextField(15);
    private JButton b_validate = new JButton("Validate");
    private JButton b_start = new JButton("Start");
    private JTextField t_mess= new JTextField(30);
    private JButton b_clear = new JButton("Clear");
    public SimulationFrame(){
        JPanel panel = new JPanel();

        panel.add(new JLabel("Number of clients:"));
        panel.add(t_clients);
        panel.add(new JLabel("Number of queues:"));//.setForeground(Color.getHSBColor())
        panel.add(t_queues);
        panel.add(new JLabel("Simulation interval:"));
        panel.add(t_interval);
        panel.add(new JLabel("Minimum arrival time:"));
        panel.add(t_min_arr);
        panel.add(new JLabel("Maximum arrival time:"));
        panel.add(t_max_arr);
        panel.add(new JLabel("Minimum service time:"));
        panel.add(t_min_ser);
        panel.add(new JLabel("Maximum service time:"));
        panel.add(t_max_ser);

        panel.add(Box.createVerticalStrut(10));

        panel.add(b_validate);
        panel.add(b_start);
        t_mess.setEditable(false);
        panel.add(t_mess);
        panel.add(b_clear);

        this.setContentPane(panel);
        this.pack();

        this.setSize(350,300);
        this.getContentPane().setBackground(new Color(138,184,187  ));
        this.setTitle("Queues Management Application");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        b_validate.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
               try {
                    int min_arr = Integer.parseInt(t_min_arr.getText());
                    int max_arr = Integer.parseInt(t_max_arr.getText());
                    int min_ser = Integer.parseInt(t_min_ser.getText());
                    int max_ser = Integer.parseInt(t_max_ser.getText());
                    if (min_ser > max_ser || min_arr > max_arr) {
                        t_mess.setText("Invalid values!");
                        b_start.setEnabled(false);
                    } else {
                        t_mess.setText("Start the simulation!");
                        b_start.setEnabled(true);
                    }
                }
               catch(Exception exception){
                   t_mess.setText("Invalid values!");
               }
            }
        });
        b_start.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                SimulationManager sim= new SimulationManager(t_clients.getText(),t_queues.getText(),t_interval.getText(),t_min_arr.getText(),t_max_arr.getText(),t_min_ser.getText(),t_max_ser.getText());
                //SimulationManager sim = new SimulationManager("5","2","10","1","4","1","4");
                Thread t = new Thread(sim);
                t.start();
            }
        });
        b_clear.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                t_clients.setText("");
                t_queues.setText("");
                t_interval.setText("");
                t_min_arr.setText("");
                t_max_arr.setText("");
                t_min_ser.setText("");
                t_max_ser.setText("");
            }
        });
    }
}
