package ims.nlp.lucene.search;

import ims.nlp.lucene.analyzer.AnalyzerFactory;

import java.io.StringReader;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class SmartSearchMpBufManagment extends SearchMapBufferManagment {

	private String analyzerName;

	public SmartSearchMpBufManagment(String analyzerName) {
		super();
		if (analyzerName == null || analyzerName.equals("")) {
			this.analyzerName = "mmseg4j";
		}
		this.analyzerName = analyzerName;
	}

	/**
	 * 智能载入分词器，将不规则的关键词字符串转化为规则的关键词字符串
	 * 
	 * @param strKeyWords
	 * @return
	 * @throws Exception
	 */
	public String smartAnalyzerWords(String strKeyWords) throws Exception {

		// 载入分词器
		AnalyzerFactory.setAnalyzerName(this.analyzerName);
		Analyzer analyzer = AnalyzerFactory.produceDiyAnalyzer(null);

		String analyzedKeyWords = "";

		String[] strKeyWordList = strKeyWords.split(";");

		for (String strKeyWord : strKeyWordList) {
			TokenStream stream = analyzer.tokenStream("content",
					new StringReader(strKeyWord));

			// 创建一个属性，这个属性会添加流中，随着这个TokenStream增加
			CharTermAttribute cta = stream
					.addAttribute(CharTermAttribute.class);
			while (stream.incrementToken()) {
				analyzedKeyWords += (cta.toString() + ";");
			}
		}

		return analyzedKeyWords;
	}

	/**
	 * 传入的keyWords格式要求：无要求
	 * 
	 * @param postUrlMD5
	 * @param collectionName
	 * @param strKeyWords
	 */
	@Override
	public void handleNewSearchRes(String postUrlMD5, String collectionName,
			String strKeyWords) {

		try {
			strKeyWords = this.smartAnalyzerWords(strKeyWords);

			super.handleNewSearchRes(postUrlMD5, collectionName, strKeyWords);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 直接传入搜索结果的处理方法
	 * 
	 * @param searchResults
	 * @param strKeyWords
	 */
	@Override
	public void handleNewSearchRes(Map<String, Object> searchResults,
			String strKeyWords) {

		try {
			strKeyWords = this.smartAnalyzerWords(strKeyWords);

			super.handleNewSearchRes(searchResults, strKeyWords);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getAnalyzerName() {
		return analyzerName;
	}

	public void setAnalyzerName(String analyzerName) {
		this.analyzerName = analyzerName;
	}

}
