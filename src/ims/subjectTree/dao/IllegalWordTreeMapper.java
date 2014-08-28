package ims.subjectTree.dao;

import ims.subjectTree.model.IllegalWordTree;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IllegalWordTreeMapper {
//	------------------------------- dd-------------------------------------------------
	// 返回后台行业树的所有节点列表
	List<IllegalWordTree> loadAllIllegalWordTree();
	
	// 返回后台行业树的所有illegalWordTreeId列表
	//List<Integer> loadAllIllegalWordTreeIds();
	
	// 返回后台树所有根节点(行业节点)列表
	List<IllegalWordTree> loadAllRootIllegalWordTree();
	// 获得行业树下拉框需要的数据(illegalWordTreeId,treeTitle)
	List<IllegalWordTree> loadIllegalWordTreeComboxList();
	
	//通过路径path里的一个数like查找
	List<IllegalWordTree> loadIllegalWordTreePathList(int id);
	
	IllegalWordTree listByIllegalWordTreeId(int illegalWordTreeId);
	
	// 新增一后台敏感词树节点
	void addIllegalWordTreeNode(IllegalWordTree illegalWordTree);
	
	// 返回指定ID的相应后台树节点
	//IllegalWordTree getBackNodeById(int illegalWordTreeId) throws Exception;

	
	//更新IllegalWordTree
	void updateIllegalWordTree(IllegalWordTree illegalWordTree);
	
//	------ 重名判断 -------
	// 返回当前除了指定行业外的其他所有行业名称集合
	List<String> getAllOtherIllegalWordTreeTradeNames(int illegalWordTreeId);
	// 返回指定节点的直接子节点的所有名字列表
	List<String> getIllegalWordTreeSubNodeNames(int nodeId);
	// 返回指定节点及其直接子节点的所有名字列表
	List<String> getIllegalWordTreeSelfAndSubNames(int nodeId);
	
	// 指定节点返回其父节点、兄弟节点及其直接孩子节点的所有名字列表(不包含该节点自身)
	List<String> getIllegalWordTree3GNodeNamesById(@Param(value="backNodeId")int backNodeId , @Param(value="parentId")int parentId) throws Exception;
	
	// 删除指定的后台树节点
	void delIllegalWordTreeNodeById(int illegalWordTreeId);
	
	// 返回指定编号的节点的直接孩子列表
	List<IllegalWordTree> loadIllegalWordTreeDirectChildrenById(int parentId);
	
}
