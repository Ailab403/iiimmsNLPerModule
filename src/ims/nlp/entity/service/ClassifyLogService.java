package ims.nlp.entity.service;

import ims.nlp.entity.model.ClassifyLog;

import java.util.List;

public interface ClassifyLogService {

	public void add(ClassifyLog classifyLog);

	public void deleteById(String classifyLogId);

	public void update(ClassifyLog classifyLog);

	public ClassifyLog loadById(String classifyLogId);

	public List<ClassifyLog> listAll();

}
