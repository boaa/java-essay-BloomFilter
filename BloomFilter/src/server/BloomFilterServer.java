package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import util.BloomFilter;
import util.TestFileUtil;

/**   
* 
* @author zhouhangbo  
* @date 2016年5月11日 下午3:45:47 
* @version V1.0.0   
* @Description: 
*/
public class BloomFilterServer {
	

	private static int allCount = 0;
	private static int errorCount = 0;
	
	public static String getData(String key){
		if ("allCount".equals(key)){
			return String.valueOf(allCount);
		} else if("errorCount".equals(key)){
			return String.valueOf(errorCount);
		}
		return null;
	}
	
	static{
		BloomFilter.init();
	}

	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(8081);  
	        Socket client = server.accept();  
	        BufferedReader in = new BufferedReader(new InputStreamReader(  
	                client.getInputStream()));  
	        PrintWriter out = new PrintWriter(client.getOutputStream());   
	        while (true) {  
	            String str = in.readLine();  
	            
	            String result = getData(str);
	            if(result == null){
	            	allCount ++;
	            	if(BloomFilter.contains(str)){
	            		errorCount ++;
	            		TestFileUtil.writeFile("result.txt", str);
	            	}
	            }else{
	            	out.write(result);
	            	out.flush();
	            }
	            if (str.equals("end")) {  
	                break;  
	            }  
	            if(allCount % 100000 == 0){
	            	System.out.println(errorCount);  
	            }
	        }  
	        client.close();  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
