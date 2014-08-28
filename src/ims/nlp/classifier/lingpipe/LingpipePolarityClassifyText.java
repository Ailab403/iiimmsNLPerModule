package ims.nlp.classifier.lingpipe;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aliasi.classify.JointClassification;
import com.aliasi.classify.LMClassifier;
import com.aliasi.util.AbstractExternalizable;
import com.aliasi.util.Files;

public class LingpipePolarityClassifyText {

	private String modelDiskPath;
	private String polarityTestFileDir;

	public LingpipePolarityClassifyText(String modelDiskPath,
			String polarityTestFileDir) {
		super();
		this.modelDiskPath = modelDiskPath;
		this.polarityTestFileDir = polarityTestFileDir;
	}

	/**
	 * �Ӵ��������뼫�Է�����
	 * 
	 * @return
	 */
	public LMClassifier loadPolarityClassifier() {
		try {
			File classifierFile = new File(this.modelDiskPath);

			LMClassifier polarityClassifier = (LMClassifier) AbstractExternalizable
					.readObject(classifierFile);

			return polarityClassifier;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return null;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return null;
		}
	}

	public List<Map<String, Object>> polarityClassifyText() {

		// ׼����������
		List<Map<String, Object>> polarityClassifyResMaps = new ArrayList<Map<String, Object>>();

		// TODO delete print
		System.out.println("�������뼫�Է���ģ�ͣ����Ե�...");

		LMClassifier polarityClassifier = this.loadPolarityClassifier();
		if (polarityClassifier == null) {
			System.err.println("δ�ܳɹ����ؼ��Է�����");
			return null;
		}

		try {
			File testFileDir = new File(this.polarityTestFileDir);
			for (File testFile : testFileDir.listFiles()) {
				Map<String, Object> polarityResMap = new HashMap<String, Object>();

				String testContent = Files.readFromFile(testFile, "UTF-8");

				JointClassification classification = polarityClassifier
						.classify(testContent);

				// �ó���ѷ���Ľ��
				String bestCategory = classification.bestCategory();
				double polarityScore = classification
						.conditionalProbability("pos")
						- classification.conditionalProbability("neg");
				// ֻȡС����ǰ��λ
				DecimalFormat df = new DecimalFormat("#.00");
				polarityScore = Double.valueOf(df.format(polarityScore));

				// TODO delete print
				System.out.println("�ļ�����" + testFile.getName() + "->��ѷ��ࣺ"
						+ bestCategory + "\n����÷֣�\n" + polarityScore
						+ "\n����ϸ�ڣ�\n" + classification.toString());

				polarityResMap.put("fileName", testFile.getName());
				polarityResMap.put("polarityScore", polarityScore);
				polarityClassifyResMaps.add(polarityResMap);
			}

			return polarityClassifyResMaps;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return null;
		}
	}

	public String getModelDiskPath() {
		return modelDiskPath;
	}

	public void setModelDiskPath(String modelDiskPath) {
		this.modelDiskPath = modelDiskPath;
	}

	public String getPolarityTestFileDir() {
		return polarityTestFileDir;
	}

	public void setPolarityTestFileDir(String polarityTestFileDir) {
		this.polarityTestFileDir = polarityTestFileDir;
	}

}
