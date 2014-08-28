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
	 * ˽�й��췽��
	 */
	private FolderFileBufferPool() {
		super();
	}

	/**
	 * ��ȡ�����
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
	 * ��ȡ�����
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
	 * �ж��ļ��Ƿ����
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
	 * ����ļ��Ƿ�Ϊ�µ��ļ����Ƿ��ڻ�����У����ھ�˵�����µģ�
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
	 * �򻺴���м����µ��ı�ӳ����󣨷��ز����Ƿ�ɹ���
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
	 * ��ջ����
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
