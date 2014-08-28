package ims.synWords.service;

import ims.site.service.SiteServiceImpl;
import ims.synWords.dao.SynWordsMapper;
import ims.synWords.model.SynWords;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


public class SynWordsServiceImpl implements SynWordsService {
	private static final Log log = LogFactory.getLog(SiteServiceImpl.class);

	/*-----------��װDAO��------------*/
	private SynWordsMapper synWordsMapper; // ע��mapper����
	
	/*------------------------------------------------------�������-----------------------------------------------------------------------------*/
	private DataSourceTransactionManager transactionManager;    // �ṩ�Ե���javax.sql.DataSource�������
	
	private DefaultTransactionDefinition txDefinition;			// ͨ��DefaultTransactionDefinition��������������������
	
	private TransactionStatus status;							// ��ȡ�����״̬
	
/*---------------------------------------------------------ҵ���߼�����--------------------------------------------------------------------------*/
	
	// չʾ����ͬ���
	public List<SynWords> listAll(){
		List<SynWords> synWordsList = null;
		try {
			synWordsList = synWordsMapper.listSynWordsAll();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return synWordsList;
	}           
	 
	// ���ͬ���
	public String add(SynWords synWords){
		log.debug("add SynWords instance");
        this.status=transactionManager.getTransaction(txDefinition);
        try{
        	this.synWordsMapper.addSynWords(synWords);
        	transactionManager.commit(status);
        }catch(Exception e){
        	e.printStackTrace();
        	transactionManager.rollback(status);
        	return "no";
        }
		return "ok";
	}       
	
	// ɾ��ͬ���
	public String deleteById(int synWordId){
		log.debug("delete SynWords instance");
        this.status=transactionManager.getTransaction(txDefinition);
        try{
        	this.synWordsMapper.deleteSynWordsById(synWordId);
        	transactionManager.commit(status);
        }catch(Exception e){
        	e.printStackTrace();
        	transactionManager.rollback(status);
        	return "no";
        }
		return "ok";
	}    

	// �༭ͬ���
	public String update(SynWords synWords){
		log.debug("update SynWords instance");
        this.status=transactionManager.getTransaction(txDefinition);
        try{
        	this.synWordsMapper.updateSynWords(synWords);
        	transactionManager.commit(status);
        }catch(Exception e){
        	e.printStackTrace();
        	transactionManager.rollback(status);
        	return "no";
        }
		return "ok";
	}   

	// ����ͬ���
	public SynWords loadById(int synWordId){
		SynWords synWordsList = null;
		log.debug("Load SynWords instance");
		this.status=transactionManager.getTransaction(txDefinition);
		try {
			synWordsList = synWordsMapper.loadSynWordsById(synWordId);
			transactionManager.commit(status);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return synWordsList;
	}

	public SynWordsMapper getSynWordsMapper() {
		return synWordsMapper;
	}

	public void setSynWordsMapper(SynWordsMapper synWordsMapper) {
		this.synWordsMapper = synWordsMapper;
	}

	public DataSourceTransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(
			DataSourceTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public DefaultTransactionDefinition getTxDefinition() {
		return txDefinition;
	}

	public void setTxDefinition(DefaultTransactionDefinition txDefinition) {
		this.txDefinition = txDefinition;
	}

	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}  

}
