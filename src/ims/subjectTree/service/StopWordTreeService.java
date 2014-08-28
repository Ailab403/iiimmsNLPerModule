package ims.subjectTree.service;

import ims.subjectTree.model.StopWordTree;
import ims.subjectTree.model.StopWords;

import java.util.HashSet;
import java.util.List;

public interface StopWordTreeService {
	
//	-------- ��ʾ��ҵ�� ---------
	// ������ʾ��ҵ��(����ȫ����ҵ)��json��ʽ�ַ���
	String formAllStopWordTreeStr() throws Exception;
	// ���غ�̨��ҵ�������нڵ��б�
	List<StopWordTree> findAllStopWordTree();
	// ���غ�̨�����и��ڵ�(��ҵ�ڵ�)�б�
	List<StopWordTree> findAllRootStopWordTrees();

	// �����ҵ����������Ҫ������(stopWordTreeId,treeTitle)
	List<StopWordTree> findComboxList() throws Exception;
	// ����ָ���ڵ㷵�ع�������ҵ�������ַ���
	String formStopWordTreeStrById(int nodeId) throws Exception;
	
	//ͨ��·��path���һ����like����
	List<StopWordTree> getTreePathList(int id);
	
	StopWordTree listByStopWordTreeId(int stopWordTreeId);
	
	// ������ѡ��̨�ڵ������·����
	String wholeNameByStopWordNodeId(int backNodeId) throws Exception;
	
	// ����һ��̨���д����ڵ�
	String addStopWordTreeNode(StopWordTree illegalWordTree);
	
	
	
	//����illegalWordTreeId���عؼ���
	List<StopWords> findStopWordsByStopWordTreeId(int stopWordTreeId);
	
	//�����ҵ�ؼ���
	void addKeyWords(StopWords iw);
	
	//����IllegalWordTree
	void update(StopWordTree illegalWordTree);
	
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
	boolean saveTradeNode(StopWordTree curNode , List<StopWords> illegalWordsList) throws Exception;
	// �����޸ĺ�ĺ�̨������ڵ�(���������������)
	boolean saveSubjectNode(StopWordTree curNode) throws Exception;
	
//	-------- ɾ�� ---------	
	// �ڵ�ɾ��ǰԤ�ж�(�ܷ�ɾ��)('��ҵ�ڵ�'��'����ڵ�'ͨ��)
	int beforeDelCheck(int backNodeId) throws Exception;
	// ɾ��ָ���ĺ�̨���ڵ�
	
	// ɾ��ָ���ĺ�̨���ڵ�
	boolean delStopWordNodeById(int stopWordTreeId);
	
//	-------- �ѱ����ݸ��µ��ı� ---------		
	String updateToTxt();

}
