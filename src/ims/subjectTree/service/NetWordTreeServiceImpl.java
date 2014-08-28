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

	// -------- 显示行业树 ---------
	// 返回显示行业树(包含全部行业)的json格式字符串
	public String formAllNetWordTreeStr() throws Exception {
		String allBackTreeStr = null;
		try {
			List<Menu> menus = new ArrayList<Menu>();
			List<NetWordTree> firstNodes = this.findAllRootNetWordTree();
			if (firstNodes != null && !firstNodes.isEmpty()) { // 有行业存在
				List<Menu> menulist = new ArrayList<Menu>();
				for (int i = 0; i < firstNodes.size(); i++) {
					int illegalWordParentId;
					NetWordTree illegalWordTree = firstNodes.get(i); // 取得每个行业根节点
					//得到敏感词的ID
					illegalWordParentId = illegalWordTree
							.getNetWordTreeId();
					
					String illegalWordParentTitle = illegalWordTree
							.getTreeTitle();
					menulist.add(new Menu(illegalWordParentId, illegalWordParentTitle, false, "", null, illegalWordParentTitle));
				}
				List<NetWordTree> allNodes = this.findAllNetWordTree(); // 返回后台行业树的所有节点列表
				if (allNodes != null)
					for (int i = 0; i < menulist.size(); i++) { // 循环每个行业根节点
						Menu menu = findChildren(allNodes , menulist.get(i)); 	// 调用递归方法，封装每个menu
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

	// 递归：用menu封装指定父节点的所有孩子节点 
	public Menu findChildren(List<NetWordTree> allNodes , Menu parent){
		if (allNodes == null || parent == null) return null;
		int pid = parent.getId();
		for (int i = 0; i < allNodes.size(); i++) {	// 逐个比较每一个后台节点
			NetWordTree curNode = allNodes.get(i);
			if (curNode != null) {
				int cid = curNode.getNetWordTreeId();
				int cpid = curNode.getParentId();
				String des = curNode.getTreeTitle();
				if (cpid == pid) { // 若该节点的父亲是传入节点parent，则将其封装进parent里
						if (parent.getChildren() == null) parent.setChildren(new ArrayList<Menu>());
						parent.getChildren().add(findChildren(allNodes , new Menu(cid, des, false, "", null,des))); // 递归:去封装自己的孩子
				}
			}
		}
		return parent;
	}
	// 返回所有后台行业节点Map<节点编号，节点>
	public HashMap<Integer,NetWordTree> formAllBackNodesMap() throws Exception{
		HashMap<Integer,NetWordTree> backNodesMap = null;
		try{
			List<NetWordTree> allNodes = this.findAllNetWordTree(); // 返回后台行业树的所有节点
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
	
	// 根据指定节点返回构成其行业子树的字符串
	public String formNetWordTreeStrById(int nodeId) throws Exception{
		String backTreeStr = null;
		try{
			NetWordTree rootNode = this.listByNetWordTreeId(nodeId); // 获得行业根节点
			if (rootNode != null){	// 根节点存在
				Menu menu = null;
				int cid = rootNode.getNetWordTreeId();
				String treeTitle = rootNode.getTreeTitle();
				menu = new Menu(cid, treeTitle, false, "", null, treeTitle);
				List<NetWordTree> allNodes = this.findAllNetWordTree(); // 返回后台行业树的所有节点Map
				if (allNodes != null){
					List<Menu> menus = new ArrayList<Menu>();
					Menu menu2 = findChildren(allNodes , menu);	  // 调用递归方法来生成树
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
	
	
	// 返回所选后台节点的完整路径名 
	public String wholeNameByNetNodeId(int netWordTreeId) throws Exception{
		String wholeName = null;
		try{
			// 先看该节点是否存在，若存在则找到该节点
			NetWordTree curNode = this.listByNetWordTreeId(netWordTreeId); // 找到该节点
			if (curNode != null) { // 该节点存在
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
	
	//新增加节点
	public String addNetWordTreeNode(NetWordTree illegalWordTree) {
		try{
			this.netWordTreeMapper.addNetWordTreeNode(illegalWordTree);
		}catch(Exception e){
			e.printStackTrace();
			return "no";
		}
		return "ok";
	}
	
    //对NetWords增加敏感词
	public void addNetWords(NetWords iw) {
		this.netWordsMapper.insertNetWords(iw);
	}
	
	
//	-------- 保存修改 ---------	
	// 保存修改后的后台树行业节点(级联保存行业关键词)
	public boolean saveTradeNode(NetWordTree curNode , List<NetWords> illegalWordsList) throws Exception{
		try {
			if (curNode == null) return false;
			netWordTreeMapper.updateNetWordTree(curNode); 	// 保存行业节点信息
			// 删除该行业节点已有的所有关键词
			netWordsMapper.delNetWordsByNetWordTreeId(curNode.getNetWordTreeId());
			// 循环添加所有的关键词
			if (illegalWordsList != null)
				for (int i=0; i<illegalWordsList.size(); i++){
					NetWords iw = illegalWordsList.get(i);
					if (iw != null)
						netWordsService.addNetWords(iw);	// 保存敏感词(对每个NetWords对象自动生成主键)
				}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	// 保存修改后的后台树主题节点(级联保存主题规则)
	public boolean saveSubjectNode(NetWordTree curNode) throws Exception{
		try {
			if (curNode == null) return false;
			netWordTreeMapper.updateNetWordTree(curNode); 	// 保存主题节点信息
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
//	-------- 重名判断 ---------
	// 判断新修改的行业名称是否已被其他行业占用
	public boolean isTradeNameExist(int netWordTreeId , String backNodeName) throws Exception{
		try {
			if (backNodeName != null){
				HashSet<String> tradeNames = this.getAllOtherTradeNames(netWordTreeId); // 获得其它行业的名称
				if (tradeNames == null || tradeNames.isEmpty()) return false;	// 没有其它行业存在
				if (!tradeNames.contains(backNodeName)) return false;			// 该行业名称尚未被占用
			}
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
		return false;
	}
	// 返回当前除了指定行业外的其他所有行业名称集合
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
	// 判断新修改的行业节点名字是否与其直接孩子节点的名字重复
	public boolean isNameExistInSubNodes(int netWordTreeId,String backNodeName) throws Exception{
		try {
			if (backNodeName != null){
				List<String> existNames = netWordTreeMapper.getNetWordTreeSubNodeNames(netWordTreeId); // 获得直接孩子的名称列表
				if (existNames == null || existNames.isEmpty()) return false;	// 没有孩子存在
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
	// 判断新添加的主题节点是否与其父节点、兄弟节点重名(传入参数是新添加节点的父节点编号)
	public boolean isNameExistInParAndBro(int parentId,String backNodeName) throws Exception{
		try {
			if (backNodeName != null){
				List<String> existNames = netWordTreeMapper.getNetWordTreeSelfAndSubNames(parentId); // 获得父节点、兄弟节点的全部名字
				if (existNames == null || existNames.isEmpty()) return false;	// 没有孩子存在
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
	// 判断新修改的节点名称是否与其父节点、兄弟节点及其直接孩子节点的名字有重复
	public boolean isNodeNameExist(int netWordTreeId , int parentId, String backNodeName) throws Exception{
		try {
			if (backNodeName != null){
				HashSet<String> existNames = this.get3GNodeNamesById(netWordTreeId,parentId); // 获得三代的名称
				if (existNames == null || existNames.isEmpty()) return false;	// 没有其它节点存在
				if (!existNames.contains(backNodeName)) return false;			// 该名称尚未被占用
			}
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
		return true;
	}
	// 指定节点返回其父节点、兄弟节点及其直接孩子节点的所有名字列表(不包含该节点自身)
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
	
//	-------- 删除 ---------	
	// 节点删除前预判断(能否删除)('行业节点'与'主题节点'通用)
	public int beforeDelCheck(int netWordTreeId) throws Exception{
		// 节点删除原则：('行业节点'与'主题节点'通用)
		// 1. 该节点存在；- 否则返回 0
		// 2. 不是非叶子节点(非叶子节点不能直接删除)； - 否则返回 1
		// 3. 叶子节点可删除
		// - 满足删除条件则返回 3
		try{
			NetWordTree illegalWordTree=this.listByNetWordTreeId(netWordTreeId);// 查找该节点
			if (illegalWordTree == null) return 0; 						// 节点不存在
			//查找它的孩子节点
			List<NetWordTree> illeIfExitChild=this.netWordTreeMapper.getNetWordTreeDirectChildrenById(netWordTreeId);
			// 非叶子节点，不能直接删除
			if(illeIfExitChild!=null&& !illeIfExitChild.isEmpty()){
				return 1; 				
			}
		} catch(Exception e){
			e.printStackTrace();
			return 0;
		}
		return 3;	// 满足删除条件
	}
	public boolean delBackNodeById(int netWordTreeId) {
		try {
			// 先找到该节点
			NetWordTree illegalWordTree=this.listByNetWordTreeId(netWordTreeId);
			if (illegalWordTree == null) return true;	// 节点不存在，不需要删除
				//把该路径下的所有节点给删了
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
						netWordTreeMapper.delNetWordTreeNodeById(iTreeId); // 删除节点
						netWordsMapper.delNetWordsByNetWordTreeId(iTreeId);	// 级联删除行业关键词
					}
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	//通过敏感词树的netWordTreeId找到敏感词
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
	
	//把内容更新到文本
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
