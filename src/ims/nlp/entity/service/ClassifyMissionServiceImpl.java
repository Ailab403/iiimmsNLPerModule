package ims.nlp.entity.service;

import ims.nlp.entity.dao.ClassifyMissionMapper;
import ims.nlp.entity.model.ClassifyMission;

import java.util.List;

public class ClassifyMissionServiceImpl implements ClassifyMissionService {

	private ClassifyMissionMapper classifyMissionMapper;

	public void add(ClassifyMission classifyMission) {
		this.classifyMissionMapper.add(classifyMission);
	}

	public void deleteById(String missionId) {
		this.classifyMissionMapper.deleteById(missionId);
	}

	public List<ClassifyMission> listAll() {
		return this.classifyMissionMapper.listAll();
	}

	public ClassifyMission loadById(String missionId) {
		return this.classifyMissionMapper.loadById(missionId);
	}

	public void update(ClassifyMission classifyMission) {
		this.classifyMissionMapper.update(classifyMission);
	}

	public ClassifyMissionMapper getClassifyMissionMapper() {
		return classifyMissionMapper;
	}

	public void setClassifyMissionMapper(
			ClassifyMissionMapper classifyMissionMapper) {
		this.classifyMissionMapper = classifyMissionMapper;
	}

}
