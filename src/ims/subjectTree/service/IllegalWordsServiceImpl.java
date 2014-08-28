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

	// 将关键词列表加工(去空格、去重)后形成IllegalWords列表
	public List<IllegalWords> formIllegalWordsList(int illegalWordTreeId,
			String[] keyList) throws Exception {
		if (keyList == null || keyList.length == 0)
			return null;
		List<IllegalWords> illegalWordsList = null;
		HashSet<String> illegalWordsSet = new HashSet<String>();
		for (int i = 0; i < keyList.length; i++) {
					illegalWordsSet.add(del_space(keyList[i])); // 去空格后加入集合(去重)
			}

		if (illegalWordsSet != null && !illegalWordsSet.isEmpty()) {
			illegalWordsList = new ArrayList<IllegalWords>();
			Iterator<String> itr = illegalWordsSet.iterator();
			while (itr.hasNext()) {
				String illegalWordCnt = itr.next();
				if (illegalWordCnt != null)
					illegalWordsList.add(new IllegalWords(0, illegalWordTreeId,
							illegalWordCnt, time)); // 创建对象并加入列表
			}
		}
		return illegalWordsList;
	}

	// 删除字符串中的空格
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

	// 通过敏感词树的id得到敏感词
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
