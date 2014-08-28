package ims.nlp.lucene.analyzer;

import ims.nlp.lucene.analyzer.detial.MmsegDiyAnalyzer;
import ims.nlp.lucene.analyzer.util.LoadSynWordContext;
import ims.nlp.lucene.util.LoadStopWordsList;

import java.util.List;

import org.apache.lucene.analysis.Analyzer;

public class AnalyzerFactory {

	private static String analyzerName = null;

	/**
	 * 通过分词器名称生产对应的分词器，后面一个参数是停用词的列表，如果不设停用词则传入空值，系统
	 * 
	 * @param analyzerName
	 * @param stopWordList
	 * @return
	 */
	public static Analyzer produceDiyAnalyzer(List<String> stopWordList) {

		Analyzer analyzer = null;

		// 如果传入的是空值，系统自动加载停用词
		if (stopWordList == null) {
			stopWordList = LoadStopWordsList.getStopWordsList();
		}

		if (analyzerName == null || analyzerName.equals("")) {
			// 如果传入的分词器名称为空，默认使用mmseg4j
			analyzer = new MmsegDiyAnalyzer(stopWordList,
					new LoadSynWordContext());
		} else if (analyzerName.equals("mmseg4j")) {
			analyzer = new MmsegDiyAnalyzer(stopWordList,
					new LoadSynWordContext());
		}

		return analyzer;
	}

	public static String getAnalyzerName() {
		return analyzerName;
	}

	public static void setAnalyzerName(String analyzerName) {
		AnalyzerFactory.analyzerName = analyzerName;
	}

}
