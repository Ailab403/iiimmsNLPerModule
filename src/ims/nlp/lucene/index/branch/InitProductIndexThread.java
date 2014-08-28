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
 * 全部分类产品信息初始索引建立线程
 * 
 * @author superhy
 * 
 */
public class InitProductIndexThread implements Callable<Boolean> {

	// 这个线程所执行创建索引分任务的集合名称
	private String collectionName;
	// 所用的分词器
	private Analyzer analyzer;
	// 索引所建得得地址查询参数
	private String indexProductContentPath;

	public InitProductIndexThread(String collectionName, Analyzer analyzer,
			String indexProductContentPath) {
		super();
		this.collectionName = collectionName;
		this.analyzer = analyzer;
		this.indexProductContentPath = indexProductContentPath;
	}

	/**
	 * 得到某个集合中所有的产品对象
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
	 * 将每个产品对象转化成建立索引需要的映射集合
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
	 * 将每一个产品信息写入索引，并返回是否成功
	 * 
	 * @param postIndexContentMap
	 * @return
	 */
	public boolean writeProductIntoIndex(
			Map<String, Object> productIndexContentMap) {

		// 取出需要建立索引的域值
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
		// 判断是否全部成功写入索引的标记
		Boolean succFlag = true;

		// 获得单个集合中所有的节点对象
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

		// 返回是否全部成功
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
