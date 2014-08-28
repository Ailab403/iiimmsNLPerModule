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

			// 创建索引到硬盘当中
			directory = FSDirectory.open(new File(indexPath));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 获取查询器
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
	 * 模糊布尔查询,field为查询的域值,value为查询的关键字,num为查询的数量
	 * 
	 * @param field
	 * @param value
	 * @param num
	 * @return
	 */
	public List<Map<String, Object>> phraseQuerySearcher(String value,
			Integer num, Analyzer analyzer) {

		// 准备查询返回结果
		List<Map<String, Object>> searchResults = new ArrayList<Map<String, Object>>();

		try {
			// 根据IndexReader创建IndexSeacher
			IndexSearcher searcher = this.getSearcher();

			// 创建搜索的Query
			// 创建parser来确定搜索内容，第二个参数表示搜索的域，最后一个参数表示所使用的分词器
			String fieldName = "nodeContent";
			QueryParser parser = new QueryParser(Version.LUCENE_35, fieldName,
					analyzer);
			// 设定布尔操作为"或"操作
			parser.setDefaultOperator(QueryParser.OR_OPERATOR);
			// 创建query表示搜索域为content包含制定的文档，使用短语查询
			Query query = parser.parse(value);

			// TODO delete print
			System.out.println(query.toString());

			// 根据seacher搜索并且返回TopDocs
			TopDocs tds = searcher.search(query, num);

			// 根据TopDocs获取ScoreDoc对象
			ScoreDoc[] sds = tds.scoreDocs;
			for (ScoreDoc sd : sds) {

				// 根据seacher和ScoreDoc对象获取具体的Documnet对象
				Document d = searcher.doc(sd.doc);

				String collectionName = d.get("collectionName");
				String siteId = d.get("siteId");
				String nodeId = d.get("nodeId");

				// 装载查询返回结果
				Map<String, Object> searchResultMap = new HashMap<String, Object>();
				searchResultMap.put("collectionName", collectionName);
				searchResultMap.put("siteId", siteId);
				searchResultMap.put("nodeId", nodeId);

				// 增加高亮支持
				String nodeContentText = this.getNodeContentText(
						d.get("nodeId"), d.get("collectionName"));
				String highlighterContent = this.prodHighlighterStr(analyzer,
						query, nodeContentText, fieldName);
				searchResultMap.put("highlighterContent", highlighterContent);

				searchResults.add(searchResultMap);
			}

			// 关闭searcher
			searcher.close();
			// 关闭reader
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
	 * 增加查询的高亮支持
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
		// 准备高亮字符串
		String strHighLighter = "";

		// 获取高亮查询的评分query
		QueryScorer scorer = new QueryScorer(query);
		// 设置高亮格式
		Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
		Formatter formatter = new SimpleHTMLFormatter(
				"<span style='color:red'>", "</span>");
		Highlighter highlighter = new Highlighter(formatter, scorer);
		highlighter.setTextFragmenter(fragmenter);
		// 获取高亮字符串
		strHighLighter = highlighter.getBestFragment(analyzer, fieldName,
				highlighterText);

		return strHighLighter;
	}

	/**
	 * 为高亮支持准备产品内容（从mongodb中查询）
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
