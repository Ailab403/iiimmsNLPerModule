package ims.nlp.lucene.analyzer.util;

import ims.nlp.cache.AnalyzerDataPath;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Set;

public class LoadOutWordContext {

	public static Set<String> wordSet = new HashSet<String>();
	public static Set<String> wordMySet = new HashSet<String>();

	/**
	 * 导入外部词条文件
	 * 
	 * @param wordFile
	 * @return
	 */
	public boolean loadWordFile(File wordFile) {

		try {
			/*
			 * 加载原有词库中的词
			 */
			File fileWord = new File(AnalyzerDataPath.MMSEG_DIC_PATH
					+ "\\word.dic");
			File fileWordMy = new File(AnalyzerDataPath.MMSEG_DIC_PATH
					+ "\\word-my.dic");
			BufferedReader br = null;

			// 原有单词缓存库为空
			if (wordSet.size() == 0) {
				FileReader frWord = new FileReader(fileWord);
				FileReader frWordMy = new FileReader(fileWordMy);

				br = new BufferedReader(frWord);
				String line = br.readLine();
				while (line != null) {
					System.out.println("加载词条：" + line);

					wordSet.add(line);
					line = br.readLine();
				}
				br = new BufferedReader(frWordMy);
				line = br.readLine();
				while (line != null) {
					System.out.println("加载my词条：" + line);

					wordMySet.add(line);
					line = br.readLine();
				}

				wordSet.addAll(wordMySet);
			}

			FileReader frWordFile = new FileReader(wordFile);
			br = new BufferedReader(frWordFile);

			String line = br.readLine();
			while (line != null) {
				System.out.println("从外部引入词条：" + line);

				if (!wordSet.contains(line)) {
					wordMySet.add(line);
				}
				line = br.readLine();
			}
			FileWriter fw = new FileWriter(fileWordMy);
			BufferedWriter bw = new BufferedWriter(fw);
			for (String word : wordMySet) {
				bw.write(line + "\r\n");
			}

			return true;
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();

			return false;
		}
	}
}
