package ims.nlp.lucene.index;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;

/**
 * 
 * @author superhy
 * 
 */
public class WriteDocIntoIndex {

	/**
	 * ��ʼ������Ŀ¼
	 * 
	 * @param indexPath
	 */
	public static Directory loadDirectory(String indexPath) {

		Directory directory = null;

		try {

			// ����������Ӳ�̵���
			directory = FSDirectory.open(new File(indexPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return directory;
	}

	/**
	 * ��ԭʼ������������д���ĵ���Ϣ
	 * 
	 * @param content
	 * @param collectionName
	 * @param postUrlMD5
	 * @param taskLogId
	 * @param indexPath
	 * @param analyzer
	 */
	public synchronized static void writeSinglePostIntoIndex(String content,
			String collectionName, String postUrlMD5, String taskLogId,
			String indexPath, Analyzer analyzer) {

		Directory directory = loadDirectory(indexPath);

		IndexWriter writer = null;

		try {
			// ����indexWriter
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35,
					analyzer);
			writer = new IndexWriter(directory, iwc);

			// ����document
			Document document = new Document();

			// TODO delete print
			System.out.println("���ڴ���������" + collectionName + " " + postUrlMD5);

			// ��document�������ֵ�������Ƿ�洢���Ƿ�ִ�
			document.add(new Field("content", content, Field.Store.NO,
					Field.Index.ANALYZED));
			document.add(new Field("collectionName", collectionName,
					Field.Store.YES, Field.Index.NOT_ANALYZED));
			document.add(new Field("postUrlMD5", postUrlMD5, Field.Store.YES,
					Field.Index.NOT_ANALYZED));
			document.add(new Field("taskLogId", taskLogId, Field.Store.YES,
					Field.Index.NOT_ANALYZED));

			// ͨ��IndexWriter����ĵ���������
			writer.addDocument(document);
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				// ע��ر�����д��������������д��
				if (writer != null) {
					writer.close();
				}
			} catch (CorruptIndexException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public synchronized static void writeSingleProductIntoIndex(String nodeContent,
			String collectionName, String nodeId, Integer siteId,
			String indexPath, Analyzer analyzer) {

		Directory directory = loadDirectory(indexPath);

		IndexWriter writer = null;

		try {
			// ����indexWriter
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35,
					analyzer);
			writer = new IndexWriter(directory, iwc);

			// ����document
			Document document = new Document();

			// TODO delete print
			System.out.println("���ڴ���������" + collectionName + " " + nodeId);

			// ��document�������ֵ�������Ƿ�洢���Ƿ�ִ�
			document.add(new Field("nodeContent", nodeContent, Field.Store.NO,
					Field.Index.ANALYZED));
			document.add(new Field("collectionName", collectionName,
					Field.Store.YES, Field.Index.NOT_ANALYZED));
			document.add(new Field("nodeId", nodeId, Field.Store.YES,
					Field.Index.NOT_ANALYZED));
			document.add(new Field("siteId", siteId.toString(),
					Field.Store.YES, Field.Index.NOT_ANALYZED));

			// ͨ��IndexWriter����ĵ���������
			writer.addDocument(document);
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				// ע��ر�����д��������������д��
				if (writer != null) {
					writer.close();
				}
			} catch (CorruptIndexException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
