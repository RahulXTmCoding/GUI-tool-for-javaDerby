import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.AbstractTableModel;
class view extends JFrame implements ActionListener,SqlListener
{
private JTextField sql;
private JTextArea error,output;
private JButton connect,quit,disconnect;
private JButton run;
private JButton menu;
private JMenuBar mb;
private JPanel p8;
private JPanel pTable; 
private JTable table; 
private JScrollPane jsp; 
private MyModel m;
private Object[][] data ;
private String[] title;
private MyClass myClass;
private int flag;
private JLabel moduleTitleLabel,errorLabel,outputLabel,sqlLabel;
view()
{
super("GUI client tool for java derby");
data = new Object[1][2];
data[0][0]=1;
data[0][1]="sajal";
title = new String[0];
m = new MyModel();
myClass=MyClass.getInstance();
myClass.setSqlListener(this);
moduleTitleLabel=new JLabel("GUI client tool for java derby");
flag=0;
errorLabel =new JLabel("Error");
outputLabel= new JLabel("Output");
sqlLabel= new JLabel("SQL  Statement");

sql=new JTextField();
error=new JTextArea();
output=new JTextArea();
error.setBackground(Color.black);
error.setForeground(Color.red);
output.setForeground(Color.green);
Font fn=new Font("Verdana",Font.PLAIN,16);  
error.setFont(fn); 
output.setFont(fn);
error.setLineWrap(true);
output.setLineWrap(true);

connect=new JButton("Connect");
disconnect=new JButton("Disconnect");
quit=new JButton("Quit");
menu=new JButton("Menu");
run=new JButton("run");

menu.setVisible(true);
connect.setVisible(false);
disconnect.setVisible(false);
quit.setVisible(false);

connect.setEnabled(true);
disconnect.setEnabled(false);
quit.setEnabled(false);

connect.addActionListener(this);
disconnect.addActionListener(this);
quit.addActionListener(this);
run.addActionListener(this);
menu.addActionListener(this);

JPanel p1=new JPanel();
p1.setLayout(null);
p1.setBounds(0,0,200,200);
menu.setBounds(0,0,150,30);
connect.setBounds(0,35,150,30);
disconnect.setBounds(0,70,150,30);
quit.setBounds(0,105,150,30);
p1.add(menu);
p1.add(connect);
p1.add(disconnect);
p1.add(quit);
p1.setBorder(BorderFactory.createLineBorder(Color.black));
JPanel p2=new JPanel();

p2.setLayout(new GridLayout(3,5));
p2.add(new JLabel(" "));
p2.add(new JLabel(" "));
p2.add(new JLabel(" "));
p2.add(new JLabel(" "));
p2.add(new JLabel(" "));
p2.add(new JLabel(" "));
p2.add(new JLabel(" "));
p2.add(moduleTitleLabel);
p2.add(new JLabel(" "));
p2.add(new JLabel(" "));
p2.add(new JLabel(" "));
p2.add(new JLabel(" "));
p2.add(new JLabel(" "));
p2.add(new JLabel(" "));
p2.add(new JLabel(" "));
p2.setBorder(BorderFactory.createLineBorder(Color.black));

JPanel p3=new JPanel();
p3.setLayout(null);
p3.setBounds(0,0,800,60);
sqlLabel.setBounds(10,0,500,29);
sql.setBounds(0,30,650,30);
run.setBounds(653,30,60,30);
p3.add(sqlLabel);
p3.add(sql);
p3.add(run);
p3.setBorder(BorderFactory.createLineBorder(Color.black));

JPanel p4=new JPanel();
p4.setLayout(null);
p4.setBounds(0,60,800,340);
errorLabel.setBounds(15,0,150,30);
error.setBounds(0,31,713,339);
p4.add(errorLabel);
p4.add(error);
p4.setBorder(BorderFactory.createLineBorder(Color.black));

JPanel p5=new JPanel();
p5.setLayout(null);
p5.setBounds(0,0,1102,402);
p5.add(p3);
p5.add(p4);
p5.setBorder(BorderFactory.createLineBorder(Color.black));
JPanel p7=new JPanel();
p7.setLayout(new GridLayout(1,2));
p7.add(p1);
p7.add(p5);
p7.setBorder(BorderFactory.createLineBorder(Color.black));



pTable=new JPanel();
table=new JTable(m); 
Font f=new Font("Verdana",Font.PLAIN,16); 
table.setFont(f); 
table.setRowHeight(30); 
jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS); 
pTable.setLayout(new BorderLayout()); 
pTable.add(jsp,BorderLayout.CENTER);   
pTable.setVisible(false); 
pTable.setBounds(15,30,1440,270);

p8=new JPanel();
p8.setLayout(null);
outputLabel.setBounds(15,0,150,30);
output.setBounds(15,30,1440,270);
output.setVisible(true);
p8.add(outputLabel);
p8.add(pTable);
p8.add(output);
p8.setBorder(BorderFactory.createLineBorder(Color.black));
JPanel p9=new JPanel();
p9.setLayout(new BoxLayout(p9, BoxLayout.Y_AXIS));
p9.add(p7);
p9.add(p8); 
add(p2,BorderLayout.NORTH);
add(p9,BorderLayout.CENTER);

p9.setBorder(BorderFactory.createLineBorder(Color.black));
setLocation(10,10);
setSize(1500,800);
setVisible(true);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

}


public void actionPerformed(ActionEvent e)
{

if(e.getSource()==connect)
{
MyConnection c = new MyConnection();
connect.setEnabled(false);
disconnect.setEnabled(true);
quit.setEnabled(true);
}

if(e.getSource()==disconnect)
{
myClass.disConnect();
connect.setEnabled(true);
disconnect.setEnabled(false);
quit.setEnabled(false);

}

if(e.getSource()==quit)
{
myClass.disConnect();
connect.setEnabled(true);
disconnect.setEnabled(false);
quit.setEnabled(false);
System.exit(0);
}

if(e.getSource()==menu)
{
if(flag==0)
{
flag=1;
connect.setVisible(true);
disconnect.setVisible(true);
quit.setVisible(true);
}
else
{
if(flag==1)
{
flag=0;
connect.setVisible(false);
disconnect.setVisible(false);
quit.setVisible(false);
}
}
}

if(e.getSource()==run)
{
String s=sql.getText();
if(s.trim().length()==0)
{
error.setText("Please! enter sql statement");
}
else
{
if(s.trim().indexOf("select") != -1) myClass.myExecuteQuery(s.trim());
else myClass.myExecuteUpdate(s.trim());
}
}
}


public void setErrorText(String s)
{
error.setText(s);
output.setText("");
}

public void setOutputText(String s)
{
pTable.setVisible(false);
output.setVisible(true);
output.setText(s);
error.setText("");
}


public void setTable(Object[][] data, String [] title)
{
this.data=data;
this.title=title;
AbstractTableModel atm = (AbstractTableModel) table.getModel();
atm.fireTableStructureChanged();
output.setVisible(false);
pTable.setVisible(true);
error.setText("");
}


//inner class



public class MyModel extends AbstractTableModel 
{ 
public MyModel() 
{ 

} 
public int getColumnCount() 
{ 
return title.length; 
} 
public String getColumnName(int columnIndex) 
{ 
return title[columnIndex]; 
} 
public int getRowCount() 
{ 
return data.length; 
} 
public boolean isCellEditable(int rowIndex,int columnIndex) 
{  
return false; 
} 
public Object getValueAt(int rowIndex,int columnIndex) 
{ 
return data[rowIndex][columnIndex]; 
} 
/*
public Class getColumnClass(int columnIndex) 
{ 
Class c=null; 
try 
{ 
if(columnIndex==0 || columnIndex==1) 
{
 c=Class.forName("java.lang.Integer"); 
} 
if(columnIndex==2) 
{ 
c=Class.forName("java.lang.String"); 
} 
if(columnIndex==3) 
{
 c=Class.forName("java.lang.Boolean"); 
} 
}catch(ClassNotFoundException cnfe) { } 
return c;
}
public void setValueAt(Object d,int rowIndex,int columnIndex)
 { 
data[rowIndex][columnIndex]=d; 
} 
*/

} 

public void drawDatabaseTable(String [] tables)
{

}
public static void main(String gg[])
{
try
{
view a=new view();
}catch(Exception e)
{
System.out.println(e);
}
}

}
