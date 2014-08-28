package ims.nlp.entity.service;

import ims.nlp.entity.dao.AnalyzerMapper;
import ims.nlp.entity.model.Analyzer;

import java.util.List;

public class AnalyzerServiceImpl implements AnalyzerService {

	private AnalyzerMapper analyzerMapper;

	public void add(Analyzer analyzer) {
		this.analyzerMapper.add(analyzer);
	}

	public void deleteById(int analyzerId) {
		this.analyzerMapper.deleteById(analyzerId);
	}

	public List<Analyzer> listAll() {
		return this.analyzerMapper.listAll();
	}

	public Analyzer loadById(int analyzerId) {
		return this.analyzerMapper.loadById(analyzerId);
	}

	public void update(Analyzer analyzer) {
		this.analyzerMapper.update(analyzer);
	}

	public AnalyzerMapper getAnalyzerMapper() {
		return analyzerMapper;
	}

	public void setAnalyzerMapper(AnalyzerMapper analyzerMapper) {
		this.analyzerMapper = analyzerMapper;
	}

}
