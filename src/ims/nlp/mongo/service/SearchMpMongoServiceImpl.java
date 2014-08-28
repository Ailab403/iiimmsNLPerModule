package ims.nlp.mongo.service;

import ims.nlp.mongo.MongoSearchMapPoolDbBean;

import java.util.List;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 
 * @author superhy
 * 
 */
public class SearchMpMongoServiceImpl implements SearchMpMongoService {

	/**
	 * ��ȡ���еļ�������
	 * 
	 * @return
	 */
	public Set<String> getAllCollectionNames() {
		MongoSearchMapPoolDbBean mongoDbBean = MongoSearchMapPoolDbBean
				.getMongoDbBean();
		Set<String> collectionNames = mongoDbBean.getCollectionNames();
		
		// ȥ��system.indexes������õļ���
		collectionNames.remove("system.indexes");

		return collectionNames;
	}

	/**
	 * �ж�һ�������Ƿ����
	 * 
	 * @param collectionName
	 * @return
	 */
	public boolean judgeCollectionExists(String collectionName) {
		// ��ȡ����ʵ��
		MongoSearchMapPoolDbBean mongoDbBean = MongoSearchMapPoolDbBean
				.getMongoDbBean();

		return mongoDbBean.collectionExistsOrNot(collectionName);
	}

	/**
	 * ����һ���µļ��ϣ����ش����Ƿ�ɹ�
	 * 
	 * @param collectionName
	 * @return
	 */
	public boolean createCollection(String collectionName) {

		MongoSearchMapPoolDbBean mongoDbBean = MongoSearchMapPoolDbBean
				.getMongoDbBean();

		return mongoDbBean.createNewCollection(collectionName);
	}

	/**
	 * ��ӳ����в����µ��������ӳ��
	 * 
	 * @param postUrlMD5
	 * @param strSearchKeyWords
	 * @param collectionName
	 * @return
	 */
	public boolean insertSearchResMap(String postUrlMD5,
			String strSearchKeyWords, String collectionName) {

		MongoSearchMapPoolDbBean mongoDbBean = MongoSearchMapPoolDbBean
				.getMongoDbBean();

		DBObject searchResObject = new BasicDBObject();
		searchResObject.put("postUrlMD5", postUrlMD5);
		searchResObject.put("keyWords", strSearchKeyWords);

		return mongoDbBean.insert(searchResObject, collectionName);
	}

	/**
	 * ��ѯ���ض��Ľ��ӳ��ԣ����û��ѯ�������ؿն���
	 * 
	 * @param postUrlMD5
	 * @param collectionName
	 * @return
	 */
	public List<DBObject> findMapByPostUrlMD5(String postUrlMD5,
			String collectionName) {

		MongoSearchMapPoolDbBean mongoDbBean = MongoSearchMapPoolDbBean
				.getMongoDbBean();

		// ��Ӳ�ѯ����
		DBObject ref = new BasicDBObject();
		ref.put("postUrlMD5", postUrlMD5);

		List<DBObject> searchRes = mongoDbBean.findByKeyAndRef(ref, null,
				collectionName);

		return searchRes;
	}

	/**
	 * ��ü��������е�ӳ���
	 * 
	 * @param collectionName
	 * @return
	 */
	public List<DBObject> findAllMapInCollection(String collectionName) {

		MongoSearchMapPoolDbBean mongoDbBean = MongoSearchMapPoolDbBean
				.getMongoDbBean();

		List<DBObject> searchRes = mongoDbBean.findByKeyAndRef(null, null,
				collectionName);

		return searchRes;
	}

	/**
	 * �����ض�ӳ��Ĺؼ����ַ���������ӳ��Ե�MD5ֵ��
	 * 
	 * @param postUrlMD5
	 * @param strSearchKeyWords
	 * @param collectionName
	 * @return
	 */
	public int updateSearchMap(String postUrlMD5, String strSearchKeyWords,
			String collectionName) {

		MongoSearchMapPoolDbBean mongoDbBean = MongoSearchMapPoolDbBean
				.getMongoDbBean();

		// ��Ӹ��µĲ�ѯ����
		DBObject find = new BasicDBObject();
		find.put("postUrlMD5", postUrlMD5);
		// ��Ӹ��µ�����
		DBObject update = new BasicDBObject();
		// warning : need new BasicObject(with entity)
		update.put("$set", new BasicDBObject("keyWords", strSearchKeyWords));

		return mongoDbBean.updateValue(find, update, false, true,
				collectionName);
	}

	/**
	 * ɾ�����ڵĻ���ӳ���
	 * 
	 * @param collectionName
	 * @return
	 */
	public boolean dropSearchMapCollection(String collectionName) {

		MongoSearchMapPoolDbBean mongoDbBean = MongoSearchMapPoolDbBean
				.getMongoDbBean();

		return mongoDbBean.dropCollection(collectionName);
	}
}
