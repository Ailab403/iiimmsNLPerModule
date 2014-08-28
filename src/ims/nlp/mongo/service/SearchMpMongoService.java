package ims.nlp.mongo.service;

import java.util.List;
import java.util.Set;

import com.mongodb.DBObject;

public interface SearchMpMongoService {

	/**
	 * 获取所有的集合名称
	 * 
	 * @return
	 */
	public Set<String> getAllCollectionNames();

	/**
	 * 判断一个集合是否存在
	 * 
	 * @param collectionName
	 * @return
	 */
	public boolean judgeCollectionExists(String collectionName);

	/**
	 * 创建一个新的集合，返回创建是否成功
	 * 
	 * @param collectionName
	 * @return
	 */
	public boolean createCollection(String collectionName);

	/**
	 * 向映射池中插入新的搜索结果映射
	 * 
	 * @param postUrlMD5
	 * @param strSearchKeyWords
	 * @param collectionName
	 * @return
	 */
	public boolean insertSearchResMap(String postUrlMD5,
			String strSearchKeyWords, String collectionName);

	/**
	 * 查询到特定的结果映射对，如果没查询到，返回空队列
	 * 
	 * @param postUrlMD5
	 * @param collectionName
	 * @return
	 */
	public List<DBObject> findMapByPostUrlMD5(String postUrlMD5,
			String collectionName);

	/**
	 * 获得集合中所有的映射对
	 * 
	 * @param collectionName
	 * @return
	 */
	public List<DBObject> findAllMapInCollection(String collectionName);

	/**
	 * 更新特定映射的关键字字符串，传入映射对的MD5值，
	 * 
	 * @param postUrlMD5
	 * @param strSearchKeyWords
	 * @param collectionName
	 * @return
	 */
	public int updateSearchMap(String postUrlMD5, String strSearchKeyWords,
			String collectionName);

	/**
	 * 删除过期的缓冲映射池
	 * 
	 * @param collectionName
	 * @return
	 */
	public boolean dropSearchMapCollection(String collectionName);
}
