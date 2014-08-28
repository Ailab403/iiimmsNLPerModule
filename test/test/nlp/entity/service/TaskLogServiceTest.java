package test.nlp.entity.service;

import ims.crawler.cache.ApplicationContextFactory;
import ims.crawlerLog.model.TaskLog;
import ims.crawlerLog.service.TaskLogService;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Test;

public class TaskLogServiceTest {

	@Test
	public void testListAfterStartTime() {
		Timestamp time = Timestamp.valueOf("2013-01-01 22:00:00");

		TaskLogService taskLogService = (TaskLogService) ApplicationContextFactory.appContext
				.getBean("taskLogService");
		List<TaskLog> taskLogs = taskLogService.listAfterStartTime(time);

		for (TaskLog taskLog : taskLogs) {
			System.out.println(taskLog.toString());
		}
	}
}
