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
 * ��ʼ������Ʒ�����������ݵ���������ע�룩
 * 
 * @author superhy
 * 
 */
public class InitAllProductIndex {

	// ��springע���Ӧ��mongoService
	private ProductIndexMongoService productIndexMongoService;
	// ��springע���Ӧ�������������ʵ��
	private HandleIndexResult handleIndexResult;

	// mongo�е�ǰ���еļ�����
	private Set<String> collectionsName;

	public void getAllCollections() {
		setCollectionsName(this.productIndexMongoService
				.findAllCollectionsName());
	}

	/**
	 * ���½���������Ϣ������¼��mysql���ݿ�
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
	 * ִ���߳�д������
	 * 
	 * @param indexProductContentPath
	 * @param analyzer
	 * @return
	 */
	public boolean execCreateIndexThread(String indexProductContentPath,
			Analyzer analyzer) {

		// ���Ȼ�����м��ϵ�����
		this.getAllCollections();

		// �����̳߳�
		ExecutorService exes = Executors.newCachedThreadPool();
		Set<Future<Boolean>> setThreads = new HashSet<Future<Boolean>>();

		// Ϊÿһ�������ύһ���߳�����
		for (String collectionName : getCollectionsName()) {
			InitProductIndexThread initProductIndexThread = new InitProductIndexThread(
					collectionName, analyzer, indexProductContentPath);

			setThreads.add(exes.submit(initProductIndexThread));
		}

		// ����һ��ȫ���߳�����ִ�гɹ��ı��
		boolean succAllflag = true;
		// ��ȡÿ������ִ�еĽ��
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
