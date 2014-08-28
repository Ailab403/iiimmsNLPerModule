package ims.corpusProduct.model;

import ims.nlp.entity.model.ClassicTextSet;
import ims.site.model.Post;
import ims.site.model.Site;

import java.sql.Date;

public class CorpusProduct implements java.io.Serializable{
	private static final long serialVersionUID = -4794151864336777944L;
	
	private int     corpusProductId;      // ���ϲ�Ʒ���
	private int     setId;              // ����������б� 
	private int     siteId;               // ��վ���
	private int     postId;               // �ڵ���
	private String  corpusProductName;    // ���ϱ���
	private String  corpusProductAbs;     // ����ժҪ
	private int     scoreRank;            // �̶�����
	private String  nodeId;               // ��Ӧ Mongo�ڵ�
	private String  annexPath;            // ����ͼƬ��ַ
	private int     author;               // ��������
	private Date    prodTime;             // ����ʱ��
	private Date    lastEditTime;         // ���һ���޸�ʱ��	
	private String  corpusProductExp;     // ���ϲ�Ʒ��Ϣ˵��
	
	/*-----------------------��Ӧ��������------------------------*/
	private ClassicTextSet classicTextSet;
	private Site site;
	private Post post;
	//---------------------------------CorpusProduct�Ĺ��췽��-------------------------------------------	
	public CorpusProduct() {
		super();
	}
	public CorpusProduct(int corpusProductId, int setId, int siteId,
			int postId, String corpusProductName, String corpusProductAbs,
			int scoreRank, String nodeId, String annexPath, int author,
			Date prodTime, Date lastEditTime, String corpusProductExp,
			ClassicTextSet classicTextSet, Site site, Post post) {
		super();
		this.corpusProductId = corpusProductId;
		this.setId = setId;
		this.siteId = siteId;
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
		this.post = post;
	}
	//-------------------------------------setter/getter--------------------------------------------
	public int getCorpusProductId() {
		return corpusProductId;
	}
	public void setCorpusProductId(int corpusProductId) {
		this.corpusProductId = corpusProductId;
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
	public Date getProdTime() {
		return prodTime;
	}
	public void setProdTime(Date prodTime) {
		this.prodTime = prodTime;
	}
	public Date getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
	public String getCorpusProductExp() {
		return corpusProductExp;
	}
	public void setCorpusProductExp(String corpusProductExp) {
		this.corpusProductExp = corpusProductExp;
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
	public int getSetId() {
		return setId;
	}
	public void setSetId(int setId) {
		this.setId = setId;
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
}
