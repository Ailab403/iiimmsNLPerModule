package ims.nlp.mongo.service;

import java.util.List;
import java.util.Set;

import com.mongodb.DBObject;

/**
 * 
 * @author superhy
 *
 */
public interface ProductIndexMongoService {

	/**
	 * �õ����еļ�������
	 * 
	 * @return
	 */
	public Set<String> findAllCollectionsName();

	/**
	 * �õ�ָ�����������е�product�ڵ�
	 * 
	 * @param collectionName
	 * @return
	 */
	public List<DBObject> findAllProductInColl(String collectionName);

	/**
	 * �õ�ָ���������ض�nodeIdֵ��product�ڵ�
	 * 
	 * @param postUrlMD5
	 * @param collectionName
	 * @return
	 */
	public DBObject findProductInCollByNodeId(String nodeId, String collectionName);
}
