package ims.nlp.lucene.index.branch;

import ims.nlp.handle.HandleIndexResult;
import ims.nlp.mongo.service.ProductIndexMongoService;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.lucene.analysis.Analyzer;

/**
 * 初始创建产品库中所有内容的索引（需注入）
 * 
 * @author superhy
 * 
 */
public class InitAllProductIndex {

	// 从spring注入对应的mongoService
	private ProductIndexMongoService productIndexMongoService;
	// 从spring注入对应的索引结果处理实体
	private HandleIndexResult handleIndexResult;

	// mongo中当前所有的集合名
	private Set<String> collectionsName;

	public void getAllCollections() {
		setCollectionsName(this.productIndexMongoService
				.findAllCollectionsName());
	}

	/**
	 * 将新建的索引信息创建记录到mysql数据库
	 * 
	 * @param indexPath
	 */
	public void addNewIndexEntity(String indexPath) {
		int setId = -1;
		String indexName = "lucene_product_index";
		String indexExp = "all product index";

		this.handleIndexResult.createNewIndexEntity(setId, indexName,
				indexPath, indexExp);
	}

	/**
	 * 执行线程写入索引
	 * 
	 * @param indexProductContentPath
	 * @param analyzer
	 * @return
	 */
	public boolean execCreateIndexThread(String indexProductContentPath,
			Analyzer analyzer) {

		// 首先获得所有集合的名称
		this.getAllCollections();

		// 建立线程池
		ExecutorService exes = Executors.newCachedThreadPool();
		Set<Future<Boolean>> setThreads = new HashSet<Future<Boolean>>();

		// 为每一个集合提交一个线程任务
		for (String collectionName : getCollectionsName()) {
			InitProductIndexThread initProductIndexThread = new InitProductIndexThread(
					collectionName, analyzer, indexProductContentPath);

			setThreads.add(exes.submit(initProductIndexThread));
		}

		// 设置一个全部线程任务执行成功的标记
		boolean succAllflag = true;
		// 获取每个任务执行的结果
		for (Future<Boolean> future : setThreads) {

			try {
				boolean succThreadflag = future.get();
				if (succThreadflag == false) {
					succAllflag = false;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				succAllflag = false;
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				succAllflag = false;
			}
		}

		if (succAllflag == true) {
			this.addNewIndexEntity(indexProductContentPath);
		}

		return succAllflag;
	}

	public ProductIndexMongoService getProductIndexMongoService() {
		return productIndexMongoService;
	}

	public void setProductIndexMongoService(
			ProductIndexMongoService productIndexMongoService) {
		this.productIndexMongoService = productIndexMongoService;
	}

	public HandleIndexResult getHandleIndexResult() {
		return handleIndexResult;
	}

	public void setHandleIndexResult(HandleIndexResult handleIndexResult) {
		this.handleIndexResult = handleIndexResult;
	}

	public Set<String> getCollectionsName() {
		return collectionsName;
	}

	public void setCollectionsName(Set<String> collectionsName) {
		this.collectionsName = collectionsName;
	}

}
