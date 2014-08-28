package ims.subjectTree.dao;

import ims.subjectTree.model.NetWordTree;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface NetWordTreeMapper {
//	------------------------------- dd-------------------------------------------------
	// ���غ�̨��ҵ�������нڵ��б�
	List<NetWordTree> loadAllNetWordTree();
	
	// ���غ�̨�����и��ڵ�(��ҵ�ڵ�)�б�
	List<NetWordTree> loadAllRootNetWordTree();
	// �����ҵ����������Ҫ������(netWordTreeId,treeTitle)
	List<NetWordTree> loadNetWordTreeComboxList();
	
	//ͨ��·��path���һ����like����
	List<NetWordTree> loadNetWordTreePathList(int id);
	
	NetWordTree listByNetWordTreeId(int netWordTreeId);
	
	// ����һ��̨���д����ڵ�
	void addNetWordTreeNode(NetWordTree netWordTree);
	
	//����NetWordTree
	void updateNetWordTree(NetWordTree netWordTree);
	
//	------ �����ж� -------
	// ���ص�ǰ����ָ����ҵ�������������ҵ���Ƽ���
	List<String> getNetWordTreeAllOtherTradeNames(int backNodeId);
	// ����ָ���ڵ��ֱ���ӽڵ�����������б�
	List<String> getNetWordTreeSubNodeNames(int nodeId);
	// ����ָ���ڵ㼰��ֱ���ӽڵ�����������б�
	List<String> getNetWordTreeSelfAndSubNames(int nodeId);
	
	// ָ���ڵ㷵���丸�ڵ㡢�ֵܽڵ㼰��ֱ�Ӻ��ӽڵ�����������б�(�������ýڵ�����)
	List<String> getNetWordTree3GNodeNamesById(@Param(value="backNodeId")int backNodeId , @Param(value="parentId")int parentId) throws Exception;
	
	// ɾ��ָ���ĺ�̨���ڵ�
	void delNetWordTreeNodeById(int netWordTreeId);
	
	// ����ָ����ŵĽڵ��ֱ�Ӻ����б�
	List<NetWordTree> getNetWordTreeDirectChildrenById(int parentId);

}
