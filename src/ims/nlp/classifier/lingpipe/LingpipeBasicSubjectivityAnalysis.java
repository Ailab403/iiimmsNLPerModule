package ims.nlp.classifier.lingpipe;

import ims.nlp.cache.LingpipeAnalysisDataPath;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.aliasi.classify.BaseClassifierEvaluator;
import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;
import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.lm.NGramProcessLM;
import com.aliasi.util.Files;

@Deprecated
public class LingpipeBasicSubjectivityAnalysis {

	private File mPolarityDir;
	private String[] mCategories;
	private DynamicLMClassifier<NGramProcessLM> mClassifier;

	public LingpipeBasicSubjectivityAnalysis(String baseCorpusDir) {
		System.out.println("\nBASIC SUBJECTIVITY DEMO");
		mPolarityDir = new File(baseCorpusDir);
		System.out.println("\nData Directory=" + mPolarityDir);
		mCategories = new String[] { "plot", "quote" };
		int nGram = 8;
		mClassifier = DynamicLMClassifier
				.createNGramProcess(mCategories, nGram);
	}

	public void train() throws IOException {
		int numTrainingChars = 0;
		System.out.println("\nTraining.");
		for (int i = 0; i < mCategories.length; ++i) {
			String category = mCategories[i];
			Classification classification = new Classification(category);
			File file = new File(mPolarityDir, mCategories[i] + ".tok.gt9.5000");
			String data = Files.readFromFile(file, "ISO-8859-1");
			String[] sentences = data.split("\n");
			System.out.println("# Sentences " + category + "="
					+ sentences.length);
			int numTraining = (sentences.length * 9) / 10;
			for (int j = 0; j < numTraining; ++j) {
				String sentence = sentences[j];
				numTrainingChars += sentence.length();
				Classified<CharSequence> classified = new Classified<CharSequence>(
						sentence, classification);
				mClassifier.handle(classified);
			}
		}

		System.out.println("\nCompiling.\n  Model file=subjectivity.model");
		FileOutputStream fileOut = new FileOutputStream(
				LingpipeAnalysisDataPath.LINGPIPE_SUBJECTIVITYCLASSIFIER_MODEL_PATH);
		ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
		mClassifier.compileTo(objOut);
		objOut.close();

		System.out.println("  # Training Cases=" + 9000);
		System.out.println("  # Training Chars=" + numTrainingChars);
	}

	public void evaluate() throws IOException {
		// classifier hasn't been compiled, so it'll be slower
		boolean storeInputs = false;

		// JointClassifierEvaluator<CharSequence> evaluator = new
		// JointClassifierEvaluator<CharSequence>(
		// mClassifier, mCategories, storeInputs);

		BaseClassifierEvaluator<CharSequence> evaluator = new BaseClassifierEvaluator<CharSequence>(
				mClassifier, mCategories, storeInputs);
		System.out.println("\nEvaluating.");
		for (int i = 0; i < mCategories.length; ++i) {
			String category = mCategories[i];
			Classification classification = new Classification(category);
			File file = new File(mPolarityDir, mCategories[i] + ".tok.gt9.5000");
			String data = Files.readFromFile(file, "ISO-8859-1");
			String[] sentences = data.split("\n");
			int numTraining = (sentences.length * 9) / 10;
			for (int j = numTraining; j < sentences.length; ++j) {
				Classified<CharSequence> classified = new Classified<CharSequence>(
						sentences[j], classification);
				evaluator.handle(classified);
			}
		}
		System.out.println();
		System.out.println(evaluator.toString());
	}

	public void test() throws Exception {

		System.out.println("\nTesting.");

		for (int i = 0; i < mCategories.length; i++) {
			File file = new File(mPolarityDir, mCategories[i] + ".tok.gt9.5000");
			String data = Files.readFromFile(file, "ISO-8859-1");
			String[] sentences = data.split("\n");
			int numTraining = (sentences.length * 99) / 100;
			for (int j = numTraining; j < sentences.length; j++) {
				Classification classification = mClassifier
						.classify(sentences[j]);

				String resultCategory = classification.bestCategory();

				// TODO delete print
				System.out.println("²âÊÔ½á¹û£º" + j + "->" + resultCategory + "\n"
						+ classification.toString());
			}
		}

	}
}
