package ims.corpusProduct.dao;

import ims.corpusProduct.model.CorpusProduct;
import ims.nlp.entity.model.CorpusText;

import java.util.List;

public interface CorpusProductMapper {
	List <CorpusProduct> listAll();              		// 显示所有语料产品
	
	CorpusProduct lodeById(int corpusProductId); 		// 通过语料产品编号查找语料产品
	
	List <CorpusProduct> lodeBySetId(int setId);        // 通过类别编号查找语料产品
	
	void add(CorpusProduct corpusProduct);              // 插入对象
}
