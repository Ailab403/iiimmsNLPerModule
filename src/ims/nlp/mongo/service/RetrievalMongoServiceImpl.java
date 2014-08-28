package ims.nlp.mongo.service;

import ims.nlp.mongo.MongoCorpusDbBean;

import java.util.List;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;

/**
 * 
 * @author superhy
 * 
 */
public class RetrievalMongoServiceImpl implements RetrievalMongoService {

	/**
	 * �õ����еļ�������
	 * 
	 * @return
	 */
	public Set<String> findAllCollectionsName() {
		MongoCorpusDbBean mongoDbBean = MongoCorpusDbBean.getMongoDbBean();
		Set<String> collectionsName = mongoDbBean.getCollectionNames();

		// ȥ��system.indexes������õļ���
		collectionsName.remove("system.indexes");

		return collectionsName;
	}

	/**
	 * �õ�ָ�����������е�post�ڵ�
	 * 
	 * @param collectionName
	 * @return
	 */
	public List<DBObject> findAllPostInColl(String collectionName) {

		MongoCorpusDbBean mongoDbBean = MongoCorpusDbBean.getMongoDbBean();
		List<DBObject> postsInColl = mongoDbBean.findByKeyAndRef(null, null,
				collectionName);

		return postsInColl;
	}

	/**
	 * �õ�ָ�������������ض������post�ڵ�
	 * 
	 * @param taskLogId
	 * @param collectionName
	 * @return
	 */
	public List<DBObject> findPostInCollByTasklogId(String taskLogId,
			String collectionName) {

		MongoCorpusDbBean mongoDbBean = MongoCorpusDbBean.getMongoDbBean();
		// ��Ӳ�ѯ������
		DBObject ref = new BasicDBObject();
		ref.put("taskLogId", taskLogId);
		List<DBObject> postsInColl = mongoDbBean.findByKeyAndRef(ref, null,
				collectionName);

		return postsInColl;
	}

	/**
	 * �õ�ָ���������ض�MD5ֵ��post�ڵ�
	 * 
	 * @param postUrlMD5
	 * @param collectionName
	 * @return
	 */
	public DBObject findPostInCollByMD5(String postUrlMD5, String collectionName) {

		MongoCorpusDbBean mongoDbBean = MongoCorpusDbBean.getMongoDbBean();

		// ��Ӳ�ѯ����
		DBObject ref = new BasicDBObject();
		ref.put("postUrlMD5", postUrlMD5);
		List<DBObject> postsInColl = mongoDbBean.findByKeyAndRef(ref, null,
				collectionName);

		// �涨��Ӧ�ض���postUrlMD5��post���ҽ���һ��
		DBObject postWant = postsInColl.get(0);

		return postWant;
	}
}
