package ims.nlp.mongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

/**
 * MongoDB数据库基本操作类
 * 
 * @author WQS
 * 
 */
public class MongoCorpusProductDbBean {

	// 建立MongoDB数据库链接对象
	public static Mongo mongoConnection = null;
	// 创建MongoDB具体数据库的对象
	public static DB dbConnection = null;

	// 单例模式，静态内部类
	private static class MongoDbBeanHolder {
		private static final MongoCorpusProductDbBean MONGO_DB_BEAN = new MongoCorpusProductDbBean();
	}

	/**
	 * MongoDB链接所需要的BEAN
	 */
	@SuppressWarnings("static-access")
	private MongoCorpusProductDbBean() {
		try {
			if (mongoConnection == null || dbConnection == null) {
				String connectHost = "127.0.0.1:27017";
				String dbName = "corpusProduct";
				this.mongoConnection = new Mongo(connectHost);
				this.dbConnection = mongoConnection.getDB(dbName);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	// 返回类实体
	public static final MongoCorpusProductDbBean getMongoDbBean() {
		return MongoDbBeanHolder.MONGO_DB_BEAN;
	}
	
	/**
	 * 查询数据库集合名称
	 * 
	 * code by:dd
	 */
	public Set<String> getCollectionNames() {
		Set<String> colls = this.dbConnection.getCollectionNames();
		return colls;
	}

	
	 /* ===================================MongoDB的基本数据操作_WQS========================== */

	/**WQS-2014-07-02
	 * 向指定集合中添加数据(添加附件功能)
	 * 
	 * @param dbs
	 * @param collectionName
	 * @return
	 */
	@SuppressWarnings("static-access")
	public boolean insert(DBObject dbs, String collectionName) {
		try {
			// 获得对应集合
			DBCollection collection = this.dbConnection.getCollection(collectionName);
			
			// 插入数据
			collection.insert(dbs);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	/**WQS-2014-07-02
	 * 向指定集合中批量添加数据(添加附件功能-- 暂时没用到)
	 * 
	 * @param dbses
	 * @param collectionName
	 * @return
	 */
	@SuppressWarnings("static-access")
	public boolean batchInsert(List<DBObject> dbses, String collectionName) {
		try {
			// 得到对应集合
			DBCollection collection = this.dbConnection.getCollection(collectionName);
			
			// 批量插入数据
			collection.insert(dbses);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	/**WQS-2014-07-08
	 * 删除指定集合中的特定对象
	 * 
	 * @param dbs
	 * @param collectionName
	 * @return
	 */
	@SuppressWarnings("static-access")
	public boolean deleteByDbs(DBObject dbs, String collectionName) {
		try{
			// 获得要进行操作的集合
			DBCollection collection = this.dbConnection.getCollection(collectionName);
			
			// 执行删除操作并返回影响的行数
			collection.remove(dbs);

			return true;
			
		}catch(Exception e) {
			e.printStackTrace();
			
			return false;
		}
	}
	

	/**
	 * WQS-2014-07-01(文本编辑)
	 * 更新数据,find查询器,update更新器,upsert更新或插入,multi是否批量更新,collName集合名称,
	 * return返回影响的数据条数
	 * 
	 * @param find
	 * @param update
	 * @param upsert
	 * @param multi
	 * @param collName
	 * @return
	 */
	@SuppressWarnings("static-access")
	public boolean updateValue(DBObject find, DBObject update, boolean upsert,
			boolean multi, String collName) {

		try {
			// 得到集合
			DBCollection coll = this.dbConnection.getCollection(collName);

			coll.update(find, update, upsert, multi);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * WQS-2014-07-01
	 * 通用find方法，传入三个参数，第一个是查询的条件，第二个是查询的字段限制，第三个是查询的集合名，返回值是DBObject的映射集合
	 * exp:不带分页的查询
	 * 
	 * @return
	 */
	@SuppressWarnings("static-access")
	public List<DBObject> findByKeyAndRef(DBObject ref, DBObject keys,String collectionName) {
		// 准备要返回的映射集合
		List<DBObject> dbObjects = new ArrayList<DBObject>();

		// 获得要进行操作的集合
		DBCollection collection = this.dbConnection.getCollection(collectionName);
		
		// 创建查询的游标
		DBCursor cursor = collection.find(ref, keys);

		// 遍历游标获得查询结果集合
		while (cursor.hasNext()) {
			dbObjects.add(cursor.next());
		}

		return dbObjects;
	}
	
	public static void deleteColData(){
		
	}

}
