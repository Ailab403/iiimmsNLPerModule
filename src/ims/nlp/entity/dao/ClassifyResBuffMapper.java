package ims.nlp.entity.dao;

import java.util.List;
import java.util.Map;

import ims.nlp.entity.model.ClassifyResBuff;

public interface ClassifyResBuffMapper {

	void add(ClassifyResBuff classifyResBuff);

	void deleteById(int classifyResId);

	void update(ClassifyResBuff classifyResBuff);

	ClassifyResBuff loadById(int classifyResId);

	List<ClassifyResBuff> listAll();

	List<ClassifyResBuff> listByResLabeledSetIdAndLogId(
			Map<String, Object> setIdAndLogIdMap);

	List<ClassifyResBuff> listByResSetIdAndLogId(
			Map<String, Object> setIdAndLogIdMap);

	List<ClassifyResBuff> listByLabeledSetIdAndLogIdGroupTextId(
			Map<String, Object> setIdAndLogIdMap);
}
