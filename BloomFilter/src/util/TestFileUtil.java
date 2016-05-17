package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

/**   
* 
* @author zhouhangbo  
* @date 2016年5月16日 下午2:17:23 
* @version V1.0.0   
* @Description: 
*/
public class TestFileUtil {
	
	private static final int size = 1000000;
	
	private static final String enter = "\r\n";
	
	public static final String path = "test.txt";

	public static void makeFile() throws IOException{
		makeFile(path);
	}
	public static void makeFile(String path) throws IOException{
		File file = new File(path);
		BufferedWriter bw = null;
		try {
			bw  = new BufferedWriter(new FileWriter(file));
			StringBuffer sb = new StringBuffer((UUID.randomUUID().toString().length() +enter.length()) * size);
			for (int i = 0;i < size ; i++) {
				sb.append(UUID.randomUUID().toString()).append(enter);
			}
			bw.write(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			bw.close();
		}
	}
	public static void writeFile(String path,String content) throws IOException{
		File file = new File(path);
		if(!file.exists()){
			BufferedWriter bw = null;
			try {
				bw  = new BufferedWriter(new FileWriter(file));
				bw.write(content);
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				bw.close();
			}
		}else{
			try {
		        //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
		        FileWriter writer = new FileWriter(path, true);
		        writer.write(enter + content);
		        writer.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println("-----------start-----------");
		makeFile();
		System.out.println("-----------end-----------");
	}
}
