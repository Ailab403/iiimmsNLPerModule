package ims.nlp.classifier.weka.model;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;

/**
 * 
 * @author superhy
 * 
 */
public class NaiveBayesClassifierModel {

	public Classifier produceModel(String modelParame) {

		NaiveBayes naiveBayesClassifier = new NaiveBayes();

		return naiveBayesClassifier;
	}
}
