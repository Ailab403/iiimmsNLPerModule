package ims.nlp.cache;

import ims.nlp.entity.model.CorpusText;

import java.util.HashMap;
import java.util.Map;

public class PolarityTestFileMapPool {

	// 测试文件夹中的文件顺序映射对
	public static Map<String, Object> filePolarityMapPool = new HashMap<String, Object>();

	/**
	 * 获取缓存池
	 * 
	 * @return
	 */
	public static Map<String, Object> getFileOrderMapPool() {
		if (filePolarityMapPool == null) {
			filePolarityMapPool = new HashMap<String, Object>();
		}

		return filePolarityMapPool;
	}

	/**
	 * 获得缓存池大小
	 * 
	 * @return
	 */
	public static int getFileOrderMapPoolSize() {
		return filePolarityMapPool.size();
	}

	/**
	 * 检查映射实体是否存在于缓存池中
	 * 
	 * @param fileName
	 * @return
	 */
	public synchronized static boolean checkEntityInPool(String fileName) {
		if (filePolarityMapPool.containsKey(fileName)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 得到对应的text实体
	 * 
	 * @param fileName
	 * @return
	 */
	public synchronized static CorpusText loadTextFromPool(String fileName) {
		if (checkEntityInPool(fileName)) {
			return (CorpusText) filePolarityMapPool.get(fileName);
		} else {
			return null;
		}
	}

	/**
	 * 向缓存池中加入新的文本映射对象（返回插入是否成功）
	 * 
	 * @param orderNum
	 * @param corpusText
	 * @return
	 */
	public synchronized static boolean addNewEntityInPool(String fileName,
			CorpusText corpusText) {

		try {
			filePolarityMapPool.put(fileName, corpusText);

			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 从缓存池中删除单个文本映射对象
	 * 
	 * @param fileName
	 * @return
	 */
	public synchronized static boolean deleteEntityFromPool(String fileName) {
		try {
			filePolarityMapPool.remove(fileName);

			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 清空缓存池
	 * 
	 * @return
	 */
	public synchronized static boolean clearBufferPool() {

		try {
			if (filePolarityMapPool != null && filePolarityMapPool.size() > 0) {
				filePolarityMapPool.clear();
			}

			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
