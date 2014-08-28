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
 * ����ע�룩
 * 
 * @author superhy
 * 
 */
public class IndexDocManagment {

	// ��springע���Ӧ��mongoService
	private RetrievalMongoService retrievalMongoService;
	// ��springע���Ӧ��mysqlService
	private TaskLogService taskLogService;
	// ��springע���Ӧ������Ԫ��
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
	 * ��ʼ��������Ҫ���������ʵ�壨������ǰ���ã�
	 * 
	 * @param index
	 */
	public void initIndexEntity(Index index) {
		setIndex(index);
	}

	/**
	 * ����������־��Ϣ��ɾ�������е��ĵ�
	 * 
	 * @param taskLogId
	 */
	public void deleteTaskLogDocFromIndex(String taskLogId) {

		if (this.index == null) {
			System.err.println("������Ϣʵ��Ϊ�գ����飡");
			return;
		}

		// ���Ҫ���в�������������Ϣ
		String indexPath = this.index.getIndexPath();
		Analyzer analyzer = AnalyzerFactory.produceDiyAnalyzer(null);

		DeleteDocFromIndex.deleteDocFromIndexByTaskLogId(taskLogId, indexPath,
				analyzer);

		this.handleIndexResult.updateIndexEntity(this.index);
	}

	/**
	 * ����վ����ǳƣ���������ɾ�������е��ĵ�
	 * 
	 * @param nickName
	 */
	public void deleteSiteDocFromIndex(String nickName) {

		if (this.index == null) {
			System.err.println("������Ϣʵ��Ϊ�գ����飡");
			return;
		}

		// ���Ҫ���в�������������Ϣ
		String indexPath = this.index.getIndexPath();
		Analyzer analyzer = AnalyzerFactory.produceDiyAnalyzer(null);

		DeleteDocFromIndex.deleteDocFromIndexByCollectionName(nickName,
				indexPath, analyzer);

		this.handleIndexResult.updateIndexEntity(this.index);
	}

	/**
	 * ɾ�������е�ȫ���ĵ�
	 */
	public void deleteAllDocFromIndex() {

		if (this.index == null) {
			System.err.println("������Ϣʵ��Ϊ�գ����飡");
			return;
		}

		// ���Ҫ���в�������������Ϣ
		String indexPath = this.index.getIndexPath();
		Analyzer analyzer = AnalyzerFactory.produceDiyAnalyzer(null);

		DeleteDocFromIndex.clearAllIndex(indexPath, analyzer);

		this.handleIndexResult.updateIndexEntity(this.index);
	}

	/**
	 * �ָ���������վ�е��ĵ�
	 */
	public void recoverAllDocInRecycle() {

		if (this.index == null) {
			System.err.println("������Ϣʵ��Ϊ�գ����飡");
			return;
		}

		if (this.index.getDocDeletedNum() == 0) {
			System.err.println("���������Ѿ�û���ĵ���");
			return;
		}

		// ���Ҫ���в�������������Ϣ
		String indexPath = this.index.getIndexPath();

		DeleteDocFromIndex.recoverAllDeleteDoc(indexPath);

		this.handleIndexResult.updateIndexEntity(this.index);
	}

	/**
	 * �������µ�������������
	 */
	public void refreshNewTaskDocIntoIndex() {

		if (this.index == null) {
			System.err.println("������Ϣʵ��Ϊ�գ����飡");
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
	 * ���״Ӵ�����ɾ������
	 */
	public void dropIndexFromDis() {

		if (this.index == null) {
			System.err.println("������Ϣʵ��Ϊ�գ����飡");
			return;
		}

		// ���Ҫ���в�������������Ϣ
		String indexPath = this.index.getIndexPath();

		try {
			FileDocumentIOManagement.delAllFileInFolder(indexPath);

			this.handleIndexResult.abolishIndexEntity(this.index);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			System.err.println("ɾ��ʧ�ܣ������ļ��Ƿ�ռ�ã�");
		}

	}

	/**
	 * �������½�������
	 * 
	 * @param analyzerName
	 */
	public void rebuildIndexCompletely(String analyzerName) {

		if (this.index == null) {
			System.err.println("������Ϣʵ��Ϊ�գ����飡");
			return;
		}

		// ���Ҫ���в�������������Ϣ
		String indexPath = this.index.getIndexPath();
		String indexExp = this.index.getIndexExp();

		// ������ֶ��趨�µķִ������ƣ����������Ĭ�Ϸִ���
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

			System.err.println("ɾ��ʧ�ܣ������ļ��Ƿ�ռ�ã�");
		}

		// ��ʱ���ݲ�ͬ���������͵��ò�ͬ���ཨ������
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
