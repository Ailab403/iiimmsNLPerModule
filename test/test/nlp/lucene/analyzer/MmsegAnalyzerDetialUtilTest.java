package test.nlp.lucene.analyzer;

import ims.nlp.cache.AnalyzerDataPath;
import ims.nlp.lucene.analyzer.AnalyzerFactory;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;

public class MmsegAnalyzerDetialUtilTest {

	@Test
	public void testAnalyzer() {

		List<String> stopWordList = new ArrayList<String>();
		stopWordList.add("是");
		stopWordList.add("的");
		stopWordList.add("了");

		AnalyzerFactory.setAnalyzerName("mmseg4j");
		Analyzer a1 = AnalyzerFactory.produceDiyAnalyzer(null);
		Analyzer a2 = new MMSegAnalyzer(AnalyzerDataPath.MMSEG_DIC_PATH);

		// 对比其他分词器
		Analyzer a3 = new IKAnalyzer(true);

		String txt = "中华人民共和国，你这个人真吊爆，一不做二不休，胡锦涛，习近平，犀利哥，金瓶梅，肉蒲团，苍井空;本色";
		AnalyzerUtils.displayToken(txt, a1);
		AnalyzerUtils.displayToken(txt, a2);

		// 对比其他分词器
		AnalyzerUtils.displayToken(txt, a3);
	}
}
