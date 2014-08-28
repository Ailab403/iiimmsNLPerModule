package ims.nlp.classifier.exec;

import java.io.File;
import java.util.List;
import java.util.Map;

import ims.nlp.cache.LingpipeAnalysisDataPath;
import ims.nlp.cache.PolarityTestFileMapPool;
import ims.nlp.classifier.lingpipe.LingpipePolarityClassifyText;
import ims.nlp.entity.model.CorpusText;
import ims.nlp.entity.service.CorpusTextService;
import ims.nlp.util.FileDocumentIOManagement;

/**
 * 情感分析任务执行类（需注入）
 * 
 * @author superhy
 * 
 */
public class ExecLingpipePolarityClassifyTask {

	// 标记程序是否在进行
	public static Boolean missionRunFlag = false;

	private CorpusTextService corpusTextService;

	/**
	 * 将要进行情感分析的文本拷贝到情感分析测试文件夹
	 * 
	 * @param polarityTestTexts
	 * @return
	 */
	public void transTextToTestDir(List<CorpusText> polarityTestTexts) {
		for (CorpusText polarityText : polarityTestTexts) {
			// 转移文件
			FileDocumentIOManagement.transFileToOtherFolder(polarityText
					.getFilePath(),
					LingpipeAnalysisDataPath.LINGPIPE_POLARITYTEST_FOLDER);

			// 写入缓存池
			PolarityTestFileMapPool.addNewEntityInPool(new File(polarityText
					.getFilePath()).getName(), polarityText);
		}
	}

	/**
	 * 将极性分类结果写入数据库表，属于同一篇文章的，都自动识别修改字段
	 * 
	 * @param polarityClassifyResMaps
	 * @return
	 */
	public void handlePolarityRes(
			List<Map<String, Object>> polarityClassifyResMaps) {

		for (Map<String, Object> polarityResMap : polarityClassifyResMaps) {
			CorpusText polarityText = PolarityTestFileMapPool
					.loadTextFromPool((String) polarityResMap.get("fileName"));

			List<CorpusText> needChangeTexts = this.corpusTextService
					.listByNodeId(polarityText.getNodeId());
			for (CorpusText needChangeText : needChangeTexts) {
				needChangeText.setPolarityScore((Double) polarityResMap
						.get("polarityScore"));
				this.corpusTextService.update(needChangeText);
			}
		}
	}

	/**
	 * 极性分类完成后，清空测试文档文件夹
	 */
	public void clearPolarityTestFolder() {
		FileDocumentIOManagement
				.delAllFileInFolder(LingpipeAnalysisDataPath.LINGPIPE_POLARITYTEST_FOLDER);
		PolarityTestFileMapPool.clearBufferPool();
	}

	/**
	 * 执行极性分类（需要传入测试文档的文档信息实体列表）
	 * 
	 * @return
	 */
	public Boolean execPolarityClassifyText(List<CorpusText> polarityTestTexts) {
		try {
			missionRunFlag = true;
			this.transTextToTestDir(polarityTestTexts);
			LingpipePolarityClassifyText lingpipePolarityClassifyText = new LingpipePolarityClassifyText(
					LingpipeAnalysisDataPath.LINGPIPE_POLARITYCLASSIFIER_MODEL_PATH,
					LingpipeAnalysisDataPath.LINGPIPE_POLARITYTEST_FOLDER);
			List<Map<String, Object>> polarityClassifyResMaps = lingpipePolarityClassifyText
					.polarityClassifyText();
			this.handlePolarityRes(polarityClassifyResMaps);
			this.clearPolarityTestFolder();
			missionRunFlag = false;

			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

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
