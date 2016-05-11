package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**   
* 
* @author zhouhangbo  
* @date 2016年5月11日 下午3:45:47 
* @version V1.0.0   
* @Description: 
*/
public class BloomFilterServer {

	public static void main(String[] args) {
		try {
			Socket client = new Socket("127.0.0.1",8081);
			BufferedReader in = new BufferedReader(new InputStreamReader(  
					client.getInputStream()));  
	        PrintWriter out = new PrintWriter(client.getOutputStream());  
	        BufferedReader wt = new BufferedReader(new InputStreamReader(System.in));  
	        while (true) {  
	            String str = wt.readLine();  
	            out.println(str);  
	            out.flush();  
	            if (str.equals("end")) {  
	                break;  
	            }  
	            System.out.println(in.readLine());  
	        }  
	        client.close();  
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
