package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**   
* 
* @author zhouhangbo  
* @date 2016年5月11日 下午3:45:52 
* @version V1.0.0   
* @Description: 
*/
public class Server {

	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(8080);  
	        Socket client = server.accept();  
	        BufferedReader in = new BufferedReader(new InputStreamReader(  
	                client.getInputStream()));  
	        PrintWriter out = new PrintWriter(client.getOutputStream());  
	        while (true) {  
	            String str = in.readLine();  
	            System.out.println(str);  
	            out.println("has receive....");  
	            out.flush();  
	            if (str.equals("end"))  
	                break;  
	        }  
	        client.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
