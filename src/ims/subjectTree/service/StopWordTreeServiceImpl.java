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

	// -------- ��ʾ��ҵ�� ---------
	// ������ʾ��ҵ��(����ȫ����ҵ)��json��ʽ�ַ���
	public String formAllStopWordTreeStr() throws Exception {
		String allBackTreeStr = null;
		try {
			List<Menu> menus = new ArrayList<Menu>();
			List<StopWordTree> firstNodes = this.findAllRootStopWordTrees();
			if (firstNodes != null && !firstNodes.isEmpty()) { // ����ҵ����
				List<Menu> menulist = new ArrayList<Menu>();
				for (int i = 0; i < firstNodes.size(); i++) {
					int illegalWordParentId;
					StopWordTree illegalWordTree = firstNodes.get(i); // ȡ��ÿ����ҵ���ڵ�
					// �õ����дʵ�ID
					illegalWordParentId = illegalWordTree.getStopWordTreeId();

					String illegalWordParentTitle = illegalWordTree
							.getTreeTitle();
					menulist.add(new Menu(illegalWordParentId,
							illegalWordParentTitle, false, "", null,
							illegalWordParentTitle));
				}
				List<StopWordTree> allNodes = this.findAllStopWordTree(); // ���غ�̨��ҵ�������нڵ��б�
				if (allNodes != null)
					for (int i = 0; i < menulist.size(); i++) { // ѭ��ÿ����ҵ���ڵ�
						Menu menu = findChildren(allNodes, menulist.get(i)); // ���õݹ鷽������װÿ��menu
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
	public Menu findChildren(List<StopWordTree> allNodes, Menu parent) {
		if (allNodes == null || parent == null)
			return null;
		int pid = parent.getId();
		for (int i = 0; i < allNodes.size(); i++) { // ����Ƚ�ÿһ����̨�ڵ�
			StopWordTree curNode = allNodes.get(i);
			if (curNode != null) {
				int cid = curNode.getStopWordTreeId();
				int cpid = curNode.getParentId();
				String des = curNode.getTreeTitle();
				if (cpid == pid) { // ���ýڵ�ĸ����Ǵ���ڵ�parent�������װ��parent��
					if (parent.getChildren() == null)
						parent.setChildren(new ArrayList<Menu>());
					parent.getChildren().add(
							findChildren(allNodes, new Menu(cid, des, false,
									"", null, des))); // �ݹ�:ȥ��װ�Լ��ĺ���
				}
			}
		}
		return parent;
	}

	// �������к�̨��ҵ�ڵ�Map<�ڵ��ţ��ڵ�>
	public HashMap<Integer, StopWordTree> formAllStopWordNodesMap()
			throws Exception {
		HashMap<Integer, StopWordTree> backNodesMap = null;
		try {
			List<StopWordTree> allNodes = this.findAllStopWordTree(); // ���غ�̨��ҵ�������нڵ�
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

	// ����ָ���ڵ㷵�ع�������ҵ�������ַ���
	public String formStopWordTreeStrById(int nodeId) throws Exception {
		String backTreeStr = null;
		try {
			StopWordTree rootNode = this.listByStopWordTreeId(nodeId); // �����ҵ���ڵ�
			if (rootNode != null) { // ���ڵ����
				Menu menu = null;
				int cid = rootNode.getStopWordTreeId();
				String treeTitle = rootNode.getTreeTitle();
				menu = new Menu(cid, treeTitle, false, "", null, treeTitle);
				List<StopWordTree> allNodes = this.findAllStopWordTree(); // ���غ�̨��ҵ�������нڵ�Map
				if (allNodes != null) {
					List<Menu> menus = new ArrayList<Menu>();
					Menu menu2 = findChildren(allNodes, menu); // ���õݹ鷽����������
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

	// ������ѡ��̨�ڵ������·����
	public String wholeNameByStopWordNodeId(int stopWordTreeId)
			throws Exception {
		String wholeName = null;
		try {
			// �ȿ��ýڵ��Ƿ���ڣ����������ҵ��ýڵ�
			StopWordTree curNode = this.listByStopWordTreeId(stopWordTreeId); // �ҵ��ýڵ�
			if (curNode != null) { // �ýڵ����
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

	// �����ӽڵ�
	public String addStopWordTreeNode(StopWordTree illegalWordTree) {
		try {
			this.stopWordTreeMapper.addStopWordTreeNode(illegalWordTree);
		} catch (Exception e) {
			e.printStackTrace();
			return "no";
		}
		return "ok";
	}

	// ��illegalWords�������д�
	public void addKeyWords(StopWords iw) {
		this.stopWordsMapper.insertStopWords(iw);
	}

	// -------- �����޸� ---------
	// �����޸ĺ�ĺ�̨����ҵ�ڵ�(����������ҵ�ؼ���)
	public boolean saveTradeNode(StopWordTree curNode,
			List<StopWords> illegalWordsList) throws Exception {
		try {
			if (curNode == null)
				return false;
			stopWordTreeMapper.updateStopWordTree(curNode); // ������ҵ�ڵ���Ϣ
			// ɾ������ҵ�ڵ����е����йؼ���
			stopWordsMapper.delStopWordsByStopWordsId(curNode
					.getStopWordTreeId());
			// ѭ��������еĹؼ���
			if (illegalWordsList != null)
				for (int i = 0; i < illegalWordsList.size(); i++) {
					StopWords iw = illegalWordsList.get(i);
					if (iw != null)
						stopWordsService.addKeyWords(iw); // �������д�(��ÿ��IllegalWords�����Զ���������)
				}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// �����޸ĺ�ĺ�̨������ڵ�(���������������)
	public boolean saveSubjectNode(StopWordTree curNode) throws Exception {
		try {
			if (curNode == null)
				return false;
			stopWordTreeMapper.updateStopWordTree(curNode); // ��������ڵ���Ϣ
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// -------- �����ж� ---------
	// �ж����޸ĵ���ҵ�����Ƿ��ѱ�������ҵռ��
	public boolean isTradeNameExist(int stopWordTreeId, String backNodeName)
			throws Exception {
		try {
			if (backNodeName != null) {
				HashSet<String> tradeNames = this
						.getAllOtherTradeNames(stopWordTreeId); // ���������ҵ������
				if (tradeNames == null || tradeNames.isEmpty())
					return false; // û��������ҵ����
				if (!tradeNames.contains(backNodeName))
					return false; // ����ҵ������δ��ռ��
			}
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
		return false;
	}

	// ���ص�ǰ����ָ����ҵ�������������ҵ���Ƽ���
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

	// �ж����޸ĵ���ҵ�ڵ������Ƿ�����ֱ�Ӻ��ӽڵ�������ظ�
	public boolean isNameExistInSubNodes(int stopWordTreeId, String backNodeName)
			throws Exception {
		try {
			if (backNodeName != null) {
				List<String> existNames = stopWordTreeMapper
						.getStopWordTreeSubNodeNames(stopWordTreeId); // ���ֱ�Ӻ��ӵ������б�
				if (existNames == null || existNames.isEmpty())
					return false; // û�к��Ӵ���
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

	// �ж�����ӵ�����ڵ��Ƿ����丸�ڵ㡢�ֵܽڵ�����(�������������ӽڵ�ĸ��ڵ���)
	public boolean isNameExistInParAndBro(int parentId, String backNodeName)
			throws Exception {
		try {
			if (backNodeName != null) {
				List<String> existNames = stopWordTreeMapper
						.getStopWordTreeSelfAndSubNames(parentId); // ��ø��ڵ㡢�ֵܽڵ��ȫ������
				if (existNames == null || existNames.isEmpty())
					return false; // û�к��Ӵ���
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

	// �ж����޸ĵĽڵ������Ƿ����丸�ڵ㡢�ֵܽڵ㼰��ֱ�Ӻ��ӽڵ���������ظ�
	public boolean isNodeNameExist(int stopWordTreeId, int parentId,
			String backNodeName) throws Exception {
		try {
			if (backNodeName != null) {
				HashSet<String> existNames = this.get3GNodeNamesById(
						stopWordTreeId, parentId); // �������������
				if (existNames == null || existNames.isEmpty())
					return false; // û�������ڵ����
				if (!existNames.contains(backNodeName))
					return false; // ��������δ��ռ��
			}
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
		return true;
	}

	// ָ���ڵ㷵���丸�ڵ㡢�ֵܽڵ㼰��ֱ�Ӻ��ӽڵ�����������б�(�������ýڵ�����)
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

	// -------- ɾ�� ---------
	// �ڵ�ɾ��ǰԤ�ж�(�ܷ�ɾ��)('��ҵ�ڵ�'��'����ڵ�'ͨ��)
	public int beforeDelCheck(int stopWordTreeId) throws Exception {
		// �ڵ�ɾ��ԭ��('��ҵ�ڵ�'��'����ڵ�'ͨ��)
		// 1. �ýڵ���ڣ�- ���򷵻� 0
		// 2. ���Ƿ�Ҷ�ӽڵ�(��Ҷ�ӽڵ㲻��ֱ��ɾ��)�� - ���򷵻� 1
		// 3. Ҷ�ӽڵ��ɾ��
		// - ����ɾ�������򷵻� 3
		try {
			StopWordTree illegalWordTree = this
					.listByStopWordTreeId(stopWordTreeId);// ���Ҹýڵ�
			if (illegalWordTree == null)
				return 0; // �ڵ㲻����
			// �������ĺ��ӽڵ�
			List<StopWordTree> illeIfExitChild = this.stopWordTreeMapper
					.loadStopWordTreeDirectChildrenById(stopWordTreeId);
			// ��Ҷ�ӽڵ㣬����ֱ��ɾ��
			if (illeIfExitChild != null && !illeIfExitChild.isEmpty()) {
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 3; // ����ɾ������
	}

	public boolean delStopWordNodeById(int stopWordTreeId) {
		try {
			// ���ҵ��ýڵ�
			StopWordTree illegalWordTree = this
					.listByStopWordTreeId(stopWordTreeId);
			if (illegalWordTree == null)
				return true; // �ڵ㲻���ڣ�����Ҫɾ��
			// �Ѹ�·���µ����нڵ��ɾ��
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
						stopWordTreeMapper.delStopWordTreeNodeById(iTreeId); // ɾ���ڵ�
						stopWordsMapper.delStopWordsByStopWordsId(iTreeId); // ����ɾ����ҵ�ؼ���
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	// ͨ�����д�����illegalWordTreeId�ҵ����д�
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

	// �����ݸ��µ��ı�
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
