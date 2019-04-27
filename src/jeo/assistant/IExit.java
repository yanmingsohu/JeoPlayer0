// CatfoOD 2008-6-30 下午06:34:25

package jeo.assistant;

/**
 * 系统退出接口，当系统推出时依次调用所有退出的方法
 */
public interface IExit {
	/**
	 * 推出时需要执行的操作
	 */
	public void exit();
	
	/**
	 * 取得推出优先级，越小优先级越高
	 */
	public int getPriority();
}
