package ims.nlp.handle;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import ims.nlp.entity.model.ClassicTextSet;
import ims.nlp.entity.model.Index;
import ims.nlp.entity.service.ClassicTextSetService;
import ims.nlp.entity.service.IndexService;

/**
 * �漰��mysql���ݿ���������Ϣ�����ķ�������ע�룩
 * 
 * @author superhy
 * 
 */
public class HandleIndexResult {

	// ��spring��ע��service����
	private IndexService indexService;
	private ClassicTextSetService classicTextSetService;

	private ClassicTextSet classicTextSet;

	public void initClassicTextSet(int setId) {
		try {
			ClassicTextSet classicTextSet = this.classicTextSetService
					.loadById(setId);

			setClassicTextSet(classicTextSet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			System.err.println("û�в鵽��Ӧ�ķ���������");
		}
	}

	/**
	 * �����ݿ�������µ�������Ϣʵ��
	 * 
	 * @param setId
	 * @param indexName
	 * @param indexPath
	 * @param indexExp
	 */
	public void createNewIndexEntity(int setId, String indexName,
			String indexPath, String indexExp) {

		IndexReader reader = null;

		try {
			Directory directory = FSDirectory.open(new File(indexPath));
			reader = IndexReader.open(directory);
			int docNum = reader.numDocs();
			int docDeletedNum = reader.numDeletedDocs();
			Timestamp refreshTime = Timestamp.valueOf(new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss").format(new Date()));

			this.initClassicTextSet(setId);
			Index index = new Index(setId, indexName, indexPath, docNum,
					docDeletedNum, refreshTime, indexExp, this.classicTextSet);

			// ���뵽������Ϣ����
			this.indexService.add(index);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			System.err.println("���������ļ�����������������ϵ������ļ���");
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * �޸����ݿ��е�������Ϣʵ��
	 * 
	 * @param index
	 */
	public void updateIndexEntity(Index index) {

		IndexReader reader = null;

		try {
			Directory directory = FSDirectory.open(new File(index
					.getIndexPath()));
			reader = IndexReader.open(directory);
			int docNum = reader.numDocs();
			int docDeletedNum = reader.numDeletedDocs();
			Timestamp refreshTime = Timestamp.valueOf(new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss").format(new Date()));

			index.setDocNum(docNum);
			index.setDocDeletedNum(docDeletedNum);
			index.setRefreshTime(refreshTime);

			// �޸ĵ�������Ϣ����
			this.indexService.update(index);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			System.err.println("���������ļ�����������������ϵ������ļ���");
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * �ϳ�������Ϣʵ�壨��ɾ��������������Ϣ������ֹ��
	 * 
	 * @param index
	 */
	public void abolishIndexEntity(Index index) {
		index.setDocNum(0);
		index.setDocDeletedNum(0);
		index.setRefreshTime(Timestamp.valueOf(new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss").format(new Date())));
		index.setIndexExp("abolished");
		
		this.indexService.update(index);
	}

	/**
	 * ɾ��������Ϣʵ��
	 * 
	 * @param index
	 */
	@Deprecated
	public void deleteIndexEntity(Index index) {

		this.indexService.deleteById(index.getIndexId());
	}

	public IndexService getIndexService() {
		return indexService;
	}

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	public ClassicTextSetService getClassicTextSetService() {
		return classicTextSetService;
	}

	public void setClassicTextSetService(
			ClassicTextSetService classicTextSetService) {
		this.classicTextSetService = classicTextSetService;
	}

	public ClassicTextSet getClassicTextSet() {
		return classicTextSet;
	}

	public void setClassicTextSet(ClassicTextSet classicTextSet) {
		this.classicTextSet = classicTextSet;
	}

}
