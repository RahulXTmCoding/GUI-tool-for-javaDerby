import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
class MyConnection extends JFrame implements ActionListener
{
private JLabel portNumberLabel,serverNameLabel,databaseLabel;
private JTextField portNumber,serverName,databaseName;
private JButton connect ,cancel,create;
MyConnection ()
{

super("Connection Window");
portNumberLabel = new JLabel("Port Number");
serverNameLabel = new JLabel("Server Name");
databaseLabel = new JLabel("Database Name");

portNumber = new JTextField ();
serverName = new JTextField ();
databaseName = new JTextField ();

connect = new JButton("Connect");
cancel = new JButton("Cancel");
create = new JButton("Create");

JPanel p1 = new JPanel();
p1.setLayout(null);
portNumberLabel.setBounds(20,50,150,30);
p1.add(portNumberLabel);
portNumber.setBounds(200,50,150,30);
p1.add(portNumber);
serverNameLabel.setBounds(20,100,150,30);
p1.add(serverNameLabel);
serverName.setBounds(200,100,150,30);
p1.add(serverName);
databaseLabel.setBounds(20,150,150,30);
p1.add(databaseLabel);
databaseName.setBounds(200,150,150,30);
p1.add(databaseName);

JPanel p2 = new JPanel();
p2.setLayout(null);
connect.setBounds(30,50,120,30);
create.setBounds(160,50,120,30);
cancel.setBounds(290,50,120,30);
p2.add(connect);
p2.add(cancel);
p2.add(create);
JPanel p3 = new JPanel();
p3.setLayout(new GridLayout(2,1));
p3.add(p1);
p3.add(p2);
add(p3);                                      
setSize(500,400);
setVisible(true);
setLocationRelativeTo(null);

connect.addActionListener(this);
cancel.addActionListener(this);
create.addActionListener(this);
}

public void actionPerformed(ActionEvent e) 
{
if(e.getSource()==connect)
{
MyClass s=MyClass.getInstance();
s.connectToDatabase(portNumber.getText(),serverName.getText(),databaseName.getText(),"");
s.getTables();
dispose();
}
if(e.getSource()==create)
{
MyClass s=MyClass.getInstance();
s.connectToDatabase(portNumber.getText(),serverName.getText(),databaseName.getText(),";create=true");
dispose();

}
if(e.getSource()==cancel)
{
dispose();
}



}








}