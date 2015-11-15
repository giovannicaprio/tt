import java.util.ArrayList;

/*
 * Classe buffer tem que funcionar da seguinte maneira:
 * 	1. Ao ser criado o buffer le 256 datablocks e os armazena em memoria
 *  2. Para otimizar a chave do array será o rowid e seu conteúdo será o conteúdo do datablock;
 *  3. O algortimo implementado é o clock  
 *  
 */



public class Buffer {
	
	public ArrayList<Integer> listaRowIds;
	
	public int key[];
	boolean cacheHit = false;

	
	public Buffer(){
		listaRowIds = new ArrayList<Integer>();
		populaBuffer();
		
		
		
	}
	
	public void populaBuffer()
	{
		
		
		
		Funcoes funcoes = new Funcoes();
		int[] usados = funcoes.RecuperaDataBlocksUsados();
		
		for(int i= 0; i < 257; i++)
		{
		//	listaRowIds.add(usados[i], usados[i].toString());
		}
		
		
		
	}
	
	public boolean temNoBuffer(int rowId)
	{
		if(listaRowIds.get(rowId) == null)
		{
			return false; //cache miss
		}
		
		return true;
	}
	
	public DataBlock clock(int rowId)
	{
		DataBlock resposta = new DataBlock();
		
		cacheHit = this.temNoBuffer(rowId);
		
		if(cacheHit)
		{
			//return listaRowIds.get(rowId);
		}
		
		
		
		
		
		
		
		return resposta;
	}
	
	
	
	

}
