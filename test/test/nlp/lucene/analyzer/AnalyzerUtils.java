package test.nlp.lucene.analyzer;

import ims.nlp.lucene.analyzer.detial.MmsegDiyAnalyzer;
import ims.nlp.lucene.analyzer.util.LoadSynWordContext;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

public class AnalyzerUtils {

	public static void displayToken(String str, Analyzer a) {
		try {
			TokenStream stream = a
					.tokenStream("content", new StringReader(str));
			// 创建一个属性，这个属性会添加流中，随着这个TokenStream增加
			CharTermAttribute cta = stream
					.addAttribute(CharTermAttribute.class);
			while (stream.incrementToken()) {
				System.out.print("[" + cta + "]");
			}
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void displayAllTokenInfo(String str, Analyzer a) {
		try {
			TokenStream stream = a
					.tokenStream("content", new StringReader(str));
			// 位置增量的属性，存储语汇单元之间的距离
			PositionIncrementAttribute pia = stream
					.addAttribute(PositionIncrementAttribute.class);
			// 每个语汇单元的位置偏移量
			OffsetAttribute oa = stream.addAttribute(OffsetAttribute.class);
			// 存储每一个语汇单元的信息（分词单元信息）
			CharTermAttribute cta = stream
					.addAttribute(CharTermAttribute.class);
			// 使用的分词器的类型信息
			TypeAttribute ta = stream.addAttribute(TypeAttribute.class);
			for (; stream.incrementToken();) {
				System.out.print(pia.getPositionIncrement() + ":");
				System.out.print(cta + "[" + oa.startOffset() + "-"
						+ oa.endOffset() + "]-->" + ta.type() + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSameWordAnalyzer() {
		try {
			Analyzer a2 = new MmsegDiyAnalyzer(null, new LoadSynWordContext());
			String txt = "我来自中国云南昭通昭阳区昭通师专，不是日本！";
			Directory dir = new RAMDirectory();
			IndexWriter writer = new IndexWriter(dir, new IndexWriterConfig(
					Version.LUCENE_35, a2));
			Document doc = new Document();
			doc.add(new Field("content", txt, Field.Store.YES,
					Field.Index.ANALYZED));
			writer.addDocument(doc);
			writer.close();
			IndexSearcher searcher = new IndexSearcher(IndexReader.open(dir));
			TopDocs tds = searcher.search(new TermQuery(
					new Term("content", "咱")), 10);
			// Document d = searcher.doc(tds.scoreDocs[0].doc);
			// System.out.println(d.get("content"));
			AnalyzerUtils.displayAllTokenInfo(txt, a2);
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
