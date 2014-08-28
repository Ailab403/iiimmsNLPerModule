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

	// ����MongoDB���ݿ����Ӷ���
	public static Mongo mongoConnection = null;
	// ����MongoDB�������ݿ�Ķ���
	public static DB dbConnection = null;

	// ����ģʽ����̬�ڲ���
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

	// ������ʵ��
	public static final MongoSearchMapPoolDbBean getMongoDbBean() {
		return MongoDbBeanHolder.MONGO_DB_BEAN;
	}

	// MongoDB�Ļ������ݲ���

	/**
	 * ���ش��ڵ����м��ϵ�����
	 */
	public Set<String> getCollectionNames() {
		Set<String> colls = this.dbConnection.getCollectionNames();
		return colls;
	}

	/**
	 * �ж��Ƿ����ĳ����
	 */
	public boolean collectionExistsOrNot(String collectionName) {

		boolean flagExists = this.dbConnection.collectionExists(collectionName);

		return flagExists;
	}

	/**
	 * �����µ����ݿ⼯��
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
	 * ��ָ���������������
	 * 
	 * @param dbs
	 * @param collectionName
	 * @return
	 */
	public boolean insert(DBObject dbs, String collectionName) {

		try {
			// ��ö�Ӧ����
			DBCollection collection = this.dbConnection
					.getCollection(collectionName);
			// ��������
			collection.insert(dbs);

			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * ͨ��find����������������������һ���ǲ�ѯ���������ڶ����ǲ�ѯ���ֶ����ƣ��������ǲ�ѯ�ļ�����������ֵ��DBObject��ӳ�伯��
	 * exp:������ҳ�Ĳ�ѯ
	 * 
	 * @return
	 */
	public List<DBObject> findByKeyAndRef(DBObject ref, DBObject keys,
			String collectionName) {

		// ׼��Ҫ���ص�ӳ�伯��
		List<DBObject> dbObjects = new ArrayList<DBObject>();

		// ���Ҫ���в����ļ���
		DBCollection collection = this.dbConnection
				.getCollection(collectionName);
		// ������ѯ���α�
		DBCursor cursor = collection.find(ref, keys);

		// �����α��ò�ѯ�������
		while (cursor.hasNext()) {
			dbObjects.add(cursor.next());
		}

		return dbObjects;
	}

	/**
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
	public int updateValue(DBObject find, DBObject update, boolean upsert,
			boolean multi, String collName) {

		// �õ�����
		DBCollection coll = this.dbConnection.getCollection(collName);

		int count = coll.update(find, update, upsert, multi).getN();
		return count;
	}

	/**
	 * ���ݼ������ƣ�ֱ��ɾ������
	 * 
	 * @param collectionName
	 * @return
	 */
	public boolean dropCollection(String collectionName) {

		try {
			// ���Ҫ���в����ļ���
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
