package ims.subjectTree.model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Ãô¸Ð´ÊÕ¹Ê¾Ê÷
 * @author DD
 *
 */
public class IllegalWordTree {
	private static final long serialVersionUID= -4794151864336777944L;
	private int illegalWordTreeId;
	private String treeTitle;
	private int parentId;;
	private String wholePath;
	private Timestamp createTime;
	private String treeExp;
	
	public IllegalWordTree() {
		super();
	}

	public IllegalWordTree(int illegalWordTreeId, String treeTitle,
			int parentId, String wholePath, Timestamp createTime, String treeExp) {
		super();
		this.illegalWordTreeId = illegalWordTreeId;
		this.treeTitle = treeTitle;
		this.parentId = parentId;
		this.wholePath = wholePath;
		this.createTime = createTime;
		this.treeExp = treeExp;
	}



	public int getIllegalWordTreeId() {
		return illegalWordTreeId;
	}



	public void setIllegalWordTreeId(int illegalWordTreeId) {
		this.illegalWordTreeId = illegalWordTreeId;
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
