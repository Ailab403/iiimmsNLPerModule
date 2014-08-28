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
 * weka分类模型管理类（需要注入）
 * 
 * @author superhy
 * 
 */
public class WekaClassifierModelManagment {

	// 分类模型服务实体
	private ClassifierModelService classifierModelService;

	/**
	 * 切换数据集形式（dataRaw代表标称型数据，dataFilter代表经过tf·idf处理的数字型数据）
	 * 
	 * @param modelId
	 */
	public void switchDataArffKind(int modelId) {
		// 加载已有的model
		ClassifierModel classifierModel = this.classifierModelService
				.loadById(modelId);
		
		String arffName = classifierModel.getArffName();

		// 执行切换操作
		if (arffName.equals("dataFilter")) {
			classifierModel.setArffName("dataRaw");
		} else if (arffName.equals("dataRaw")) {
			classifierModel.setArffName("dataFilter");
		}
		this.classifierModelService.update(classifierModel);
	}

	/**
	 * 激活睡眠的模型
	 * 
	 * @param modelId
	 */
	public void activeClassifierModel(int modelId) {
		// 加载已有的model
		ClassifierModel classifierModel = this.classifierModelService
				.loadById(modelId);

		// 对将要激活的模型进行训练
		this.retrainClassifierModel(modelId, true);

		// 如果原本是睡眠状态，才行使激活的操作
		if (classifierModel.getActiveState() == 0) {
			classifierModel.setActiveState(1);
			this.classifierModelService.update(classifierModel);
		}
	}

	/**
	 * 冻结激活的模型
	 * 
	 * @param modelId
	 */
	public void freezeClassifierModel(int modelId) {
		// 加载已有的model
		ClassifierModel classifierModel = this.classifierModelService
				.loadById(modelId);

		// 删除磁盘上的model文件
		FileDocumentIOManagement.delFile(classifierModel.getModelPath());

		// 如果原本是睡眠状态，才行使激活的操作
		if (classifierModel.getActiveState() == 1) {
			classifierModel.setActiveState(0);
			this.classifierModelService.update(classifierModel);
		}
	}

	/**
	 * 手动重新训练模型(needArffFlag 代表是否需要重新创建arff文件)
	 * 
	 * @param modelId
	 * @param needArffFlag
	 */
	public void retrainClassifierModel(int modelId, Boolean needArffFlag) {

		// 准备训练模型需要的参数
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

		// 调用方法重新训练模型
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

		// 将模型的创建时间刷新成当前的时间
		Timestamp nowTime = Timestamp.valueOf(new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss").format(new Date()));
		classifierModel.setModelCreateTime(nowTime);
		this.classifierModelService.update(classifierModel);
	}

	/**
	 * 自动扫描训练文本源文件夹决定是否重新训练模型（返回是否影响了进行操作的模型）
	 * 
	 * @param modelId
	 * @return
	 */
	public boolean autoRefreshClassiferModel(int modelId) {

		ScanAllFileInTrainFolder scanAllFileInTrainFolder = new ScanAllFileInTrainFolder();
		int checkRes = scanAllFileInTrainFolder
				.execCheck(WekaClassifierDataPath.TRAIN_SEGMENT_SOURCEDIR);
		if (checkRes == 1) {
			// 如果发现有新的文件，那么强制重新训练模型
			this.retrainClassifierModel(modelId, true);

			// 当前模型发生了改变，返回true
			return true;
		}

		return false;
	}

	/**
	 * 自动扫描更新所有的模型（仅限于激活的模型）
	 * 
	 * @return
	 */
	public int autoRefreshAllModel() {

		// 返回操作影响的模型数
		int affectModelNum = 0;

		// 获得所有的模型实体
		List<ClassifierModel> allActivedModel = this.classifierModelService
				.listByActiveState();
		for (ClassifierModel eachActivedModel : allActivedModel) {
			// 如果有模型发生了改变，那么影响数目加一
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
