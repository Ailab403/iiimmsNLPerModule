package ims.nlp.entity.dao;

import java.util.List;

import ims.nlp.entity.model.ClassifyMission;

public interface ClassifyMissionMapper {

	void add(ClassifyMission classifyMission);

	void deleteById(String missionId);

	void update(ClassifyMission classifyMission);

	ClassifyMission loadById(String missionId);

	List<ClassifyMission> listAll();
}
