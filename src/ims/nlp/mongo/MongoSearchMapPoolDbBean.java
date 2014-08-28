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
 * 
 * @author superhy
 * 
 */
public class MongoSearchMapPoolDbBean {

	// 建立MongoDB数据库链接对象
	public static Mongo mongoConnection = null;
	// 创建MongoDB具体数据库的对象
	public static DB dbConnection = null;

	// 单例模式，静态内部类
	private static class MongoDbBeanHolder {
		private static final MongoSearchMapPoolDbBean MONGO_DB_BEAN = new MongoSearchMapPoolDbBean();
	}

	private MongoSearchMapPoolDbBean() {
		try {
			if (mongoConnection == null || dbConnection == null) {
				String connectHost = "127.0.0.1:27017";
				String dbName = "searchMapPool";

				this.mongoConnection = new Mongo(connectHost);
				this.dbConnection = mongoConnection.getDB(dbName);
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 返回类实体
	public static final MongoSearchMapPoolDbBean getMongoDbBean() {
		return MongoDbBeanHolder.MONGO_DB_BEAN;
	}

	// MongoDB的基本数据操作

	/**
	 * 返回存在的所有集合的名称
	 */
	public Set<String> getCollectionNames() {
		Set<String> colls = this.dbConnection.getCollectionNames();
		return colls;
	}

	/**
	 * 判断是否存在某集合
	 */
	public boolean collectionExistsOrNot(String collectionName) {

		boolean flagExists = this.dbConnection.collectionExists(collectionName);

		return flagExists;
	}

	/**
	 * 创建新的数据库集合
	 */
	public boolean createNewCollection(String collectionName) {
		try {
			DBObject dbCollection = new BasicDBObject();
			this.dbConnection.createCollection(collectionName, dbCollection);

			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 向指定集合中添加数据
	 * 
	 * @param dbs
	 * @param collectionName
	 * @return
	 */
	public boolean insert(DBObject dbs, String collectionName) {

		try {
			// 获得对应集合
			DBCollection collection = this.dbConnection
					.getCollection(collectionName);
			// 插入数据
			collection.insert(dbs);

			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 通用find方法，传入三个参数，第一个是查询的条件，第二个是查询的字段限制，第三个是查询的集合名，返回值是DBObject的映射集合
	 * exp:不带分页的查询
	 * 
	 * @return
	 */
	public List<DBObject> findByKeyAndRef(DBObject ref, DBObject keys,
			String collectionName) {

		// 准备要返回的映射集合
		List<DBObject> dbObjects = new ArrayList<DBObject>();

		// 获得要进行操作的集合
		DBCollection collection = this.dbConnection
				.getCollection(collectionName);
		// 创建查询的游标
		DBCursor cursor = collection.find(ref, keys);

		// 遍历游标获得查询结果集合
		while (cursor.hasNext()) {
			dbObjects.add(cursor.next());
		}

		return dbObjects;
	}

	/**
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
	public int updateValue(DBObject find, DBObject update, boolean upsert,
			boolean multi, String collName) {

		// 得到集合
		DBCollection coll = this.dbConnection.getCollection(collName);

		int count = coll.update(find, update, upsert, multi).getN();
		return count;
	}

	/**
	 * 根据集合名称，直接删除集合
	 * 
	 * @param collectionName
	 * @return
	 */
	public boolean dropCollection(String collectionName) {

		try {
			// 获得要进行操作的集合
			DBCollection collection = this.dbConnection
					.getCollection(collectionName);

			collection.drop();

			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return false;
		}
	}

}
