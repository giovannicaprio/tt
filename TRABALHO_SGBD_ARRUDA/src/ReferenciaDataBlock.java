import java.util.LinkedList;


public class ReferenciaDataBlock {

	public int id;
	public DataBlock prox;
	public int[]  listaInterna ;
	public DataBlock ant;
	public String dados;

	
	public ReferenciaDataBlock(int id,int[] listaInterna){
		this.id = id;
		this.listaInterna = listaInterna;
	}
	public ReferenciaDataBlock(){
		this.id = 0;
		this.listaInterna = null;
		this.dados = "";	
	}
	
	
	
	
}
