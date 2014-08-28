package test.nlp.classifier;

import ims.crawler.cache.ApplicationContextFactory;
import ims.nlp.classifier.weka.WekaProduceClassifierModel;
import ims.nlp.entity.model.ClassifierModel;
import ims.nlp.entity.service.ClassifierModelService;

import org.junit.Before;
import org.junit.Test;

public class WekaProduceClassifierModelTest {

	private WekaProduceClassifierModel produceClassifierModel;

	// 设置各种路径

	// directory
	private String segmentSourceDir = "./file/nlp_train_content";
	private String segmentTargetDir = "./file/nlp_weka/analyzed_train_content";
	// file
	private String dataRawPath = "./file/nlp_weka/train_arff/dataRaw.arff";
	private String dataFilterPath = "./file/nlp_weka/train_arff/dataFilter.arff";
	private String dataArffPath = "./file/nlp_weka/train_arff/dataFilter.arff";
	// private String dataArffPath =
	// "./file/nlp_weka/train_arff/contact-lenses.arff";

	private ClassifierModel classifierModel;

	@Before
	public void loadUseClassiferModel() {
		WekaProduceClassifierModel useClassifierModel = new WekaProduceClassifierModel();
		setProduceClassifierModel(useClassifierModel);

		ClassifierModelService classifierModelService = (ClassifierModelService) ApplicationContextFactory.appContext
				.getBean("classifierModelService");
		ClassifierModel classifierModel = classifierModelService.loadById(2);
		setClassifierModel(classifierModel);
	}

	@Test
	public void testCreateArffSource() {
		this.produceClassifierModel.createArffSource(segmentSourceDir,
				segmentTargetDir, dataRawPath, dataFilterPath);
	}

	@Test
	public void testTrainClassifierModelWithArff() {
		this.produceClassifierModel.trainClassifierModelWithArff(dataArffPath,
				this.classifierModel.getModelObj(), this.classifierModel
						.getModelPath(), this.classifierModel.getModelParame());
	}

	@Test
	public void testTrainClassifierModelWithoutArff() {
		this.produceClassifierModel.trainClassifierModelWithoutArff(
				segmentSourceDir, segmentTargetDir, dataRawPath,
				dataFilterPath, dataArffPath, this.classifierModel
						.getModelObj(), this.classifierModel.getModelPath(),
				this.classifierModel.getModelParame());
	}

	public WekaProduceClassifierModel getProduceClassifierModel() {
		return produceClassifierModel;
	}

	public void setProduceClassifierModel(
			WekaProduceClassifierModel produceClassifierModel) {
		this.produceClassifierModel = produceClassifierModel;
	}

	public ClassifierModel getClassifierModel() {
		return classifierModel;
	}

	public void setClassifierModel(ClassifierModel classifierModel) {
		this.classifierModel = classifierModel;
	}

}
