package ims.nlp.entity.service;

import ims.nlp.entity.model.CorpusText;

import java.util.List;
import java.util.Set;

public interface CorpusTextService {

	public void add(CorpusText corpusText);

	public void deleteById(int textId);

	public void deleteBySetId(int setId);

	public void deleteByTasklogId(int taskLogId);

	public void deleteBySiteId(int siteId);

	public void deleteByNodeId(String nodeId);

	public void update(CorpusText corpusText);

	public CorpusText loadById(int textId);

	public List<CorpusText> listByNodeId(String nodeId);
	
	public List<CorpusText> listByInTestFolder(int inTestFolder);

	public Set<CorpusText> listByTaskLogId(int taskLogId);

	public Set<CorpusText> listBySiteId(int siteId);

	public Set<CorpusText> listAll();
	
	public List<CorpusText> listByBlongSetId(int blongSetId);
	
	public CorpusText loadByFileNameInAllNeg(String fileName);

	public CorpusText loadByFileNameInAll(String fileName);
}
