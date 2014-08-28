package ims.nlp.entity.service;

import ims.nlp.entity.model.ClassifyResBuff;

import java.util.List;
import java.util.Map;

public interface ClassifyResBuffService {

	public void add(ClassifyResBuff classifyResBuff);

	public void deleteById(int classifyResId);

	public void update(ClassifyResBuff classifyResBuff);

	public ClassifyResBuff loadById(int classifyResId);

	public List<ClassifyResBuff> listAll();
	
	List<ClassifyResBuff> listByResLabeledSetIdAndLogId(
			Map<String, Object> setIdAndLogIdMap);

	List<ClassifyResBuff> listByResSetIdAndLogId(
			Map<String, Object> setIdAndLogIdMap);

	List<ClassifyResBuff> listByLabeledSetIdAndLogIdGroupTextId(
			Map<String, Object> setIdAndLogIdMap);

}
