package ims.nlp.classifier.weka;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Method;

import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.SerializationHelper;

/**
 * 存储和载入磁盘上的分类模型;weka_step:03
 * 
 * @author superhy
 * 
 */
public class WekaSaveLoadingClassifierModel {

	/**
	 * 序列化模型
	 * 
	 * @param dataArffPath
	 * @param modelObj
	 * @param modelPath
	 * @param modelParame
	 */
	public synchronized static void sericalizingModel(String dataArffPath,
			String modelObj, String modelPath, String modelParame) {
		try {

			// TODO delete print
			System.out.println("正在建立分类模型");

			// 新建分类模型
			Class<?> classModel = Class
					.forName("ims.nlp.classifier.weka.model." + modelObj);
			Method methodProduce = classModel.getMethod("produceModel",
					String.class);
			Classifier classifier = (Classifier) methodProduce.invoke(
					classModel.newInstance(), modelParame);

			Instances instances = new Instances(new BufferedReader(
					new FileReader(dataArffPath)));
			// PS:手动生成的arff文件类别是在第一行，下标为0
			instances.setClassIndex(0);

			// 建立一个分类器
			classifier.buildClassifier(instances);

			// 序列化写入分类器
			SerializationHelper.write(modelPath, classifier);

			// TODO delete print
			System.out.println("建立分类模型成功");

		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
		}
	}

	/**
	 * 载入本地磁盘上的模型
	 * 
	 * @param modelPath
	 * @return
	 */
	public synchronized static Classifier deserializingModel(String modelPath) {
		try {

			// TODO delete print
			System.out.println("正在加载分类模型");

			Classifier classifier = (Classifier) SerializationHelper
					.read(modelPath);

			// TODO delete print
			System.out.println("加载分类模型成功");

			// 返回加在得出的classifier
			return classifier;
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();

			return null;
		}
	}
}
