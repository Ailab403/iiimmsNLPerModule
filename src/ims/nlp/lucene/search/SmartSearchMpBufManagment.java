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
	 * ��������ִ�������������Ĺؼ����ַ���ת��Ϊ����Ĺؼ����ַ���
	 * 
	 * @param strKeyWords
	 * @return
	 * @throws Exception
	 */
	public String smartAnalyzerWords(String strKeyWords) throws Exception {

		// ����ִ���
		AnalyzerFactory.setAnalyzerName(this.analyzerName);
		Analyzer analyzer = AnalyzerFactory.produceDiyAnalyzer(null);

		String analyzedKeyWords = "";

		String[] strKeyWordList = strKeyWords.split(";");

		for (String strKeyWord : strKeyWordList) {
			TokenStream stream = analyzer.tokenStream("content",
					new StringReader(strKeyWord));

			// ����һ�����ԣ�������Ի�������У��������TokenStream����
			CharTermAttribute cta = stream
					.addAttribute(CharTermAttribute.class);
			while (stream.incrementToken()) {
				analyzedKeyWords += (cta.toString() + ";");
			}
		}

		return analyzedKeyWords;
	}

	/**
	 * �����keyWords��ʽҪ����Ҫ��
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
	 * ֱ�Ӵ�����������Ĵ�����
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
