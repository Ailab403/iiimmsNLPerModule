package ims.nlp.lucene.index;

import java.sql.Timestamp;
import java.util.List;

import ims.crawlerLog.model.TaskLog;
import ims.crawlerLog.service.TaskLogService;
import ims.nlp.entity.model.Index;
import ims.nlp.handle.HandleIndexResult;
import ims.nlp.lucene.analyzer.AnalyzerFactory;
import ims.nlp.lucene.index.branch.InitAllProductIndex;
import ims.nlp.mongo.service.RetrievalMongoService;
import ims.nlp.util.FileDocumentIOManagement;

import org.apache.lucene.analysis.Analyzer;

/**
 * （需注入）
 * 
 * @author superhy
 * 
 */
public class IndexDocManagment {

	// 从spring注入对应的mongoService
	private RetrievalMongoService retrievalMongoService;
	// 从spring注入对应的mysqlService
	private TaskLogService taskLogService;
	// 从spring注入对应的其他元素
	private AddNewContentIndex addNewContentIndex;
	private HandleIndexResult handleIndexResult;
	private InitAllContentIndex initAllContentIndex;
	private InitAllProductIndex initAllProductIndex;

	private Index index;

	public IndexDocManagment() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 初始化本类需要处理的索引实体（必须提前调用）
	 * 
	 * @param index
	 */
	public void initIndexEntity(Index index) {
		setIndex(index);
	}

	/**
	 * 根据任务日志信息表删除索引中的文档
	 * 
	 * @param taskLogId
	 */
	public void deleteTaskLogDocFromIndex(String taskLogId) {

		if (this.index == null) {
			System.err.println("索引信息实体为空，请检查！");
			return;
		}

		// 获得要进行操作的索引的信息
		String indexPath = this.index.getIndexPath();
		Analyzer analyzer = AnalyzerFactory.produceDiyAnalyzer(null);

		DeleteDocFromIndex.deleteDocFromIndexByTaskLogId(taskLogId, indexPath,
				analyzer);

		this.handleIndexResult.updateIndexEntity(this.index);
	}

	/**
	 * 根据站点的昵称（集合名）删除索引中的文档
	 * 
	 * @param nickName
	 */
	public void deleteSiteDocFromIndex(String nickName) {

		if (this.index == null) {
			System.err.println("索引信息实体为空，请检查！");
			return;
		}

		// 获得要进行操作的索引的信息
		String indexPath = this.index.getIndexPath();
		Analyzer analyzer = AnalyzerFactory.produceDiyAnalyzer(null);

		DeleteDocFromIndex.deleteDocFromIndexByCollectionName(nickName,
				indexPath, analyzer);

		this.handleIndexResult.updateIndexEntity(this.index);
	}

	/**
	 * 删除索引中的全部文档
	 */
	public void deleteAllDocFromIndex() {

		if (this.index == null) {
			System.err.println("索引信息实体为空，请检查！");
			return;
		}

		// 获得要进行操作的索引的信息
		String indexPath = this.index.getIndexPath();
		Analyzer analyzer = AnalyzerFactory.produceDiyAnalyzer(null);

		DeleteDocFromIndex.clearAllIndex(indexPath, analyzer);

		this.handleIndexResult.updateIndexEntity(this.index);
	}

	/**
	 * 恢复索引回收站中的文档
	 */
	public void recoverAllDocInRecycle() {

		if (this.index == null) {
			System.err.println("索引信息实体为空，请检查！");
			return;
		}

		if (this.index.getDocDeletedNum() == 0) {
			System.err.println("索引当中已经没有文档！");
			return;
		}

		// 获得要进行操作的索引的信息
		String indexPath = this.index.getIndexPath();

		DeleteDocFromIndex.recoverAllDeleteDoc(indexPath);

		this.handleIndexResult.updateIndexEntity(this.index);
	}

	/**
	 * 按照最新的任务增量更新
	 */
	public void refreshNewTaskDocIntoIndex() {

		if (this.index == null) {
			System.err.println("索引信息实体为空，请检查！");
			return;
		}

		Timestamp indexRefreshTime = this.index.getRefreshTime();
		List<TaskLog> newTaskLogs = this.taskLogService
				.listAfterStartTime(indexRefreshTime);

		for (TaskLog taskLog : newTaskLogs) {

			// TODO load stopWordsList

			Analyzer analyzer = AnalyzerFactory.produceDiyAnalyzer(null);
			this.addNewContentIndex.execAddContentIndexThread(taskLog
					.getTaskLogId(), this.index.getIndexPath(), analyzer);
		}

		this.handleIndexResult.updateIndexEntity(this.index);
	}

	/**
	 * 彻底从磁盘上删除索引
	 */
	public void dropIndexFromDis() {

		if (this.index == null) {
			System.err.println("索引信息实体为空，请检查！");
			return;
		}

		// 获得要进行操作的索引的信息
		String indexPath = this.index.getIndexPath();

		try {
			FileDocumentIOManagement.delAllFileInFolder(indexPath);

			this.handleIndexResult.abolishIndexEntity(this.index);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			System.err.println("删除失败，请检查文件是否被占用！");
		}

	}

	/**
	 * 彻底重新建立索引
	 * 
	 * @param analyzerName
	 */
	public void rebuildIndexCompletely(String analyzerName) {

		if (this.index == null) {
			System.err.println("索引信息实体为空，请检查！");
			return;
		}

		// 获得要进行操作的索引的信息
		String indexPath = this.index.getIndexPath();
		String indexExp = this.index.getIndexExp();

		// 如果不手动设定新的分词器名称，则继续沿用默认分词器
		if (analyzerName != null && !analyzerName.equals("")) {
			AnalyzerFactory.setAnalyzerName(analyzerName);
		}
		Analyzer analyzer = AnalyzerFactory.produceDiyAnalyzer(null);

		try {
			if (!indexExp.equals("abolished")) {
				FileDocumentIOManagement.delAllFileInFolder(indexPath);
			}

			this.handleIndexResult.deleteIndexEntity(this.index);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			System.err.println("删除失败，请检查文件是否被占用！");
		}

		// 暂时根据不同的索引类型调用不同的类建立索引
		if (this.index.getIndexName().contains("all")
				&& !this.index.getIndexName().contains("product")) {
			this.initAllContentIndex.execCreateIndexThread(indexPath, analyzer);
		} else if (!this.index.getIndexName().contains("all")
				&& this.index.getIndexName().contains("product")) {
			this.initAllProductIndex.execCreateIndexThread(indexPath, analyzer);
		}

	}

	public RetrievalMongoService getRetrievalMongoService() {
		return retrievalMongoService;
	}

	public void setRetrievalMongoService(
			RetrievalMongoService retrievalMongoService) {
		this.retrievalMongoService = retrievalMongoService;
	}

	public TaskLogService getTaskLogService() {
		return taskLogService;
	}

	public void setTaskLogService(TaskLogService taskLogService) {
		this.taskLogService = taskLogService;
	}

	public AddNewContentIndex getAddNewContentIndex() {
		return addNewContentIndex;
	}

	public void setAddNewContentIndex(AddNewContentIndex addNewContentIndex) {
		this.addNewContentIndex = addNewContentIndex;
	}

	public HandleIndexResult getHandleIndexResult() {
		return handleIndexResult;
	}

	public void setHandleIndexResult(HandleIndexResult handleIndexResult) {
		this.handleIndexResult = handleIndexResult;
	}

	public InitAllContentIndex getInitAllContentIndex() {
		return initAllContentIndex;
	}

	public void setInitAllContentIndex(InitAllContentIndex initAllContentIndex) {
		this.initAllContentIndex = initAllContentIndex;
	}

	public InitAllProductIndex getInitAllProductIndex() {
		return initAllProductIndex;
	}

	public void setInitAllProductIndex(InitAllProductIndex initAllProductIndex) {
		this.initAllProductIndex = initAllProductIndex;
	}

	public Index getIndex() {
		return index;
	}

	public void setIndex(Index index) {
		this.index = index;
	}

}
