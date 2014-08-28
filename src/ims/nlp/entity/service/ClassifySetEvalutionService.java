package ims.nlp.entity.service;

import ims.nlp.entity.model.ClassifySetEvalution;

import java.util.List;

public interface ClassifySetEvalutionService {

	public void add(ClassifySetEvalution classifySetEvalution);

	public void deleteById(int evalutionId);

	public void update(ClassifySetEvalution classifySetEvalution);

	public ClassifySetEvalution loadById(int evalutionId);

	public List<ClassifySetEvalution> listAll();

}
