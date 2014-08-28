package ims.nlp.classifier.weka;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import weka.core.Instances;
import weka.core.stemmers.NullStemmer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToNominal;
import weka.filters.unsupervised.attribute.StringToWordVector;

/**
 * 制作Weka专门的解析文件arff;weka_step:02
 * 
 * @author superhy
 * 
 */
public class WekaProduceArffFile {

	/**
	 * @param analyzedContentPath
	 * @param dataRawPath
	 * @param dataFilterPath
	 */
	public void produceArffFile(String analyzedContentPath, String dataRawPath,
			String dataFilterPath) {
		try {

			WekaDiyTextDirectoryLoader loader = new WekaDiyTextDirectoryLoader();
			loader.setDirectory(new File(analyzedContentPath));
			Instances dataBasic = loader.getDataSet();
			// {
			// FileWriter fw = new FileWriter(dataRawPath);
			// BufferedWriter bw = new BufferedWriter(fw);
			//
			// bw.write(dataBasic.toString());
			//
			// bw.close();
			// fw.close();
			// }

			StringToNominal stringToNominal = new StringToNominal();
			stringToNominal.setAttributeRange("last");
			stringToNominal.setInputFormat(dataBasic);
			Instances dataRaw = Filter.useFilter(dataBasic, stringToNominal);
			{
				FileWriter fw = new FileWriter(dataRawPath);
				BufferedWriter bw = new BufferedWriter(fw);

				bw.write(dataRaw.toString());

				bw.close();
				fw.close();
			}

			StringToWordVector stringToWordVector = new StringToWordVector();
			stringToWordVector.setStemmer(new NullStemmer());
			stringToWordVector.setInputFormat(dataBasic);
			Instances dataFilter = Filter.useFilter(dataBasic,
					stringToWordVector);
			{
				FileWriter fw = new FileWriter(dataFilterPath);
				BufferedWriter bw = new BufferedWriter(fw);

				bw.write(dataFilter.toString());

				bw.close();
				fw.close();
			}

			// PS:手动生成的arff文件类别是在第一行，下标为0
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
