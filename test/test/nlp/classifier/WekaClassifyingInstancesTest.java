package test.nlp.classifier;

import ims.nlp.classifier.weka.WekaClassifyingInstances;

import org.junit.Before;
import org.junit.Test;

public class WekaClassifyingInstancesTest {

	private WekaClassifyingInstances classifyingInstances;

	// 设置各种路径

	// directory
	private String segmentSourceDir = "./file/nlp_classifier_test";
	private String segmentTargetDir = "./file/nlp_weka/analyzed_classifying_content";
	// private String analyzedContentPath =
	// "./file/nlp_weka/analyzed_classifying_content";
	// file
	private String dataRawPath = "./file/nlp_weka/classifying_arff/dataRaw.arff";
	private String dataFilterPath = "./file/nlp_weka/classifying_arff/dataFilter.arff";
	private String labeledDataArffPath = "./file/nlp_weka/train_arff/dataFilter.arff";
	private String unlabeledDataArffPath = "./file/nlp_weka/classifying_arff/dataFilter.arff";
	// private String labeledDataArffPath =
	// "./file/nlp_weka/train_arff/contact-lenses.arff";
	// private String unlabeledDataArffPath =
	// "./file/nlp_weka/classifying_arff/contact-lenses.arff";

	private String modelPath = "./file/nlp_weka/model/KnnClassifier.model";
	// private String modelPath = "./file/nlp_weka/model/libSVMClassifier.model";
	// private String modelPath = "./file/nlp_weka/model/naiveBayesClassifier.model";

	@Before
	public void loadUseClassiferModel() {

		WekaClassifyingInstances wekaClassifyingInstances = new WekaClassifyingInstances();

		setClassifyingInstances(wekaClassifyingInstances);
	}

	@Test
	public void testCreateArffSource() {
		this.classifyingInstances.createArffSource(segmentSourceDir,
				segmentTargetDir, dataRawPath, dataFilterPath);
	}

	@Test
	public void testClassifyingInstancesWithArff() {
		this.classifyingInstances.classifyingInstancesWithArff(
				unlabeledDataArffPath, labeledDataArffPath, modelPath);
	}

	@Test
	public void testClassifyingInstancesWithoutArff() {
		this.classifyingInstances.classifyingInstancesWithoutArff(
				segmentSourceDir, segmentTargetDir, dataRawPath,
				dataFilterPath, unlabeledDataArffPath, labeledDataArffPath,
				modelPath);
	}

	public WekaClassifyingInstances getClassifyingInstances() {
		return classifyingInstances;
	}

	public void setClassifyingInstances(
			WekaClassifyingInstances classifyingInstances) {
		this.classifyingInstances = classifyingInstances;
	}

}
