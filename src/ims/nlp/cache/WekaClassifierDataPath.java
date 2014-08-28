package ims.nlp.cache;

public class WekaClassifierDataPath {

	// 训练和测试的文本分词源文档和目标文档存放目录(用于分词)
	public static String TRAIN_SEGMENT_SOURCEDIR = ".\\file\\nlp_train_content";
	public static String TRAIN_SEGMENT_TARGETDIR = ".\\file\\nlp_weka\\analyzed_train_content";
	public static String TEST_SEGMENT_SOURCEDIR = ".\\file\\nlp_classifier_test";
	public static String TEST_FILE_SOURCEFOLDER = ".\\file\\nlp_classifier_test\\unlabeled";
	public static String TEST_SEGMENT_TARGETDIR = ".\\file\\nlp_weka\\analyzed_classifying_content";

	// 训练和测试所生成的各种数据集存放的路径(用于训练和分类时生成数据的存放)
	public static String TRAIN_DATARAW_PATH = ".\\file\\nlp_weka\\train_arff\\dataRaw.arff";
	public static String TRAIN_DATAFILTER_PATH = ".\\file\\nlp_weka\\train_arff\\dataFilter.arff";
	public static String TEST_DATARAW_PATH = ".\\file\\nlp_weka\\classifying_arff\\dataRaw.arff";
	public static String TEST_DATAFILTER_PATH = ".\\file\\nlp_weka\\classifying_arff\\dataFilter.arff";

	// 训练和测试所生成的各种数据集存放的目录(用于分类时加载数据集)
	public static String TRAIN_DATADIR_PATH = ".\\file\\nlp_weka\\train_arff\\";
	public static String TEST_DATADIR_PATH = ".\\file\\nlp_weka\\classifying_arff\\";

}
