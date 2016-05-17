package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import util.TestFileUtil;

public class BloomFilterClient {
	
	public static void main(String[] args) {
		try {
			Socket client = new Socket("127.0.0.1",8081);
			BufferedReader in = new BufferedReader(new InputStreamReader(  
					client.getInputStream()));  
	        PrintWriter out = new PrintWriter(client.getOutputStream());  
	        BufferedReader wt = new BufferedReader(new InputStreamReader(System.in));  
	        File file = new File("test1.txt");
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(file));
				while(true){
					String str = br.readLine();
					if (str == null) {  
		                break;  
		            }
					out.println(str);
					out.flush();  
					
//					System.out.println(in.readLine());  
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally{
				br.close();
			}
	        client.close();  
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
