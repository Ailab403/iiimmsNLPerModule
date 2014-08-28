package ims.nlp.classifier.util;

import ims.nlp.cache.ClassifyMessionDataPath;
import ims.nlp.cache.ClassifyTestFileMapPool;
import ims.nlp.entity.model.CorpusText;
import ims.nlp.entity.service.CorpusTextService;
import ims.nlp.util.FileDocumentIOManagement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * ׼�������ı��ļ��Լ�����������������ע�룩
 * 
 * @author superhy
 * 
 */
public class PrepareTextForClassifying {

	private CorpusTextService corpusTextService;

	/**
	 * ���ƴ����Ե��ļ��������ļ���
	 * 
	 * @param testCorpusTexts
	 */
	public int transTextIntoTextDir(List<CorpusText> testCorpusTexts) {
		// �ȼ�黺����Ƿ���Ҫ��ʼ���������Ҫ�����г�ʼ������
		if (this.checkTestFilePoolEmpty()) {
			this.initTestFilePoolByFolder();
		}

		// ׼�����ص������������ĵ���
		int transFileNum = 0;

		// ��ʼ��׼������ӳ���
		for (CorpusText eachTestText : testCorpusTexts) {
			String textFileName = FileDocumentIOManagement
					.getTextFileName(eachTestText.getFilePath());

			// �����ļ��������ļ���
			Boolean flagCopy = FileDocumentIOManagement.transFileToOtherFolder(
					eachTestText.getFilePath(),
					ClassifyMessionDataPath.TESTFILE_FOLDER_PATH);
			if (flagCopy) {
				System.out.println("�����ļ��ɹ�");

				if (eachTestText.getInTestFolder() == 0) {
					// �����ݿ����ֶα��Ϊ�Ѿ���������ļ���
					eachTestText.setInTestFolder(1);
					this.corpusTextService.update(eachTestText);
				}

				// ����������ļ��е��ļ���Ϣд���ļ�˳��ӳ�仺���
				ClassifyTestFileMapPool.addNewEntityInPool(textFileName,
						eachTestText);
				// ���������Ƶ��ĵ�������1
				transFileNum++;
			} else {
				System.err.println("�����ļ���������");
			}
		}

		return transFileNum;
	}

	/**
	 * �������ļ����е��ļ��Ƿ��Ѿ���ǣ�ѵ��ʱ�ã�
	 * 
	 * @return
	 */
	public List<CorpusText> checkTestFileHasLabeled() {

		// ׼�����ص�����
		List<CorpusText> unlabeledTexts = new ArrayList<CorpusText>();

		/*
		 * ���ز����ļ��������е��ļ�����Ӧ�������ļ��л����������
		 */
		File testFileFolder = new File(
				ClassifyMessionDataPath.TESTFILE_FOLDER_PATH);
		for (File eachTestFile : testFileFolder.listFiles()) {
			// ����ڻ�����и�������������ļ�����������ļ�
			if (!ClassifyTestFileMapPool.checkEntityInPool(eachTestFile
					.getName())) {
				this.abdTestFileInFolder(eachTestFile);
			}

			// �������ļ��Ƿ��Ѿ���ǣ���δ��ǵ��ļ�ʵ������б�
			CorpusText testText = ClassifyTestFileMapPool
					.loadTextFromPool(eachTestFile.getName());
			if (testText.getLabeledSetId() == 1) {
				// ����������ȻΪ1������δ��ǣ��������������δ��ǣ�
				unlabeledTexts.add(testText);
			}
		}

		// �������ֵ��Ϊ�գ����շ��ص����ݳ��ҳ���Ӧ��¼����ʾ�û���ǣ� �������ֵΪ�գ������������
		return unlabeledTexts;
	}

	/**
	 * �ڲ����ļ����ļ���Ϣ�����Ϊ�յ�����£������ļ����е����ݳ�ʼ�������
	 */
	public void initTestFilePoolByFolder() {

		// ���ز����ļ����е������ļ���
		File textFolder = new File(ClassifyMessionDataPath.TESTFILE_FOLDER_PATH);
		for (File eachTestFile : textFolder.listFiles()) {
			// ���ĳ�������ļ����ļ���
			String testFileName = eachTestFile.getName();
			// ���س������ļ����е�ĳ���ļ����������ݱ��еĶ�Ӧ��Ϣ
			CorpusText textInAllNeg = this.corpusTextService
					.loadByFileNameInAllNeg(testFileName);

			if (textInAllNeg != null) {
				if (textInAllNeg.getInTestFolder() == 0) {
					// ������ݿ�����ʾ���Ǵ����ڲ����ļ����У��޸����ݿ�
					textInAllNeg.setInTestFolder(1);
					this.corpusTextService.update(textInAllNeg);
				}
				ClassifyTestFileMapPool.addNewEntityInPool(testFileName,
						textInAllNeg);
			} else {
				// ���и���û���ҵ���Ӧ��Ϣ���������ĵ�
				this.abdTestFileInFolder(eachTestFile);
			}
		}
	}

	/**
	 * ��ղ����ļ���
	 */
	public void clearTestFileFolder() {

		// ɾ�������ļ����������е��ļ�
		FileDocumentIOManagement
				.delAllFileInFolder(ClassifyMessionDataPath.TESTFILE_FOLDER_PATH);
		// ��ջ����
		ClassifyTestFileMapPool.clearBufferPool();

		// ���mysql���б���ֶ�
		List<CorpusText> corpusTexts = this.corpusTextService
				.listByInTestFolder(1);
		for (CorpusText corpusText : corpusTexts) {
			corpusText.setInTestFolder(0);
			this.corpusTextService.update(corpusText);
		}
	}

	/**
	 * ���������ļ����µ�ĳ���ļ�
	 * 
	 * @param testFile
	 */
	public void abdTestFileInFolder(File testFile) {

		// ɾ�������ļ����µ�ĳ���ļ�
		FileDocumentIOManagement.delFile(testFile.getAbsolutePath());
		// ɾ��������ж�Ӧ�Ķ���
		if (ClassifyTestFileMapPool.checkEntityInPool(testFile.getName())) {
			ClassifyTestFileMapPool.deleteEntityFromPool(testFile.getName());
		}
	}

	/**
	 * ����ĳ�������ı��������ļ���Ϊ������
	 * 
	 * @param fileName
	 */
	public void abdTestFileInFolder(String fileName) {
		String filePath = ClassifyMessionDataPath.TESTFILE_FOLDER_PATH + "\\"
				+ fileName;
		File testFile = new File(filePath);

		this.abdTestFileInFolder(testFile);
	}

	/**
	 * �������ļ�������Ƿ�Ϊ��
	 * 
	 * @return
	 */
	public Boolean checkTestFilePoolEmpty() {
		// ���ز����ļ���
		File testFolder = new File(ClassifyMessionDataPath.TESTFILE_FOLDER_PATH);

		if (ClassifyTestFileMapPool.getFileOrderMapPoolSize() == 0
				&& (testFolder.listFiles() == null || testFolder.listFiles().length != 0)) {
			// �������ر���Ϊ�ն��Ҳ����ļ��������ļ�����ô˵������������Ҫ��ʼ��
			return true;
		} else {
			return false;
		}
	}

	public CorpusTextService getCorpusTextService() {
		return corpusTextService;
	}

	public void setCorpusTextService(CorpusTextService corpusTextService) {
		this.corpusTextService = corpusTextService;
	}

}
