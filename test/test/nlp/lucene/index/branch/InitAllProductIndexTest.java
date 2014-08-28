package test.nlp.lucene.index.branch;

import ims.crawler.cache.ApplicationContextFactory;
import ims.nlp.cache.IndexDirectoryLocPath;
import ims.nlp.lucene.analyzer.AnalyzerFactory;
import ims.nlp.lucene.index.branch.InitAllProductIndex;

import org.apache.lucene.analysis.Analyzer;

public class InitAllProductIndexTest {

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
		InitAllProductIndex initAllProductIndex = (InitAllProductIndex) ApplicationContextFactory.appContext
				.getBean("initAllProductIndex");

		// 记录开始时间
		long startTime = System.currentTimeMillis();

		AnalyzerFactory.setAnalyzerName("mmseg4j");
		Analyzer analyzer = AnalyzerFactory.produceDiyAnalyzer(null);
		initAllProductIndex.execCreateIndexThread(
				IndexDirectoryLocPath.LUCENE_PRODUCT_INDEX, analyzer);

		// 记录结束时间
		long endTime = System.currentTimeMillis();

		String costTime = formatDuring(endTime - startTime);
		System.out.println(costTime);
	}
}
