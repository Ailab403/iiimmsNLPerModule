package ims.nlp.lucene.analyzer.util;

import ims.nlp.cache.AnalyzerDataPath;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;

public class LoadOutWordContext {

	public static Set<String> wordAllSet = new HashSet<String>();
	public static Set<String> wordMySet = new HashSet<String>();

	/**
	 * �����ⲿ�����ļ�
	 * 
	 * @param wordFile
	 * @return
	 */
	public static boolean loadWordFile(File wordFile) {

		try {
			/*
			 * ����ԭ�дʿ��еĴ�
			 */
			BufferedReader brWord = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(
							AnalyzerDataPath.MMSEG_DIC_PATH + "\\words.dic")),
					"UTF-8"));
			BufferedReader brWordMy = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(new File(
									AnalyzerDataPath.MMSEG_DIC_PATH
											+ "\\words-my.dic")), "UTF-8"));

			// ԭ�е��ʻ����Ϊ��
			if (wordAllSet.size() == 0) {

				String line = brWord.readLine();
				while (line != null) {
					System.out.println("���ش�����" + line);

					wordAllSet.add(line);
					line = brWord.readLine();
				}

				line = brWordMy.readLine();
				while (line != null) {
					System.out.println("����my������" + line);

					wordMySet.add(line);
					line = brWordMy.readLine();
				}

				wordAllSet.addAll(wordMySet);
			}

			BufferedReader brWordOut = new BufferedReader(new FileReader(
					wordFile));

			String line = brWordOut.readLine();
			while (line != null) {
				System.out.println("���ⲿ���������" + line);

				if (!wordAllSet.contains(line)) {
					wordMySet.add(line);
				}
				line = brWordOut.readLine();
			}

			BufferedWriter bwWordMy = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(new File(
									AnalyzerDataPath.MMSEG_DIC_PATH
											+ "\\words-my.dic")), "UTF-8"));
			for (String word : wordMySet) {
				bwWordMy.write(word + "\r\n");
			}

			bwWordMy.close();
			brWordOut.close();
			brWordMy.close();
			brWord.close();

			return true;
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();

			return false;
		}
	}

	/**
	 * �����ⲿ�����ļ�
	 * 
	 * @param wordAllSet
	 * @return
	 */
	public static boolean loadWordFile(Set<String> wordOutSet) {

		try {
			/*
			 * ����ԭ�дʿ��еĴ�
			 */
			BufferedReader brWord = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(
							AnalyzerDataPath.MMSEG_DIC_PATH + "\\words.dic")),
					"UTF-8"));
			BufferedReader brWordMy = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(new File(
									AnalyzerDataPath.MMSEG_DIC_PATH
											+ "\\words-my.dic")), "UTF-8"));

			// ԭ�е��ʻ����Ϊ��
			if (wordAllSet.size() == 0) {

				String line = brWord.readLine();
				while (line != null) {
					System.out.println("���ش�����" + line);

					wordAllSet.add(line);
					line = brWord.readLine();
				}

				line = brWordMy.readLine();
				while (line != null) {
					System.out.println("����my������" + line);

					wordMySet.add(line);
					line = brWordMy.readLine();
				}

				wordAllSet.addAll(wordMySet);
			}

			for (String line : wordOutSet) {
				System.out.println("���ⲿ���������" + line);
				if (!wordAllSet.contains(line)) {
					wordMySet.add(line);
				}
			}

			BufferedWriter bwWordMy = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(new File(
									AnalyzerDataPath.MMSEG_DIC_PATH
											+ "\\words-my.dic")), "UTF-8"));
			for (String word : wordMySet) {
				bwWordMy.write(word + "\r\n");
			}

			bwWordMy.close();
			brWordMy.close();
			brWord.close();

			return true;
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();

			return false;
		}
	}
}
