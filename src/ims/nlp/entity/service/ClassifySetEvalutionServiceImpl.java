package ims.nlp.entity.service;

import ims.nlp.entity.dao.ClassifySetEvalutionMapper;
import ims.nlp.entity.model.ClassifySetEvalution;

import java.util.List;

public class ClassifySetEvalutionServiceImpl implements
		ClassifySetEvalutionService {

	private ClassifySetEvalutionMapper classifySetEvalutionMapper;

	public void add(ClassifySetEvalution classifySetEvalution) {
		this.classifySetEvalutionMapper.add(classifySetEvalution);
	}

	public void deleteById(int evalutionId) {
		this.classifySetEvalutionMapper.deleteById(evalutionId);
	}

	public List<ClassifySetEvalution> listAll() {
		return this.classifySetEvalutionMapper.listAll();
	}

	public ClassifySetEvalution loadById(int evalutionId) {
		return this.classifySetEvalutionMapper.loadById(evalutionId);
	}

	public void update(ClassifySetEvalution classifySetEvalution) {
		this.classifySetEvalutionMapper.update(classifySetEvalution);
	}

	public ClassifySetEvalutionMapper getClassifySetEvalutionMapper() {
		return classifySetEvalutionMapper;
	}

	public void setClassifySetEvalutionMapper(
			ClassifySetEvalutionMapper classifySetEvalutionMapper) {
		this.classifySetEvalutionMapper = classifySetEvalutionMapper;
	}

}
