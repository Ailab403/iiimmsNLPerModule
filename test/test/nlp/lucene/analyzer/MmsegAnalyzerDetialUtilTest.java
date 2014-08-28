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
		stopWordList.add("��");
		stopWordList.add("��");
		stopWordList.add("��");

		AnalyzerFactory.setAnalyzerName("mmseg4j");
		Analyzer a1 = AnalyzerFactory.produceDiyAnalyzer(null);
		Analyzer a2 = new MMSegAnalyzer(AnalyzerDataPath.MMSEG_DIC_PATH);

		// �Ա������ִ���
		Analyzer a3 = new IKAnalyzer(true);

		String txt = "�л����񹲺͹�����������������һ���������ݣ������Σ�ϰ��ƽ��Ϭ���磬��ƿ÷�������ţ��Ծ���;��ɫ";
		AnalyzerUtils.displayToken(txt, a1);
		AnalyzerUtils.displayToken(txt, a2);

		// �Ա������ִ���
		AnalyzerUtils.displayToken(txt, a3);
	}
}
