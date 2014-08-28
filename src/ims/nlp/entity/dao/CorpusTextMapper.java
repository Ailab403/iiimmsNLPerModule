package ims.nlp.entity.dao;

import java.util.List;
import java.util.Set;

import ims.nlp.entity.model.CorpusText;

public interface CorpusTextMapper {

	void add(CorpusText corpusText);

	void deleteById(int textId);

	void deleteBySetId(int setId);

	void deleteByTasklogId(int taskLogId);

	void deleteBySiteId(int siteId);

	void deleteByNodeId(String nodeId);

	void update(CorpusText corpusText);

	CorpusText loadById(int textId);

	List<CorpusText> listByNodeId(String nodeId);
	
	List<CorpusText> listByInTestFolder(int inTestFolder);

	Set<CorpusText> listByTaskLogId(int taskLogId);

	Set<CorpusText> listBySiteId(int siteId);

	Set<CorpusText> listAll();

	List<CorpusText> listByBlongSetId(int blongSetId);

	CorpusText loadByFileNameInAllNeg(String fileName);

	CorpusText loadByFileNameInAll(String fileName);

}
