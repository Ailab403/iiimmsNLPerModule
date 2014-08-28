package ims.nlp.cache;

public class WekaClassifierDataPath {

	// ѵ���Ͳ��Ե��ı��ִ�Դ�ĵ���Ŀ���ĵ����Ŀ¼(���ڷִ�)
	public static String TRAIN_SEGMENT_SOURCEDIR = ".\\file\\nlp_train_content";
	public static String TRAIN_SEGMENT_TARGETDIR = ".\\file\\nlp_weka\\analyzed_train_content";
	public static String TEST_SEGMENT_SOURCEDIR = ".\\file\\nlp_classifier_test";
	public static String TEST_FILE_SOURCEFOLDER = ".\\file\\nlp_classifier_test\\unlabeled";
	public static String TEST_SEGMENT_TARGETDIR = ".\\file\\nlp_weka\\analyzed_classifying_content";

	// ѵ���Ͳ��������ɵĸ������ݼ���ŵ�·��(����ѵ���ͷ���ʱ�������ݵĴ��)
	public static String TRAIN_DATARAW_PATH = ".\\file\\nlp_weka\\train_arff\\dataRaw.arff";
	public static String TRAIN_DATAFILTER_PATH = ".\\file\\nlp_weka\\train_arff\\dataFilter.arff";
	public static String TEST_DATARAW_PATH = ".\\file\\nlp_weka\\classifying_arff\\dataRaw.arff";
	public static String TEST_DATAFILTER_PATH = ".\\file\\nlp_weka\\classifying_arff\\dataFilter.arff";

	// ѵ���Ͳ��������ɵĸ������ݼ���ŵ�Ŀ¼(���ڷ���ʱ�������ݼ�)
	public static String TRAIN_DATADIR_PATH = ".\\file\\nlp_weka\\train_arff\\";
	public static String TEST_DATADIR_PATH = ".\\file\\nlp_weka\\classifying_arff\\";

}
