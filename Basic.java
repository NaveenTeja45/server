package webserver;
import java.io.BufferedReader;
import java.io.*;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
public class Basic {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			ServerSocket server=new ServerSocket(5050);
			System.out.println("Listening on 5050");
			while(true)
			{
				Socket connection=server.accept();
				System.out.println("connection created");
				
				InputStreamReader isr=new InputStreamReader(connection.getInputStream());
				BufferedReader reader=new BufferedReader(isr);
				String line=reader.readLine();
				int j=0,l=0;
				String file_name="";
				System.out.println("Reading request and file name");
				while(line!=null && !line.isEmpty())
				{
					if(j==0)
					{
						++j;
						l=line.indexOf("HTTP");
						file_name=line.substring(5,l-1);
						file_name+=".html";
					}
					System.out.println(line);
					line=reader.readLine();
				}
				System.out.println(file_name);
				try {
					l=file_name.indexOf(".html");
					
					//when u type localhost:3030/getmoved.html  it redirects to www.google.com
					if(file_name.equals("getmoved.html"))
					{
						String response= "HTTP/ 1.1 301 Moved Permanently \r\n\r\n";
						file_name="redirect.html";
						File open=new File("C:\\oops lab\\webserver\\src\\webserver\\"+file_name);
						System.out.println("Reading File");
						BufferedReader sc=new BufferedReader(new FileReader(open));
						String line2=sc.readLine();
						String ans="";
						while(line2!=null && !line2.isEmpty())
						{
							ans=ans+line2;
							line2=sc.readLine();
						}
						response=response+ans;
						connection.getOutputStream().write(response.getBytes("UTF-8"));
						System.out.println("Written response");
					}
					
					//when u type localhost:3030/filename.html	
					else if(l>0)
					{
						String response="HTTP/1.1 200 OK \r\n\r\n ";
						File open=new File("C:\\oops lab\\webserver\\src\\webserver\\"+file_name);		//if file not found exception occurs
						System.out.println("Reading File");
						BufferedReader sc=new BufferedReader(new FileReader(open));
						String line2=sc.readLine();
						String ans="";
						while(line2!=null && !line2.isEmpty())
						{
							ans=ans+line2;
							line2=sc.readLine();
						}
						response=response+ans;
						connection.getOutputStream().write(response.getBytes("UTF-8"));
						System.out.println("Written response");
					}
					
					//when u type localhost:3030/  not accesing any files
					else															
					{
					//	Date date=new Date();
						String response="HTTP/1.1 200 OK \r\n\r\n "+"u are not accesing HTML files pls access it"+"\n"+"\n"+"\n"+"in format of localhost:4040/filename.html";
						connection.getOutputStream().write(response.getBytes("UTF-8"));
						System.out.println("Written response");
					}
					connection.close();
				}
				catch(FileNotFoundException e)
				{
					String response = "HTTP/ 1.1 404 FILE NOT FOUND \r\n\r\n ";
					File open=new File("C:\\oops lab\\webserver\\src\\webserver\\"+"error.html");
					BufferedReader sc=new BufferedReader(new FileReader(open));
					String line2=sc.readLine();
					String ans="";
					while(line2!=null && !line2.isEmpty())
					{
						ans=ans+line2;
						line2=sc.readLine();
					}
					response=response+ans;
					connection.getOutputStream().write(response.getBytes("UTF-8"));
					System.out.println("404 Page Not Found");
					connection.close();
				}
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

}
