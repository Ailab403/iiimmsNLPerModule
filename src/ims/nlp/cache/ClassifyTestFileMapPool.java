package ims.nlp.cache;

import ims.nlp.entity.model.CorpusText;

import java.util.HashMap;
import java.util.Map;

public class ClassifyTestFileMapPool {

	// �����ļ����е��ļ�˳��ӳ���
	public static Map<String, Object> fileOrderMapPool = new HashMap<String, Object>();

	/**
	 * ��ȡ�����
	 * 
	 * @return
	 */
	public static Map<String, Object> getFileOrderMapPool() {
		return fileOrderMapPool;
	}

	/**
	 * ��û���ش�С
	 * 
	 * @return
	 */
	public static int getFileOrderMapPoolSize() {
		return fileOrderMapPool.size();
	}

	/**
	 * ���ӳ��ʵ���Ƿ�����ڻ������
	 * 
	 * @param fileName
	 * @return
	 */
	public synchronized static boolean checkEntityInPool(String fileName) {
		if (fileOrderMapPool.containsKey(fileName)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * �õ���Ӧ��textʵ��
	 * 
	 * @param fileName
	 * @return
	 */
	public synchronized static CorpusText loadTextFromPool(String fileName) {
		if (checkEntityInPool(fileName)) {
			return (CorpusText) fileOrderMapPool.get(fileName);
		} else {
			return null;
		}
	}

	/**
	 * �򻺴���м����µ��ı�ӳ����󣨷��ز����Ƿ�ɹ���
	 * 
	 * @param orderNum
	 * @param corpusText
	 * @return
	 */
	public synchronized static boolean addNewEntityInPool(String fileName,
			CorpusText corpusText) {

		try {
			fileOrderMapPool.put(fileName, corpusText);

			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * �ӻ������ɾ�������ı�ӳ�����
	 * 
	 * @param fileName
	 * @return
	 */
	public synchronized static boolean deleteEntityFromPool(String fileName) {
		try {
			fileOrderMapPool.remove(fileName);

			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * ��ջ����
	 * 
	 * @return
	 */
	public synchronized static boolean clearBufferPool() {

		try {
			if (fileOrderMapPool != null && fileOrderMapPool.size() > 0) {
				fileOrderMapPool.clear();
			}

			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
