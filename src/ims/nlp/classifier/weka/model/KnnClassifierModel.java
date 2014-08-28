package ims.nlp.classifier.weka.model;

import net.sf.json.JSONObject;
import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.core.SelectedTag;

/**
 * 
 * @author superhy
 * 
 */
public class KnnClassifierModel {

	public Classifier produceModel(String modelParame) {

		// 如果k的值没有设定，那么默认k为5
		int k = 5;
		if (modelParame != null) {
			try {
				k = Integer.valueOf(JSONObject.fromObject(modelParame)
						.getString("k"));

				System.out.println("额外参数设置：k值设置为" + k);
			} catch (Exception e) {
				// TODO: handle exception
				k = 5;
			}
		}

		IBk kNNClassifier = new IBk(k);
		kNNClassifier.setDistanceWeighting(new SelectedTag(
				IBk.WEIGHT_SIMILARITY, IBk.TAGS_WEIGHTING));

		return kNNClassifier;
	}
}
