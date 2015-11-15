import java.util.LinkedList;


public class ReferenciaDataBlock {

	public String rowID;
	public int id;
	public DataBlock prox;
	public int[]  listaInterna ;
	public DataBlock ant;
	public String dados;

	
	public ReferenciaDataBlock(int id,int[] listaInterna, String rowId){
		this.id = id;
		this.rowID = rowId; 
		this.listaInterna = listaInterna;
	}
	public ReferenciaDataBlock(){
		this.id = 0;
		this.listaInterna = null;
		this.rowID = "0;0";
		this.dados = "";	
	}
	
	
	
	
}
