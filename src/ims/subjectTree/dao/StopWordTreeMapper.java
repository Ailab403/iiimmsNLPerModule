package ims.subjectTree.dao;

import ims.subjectTree.model.StopWordTree;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StopWordTreeMapper {
//	------------------------------- dd-------------------------------------------------
	// 返回后台行业树的所有节点列表
	List<StopWordTree> loadAllStopWordTree();
	
	// 返回后台树所有根节点(行业节点)列表
	List<StopWordTree> loadAllStopWordTreeRoot();
	// 获得行业树下拉框需要的数据(stopWordTreeId,treeTitle)
	List<StopWordTree> loadStopWordTreeComboxList();
	
	//通过路径path里的一个数like查找
	List<StopWordTree> loadStopWordTreePathList(int id);
	
	StopWordTree listByStopWordTreeId(int stopWordTreeId);
	
	// 新增一后台暂停树节点
	void addStopWordTreeNode(StopWordTree stopWordTree);
	
	//更新StopWordTree
	void updateStopWordTree(StopWordTree stopWordTree);
	
//	------ 重名判断 -------
	// 返回当前除了指定行业外的其他所有行业名称集合
	List<String> getStopWordTreeAllOtherTradeNames(int backNodeId);
	// 返回指定节点的直接子节点的所有名字列表
	List<String> getStopWordTreeSubNodeNames(int nodeId);
	// 返回指定节点及其直接子节点的所有名字列表
	List<String> getStopWordTreeSelfAndSubNames(int nodeId);
	
	// 指定节点返回其父节点、兄弟节点及其直接孩子节点的所有名字列表(不包含该节点自身)
	List<String> getStopWordTree3GNodeNamesById(@Param(value="backNodeId")int backNodeId , @Param(value="parentId")int parentId) throws Exception;
	
	// 删除指定的后台树节点
	void delStopWordTreeNodeById(int stopWordTreeId);
	
	// 返回指定编号的节点的直接孩子列表
	List<StopWordTree> loadStopWordTreeDirectChildrenById(int parentId);

}
