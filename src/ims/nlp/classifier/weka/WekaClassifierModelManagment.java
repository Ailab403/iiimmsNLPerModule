package ims.nlp.classifier.weka;

import ims.nlp.cache.WekaClassifierDataPath;
import ims.nlp.classifier.util.ScanAllFileInTrainFolder;
import ims.nlp.entity.model.ClassifierModel;
import ims.nlp.entity.service.ClassifierModelService;
import ims.nlp.util.FileDocumentIOManagement;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * weka����ģ�͹����ࣨ��Ҫע�룩
 * 
 * @author superhy
 * 
 */
public class WekaClassifierModelManagment {

	// ����ģ�ͷ���ʵ��
	private ClassifierModelService classifierModelService;

	/**
	 * �л����ݼ���ʽ��dataRaw�����������ݣ�dataFilter������tf��idf��������������ݣ�
	 * 
	 * @param modelId
	 */
	public void switchDataArffKind(int modelId) {
		// �������е�model
		ClassifierModel classifierModel = this.classifierModelService
				.loadById(modelId);
		
		String arffName = classifierModel.getArffName();

		// ִ���л�����
		if (arffName.equals("dataFilter")) {
			classifierModel.setArffName("dataRaw");
		} else if (arffName.equals("dataRaw")) {
			classifierModel.setArffName("dataFilter");
		}
		this.classifierModelService.update(classifierModel);
	}

	/**
	 * ����˯�ߵ�ģ��
	 * 
	 * @param modelId
	 */
	public void activeClassifierModel(int modelId) {
		// �������е�model
		ClassifierModel classifierModel = this.classifierModelService
				.loadById(modelId);

		// �Խ�Ҫ�����ģ�ͽ���ѵ��
		this.retrainClassifierModel(modelId, true);

		// ���ԭ����˯��״̬������ʹ����Ĳ���
		if (classifierModel.getActiveState() == 0) {
			classifierModel.setActiveState(1);
			this.classifierModelService.update(classifierModel);
		}
	}

	/**
	 * ���ἤ���ģ��
	 * 
	 * @param modelId
	 */
	public void freezeClassifierModel(int modelId) {
		// �������е�model
		ClassifierModel classifierModel = this.classifierModelService
				.loadById(modelId);

		// ɾ�������ϵ�model�ļ�
		FileDocumentIOManagement.delFile(classifierModel.getModelPath());

		// ���ԭ����˯��״̬������ʹ����Ĳ���
		if (classifierModel.getActiveState() == 1) {
			classifierModel.setActiveState(0);
			this.classifierModelService.update(classifierModel);
		}
	}

	/**
	 * �ֶ�����ѵ��ģ��(needArffFlag �����Ƿ���Ҫ���´���arff�ļ�)
	 * 
	 * @param modelId
	 * @param needArffFlag
	 */
	public void retrainClassifierModel(int modelId, Boolean needArffFlag) {

		// ׼��ѵ��ģ����Ҫ�Ĳ���
		ClassifierModel classifierModel = classifierModelService
				.loadById(modelId);

		String segmentSourceDir = WekaClassifierDataPath.TRAIN_SEGMENT_SOURCEDIR;
		String segmentTargetDir = WekaClassifierDataPath.TRAIN_SEGMENT_TARGETDIR;
		String dataRawPath = WekaClassifierDataPath.TRAIN_DATARAW_PATH;
		String dataFilterPath = WekaClassifierDataPath.TRAIN_DATAFILTER_PATH;
		String trainDataArffPath = WekaClassifierDataPath.TRAIN_DATADIR_PATH
				+ (classifierModel.getArffName().contains(".arff") ? classifierModel
						.getArffName()
						: (classifierModel.getArffName() + ".arff"));
		String modelObj = classifierModel.getModelObj();
		String modelPath = classifierModel.getModelPath();
		String modelParame = classifierModel.getModelParame();

		// ���÷�������ѵ��ģ��
		WekaProduceClassifierModel wekaProduceClassifierModel = new WekaProduceClassifierModel();
		if (needArffFlag == true) {
			wekaProduceClassifierModel.trainClassifierModelWithoutArff(
					segmentSourceDir, segmentTargetDir, dataRawPath,
					dataFilterPath, trainDataArffPath, modelObj, modelPath,
					modelParame);
		} else {
			wekaProduceClassifierModel.trainClassifierModelWithArff(
					trainDataArffPath, modelObj, modelPath, modelParame);
		}

		// ��ģ�͵Ĵ���ʱ��ˢ�³ɵ�ǰ��ʱ��
		Timestamp nowTime = Timestamp.valueOf(new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss").format(new Date()));
		classifierModel.setModelCreateTime(nowTime);
		this.classifierModelService.update(classifierModel);
	}

	/**
	 * �Զ�ɨ��ѵ���ı�Դ�ļ��о����Ƿ�����ѵ��ģ�ͣ������Ƿ�Ӱ���˽��в�����ģ�ͣ�
	 * 
	 * @param modelId
	 * @return
	 */
	public boolean autoRefreshClassiferModel(int modelId) {

		ScanAllFileInTrainFolder scanAllFileInTrainFolder = new ScanAllFileInTrainFolder();
		int checkRes = scanAllFileInTrainFolder
				.execCheck(WekaClassifierDataPath.TRAIN_SEGMENT_SOURCEDIR);
		if (checkRes == 1) {
			// ����������µ��ļ�����ôǿ������ѵ��ģ��
			this.retrainClassifierModel(modelId, true);

			// ��ǰģ�ͷ����˸ı䣬����true
			return true;
		}

		return false;
	}

	/**
	 * �Զ�ɨ��������е�ģ�ͣ������ڼ����ģ�ͣ�
	 * 
	 * @return
	 */
	public int autoRefreshAllModel() {

		// ���ز���Ӱ���ģ����
		int affectModelNum = 0;

		// ������е�ģ��ʵ��
		List<ClassifierModel> allActivedModel = this.classifierModelService
				.listByActiveState();
		for (ClassifierModel eachActivedModel : allActivedModel) {
			// �����ģ�ͷ����˸ı䣬��ôӰ����Ŀ��һ
			if (this.autoRefreshClassiferModel(eachActivedModel.getModelId()) == true) {
				affectModelNum++;
			}
		}

		return affectModelNum;
	}

	public ClassifierModelService getClassifierModelService() {
		return classifierModelService;
	}

	public void setClassifierModelService(
			ClassifierModelService classifierModelService) {
		this.classifierModelService = classifierModelService;
	}

}
