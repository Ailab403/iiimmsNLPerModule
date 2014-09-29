package ims.corpusProduct.model;

import ims.crawlerLog.model.TaskLog;
import ims.nlp.entity.model.ClassicTextSet;
import ims.nlp.entity.model.ClassifyLog;

import java.sql.Timestamp;
import ims.site.model.Post;
import ims.site.model.Site;

public class CorpusProduct implements java.io.Serializable {
	private static final long serialVersionUID = -4794151864336777944L;

	private int corpusProductId; // 语料产品编号
	private int setId; // 所属类别编号列表
	private int siteId; // 网站编号
	private String taskLogId; // 总日志编号
	private String classifyLogId; // 映射到分类任务日志表
	private int postId; // 节点编号
	private String corpusProductName; // 语料标题
	private String corpusProductAbs; // 语料摘要
	private int scoreRank; // 程度评分
	private String nodeId; // 对应 Mongo节点
	private String annexPath; // 附件图片地址
	private int author; // 创建作者
	private Timestamp prodTime; // 创建时间
	private Timestamp lastEditTime; // 最后一次修改时间
	private String corpusProductExp; // 语料产品信息说明

	// 虚拟字段标志当前产品有没有被关联到特定的自定义主题中（数据库中没有此字段，其余部分不用修改）
	private boolean flagLinked;

	/*-----------------------对应外键表关联------------------------*/
	private ClassicTextSet classicTextSet;
	private Site site;
	private TaskLog taskLog;
	private ClassifyLog classifyLog;
	private Post post;

	// ---------------------------------CorpusProduct的构造方法-------------------------------------------
	public CorpusProduct() {
		super();
	}

	public CorpusProduct(int setId, int siteId, String taskLogId,
			String classifyLogId, int postId, String corpusProductName,
			String corpusProductAbs, int scoreRank, String nodeId,
			String annexPath, int author, Timestamp prodTime,
			Timestamp lastEditTime, String corpusProductExp,
			ClassicTextSet classicTextSet, Site site, TaskLog taskLog,
			ClassifyLog classifyLog, Post post) {
		super();
		this.setId = setId;
		this.siteId = siteId;
		this.taskLogId = taskLogId;
		this.classifyLogId = classifyLogId;
		this.postId = postId;
		this.corpusProductName = corpusProductName;
		this.corpusProductAbs = corpusProductAbs;
		this.scoreRank = scoreRank;
		this.nodeId = nodeId;
		this.annexPath = annexPath;
		this.author = author;
		this.prodTime = prodTime;
		this.lastEditTime = lastEditTime;
		this.corpusProductExp = corpusProductExp;
		this.classicTextSet = classicTextSet;
		this.site = site;
		this.taskLog = taskLog;
		this.classifyLog = classifyLog;
		this.post = post;
	}

	public CorpusProduct(int corpusProductId, int setId, int siteId,
			String taskLogId, String classifyLogId, int postId,
			String corpusProductName, String corpusProductAbs, int scoreRank,
			String nodeId, String annexPath, int author, Timestamp prodTime,
			Timestamp lastEditTime, String corpusProductExp,
			ClassicTextSet classicTextSet, Site site, TaskLog taskLog,
			ClassifyLog classifyLog, Post post) {
		super();
		this.corpusProductId = corpusProductId;
		this.setId = setId;
		this.siteId = siteId;
		this.taskLogId = taskLogId;
		this.classifyLogId = classifyLogId;
		this.postId = postId;
		this.corpusProductName = corpusProductName;
		this.corpusProductAbs = corpusProductAbs;
		this.scoreRank = scoreRank;
		this.nodeId = nodeId;
		this.annexPath = annexPath;
		this.author = author;
		this.prodTime = prodTime;
		this.lastEditTime = lastEditTime;
		this.corpusProductExp = corpusProductExp;
		this.classicTextSet = classicTextSet;
		this.site = site;
		this.taskLog = taskLog;
		this.classifyLog = classifyLog;
		this.post = post;
	}

	public CorpusProduct(int setId, int siteId, String taskLogId,
			String classifyLogId, int postId, String corpusProductName,
			String corpusProductAbs, int scoreRank, String nodeId,
			String annexPath, int author, Timestamp prodTime,
			Timestamp lastEditTime, String corpusProductExp,
			boolean flagLinked, ClassicTextSet classicTextSet, Site site,
			TaskLog taskLog, ClassifyLog classifyLog, Post post) {
		super();
		this.setId = setId;
		this.siteId = siteId;
		this.taskLogId = taskLogId;
		this.classifyLogId = classifyLogId;
		this.postId = postId;
		this.corpusProductName = corpusProductName;
		this.corpusProductAbs = corpusProductAbs;
		this.scoreRank = scoreRank;
		this.nodeId = nodeId;
		this.annexPath = annexPath;
		this.author = author;
		this.prodTime = prodTime;
		this.lastEditTime = lastEditTime;
		this.corpusProductExp = corpusProductExp;
		this.flagLinked = flagLinked;
		this.classicTextSet = classicTextSet;
		this.site = site;
		this.taskLog = taskLog;
		this.classifyLog = classifyLog;
		this.post = post;
	}

	public CorpusProduct(int corpusProductId, int setId, int siteId,
			String taskLogId, String classifyLogId, int postId,
			String corpusProductName, String corpusProductAbs, int scoreRank,
			String nodeId, String annexPath, int author, Timestamp prodTime,
			Timestamp lastEditTime, String corpusProductExp,
			boolean flagLinked, ClassicTextSet classicTextSet, Site site,
			TaskLog taskLog, ClassifyLog classifyLog, Post post) {
		super();
		this.corpusProductId = corpusProductId;
		this.setId = setId;
		this.siteId = siteId;
		this.taskLogId = taskLogId;
		this.classifyLogId = classifyLogId;
		this.postId = postId;
		this.corpusProductName = corpusProductName;
		this.corpusProductAbs = corpusProductAbs;
		this.scoreRank = scoreRank;
		this.nodeId = nodeId;
		this.annexPath = annexPath;
		this.author = author;
		this.prodTime = prodTime;
		this.lastEditTime = lastEditTime;
		this.corpusProductExp = corpusProductExp;
		this.flagLinked = flagLinked;
		this.classicTextSet = classicTextSet;
		this.site = site;
		this.taskLog = taskLog;
		this.classifyLog = classifyLog;
		this.post = post;
	}

	// -------------------------------------setter/getter--------------------------------------------
	public int getCorpusProductId() {
		return corpusProductId;
	}

	public void setCorpusProductId(int corpusProductId) {
		this.corpusProductId = corpusProductId;
	}

	public int getSetId() {
		return setId;
	}

	public void setSetId(int setId) {
		this.setId = setId;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getCorpusProductName() {
		return corpusProductName;
	}

	public void setCorpusProductName(String corpusProductName) {
		this.corpusProductName = corpusProductName;
	}

	public String getCorpusProductAbs() {
		return corpusProductAbs;
	}

	public void setCorpusProductAbs(String corpusProductAbs) {
		this.corpusProductAbs = corpusProductAbs;
	}

	public int getScoreRank() {
		return scoreRank;
	}

	public void setScoreRank(int scoreRank) {
		this.scoreRank = scoreRank;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getAnnexPath() {
		return annexPath;
	}

	public void setAnnexPath(String annexPath) {
		this.annexPath = annexPath;
	}

	public int getAuthor() {
		return author;
	}

	public void setAuthor(int author) {
		this.author = author;
	}

	public Timestamp getProdTime() {
		return prodTime;
	}

	public void setProdTime(Timestamp prodTime) {
		this.prodTime = prodTime;
	}

	public Timestamp getLastEditTime() {
		return lastEditTime;
	}

	public void setLastEditTime(Timestamp lastEditTime) {
		this.lastEditTime = lastEditTime;
	}

	public String getCorpusProductExp() {
		return corpusProductExp;
	}

	public void setCorpusProductExp(String corpusProductExp) {
		this.corpusProductExp = corpusProductExp;
	}

	public ClassicTextSet getClassicTextSet() {
		return classicTextSet;
	}

	public void setClassicTextSet(ClassicTextSet classicTextSet) {
		this.classicTextSet = classicTextSet;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public String getTaskLogId() {
		return taskLogId;
	}

	public void setTaskLogId(String taskLogId) {
		this.taskLogId = taskLogId;
	}

	public String getClassifyLogId() {
		return classifyLogId;
	}

	public void setClassifyLogId(String classifyLogId) {
		this.classifyLogId = classifyLogId;
	}

	public TaskLog getTaskLog() {
		return taskLog;
	}

	public void setTaskLog(TaskLog taskLog) {
		this.taskLog = taskLog;
	}

	public ClassifyLog getClassifyLog() {
		return classifyLog;
	}

	public void setClassifyLog(ClassifyLog classifyLog) {
		this.classifyLog = classifyLog;
	}

	public boolean isFlagLinked() {
		return flagLinked;
	}

	public void setFlagLinked(boolean flagLinked) {
		this.flagLinked = flagLinked;
	}

}
