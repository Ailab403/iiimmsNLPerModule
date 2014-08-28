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
 * 对第一次检索的结果进行二次检索过滤，留下出现不良信息的内容块
 * 
 * @author superhy
 * 
 */
public class SearchResContentFilter {

	// 从spring中注入mongoService
	private RetrievalMongoService retrievalMongoService;
	// lucene索引的存储介质
	private Directory directory = null;

	// 索引reader工具
	private IndexReader reader;
	// 获得分词器
	Analyzer analyzer = AnalyzerFactory.produceDiyAnalyzer(null);

	/**
	 * 创建内存索引目录
	 */
	public void initIndexDirectory() {
		// 创建索引到内存当中
		Directory directory = new RAMDirectory();

		setDirectory(directory);
	}

	/**
	 * 根据post节点内容键值对创建内存索引(*)
	 * 
	 * @param postContentNodeMaps
	 */
	public void createContentNodeIndex(
			List<Map<String, Object>> postContentNodeMaps) {

		// 准备工作：如果索引目录尚未创建，初始化索引目录
		if (directory == null) {
			this.initIndexDirectory();
		}

		IndexWriter writer = null;

		try {
			// 创建indexWriter
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35,
					analyzer);
			writer = new IndexWriter(this.directory, iwc);

			for (Map<String, Object> eachContentNodeMap : postContentNodeMaps) {
				String nodeId = (String) eachContentNodeMap.get("nodeId");
				String content = (String) eachContentNodeMap.get("content");
				String collectionName = (String) eachContentNodeMap
						.get("collectionName");

				// 有内容为空值
				if (nodeId == null || content == null || collectionName == null) {
					continue;
				}

				// TODO delete print
				System.out.println("正在创见内存索引，nodeId：" + nodeId
						+ "，collectionName：" + collectionName);

				// 创建document
				Document doc = new Document();

				doc.add(new Field("nodeId", nodeId, Field.Store.YES,
						Field.Index.NOT_ANALYZED));
				doc.add(new Field("content", content, Field.Store.YES,
						Field.Index.ANALYZED));
				doc.add(new Field("collectionName", collectionName,
						Field.Store.YES, Field.Index.NOT_ANALYZED));

				// 将文档写入索引
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
				// 注意关闭索引写入流，否则会产生写锁
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
	 * 组装内容node
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
	 * 得出对应post节点的内容键值对集合(*)
	 * 
	 * @param postUrlMD5
	 * @param collectionName
	 * @return
	 */
	public List<Map<String, Object>> getPostAllContentNode(String postUrlMD5,
			String collectionName) {

		// 准备要返回的内容键值对
		List<Map<String, Object>> postContentNodeMaps = new ArrayList<Map<String, Object>>();

		// 获得对应MD5码的mongo实体
		DBObject postObject = this.retrievalMongoService.findPostInCollByMD5(
				postUrlMD5, collectionName);

		// 获得title内容
		String title = (String) postObject.get("title");
		// 获取article内容列表
		List<DBObject> articleList = (List<DBObject>) postObject.get("article");

		boolean flagFirstArticle = true;
		if (articleList != null) {
			for (DBObject articleObject : articleList) {
				String articleId = (String) articleObject.get("articleId");
				String articleContent = (String) articleObject
						.get("articleContent");
				// 如果是第一篇article，加入title内容
				if (flagFirstArticle == true) {
					articleContent = title + ("\r\n" + articleContent);
					flagFirstArticle = false;
				}

				postContentNodeMaps.add(this.produceContentNode(articleId,
						articleContent, collectionName));

				// 得到一个article中所有的reply元素
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
	 * 二次检索出含不良信息的内容块(*)
	 * 
	 * @param strKeyWords
	 * @param num
	 * @return
	 */
	public List<Map<String, Object>> searchSensitiveContentNode(
			String strKeyWords, Integer num) {

		// 准备 查询返回结果
		List<Map<String, Object>> searchResults = new ArrayList<Map<String, Object>>();

		try {
			// 根据IndexReader创建IndexSeacher
			IndexSearcher searcher = this.getSearcher();

			// 创建搜索的Query
			// 创建parser来确定搜索内容，第二个参数表示搜索的域，最后一个参数表示所使用的分词器
			QueryParser parser = new QueryParser(Version.LUCENE_35, "content",
					this.analyzer);
			// 设定布尔操作为"或"操作
			parser.setDefaultOperator(QueryParser.OR_OPERATOR);
			// 创建query表示搜索域为content包含制定的文档，使用短语查询
			Query query = parser.parse(strKeyWords);

			// TODO delete print
			System.out.println(query.toString());

			// 根据seacher搜索并且返回TopDocs
			if (num == null) {
				num = 1000;
			}
			TopDocs tds = searcher.search(query, num);

			// 根据TopDocs获取ScoreDoc对象
			ScoreDoc[] sds = tds.scoreDocs;
			for (ScoreDoc sd : sds) {
				// 根据seacher和ScoreDoc对象获取具体的Documnet对象
				Document d = searcher.doc(sd.doc);

				// 装载查询返回结果
				Map<String, Object> searchResultMap = new HashMap<String, Object>();
				searchResultMap.put("nodeId", d.get("nodeId"));
				searchResultMap.put("content", d.get("content"));
				searchResultMap.put("collectionName", d.get("collectionName"));
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
		}

		return searchResults;
	}

	/**
	 * 执行二次检索过滤出细节的不良信息，参数是第一次检索的映射对各项键值，以及对过滤不良信息结果数量上限的设定（默认为1000）
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

		// 获取指定post节点中所有的内容块信息键值对
		List<Map<String, Object>> postContentNodeMaps = this
				.getPostAllContentNode(postUrlMD5, collectionName);
		// 根据内容信息键值对创建索引
		this.createContentNodeIndex(postContentNodeMaps);
		// 二次索引得出所有的含有不良信息键值对
		List<Map<String, Object>> sensitiveContentNodeMaps = this
				.searchSensitiveContentNode(strKeyWords, num);

		return sensitiveContentNodeMaps;
	}

	/**
	 * 直接传入首次检索结果键值对，执行execSensitiveContentFilter
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
