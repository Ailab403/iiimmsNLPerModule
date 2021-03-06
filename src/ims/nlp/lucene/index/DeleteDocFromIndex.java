package ims.nlp.lucene.index;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;

public class DeleteDocFromIndex {

	/**
	 * 初始化索引目录
	 * 
	 * @param indexPath
	 */
	public static Directory loadDirectory(String indexPath) {

		Directory directory = null;

		try {

			// 创建索引到硬盘当中
			directory = FSDirectory.open(new File(indexPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return directory;
	}

	/**
	 * 清空索引中所有的文档
	 * 
	 * @param indexPath
	 * @param analyzer
	 */
	public synchronized static void clearAllIndex(String indexPath,
			Analyzer analyzer) {

		IndexWriter writer = null;

		try {
			Directory directory = loadDirectory(indexPath);
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35,
					analyzer);
			writer = new IndexWriter(directory, iwc);

			writer.deleteAll();
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据postUrlMD5删除索引中的唯一文档到回收站
	 * 
	 * @param postUrlMD5
	 * @param indexPath
	 * @param analyzer
	 */
	public synchronized static void deleteSingleDocFromIndex(String postUrlMD5,
			String indexPath, Analyzer analyzer) {

		IndexWriter writer = null;

		try {
			Directory directory = loadDirectory(indexPath);
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35,
					analyzer);
			writer = new IndexWriter(directory, iwc);

			writer.deleteDocuments(new Term("postUrlMD5", postUrlMD5));
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据taskLogId批量删除索引中的文档
	 * 
	 * @param taskLogId
	 * @param indexPath
	 * @param analyzer
	 */
	public synchronized static void deleteDocFromIndexByTaskLogId(
			String taskLogId, String indexPath, Analyzer analyzer) {

		IndexWriter writer = null;

		try {
			Directory directory = loadDirectory(indexPath);
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35,
					analyzer);
			writer = new IndexWriter(directory, iwc);

			writer.deleteDocuments(new Term("taskLogId", taskLogId));
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据mongo中的集合名称批量删除索引中的文档
	 * 
	 * @param collectionName
	 * @param indexPath
	 * @param analyzer
	 */
	public synchronized static void deleteDocFromIndexByCollectionName(
			String collectionName, String indexPath, Analyzer analyzer) {

		IndexWriter writer = null;

		try {
			Directory directory = loadDirectory(indexPath);
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35,
					analyzer);
			writer = new IndexWriter(directory, iwc);

			writer.deleteDocuments(new Term("collectionName", collectionName));
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 恢复索引文档回收站的所有已删除文档
	 * 
	 * @param indexPath
	 */
	public synchronized static void recoverAllDeleteDoc(String indexPath) {

		IndexReader reader = null;

		try {
			Directory directory = loadDirectory(indexPath);
			// 设置只读为false
			reader = IndexReader.open(directory, false);

			reader.undeleteAll();
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	 * 强制清除索引文档回收站，不推荐使用
	 * 
	 * @param indexPath
	 * @param analyzer
	 */
	@Deprecated
	public synchronized static void forceMergeAllDeleteDoc(String indexPath,
			Analyzer analyzer) {

		IndexWriter writer = null;

		try {
			Directory directory = loadDirectory(indexPath);
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35,
					analyzer);
			writer = new IndexWriter(directory, iwc);
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
