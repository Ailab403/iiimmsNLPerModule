package ims.nlp.lucene.index.branch;

import ims.crawler.cache.ApplicationContextFactory;
import ims.nlp.lucene.index.WriteDocIntoIndex;
import ims.nlp.lucene.util.TransMongoContentForIndex;
import ims.nlp.mongo.service.ProductIndexMongoService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.lucene.analysis.Analyzer;
import org.springframework.context.ApplicationContext;

import com.mongodb.DBObject;

/**
 * ȫ�������Ʒ��Ϣ��ʼ���������߳�
 * 
 * @author superhy
 * 
 */
public class InitProductIndexThread implements Callable<Boolean> {

	// ����߳���ִ�д�������������ļ�������
	private String collectionName;
	// ���õķִ���
	private Analyzer analyzer;
	// ���������õõ�ַ��ѯ����
	private String indexProductContentPath;

	public InitProductIndexThread(String collectionName, Analyzer analyzer,
			String indexProductContentPath) {
		super();
		this.collectionName = collectionName;
		this.analyzer = analyzer;
		this.indexProductContentPath = indexProductContentPath;
	}

	/**
	 * �õ�ĳ�����������еĲ�Ʒ����
	 * 
	 * @return
	 */
	public List<DBObject> getProductsInColl() {

		ApplicationContext appContext = ApplicationContextFactory.appContext;
		ProductIndexMongoService productIndexMongoService = (ProductIndexMongoService) appContext
				.getBean("productIndexMongoService");

		List<DBObject> productObjects = productIndexMongoService
				.findAllProductInColl(this.collectionName);

		return productObjects;
	}

	/**
	 * ��ÿ����Ʒ����ת���ɽ���������Ҫ��ӳ�伯��
	 * 
	 * @param postObject
	 * @return
	 */
	public Map<String, Object> transProductContent(DBObject productObject) {
		Map<String, Object> productIndexContentMap = TransMongoContentForIndex
				.produceProductIndexContent(productObject, this.collectionName);

		return productIndexContentMap;
	}

	/**
	 * ��ÿһ����Ʒ��Ϣд���������������Ƿ�ɹ�
	 * 
	 * @param postIndexContentMap
	 * @return
	 */
	public boolean writeProductIntoIndex(
			Map<String, Object> productIndexContentMap) {

		// ȡ����Ҫ������������ֵ
		String nodeContent = (String) productIndexContentMap.get("nodeContent");
		String collectionName = (String) productIndexContentMap
				.get("collectionName");
		int siteId = (Integer) productIndexContentMap.get("siteId");
		String nodeId = (String) productIndexContentMap.get("nodeId");

		try {
			WriteDocIntoIndex.writeSingleProductIntoIndex(nodeContent,
					collectionName, nodeId, siteId,
					this.indexProductContentPath, this.analyzer);

			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return false;
		}
	}

	public Boolean call() throws Exception {
		// �ж��Ƿ�ȫ���ɹ�д�������ı��
		Boolean succFlag = true;

		// ��õ������������еĽڵ����
		List<DBObject> productObjects = this.getProductsInColl();

		for (DBObject productObject : productObjects) {
			Map<String, Object> productIndexContentMap = this
					.transProductContent(productObject);

			Boolean singleSuccFlag = this
					.writeProductIntoIndex(productIndexContentMap);
			if (singleSuccFlag == false) {
				succFlag = false;
			}
		}

		// �����Ƿ�ȫ���ɹ�
		return succFlag;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

	public String getIndexProductContentPath() {
		return indexProductContentPath;
	}

	public void setIndexProductContentPath(String indexProductContentPath) {
		this.indexProductContentPath = indexProductContentPath;
	}

}
