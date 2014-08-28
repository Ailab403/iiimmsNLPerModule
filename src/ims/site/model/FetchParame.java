package ims.site.model;

public class FetchParame {

	// �ɼ����̻�����������

	private int fetchParameId; // �������(primary key)

	private int siteId;
	private int categoryId;

	private String baNameQuery; // ������ʶ��Query
	private String titleQuery; // ���ɱ���ʶ��Query
	private String readNumQuery; // �����Ķ���ʶ�����
	private String commentNumQuery; // ����������ʶ�����
	private String forwardNumQuery; // ����ת����ʶ�����
	private String pagerQuery; // ��ҳ��Ϣʶ��Query

	private FetchPagerObj fetchPagerObj; // ������ҳʵ������Ϣ��

	private String postDivQuery; // ��������divʶ��Query
	private String postContentQuery; // ��������ʶ��Query
	private String postAuthorQuery; // ��������ʶ��Query
	private String postTimeQuery; // ����ʱ��ʶ��Query
	private String replyDivQuery; // �ظ�����divʶ��Query
	private String replyContentQuery; // �ظ���Ϣʶ��Query
	private String replyAuthorQuery; // �ظ�����ʶ��Query
	private String replyTimeQuery; // �ظ�ʱ��ʶ��Query

	private Site site; // ��վId�����Ӧ�����
	private SiteCategory siteCategory;

	public FetchParame() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FetchParame(int siteId, int categoryId, String baNameQuery,
			String titleQuery, String readNumQuery, String commentNumQuery,
			String forawardNumQuery, String pagerQuery,
			FetchPagerObj fetchPagerObj, String postDivQuery,
			String postContentQuery, String postAuthorQuery,
			String postTimeQuery, String replyDivQuery,
			String replyContentQuery, String replyAuthorQuery,
			String replyTimeQuery, Site site, SiteCategory siteCategory) {
		super();
		this.siteId = siteId;
		this.categoryId = categoryId;
		this.baNameQuery = baNameQuery;
		this.titleQuery = titleQuery;
		this.readNumQuery = readNumQuery;
		this.commentNumQuery = commentNumQuery;
		this.forwardNumQuery = forawardNumQuery;
		this.pagerQuery = pagerQuery;
		this.fetchPagerObj = fetchPagerObj;
		this.postDivQuery = postDivQuery;
		this.postContentQuery = postContentQuery;
		this.postAuthorQuery = postAuthorQuery;
		this.postTimeQuery = postTimeQuery;
		this.replyDivQuery = replyDivQuery;
		this.replyContentQuery = replyContentQuery;
		this.replyAuthorQuery = replyAuthorQuery;
		this.replyTimeQuery = replyTimeQuery;
		this.site = site;
		this.siteCategory = siteCategory;
	}

	public FetchParame(int fetchParameId, int siteId, int categoryId,
			String baNameQuery, String titleQuery, String readNumQuery,
			String commentNumQuery, String forawardNumQuery, String pagerQuery,
			FetchPagerObj fetchPagerObj, String postDivQuery,
			String postContentQuery, String postAuthorQuery,
			String postTimeQuery, String replyDivQuery,
			String replyContentQuery, String replyAuthorQuery,
			String replyTimeQuery, Site site, SiteCategory siteCategory) {
		super();
		this.fetchParameId = fetchParameId;
		this.siteId = siteId;
		this.categoryId = categoryId;
		this.baNameQuery = baNameQuery;
		this.titleQuery = titleQuery;
		this.readNumQuery = readNumQuery;
		this.commentNumQuery = commentNumQuery;
		this.forwardNumQuery = forawardNumQuery;
		this.pagerQuery = pagerQuery;
		this.fetchPagerObj = fetchPagerObj;
		this.postDivQuery = postDivQuery;
		this.postContentQuery = postContentQuery;
		this.postAuthorQuery = postAuthorQuery;
		this.postTimeQuery = postTimeQuery;
		this.replyDivQuery = replyDivQuery;
		this.replyContentQuery = replyContentQuery;
		this.replyAuthorQuery = replyAuthorQuery;
		this.replyTimeQuery = replyTimeQuery;
		this.site = site;
		this.siteCategory = siteCategory;
	}

	public int getFetchParameId() {
		return fetchParameId;
	}

	public void setFetchParameId(int fetchParameId) {
		this.fetchParameId = fetchParameId;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getBaNameQuery() {
		return baNameQuery;
	}

	public void setBaNameQuery(String baNameQuery) {
		this.baNameQuery = baNameQuery;
	}

	public String getTitleQuery() {
		return titleQuery;
	}

	public void setTitleQuery(String titleQuery) {
		this.titleQuery = titleQuery;
	}

	public String getReadNumQuery() {
		return readNumQuery;
	}

	public void setReadNumQuery(String readNumQuery) {
		this.readNumQuery = readNumQuery;
	}

	public String getCommentNumQuery() {
		return commentNumQuery;
	}

	public void setCommentNumQuery(String commentNumQuery) {
		this.commentNumQuery = commentNumQuery;
	}

	public String getForwardNumQuery() {
		return forwardNumQuery;
	}

	public void setForwardNumQuery(String forwardNumQuery) {
		this.forwardNumQuery = forwardNumQuery;
	}

	public String getPagerQuery() {
		return pagerQuery;
	}

	public void setPagerQuery(String pagerQuery) {
		this.pagerQuery = pagerQuery;
	}

	public FetchPagerObj getFetchPagerObj() {
		return fetchPagerObj;
	}

	public void setFetchPagerObj(FetchPagerObj fetchPagerObj) {
		this.fetchPagerObj = fetchPagerObj;
	}

	public String getPostDivQuery() {
		return postDivQuery;
	}

	public void setPostDivQuery(String postDivQuery) {
		this.postDivQuery = postDivQuery;
	}

	public String getPostContentQuery() {
		return postContentQuery;
	}

	public void setPostContentQuery(String postContentQuery) {
		this.postContentQuery = postContentQuery;
	}

	public String getPostAuthorQuery() {
		return postAuthorQuery;
	}

	public void setPostAuthorQuery(String postAuthorQuery) {
		this.postAuthorQuery = postAuthorQuery;
	}

	public String getPostTimeQuery() {
		return postTimeQuery;
	}

	public void setPostTimeQuery(String postTimeQuery) {
		this.postTimeQuery = postTimeQuery;
	}

	public String getReplyDivQuery() {
		return replyDivQuery;
	}

	public void setReplyDivQuery(String replyDivQuery) {
		this.replyDivQuery = replyDivQuery;
	}

	public String getReplyContentQuery() {
		return replyContentQuery;
	}

	public void setReplyContentQuery(String replyContentQuery) {
		this.replyContentQuery = replyContentQuery;
	}

	public String getReplyAuthorQuery() {
		return replyAuthorQuery;
	}

	public void setReplyAuthorQuery(String replyAuthorQuery) {
		this.replyAuthorQuery = replyAuthorQuery;
	}

	public String getReplyTimeQuery() {
		return replyTimeQuery;
	}

	public void setReplyTimeQuery(String replyTimeQuery) {
		this.replyTimeQuery = replyTimeQuery;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public SiteCategory getSiteCategory() {
		return siteCategory;
	}

	public void setSiteCategory(SiteCategory siteCategory) {
		this.siteCategory = siteCategory;
	}

	@Override
	public String toString() {
		return "FetchParame [baNameQuery=" + baNameQuery + ", commentNumQuery="
				+ commentNumQuery + ", fetchPagerObj=" + fetchPagerObj
				+ ", fetchParameId=" + fetchParameId + ", forawardNumQuery="
				+ forwardNumQuery + ", pagerQuery=" + pagerQuery
				+ ", postAuthorQuery=" + postAuthorQuery
				+ ", postContentQuery=" + postContentQuery + ", postDivQuery="
				+ postDivQuery + ", postTimeQuery=" + postTimeQuery
				+ ", readNumQuery=" + readNumQuery + ", replyAuthorQuery="
				+ replyAuthorQuery + ", replyContentQuery=" + replyContentQuery
				+ ", replyDivQuery=" + replyDivQuery + ", replyTimeQuery="
				+ replyTimeQuery + ", site=" + site + ", siteCategory="
				+ siteCategory + ", categoryId=" + categoryId + ", siteId="
				+ siteId + ", titleQuery=" + titleQuery + "]";
	}

}
