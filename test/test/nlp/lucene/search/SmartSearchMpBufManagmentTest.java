package test.nlp.lucene.search;

import ims.nlp.lucene.search.SmartSearchMpBufManagment;

import org.junit.Test;

public class SmartSearchMpBufManagmentTest {

	@Test
	public void testHandleNewSearchRes() {
		String postUrlMD5 = "test123456";
		String collectionName = "testCollection";
		String strKeyWords = "中国人民生活在水生火热之中,这是真的么？还是我们看到了假象？";

		SmartSearchMpBufManagment smartSearchMpBufManagment = new SmartSearchMpBufManagment(
				"mmseg4j");

		smartSearchMpBufManagment.handleNewSearchRes(postUrlMD5,
				collectionName, strKeyWords);
	}
}
