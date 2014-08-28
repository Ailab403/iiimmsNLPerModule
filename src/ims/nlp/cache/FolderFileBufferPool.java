package ims.nlp.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author superhy
 * 
 */
public class FolderFileBufferPool {

	private static FolderFileBufferPool folderFileBufferPool;
	private Map<String, String> fileBufferPool = new HashMap<String, String>();

	/**
	 * 私有构造方法
	 */
	private FolderFileBufferPool() {
		super();
	}

	/**
	 * 获取类变量
	 * 
	 * @return
	 */
	public static FolderFileBufferPool getFolderFileBufferPool() {
		if (folderFileBufferPool == null) {
			folderFileBufferPool = new FolderFileBufferPool();
		}
		return folderFileBufferPool;
	}

	/**
	 * 获取缓存池
	 * 
	 * @return
	 */
	public Map<String, String> getFileBufferPool() {
		if (this.fileBufferPool == null) {
			this.fileBufferPool = new HashMap<String, String>();
		}

		return fileBufferPool;
	}

	/**
	 * 判断文件是否减少
	 * 
	 * @param fileNumNow
	 * @return
	 */
	public synchronized boolean checkFileNumLess(int fileNumNow) {
		if (this.fileBufferPool.size() > fileNumNow) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检查文件是否为新的文件（是否在缓存池中，不在就说明是新的）
	 * 
	 * @param fileName
	 * @param filePath
	 * @return
	 */
	public synchronized boolean checkFileInFolder(String fileName,
			String filePath) {

		if (this.getFileBufferPool().containsKey(fileName)) {
			if (this.getFileBufferPool().get(fileName).equals(filePath)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 向缓存池中加入新的文本映射对象（返回插入是否成功）
	 * 
	 * @param fileName
	 * @param filePath
	 * @return
	 */
	public synchronized boolean addNewEntityInPool(String fileName,
			String filePath) {

		try {
			this.fileBufferPool.put(fileName, filePath);

			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 清空缓存池
	 * 
	 * @return
	 */
	public synchronized boolean clearBufferPool() {

		try {
			if (this.fileBufferPool != null && this.fileBufferPool.size() > 0) {
				this.fileBufferPool.clear();
			}

			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
