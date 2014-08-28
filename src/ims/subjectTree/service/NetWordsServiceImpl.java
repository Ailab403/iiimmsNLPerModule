package ims.subjectTree.service;

import ims.subjectTree.dao.NetWordsMapper;
import ims.subjectTree.model.NetWords;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NetWordsServiceImpl implements NetWordsService {
	
	private NetWordsMapper netWordsMapper;
	private Timestamp time =Timestamp.valueOf(new SimpleDateFormat(
	"yyyy-MM-dd HH:mm:ss").format(new Date()));

	public void addNetWords(NetWords netWords) {
		this.netWordsMapper.insertNetWords(netWords);
		
	}
	// 将关键词列表加工(去空格、去重)后形成NetWords列表
	public List<NetWords> formNetWordsList(int netWordTreeId , String[] keyList) throws Exception {
		if (keyList == null || keyList.length == 0) return null;
		List<NetWords> netWordsList = null;
		HashSet<String> netWordsSet = new HashSet<String>();
		for (int i=0; i<keyList.length; i++)	
			netWordsSet.add(del_space(keyList[i]));	// 去空格后加入集合(去重)
		if (netWordsSet != null && !netWordsSet.isEmpty()) {
			netWordsList = new ArrayList<NetWords>();
			Iterator<String> itr = netWordsSet.iterator();
			while (itr.hasNext()){
				String netWordCnt = itr.next();
				if (netWordCnt!= null)
					netWordsList.add(new NetWords(0,netWordTreeId, netWordCnt, time));	// 创建对象并加入列表
			}
		}
		return netWordsList;
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

	//通过敏感词树的id得到敏感词
	public List<NetWords> findNetWordsByNetWordTreeId(int netWordTreeId) {
		return this.netWordsMapper.loadNetWordsByNetWordTreeId(netWordTreeId);
	}
	public NetWordsMapper getNetWordsMapper() {
		return netWordsMapper;
	}
	public void setNetWordsMapper(NetWordsMapper netWordsMapper) {
		this.netWordsMapper = netWordsMapper;
	}

}
