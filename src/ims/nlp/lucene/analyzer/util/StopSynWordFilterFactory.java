package ims.nlp.lucene.analyzer.util;

import java.io.Reader;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.util.Version;

public class StopSynWordFilterFactory extends Analyzer {

	// ͣ�ôʼ���
	private Set<Object> stops;
	// ͬ�����
	private SynWordContext synwordContext;

	// �ִ���������ʵ��
	private Tokenizer tokenizer;

	public StopSynWordFilterFactory(List<String> stopWordList,
			SynWordContext samewordContext, Tokenizer tokenizer) {
		// ��ʼ��ͣ�ôʼ���
		this.stops = StopFilter.makeStopSet(Version.LUCENE_35, stopWordList,
				true);
		// ��ʼ��ͬ�����
		this.synwordContext = samewordContext;
		// ��ʼ���ִ���������ʵ��
		this.tokenizer = tokenizer;
	}

	@Override
	public TokenStream tokenStream(String arg0, Reader arg1) {

		// ������ʽ���������
		return new SynWordTokenFilter(new StopFilter(Version.LUCENE_35,
				this.tokenizer, this.stops), synwordContext);
	}
}
