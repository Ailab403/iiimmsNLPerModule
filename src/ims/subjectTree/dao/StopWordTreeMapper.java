package ims.subjectTree.dao;

import ims.subjectTree.model.StopWordTree;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StopWordTreeMapper {
//	------------------------------- dd-------------------------------------------------
	// ���غ�̨��ҵ�������нڵ��б�
	List<StopWordTree> loadAllStopWordTree();
	
	// ���غ�̨�����и��ڵ�(��ҵ�ڵ�)�б�
	List<StopWordTree> loadAllStopWordTreeRoot();
	// �����ҵ����������Ҫ������(stopWordTreeId,treeTitle)
	List<StopWordTree> loadStopWordTreeComboxList();
	
	//ͨ��·��path���һ����like����
	List<StopWordTree> loadStopWordTreePathList(int id);
	
	StopWordTree listByStopWordTreeId(int stopWordTreeId);
	
	// ����һ��̨��ͣ���ڵ�
	void addStopWordTreeNode(StopWordTree stopWordTree);
	
	//����StopWordTree
	void updateStopWordTree(StopWordTree stopWordTree);
	
//	------ �����ж� -------
	// ���ص�ǰ����ָ����ҵ�������������ҵ���Ƽ���
	List<String> getStopWordTreeAllOtherTradeNames(int backNodeId);
	// ����ָ���ڵ��ֱ���ӽڵ�����������б�
	List<String> getStopWordTreeSubNodeNames(int nodeId);
	// ����ָ���ڵ㼰��ֱ���ӽڵ�����������б�
	List<String> getStopWordTreeSelfAndSubNames(int nodeId);
	
	// ָ���ڵ㷵���丸�ڵ㡢�ֵܽڵ㼰��ֱ�Ӻ��ӽڵ�����������б�(�������ýڵ�����)
	List<String> getStopWordTree3GNodeNamesById(@Param(value="backNodeId")int backNodeId , @Param(value="parentId")int parentId) throws Exception;
	
	// ɾ��ָ���ĺ�̨���ڵ�
	void delStopWordTreeNodeById(int stopWordTreeId);
	
	// ����ָ����ŵĽڵ��ֱ�Ӻ����б�
	List<StopWordTree> loadStopWordTreeDirectChildrenById(int parentId);

}
