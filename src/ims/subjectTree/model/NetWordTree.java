package ims.subjectTree.model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * ÍøÂç´ÊÕ¹Ê¾Ê÷
 * @author DD
 *
 */
public class NetWordTree {
	
	private int netWordTreeId;
	private String treeTitle;
	private int parentId;
	private String wholePath;
	private Timestamp createTime;
	private String treeExp;
	
	public NetWordTree() {
		super();
	}
	public NetWordTree(int netWordTreeId, String treeTitle, int parentId,
			String wholePath, Timestamp createTime, String treeExp) {
		super();
		this.netWordTreeId = netWordTreeId;
		this.treeTitle = treeTitle;
		this.parentId = parentId;
		this.wholePath = wholePath;
		this.createTime = createTime;
		this.treeExp = treeExp;
	}
	public int getNetWordTreeId() {
		return netWordTreeId;
	}
	public void setNetWordTreeId(int netWordTreeId) {
		this.netWordTreeId = netWordTreeId;
	}
	public String getTreeTitle() {
		return treeTitle;
	}
	public void setTreeTitle(String treeTitle) {
		this.treeTitle = treeTitle;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getWholePath() {
		return wholePath;
	}
	public void setWholePath(String wholePath) {
		this.wholePath = wholePath;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getTreeExp() {
		return treeExp;
	}
	public void setTreeExp(String treeExp) {
		this.treeExp = treeExp;
	}
	
	

}
