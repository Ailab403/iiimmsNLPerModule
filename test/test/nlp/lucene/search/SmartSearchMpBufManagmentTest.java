package test.nlp.lucene.search;

import ims.nlp.lucene.search.SmartSearchMpBufManagment;

import org.junit.Test;

public class SmartSearchMpBufManagmentTest {

	@Test
	public void testHandleNewSearchRes() {
		String postUrlMD5 = "test123456";
		String collectionName = "testCollection";
		String strKeyWords = "�й�����������ˮ������֮��,�������ô���������ǿ����˼���";

		SmartSearchMpBufManagment smartSearchMpBufManagment = new SmartSearchMpBufManagment(
				"mmseg4j");

		smartSearchMpBufManagment.handleNewSearchRes(postUrlMD5,
				collectionName, strKeyWords);
	}
}
