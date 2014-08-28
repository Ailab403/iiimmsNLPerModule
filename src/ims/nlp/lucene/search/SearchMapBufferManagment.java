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
 * ���ι��������������ӳ��ع���
 * 
 * @author superhy
 * 
 */
public class SearchMapBufferManagment {

	private static SearchMpMongoService searchMpMongoService = (SearchMpMongoService) ApplicationContextFactory.appContext
			.getBean("searchMpMongoService");

	/**
	 * �����keyWords��ʽҪ��key1;key2;key3;...
	 * 
	 * @param postUrlMD5
	 * @param collectionName
	 * @param strKeyWords
	 */
	public synchronized void handleNewSearchRes(String postUrlMD5, String collectionName,
			String strKeyWords) {

		// �����û�ж�Ӧ���ϵ������������ӳ�伯�ϣ����䴴��
		if (this.searchMpMongoService.judgeCollectionExists(collectionName) == false) {
			this.searchMpMongoService.createCollection(collectionName);
		}

		// �������ӳ����ڻ���ӳ�伯�����Ѿ����ڣ�˵���ǵ�n�α������������¹ؼ����ַ���
		List<DBObject> searchMapDbObjects = this.searchMpMongoService
				.findMapByPostUrlMD5(postUrlMD5, collectionName);
		if (searchMapDbObjects.size() > 0) {
			// �涨���ҽ���һ�����
			Map<String, Object> searchResMap = searchMapDbObjects.get(0)
					.toMap();

			// ���¾�keyWords�ַ���ȥ��
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

			// �ռ��¾�keyWord���в��ظ�����
			String newKeyWords = "";
			for (String strKeyWord : newKeyWordsSet) {
				newKeyWords += (strKeyWord + ";");
			}

			// TODO delete print
			System.out.println("д����μ������ӳ���:" + postUrlMD5 + "," + newKeyWords
					+ "," + collectionName);

			this.searchMpMongoService.updateSearchMap(postUrlMD5, newKeyWords,
					collectionName);
		} else {

			this.searchMpMongoService.insertSearchResMap(postUrlMD5,
					strKeyWords, collectionName);
		}
	}

	/**
	 * ֱ�Ӵ�����������Ĵ�����
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
	 * ��ȡ���еļ�������
	 */
	public Set<String> getAllSearchMpCollections() {

		return this.searchMpMongoService.getAllCollectionNames();
	}

	/**
	 * ��ȡĳ���������е�ӳ���
	 * 
	 * @param collectionName
	 * @return
	 */
	public List<Map<String, Object>> getAllSearchMpInCollection(
			String collectionName) {

		// ׼��Ҫ���ص�����
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
	 * ɾ�����ڵ�ӳ��Ի���
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
