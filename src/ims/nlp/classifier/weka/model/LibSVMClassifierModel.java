package ims.nlp.classifier.weka.model;

import weka.classifiers.Classifier;
import weka.core.Utils;
import wlsvm.WLSVM;

/**
 * 
 * @author superhy
 * 
 */
public class LibSVMClassifierModel {

	public Classifier produceModel(String modelParame) {

		WLSVM libSVMClassifier = new WLSVM();

		try {
			String[] options = Utils
					.splitOptions("-S 0 -K 2 -D 3 -G 0.0 -R 0.0 -N 0.5 -M 40.0 -C 1.0 -E 0.0010 -P 0.1 -B 0");
			libSVMClassifier.setOptions(options);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return libSVMClassifier;
	}
}
