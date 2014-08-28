package test.nlp.handle;

import ims.crawler.cache.ApplicationContextFactory;
import ims.nlp.entity.model.ClassifierModel;
import ims.nlp.entity.model.ClassifyMission;
import ims.nlp.handle.HandleClassifyLogResult;

import org.junit.Test;

public class HandleClassifyLogResultTest {

	@Test
	public void testCreateNewClassifyLog() {
		ClassifyMission classifyMission = new ClassifyMission();
		ClassifierModel classifierModel = new ClassifierModel();

		classifyMission.setMissionId("test");
		classifierModel.setActiveState(1);

		HandleClassifyLogResult handleClassifyLogResult = (HandleClassifyLogResult) ApplicationContextFactory.appContext
				.getBean("handleClassifyLogResult");

		handleClassifyLogResult.createNewClassifyLog(classifyMission,
				classifierModel, 0);

	}
}
