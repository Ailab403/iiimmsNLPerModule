package ims.subjectTree.service;

import ims.subjectTree.dao.IllegalWordTreeMapper;
import ims.subjectTree.dao.IllegalWordsMapper;
import ims.subjectTree.model.IllegalWordTree;
import ims.subjectTree.model.IllegalWords;
import ims.subjectTree.model.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import net.sf.json.JSONArray;

public class IllegalWordTreeServiceImpl implements IllegalWordTreeService {
	
	private IllegalWordTreeMapper illegalWordTreeMapper;
	private IllegalWordsMapper illegalWordsMapper;
	private IllegalWordsService illegalWordsService;

	// -------- ��ʾ��ҵ�� ---------
	// ������ʾ��ҵ��(����ȫ����ҵ)��json��ʽ�ַ���
	public String formAllBackTreeStr() throws Exception {
		String allBackTreeStr = null;
		try {
			List<Menu> menus = new ArrayList<Menu>();
			List<IllegalWordTree> firstNodes = this.findRootIllegalWordTree();
			if (firstNodes != null && !firstNodes.isEmpty()) { // ����ҵ����
				List<Menu> menulist = new ArrayList<Menu>();
				for (int i = 0; i < firstNodes.size(); i++) {
					int illegalWordParentId;
					IllegalWordTree illegalWordTree = firstNodes.get(i); // ȡ��ÿ����ҵ���ڵ�
					//�õ����дʵ�ID
					illegalWordParentId = illegalWordTree
							.getIllegalWordTreeId();
					
					String illegalWordParentTitle = illegalWordTree
							.getTreeTitle();
					menulist.add(new Menu(illegalWordParentId, illegalWordParentTitle, false, "", null, illegalWordParentTitle));
				}
				List<IllegalWordTree> allNodes = this.findAllIllegalWordTree(); // ���غ�̨��ҵ�������нڵ��б�
				if (allNodes != null)
					for (int i = 0; i < menulist.size(); i++) { // ѭ��ÿ����ҵ���ڵ�
						Menu menu = findChildren(allNodes , menulist.get(i)); 	// ���õݹ鷽������װÿ��menu
						if (menu != null)
							menus.add(menu);
					}
			}
			JSONArray jsonObject = JSONArray.fromObject(menus);
			allBackTreeStr = jsonObject.toString();
			return allBackTreeStr;
		} catch (Exception e) {
			e.printStackTrace();
			return allBackTreeStr;
		}
	}

	// �ݹ飺��menu��װָ�����ڵ�����к��ӽڵ� 
	public Menu findChildren(List<IllegalWordTree> allNodes , Menu parent){
		if (allNodes == null || parent == null) return null;
		int pid = parent.getId();
		for (int i = 0; i < allNodes.size(); i++) {	// ����Ƚ�ÿһ����̨�ڵ�
			IllegalWordTree curNode = allNodes.get(i);
			if (curNode != null) {
				int cid = curNode.getIllegalWordTreeId();
				int cpid = curNode.getParentId();
				String des = curNode.getTreeTitle();
				if (cpid == pid) { // ���ýڵ�ĸ����Ǵ���ڵ�parent�������װ��parent��
						if (parent.getChildren() == null) parent.setChildren(new ArrayList<Menu>());
						parent.getChildren().add(findChildren(allNodes , new Menu(cid, des, false, "", null,des))); // �ݹ�:ȥ��װ�Լ��ĺ���
				}
			}
		}
		return parent;
	}
	// �������к�̨��ҵ�ڵ�Map<�ڵ��ţ��ڵ�>
	public HashMap<Integer,IllegalWordTree> formAllBackNodesMap() throws Exception{
		HashMap<Integer,IllegalWordTree> backNodesMap = null;
		try{
			List<IllegalWordTree> allNodes = this.findAllIllegalWordTree(); // ���غ�̨��ҵ�������нڵ�
			if (allNodes != null) {
				backNodesMap = new HashMap<Integer,IllegalWordTree>();
				for (int i=0; i<allNodes.size(); i++){
					IllegalWordTree frontNode = allNodes.get(i);
					if (frontNode!=null)
						backNodesMap.put(frontNode.getIllegalWordTreeId(),frontNode);
				}
			}
		} catch (Exception e){
			throw(e);
		}
		return backNodesMap;
	}
	
	// ����ָ���ڵ㷵�ع�������ҵ�������ַ���
	public String formBackTreeStrById(int nodeId) throws Exception{
		String backTreeStr = null;
		try{
			IllegalWordTree rootNode = this.listByIllegalWordTreeId(nodeId); // �����ҵ���ڵ�
			if (rootNode != null){	// ���ڵ����
				Menu menu = null;
				int cid = rootNode.getIllegalWordTreeId();
				String treeTitle = rootNode.getTreeTitle();
				menu = new Menu(cid, treeTitle, false, "", null, treeTitle);
				List<IllegalWordTree> allNodes = this.findAllIllegalWordTree(); // ���غ�̨��ҵ�������нڵ�Map
				if (allNodes != null){
					List<Menu> menus = new ArrayList<Menu>();
					Menu menu2 = findChildren(allNodes , menu);	  // ���õݹ鷽����������
					if (menu2 != null)
						menus.add(menu2);
					JSONArray jsonObject = JSONArray.fromObject(menus);
					backTreeStr = jsonObject.toString();
				}
			}
			return backTreeStr;
		} catch (Exception e){
			e.printStackTrace();
			return backTreeStr;
		}
	}
	
	
	// ������ѡ��̨�ڵ������·���� 
	public String wholeNameByBackNodeId(int illegalWordTreeId) throws Exception{
		String wholeName = null;
		try{
			// �ȿ��ýڵ��Ƿ���ڣ����������ҵ��ýڵ�
			IllegalWordTree curNode = this.listByIllegalWordTreeId(illegalWordTreeId); // �ҵ��ýڵ�
			if (curNode != null) { // �ýڵ����
				String curNodePath=curNode.getWholePath();
				StringBuffer pathName=this.getNameFromPath(curNodePath);
				wholeName=pathName.toString();
			}
		} catch (Exception e){
			throw(e);
		}
		return wholeName;
	}
	
	public StringBuffer getNameFromPath(String curNodePath){
		StringBuffer wholeName=new StringBuffer();
		String[] path = curNodePath.split(",");
		for(int i=0;i<path.length;i++){
			String singlePathDeal = path[i];
			 int illeWordId = Integer.parseInt(singlePathDeal.substring(
					singlePathDeal.indexOf("-")+1, singlePathDeal
							.lastIndexOf("-")));
			IllegalWordTree illegalWordTree=this.listByIllegalWordTreeId(illeWordId);
			String treeTitle=illegalWordTree.getTreeTitle();
			wholeName.append(treeTitle);
			if(i==path.length-1){
				break;
			}else {
				wholeName.append("--->");
			}
		}
		return wholeName;
	}
	
	//�����ӽڵ�
	public String addIllegalWordTreeNode(IllegalWordTree illegalWordTree) {
		try{
			this.illegalWordTreeMapper.addIllegalWordTreeNode(illegalWordTree);
		}catch(Exception e){
			e.printStackTrace();
			return "no";
		}
		return "ok";
	}
	
    //��illegalWords�������д�
	public void addKeyWords(IllegalWords iw) {
		this.illegalWordsMapper.insertIllegalWords(iw);
	}
	
	
//	-------- �����޸� ---------	
	// �����޸ĺ�ĺ�̨����ҵ�ڵ�(����������ҵ�ؼ���)
	public boolean saveTradeNode(IllegalWordTree curNode , List<IllegalWords> illegalWordsList) throws Exception{
		try {
			if (curNode == null) return false;
			illegalWordTreeMapper.updateIllegalWordTree(curNode); 	// ������ҵ�ڵ���Ϣ
			// ɾ������ҵ�ڵ����е����йؼ���
			illegalWordsMapper.delIllegalWordsByIllegalWordsId(curNode.getIllegalWordTreeId());
			// ѭ��������еĹؼ���
			if (illegalWordsList != null)
				for (int i=0; i<illegalWordsList.size(); i++){
					IllegalWords iw = illegalWordsList.get(i);
					if (iw != null)
						illegalWordsService.addKeyWords(iw);	// �������д�(��ÿ��IllegalWords�����Զ���������)
				}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public IllegalWordsService getIllegalWordsService() {
		return illegalWordsService;
	}

	public void setIllegalWordsService(IllegalWordsService illegalWordsService) {
		this.illegalWordsService = illegalWordsService;
	}

	// �����޸ĺ�ĺ�̨������ڵ�(���������������)
	public boolean saveSubjectNode(IllegalWordTree curNode) throws Exception{
		//this.status = transactionManager.getTransaction(txDefinition);
		try {
			if (curNode == null) return false;
			illegalWordTreeMapper.updateIllegalWordTree(curNode); 	// ��������ڵ���Ϣ
			// ɾ��������ڵ����е����й���
			//subjectRuleMapper.delRulesBySubjectId(curNode.getSubjectId());
			// ѭ��������еĹ���
			/*if (ruleList != null)
				for (int i=0; i<ruleList.size(); i++){
					SubjectRule sr = ruleList.get(i);
					if (sr != null){
						sr.setSubjectId(curNode.getSubjectId());
						//subjectRuleMapper.addSubjectRule(sr);	// �����������
					}
				}
			transactionManager.commit(status);*/
		} catch (Exception e) {
			//transactionManager.rollback(status);
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
//	-------- �����ж� ---------
	// �ж����޸ĵ���ҵ�����Ƿ��ѱ�������ҵռ��
	public boolean isTradeNameExist(int illegalWordTreeId , String backNodeName) throws Exception{
		try {
			if (backNodeName != null){
				HashSet<String> tradeNames = this.getAllOtherTradeNames(illegalWordTreeId); // ���������ҵ������
				if (tradeNames == null || tradeNames.isEmpty()) return false;	// û��������ҵ����
				if (!tradeNames.contains(backNodeName)) return false;			// ����ҵ������δ��ռ��
			}
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
		return false;
	}
	// ���ص�ǰ����ָ����ҵ�������������ҵ���Ƽ���
	public HashSet<String> getAllOtherTradeNames(int illegalWordTreeId) throws Exception{
		HashSet<String> tradeNames = null;
		try {
			List<String> tradeNameList = illegalWordTreeMapper.getAllOtherIllegalWordTreeTradeNames(illegalWordTreeId);
			if (tradeNameList != null && !tradeNameList.isEmpty()){
				tradeNames = new HashSet<String>();
				for (int i=0; i<tradeNameList.size(); i++)
					tradeNames.add(tradeNameList.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw(e);
		}
		return tradeNames;
	}
	// �ж����޸ĵ���ҵ�ڵ������Ƿ�����ֱ�Ӻ��ӽڵ�������ظ�
	public boolean isNameExistInSubNodes(int illegalWordTreeId,String backNodeName) throws Exception{
		try {
			if (backNodeName != null){
				List<String> existNames = illegalWordTreeMapper.getIllegalWordTreeSubNodeNames(illegalWordTreeId); // ���ֱ�Ӻ��ӵ������б�
				if (existNames == null || existNames.isEmpty()) return false;	// û�к��Ӵ���
				for (int i=0; i<existNames.size(); i++){
					if (existNames.get(i).equalsIgnoreCase(backNodeName))
						return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
		return false;
	}
	// �ж�����ӵ�����ڵ��Ƿ����丸�ڵ㡢�ֵܽڵ�����(�������������ӽڵ�ĸ��ڵ���)
	public boolean isNameExistInParAndBro(int parentId,String backNodeName) throws Exception{
		try {
			if (backNodeName != null){
				List<String> existNames = illegalWordTreeMapper.getIllegalWordTreeSelfAndSubNames(parentId); // ��ø��ڵ㡢�ֵܽڵ��ȫ������
				if (existNames == null || existNames.isEmpty()) return false;	// û�к��Ӵ���
				for (int i=0; i<existNames.size(); i++){
					if (existNames.get(i).equalsIgnoreCase(backNodeName))
						return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
		return false;
	}
	// �ж����޸ĵĽڵ������Ƿ����丸�ڵ㡢�ֵܽڵ㼰��ֱ�Ӻ��ӽڵ���������ظ�
	public boolean isNodeNameExist(int illegalWordTreeId , int parentId, String backNodeName) throws Exception{
		try {
			if (backNodeName != null){
				HashSet<String> existNames = this.get3GNodeNamesById(illegalWordTreeId,parentId); // �������������
				if (existNames == null || existNames.isEmpty()) return false;	// û�������ڵ����
				if (!existNames.contains(backNodeName)) return false;			// ��������δ��ռ��
			}
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
		return true;
	}
	// ָ���ڵ㷵���丸�ڵ㡢�ֵܽڵ㼰��ֱ�Ӻ��ӽڵ�����������б�(�������ýڵ�����)
	public HashSet<String> get3GNodeNamesById(int illegalWordTreeId , int parentId) throws Exception{
		HashSet<String> existNames = null;
		try {
			List<String> existNameList = illegalWordTreeMapper.getIllegalWordTree3GNodeNamesById(illegalWordTreeId,parentId);
			if (existNameList != null && !existNameList.isEmpty()){
				existNames = new HashSet<String>();
				for (int i=0; i<existNameList.size(); i++)
					existNames.add(existNameList.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw(e);
		}
		return existNames;
	}
	public void update(IllegalWordTree illegalWordTree) {
        this.illegalWordTreeMapper.updateIllegalWordTree(illegalWordTree);  		
	}
	
//	-------- ɾ�� ---------	
	// �ڵ�ɾ��ǰԤ�ж�(�ܷ�ɾ��)('��ҵ�ڵ�'��'����ڵ�'ͨ��)
	public int beforeDelCheck(int illegalWordTreeId) throws Exception{
		// �ڵ�ɾ��ԭ��('��ҵ�ڵ�'��'����ڵ�'ͨ��)
		// 1. �ýڵ���ڣ�- ���򷵻� 0
		// 2. ���Ƿ�Ҷ�ӽڵ�(��Ҷ�ӽڵ㲻��ֱ��ɾ��)�� - ���򷵻� 1
		// 3. Ҷ�ӽڵ��ɾ��
		// - ����ɾ�������򷵻� 3
		try{
			IllegalWordTree illegalWordTree=this.listByIllegalWordTreeId(illegalWordTreeId);// ���Ҹýڵ�
			if (illegalWordTree == null) return 0; 						// �ڵ㲻����
			//�������ĺ��ӽڵ�
			List<IllegalWordTree> illeIfExitChild=this.illegalWordTreeMapper.loadIllegalWordTreeDirectChildrenById(illegalWordTreeId);
			// ��Ҷ�ӽڵ㣬����ֱ��ɾ��
			if(illeIfExitChild!=null&& !illeIfExitChild.isEmpty()){
				return 1; 				
			}
		} catch(Exception e){
			e.printStackTrace();
			return 0;
		}
		return 3;	// ����ɾ������
	}
	public boolean delBackNodeById(int illegalWordTreeId) {
		try {
			// ���ҵ��ýڵ�
			IllegalWordTree illegalWordTree=this.listByIllegalWordTreeId(illegalWordTreeId);
			if (illegalWordTree == null) return true;	// �ڵ㲻���ڣ�����Ҫɾ��
				//�Ѹ�·���µ����нڵ��ɾ��
				List<IllegalWordTree> pathId=this.illegalWordTreeMapper.loadIllegalWordTreePathList(illegalWordTreeId);
				for(IllegalWordTree iTree:pathId){
					int iTreeId=iTree.getIllegalWordTreeId();
					String curNodePath=iTree.getWholePath();
					String[] path = curNodePath.split(",");
					for(int i=0;i<path.length;i++){
						String singlePathDeal = path[i];
						 int illeWordId = Integer.parseInt(singlePathDeal.substring(
								singlePathDeal.indexOf("-")+1, singlePathDeal
										.lastIndexOf("-")));
					if(illeWordId>=illegalWordTreeId){
						illegalWordTreeMapper.delIllegalWordTreeNodeById(iTreeId); // ɾ���ڵ�
						illegalWordsMapper.delIllegalWordsByIllegalWordsId(iTreeId);	// ����ɾ����ҵ�ؼ���
					}
					}
			/*} else {							// ����ڵ�
				illegalWordTreeMapper.delBackNodeById(illegalWordTreeId); // ɾ���ڵ�
				// ���������һ�����ӣ���Ҫ�����ڵ��hasChild��Ϊ0
				List<illegalWordTree> children = this.getDirectChildrenById(illegalWordTree.getParentId()); // ��ñ�ɾ���ڵ�������ֵ�
				if (children == null || children.size() == 0)  // �丸�ڵ��Ѿ��޺���
					illegalWordTreeMapper.updateHasChild(illegalWordTree.getParentId(), 0);	// �޸ĸ��ڵ��hasChild�ֶ�ֵ
				illegalWordTreeMapper.deleteSubjectRuleById(illegalWordTreeId);		// ����ɾ���������
*/			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	//ͨ�����д�����illegalWordTreeId�ҵ����д�
	public List<IllegalWords> findIllegalWordsByIllegalWordTreeId(int illegalWordTreeId) {
		return this.illegalWordsMapper.loadIllegalWordsByIllegalWordTreeId(illegalWordTreeId);
	}
	
	public List<IllegalWordTree> findAllIllegalWordTree() {
		
		return this.illegalWordTreeMapper.loadAllIllegalWordTree();
	}

	public List<IllegalWordTree> findRootIllegalWordTree() {
		return this.illegalWordTreeMapper.loadAllRootIllegalWordTree();
	}

	public List<IllegalWordTree> findComboxList() throws Exception {
		return this.illegalWordTreeMapper.loadIllegalWordTreeComboxList();
	}


	public List<IllegalWordTree> loadTreePathList(int id) {
		return this.illegalWordTreeMapper.loadIllegalWordTreePathList(id);
	}

	public IllegalWordTree listByIllegalWordTreeId(int illegalWordTreeId) {
		return this.illegalWordTreeMapper.listByIllegalWordTreeId(illegalWordTreeId);
	}
	

	public IllegalWordTreeMapper getIllegalWordTreeMapper() {
		return illegalWordTreeMapper;
	}

	public void setIllegalWordTreeMapper(
			IllegalWordTreeMapper illegalWordTreeMapper) {
		this.illegalWordTreeMapper = illegalWordTreeMapper;
	}
	public IllegalWordsMapper getIllegalWordsMapper() {
		return illegalWordsMapper;
	}

	public void setIllegalWordsMapper(IllegalWordsMapper illegalWordsMapper) {
		this.illegalWordsMapper = illegalWordsMapper;
	}


}