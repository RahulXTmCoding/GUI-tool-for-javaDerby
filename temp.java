import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.table.AbstractTableModel;
class temp extends JFrame implements ActionListener,SqlListener,ListSelectionListener
{
DefaultListModel<String> model;
private JList tableList;
private JTextField sql;
private JTextArea error,output;
//private JButton connect,quit,disconnect;
private JButton run;
//private JButton menu;
private JPanel p8;
private JPanel pTable,listPanel; 
private JTable table; 
private JScrollPane jsp; 
private MyModel m;
private Object[][] data ;
private String[] title;
private MyClass myClass;
private int flag;

private JMenuBar mb;
private JMenu menu;
public static JMenuItem connect,disconnect,quit;

private JLabel moduleTitleLabel,errorLabel,outputLabel,sqlLabel,dbTables;
temp()
{
super("GUI client tool for java derby");
data = new Object[0][0];
title = new String[0];
m = new MyModel();
myClass=MyClass.getInstance();
myClass.setSqlListener(this);
moduleTitleLabel=new JLabel("GUI client tool for java derby");
flag=0;
errorLabel =new JLabel("Error");
outputLabel= new JLabel("Output");
sqlLabel= new JLabel("SQL  Statement");
dbTables = new JLabel("Database Tables");
dbTables.setBounds(160,25,150,30);
Font fnn=new Font("Calibary",Font.BOLD,16); 
dbTables.setFont(fnn);

model = new DefaultListModel<>();
tableList= new JList<>( model );
tableList.setBounds(8,62,431,640);
tableList.setBorder(BorderFactory.createLineBorder(Color.black));

sql=new JTextField();
error=new JTextArea();
output=new JTextArea();
error.setBackground(Color.black);
error.setForeground(Color.red);
output.setForeground(Color.green);
Font fn=new Font("Verdana",Font.PLAIN,16);  
error.setFont(fn); 
output.setFont(fn);
sql.setFont(fn);
error.setLineWrap(true);
output.setLineWrap(true);



connect=new JMenuItem("Connect");
disconnect=new JMenuItem("Disconnect");
disconnect.setEnabled(false);
quit=new JMenuItem("QUIT");

menu=new JMenu("MENU");
menu.add(connect);
menu.add(disconnect);
menu.add(quit);
mb=new JMenuBar();
mb.add(menu);
setJMenuBar(mb);



run=new JButton("Run");

connect.setEnabled(true);
disconnect.setEnabled(false);
quit.setEnabled(false);

connect.addActionListener(this);
disconnect.addActionListener(this);
quit.addActionListener(this);
run.addActionListener(this);
menu.addActionListener(this);
tableList.addListSelectionListener(this);

JPanel p3=new JPanel();
p3.setLayout(null);
p3.setBounds(15,2,975,58);
sqlLabel.setBounds(9,0,500,29);
sql.setBounds(9,25,700,30);
run.setBounds(750,25,80,30);
p3.add(sqlLabel);
p3.add(sql);
p3.add(run);


JPanel p4=new JPanel();
p4.setLayout(null);
p4.setBounds(15,450,1000,280);
errorLabel.setBounds(15,0,150,30);
error.setBounds(20,30,970,230);
p4.add(errorLabel);
p4.add(error);
p4.setBorder(BorderFactory.createLineBorder(Color.black));

JPanel p5=new JPanel();
p5.setLayout(null);
p5.setBounds(15,5,1000,445);
p5.add(p3);
p5.setBorder(BorderFactory.createLineBorder(Color.black));




pTable=new JPanel();
table=new JTable(m); 
Font f=new Font("Verdana",Font.PLAIN,16); 
table.setFont(f); 
table.setRowHeight(30);
table.getTableHeader().setReorderingAllowed(false); 
jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS); 
pTable.setLayout(new BorderLayout()); 
pTable.add(jsp,BorderLayout.CENTER);   
pTable.setVisible(false); 
pTable.setBounds(15,30,984,355);

p8=new JPanel();
p8.setLayout(null);
p8.setBounds(0,61,1000,390);
output.setBorder(BorderFactory.createLineBorder(Color.black));

outputLabel.setBounds(15,0,150,30);
output.setBounds(20,30,970,350);
output.setVisible(true);
p8.add(outputLabel);
p8.add(pTable);
p8.add(output);
p8.setBorder(BorderFactory.createLineBorder(Color.black));

p5.add(p8);



listPanel = new JPanel();
listPanel.setLayout(null);
listPanel.setBounds(1020,4,445,725);
listPanel.setBorder(BorderFactory.createLineBorder(Color.black));
listPanel.add(dbTables);
listPanel.add(tableList);





JPanel p9=new JPanel();
p9.setLayout(null);
p9.add(p5);
p9.add(p4);
p9.add(listPanel); 
//add(p2,BorderLayout.NORTH);
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






 MouseListener mouseListener = new MouseAdapter() {
      public void mouseClicked(MouseEvent mouseEvent) {
        JList theList = (JList) mouseEvent.getSource();
        if (mouseEvent.getClickCount() == 2) {
          int index = theList.locationToIndex(mouseEvent.getPoint());
          if (index >= 0) {
            Object o = theList.getModel().getElementAt(index);
            System.out.println("Double-clicked on: " + o.toString());
          }
        }
      }
    };
    tableList.addMouseListener(mouseListener);








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
public void drawDatabaseTable(String [] tables)
{
for(String s:tables)
{
model.addElement(s);
}

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


public static void main(String gg[])
{
try
{
temp a=new temp();
}catch(Exception e)
{
System.out.println(e);
}
}

}
