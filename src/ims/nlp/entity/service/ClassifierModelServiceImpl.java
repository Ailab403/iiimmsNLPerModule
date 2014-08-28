package ims.nlp.entity.service;

import java.util.List;

import ims.nlp.entity.dao.ClassifierModelMapper;
import ims.nlp.entity.model.ClassifierModel;

public class ClassifierModelServiceImpl implements ClassifierModelService {

	private ClassifierModelMapper classifierModelMapper;

	public void add(ClassifierModel classifierModel) {
		this.classifierModelMapper.add(classifierModel);
	}

	public void deleteById(int modelId) {
		this.classifierModelMapper.deleteById(modelId);
	}

	public List<ClassifierModel> listAll() {
		return this.classifierModelMapper.listAll();
	}

	public ClassifierModel loadById(int modelId) {
		return this.classifierModelMapper.loadById(modelId);
	}

	public void update(ClassifierModel classifierModel) {
		this.classifierModelMapper.update(classifierModel);
	}

	public List<ClassifierModel> listByActiveState() {
		return this.listByActiveState();
	}

	public List<ClassifierModel> listByActiveStateNeg() {
		return this.classifierModelMapper.listByActiveStateNeg();
	}

	public ClassifierModelMapper getClassifierModelMapper() {
		return classifierModelMapper;
	}

	public void setClassifierModelMapper(
			ClassifierModelMapper classifierModelMapper) {
		this.classifierModelMapper = classifierModelMapper;
	}

}
