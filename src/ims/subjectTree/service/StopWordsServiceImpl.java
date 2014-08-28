package ims.subjectTree.service;

import ims.subjectTree.dao.StopWordsMapper;
import ims.subjectTree.model.StopWords;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StopWordsServiceImpl implements StopWordsService {

	private StopWordsMapper stopWordsMapper;
	private Timestamp time =Timestamp.valueOf(new SimpleDateFormat(
	"yyyy-MM-dd HH:mm:ss").format(new Date()));

	public void addKeyWords(StopWords stopWords) {
		this.stopWordsMapper.insertStopWords(stopWords);
		
	}
	public List<StopWords> loadAllStopWords() {
		return this.stopWordsMapper.loadAllStopWords();
	}

	// ���ؼ����б�ӹ�(ȥ�ո�ȥ��)���γ�IllegalWords�б�
	public List<StopWords> formIllegalWordsList(int stoplWordTreeId , String[] keyList) throws Exception {
		if (keyList == null || keyList.length == 0) return null;
		List<StopWords> stopWordsList = null;
		HashSet<String> stopWordsSet = new HashSet<String>();
		for (int i=0; i<keyList.length; i++)	
			stopWordsSet.add(del_space(keyList[i]));	// ȥ�ո����뼯��(ȥ��)
		if (stopWordsSet != null && !stopWordsSet.isEmpty()) {
			stopWordsList = new ArrayList<StopWords>();
			Iterator<String> itr = stopWordsSet.iterator();
			while (itr.hasNext()){
				String stopWordCnt = itr.next();
				if (stopWordCnt!= null)
					stopWordsList.add(new StopWords(0,stoplWordTreeId, stopWordCnt, time));	// �������󲢼����б�
			}
		}
		return stopWordsList;
	}
	
	public boolean delKeyWordsByBackId(int backNodeId) {
		// TODO Auto-generated method stub
		return false;
	}

	// ɾ���ַ����еĿո�
	public String del_space(String str) {
		if (str == null || str.equalsIgnoreCase("")) {
			return null;
		}
		char[] str_old = str.toCharArray();
		StringBuffer str_new = new StringBuffer();
		int i = 0;
		for (char a : str_old) {
			if (a != ' ') {
				str_new.append(a);
				i++;
			}
		}
		return str_new.toString();
	}

	//ͨ�����д�����id�õ����д�
	public List<StopWords> findStopWordsByStopWordTreeId(int stoplWordTreeId) {
		return this.stopWordsMapper.loadStopWordsByStopWordTreeId(stoplWordTreeId);
	}
	public StopWordsMapper getStopWordsMapper() {
		return stopWordsMapper;
	}
	public void setStopWordsMapper(StopWordsMapper stopWordsMapper) {
		this.stopWordsMapper = stopWordsMapper;
	}

	
}
