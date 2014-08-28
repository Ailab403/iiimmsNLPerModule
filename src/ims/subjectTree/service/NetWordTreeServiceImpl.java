package ims.subjectTree.service;

import ims.nlp.cache.NlpDiyWordsPath;
import ims.subjectTree.dao.NetWordTreeMapper;
import ims.subjectTree.dao.NetWordsMapper;
import ims.subjectTree.model.Menu;
import ims.subjectTree.model.NetWordTree;
import ims.subjectTree.model.NetWords;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NetWordTreeServiceImpl implements NetWordTreeService {
	
	private NetWordTreeMapper netWordTreeMapper;
	private NetWordsMapper netWordsMapper;
	private NetWordsService netWordsService;

	// -------- ��ʾ��ҵ�� ---------
	// ������ʾ��ҵ��(����ȫ����ҵ)��json��ʽ�ַ���
	public String formAllNetWordTreeStr() throws Exception {
		String allBackTreeStr = null;
		try {
			List<Menu> menus = new ArrayList<Menu>();
			List<NetWordTree> firstNodes = this.findAllRootNetWordTree();
			if (firstNodes != null && !firstNodes.isEmpty()) { // ����ҵ����
				List<Menu> menulist = new ArrayList<Menu>();
				for (int i = 0; i < firstNodes.size(); i++) {
					int illegalWordParentId;
					NetWordTree illegalWordTree = firstNodes.get(i); // ȡ��ÿ����ҵ���ڵ�
					//�õ����дʵ�ID
					illegalWordParentId = illegalWordTree
							.getNetWordTreeId();
					
					String illegalWordParentTitle = illegalWordTree
							.getTreeTitle();
					menulist.add(new Menu(illegalWordParentId, illegalWordParentTitle, false, "", null, illegalWordParentTitle));
				}
				List<NetWordTree> allNodes = this.findAllNetWordTree(); // ���غ�̨��ҵ�������нڵ��б�
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
	public Menu findChildren(List<NetWordTree> allNodes , Menu parent){
		if (allNodes == null || parent == null) return null;
		int pid = parent.getId();
		for (int i = 0; i < allNodes.size(); i++) {	// ����Ƚ�ÿһ����̨�ڵ�
			NetWordTree curNode = allNodes.get(i);
			if (curNode != null) {
				int cid = curNode.getNetWordTreeId();
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
	public HashMap<Integer,NetWordTree> formAllBackNodesMap() throws Exception{
		HashMap<Integer,NetWordTree> backNodesMap = null;
		try{
			List<NetWordTree> allNodes = this.findAllNetWordTree(); // ���غ�̨��ҵ�������нڵ�
			if (allNodes != null) {
				backNodesMap = new HashMap<Integer,NetWordTree>();
				for (int i=0; i<allNodes.size(); i++){
					NetWordTree frontNode = allNodes.get(i);
					if (frontNode!=null)
						backNodesMap.put(frontNode.getNetWordTreeId(),frontNode);
				}
			}
		} catch (Exception e){
			throw(e);
		}
		return backNodesMap;
	}
	
	// ����ָ���ڵ㷵�ع�������ҵ�������ַ���
	public String formNetWordTreeStrById(int nodeId) throws Exception{
		String backTreeStr = null;
		try{
			NetWordTree rootNode = this.listByNetWordTreeId(nodeId); // �����ҵ���ڵ�
			if (rootNode != null){	// ���ڵ����
				Menu menu = null;
				int cid = rootNode.getNetWordTreeId();
				String treeTitle = rootNode.getTreeTitle();
				menu = new Menu(cid, treeTitle, false, "", null, treeTitle);
				List<NetWordTree> allNodes = this.findAllNetWordTree(); // ���غ�̨��ҵ�������нڵ�Map
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
	public String wholeNameByNetNodeId(int netWordTreeId) throws Exception{
		String wholeName = null;
		try{
			// �ȿ��ýڵ��Ƿ���ڣ����������ҵ��ýڵ�
			NetWordTree curNode = this.listByNetWordTreeId(netWordTreeId); // �ҵ��ýڵ�
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
			NetWordTree illegalWordTree=this.listByNetWordTreeId(illeWordId);
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
	public String addNetWordTreeNode(NetWordTree illegalWordTree) {
		try{
			this.netWordTreeMapper.addNetWordTreeNode(illegalWordTree);
		}catch(Exception e){
			e.printStackTrace();
			return "no";
		}
		return "ok";
	}
	
    //��NetWords�������д�
	public void addNetWords(NetWords iw) {
		this.netWordsMapper.insertNetWords(iw);
	}
	
	
//	-------- �����޸� ---------	
	// �����޸ĺ�ĺ�̨����ҵ�ڵ�(����������ҵ�ؼ���)
	public boolean saveTradeNode(NetWordTree curNode , List<NetWords> illegalWordsList) throws Exception{
		try {
			if (curNode == null) return false;
			netWordTreeMapper.updateNetWordTree(curNode); 	// ������ҵ�ڵ���Ϣ
			// ɾ������ҵ�ڵ����е����йؼ���
			netWordsMapper.delNetWordsByNetWordTreeId(curNode.getNetWordTreeId());
			// ѭ��������еĹؼ���
			if (illegalWordsList != null)
				for (int i=0; i<illegalWordsList.size(); i++){
					NetWords iw = illegalWordsList.get(i);
					if (iw != null)
						netWordsService.addNetWords(iw);	// �������д�(��ÿ��NetWords�����Զ���������)
				}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	// �����޸ĺ�ĺ�̨������ڵ�(���������������)
	public boolean saveSubjectNode(NetWordTree curNode) throws Exception{
		try {
			if (curNode == null) return false;
			netWordTreeMapper.updateNetWordTree(curNode); 	// ��������ڵ���Ϣ
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
//	-------- �����ж� ---------
	// �ж����޸ĵ���ҵ�����Ƿ��ѱ�������ҵռ��
	public boolean isTradeNameExist(int netWordTreeId , String backNodeName) throws Exception{
		try {
			if (backNodeName != null){
				HashSet<String> tradeNames = this.getAllOtherTradeNames(netWordTreeId); // ���������ҵ������
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
	public HashSet<String> getAllOtherTradeNames(int netWordTreeId) throws Exception{
		HashSet<String> tradeNames = null;
		try {
			List<String> tradeNameList = netWordTreeMapper.getNetWordTreeAllOtherTradeNames(netWordTreeId);
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
	public boolean isNameExistInSubNodes(int netWordTreeId,String backNodeName) throws Exception{
		try {
			if (backNodeName != null){
				List<String> existNames = netWordTreeMapper.getNetWordTreeSubNodeNames(netWordTreeId); // ���ֱ�Ӻ��ӵ������б�
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
				List<String> existNames = netWordTreeMapper.getNetWordTreeSelfAndSubNames(parentId); // ��ø��ڵ㡢�ֵܽڵ��ȫ������
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
	public boolean isNodeNameExist(int netWordTreeId , int parentId, String backNodeName) throws Exception{
		try {
			if (backNodeName != null){
				HashSet<String> existNames = this.get3GNodeNamesById(netWordTreeId,parentId); // �������������
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
	public HashSet<String> get3GNodeNamesById(int netWordTreeId , int parentId) throws Exception{
		HashSet<String> existNames = null;
		try {
			List<String> existNameList = netWordTreeMapper.getNetWordTree3GNodeNamesById(netWordTreeId,parentId);
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
	public void update(NetWordTree illegalWordTree) {
        this.netWordTreeMapper.updateNetWordTree(illegalWordTree);  		
	}
	
//	-------- ɾ�� ---------	
	// �ڵ�ɾ��ǰԤ�ж�(�ܷ�ɾ��)('��ҵ�ڵ�'��'����ڵ�'ͨ��)
	public int beforeDelCheck(int netWordTreeId) throws Exception{
		// �ڵ�ɾ��ԭ��('��ҵ�ڵ�'��'����ڵ�'ͨ��)
		// 1. �ýڵ���ڣ�- ���򷵻� 0
		// 2. ���Ƿ�Ҷ�ӽڵ�(��Ҷ�ӽڵ㲻��ֱ��ɾ��)�� - ���򷵻� 1
		// 3. Ҷ�ӽڵ��ɾ��
		// - ����ɾ�������򷵻� 3
		try{
			NetWordTree illegalWordTree=this.listByNetWordTreeId(netWordTreeId);// ���Ҹýڵ�
			if (illegalWordTree == null) return 0; 						// �ڵ㲻����
			//�������ĺ��ӽڵ�
			List<NetWordTree> illeIfExitChild=this.netWordTreeMapper.getNetWordTreeDirectChildrenById(netWordTreeId);
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
	public boolean delBackNodeById(int netWordTreeId) {
		try {
			// ���ҵ��ýڵ�
			NetWordTree illegalWordTree=this.listByNetWordTreeId(netWordTreeId);
			if (illegalWordTree == null) return true;	// �ڵ㲻���ڣ�����Ҫɾ��
				//�Ѹ�·���µ����нڵ��ɾ��
				List<NetWordTree> pathId=this.netWordTreeMapper.loadNetWordTreePathList(netWordTreeId);
				for(NetWordTree iTree:pathId){
					int iTreeId=iTree.getNetWordTreeId();
					String curNodePath=iTree.getWholePath();
					String[] path = curNodePath.split(",");
					for(int i=0;i<path.length;i++){
						String singlePathDeal = path[i];
						 int illeWordId = Integer.parseInt(singlePathDeal.substring(
								singlePathDeal.indexOf("-")+1, singlePathDeal
										.lastIndexOf("-")));
					if(illeWordId>=netWordTreeId){
						netWordTreeMapper.delNetWordTreeNodeById(iTreeId); // ɾ���ڵ�
						netWordsMapper.delNetWordsByNetWordTreeId(iTreeId);	// ����ɾ����ҵ�ؼ���
					}
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	//ͨ�����д�����netWordTreeId�ҵ����д�
	public List<NetWords> findNetWordsByNetWordTreeId(int netWordTreeId) {
		return this.netWordsMapper.loadNetWordsByNetWordTreeId(netWordTreeId);
	}
	
	public List<NetWordTree> findAllNetWordTree() {
		
		return this.netWordTreeMapper.loadAllNetWordTree();
	}

	public List<NetWordTree> findAllRootNetWordTree() {
		return this.netWordTreeMapper.loadAllRootNetWordTree();
	}

	public List<NetWordTree> findComboxList() throws Exception {
		return this.netWordTreeMapper.loadNetWordTreeComboxList();
	}


	public List<NetWordTree> loadTreePathList(int id) {
		return this.netWordTreeMapper.loadNetWordTreePathList(id);
	}

	public NetWordTree listByNetWordTreeId(int netWordTreeId) {
		return this.netWordTreeMapper.listByNetWordTreeId(netWordTreeId);
	}
	
	//�����ݸ��µ��ı�
	public String updateToTxt() {
		List<NetWords> netWords=this.netWordsMapper.loadAllNetWords();
		try {
			File fileResult = new File(NlpDiyWordsPath.NET_WORDS_PATH);
			if (!fileResult.exists()) {
				fileResult.createNewFile();
			}

			FileWriter fw = new FileWriter(fileResult);
			for(NetWords nw:netWords){
				String netWordCnt=nw.getNetWordCnt();
				fw.write(netWordCnt+"\r\n");
			}
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
			return "no";
		}
		return "yes";
	}
	
	public NetWordTreeMapper getNetWordTreeMapper() {
		return netWordTreeMapper;
	}

	public void setNetWordTreeMapper(NetWordTreeMapper netWordTreeMapper) {
		this.netWordTreeMapper = netWordTreeMapper;
	}

	public NetWordsMapper getNetWordsMapper() {
		return netWordsMapper;
	}

	public void setNetWordsMapper(NetWordsMapper netWordsMapper) {
		this.netWordsMapper = netWordsMapper;
	}

	public NetWordsService getNetWordsService() {
		return netWordsService;
	}

	public void setNetWordsService(NetWordsService netWordsService) {
		this.netWordsService = netWordsService;
	}


}
