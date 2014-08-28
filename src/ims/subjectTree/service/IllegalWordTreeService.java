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
//	-------- ��ʾ��ҵ�� ---------
	// ������ʾ��ҵ��(����ȫ����ҵ)��json��ʽ�ַ���
	String formAllBackTreeStr() throws Exception;
	// ���غ�̨��ҵ�������нڵ��б�
	List<IllegalWordTree> findAllIllegalWordTree();
	// ���غ�̨�����и��ڵ�(��ҵ�ڵ�)�б�
	List<IllegalWordTree> findRootIllegalWordTree();
	
	// �����ҵ����������Ҫ������(badWordTreeId,treeTitle)
	List<IllegalWordTree> findComboxList() throws Exception;
	// ����ָ���ڵ㷵�ع�������ҵ�������ַ���
	String formBackTreeStrById(int nodeId) throws Exception;
	
	//ͨ��·��path���һ����like����
	List<IllegalWordTree> loadTreePathList(int id);
	
	IllegalWordTree listByIllegalWordTreeId(int illegalWordTreeId);
	
	// ������ѡ��̨�ڵ������·����
	String wholeNameByBackNodeId(int backNodeId) throws Exception;
	
	// ����һ��̨���д����ڵ�
	String addIllegalWordTreeNode(IllegalWordTree illegalWordTree);
	
	
	
	//����illegalWordTreeId���عؼ���
	List<IllegalWords> findIllegalWordsByIllegalWordTreeId(int illegalWordTreeId);
	
	//�����ҵ�ؼ���
	void addKeyWords(IllegalWords iw);
	
	//����IllegalWordTree
	void update(IllegalWordTree illegalWordTree);
	
//	-------- �����ж� ---------
	// �ж����޸ĵ���ҵ�����Ƿ��ѱ�������ҵռ��
	boolean isTradeNameExist(int backNodeId , String backNodeName) throws Exception;
	// ���ص�ǰ����ָ����ҵ�������������ҵ���Ƽ���
	HashSet<String> getAllOtherTradeNames(int backNodeId) throws Exception;
	// �ж����޸ĵ���ҵ�ڵ������Ƿ�����ֱ�Ӻ��ӽڵ�������ظ�
	boolean isNameExistInSubNodes(int backNodeId,String backNodeName) throws Exception;
	// �ж����޸ĵĽڵ������Ƿ����丸�ڵ㡢�ֵܽڵ㼰��ֱ�Ӻ��ӽڵ���������ظ�
	boolean isNodeNameExist(int backNodeId , int parentId, String backNodeName) throws Exception;
	// �ж�����ӵ�����ڵ��Ƿ����丸�ڵ㡢�ֵܽڵ�����(�������������ӽڵ�ĸ��ڵ���)
	boolean isNameExistInParAndBro(int parentId,String backNodeName) throws Exception;
	// ָ���ڵ㷵���丸�ڵ㡢�ֵܽڵ㼰��ֱ�Ӻ��ӽڵ�����������б�(�������ýڵ�����)
	HashSet<String> get3GNodeNamesById(int backNodeId , int parentId) throws Exception;
	
	
//	-------- �����޸� ---------	
	// �����޸ĺ�ĺ�̨����ҵ�ڵ�(����������ҵ�ؼ���)
	boolean saveTradeNode(IllegalWordTree curNode , List<IllegalWords> illegalWordsList) throws Exception;
	// �����޸ĺ�ĺ�̨������ڵ�(���������������)
	boolean saveSubjectNode(IllegalWordTree curNode) throws Exception;
	
//	-------- ɾ�� ---------	
	// �ڵ�ɾ��ǰԤ�ж�(�ܷ�ɾ��)('��ҵ�ڵ�'��'����ڵ�'ͨ��)
	int beforeDelCheck(int backNodeId) throws Exception;
	// ɾ��ָ���ĺ�̨���ڵ�
	boolean delBackNodeById(int illegalWordTreeId);
	
}
