package test.nlp.lucene.search;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import au.com.bytecode.opencsv.CSVReader;

public class AnalyzerCsvTest {

	@Test
	public void testAnalyzer() throws Exception {
		String filePath = "./src/csv_mapping_pool/test.csv";
		CSVReader reader = new CSVReader(new FileReader(filePath), ',');

		List<String[]> csvEntries = reader.readAll();

		Map<String, String> paramePairMap = new HashMap<String, String>();

		for (String[] paramePairStr : csvEntries) {
			System.out.println(paramePairStr[0] + ":" + paramePairStr[1]);

			// ��csv��ֵ��װ��mapӳ�伯��
			paramePairMap.put(paramePairStr[0], paramePairStr[1]);
		}

		// ����mapӳ�伯��
		Iterator<String> iterator = paramePairMap.keySet().iterator();
		while (iterator.hasNext()) {
			System.out.println(paramePairMap.get(iterator.next()));
		}
	}
}
