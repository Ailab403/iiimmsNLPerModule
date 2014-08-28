package ims.nlp.mongo.service;

import java.util.List;
import java.util.Set;

import com.mongodb.DBObject;

public interface RetrievalMongoService {

	/**
	 * �õ����еļ�������
	 * 
	 * @return
	 */
	public Set<String> findAllCollectionsName();

	/**
	 * �õ�ָ�����������е�post�ڵ�
	 * 
	 * @param collectionName
	 * @return
	 */
	public List<DBObject> findAllPostInColl(String collectionName);

	/**
	 * �õ�ָ�������������ض������post�ڵ�
	 * 
	 * @param taskLogId
	 * @param collectionName
	 * @return
	 */
	public List<DBObject> findPostInCollByTasklogId(String taskLogId,
			String collectionName);

	/**
	 * �õ�ָ���������ض�MD5ֵ��post�ڵ�
	 * 
	 * @param postUrlMD5
	 * @param collectionName
	 * @return
	 */
	public DBObject findPostInCollByMD5(String postUrlMD5, String collectionName);

}
