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
		// 获取语料集
		this.pDir = new File(baseCorpusDir);
		// 获取类别
		this.categories = pDir.list();
		int nGram = 8;
		// 新建动态分类器
		this.classifier = DynamicLMClassifier.createNGramProcess(
				this.categories, nGram);
	}

	public void train() {
		try {
			for (int i = 0; i < this.categories.length; ++i) {
				String category = this.categories[i];
				// 新建类别
				Classification classification = new Classification(category);
				File dir = new File(this.pDir, this.categories[i]);
				File[] trainFiles = dir.listFiles();
				for (int j = 0; j < trainFiles.length; ++j) {
					File trainFile = trainFiles[j];
					if (isTrainingFile(trainFile)) {

						// TODO delete print
						System.out.println("正在训练：" + trainFile.getName());

						// 判断一下是为了让一部分数据作为训练集、一部分作为测试集
						String review = Files.readFromFile(trainFile, "UTF-8");
						// 指定内容和类别
						Classified classified = new Classified(review,
								classification);
						// 训练
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
		// 如果第2位为1就是测试集
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
						System.out.println("测试结果：" + testFile.getName() + "->"
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

		System.out.println("测试总数：" + numTests);
		System.out.println("正确数：" + numCorrect);
		System.out.println("正确率" + ((double) numCorrect) / (double) numTests);
	}

}
