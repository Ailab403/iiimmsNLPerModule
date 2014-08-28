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
	 * 获取所有的集合名称
	 * 
	 * @return
	 */
	public Set<String> getAllCollectionNames() {
		MongoSearchMapPoolDbBean mongoDbBean = MongoSearchMapPoolDbBean
				.getMongoDbBean();
		Set<String> collectionNames = mongoDbBean.getCollectionNames();
		
		// 去掉system.indexes这个无用的集合
		collectionNames.remove("system.indexes");

		return collectionNames;
	}

	/**
	 * 判断一个集合是否存在
	 * 
	 * @param collectionName
	 * @return
	 */
	public boolean judgeCollectionExists(String collectionName) {
		// 获取单例实体
		MongoSearchMapPoolDbBean mongoDbBean = MongoSearchMapPoolDbBean
				.getMongoDbBean();

		return mongoDbBean.collectionExistsOrNot(collectionName);
	}

	/**
	 * 创建一个新的集合，返回创建是否成功
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
	 * 向映射池中插入新的搜索结果映射
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
	 * 查询到特定的结果映射对，如果没查询到，返回空队列
	 * 
	 * @param postUrlMD5
	 * @param collectionName
	 * @return
	 */
	public List<DBObject> findMapByPostUrlMD5(String postUrlMD5,
			String collectionName) {

		MongoSearchMapPoolDbBean mongoDbBean = MongoSearchMapPoolDbBean
				.getMongoDbBean();

		// 添加查询条件
		DBObject ref = new BasicDBObject();
		ref.put("postUrlMD5", postUrlMD5);

		List<DBObject> searchRes = mongoDbBean.findByKeyAndRef(ref, null,
				collectionName);

		return searchRes;
	}

	/**
	 * 获得集合中所有的映射对
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
	 * 更新特定映射的关键字字符串，传入映射对的MD5值，
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

		// 添加更新的查询条件
		DBObject find = new BasicDBObject();
		find.put("postUrlMD5", postUrlMD5);
		// 添加更新的内容
		DBObject update = new BasicDBObject();
		// warning : need new BasicObject(with entity)
		update.put("$set", new BasicDBObject("keyWords", strSearchKeyWords));

		return mongoDbBean.updateValue(find, update, false, true,
				collectionName);
	}

	/**
	 * 删除过期的缓冲映射池
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
