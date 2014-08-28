package ims.subjectTree.service;

import ims.subjectTree.dao.IllegalWordsMapper;
import ims.subjectTree.dao.StopWordsMapper;
import ims.subjectTree.model.IllegalWords;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IllegalWordsServiceImpl implements IllegalWordsService {
	
	private IllegalWordsMapper illegalWordsMapper;
	private StopWordsMapper stopWordsMapper;
	private Timestamp time =Timestamp.valueOf(new SimpleDateFormat(
	"yyyy-MM-dd HH:mm:ss").format(new Date()));

	public void addKeyWords(IllegalWords illegalWords) {
		this.illegalWordsMapper.insertIllegalWords(illegalWords);

	}

	// ���ؼ����б�ӹ�(ȥ�ո�ȥ��)���γ�IllegalWords�б�
	public List<IllegalWords> formIllegalWordsList(int illegalWordTreeId,
			String[] keyList) throws Exception {
		if (keyList == null || keyList.length == 0)
			return null;
		List<IllegalWords> illegalWordsList = null;
		HashSet<String> illegalWordsSet = new HashSet<String>();
		for (int i = 0; i < keyList.length; i++) {
					illegalWordsSet.add(del_space(keyList[i])); // ȥ�ո����뼯��(ȥ��)
			}

		if (illegalWordsSet != null && !illegalWordsSet.isEmpty()) {
			illegalWordsList = new ArrayList<IllegalWords>();
			Iterator<String> itr = illegalWordsSet.iterator();
			while (itr.hasNext()) {
				String illegalWordCnt = itr.next();
				if (illegalWordCnt != null)
					illegalWordsList.add(new IllegalWords(0, illegalWordTreeId,
							illegalWordCnt, time)); // �������󲢼����б�
			}
		}
		return illegalWordsList;
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

	// ͨ�����д�����id�õ����д�
	public List<IllegalWords> findIdTreeWords(int illegalWordTreeId) {
		return this.illegalWordsMapper
				.loadIllegalWordsByIllegalWordTreeId(illegalWordTreeId);
	}

	public IllegalWordsMapper getIllegalWordsMapper() {
		return illegalWordsMapper;
	}

	public void setIllegalWordsMapper(IllegalWordsMapper illegalWordsMapper) {
		this.illegalWordsMapper = illegalWordsMapper;
	}

	public StopWordsMapper getStopWordsMapper() {
		return stopWordsMapper;
	}

	public void setStopWordsMapper(StopWordsMapper stopWordsMapper) {
		this.stopWordsMapper = stopWordsMapper;
	}

}
