package ims.subjectTree.service;

import ims.nlp.cache.NlpDiyWordsPath;
import ims.subjectTree.dao.StopWordTreeMapper;
import ims.subjectTree.dao.StopWordsMapper;
import ims.subjectTree.model.Menu;
import ims.subjectTree.model.NetWords;
import ims.subjectTree.model.StopWordTree;
import ims.subjectTree.model.StopWords;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StopWordTreeServiceImpl implements StopWordTreeService {

	private StopWordTreeMapper stopWordTreeMapper;
	private StopWordsMapper stopWordsMapper;
	private StopWordsService stopWordsService;

	// -------- 显示行业树 ---------
	// 返回显示行业树(包含全部行业)的json格式字符串
	public String formAllStopWordTreeStr() throws Exception {
		String allBackTreeStr = null;
		try {
			List<Menu> menus = new ArrayList<Menu>();
			List<StopWordTree> firstNodes = this.findAllRootStopWordTrees();
			if (firstNodes != null && !firstNodes.isEmpty()) { // 有行业存在
				List<Menu> menulist = new ArrayList<Menu>();
				for (int i = 0; i < firstNodes.size(); i++) {
					int illegalWordParentId;
					StopWordTree illegalWordTree = firstNodes.get(i); // 取得每个行业根节点
					// 得到敏感词的ID
					illegalWordParentId = illegalWordTree.getStopWordTreeId();

					String illegalWordParentTitle = illegalWordTree
							.getTreeTitle();
					menulist.add(new Menu(illegalWordParentId,
							illegalWordParentTitle, false, "", null,
							illegalWordParentTitle));
				}
				List<StopWordTree> allNodes = this.findAllStopWordTree(); // 返回后台行业树的所有节点列表
				if (allNodes != null)
					for (int i = 0; i < menulist.size(); i++) { // 循环每个行业根节点
						Menu menu = findChildren(allNodes, menulist.get(i)); // 调用递归方法，封装每个menu
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
	public Menu findChildren(List<StopWordTree> allNodes, Menu parent) {
		if (allNodes == null || parent == null)
			return null;
		int pid = parent.getId();
		for (int i = 0; i < allNodes.size(); i++) { // 逐个比较每一个后台节点
			StopWordTree curNode = allNodes.get(i);
			if (curNode != null) {
				int cid = curNode.getStopWordTreeId();
				int cpid = curNode.getParentId();
				String des = curNode.getTreeTitle();
				if (cpid == pid) { // 若该节点的父亲是传入节点parent，则将其封装进parent里
					if (parent.getChildren() == null)
						parent.setChildren(new ArrayList<Menu>());
					parent.getChildren().add(
							findChildren(allNodes, new Menu(cid, des, false,
									"", null, des))); // 递归:去封装自己的孩子
				}
			}
		}
		return parent;
	}

	// 返回所有后台行业节点Map<节点编号，节点>
	public HashMap<Integer, StopWordTree> formAllStopWordNodesMap()
			throws Exception {
		HashMap<Integer, StopWordTree> backNodesMap = null;
		try {
			List<StopWordTree> allNodes = this.findAllStopWordTree(); // 返回后台行业树的所有节点
			if (allNodes != null) {
				backNodesMap = new HashMap<Integer, StopWordTree>();
				for (int i = 0; i < allNodes.size(); i++) {
					StopWordTree frontNode = allNodes.get(i);
					if (frontNode != null)
						backNodesMap.put(frontNode.getStopWordTreeId(),
								frontNode);
				}
			}
		} catch (Exception e) {
			throw (e);
		}
		return backNodesMap;
	}

	// 根据指定节点返回构成其行业子树的字符串
	public String formStopWordTreeStrById(int nodeId) throws Exception {
		String backTreeStr = null;
		try {
			StopWordTree rootNode = this.listByStopWordTreeId(nodeId); // 获得行业根节点
			if (rootNode != null) { // 根节点存在
				Menu menu = null;
				int cid = rootNode.getStopWordTreeId();
				String treeTitle = rootNode.getTreeTitle();
				menu = new Menu(cid, treeTitle, false, "", null, treeTitle);
				List<StopWordTree> allNodes = this.findAllStopWordTree(); // 返回后台行业树的所有节点Map
				if (allNodes != null) {
					List<Menu> menus = new ArrayList<Menu>();
					Menu menu2 = findChildren(allNodes, menu); // 调用递归方法来生成树
					if (menu2 != null)
						menus.add(menu2);
					JSONArray jsonObject = JSONArray.fromObject(menus);
					backTreeStr = jsonObject.toString();
				}
			}
			return backTreeStr;
		} catch (Exception e) {
			e.printStackTrace();
			return backTreeStr;
		}
	}

	// 返回所选后台节点的完整路径名
	public String wholeNameByStopWordNodeId(int stopWordTreeId)
			throws Exception {
		String wholeName = null;
		try {
			// 先看该节点是否存在，若存在则找到该节点
			StopWordTree curNode = this.listByStopWordTreeId(stopWordTreeId); // 找到该节点
			if (curNode != null) { // 该节点存在
				String curNodePath = curNode.getWholePath();
				StringBuffer pathName = this.getNameFromPath(curNodePath);
				wholeName = pathName.toString();
			}
		} catch (Exception e) {
			throw (e);
		}
		return wholeName;
	}

	public StringBuffer getNameFromPath(String curNodePath) {
		StringBuffer wholeName = new StringBuffer();
		String[] path = curNodePath.split(",");
		for (int i = 0; i < path.length; i++) {
			String singlePathDeal = path[i];
			int illeWordId = Integer.parseInt(singlePathDeal.substring(
					singlePathDeal.indexOf("-") + 1, singlePathDeal
							.lastIndexOf("-")));
			StopWordTree illegalWordTree = this
					.listByStopWordTreeId(illeWordId);
			String treeTitle = illegalWordTree.getTreeTitle();
			wholeName.append(treeTitle);
			if (i == path.length - 1) {
				break;
			} else {
				wholeName.append("--->");
			}
		}
		return wholeName;
	}

	// 新增加节点
	public String addStopWordTreeNode(StopWordTree illegalWordTree) {
		try {
			this.stopWordTreeMapper.addStopWordTreeNode(illegalWordTree);
		} catch (Exception e) {
			e.printStackTrace();
			return "no";
		}
		return "ok";
	}

	// 对illegalWords增加敏感词
	public void addKeyWords(StopWords iw) {
		this.stopWordsMapper.insertStopWords(iw);
	}

	// -------- 保存修改 ---------
	// 保存修改后的后台树行业节点(级联保存行业关键词)
	public boolean saveTradeNode(StopWordTree curNode,
			List<StopWords> illegalWordsList) throws Exception {
		try {
			if (curNode == null)
				return false;
			stopWordTreeMapper.updateStopWordTree(curNode); // 保存行业节点信息
			// 删除该行业节点已有的所有关键词
			stopWordsMapper.delStopWordsByStopWordsId(curNode
					.getStopWordTreeId());
			// 循环添加所有的关键词
			if (illegalWordsList != null)
				for (int i = 0; i < illegalWordsList.size(); i++) {
					StopWords iw = illegalWordsList.get(i);
					if (iw != null)
						stopWordsService.addKeyWords(iw); // 保存敏感词(对每个IllegalWords对象自动生成主键)
				}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// 保存修改后的后台树主题节点(级联保存主题规则)
	public boolean saveSubjectNode(StopWordTree curNode) throws Exception {
		try {
			if (curNode == null)
				return false;
			stopWordTreeMapper.updateStopWordTree(curNode); // 保存主题节点信息
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// -------- 重名判断 ---------
	// 判断新修改的行业名称是否已被其他行业占用
	public boolean isTradeNameExist(int stopWordTreeId, String backNodeName)
			throws Exception {
		try {
			if (backNodeName != null) {
				HashSet<String> tradeNames = this
						.getAllOtherTradeNames(stopWordTreeId); // 获得其它行业的名称
				if (tradeNames == null || tradeNames.isEmpty())
					return false; // 没有其它行业存在
				if (!tradeNames.contains(backNodeName))
					return false; // 该行业名称尚未被占用
			}
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
		return false;
	}

	// 返回当前除了指定行业外的其他所有行业名称集合
	public HashSet<String> getAllOtherTradeNames(int stopWordTreeId)
			throws Exception {
		HashSet<String> tradeNames = null;
		try {
			List<String> tradeNameList = stopWordTreeMapper
					.getStopWordTreeAllOtherTradeNames(stopWordTreeId);
			if (tradeNameList != null && !tradeNameList.isEmpty()) {
				tradeNames = new HashSet<String>();
				for (int i = 0; i < tradeNameList.size(); i++)
					tradeNames.add(tradeNameList.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw (e);
		}
		return tradeNames;
	}

	// 判断新修改的行业节点名字是否与其直接孩子节点的名字重复
	public boolean isNameExistInSubNodes(int stopWordTreeId, String backNodeName)
			throws Exception {
		try {
			if (backNodeName != null) {
				List<String> existNames = stopWordTreeMapper
						.getStopWordTreeSubNodeNames(stopWordTreeId); // 获得直接孩子的名称列表
				if (existNames == null || existNames.isEmpty())
					return false; // 没有孩子存在
				for (int i = 0; i < existNames.size(); i++) {
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
	public boolean isNameExistInParAndBro(int parentId, String backNodeName)
			throws Exception {
		try {
			if (backNodeName != null) {
				List<String> existNames = stopWordTreeMapper
						.getStopWordTreeSelfAndSubNames(parentId); // 获得父节点、兄弟节点的全部名字
				if (existNames == null || existNames.isEmpty())
					return false; // 没有孩子存在
				for (int i = 0; i < existNames.size(); i++) {
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
	public boolean isNodeNameExist(int stopWordTreeId, int parentId,
			String backNodeName) throws Exception {
		try {
			if (backNodeName != null) {
				HashSet<String> existNames = this.get3GNodeNamesById(
						stopWordTreeId, parentId); // 获得三代的名称
				if (existNames == null || existNames.isEmpty())
					return false; // 没有其它节点存在
				if (!existNames.contains(backNodeName))
					return false; // 该名称尚未被占用
			}
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
		return true;
	}

	// 指定节点返回其父节点、兄弟节点及其直接孩子节点的所有名字列表(不包含该节点自身)
	public HashSet<String> get3GNodeNamesById(int stopWordTreeId, int parentId)
			throws Exception {
		HashSet<String> existNames = null;
		try {
			List<String> existNameList = stopWordTreeMapper
					.getStopWordTree3GNodeNamesById(stopWordTreeId, parentId);
			if (existNameList != null && !existNameList.isEmpty()) {
				existNames = new HashSet<String>();
				for (int i = 0; i < existNameList.size(); i++)
					existNames.add(existNameList.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw (e);
		}
		return existNames;
	}

	public void update(StopWordTree illegalWordTree) {
		this.stopWordTreeMapper.updateStopWordTree(illegalWordTree);
	}

	// -------- 删除 ---------
	// 节点删除前预判断(能否删除)('行业节点'与'主题节点'通用)
	public int beforeDelCheck(int stopWordTreeId) throws Exception {
		// 节点删除原则：('行业节点'与'主题节点'通用)
		// 1. 该节点存在；- 否则返回 0
		// 2. 不是非叶子节点(非叶子节点不能直接删除)； - 否则返回 1
		// 3. 叶子节点可删除
		// - 满足删除条件则返回 3
		try {
			StopWordTree illegalWordTree = this
					.listByStopWordTreeId(stopWordTreeId);// 查找该节点
			if (illegalWordTree == null)
				return 0; // 节点不存在
			// 查找它的孩子节点
			List<StopWordTree> illeIfExitChild = this.stopWordTreeMapper
					.loadStopWordTreeDirectChildrenById(stopWordTreeId);
			// 非叶子节点，不能直接删除
			if (illeIfExitChild != null && !illeIfExitChild.isEmpty()) {
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 3; // 满足删除条件
	}

	public boolean delStopWordNodeById(int stopWordTreeId) {
		try {
			// 先找到该节点
			StopWordTree illegalWordTree = this
					.listByStopWordTreeId(stopWordTreeId);
			if (illegalWordTree == null)
				return true; // 节点不存在，不需要删除
			// 把该路径下的所有节点给删了
			List<StopWordTree> pathId = this.stopWordTreeMapper
					.loadStopWordTreePathList(stopWordTreeId);
			for (StopWordTree iTree : pathId) {
				int iTreeId = iTree.getStopWordTreeId();
				String curNodePath = iTree.getWholePath();
				String[] path = curNodePath.split(",");
				for (int i = 0; i < path.length; i++) {
					String singlePathDeal = path[i];
					int illeWordId = Integer.parseInt(singlePathDeal.substring(
							singlePathDeal.indexOf("-") + 1, singlePathDeal
									.lastIndexOf("-")));
					if (illeWordId >= stopWordTreeId) {
						stopWordTreeMapper.delStopWordTreeNodeById(iTreeId); // 删除节点
						stopWordsMapper.delStopWordsByStopWordsId(iTreeId); // 级联删除行业关键词
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	// 通过敏感词树的illegalWordTreeId找到敏感词
	public List<StopWords> findStopWordsByStopWordTreeId(int stopWordTreeId) {
		return this.stopWordsMapper
				.loadStopWordsByStopWordTreeId(stopWordTreeId);
	}

	public List<StopWordTree> findAllStopWordTree() {

		return this.stopWordTreeMapper.loadAllStopWordTree();
	}

	public List<StopWordTree> findAllRootStopWordTrees() {
		return this.stopWordTreeMapper.loadAllStopWordTreeRoot();
	}

	public List<StopWordTree> findComboxList() throws Exception {
		return this.stopWordTreeMapper.loadStopWordTreeComboxList();
	}

	public List<StopWordTree> getTreePathList(int id) {
		return this.stopWordTreeMapper.loadStopWordTreePathList(id);
	}

	public StopWordTree listByStopWordTreeId(int stopWordTreeId) {
		return this.stopWordTreeMapper.listByStopWordTreeId(stopWordTreeId);
	}

	// 把内容更新到文本
	public String updateToTxt() {
		List<StopWords> stopWords = this.stopWordsMapper.loadAllStopWords();
		try {
			File fileResult = new File(NlpDiyWordsPath.STOP_WORDS_PATH);
			if (!fileResult.exists()) {
				fileResult.createNewFile();
			}

			FileWriter fw = new FileWriter(fileResult);
			for (StopWords sw : stopWords) {
				String stopWordCnt = sw.getStopWordCnt();
				fw.write(stopWordCnt + "\r\n");
			}
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
			return "no";
		}
		return "yes";
	}

	public StopWordTreeMapper getStopWordTreeMapper() {
		return stopWordTreeMapper;
	}

	public void setStopWordTreeMapper(StopWordTreeMapper stopWordTreeMapper) {
		this.stopWordTreeMapper = stopWordTreeMapper;
	}

	public StopWordsMapper getStopWordsMapper() {
		return stopWordsMapper;
	}

	public void setStopWordsMapper(StopWordsMapper stopWordsMapper) {
		this.stopWordsMapper = stopWordsMapper;
	}

	public StopWordsService getStopWordsService() {
		return stopWordsService;
	}

	public void setStopWordsService(StopWordsService stopWordsService) {
		this.stopWordsService = stopWordsService;
	}

}
