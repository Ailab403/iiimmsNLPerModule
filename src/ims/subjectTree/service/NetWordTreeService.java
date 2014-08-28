package ims.subjectTree.service;

import ims.subjectTree.model.NetWordTree;
import ims.subjectTree.model.NetWords;

import java.util.HashSet;
import java.util.List;

public interface NetWordTreeService {
	
	// ������ʾ��ҵ��(����ȫ����ҵ)��json��ʽ�ַ���
	String formAllNetWordTreeStr() throws Exception;
	// ���غ�̨��ҵ�������нڵ��б�
	List<NetWordTree> findAllNetWordTree();
	// ���غ�̨�����и��ڵ�(����)�б�
	List<NetWordTree> findAllRootNetWordTree();

	// �����ҵ����������Ҫ������(netWordTreeId,treeTitle)
	List<NetWordTree> findComboxList() throws Exception;
	// ����ָ���ڵ㷵�ع�������ҵ�������ַ���
	String formNetWordTreeStrById(int nodeId) throws Exception;
	
	//ͨ��·��path���һ����like����
	List<NetWordTree> loadTreePathList(int id);
	
	NetWordTree listByNetWordTreeId(int netWordTreeId);
	
	// ������ѡ��̨�ڵ������·����
	String wholeNameByNetNodeId(int netWordTreeId) throws Exception;
	
	// ����һ��̨���д����ڵ�
	String addNetWordTreeNode(NetWordTree netWordTree);
	
	
	
	//����netWordTreeId���عؼ���
	List<NetWords> findNetWordsByNetWordTreeId(int netWordTreeId);
	
	//�����ҵ�ؼ���
	void addNetWords(NetWords iw);
	
	//����netWordTree
	void update(NetWordTree netWordTree);
	
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
	boolean saveTradeNode(NetWordTree curNode , List<NetWords> illegalWordsList) throws Exception;
	// �����޸ĺ�ĺ�̨������ڵ�(���������������)
	boolean saveSubjectNode(NetWordTree curNode) throws Exception;
	
//	-------- ɾ�� ---------	
	// �ڵ�ɾ��ǰԤ�ж�(�ܷ�ɾ��)
	int beforeDelCheck(int backNodeId) throws Exception;
	// ɾ��ָ���ĺ�̨���ڵ�
	boolean delBackNodeById(int netWordTreeId);
//	-------- �ѱ����ݸ��µ��ı� ---------		
	String updateToTxt();
	
}
