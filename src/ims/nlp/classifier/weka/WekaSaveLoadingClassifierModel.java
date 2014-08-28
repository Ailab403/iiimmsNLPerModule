package ims.nlp.classifier.weka;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Method;

import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.SerializationHelper;

/**
 * �洢����������ϵķ���ģ��;weka_step:03
 * 
 * @author superhy
 * 
 */
public class WekaSaveLoadingClassifierModel {

	/**
	 * ���л�ģ��
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
			System.out.println("���ڽ�������ģ��");

			// �½�����ģ��
			Class<?> classModel = Class
					.forName("ims.nlp.classifier.weka.model." + modelObj);
			Method methodProduce = classModel.getMethod("produceModel",
					String.class);
			Classifier classifier = (Classifier) methodProduce.invoke(
					classModel.newInstance(), modelParame);

			Instances instances = new Instances(new BufferedReader(
					new FileReader(dataArffPath)));
			// PS:�ֶ����ɵ�arff�ļ�������ڵ�һ�У��±�Ϊ0
			instances.setClassIndex(0);

			// ����һ��������
			classifier.buildClassifier(instances);

			// ���л�д�������
			SerializationHelper.write(modelPath, classifier);

			// TODO delete print
			System.out.println("��������ģ�ͳɹ�");

		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
		}
	}

	/**
	 * ���뱾�ش����ϵ�ģ��
	 * 
	 * @param modelPath
	 * @return
	 */
	public synchronized static Classifier deserializingModel(String modelPath) {
		try {

			// TODO delete print
			System.out.println("���ڼ��ط���ģ��");

			Classifier classifier = (Classifier) SerializationHelper
					.read(modelPath);

			// TODO delete print
			System.out.println("���ط���ģ�ͳɹ�");

			// ���ؼ��ڵó���classifier
			return classifier;
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();

			return null;
		}
	}
}
