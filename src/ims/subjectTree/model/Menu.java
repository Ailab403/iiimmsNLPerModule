package ims.subjectTree.model;

import java.util.List;

public class Menu {
     private int id;
     private String text;
     private boolean leaf;
     private String cls;
     private List<Menu> children;
     private String qtip;
     
     
 	public Menu(int id, String text, boolean leaf, String cls,
			List<Menu> children, String qtip) {
		super();
		this.id = id;
		this.text = text;
		this.leaf = leaf;
		this.cls = cls;
		this.children = children;
		this.qtip = qtip;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public boolean isLeaf() {
		return leaf;
	}


	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}


	public String getCls() {
		return cls;
	}


	public void setCls(String cls) {
		this.cls = cls;
	}


	public List<Menu> getChildren() {
		return children;
	}


	public void setChildren(List<Menu> children) {
		this.children = children;
	}


	public String getQtip() {
		return qtip;
	}


	public void setQtip(String qtip) {
		this.qtip = qtip;
	}
 	
     
}
