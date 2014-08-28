package ims.nlp.entity.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;

import ims.crawlerLog.model.SiteLog;
import ims.nlp.cache.NlpDiyWordsPath;
import ims.nlp.entity.dao.ClassicTextSetMapper;
import ims.nlp.entity.model.ClassicTextSet;
import ims.site.model.Site;
import ims.subjectTree.model.Menu;

public class ClassicTextSetServiceImpl implements ClassicTextSetService {

	private ClassicTextSetMapper classicTextSetMapper;

	public void add(ClassicTextSet classicTextSet) {
		this.classicTextSetMapper.add(classicTextSet);
	}

	public void deleteById(int setId) {
		this.classicTextSetMapper.deleteById(setId);
	}

	public List<ClassicTextSet> listAll() {
		return this.classicTextSetMapper.listAll();
	}

	public ClassicTextSet loadById(int setId) {
		return this.classicTextSetMapper.loadById(setId);
	}

	public ClassicTextSet loadByNickName(String nickName) {
		return this.classicTextSetMapper.loadByNickName(nickName);
	}

	public void update(ClassicTextSet classicTextSet) {
		this.classicTextSetMapper.update(classicTextSet);
	}
	
	public List<ClassicTextSet> listAllSetNeg() {
		return this.classicTextSetMapper.listAllSetNeg();
	}

	// 形成ClassicTextSet的树
	public String formClassicTextSetTree() {
		// setId setName放在该menus里
		List<Menu> menus = new ArrayList<Menu>();
		List<ClassicTextSet> classicTextSets = this.classicTextSetMapper
				.listAll();
		for (ClassicTextSet cts : classicTextSets) {
			int setId = cts.getSetId();
			String setName = cts.getSetName();
			menus.add(new Menu(setId, setName, true, null, null, null));
		}
		JSONArray jsonObject = JSONArray.fromObject(menus);
		String allClassicTextTree = jsonObject.toString();

		return allClassicTextTree;
	}

	// 根据文件路径读取文件内容
	public String readFileInfo(String filePath) {
		StringBuffer sb = new StringBuffer();
		try {
			File file = new File(filePath);
			/*
			 * if(!file.exists()||file.isDirectory()){ return "no"; }
			 */
			BufferedReader br = new BufferedReader(new FileReader(file));
			String temp = null;
			temp = br.readLine();
			while (temp != null) {
				sb.append(temp + " ");
				temp = br.readLine();
			}
			// System.out.println(sb.toString());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public ClassicTextSetMapper getClassicTextSetMapper() {
		return classicTextSetMapper;
	}

	public void setClassicTextSetMapper(
			ClassicTextSetMapper classicTextSetMapper) {
		this.classicTextSetMapper = classicTextSetMapper;
	}

}
