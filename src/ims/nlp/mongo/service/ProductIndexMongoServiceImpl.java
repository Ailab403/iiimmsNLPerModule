package ims.nlp.mongo.service;

import ims.nlp.mongo.MongoCorpusProductDbBean;

import java.util.List;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 
 * @author superhy
 *
 */
public class ProductIndexMongoServiceImpl implements ProductIndexMongoService {

	/**
	 * 得到所有的集合名称
	 * 
	 * @return
	 */
	public Set<String> findAllCollectionsName() {
		MongoCorpusProductDbBean mongoDbBean = MongoCorpusProductDbBean
				.getMongoDbBean();
		Set<String> collectionsName = mongoDbBean.getCollectionNames();

		// 去掉system.indexes这个无用的集合
		collectionsName.remove("system.indexes");

		return collectionsName;
	}

	/**
	 * 得到指定集合中所有的product节点
	 * 
	 * @param collectionName
	 * @return
	 */
	public List<DBObject> findAllProductInColl(String collectionName) {
		MongoCorpusProductDbBean mongoDbBean = MongoCorpusProductDbBean
				.getMongoDbBean();
		List<DBObject> productsInColl = mongoDbBean.findByKeyAndRef(null, null,
				collectionName);

		return productsInColl;
	}

	/**
	 * 得到指定集合中特定nodeId值的product节点
	 * 
	 * @param postUrlMD5
	 * @param collectionName
	 * @return
	 */
	public DBObject findProductInCollByNodeId(String nodeId,
			String collectionName) {
		
		MongoCorpusProductDbBean mongoDbBean = MongoCorpusProductDbBean.getMongoDbBean();

		// 添加查询条件
		DBObject ref = new BasicDBObject();
		ref.put("nodeId", nodeId);
		List<DBObject> productsInColl = mongoDbBean.findByKeyAndRef(ref, null,
				collectionName);

		// 规定对应特定的postUrlMD5的post有且仅有一个
		DBObject productWant = productsInColl.get(0);

		return productWant;
	}

}
