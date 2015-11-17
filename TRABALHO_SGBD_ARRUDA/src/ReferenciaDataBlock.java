import java.util.LinkedList;


public class ReferenciaDataBlock {

	public String rowID;
	public int DataBlock;
	public int EntradaDocumento;
	public int id;
	public DataBlock prox;
	public int[]  listaInterna ;
	public DataBlock ant;
	public String dados;

	
	public ReferenciaDataBlock(int id,int[] listaInterna, String rowId, int DataBlock, int EntradaDocumento ){
		this.id = id;
		this.DataBlock = DataBlock;
		this.EntradaDocumento = EntradaDocumento;
		this.rowID = rowId; 
		this.listaInterna = listaInterna;
	}
	public ReferenciaDataBlock(){
		this.id = 0;
		this.listaInterna = null;
		this.rowID = "0;0";
		this.dados = "";	
		this.DataBlock = 0;
		this.EntradaDocumento = 0;
	}
	
	
	
	
}
