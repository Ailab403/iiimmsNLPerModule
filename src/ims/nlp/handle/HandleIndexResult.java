package ims.nlp.handle;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import ims.nlp.entity.model.ClassicTextSet;
import ims.nlp.entity.model.Index;
import ims.nlp.entity.service.ClassicTextSetService;
import ims.nlp.entity.service.IndexService;

/**
 * 涉及到mysql数据库中索引信息操作的方法（需注入）
 * 
 * @author superhy
 * 
 */
public class HandleIndexResult {

	// 从spring中注入service方法
	private IndexService indexService;
	private ClassicTextSetService classicTextSetService;

	private ClassicTextSet classicTextSet;

	public void initClassicTextSet(int setId) {
		try {
			ClassicTextSet classicTextSet = this.classicTextSetService
					.loadById(setId);

			setClassicTextSet(classicTextSet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			System.err.println("没有查到对应的分类分类类别！");
		}
	}

	/**
	 * 向数据库中添加新的索引信息实体
	 * 
	 * @param setId
	 * @param indexName
	 * @param indexPath
	 * @param indexExp
	 */
	public void createNewIndexEntity(int setId, String indexName,
			String indexPath, String indexExp) {

		IndexReader reader = null;

		try {
			Directory directory = FSDirectory.open(new File(indexPath));
			reader = IndexReader.open(directory);
			int docNum = reader.numDocs();
			int docDeletedNum = reader.numDeletedDocs();
			Timestamp refreshTime = Timestamp.valueOf(new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss").format(new Date()));

			this.initClassicTextSet(setId);
			Index index = new Index(setId, indexName, indexPath, docNum,
					docDeletedNum, refreshTime, indexExp, this.classicTextSet);

			// 加入到索引信息库中
			this.indexService.add(index);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			System.err.println("加载索引文件发生错误，请检查磁盘上的索引文件！");
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 修改数据库中的索引信息实体
	 * 
	 * @param index
	 */
	public void updateIndexEntity(Index index) {

		IndexReader reader = null;

		try {
			Directory directory = FSDirectory.open(new File(index
					.getIndexPath()));
			reader = IndexReader.open(directory);
			int docNum = reader.numDocs();
			int docDeletedNum = reader.numDeletedDocs();
			Timestamp refreshTime = Timestamp.valueOf(new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss").format(new Date()));

			index.setDocNum(docNum);
			index.setDocDeletedNum(docDeletedNum);
			index.setRefreshTime(refreshTime);

			// 修改到索引信息库中
			this.indexService.update(index);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			System.err.println("加载索引文件发生错误，请检查磁盘上的索引文件！");
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 废除索引信息实体（不删除，但是所有信息归零或禁止）
	 * 
	 * @param index
	 */
	public void abolishIndexEntity(Index index) {
		index.setDocNum(0);
		index.setDocDeletedNum(0);
		index.setRefreshTime(Timestamp.valueOf(new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss").format(new Date())));
		index.setIndexExp("abolished");
		
		this.indexService.update(index);
	}

	/**
	 * 删除索引信息实体
	 * 
	 * @param index
	 */
	@Deprecated
	public void deleteIndexEntity(Index index) {

		this.indexService.deleteById(index.getIndexId());
	}

	public IndexService getIndexService() {
		return indexService;
	}

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	public ClassicTextSetService getClassicTextSetService() {
		return classicTextSetService;
	}

	public void setClassicTextSetService(
			ClassicTextSetService classicTextSetService) {
		this.classicTextSetService = classicTextSetService;
	}

	public ClassicTextSet getClassicTextSet() {
		return classicTextSet;
	}

	public void setClassicTextSet(ClassicTextSet classicTextSet) {
		this.classicTextSet = classicTextSet;
	}

}
