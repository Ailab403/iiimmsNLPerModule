package ims.corpusProduct.dao;

import ims.corpusProduct.model.CorpusProduct;
import ims.nlp.entity.model.CorpusText;

import java.util.List;

public interface CorpusProductMapper {
	List <CorpusProduct> listAll();              		// ��ʾ�������ϲ�Ʒ
	
	CorpusProduct lodeById(int corpusProductId); 		// ͨ�����ϲ�Ʒ��Ų������ϲ�Ʒ
	
	List <CorpusProduct> lodeBySetId(int setId);        // ͨ������Ų������ϲ�Ʒ
	
	void add(CorpusProduct corpusProduct);              // �������
}
