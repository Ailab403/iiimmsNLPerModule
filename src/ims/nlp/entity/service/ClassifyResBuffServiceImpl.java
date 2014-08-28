package ims.nlp.entity.service;

import ims.nlp.entity.dao.ClassifyResBuffMapper;
import ims.nlp.entity.model.ClassifyResBuff;

import java.util.List;
import java.util.Map;

public class ClassifyResBuffServiceImpl implements ClassifyResBuffService {

	private ClassifyResBuffMapper classifyResBuffMapper;

	public void add(ClassifyResBuff classifyResBuff) {
		this.classifyResBuffMapper.add(classifyResBuff);
	}

	public void deleteById(int classifyResId) {
		this.classifyResBuffMapper.deleteById(classifyResId);
	}

	public List<ClassifyResBuff> listAll() {
		return this.classifyResBuffMapper.listAll();
	}

	public ClassifyResBuff loadById(int classifyResId) {
		return this.classifyResBuffMapper.loadById(classifyResId);
	}

	public void update(ClassifyResBuff classifyResBuff) {
		this.classifyResBuffMapper.update(classifyResBuff);
	}

	public List<ClassifyResBuff> listByLabeledSetIdAndLogIdGroupTextId(
			Map<String, Object> setIdAndLogIdMap) {
		return this.classifyResBuffMapper
				.listByLabeledSetIdAndLogIdGroupTextId(setIdAndLogIdMap);
	}

	public List<ClassifyResBuff> listByResLabeledSetIdAndLogId(
			Map<String, Object> setIdAndLogIdMap) {
		return this.classifyResBuffMapper
				.listByResLabeledSetIdAndLogId(setIdAndLogIdMap);
	}

	public List<ClassifyResBuff> listByResSetIdAndLogId(
			Map<String, Object> setIdAndLogIdMap) {
		return this.classifyResBuffMapper
				.listByResSetIdAndLogId(setIdAndLogIdMap);
	}

	public ClassifyResBuffMapper getClassifyResBuffMapper() {
		return classifyResBuffMapper;
	}

	public void setClassifyResBuffMapper(
			ClassifyResBuffMapper classifyResBuffMapper) {
		this.classifyResBuffMapper = classifyResBuffMapper;
	}

}
