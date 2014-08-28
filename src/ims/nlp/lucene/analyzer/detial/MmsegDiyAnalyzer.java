package ims.nlp.lucene.analyzer.detial;

import ims.nlp.cache.AnalyzerDataPath;
import ims.nlp.lucene.analyzer.util.SynWordContext;
import ims.nlp.lucene.analyzer.util.StopSynWordFilterFactory;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;

import com.chenlb.mmseg4j.ComplexSeg;
import com.chenlb.mmseg4j.Dictionary;
import com.chenlb.mmseg4j.analysis.MMSegTokenizer;

public class MmsegDiyAnalyzer extends Analyzer {

	// 停用词队列
	private List<String> stopWordList;
	// 同义词流
	private SynWordContext synwordContext;

	public MmsegDiyAnalyzer(List<String> stopWordList,
			SynWordContext samewordContext) {

		if (stopWordList == null) {
			this.stopWordList = new ArrayList<String>();
		} else {
			this.stopWordList = stopWordList;
		}

		this.synwordContext = samewordContext;
	}

	@Override
	public TokenStream tokenStream(String arg0, Reader arg1) {
		// 加载词库信息
		Dictionary dic = Dictionary
				.getInstance(AnalyzerDataPath.MMSEG_DIC_PATH);

		// 加载分词器的数据流
		Tokenizer mmSegTokenizer = new MMSegTokenizer(new ComplexSeg(dic), arg1);

		return new StopSynWordFilterFactory(this.stopWordList,
				this.synwordContext, mmSegTokenizer).tokenStream(null, null);
	}
}
