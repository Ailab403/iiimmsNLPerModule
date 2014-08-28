package ims.subjectTree.service;


import ims.subjectTree.model.IllegalWordTree;
import ims.subjectTree.model.IllegalWords;

import java.util.HashSet;
import java.util.List;

/**
 * 
 * @author dd
 */
public interface IllegalWordTreeService {
//	-------------------------------- dd------------------------------------------------
//	-------- 显示行业树 ---------
	// 返回显示行业树(包含全部行业)的json格式字符串
	String formAllBackTreeStr() throws Exception;
	// 返回后台行业树的所有节点列表
	List<IllegalWordTree> findAllIllegalWordTree();
	// 返回后台树所有根节点(行业节点)列表
	List<IllegalWordTree> findRootIllegalWordTree();
	
	// 获得行业树下拉框需要的数据(badWordTreeId,treeTitle)
	List<IllegalWordTree> findComboxList() throws Exception;
	// 根据指定节点返回构成其行业子树的字符串
	String formBackTreeStrById(int nodeId) throws Exception;
	
	//通过路径path里的一个数like查找
	List<IllegalWordTree> loadTreePathList(int id);
	
	IllegalWordTree listByIllegalWordTreeId(int illegalWordTreeId);
	
	// 返回所选后台节点的完整路径名
	String wholeNameByBackNodeId(int backNodeId) throws Exception;
	
	// 新增一后台敏感词树节点
	String addIllegalWordTreeNode(IllegalWordTree illegalWordTree);
	
	
	
	//根据illegalWordTreeId返回关键词
	List<IllegalWords> findIllegalWordsByIllegalWordTreeId(int illegalWordTreeId);
	
	//添加行业关键词
	void addKeyWords(IllegalWords iw);
	
	//更新IllegalWordTree
	void update(IllegalWordTree illegalWordTree);
	
//	-------- 重名判断 ---------
	// 判断新修改的行业名称是否已被其他行业占用
	boolean isTradeNameExist(int backNodeId , String backNodeName) throws Exception;
	// 返回当前除了指定行业外的其他所有行业名称集合
	HashSet<String> getAllOtherTradeNames(int backNodeId) throws Exception;
	// 判断新修改的行业节点名字是否与其直接孩子节点的名字重复
	boolean isNameExistInSubNodes(int backNodeId,String backNodeName) throws Exception;
	// 判断新修改的节点名称是否与其父节点、兄弟节点及其直接孩子节点的名字有重复
	boolean isNodeNameExist(int backNodeId , int parentId, String backNodeName) throws Exception;
	// 判断新添加的主题节点是否与其父节点、兄弟节点重名(传入参数是新添加节点的父节点编号)
	boolean isNameExistInParAndBro(int parentId,String backNodeName) throws Exception;
	// 指定节点返回其父节点、兄弟节点及其直接孩子节点的所有名字列表(不包含该节点自身)
	HashSet<String> get3GNodeNamesById(int backNodeId , int parentId) throws Exception;
	
	
//	-------- 保存修改 ---------	
	// 保存修改后的后台树行业节点(级联保存行业关键词)
	boolean saveTradeNode(IllegalWordTree curNode , List<IllegalWords> illegalWordsList) throws Exception;
	// 保存修改后的后台树主题节点(级联保存主题规则)
	boolean saveSubjectNode(IllegalWordTree curNode) throws Exception;
	
//	-------- 删除 ---------	
	// 节点删除前预判断(能否删除)('行业节点'与'主题节点'通用)
	int beforeDelCheck(int backNodeId) throws Exception;
	// 删除指定的后台树节点
	boolean delBackNodeById(int illegalWordTreeId);
	
}
