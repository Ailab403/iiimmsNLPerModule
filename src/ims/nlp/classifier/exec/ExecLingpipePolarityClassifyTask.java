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
 * ��з�������ִ���ࣨ��ע�룩
 * 
 * @author superhy
 * 
 */
public class ExecLingpipePolarityClassifyTask {

	// ��ǳ����Ƿ��ڽ���
	public static Boolean missionRunFlag = false;

	private CorpusTextService corpusTextService;

	/**
	 * ��Ҫ������з������ı���������з��������ļ���
	 * 
	 * @param polarityTestTexts
	 * @return
	 */
	public void transTextToTestDir(List<CorpusText> polarityTestTexts) {
		for (CorpusText polarityText : polarityTestTexts) {
			// ת���ļ�
			FileDocumentIOManagement.transFileToOtherFolder(polarityText
					.getFilePath(),
					LingpipeAnalysisDataPath.LINGPIPE_POLARITYTEST_FOLDER);

			// д�뻺���
			PolarityTestFileMapPool.addNewEntityInPool(new File(polarityText
					.getFilePath()).getName(), polarityText);
		}
	}

	/**
	 * �����Է�����д�����ݿ������ͬһƪ���µģ����Զ�ʶ���޸��ֶ�
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
	 * ���Է�����ɺ���ղ����ĵ��ļ���
	 */
	public void clearPolarityTestFolder() {
		FileDocumentIOManagement
				.delAllFileInFolder(LingpipeAnalysisDataPath.LINGPIPE_POLARITYTEST_FOLDER);
		PolarityTestFileMapPool.clearBufferPool();
	}

	/**
	 * ִ�м��Է��ࣨ��Ҫ��������ĵ����ĵ���Ϣʵ���б�
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
