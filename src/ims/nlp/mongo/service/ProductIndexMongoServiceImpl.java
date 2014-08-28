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
	 * �õ����еļ�������
	 * 
	 * @return
	 */
	public Set<String> findAllCollectionsName() {
		MongoCorpusProductDbBean mongoDbBean = MongoCorpusProductDbBean
				.getMongoDbBean();
		Set<String> collectionsName = mongoDbBean.getCollectionNames();

		// ȥ��system.indexes������õļ���
		collectionsName.remove("system.indexes");

		return collectionsName;
	}

	/**
	 * �õ�ָ�����������е�product�ڵ�
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
	 * �õ�ָ���������ض�nodeIdֵ��product�ڵ�
	 * 
	 * @param postUrlMD5
	 * @param collectionName
	 * @return
	 */
	public DBObject findProductInCollByNodeId(String nodeId,
			String collectionName) {
		
		MongoCorpusProductDbBean mongoDbBean = MongoCorpusProductDbBean.getMongoDbBean();

		// ��Ӳ�ѯ����
		DBObject ref = new BasicDBObject();
		ref.put("nodeId", nodeId);
		List<DBObject> productsInColl = mongoDbBean.findByKeyAndRef(ref, null,
				collectionName);

		// �涨��Ӧ�ض���postUrlMD5��post���ҽ���һ��
		DBObject productWant = productsInColl.get(0);

		return productWant;
	}

}
