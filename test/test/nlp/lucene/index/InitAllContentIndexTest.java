package test.nlp.lucene.index;

import ims.crawler.cache.ApplicationContextFactory;
import ims.nlp.cache.IndexDirectoryLocPath;
import ims.nlp.lucene.analyzer.AnalyzerFactory;
import ims.nlp.lucene.index.InitAllContentIndex;

import org.apache.lucene.analysis.Analyzer;

public class InitAllContentIndexTest {

	public static String formatDuring(long mss) {
		long hours = mss / (1000 * 60 * 60);
		long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (mss % (1000 * 60)) / 1000;
		return (hours / 10 == 0 ? "0" : "") + hours + ":"
				+ (minutes / 10 == 0 ? "0" : "") + minutes + ":"
				+ (seconds / 10 == 0 ? "0" : "") + seconds;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InitAllContentIndex initAllContentIndex = (InitAllContentIndex) ApplicationContextFactory.appContext
				.getBean("initAllContentIndex");

		// ��¼��ʼʱ��
		long startTime = System.currentTimeMillis();

		AnalyzerFactory.setAnalyzerName("mmseg4j");
		Analyzer analyzer = AnalyzerFactory.produceDiyAnalyzer(null);
		initAllContentIndex.execCreateIndexThread(
				IndexDirectoryLocPath.LUCENE_ALL_INDEX, analyzer);

		// ��¼����ʱ��
		long endTime = System.currentTimeMillis();

		String costTime = formatDuring(endTime - startTime);
		System.out.println(costTime);
	}

}
