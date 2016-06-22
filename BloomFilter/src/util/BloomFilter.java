package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.BitSet;

/**
 * 
 * @author zhouhangbo
 * @date 2016年5月11日 下午4:12:38
 * @version V1.0.0
 * @Description:
 * @see http://www.cnblogs.com/heaad/archive/2011/01/02/1924195.html
 * @see http://pages.cs.wisc.edu/~cao/papers/summary-cache/node8.html
 */
public class BloomFilter {

	private static final int DEFAULT_SIZE = 1 << 24;// 布隆过滤器的比特长度
	private static final int[] seeds = { 3, 5, 7, 11, 13, 31, 37, 61 ,67 ,71 };// 这里要选取质数，能很好的降低错误率
	private static BitSet bits = new BitSet(DEFAULT_SIZE);
	private static SimpleHash[] func = new SimpleHash[seeds.length];
	
	static {
		for (int i = 0; i < seeds.length; i++) {
			func[i] = new SimpleHash(DEFAULT_SIZE, seeds[i]);
		}
	}
	
	public static void init(){
		try {
			genBitSet();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void addValue(String value) {
		for (SimpleHash f : func)
			// 将字符串value哈希为8个或多个整数，然后在这些整数的bit上变为1
			bits.set(f.hash(value), true);
	}

	public static void add(String value) {
		if (value != null)
			addValue(value);
	}

	public static boolean contains(String value) {
		if (value == null)
			return false;
		boolean ret = true;
		for (SimpleHash f : func)
			// 这里其实没必要全部跑完，只要一次ret==false那么就不包含这个字符串
			ret = ret && bits.get(f.hash(value));
		return ret;
	}
	
	public static void genBitSet() throws IOException{
		File file = new File(TestFileUtil.path);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			while(true){
				String str = br.readLine();
				if (str == null) {  
	                break;  
	            }
				add(str);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally{
			br.close();
		}
	}
	
	public static void main(String[] args) {
		System.out.println(DEFAULT_SIZE);
	}

}

class SimpleHash {// 这玩意相当于C++中的结构体

	private int cap;
	private int seed;

	public SimpleHash(int cap, int seed) {
		this.cap = cap;
		this.seed = seed;
	}

	public int hash(String value) {// 字符串哈希，选取好的哈希函数很重要
		int result = 0;
		int len = value.length();
		for (int i = 0; i < len; i++) {
			result = seed * result + (int)value.charAt(i);
		}
		return (cap - 1) & result;
	}
}
