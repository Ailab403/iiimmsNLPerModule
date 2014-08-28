package ims.nlp.entity.service;

import ims.nlp.entity.model.ClassifierModel;

import java.util.List;

public interface ClassifierModelService {

	public void add(ClassifierModel classifierModel);

	public void deleteById(int modelId);

	public void update(ClassifierModel classifierModel);

	public ClassifierModel loadById(int modelId);

	public List<ClassifierModel> listAll();

	public List<ClassifierModel> listByActiveState();

	public List<ClassifierModel> listByActiveStateNeg();
}
