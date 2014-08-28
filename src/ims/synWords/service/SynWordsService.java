package ims.synWords.service;

import ims.synWords.model.SynWords;

import java.util.List;

public interface SynWordsService {

	public List<SynWords> listAll();            // 展示所有同义词
	 
	public String add(SynWords synWords);       // 添加同义词

	public String deleteById(int synWordId);    // 删除同义词

	public String update(SynWords synWords);    // 编辑同义词

	public SynWords loadById(int synWordId);    // 查找同义词
}
