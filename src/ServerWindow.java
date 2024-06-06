import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ServerWindow extends JFrame{
    private JTextArea textField;
    private boolean serverWork;
    private List<ClientGUI> clients;

    ServerWindow(){
        serverWork = false;
        clients = new ArrayList<ClientGUI>();
        JFrame serverWindow = new JFrame();
        serverWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        serverWindow.setLocation(700,200);
        serverWindow.setSize(400,400);
        serverWindow.setTitle("Server Chat");
        serverWindow.add(btn(), BorderLayout.NORTH);
        serverWindow.add(text(),BorderLayout.CENTER);
        serverWindow.setVisible(true);

    }
    private  JPanel btn(){
        JPanel panel = new JPanel();
        JButton btnStart = new JButton("Start");
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setText("Сервер запущен!\n");
                serverWork = true;
            }
        });
        JButton btnStop = new JButton("Stop");
        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.append("Сервер остановлен!\n");
                serverWork = false;
            }
        });
        panel.add(btnStart);
        panel.add(btnStop);
        return panel;
    }
    private JPanel text(){
        JPanel text = new JPanel();
        textField = new JTextArea(15,35);
        textField.setLineWrap(true);
        JScrollPane area = new JScrollPane(textField);
        text.add(area);
        return text;
    }

    public boolean isServerWork() {
        return serverWork;
    }
    public  void setMessageToServerTextField(String username,String txt){
        if(isServerWork()) {
            textField.append(txt);
            for (ClientGUI cl : clients) {
                if (cl.getUserName().getText() != username) {
                    JTextArea cli = cl.getFieldChat();
                    cli.append(txt);
                    String str = cl.getUserName().getText() + ": " + txt;
                    saveChatHistory();
                    appendToChatHistory(str);

                }
            }
        }else {
            serverDisconnect();
        }


    }
    public void connected(String userName,String txt){
        textField.append(txt);

    }
    public void registrationClients(ClientGUI clientGUI){
        clients.add(clientGUI);
    }
    private void serverDisconnect(){
        textField.append("Сервер остановлен!\n");
        for (ClientGUI cl : clients){
            JTextArea clientsArea = cl.getFieldChat();
            clientsArea.setText("Соединение прервано!\n ");
        }
    }
    public void loadChatHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader("chat_history.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                for (ClientGUI cl : clients) {
                    cl.getFieldChat().append(line + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("No previous chat history found.");
        }
    }

    private void appendToChatHistory(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("chat_history.txt", true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveChatHistory() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("chat_history.txt"))) {
            writer.write(textField.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}