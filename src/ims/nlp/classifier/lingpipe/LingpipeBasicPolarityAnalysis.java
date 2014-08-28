package ims.nlp.classifier.lingpipe;

import java.io.File;
import java.io.IOException;

import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;
import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.lm.NGramProcessLM;
import com.aliasi.util.Files;

/**
 * 
 * @author superhy
 *
 */
@Deprecated
public class LingpipeBasicPolarityAnalysis {

	private File pDir;
	private String[] categories;
	private DynamicLMClassifier<NGramProcessLM> classifier;

	public LingpipeBasicPolarityAnalysis(String baseCorpusDir) {
		// ��ȡ���ϼ�
		this.pDir = new File(baseCorpusDir);
		// ��ȡ���
		this.categories = pDir.list();
		int nGram = 8;
		// �½���̬������
		this.classifier = DynamicLMClassifier.createNGramProcess(
				this.categories, nGram);
	}

	public void train() {
		try {
			for (int i = 0; i < this.categories.length; ++i) {
				String category = this.categories[i];
				// �½����
				Classification classification = new Classification(category);
				File dir = new File(this.pDir, this.categories[i]);
				File[] trainFiles = dir.listFiles();
				for (int j = 0; j < trainFiles.length; ++j) {
					File trainFile = trainFiles[j];
					if (isTrainingFile(trainFile)) {

						// TODO delete print
						System.out.println("����ѵ����" + trainFile.getName());

						// �ж�һ����Ϊ����һ����������Ϊѵ������һ������Ϊ���Լ�
						String review = Files.readFromFile(trainFile, "UTF-8");
						// ָ�����ݺ����
						Classified classified = new Classified(review,
								classification);
						// ѵ��
						this.classifier.handle(classified);
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	boolean isTrainingFile(File file) {
		// �����2λΪ1���ǲ��Լ�
		return file.getName().charAt(2) != '1';
	}

	public void evaluate() {

		int numTests = 0;
		int numCorrect = 0;

		try {
			for (int i = 0; i < this.categories.length; ++i) {
				String category = this.categories[i];
				File file = new File(this.pDir, this.categories[i]);
				File[] testFiles = file.listFiles();
				for (int j = 0; j < testFiles.length; ++j) {
					File testFile = testFiles[j];
					if (!isTrainingFile(testFile)) {
						String review = Files.readFromFile(testFile, "UTF-8");
						++numTests;
						Classification classification = this.classifier
								.classify(review);
						String resultCategory = classification.bestCategory();

						// TODO delete print
						System.out.println("���Խ����" + testFile.getName() + "->"
								+ resultCategory);

						if (resultCategory.equals(category))
							++numCorrect;
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("����������" + numTests);
		System.out.println("��ȷ����" + numCorrect);
		System.out.println("��ȷ��" + ((double) numCorrect) / (double) numTests);
	}

}
