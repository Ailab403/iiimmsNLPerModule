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
 * 准备分类文本文件以及其他辅助操作（需注入）
 * 
 * @author superhy
 * 
 */
public class PrepareTextForClassifying {

	private CorpusTextService corpusTextService;

	/**
	 * 复制待测试的文件到测试文件夹
	 * 
	 * @param testCorpusTexts
	 */
	public int transTextIntoTextDir(List<CorpusText> testCorpusTexts) {
		// 先检查缓存池是否需要初始化，如果需要，进行初始化工作
		if (this.checkTestFilePoolEmpty()) {
			this.initTestFilePoolByFolder();
		}

		// 准备返回的已正常复制文档数
		int transFileNum = 0;

		// 初始化准备数据映射对
		for (CorpusText eachTestText : testCorpusTexts) {
			String textFileName = FileDocumentIOManagement
					.getTextFileName(eachTestText.getFilePath());

			// 复制文件到测试文件夹
			Boolean flagCopy = FileDocumentIOManagement.transFileToOtherFolder(
					eachTestText.getFilePath(),
					ClassifyMessionDataPath.TESTFILE_FOLDER_PATH);
			if (flagCopy) {
				System.out.println("复制文件成功");

				if (eachTestText.getInTestFolder() == 0) {
					// 将数据库中字段标记为已经存入测试文件夹
					eachTestText.setInTestFolder(1);
					this.corpusTextService.update(eachTestText);
				}

				// 将加入测试文件夹的文件信息写入文件顺序映射缓存池
				ClassifyTestFileMapPool.addNewEntityInPool(textFileName,
						eachTestText);
				// 已正常复制的文档数增加1
				transFileNum++;
			} else {
				System.err.println("复制文件发生错误");
			}
		}

		return transFileNum;
	}

	/**
	 * 检查测试文件夹中的文件是否已经标记（训练时用）
	 * 
	 * @return
	 */
	public List<CorpusText> checkTestFileHasLabeled() {

		// 准备返回的数据
		List<CorpusText> unlabeledTexts = new ArrayList<CorpusText>();

		/*
		 * 加载测试文件夹中所有的文件，对应到测试文件夹缓存池逐个检查
		 */
		File testFileFolder = new File(
				ClassifyMessionDataPath.TESTFILE_FOLDER_PATH);
		for (File eachTestFile : testFileFolder.listFiles()) {
			// 如果在缓存池中根本不存在这个文件，放弃这个文件
			if (!ClassifyTestFileMapPool.checkEntityInPool(eachTestFile
					.getName())) {
				this.abdTestFileInFolder(eachTestFile);
			}

			// 检查测试文件是否已经标记，将未标记的文件实体加入列表
			CorpusText testText = ClassifyTestFileMapPool
					.loadTextFromPool(eachTestFile.getName());
			if (testText.getLabeledSetId() == 1) {
				// 所标记类别仍然为1代表尚未标记（不良总类代表尚未标记）
				unlabeledTexts.add(testText);
			}
		}

		// 如果返回值不为空，按照返回的数据超找出相应记录，提示用户标记； 如果返回值为空，代表都被标记了
		return unlabeledTexts;
	}

	/**
	 * 在测试文件夹文件信息缓存池为空的情况下，根据文件夹中的数据初始化缓存池
	 */
	public void initTestFilePoolByFolder() {

		// 加载测试文件夹中的所有文件名
		File textFolder = new File(ClassifyMessionDataPath.TESTFILE_FOLDER_PATH);
		for (File eachTestFile : textFolder.listFiles()) {
			// 获得某个测试文件的文件名
			String testFileName = eachTestFile.getName();
			// 加载出测试文件夹中的某个文件在语料数据表中的对应信息
			CorpusText textInAllNeg = this.corpusTextService
					.loadByFileNameInAllNeg(testFileName);

			if (textInAllNeg != null) {
				if (textInAllNeg.getInTestFolder() == 0) {
					// 如果数据库中显示不是存在于测试文件夹中，修改数据库
					textInAllNeg.setInTestFolder(1);
					this.corpusTextService.update(textInAllNeg);
				}
				ClassifyTestFileMapPool.addNewEntityInPool(testFileName,
						textInAllNeg);
			} else {
				// 表中根本没有找到对应信息，放弃该文档
				this.abdTestFileInFolder(eachTestFile);
			}
		}
	}

	/**
	 * 清空测试文件夹
	 */
	public void clearTestFileFolder() {

		// 删除测试文件夹下面所有的文件
		FileDocumentIOManagement
				.delAllFileInFolder(ClassifyMessionDataPath.TESTFILE_FOLDER_PATH);
		// 清空缓存池
		ClassifyTestFileMapPool.clearBufferPool();

		// 清空mysql表中标记字段
		List<CorpusText> corpusTexts = this.corpusTextService
				.listByInTestFolder(1);
		for (CorpusText corpusText : corpusTexts) {
			corpusText.setInTestFolder(0);
			this.corpusTextService.update(corpusText);
		}
	}

	/**
	 * 放弃测试文件夹下的某个文件
	 * 
	 * @param testFile
	 */
	public void abdTestFileInFolder(File testFile) {

		// 删除测试文件夹下的某个文件
		FileDocumentIOManagement.delFile(testFile.getAbsolutePath());
		// 删除缓存池中对应的对象
		if (ClassifyTestFileMapPool.checkEntityInPool(testFile.getName())) {
			ClassifyTestFileMapPool.deleteEntityFromPool(testFile.getName());
		}
	}

	/**
	 * 放弃某个测试文本（传入文件名为参数）
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
	 * 检查测试文件缓存池是否为空
	 * 
	 * @return
	 */
	public Boolean checkTestFilePoolEmpty() {
		// 加载测试文件夹
		File testFolder = new File(ClassifyMessionDataPath.TESTFILE_FOLDER_PATH);

		if (ClassifyTestFileMapPool.getFileOrderMapPoolSize() == 0
				&& (testFolder.listFiles() == null || testFolder.listFiles().length != 0)) {
			// 如果缓存池本身为空而且测试文件夹中有文件，那么说明缓存池真的需要初始化
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
