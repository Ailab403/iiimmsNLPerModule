package ims.nlp.entity.dao;

import java.util.List;

import ims.nlp.entity.model.ClassifyLog;

public interface ClassifyLogMapper {
	
	void add(ClassifyLog classifyLog);
	
	void deleteById(String classifyLogId);
	
	void update(ClassifyLog classifyLog);
	
	ClassifyLog loadById(String classifyLogId);
	
	List<ClassifyLog> listAll();
	
}
