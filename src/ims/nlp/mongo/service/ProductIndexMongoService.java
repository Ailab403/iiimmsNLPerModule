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
	 * 得到所有的集合名称
	 * 
	 * @return
	 */
	public Set<String> findAllCollectionsName();

	/**
	 * 得到指定集合中所有的product节点
	 * 
	 * @param collectionName
	 * @return
	 */
	public List<DBObject> findAllProductInColl(String collectionName);

	/**
	 * 得到指定集合中特定nodeId值的product节点
	 * 
	 * @param postUrlMD5
	 * @param collectionName
	 * @return
	 */
	public DBObject findProductInCollByNodeId(String nodeId, String collectionName);
}
