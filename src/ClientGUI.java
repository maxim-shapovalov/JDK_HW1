import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class ClientGUI extends JFrame{
    private JLabel labelName;
    private JLabel labelPassword;
    private JLabel labelIp;
    private JLabel labelPort;
    private JTextField userName;
    private JTextField userPassword;
    private JTextField ip;
    private JTextField port;
    private boolean serverConnect;
    private JTextArea fieldChat;
    private JTextArea message;
    private ServerWindow serverWindow;


    ClientGUI(ServerWindow serverWindow){
        this.serverWindow = serverWindow;
        serverWindow.registrationClients(this);
        serverConnect = serverWindow.isServerWork();
        JFrame clientChat = new JFrame();
        clientChat.setDefaultCloseOperation(EXIT_ON_CLOSE);
        clientChat.setLocation(300,200);
        clientChat.setSize(400,400);
        clientChat.setTitle("Client Chat");
        clientChat.add(topPanel(),BorderLayout.NORTH);
        clientChat.add(field(),BorderLayout.CENTER);
        clientChat.add(messageAndSend(),BorderLayout.SOUTH);


        clientChat.setVisible(true);

    }
    private JPanel topPanel(){
        JPanel topPanel = new JPanel(new GridLayout(5,2));
        labelName = new JLabel("User name: ");
        userName = new JTextField();
        labelPassword = new JLabel("User password");
        userPassword  = new JTextField();
        labelIp = new JLabel("ip adrese");
        ip  = new JTextField("172.0.0.1");
        labelPort = new JLabel("port");
        port = new JTextField("8080");
        JButton btnLogin = new JButton("login");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!userName.getText().isEmpty() && !userPassword.getText().isEmpty() && serverWindow.isServerWork()){
                    fieldChat.setText("Вы успешно подключились!\n");
                    serverWindow.connected(userName.getText(),"Пользователь " + userName.getText()
                            +  " подключился к беседе \n");
                    serverWindow.loadChatHistory();

                }
                else if(!userName.getText().isEmpty() && !userPassword.getText().isEmpty() && !serverWindow.isServerWork()){
                    fieldChat.setText("Сервер отключен!\n");


                }
                else if(userName.getText().isEmpty() || userPassword.getText().isEmpty()){
                    fieldChat.setText("Поля имя и пароль не должны быть пустыми!\n");
                }

            }
        });

        topPanel.add(labelName);
        topPanel.add(userName);
        topPanel.add(labelPassword);
        topPanel.add(userPassword);
        topPanel.add(labelIp);
        topPanel.add(ip);
        topPanel.add(labelPort);
        topPanel.add(port);
        topPanel.add(btnLogin,BorderLayout.CENTER);
        return topPanel;
    }
    private JPanel field(){
        JPanel field = new JPanel();
        fieldChat = new JTextArea(11,35);
        fieldChat.setLineWrap(true);
        JScrollPane area = new JScrollPane(fieldChat);
        field.add(area);
        return field;
    }
    private JPanel messageAndSend(){
        JPanel messageAndSend = new JPanel(new GridBagLayout());
        message = new JTextArea();
        JButton btnSend = new JButton("отправить");
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        GridBagConstraints gbc= new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        messageAndSend.add(message, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        messageAndSend.add(btnSend, gbc);
        return messageAndSend;
    }
    private  void sendMessage(){
        String str = message.getText();
        serverWindow.setMessageToServerTextField( userName.getText(),userName.getText() + ": " +str + "\n");


    }


    public JTextArea getFieldChat() {
        return fieldChat;
    }

    public JTextField getUserName() {
        return userName;
    }
}
