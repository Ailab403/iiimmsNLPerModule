package ims.corpusProduct.service;

import ims.corpusProduct.model.CorpusProduct;
import java.util.List;

public interface CorpusProductService {
	
	public List<CorpusProduct> listAll();
	
	public CorpusProduct loadById(int corpusProductId);

	public List<CorpusProduct> lodeBySetId(int setId);
	
	public String insertAnnexPath(CorpusProduct corpusProduct);
}
