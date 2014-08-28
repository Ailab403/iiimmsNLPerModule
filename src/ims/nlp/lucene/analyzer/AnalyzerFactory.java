package ims.nlp.lucene.analyzer;

import ims.nlp.lucene.analyzer.detial.MmsegDiyAnalyzer;
import ims.nlp.lucene.analyzer.util.LoadSynWordContext;
import ims.nlp.lucene.util.LoadStopWordsList;

import java.util.List;

import org.apache.lucene.analysis.Analyzer;

public class AnalyzerFactory {

	private static String analyzerName = null;

	/**
	 * ͨ���ִ�������������Ӧ�ķִ���������һ��������ͣ�ôʵ��б��������ͣ�ô������ֵ��ϵͳ
	 * 
	 * @param analyzerName
	 * @param stopWordList
	 * @return
	 */
	public static Analyzer produceDiyAnalyzer(List<String> stopWordList) {

		Analyzer analyzer = null;

		// ���������ǿ�ֵ��ϵͳ�Զ�����ͣ�ô�
		if (stopWordList == null) {
			stopWordList = LoadStopWordsList.getStopWordsList();
		}

		if (analyzerName == null || analyzerName.equals("")) {
			// �������ķִ�������Ϊ�գ�Ĭ��ʹ��mmseg4j
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
