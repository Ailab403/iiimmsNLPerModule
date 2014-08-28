package ims.crawlerLog.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import ims.crawlerLog.model.TaskLog;

public interface TaskLogMapper {
	
	void add(TaskLog taskLog);
	
	void deleteById(String taskLogId);
	
	void update(TaskLog taskLog);
	
	void updateTaskStatusById(TaskLog taskLog);
	
	TaskLog loadById(String taskLogId);
	
	Set<TaskLog> listAll();
	
	TaskLog loadByIdOnMax();
	
	List<TaskLog> listAfterStartTime(Timestamp time);
}
