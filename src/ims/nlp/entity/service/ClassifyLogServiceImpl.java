package ims.nlp.entity.service;

import ims.nlp.entity.dao.ClassifyLogMapper;
import ims.nlp.entity.model.ClassifyLog;

import java.util.List;

public class ClassifyLogServiceImpl implements ClassifyLogService {

	private ClassifyLogMapper classifyLogMapper;

	public void add(ClassifyLog classifyLog) {
		this.classifyLogMapper.add(classifyLog);
	}

	public void deleteById(String classifyLogId) {
		this.classifyLogMapper.deleteById(classifyLogId);
	}

	public List<ClassifyLog> listAll() {
		return this.classifyLogMapper.listAll();
	}

	public ClassifyLog loadById(String classifyLogId) {
		return this.classifyLogMapper.loadById(classifyLogId);
	}

	public void update(ClassifyLog classifyLog) {
		this.classifyLogMapper.update(classifyLog);
	}

	public ClassifyLogMapper getClassifyLogMapper() {
		return classifyLogMapper;
	}

	public void setClassifyLogMapper(ClassifyLogMapper classifyLogMapper) {
		this.classifyLogMapper = classifyLogMapper;
	}

}
