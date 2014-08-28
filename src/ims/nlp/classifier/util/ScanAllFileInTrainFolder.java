package ims.nlp.classifier.util;

import ims.nlp.cache.FolderFileBufferPool;

import java.io.File;

public class ScanAllFileInTrainFolder {

	// 标记是否有新的文件存在
	private boolean checkflag = false;
	// 记录文件的数目
	private int fileNum = 0;

	/**
	 * 检查指定的文件夹中是否存在新的文件
	 * 
	 * @param scanFolderDir
	 */
	public void checkNewFileInFolder(String scanFolderDir) {

		File file = new File(scanFolderDir);

		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (scanFolderDir.endsWith(File.separator)) {
				temp = new File(scanFolderDir + tempList[i]);
			} else {
				temp = new File(scanFolderDir + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				// 文件数目加一
				this.fileNum++;

				// 获得缓存池对象
				FolderFileBufferPool folderFileBufferPool = FolderFileBufferPool
						.getFolderFileBufferPool();

				String fileName = temp.getName();
				String filePath = temp.getAbsolutePath();
				// 检查文件是否原先就存在于文件夹中
				boolean inFlag = folderFileBufferPool.checkFileInFolder(
						fileName, filePath);

				if (inFlag == false) {
					setCheckflag(true);
					folderFileBufferPool.addNewEntityInPool(fileName, filePath);
				}
			}
			if (temp.isDirectory()) {
				checkNewFileInFolder(scanFolderDir + "/" + tempList[i]);
			}
		}
	}

	/**
	 * 检查函数执行方法（返回值-1：检查出错；0：不存在；1：存在）
	 * 
	 * @param scanFolderDir
	 * @return
	 */
	public int execCheck(String scanFolderDir) {

		// 准备检查的返回结果
		int checkRes = 0;

		File file = new File(scanFolderDir);
		if (!file.exists() || !file.isDirectory()) {
			System.err.println("传入的地址不存在或者不是一个文件夹，请检查！");

			checkRes = -1;
			return checkRes;
		}

		// 执行递归检查方法
		this.checkNewFileInFolder(scanFolderDir);
		// 检查文件数目是否减少
		FolderFileBufferPool folderFileBufferPool = FolderFileBufferPool
				.getFolderFileBufferPool();
		boolean fileNumLessFlag = folderFileBufferPool
				.checkFileNumLess(this.fileNum);

		if (isCheckflag() || fileNumLessFlag == true) {
			checkRes = 1;
		}
		return checkRes;

	}

	public boolean isCheckflag() {
		return checkflag;
	}

	public void setCheckflag(boolean checkflag) {
		this.checkflag = checkflag;
	}

}
