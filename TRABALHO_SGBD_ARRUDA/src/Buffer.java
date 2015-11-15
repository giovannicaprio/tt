import java.util.ArrayList;
import java.util.HashMap;

/*
 * Classe buffer tem que funcionar da seguinte maneira:
 * 	1. Ao ser criado o buffer le 256 datablocks e os armazena em memoria
 *  2. Para otimizar a chave do array será o rowid e seu conteúdo será o conteúdo do datablock;
 *  3. O algortimo implementado é o clock  
 *  
 */



public class Buffer {
	HashMap<Integer, String> listaDadoIndiceRowID; 
	
	//public ArrayList<Integer> listaDadoIndiceRowID;
	public int key[];
	boolean cacheHit = false;

	
	public Buffer(){
		listaDadoIndiceRowID = new HashMap<Integer, String>();			
	}
	
	public void adicionaIDSerialDados(int id, String sDados){
		if (listaDadoIndiceRowID.size() < 257){
			listaDadoIndiceRowID.put(id, sDados);
		}else{
			
			//######## Remove o ultimo
			listaDadoIndiceRowID.remove(listaDadoIndiceRowID.size() - 1);
			listaDadoIndiceRowID.put(id, sDados);			
		}
	}
	
	public String RecuperaDoBufferID(int id){
		if(listaDadoIndiceRowID.containsKey(id)){
		return listaDadoIndiceRowID.get(id);
		}else{
			return "";
		}
	}
	
	public boolean  ExcluirDoBuffer(int id){
		if(listaDadoIndiceRowID.containsKey(id)){
			listaDadoIndiceRowID.remove(id);
			return true;
		}else{
			return false;
		}
	}
	
	
	
	
	
	

}
