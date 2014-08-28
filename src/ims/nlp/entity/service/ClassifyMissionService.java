package ims.nlp.entity.service;

import ims.nlp.entity.model.ClassifyMission;

import java.util.List;

public interface ClassifyMissionService {

	public void add(ClassifyMission classifyMission);

	public void deleteById(String missionId);

	public void update(ClassifyMission classifyMission);

	public ClassifyMission loadById(String missionId);

	public List<ClassifyMission> listAll();
}
