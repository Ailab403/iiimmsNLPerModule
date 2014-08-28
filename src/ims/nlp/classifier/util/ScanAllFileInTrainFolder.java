package ims.nlp.classifier.util;

import ims.nlp.cache.FolderFileBufferPool;

import java.io.File;

public class ScanAllFileInTrainFolder {

	// ����Ƿ����µ��ļ�����
	private boolean checkflag = false;
	// ��¼�ļ�����Ŀ
	private int fileNum = 0;

	/**
	 * ���ָ�����ļ������Ƿ�����µ��ļ�
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
				// �ļ���Ŀ��һ
				this.fileNum++;

				// ��û���ض���
				FolderFileBufferPool folderFileBufferPool = FolderFileBufferPool
						.getFolderFileBufferPool();

				String fileName = temp.getName();
				String filePath = temp.getAbsolutePath();
				// ����ļ��Ƿ�ԭ�Ⱦʹ������ļ�����
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
	 * ��麯��ִ�з���������ֵ-1��������0�������ڣ�1�����ڣ�
	 * 
	 * @param scanFolderDir
	 * @return
	 */
	public int execCheck(String scanFolderDir) {

		// ׼�����ķ��ؽ��
		int checkRes = 0;

		File file = new File(scanFolderDir);
		if (!file.exists() || !file.isDirectory()) {
			System.err.println("����ĵ�ַ�����ڻ��߲���һ���ļ��У����飡");

			checkRes = -1;
			return checkRes;
		}

		// ִ�еݹ��鷽��
		this.checkNewFileInFolder(scanFolderDir);
		// ����ļ���Ŀ�Ƿ����
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
