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
 * MongoDB���ݿ����������
 * 
 * @author WQS
 * 
 */
public class MongoCorpusProductDbBean {

	// ����MongoDB���ݿ����Ӷ���
	public static Mongo mongoConnection = null;
	// ����MongoDB�������ݿ�Ķ���
	public static DB dbConnection = null;

	// ����ģʽ����̬�ڲ���
	private static class MongoDbBeanHolder {
		private static final MongoCorpusProductDbBean MONGO_DB_BEAN = new MongoCorpusProductDbBean();
	}

	/**
	 * MongoDB��������Ҫ��BEAN
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

	// ������ʵ��
	public static final MongoCorpusProductDbBean getMongoDbBean() {
		return MongoDbBeanHolder.MONGO_DB_BEAN;
	}
	
	/**
	 * ��ѯ���ݿ⼯������
	 * 
	 * code by:dd
	 */
	public Set<String> getCollectionNames() {
		Set<String> colls = this.dbConnection.getCollectionNames();
		return colls;
	}

	
	 /* ===================================MongoDB�Ļ������ݲ���_WQS========================== */

	/**WQS-2014-07-02
	 * ��ָ���������������(��Ӹ�������)
	 * 
	 * @param dbs
	 * @param collectionName
	 * @return
	 */
	@SuppressWarnings("static-access")
	public boolean insert(DBObject dbs, String collectionName) {
		try {
			// ��ö�Ӧ����
			DBCollection collection = this.dbConnection.getCollection(collectionName);
			
			// ��������
			collection.insert(dbs);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	/**WQS-2014-07-02
	 * ��ָ�������������������(��Ӹ�������-- ��ʱû�õ�)
	 * 
	 * @param dbses
	 * @param collectionName
	 * @return
	 */
	@SuppressWarnings("static-access")
	public boolean batchInsert(List<DBObject> dbses, String collectionName) {
		try {
			// �õ���Ӧ����
			DBCollection collection = this.dbConnection.getCollection(collectionName);
			
			// ������������
			collection.insert(dbses);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	/**WQS-2014-07-08
	 * ɾ��ָ�������е��ض�����
	 * 
	 * @param dbs
	 * @param collectionName
	 * @return
	 */
	@SuppressWarnings("static-access")
	public boolean deleteByDbs(DBObject dbs, String collectionName) {
		try{
			// ���Ҫ���в����ļ���
			DBCollection collection = this.dbConnection.getCollection(collectionName);
			
			// ִ��ɾ������������Ӱ�������
			collection.remove(dbs);

			return true;
			
		}catch(Exception e) {
			e.printStackTrace();
			
			return false;
		}
	}
	

	/**
	 * WQS-2014-07-01(�ı��༭)
	 * ��������,find��ѯ��,update������,upsert���»����,multi�Ƿ���������,collName��������,
	 * return����Ӱ�����������
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
			// �õ�����
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
	 * ͨ��find����������������������һ���ǲ�ѯ���������ڶ����ǲ�ѯ���ֶ����ƣ��������ǲ�ѯ�ļ�����������ֵ��DBObject��ӳ�伯��
	 * exp:������ҳ�Ĳ�ѯ
	 * 
	 * @return
	 */
	@SuppressWarnings("static-access")
	public List<DBObject> findByKeyAndRef(DBObject ref, DBObject keys,String collectionName) {
		// ׼��Ҫ���ص�ӳ�伯��
		List<DBObject> dbObjects = new ArrayList<DBObject>();

		// ���Ҫ���в����ļ���
		DBCollection collection = this.dbConnection.getCollection(collectionName);
		
		// ������ѯ���α�
		DBCursor cursor = collection.find(ref, keys);

		// �����α��ò�ѯ�������
		while (cursor.hasNext()) {
			dbObjects.add(cursor.next());
		}

		return dbObjects;
	}
	
	public static void deleteColData(){
		
	}

}
