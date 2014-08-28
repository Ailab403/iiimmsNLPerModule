package ims.nlp.entity.dao;

import java.util.List;

import ims.nlp.entity.model.ClassifierModel;

public interface ClassifierModelMapper {

	void add(ClassifierModel classifierModel);

	void deleteById(int modelId);

	void update(ClassifierModel classifierModel);

	ClassifierModel loadById(int modelId);

	List<ClassifierModel> listAll();

	List<ClassifierModel> listByActiveState();

	List<ClassifierModel> listByActiveStateNeg();

}
