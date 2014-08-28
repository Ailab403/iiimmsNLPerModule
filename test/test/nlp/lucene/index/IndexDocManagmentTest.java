package test.nlp.lucene.index;

import ims.crawler.cache.ApplicationContextFactory;
import ims.nlp.entity.model.Index;
import ims.nlp.entity.service.IndexService;
import ims.nlp.lucene.index.IndexDocManagment;

import java.util.Scanner;

import org.junit.Test;

public class IndexDocManagmentTest {

	private IndexService indexService = (IndexService) ApplicationContextFactory.appContext
			.getBean("indexService");
	private IndexDocManagment indexDocManagment = (IndexDocManagment) ApplicationContextFactory.appContext
			.getBean("indexDocManagment");

	@Test
	public void testDeleteAllDocFromIndex() {

		int indexId = new Scanner(System.in).nextInt();
		Index index = indexService.loadById(indexId);
		
		indexDocManagment.initIndexEntity(index);
		
		indexDocManagment.deleteAllDocFromIndex();
	}
	
	@Test
	public void testRecoverAllDocInRecycle() {
		
		int indexId = new Scanner(System.in).nextInt();
		Index index = indexService.loadById(indexId);
		
		indexDocManagment.initIndexEntity(index);
		
		indexDocManagment.recoverAllDocInRecycle();
	}
	
	@Test
	public void testDeleteSiteDocFromIndex() {
		
		int indexId = new Scanner(System.in).nextInt();
		Index index = indexService.loadById(indexId);
		
		indexDocManagment.initIndexEntity(index);
		
		indexDocManagment.deleteSiteDocFromIndex("maoputietie");
	}
	
	@Test
	public void testRebuildIndexCompletely() {
		
		int indexId = new Scanner(System.in).nextInt();
		Index index = indexService.loadById(indexId);
		
		indexDocManagment.initIndexEntity(index);
		
		indexDocManagment.rebuildIndexCompletely("mmseg4j");
	}
	
	@Test
	public void testDropIndexFromDis() {
		
		int indexId = new Scanner(System.in).nextInt();
		Index index = indexService.loadById(indexId);
		
		indexDocManagment.initIndexEntity(index);
		
		indexDocManagment.dropIndexFromDis();
	}
}
