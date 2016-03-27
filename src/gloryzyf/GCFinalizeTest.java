package gloryzyf;
/**
 * 每个对象的finalize方法只会被系统自动调用一次
 * 首先一个对象不可达了，会对该对象进行一次标记，然后判断该对象的finalize方法时候重写了或者已经执行过了，若果没有重写或者
 * 重写了但是已经执行过了，就不会当做没有必要执行了，就算是确定被回收了，如果重写了而且还没有被执行过，就把这个对象放到一个F-Queue
 * 队列中，并在稍后由虚拟机来自动建立的、低优先级的Finalizer线程去执行它的finalize方法，如果拯救了自己了，就移出即将回收的
 * 的集合，如果对象没有逃脱，就确定被回收了。
 * @author YU
 *
 */
public class GCFinalizeTest {
	
	public static GCFinalizeTest SAVE_HOOK=null;
	
	public void isAlive(){
		System.out.println("alive");
	}
	
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		System.out.println("finalize method");
		GCFinalizeTest.SAVE_HOOK=this;
	}


	public static void main(String []args){
		SAVE_HOOK=new GCFinalizeTest();
		//对象第一次成功拯救自己
		SAVE_HOOK=null;
		System.gc();
		//因为finalize方法优先级很低，暂定0.5秒
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(SAVE_HOOK!=null){
			SAVE_HOOK.isAlive();//执行
		}else{
			System.out.println("dead");
		}
		
		//第二次自救
		SAVE_HOOK=null;
		System.gc();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(SAVE_HOOK!=null){
			SAVE_HOOK.isAlive();
		}else{
			System.out.println("dead");//执行 
		}
	}
}

