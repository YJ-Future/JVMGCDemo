package gloryzyf;

public class TestGC {
	private static final int _1MB=1024*1024;
	
	/**
	 * VM参数：-Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetials -XX:SurvivorRatio=8 -XX:+UseSerialGC
	 * @param args
	 */
	public static void main(String []args){
		//allocation();
		//testPretenureSizeThreshold();
		testTenuringThreshold();
	}
	
	/**
	 * 
	 * [GC[DefNew: 6816K->464K(9216K), 0.0110373 secs] 6816K->6608K(19456K), 0.0111627 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
		Heap
	 */
	private static void allocation(){
		byte []allocation1,allocation2,allocation3,allocation4;
		allocation1=new byte[_1MB*2];
		allocation2=new byte[_1MB*2];
		allocation3=new byte[_1MB*2];
		allocation4=new byte[_1MB*4];
	} 
	
	/**
	 * 加上 -XX:+PretenureSizeThreshold=3145728
	 * 分配大对象时直接把对象放到了
	 * tenured generation   total 10240K, used 4096K
	 */
	public static void testPretenureSizeThreshold(){
		byte []allocation;
		allocation=new byte[_1MB*4];
	}
	
	/**
	 * -XX:MaxTenuringThreshold=1 或者-XX:MaxTenuringThreshold=15对此测试
	 * -XX:+PrintTenuringDistribution
	 *  from space 1024K,   0% used 
	 *  
	 */
	public static void testTenuringThreshold(){
		byte[] allocation1,allocation2,allocation3;
		allocation1=new byte[_1MB/4];
		allocation2=new byte[_1MB*4];
		allocation3=new byte[_1MB*4];
		allocation3=null;
		allocation3=new byte[_1MB*4];
	} 
	/**
	 * 在每次发生MinorGc之前首先判断年老代中连续空间大小是否大于年轻代所有对象的大小，如果大于就只进行MinorGC,如果小于，就要看是否设置了HanlePormotionFailure为false
	 * 默认的HandlePromotionFailure为true;如果为false就先进行一次FullGC,然后进行MinorGC;如果为true或没有设置，就要判断之前的MinorGC中移动到年老代中对象的平均大小
	 * 如果平均大小小于现在年老代的最大连续空间，就只进行MinorGC,如果大于，就要先进行一次FullGC
	 * 加上 -XX:-HandlePromotionFailure后运行提示 本机JDK1.7
	 * Java HotSpot(TM) 64-Bit Server VM warning: ignoring option HandlePromotionFailure; support was removed in 6.0_24
	 * 提示从jdk 6update 24开始就不支持了忽略改参数 设置不设置都会当做设置了
	 **
	 *现在的规则就是：如果年轻代所有对象的大小比年老代最大连续空间小，或者平均晋升到年老代的大小小于年老代现在最大连续的空间，就只进行MinorGC,否则要进行FullGC
	 */
	public static void testHandlePromotion(){
		//代码略
	}
}
