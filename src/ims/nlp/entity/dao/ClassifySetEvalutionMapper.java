package ims.nlp.entity.dao;

import java.util.List;

import ims.nlp.entity.model.ClassifySetEvalution;

public interface ClassifySetEvalutionMapper {

	void add(ClassifySetEvalution classifySetEvalution);

	void deleteById(int evalutionId);

	void update(ClassifySetEvalution classifySetEvalution);

	ClassifySetEvalution loadById(int evalutionId);

	List<ClassifySetEvalution> listAll();

}
