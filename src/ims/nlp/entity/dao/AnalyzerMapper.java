package ims.nlp.entity.dao;

import ims.nlp.entity.model.Analyzer;

import java.util.List;

public interface AnalyzerMapper {

	void add(Analyzer analyzer);

	void deleteById(int analyzerId);

	void update(Analyzer analyzer);

	Analyzer loadById(int analyzerId);

	List<Analyzer> listAll();
}
