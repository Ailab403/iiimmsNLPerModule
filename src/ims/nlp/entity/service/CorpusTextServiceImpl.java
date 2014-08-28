package ims.nlp.entity.service;

import java.util.List;
import java.util.Set;

import ims.nlp.entity.dao.CorpusTextMapper;
import ims.nlp.entity.model.CorpusText;

public class CorpusTextServiceImpl implements CorpusTextService {

	private CorpusTextMapper corpusTextMapper;

	public void add(CorpusText corpusText) {
		this.corpusTextMapper.add(corpusText);
	}

	public void deleteById(int textId) {
		this.corpusTextMapper.deleteById(textId);
	}

	public void deleteByNodeId(String nodeId) {
		this.corpusTextMapper.deleteByNodeId(nodeId);
	}

	public void deleteBySetId(int setId) {
		this.corpusTextMapper.deleteBySetId(setId);
	}

	public void deleteBySiteId(int siteId) {
		this.corpusTextMapper.deleteBySiteId(siteId);
	}

	public void deleteByTasklogId(int taskLogId) {
		this.corpusTextMapper.deleteByTasklogId(taskLogId);
	}

	public Set<CorpusText> listAll() {
		return this.corpusTextMapper.listAll();
	}

	public Set<CorpusText> listBySiteId(int siteId) {
		return this.corpusTextMapper.listBySiteId(siteId);
	}

	public Set<CorpusText> listByTaskLogId(int taskLogId) {
		return this.corpusTextMapper.listByTaskLogId(taskLogId);
	}

	public CorpusText loadById(int textId) {
		return this.corpusTextMapper.loadById(textId);
	}

	public List<CorpusText> listByNodeId(String nodeId) {
		return this.corpusTextMapper.listByNodeId(nodeId);
	}

	public List<CorpusText> listByInTestFolder(int inTestFolder) {
		return this.corpusTextMapper.listByInTestFolder(inTestFolder);
	}

	public void update(CorpusText corpusText) {
		this.corpusTextMapper.update(corpusText);
	}

	public List<CorpusText> listByBlongSetId(int blongSetId) {
		return this.corpusTextMapper.listByBlongSetId(blongSetId);
	}

	public CorpusText loadByFileNameInAll(String fileName) {
		return this.corpusTextMapper.loadByFileNameInAll(fileName);
	}

	public CorpusText loadByFileNameInAllNeg(String fileName) {
		return this.corpusTextMapper.loadByFileNameInAllNeg(fileName);
	}

	public CorpusTextMapper getCorpusTextMapper() {
		return corpusTextMapper;
	}

	public void setCorpusTextMapper(CorpusTextMapper corpusTextMapper) {
		this.corpusTextMapper = corpusTextMapper;
	}

}
