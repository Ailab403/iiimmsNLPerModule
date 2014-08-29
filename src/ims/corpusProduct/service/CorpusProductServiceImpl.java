package ims.corpusProduct.service;

import ims.corpusProduct.dao.CorpusProductMapper;
import ims.corpusProduct.model.CorpusProduct;
import ims.site.service.SiteServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class CorpusProductServiceImpl implements CorpusProductService {
	private static final Log log = LogFactory.getLog(SiteServiceImpl.class);
	/*-----------封装DAO层------------*/
	private CorpusProductMapper corpusProductMapper;
	/*------------------------------------------------------事务控制-----------------------------------------------------------------------------*/
	private DataSourceTransactionManager transactionManager; // 提供对单个javax.sql.DataSource事务管理

	private DefaultTransactionDefinition txDefinition; // 通过DefaultTransactionDefinition对象来持有事务处理属性

	private TransactionStatus status; // 获取事务的状态

	/*---------------------------------------------------------业务逻辑方法--------------------------------------------------------------------------*/
	public List<CorpusProduct> listAll() {
		log.debug("List All CorpusProducts");
		List<CorpusProduct> corpusProductList = new ArrayList<CorpusProduct>();
		try {
			corpusProductList = this.corpusProductMapper.listAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return corpusProductList;
	}

	public CorpusProduct loadById(int corpusProductId) {
		log.debug("List CorpusProducts By corpusProductId");
		CorpusProduct corpusProductList = new CorpusProduct();
		try {
			corpusProductList = this.corpusProductMapper
					.lodeById(corpusProductId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return corpusProductList;
	}

	public List<CorpusProduct> lodeBySetId(int setId) {
		log.debug("List CorpusProducts By setId");
		List<CorpusProduct> corpusProductList = new ArrayList<CorpusProduct>();
		try {
			corpusProductList = this.corpusProductMapper.lodeBySetId(setId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return corpusProductList;
	}

	public String insertAnnexPath(CorpusProduct corpusProduct) {
		log.debug("Insert AnnexPath");
		this.status = transactionManager.getTransaction(txDefinition);
		try {
			this.corpusProductMapper.add(corpusProduct);
			transactionManager.commit(status);
		} catch (Exception e) {
			e.printStackTrace();
			transactionManager.rollback(status);
			return "no";
		}
		return "ok";
	}

	/* superhy */
	public CorpusProduct loadByNodeId(String nodeId) {
		return this.corpusProductMapper.loadByNodeId(nodeId);
	}

	/*-------------------------------------------------------------setter/getter-----------------------------------------------------------*/
	public CorpusProductMapper getCorpusProductMapper() {
		return corpusProductMapper;
	}

	public void setCorpusProductMapper(CorpusProductMapper corpusProductMapper) {
		this.corpusProductMapper = corpusProductMapper;
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
