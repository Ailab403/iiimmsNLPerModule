package ims.nlp.mongo.service;

import java.util.List;
import java.util.Set;

import com.mongodb.DBObject;

public interface SearchMpMongoService {

	/**
	 * ��ȡ���еļ�������
	 * 
	 * @return
	 */
	public Set<String> getAllCollectionNames();

	/**
	 * �ж�һ�������Ƿ����
	 * 
	 * @param collectionName
	 * @return
	 */
	public boolean judgeCollectionExists(String collectionName);

	/**
	 * ����һ���µļ��ϣ����ش����Ƿ�ɹ�
	 * 
	 * @param collectionName
	 * @return
	 */
	public boolean createCollection(String collectionName);

	/**
	 * ��ӳ����в����µ��������ӳ��
	 * 
	 * @param postUrlMD5
	 * @param strSearchKeyWords
	 * @param collectionName
	 * @return
	 */
	public boolean insertSearchResMap(String postUrlMD5,
			String strSearchKeyWords, String collectionName);

	/**
	 * ��ѯ���ض��Ľ��ӳ��ԣ����û��ѯ�������ؿն���
	 * 
	 * @param postUrlMD5
	 * @param collectionName
	 * @return
	 */
	public List<DBObject> findMapByPostUrlMD5(String postUrlMD5,
			String collectionName);

	/**
	 * ��ü��������е�ӳ���
	 * 
	 * @param collectionName
	 * @return
	 */
	public List<DBObject> findAllMapInCollection(String collectionName);

	/**
	 * �����ض�ӳ��Ĺؼ����ַ���������ӳ��Ե�MD5ֵ��
	 * 
	 * @param postUrlMD5
	 * @param strSearchKeyWords
	 * @param collectionName
	 * @return
	 */
	public int updateSearchMap(String postUrlMD5, String strSearchKeyWords,
			String collectionName);

	/**
	 * ɾ�����ڵĻ���ӳ���
	 * 
	 * @param collectionName
	 * @return
	 */
	public boolean dropSearchMapCollection(String collectionName);
}
