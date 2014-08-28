package ims.nlp.entity.service;

import java.util.List;

import ims.nlp.entity.dao.IndexMapper;
import ims.nlp.entity.model.Index;

public class IndexServiceImpl implements IndexService {

	private IndexMapper indexMapper;

	public void add(Index index) {
		this.indexMapper.add(index);
	}

	public void deleteById(int indexId) {
		this.indexMapper.deleteById(indexId);
	}

	public void deleteBySetId(int setId) {
		this.indexMapper.deleteBySetId(setId);
	}

	public List<Index> listAll() {
		return this.indexMapper.listAll();
	}
	
	public List<Index> listAllIdNeg(int indexId) {
		return this.indexMapper.listAllIdNeg(indexId);
	}

	public Index loadById(int indexId) {
		return this.indexMapper.loadById(indexId);
	}

	public Index loadBySetId(int setId) {
		return this.indexMapper.loadBySetId(setId);
	}

	public void update(Index index) {
		this.indexMapper.update(index);
	}

	public Index loadByIndexName(String indexName) {
		return this.indexMapper.loadByIndexName(indexName);
	}

	public IndexMapper getIndexMapper() {
		return indexMapper;
	}

	public void setIndexMapper(IndexMapper indexMapper) {
		this.indexMapper = indexMapper;
	}

	

}
