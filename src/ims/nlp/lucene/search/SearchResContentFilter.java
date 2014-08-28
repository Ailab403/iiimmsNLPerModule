package ims.nlp.lucene.search;

import ims.nlp.lucene.analyzer.AnalyzerFactory;
import ims.nlp.mongo.service.RetrievalMongoService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import com.mongodb.DBObject;

/**
 * �Ե�һ�μ����Ľ�����ж��μ������ˣ����³��ֲ�����Ϣ�����ݿ�
 * 
 * @author superhy
 * 
 */
public class SearchResContentFilter {

	// ��spring��ע��mongoService
	private RetrievalMongoService retrievalMongoService;
	// lucene�����Ĵ洢����
	private Directory directory = null;

	// ����reader����
	private IndexReader reader;
	// ��÷ִ���
	Analyzer analyzer = AnalyzerFactory.produceDiyAnalyzer(null);

	/**
	 * �����ڴ�����Ŀ¼
	 */
	public void initIndexDirectory() {
		// �����������ڴ浱��
		Directory directory = new RAMDirectory();

		setDirectory(directory);
	}

	/**
	 * ����post�ڵ����ݼ�ֵ�Դ����ڴ�����(*)
	 * 
	 * @param postContentNodeMaps
	 */
	public void createContentNodeIndex(
			List<Map<String, Object>> postContentNodeMaps) {

		// ׼���������������Ŀ¼��δ��������ʼ������Ŀ¼
		if (directory == null) {
			this.initIndexDirectory();
		}

		IndexWriter writer = null;

		try {
			// ����indexWriter
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35,
					analyzer);
			writer = new IndexWriter(this.directory, iwc);

			for (Map<String, Object> eachContentNodeMap : postContentNodeMaps) {
				String nodeId = (String) eachContentNodeMap.get("nodeId");
				String content = (String) eachContentNodeMap.get("content");
				String collectionName = (String) eachContentNodeMap
						.get("collectionName");

				// ������Ϊ��ֵ
				if (nodeId == null || content == null || collectionName == null) {
					continue;
				}

				// TODO delete print
				System.out.println("���ڴ����ڴ�������nodeId��" + nodeId
						+ "��collectionName��" + collectionName);

				// ����document
				Document doc = new Document();

				doc.add(new Field("nodeId", nodeId, Field.Store.YES,
						Field.Index.NOT_ANALYZED));
				doc.add(new Field("content", content, Field.Store.YES,
						Field.Index.ANALYZED));
				doc.add(new Field("collectionName", collectionName,
						Field.Store.YES, Field.Index.NOT_ANALYZED));

				// ���ĵ�д������
				writer.addDocument(doc);
			}
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

	/**
	 * ��װ����node
	 * 
	 * @param nodeId
	 * @param content
	 * @param collectionName
	 * @return
	 */
	public Map<String, Object> produceContentNode(String nodeId,
			String content, String collectionName) {

		Map<String, Object> contentNodeMap = new HashMap<String, Object>();

		contentNodeMap.put("nodeId", nodeId);
		contentNodeMap.put("content", content);
		contentNodeMap.put("collectionName", collectionName);

		return contentNodeMap;
	}

	/**
	 * �ó���Ӧpost�ڵ�����ݼ�ֵ�Լ���(*)
	 * 
	 * @param postUrlMD5
	 * @param collectionName
	 * @return
	 */
	public List<Map<String, Object>> getPostAllContentNode(String postUrlMD5,
			String collectionName) {

		// ׼��Ҫ���ص����ݼ�ֵ��
		List<Map<String, Object>> postContentNodeMaps = new ArrayList<Map<String, Object>>();

		// ��ö�ӦMD5���mongoʵ��
		DBObject postObject = this.retrievalMongoService.findPostInCollByMD5(
				postUrlMD5, collectionName);

		// ���title����
		String title = (String) postObject.get("title");
		// ��ȡarticle�����б�
		List<DBObject> articleList = (List<DBObject>) postObject.get("article");

		boolean flagFirstArticle = true;
		if (articleList != null) {
			for (DBObject articleObject : articleList) {
				String articleId = (String) articleObject.get("articleId");
				String articleContent = (String) articleObject
						.get("articleContent");
				// ����ǵ�һƪarticle������title����
				if (flagFirstArticle == true) {
					articleContent = title + ("\r\n" + articleContent);
					flagFirstArticle = false;
				}

				postContentNodeMaps.add(this.produceContentNode(articleId,
						articleContent, collectionName));

				// �õ�һ��article�����е�replyԪ��
				List<DBObject> replyList = (List<DBObject>) articleObject
						.get("articleReply");
				if (replyList == null) {
					continue;
				}
				for (DBObject replyObject : replyList) {
					String replyId = (String) replyObject.get("replyId");
					String replyContent = (String) replyObject
							.get("replyContent");

					postContentNodeMaps.add(this.produceContentNode(replyId,
							replyContent, collectionName));
				}
			}
		}

		return postContentNodeMaps;
	}

	// ��ȡ��ѯ��
	public IndexSearcher getSearcher() {
		try {

			if (this.reader == null) {
				this.reader = IndexReader.open(this.directory);
			} else {
				IndexReader tr = IndexReader.openIfChanged(this.reader);
				if (tr != null) {
					this.reader.close();
					this.reader = tr;
				}
			}

			return new IndexSearcher(this.reader);
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ���μ�������������Ϣ�����ݿ�(*)
	 * 
	 * @param strKeyWords
	 * @param num
	 * @return
	 */
	public List<Map<String, Object>> searchSensitiveContentNode(
			String strKeyWords, Integer num) {

		// ׼�� ��ѯ���ؽ��
		List<Map<String, Object>> searchResults = new ArrayList<Map<String, Object>>();

		try {
			// ����IndexReader����IndexSeacher
			IndexSearcher searcher = this.getSearcher();

			// ����������Query
			// ����parser��ȷ���������ݣ��ڶ���������ʾ�����������һ��������ʾ��ʹ�õķִ���
			QueryParser parser = new QueryParser(Version.LUCENE_35, "content",
					this.analyzer);
			// �趨��������Ϊ"��"����
			parser.setDefaultOperator(QueryParser.OR_OPERATOR);
			// ����query��ʾ������Ϊcontent�����ƶ����ĵ���ʹ�ö����ѯ
			Query query = parser.parse(strKeyWords);

			// TODO delete print
			System.out.println(query.toString());

			// ����seacher�������ҷ���TopDocs
			if (num == null) {
				num = 1000;
			}
			TopDocs tds = searcher.search(query, num);

			// ����TopDocs��ȡScoreDoc����
			ScoreDoc[] sds = tds.scoreDocs;
			for (ScoreDoc sd : sds) {
				// ����seacher��ScoreDoc�����ȡ�����Documnet����
				Document d = searcher.doc(sd.doc);

				// װ�ز�ѯ���ؽ��
				Map<String, Object> searchResultMap = new HashMap<String, Object>();
				searchResultMap.put("nodeId", d.get("nodeId"));
				searchResultMap.put("content", d.get("content"));
				searchResultMap.put("collectionName", d.get("collectionName"));
				searchResults.add(searchResultMap);
			}

			// �ر�searcher
			searcher.close();
			// �ر�reader
			this.reader.close();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return searchResults;
	}

	/**
	 * ִ�ж��μ������˳�ϸ�ڵĲ�����Ϣ�������ǵ�һ�μ�����ӳ��Ը����ֵ���Լ��Թ��˲�����Ϣ����������޵��趨��Ĭ��Ϊ1000��
	 * 
	 * @param postUrlMD5
	 * @param collectionName
	 * @param strKeyWords
	 * @param num
	 * @return
	 */
	public List<Map<String, Object>> execSensitiveContentFilter(
			String postUrlMD5, String collectionName, String strKeyWords,
			Integer num) {

		// ��ȡָ��post�ڵ������е����ݿ���Ϣ��ֵ��
		List<Map<String, Object>> postContentNodeMaps = this
				.getPostAllContentNode(postUrlMD5, collectionName);
		// ����������Ϣ��ֵ�Դ�������
		this.createContentNodeIndex(postContentNodeMaps);
		// ���������ó����еĺ��в�����Ϣ��ֵ��
		List<Map<String, Object>> sensitiveContentNodeMaps = this
				.searchSensitiveContentNode(strKeyWords, num);

		return sensitiveContentNodeMaps;
	}

	/**
	 * ֱ�Ӵ����״μ��������ֵ�ԣ�ִ��execSensitiveContentFilter
	 * 
	 * @param searchResMap
	 * @param collectionName
	 * @param num
	 * @return
	 */
	public List<Map<String, Object>> execSensitiveContentFilter(
			Map<String, Object> searchResMap, String collectionName, Integer num) {

		String postUrlMD5 = (String) searchResMap.get("postUrlMD5");
		String strKeyWords = (String) searchResMap.get("keyWords");

		return this.execSensitiveContentFilter(postUrlMD5, collectionName,
				strKeyWords, num);
	}

	public RetrievalMongoService getRetrievalMongoService() {
		return retrievalMongoService;
	}

	public void setRetrievalMongoService(
			RetrievalMongoService retrievalMongoService) {
		this.retrievalMongoService = retrievalMongoService;
	}

	public Directory getDirectory() {
		return directory;
	}

	public void setDirectory(Directory directory) {
		this.directory = directory;
	}

}
