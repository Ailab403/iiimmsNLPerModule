package ims.nlp.lucene.search.branch;

import ims.crawler.cache.ApplicationContextFactory;
import ims.nlp.mongo.service.ProductIndexMongoService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.mongodb.DBObject;

/**
 * 
 * @author superhy
 * 
 */
public class SearchInProductContent {

	private Directory directory;
	private IndexReader reader;

	public SearchInProductContent(String indexPath) {
		super();
		try {

			// ����������Ӳ�̵���
			directory = FSDirectory.open(new File(indexPath));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	 * ģ��������ѯ,fieldΪ��ѯ����ֵ,valueΪ��ѯ�Ĺؼ���,numΪ��ѯ������
	 * 
	 * @param field
	 * @param value
	 * @param num
	 * @return
	 */
	public List<Map<String, Object>> phraseQuerySearcher(String value,
			Integer num, Analyzer analyzer) {

		// ׼����ѯ���ؽ��
		List<Map<String, Object>> searchResults = new ArrayList<Map<String, Object>>();

		try {
			// ����IndexReader����IndexSeacher
			IndexSearcher searcher = this.getSearcher();

			// ����������Query
			// ����parser��ȷ���������ݣ��ڶ���������ʾ�����������һ��������ʾ��ʹ�õķִ���
			String fieldName = "nodeContent";
			QueryParser parser = new QueryParser(Version.LUCENE_35, fieldName,
					analyzer);
			// �趨��������Ϊ"��"����
			parser.setDefaultOperator(QueryParser.OR_OPERATOR);
			// ����query��ʾ������Ϊcontent�����ƶ����ĵ���ʹ�ö����ѯ
			Query query = parser.parse(value);

			// TODO delete print
			System.out.println(query.toString());

			// ����seacher�������ҷ���TopDocs
			TopDocs tds = searcher.search(query, num);

			// ����TopDocs��ȡScoreDoc����
			ScoreDoc[] sds = tds.scoreDocs;
			for (ScoreDoc sd : sds) {

				// ����seacher��ScoreDoc�����ȡ�����Documnet����
				Document d = searcher.doc(sd.doc);

				String collectionName = d.get("collectionName");
				String siteId = d.get("siteId");
				String nodeId = d.get("nodeId");

				// װ�ز�ѯ���ؽ��
				Map<String, Object> searchResultMap = new HashMap<String, Object>();
				searchResultMap.put("collectionName", collectionName);
				searchResultMap.put("siteId", siteId);
				searchResultMap.put("nodeId", nodeId);

				// ���Ӹ���֧��
				String nodeContentText = this.getNodeContentText(
						d.get("nodeId"), d.get("collectionName"));
				String highlighterContent = this.prodHighlighterStr(analyzer,
						query, nodeContentText, fieldName);
				searchResultMap.put("highlighterContent", highlighterContent);

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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return searchResults;
	}

	/**
	 * ���Ӳ�ѯ�ĸ���֧��
	 * 
	 * @param analyzer
	 * @param query
	 * @param highlighterText
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public String prodHighlighterStr(Analyzer analyzer, Query query,
			String highlighterText, String fieldName) throws Exception {
		// ׼�������ַ���
		String strHighLighter = "";

		// ��ȡ������ѯ������query
		QueryScorer scorer = new QueryScorer(query);
		// ���ø�����ʽ
		Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
		Formatter formatter = new SimpleHTMLFormatter(
				"<span style='color:red'>", "</span>");
		Highlighter highlighter = new Highlighter(formatter, scorer);
		highlighter.setTextFragmenter(fragmenter);
		// ��ȡ�����ַ���
		strHighLighter = highlighter.getBestFragment(analyzer, fieldName,
				highlighterText);

		return strHighLighter;
	}

	/**
	 * Ϊ����֧��׼����Ʒ���ݣ���mongodb�в�ѯ��
	 * 
	 * @param nodeId
	 * @param collectionName
	 * @return
	 */
	public String getNodeContentText(String nodeId, String collectionName) {
		ProductIndexMongoService productIndexMongoService = (ProductIndexMongoService) ApplicationContextFactory.appContext
				.getBean("productIndexMongoService");
		DBObject productoObject = productIndexMongoService
				.findProductInCollByNodeId(nodeId, collectionName);
		String nodeContent = (String) productoObject.get("nodeContent");

		return nodeContent;
	}
}
