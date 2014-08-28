package ims.nlp.lucene.search;

import ims.crawler.cache.ApplicationContextFactory;
import ims.nlp.mongo.service.SearchMpMongoService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mongodb.DBObject;

/**
 * 初次过滤搜索结果缓冲映射池管理
 * 
 * @author superhy
 * 
 */
public class SearchMapBufferManagment {

	private static SearchMpMongoService searchMpMongoService = (SearchMpMongoService) ApplicationContextFactory.appContext
			.getBean("searchMpMongoService");

	/**
	 * 传入的keyWords格式要求：key1;key2;key3;...
	 * 
	 * @param postUrlMD5
	 * @param collectionName
	 * @param strKeyWords
	 */
	public synchronized void handleNewSearchRes(String postUrlMD5, String collectionName,
			String strKeyWords) {

		// 如果还没有对应集合的搜索结果缓冲映射集合，将其创建
		if (this.searchMpMongoService.judgeCollectionExists(collectionName) == false) {
			this.searchMpMongoService.createCollection(collectionName);
		}

		// 如果发现映射对在缓冲映射集合中已经存在，说明是第n次被检索到，更新关键词字符串
		List<DBObject> searchMapDbObjects = this.searchMpMongoService
				.findMapByPostUrlMD5(postUrlMD5, collectionName);
		if (searchMapDbObjects.size() > 0) {
			// 规定有且仅有一个结果
			Map<String, Object> searchResMap = searchMapDbObjects.get(0)
					.toMap();

			// 对新旧keyWords字符串去重
			Set<String> newKeyWordsSet = new HashSet<String>();
			String[] oldKeyWords = ((String) searchResMap.get("keyWords"))
					.split(";");
			String[] addKeyWords = strKeyWords.split(";");

			for (String strWords : oldKeyWords) {
				newKeyWordsSet.add(strWords);
			}
			for (String strWords : addKeyWords) {
				newKeyWordsSet.add(strWords);
			}

			// 收集新旧keyWord所有不重复的项
			String newKeyWords = "";
			for (String strKeyWord : newKeyWordsSet) {
				newKeyWords += (strKeyWord + ";");
			}

			// TODO delete print
			System.out.println("写入初次检索结果映射对:" + postUrlMD5 + "," + newKeyWords
					+ "," + collectionName);

			this.searchMpMongoService.updateSearchMap(postUrlMD5, newKeyWords,
					collectionName);
		} else {

			this.searchMpMongoService.insertSearchResMap(postUrlMD5,
					strKeyWords, collectionName);
		}
	}

	/**
	 * 直接传入搜索结果的处理方法
	 * 
	 * @param searchResults
	 * @param strKeyWords
	 */
	public void handleNewSearchRes(Map<String, Object> searchResults,
			String strKeyWords) {

		String postUrlMD5 = (String) searchResults.get("postUrlMD5");
		String collectionName = (String) searchResults.get("collectionName");

		this.handleNewSearchRes(postUrlMD5, collectionName, strKeyWords);
	}

	/**
	 * 获取所有的集合名称
	 */
	public Set<String> getAllSearchMpCollections() {

		return this.searchMpMongoService.getAllCollectionNames();
	}

	/**
	 * 获取某集合中所有的映射对
	 * 
	 * @param collectionName
	 * @return
	 */
	public List<Map<String, Object>> getAllSearchMpInCollection(
			String collectionName) {

		// 准备要返回的数据
		List<Map<String, Object>> allSearchResMaps = new ArrayList<Map<String, Object>>();

		List<DBObject> searchResMapObjects = this.searchMpMongoService
				.findAllMapInCollection(collectionName);

		for (DBObject eachSearchResMapObject : searchResMapObjects) {
			Map<String, Object> eachSearchResMap = eachSearchResMapObject
					.toMap();
			eachSearchResMap.put("collectionName", collectionName);

			allSearchResMaps.add(eachSearchResMap);
		}

		return allSearchResMaps;
	}

	/**
	 * 删除过期的映射对缓存
	 */
	public void dropOldSearchMapPool() {

		Set<String> collectionNames = this.getAllSearchMpCollections();
		for (String collectionName : collectionNames) {
			this.searchMpMongoService.dropSearchMapCollection(collectionName);
		}
	}

	public SearchMpMongoService getSearchMpMongoService() {
		return searchMpMongoService;
	}

	public void setSearchMpMongoService(
			SearchMpMongoService searchMpMongoService) {
		this.searchMpMongoService = searchMpMongoService;
	}

}
