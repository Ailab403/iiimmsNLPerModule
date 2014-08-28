package ims.nlp.lucene.index;

import ims.nlp.handle.HandleIndexResult;
import ims.nlp.mongo.service.RetrievalMongoService;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.lucene.analysis.Analyzer;

/**
 * 初始创建原始库中所有内容的索引（需注入）
 * 
 * @author superhy
 * 
 */
public class InitAllContentIndex {

	// 从spring注入对应的mongoService
	private RetrievalMongoService retrievalMongoService;
	// 从spring注入对应的索引结果处理实体
	private HandleIndexResult handleIndexResult;

	// mongo中当前所有的集合名
	private Set<String> collectionsName;

	public void getAllCollections() {
		setCollectionsName(this.retrievalMongoService.findAllCollectionsName());
	}

	/**
	 * 将新建的索引信息创建记录到mysql数据库
	 * 
	 * @param indexPath
	 */
	public void addNewIndexEntity(String indexPath) {
		int setId = 1;
		String indexName = "lucene_all_index";
		String indexExp = "all content index";

		this.handleIndexResult.createNewIndexEntity(setId, indexName,
				indexPath, indexExp);
	}

	/**
	 * 执行线程写入索引
	 */
	public boolean execCreateIndexThread(String indexAllContentPath,
			Analyzer analyzer) {

		// 首先获得所有集合的名称
		this.getAllCollections();

		// 建立线程池
		ExecutorService exes = Executors.newCachedThreadPool();
		Set<Future<Boolean>> setThreads = new HashSet<Future<Boolean>>();

		// 为每一个集合提交一个线程任务
		for (String collectionName : getCollectionsName()) {
			InitContentIndexThread initContentIndexThread = new InitContentIndexThread(
					collectionName, analyzer, indexAllContentPath);

			setThreads.add(exes.submit(initContentIndexThread));
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

		// 如果创建索引成功，向mysql数据库中添加新的索引信息
		if (succAllflag == true) {
			this.addNewIndexEntity(indexAllContentPath);
		}

		return succAllflag;
	}

	public RetrievalMongoService getRetrievalMongoService() {
		return retrievalMongoService;
	}

	public void setRetrievalMongoService(
			RetrievalMongoService retrievalMongoService) {
		this.retrievalMongoService = retrievalMongoService;
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
