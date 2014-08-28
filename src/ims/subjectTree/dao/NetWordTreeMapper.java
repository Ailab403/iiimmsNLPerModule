package ims.subjectTree.dao;

import ims.subjectTree.model.NetWordTree;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface NetWordTreeMapper {
//	------------------------------- dd-------------------------------------------------
	// 返回后台行业树的所有节点列表
	List<NetWordTree> loadAllNetWordTree();
	
	// 返回后台树所有根节点(行业节点)列表
	List<NetWordTree> loadAllRootNetWordTree();
	// 获得行业树下拉框需要的数据(netWordTreeId,treeTitle)
	List<NetWordTree> loadNetWordTreeComboxList();
	
	//通过路径path里的一个数like查找
	List<NetWordTree> loadNetWordTreePathList(int id);
	
	NetWordTree listByNetWordTreeId(int netWordTreeId);
	
	// 新增一后台敏感词树节点
	void addNetWordTreeNode(NetWordTree netWordTree);
	
	//更新NetWordTree
	void updateNetWordTree(NetWordTree netWordTree);
	
//	------ 重名判断 -------
	// 返回当前除了指定行业外的其他所有行业名称集合
	List<String> getNetWordTreeAllOtherTradeNames(int backNodeId);
	// 返回指定节点的直接子节点的所有名字列表
	List<String> getNetWordTreeSubNodeNames(int nodeId);
	// 返回指定节点及其直接子节点的所有名字列表
	List<String> getNetWordTreeSelfAndSubNames(int nodeId);
	
	// 指定节点返回其父节点、兄弟节点及其直接孩子节点的所有名字列表(不包含该节点自身)
	List<String> getNetWordTree3GNodeNamesById(@Param(value="backNodeId")int backNodeId , @Param(value="parentId")int parentId) throws Exception;
	
	// 删除指定的后台树节点
	void delNetWordTreeNodeById(int netWordTreeId);
	
	// 返回指定编号的节点的直接孩子列表
	List<NetWordTree> getNetWordTreeDirectChildrenById(int parentId);

}
