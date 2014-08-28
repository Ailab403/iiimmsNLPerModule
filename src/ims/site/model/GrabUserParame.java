package ims.site.model;

import java.sql.Timestamp;

public class GrabUserParame {

	// �����û��Զ������ò�����

	private int grabUserParameId; // �������(primary key)

	private int siteId;
	private int categoryId;

	private int themePageNumLimit; // ÿ�����⣨��飩��ȡ�����б�ҳ������
	private Timestamp postStartTimeLimit; // �����б�ҳʱ�����ƿ�ʼʱ�䣨��׼ʱ���ʽ��
	private Timestamp postEndTimeLimit; // �����б�ҳʱ�����ƽ�ֹʱ�䣨��׼ʱ���ʽ��
	private String postTimeRangeLimit; // ʱ����˼������
	private int timeDeterminer; // ʱ�����ƾ�������
	private int postClickNumLimit; // �ڵ��ȶȵ��������
	private int postReplyNumLimit; // �ڵ��ȶȻظ�����
	private int postForwardNumLimit; // �ڵ�ת��������

	// ��վId�����Ӧ�����
	private Site site;
	private SiteCategory siteCategory;

	public GrabUserParame() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GrabUserParame(int siteId, int categoryId, int themePageNumLimit,
			Timestamp postStartTimeLimit, Timestamp postEndTimeLimit,
			String postTimeRangeLimit, int timeDeterminer,
			int postClickNumLimit, int postReplyNumLimit,
			int postForwardNumLimit, Site site, SiteCategory siteCategory) {
		super();
		this.siteId = siteId;
		this.categoryId = categoryId;
		this.themePageNumLimit = themePageNumLimit;
		this.postStartTimeLimit = postStartTimeLimit;
		this.postEndTimeLimit = postEndTimeLimit;
		this.postTimeRangeLimit = postTimeRangeLimit;
		this.timeDeterminer = timeDeterminer;
		this.postClickNumLimit = postClickNumLimit;
		this.postReplyNumLimit = postReplyNumLimit;
		this.postForwardNumLimit = postForwardNumLimit;
		this.site = site;
		this.siteCategory = siteCategory;
	}

	public GrabUserParame(int grabUserParameId, int siteId, int categoryId,
			int themePageNumLimit, Timestamp postStartTimeLimit,
			Timestamp postEndTimeLimit, String postTimeRangeLimit,
			int timeDeterminer, int postClickNumLimit, int postReplyNumLimit,
			int postForwardNumLimit, Site site, SiteCategory siteCategory) {
		super();
		this.grabUserParameId = grabUserParameId;
		this.siteId = siteId;
		this.categoryId = categoryId;
		this.themePageNumLimit = themePageNumLimit;
		this.postStartTimeLimit = postStartTimeLimit;
		this.postEndTimeLimit = postEndTimeLimit;
		this.postTimeRangeLimit = postTimeRangeLimit;
		this.timeDeterminer = timeDeterminer;
		this.postClickNumLimit = postClickNumLimit;
		this.postReplyNumLimit = postReplyNumLimit;
		this.postForwardNumLimit = postForwardNumLimit;
		this.site = site;
		this.siteCategory = siteCategory;
	}

	public int getGrabUserParameId() {
		return grabUserParameId;
	}

	public void setGrabUserParameId(int grabUserParameId) {
		this.grabUserParameId = grabUserParameId;
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

	public int getThemePageNumLimit() {
		return themePageNumLimit;
	}

	public void setThemePageNumLimit(int themePageNumLimit) {
		this.themePageNumLimit = themePageNumLimit;
	}

	public Timestamp getPostStartTimeLimit() {
		return postStartTimeLimit;
	}

	public void setPostStartTimeLimit(Timestamp postStartTimeLimit) {
		this.postStartTimeLimit = postStartTimeLimit;
	}

	public Timestamp getPostEndTimeLimit() {
		return postEndTimeLimit;
	}

	public void setPostEndTimeLimit(Timestamp postEndTimeLimit) {
		this.postEndTimeLimit = postEndTimeLimit;
	}

	public String getPostTimeRangeLimit() {
		return postTimeRangeLimit;
	}

	public void setPostTimeRangeLimit(String postTimeRangeLimit) {
		this.postTimeRangeLimit = postTimeRangeLimit;
	}

	public int getTimeDeterminer() {
		return timeDeterminer;
	}

	public void setTimeDeterminer(int timeDeterminer) {
		this.timeDeterminer = timeDeterminer;
	}

	public int getPostClickNumLimit() {
		return postClickNumLimit;
	}

	public void setPostClickNumLimit(int postClickNumLimit) {
		this.postClickNumLimit = postClickNumLimit;
	}

	public int getPostReplyNumLimit() {
		return postReplyNumLimit;
	}

	public void setPostReplyNumLimit(int postReplyNumLimit) {
		this.postReplyNumLimit = postReplyNumLimit;
	}

	public int getPostForwardNumLimit() {
		return postForwardNumLimit;
	}

	public void setPostForwardNumLimit(int postForwardNumLimit) {
		this.postForwardNumLimit = postForwardNumLimit;
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
		return "GrabUserParame [grabUserParameId=" + grabUserParameId
				+ ", postClickNumLimit=" + postClickNumLimit
				+ ", postEndTimeLimit=" + postEndTimeLimit
				+ ", postForwardNumLimit=" + postForwardNumLimit
				+ ", postReplyNumLimit=" + postReplyNumLimit
				+ ", postStartTimeLimit=" + postStartTimeLimit
				+ ", postTimeRangeLimit=" + postTimeRangeLimit + ", site="
				+ site + ", siteCategory=" + siteCategory + ", categoryId="
				+ categoryId + ", siteId=" + siteId + ", themePageNumLimit="
				+ themePageNumLimit + ", timeDeterminer=" + timeDeterminer
				+ "]";
	}

}
