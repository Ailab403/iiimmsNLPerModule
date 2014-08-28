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

	// -------- 显示行业树 ---------
	// 返回显示行业树(包含全部行业)的json格式字符串
	public String formAllBackTreeStr() throws Exception {
		String allBackTreeStr = null;
		try {
			List<Menu> menus = new ArrayList<Menu>();
			List<IllegalWordTree> firstNodes = this.findRootIllegalWordTree();
			if (firstNodes != null && !firstNodes.isEmpty()) { // 有行业存在
				List<Menu> menulist = new ArrayList<Menu>();
				for (int i = 0; i < firstNodes.size(); i++) {
					int illegalWordParentId;
					IllegalWordTree illegalWordTree = firstNodes.get(i); // 取得每个行业根节点
					//得到敏感词的ID
					illegalWordParentId = illegalWordTree
							.getIllegalWordTreeId();
					
					String illegalWordParentTitle = illegalWordTree
							.getTreeTitle();
					menulist.add(new Menu(illegalWordParentId, illegalWordParentTitle, false, "", null, illegalWordParentTitle));
				}
				List<IllegalWordTree> allNodes = this.findAllIllegalWordTree(); // 返回后台行业树的所有节点列表
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
	public Menu findChildren(List<IllegalWordTree> allNodes , Menu parent){
		if (allNodes == null || parent == null) return null;
		int pid = parent.getId();
		for (int i = 0; i < allNodes.size(); i++) {	// 逐个比较每一个后台节点
			IllegalWordTree curNode = allNodes.get(i);
			if (curNode != null) {
				int cid = curNode.getIllegalWordTreeId();
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
	public HashMap<Integer,IllegalWordTree> formAllBackNodesMap() throws Exception{
		HashMap<Integer,IllegalWordTree> backNodesMap = null;
		try{
			List<IllegalWordTree> allNodes = this.findAllIllegalWordTree(); // 返回后台行业树的所有节点
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
	
	// 根据指定节点返回构成其行业子树的字符串
	public String formBackTreeStrById(int nodeId) throws Exception{
		String backTreeStr = null;
		try{
			IllegalWordTree rootNode = this.listByIllegalWordTreeId(nodeId); // 获得行业根节点
			if (rootNode != null){	// 根节点存在
				Menu menu = null;
				int cid = rootNode.getIllegalWordTreeId();
				String treeTitle = rootNode.getTreeTitle();
				menu = new Menu(cid, treeTitle, false, "", null, treeTitle);
				List<IllegalWordTree> allNodes = this.findAllIllegalWordTree(); // 返回后台行业树的所有节点Map
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
	public String wholeNameByBackNodeId(int illegalWordTreeId) throws Exception{
		String wholeName = null;
		try{
			// 先看该节点是否存在，若存在则找到该节点
			IllegalWordTree curNode = this.listByIllegalWordTreeId(illegalWordTreeId); // 找到该节点
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
	
	//新增加节点
	public String addIllegalWordTreeNode(IllegalWordTree illegalWordTree) {
		try{
			this.illegalWordTreeMapper.addIllegalWordTreeNode(illegalWordTree);
		}catch(Exception e){
			e.printStackTrace();
			return "no";
		}
		return "ok";
	}
	
    //对illegalWords增加敏感词
	public void addKeyWords(IllegalWords iw) {
		this.illegalWordsMapper.insertIllegalWords(iw);
	}
	
	
//	-------- 保存修改 ---------	
	// 保存修改后的后台树行业节点(级联保存行业关键词)
	public boolean saveTradeNode(IllegalWordTree curNode , List<IllegalWords> illegalWordsList) throws Exception{
		try {
			if (curNode == null) return false;
			illegalWordTreeMapper.updateIllegalWordTree(curNode); 	// 保存行业节点信息
			// 删除该行业节点已有的所有关键词
			illegalWordsMapper.delIllegalWordsByIllegalWordsId(curNode.getIllegalWordTreeId());
			// 循环添加所有的关键词
			if (illegalWordsList != null)
				for (int i=0; i<illegalWordsList.size(); i++){
					IllegalWords iw = illegalWordsList.get(i);
					if (iw != null)
						illegalWordsService.addKeyWords(iw);	// 保存敏感词(对每个IllegalWords对象自动生成主键)
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

	// 保存修改后的后台树主题节点(级联保存主题规则)
	public boolean saveSubjectNode(IllegalWordTree curNode) throws Exception{
		//this.status = transactionManager.getTransaction(txDefinition);
		try {
			if (curNode == null) return false;
			illegalWordTreeMapper.updateIllegalWordTree(curNode); 	// 保存主题节点信息
			// 删除该主题节点已有的所有规则
			//subjectRuleMapper.delRulesBySubjectId(curNode.getSubjectId());
			// 循环添加所有的规则
			/*if (ruleList != null)
				for (int i=0; i<ruleList.size(); i++){
					SubjectRule sr = ruleList.get(i);
					if (sr != null){
						sr.setSubjectId(curNode.getSubjectId());
						//subjectRuleMapper.addSubjectRule(sr);	// 保存主题规则
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
	
//	-------- 重名判断 ---------
	// 判断新修改的行业名称是否已被其他行业占用
	public boolean isTradeNameExist(int illegalWordTreeId , String backNodeName) throws Exception{
		try {
			if (backNodeName != null){
				HashSet<String> tradeNames = this.getAllOtherTradeNames(illegalWordTreeId); // 获得其它行业的名称
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
	// 判断新修改的行业节点名字是否与其直接孩子节点的名字重复
	public boolean isNameExistInSubNodes(int illegalWordTreeId,String backNodeName) throws Exception{
		try {
			if (backNodeName != null){
				List<String> existNames = illegalWordTreeMapper.getIllegalWordTreeSubNodeNames(illegalWordTreeId); // 获得直接孩子的名称列表
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
				List<String> existNames = illegalWordTreeMapper.getIllegalWordTreeSelfAndSubNames(parentId); // 获得父节点、兄弟节点的全部名字
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
	public boolean isNodeNameExist(int illegalWordTreeId , int parentId, String backNodeName) throws Exception{
		try {
			if (backNodeName != null){
				HashSet<String> existNames = this.get3GNodeNamesById(illegalWordTreeId,parentId); // 获得三代的名称
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
	
//	-------- 删除 ---------	
	// 节点删除前预判断(能否删除)('行业节点'与'主题节点'通用)
	public int beforeDelCheck(int illegalWordTreeId) throws Exception{
		// 节点删除原则：('行业节点'与'主题节点'通用)
		// 1. 该节点存在；- 否则返回 0
		// 2. 不是非叶子节点(非叶子节点不能直接删除)； - 否则返回 1
		// 3. 叶子节点可删除
		// - 满足删除条件则返回 3
		try{
			IllegalWordTree illegalWordTree=this.listByIllegalWordTreeId(illegalWordTreeId);// 查找该节点
			if (illegalWordTree == null) return 0; 						// 节点不存在
			//查找它的孩子节点
			List<IllegalWordTree> illeIfExitChild=this.illegalWordTreeMapper.loadIllegalWordTreeDirectChildrenById(illegalWordTreeId);
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
	public boolean delBackNodeById(int illegalWordTreeId) {
		try {
			// 先找到该节点
			IllegalWordTree illegalWordTree=this.listByIllegalWordTreeId(illegalWordTreeId);
			if (illegalWordTree == null) return true;	// 节点不存在，不需要删除
				//把该路径下的所有节点给删了
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
						illegalWordTreeMapper.delIllegalWordTreeNodeById(iTreeId); // 删除节点
						illegalWordsMapper.delIllegalWordsByIllegalWordsId(iTreeId);	// 级联删除行业关键词
					}
					}
			/*} else {							// 主题节点
				illegalWordTreeMapper.delBackNodeById(illegalWordTreeId); // 删除节点
				// 若其是最后一个孩子，则要将父节点的hasChild改为0
				List<illegalWordTree> children = this.getDirectChildrenById(illegalWordTree.getParentId()); // 获得被删除节点的其它兄弟
				if (children == null || children.size() == 0)  // 其父节点已经无孩子
					illegalWordTreeMapper.updateHasChild(illegalWordTree.getParentId(), 0);	// 修改父节点的hasChild字段值
				illegalWordTreeMapper.deleteSubjectRuleById(illegalWordTreeId);		// 级联删除主题规则
*/			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	//通过敏感词树的illegalWordTreeId找到敏感词
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