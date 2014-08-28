package test.nlp.any;

import ims.nlp.util.FileDocumentIOManagement;

import java.util.Scanner;

import org.junit.Test;

public class FileDocumentIOManagementTest {

	@Test
	public void testDelFolder() {

		String folderPath = new Scanner(System.in).next();

		FileDocumentIOManagement.delFolder(folderPath);
	}

	@Test
	public void testAllFileInFolder() {

		String folderPath = new Scanner(System.in).next();

		FileDocumentIOManagement.delAllFileInFolder(folderPath);
	}
}
