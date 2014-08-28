package ims.subjectTree.dao;

import ims.subjectTree.model.IllegalWordTree;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IllegalWordTreeMapper {
//	------------------------------- dd-------------------------------------------------
	// ���غ�̨��ҵ�������нڵ��б�
	List<IllegalWordTree> loadAllIllegalWordTree();
	
	// ���غ�̨��ҵ��������illegalWordTreeId�б�
	//List<Integer> loadAllIllegalWordTreeIds();
	
	// ���غ�̨�����и��ڵ�(��ҵ�ڵ�)�б�
	List<IllegalWordTree> loadAllRootIllegalWordTree();
	// �����ҵ����������Ҫ������(illegalWordTreeId,treeTitle)
	List<IllegalWordTree> loadIllegalWordTreeComboxList();
	
	//ͨ��·��path���һ����like����
	List<IllegalWordTree> loadIllegalWordTreePathList(int id);
	
	IllegalWordTree listByIllegalWordTreeId(int illegalWordTreeId);
	
	// ����һ��̨���д����ڵ�
	void addIllegalWordTreeNode(IllegalWordTree illegalWordTree);
	
	// ����ָ��ID����Ӧ��̨���ڵ�
	//IllegalWordTree getBackNodeById(int illegalWordTreeId) throws Exception;

	
	//����IllegalWordTree
	void updateIllegalWordTree(IllegalWordTree illegalWordTree);
	
//	------ �����ж� -------
	// ���ص�ǰ����ָ����ҵ�������������ҵ���Ƽ���
	List<String> getAllOtherIllegalWordTreeTradeNames(int illegalWordTreeId);
	// ����ָ���ڵ��ֱ���ӽڵ�����������б�
	List<String> getIllegalWordTreeSubNodeNames(int nodeId);
	// ����ָ���ڵ㼰��ֱ���ӽڵ�����������б�
	List<String> getIllegalWordTreeSelfAndSubNames(int nodeId);
	
	// ָ���ڵ㷵���丸�ڵ㡢�ֵܽڵ㼰��ֱ�Ӻ��ӽڵ�����������б�(�������ýڵ�����)
	List<String> getIllegalWordTree3GNodeNamesById(@Param(value="backNodeId")int backNodeId , @Param(value="parentId")int parentId) throws Exception;
	
	// ɾ��ָ���ĺ�̨���ڵ�
	void delIllegalWordTreeNodeById(int illegalWordTreeId);
	
	// ����ָ����ŵĽڵ��ֱ�Ӻ����б�
	List<IllegalWordTree> loadIllegalWordTreeDirectChildrenById(int parentId);
	
}
