import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;

public class Nodo<T extends Comparable<T>>  {
	
	public T chave;
	public Nodo esquerdo;
	public Nodo direito;
	public Nodo pai;
	public ArrayList<T> rowIds = new ArrayList<T>();
	public ArrayList<Nodo> referenciasNodos = new ArrayList<Nodo>();
	public boolean isRamo;
	
	public int altura; // melhor gastar memoria do que procesador
	
	public Nodo(T chave)
	{
		this.chave = chave;
		esquerdo = direito = null;
		isRamo = false;
		this.addOrdenadoArray(chave);
	}
	
	public int getPai(T chave2)
	{
		return (int) pai.chave;
	}
	
	public void addOrdenadoArray(T chave)
	{
			rowIds.add(chave);
			//ordena	
	        Collections.sort(rowIds);   
	        
	       			/*for (int i = 0; i <= rowIds.size(); i++) 
				{
					for (int j = 0; j < rowIds.size() - 1; j++) 
					{
						if (rowIds.get(j).compareTo(rowIds.get(j + 1)) > 0) 
						{
							troca(rowIds, j, j + 1);
							
						}
					}
				}*/
	}
	
	public void addOrdenadoNodo(Nodo nodo)
	{
		
			//add
		referenciasNodos.add(nodo);
			//ordena	
		for (int j = 0; j < referenciasNodos.size() - 1; j++) 
		{
			if ((int)referenciasNodos.get(j).chave > (int) referenciasNodos.get(j + 1).chave) 
			{
				trocaRamo(referenciasNodos, j, j + 1);
				
			}
		}
			
			
	}
	
	
	private void trocaRamo(ArrayList<Nodo> lista, int j, int i) {
		T tmp = (T) lista.get(i);
		lista.add(i, (Nodo) lista.get(j));
		lista.add(j, (Nodo) tmp);		
	}

	public void troca(ArrayList<T> lista, int j, int i) {
		T tmp = lista.get(i);
		lista.add(i, lista.get(j));
		lista.add(j, tmp);
	}

	public T getEstouro()
	{
		return (T) rowIds.get(2);
	}
	
	public T getEstouroRamo()
	{
		return (T) referenciasNodos.get(2).chave;
	}
	
	public T getPrimeiroDireita()
	{
		return (T) rowIds.get(3);
	}

	
	public boolean temEspaco()
	{
		if(rowIds.size() >= 4 )
			return false;
		else
			return true;
	}
	
	public boolean temEspacoRamo()
	{
		if(referenciasNodos.size() >= 4)
		{
			return false;
		}
			else
		return true;
	}

	public ArrayList<T> splitEsquerda() 
	{
		ArrayList<T> resposta = new ArrayList<T>();
	
			for(int i = 0; i<rowIds.size(); i++)
			{
				resposta.add(rowIds.get(i));
			}
		
		return resposta;
	}
	
	public ArrayList<T> splitDireita() {
		ArrayList<T> resposta = new ArrayList<T>();
		
		
		resposta.add(0, rowIds.get(3));
		resposta.add(1, rowIds.get(4));
			
		
		return resposta;
	}
	
	public ArrayList<Nodo> splitEsquerdaRamo() 
	{
		ArrayList<Nodo> resposta = new ArrayList<Nodo>();
	
			for(int i = 0; i<referenciasNodos.size(); i++)
			{
				resposta.add(referenciasNodos.get(i));
			}
		
		return resposta;
	}
	
	public ArrayList<Nodo> splitDireitaRamo() {
		ArrayList<Nodo> resposta = new ArrayList<Nodo>();
		
		
		resposta.add(0, referenciasNodos.get(3));
		resposta.add(1, referenciasNodos.get(4));
			
		
		return resposta;
	}
	
	public void addEsquerda(ArrayList<T> esquerda) 
	{
		for(int i = 0; i<esquerda.size(); i++)
			{
				rowIds.add(i, esquerda.get(i));

			}
	}
	
	public void addEsquerdaRamo(ArrayList<Nodo> esquerda) 
	{
		for(int i = 0; i<esquerda.size(); i++)
			{
			referenciasNodos.add(i, esquerda.get(i));

			}
	}
	
	public void limpaFilhos() 
	{
				rowIds.clear();	
	}
	
	public void addDireita(ArrayList<T> direita) 
	{
		for(int i = 0; i<direita.size(); i++)
			{
				rowIds.add(i, direita.get(i));

			}
	}
	
	public void addDireitaRamo(ArrayList<Nodo> direita) 
	{
		for(int i = 0; i<direita.size(); i++)
			{
			referenciasNodos.add(i, direita.get(i));

			}
	}
	
	public void imprimeChaves() 
	{
		for(int i = 0; i<rowIds.size(); i++)
			{
				System.out.println(rowIds.get(i));
			}
	}
	
	public void imprimeChavesNodos() 
	{
		for(int i = 0; i< referenciasNodos.size(); i++)
			{
				System.out.println(referenciasNodos.get(i).chave);

			}
	}

	public String imprimeReferencias() {
		String resp = " ";
		
		for(int i = 0; i<referenciasNodos.size(); i++)
		{
			int a = (int) referenciasNodos.get(i).chave;
			Integer.toString(a);
			resp += a + " "; 

		}
		
		
		
		// TODO Auto-generated method stub
		return null;
	}
	
	

}