package test.nlp.util;

import ims.nlp.cache.ClassifyMessionDataPath;
import ims.nlp.util.FileDocumentIOManagement;

import org.junit.Test;

public class FileDocumentIOManagementTest {

	@Test
	public void testTransFileToOtherFolder() {
		String oldFilePath = ".\\file\\nlp_train_content\\baoli\\bl20140617_01.txt";
		String newFileFolder = ClassifyMessionDataPath.TESTFILE_FOLDER_PATH;

		Boolean flag = FileDocumentIOManagement.transFileToOtherFolder(
				oldFilePath, newFileFolder);

		System.out.println(flag);
	}
}
