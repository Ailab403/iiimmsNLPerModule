package ims.nlp.entity.service;

import ims.nlp.entity.model.Analyzer;

import java.util.List;

public interface AnalyzerService {

	public void add(Analyzer analyzer);

	public void deleteById(int analyzerId);

	public void update(Analyzer analyzer);

	public Analyzer loadById(int analyzerId);

	public List<Analyzer> listAll();
}
