package ims.nlp.handle;

import ims.nlp.entity.model.ClassifyLog;
import ims.nlp.entity.model.ClassifyResBuff;
import ims.nlp.entity.model.CorpusText;
import ims.nlp.entity.service.ClassifyResBuffService;

public class HandleClassifyResBuffResult {

	private ClassifyResBuffService classifyResBuffService;

	/**
	 * 添加新的结果缓存表记录
	 * 
	 * @param classifyLog
	 * @param corpusText
	 * @param resSetId
	 * @param limitScore
	 * @param trulyScore
	 */
	public void createNewResBuff(ClassifyLog classifyLog,
			CorpusText corpusText, int resSetId, double limitScore,
			double trulyScore) {

		/*
		 * 准备参数
		 */
		String classifyLogId = classifyLog.getClassifyLogId();
		int textId = corpusText.getTextId();
		// resSetId
		int blongSetId = corpusText.getBlongSetId();
		int labeledSetId = corpusText.getLabeledSetId();
		int siteId = corpusText.getSiteId();
		// limitScore
		// trulyScore
		String classifyFilePath = corpusText.getFilePath();

		ClassifyResBuff classifyResBuff = new ClassifyResBuff(classifyLogId,
				textId, resSetId, blongSetId, labeledSetId, siteId, limitScore,
				trulyScore, classifyFilePath);
		this.classifyResBuffService.add(classifyResBuff);
	}

	public ClassifyResBuffService getClassifyResBuffService() {
		return classifyResBuffService;
	}

	public void setClassifyResBuffService(
			ClassifyResBuffService classifyResBuffService) {
		this.classifyResBuffService = classifyResBuffService;
	}

}
