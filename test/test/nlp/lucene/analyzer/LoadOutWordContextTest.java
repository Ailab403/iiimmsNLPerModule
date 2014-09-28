package test.nlp.lucene.analyzer;

import ims.nlp.lucene.analyzer.util.LoadOutWordContext;

import java.io.File;

import org.junit.Test;

public class LoadOutWordContextTest {

	@Test
	public void testLoadWordFile() {
		File fileOut = new File(".\\file\\analyzer_data\\mmseg4j\\testOut.txt");
		
		LoadOutWordContext.loadWordFile(fileOut);
	}
}
