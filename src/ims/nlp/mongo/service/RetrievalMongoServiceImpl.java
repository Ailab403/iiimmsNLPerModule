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
	 * 得到所有的集合名称
	 * 
	 * @return
	 */
	public Set<String> findAllCollectionsName() {
		MongoCorpusDbBean mongoDbBean = MongoCorpusDbBean.getMongoDbBean();
		Set<String> collectionsName = mongoDbBean.getCollectionNames();

		// 去掉system.indexes这个无用的集合
		collectionsName.remove("system.indexes");

		return collectionsName;
	}

	/**
	 * 得到指定集合中所有的post节点
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
	 * 得到指定集合中属于特定任务的post节点
	 * 
	 * @param taskLogId
	 * @param collectionName
	 * @return
	 */
	public List<DBObject> findPostInCollByTasklogId(String taskLogId,
			String collectionName) {

		MongoCorpusDbBean mongoDbBean = MongoCorpusDbBean.getMongoDbBean();
		// 添加查询的条件
		DBObject ref = new BasicDBObject();
		ref.put("taskLogId", taskLogId);
		List<DBObject> postsInColl = mongoDbBean.findByKeyAndRef(ref, null,
				collectionName);

		return postsInColl;
	}

	/**
	 * 得到指定集合中特定MD5值的post节点
	 * 
	 * @param postUrlMD5
	 * @param collectionName
	 * @return
	 */
	public DBObject findPostInCollByMD5(String postUrlMD5, String collectionName) {

		MongoCorpusDbBean mongoDbBean = MongoCorpusDbBean.getMongoDbBean();

		// 添加查询条件
		DBObject ref = new BasicDBObject();
		ref.put("postUrlMD5", postUrlMD5);
		List<DBObject> postsInColl = mongoDbBean.findByKeyAndRef(ref, null,
				collectionName);

		// 规定对应特定的postUrlMD5的post有且仅有一个
		DBObject postWant = postsInColl.get(0);

		return postWant;
	}
}
