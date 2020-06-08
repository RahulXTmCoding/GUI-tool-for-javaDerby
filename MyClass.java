import java.sql.*;
class MyClass
{
private static Connection c;
private boolean flag;
private SqlListener sqlListener;
private Object [][] data;
private String[] title;
private MyClass()
{
c=null;
flag=false;
sqlListener=null;
data=null;
title=null;
}

private static MyClass myClass=null;
public static MyClass getInstance()
{
if(myClass==null) myClass = new MyClass();
return myClass;
}
 
public void setSqlListener(SqlListener sqlListener)
{
this.sqlListener=sqlListener;
}



public void connectToDatabase(String vPortNumber,String vServerName,String vDataBase,String create)
{
try
{
Class.forName("org.apache.derby.jdbc.ClientDriver");
c=DriverManager.getConnection("jdbc:derby://"+vServerName+":"+vPortNumber+"/db/"+vDataBase+""+create+"");
// c=DriverManager.getConnection("jdbc:derby://localhost:1527/db/tmplacements");
sqlListener.setOutputText("Connected\n"+"path = jdbc:derby://"+vServerName+":"+vPortNumber+"/db/"+vDataBase);
}
catch(Exception e)
{
sqlListener.setErrorText(e.getMessage());
}
}

public void disConnect()
{
c=null;
sqlListener.setOutputText("Disconnect");
}




public void myExecuteUpdate(String sql)
{
if(c==null)
{
sqlListener.setErrorText("connect first");
}
else
{
try
{
Statement s=c.createStatement();
s.executeUpdate(sql);
s.close();
sqlListener.setOutputText("SQL statement execute successfully");
}
catch(Exception e)
{
sqlListener.setErrorText(e.getMessage());
}
}
}



public void myExecuteQuery(String sql)
{
if(c==null)
{
sqlListener.setErrorText("connect first");
}
else
{
try
{
ResultSet r;
Statement s = c.createStatement();
r = s.executeQuery(sql);
ResultSetMetaData rsmd = r.getMetaData();
int count = rsmd.getColumnCount();
title = new String[count];
for(int i=1;i<=count;++i)
{
title[i-1] = rsmd.getColumnName(i);
}
int type=0,j=0;
data = new Object[1000][count];
while(r.next())
{ 
for(int i=0;i<count;++i)
{
data[j][i]=r.getString(i+1);
}
j++;
}
s.close();
sqlListener.setTable(data,title);
}
catch(Exception e)
{
sqlListener.setErrorText(e.getMessage());
}
}
}




public void getTables()
{
try
{
String [] temp = new String[100];
DatabaseMetaData md = c.getMetaData();
ResultSet rs = md.getTables(null, null, "%", null);
int i=0;
while (rs.next()) {            
temp[i]= rs.getString(3);
i++;
}
String tables[]=new String[i];
for(int j=0;j<i;j++)
{
tables[j]=temp[j];
}
sqlListener.drawDatabaseTable(tables);
}catch(Exception e)
{
sqlListener.setErrorText(e.getMessage());
}
}























}