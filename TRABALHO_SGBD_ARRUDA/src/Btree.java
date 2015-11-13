import java.util.ArrayList;


public class Btree<T extends Comparable<T>> {
	
private Nodo<T> raiz;
	
	public void inserir(T chave)
	{
		raiz = inserir0(raiz, chave);
		
	}
	
	private Nodo<T> inserir0(Nodo<T> nodo, T chave)
	{
		if(nodo == null)
		{
			return new Nodo<T>(chave);
		}
		
		if(nodo.esquerdo == null && nodo.direito == null)
		{
			
			System.out.println("raiz sem esquerdo e nem direito " + nodo.chave);

			//olha pro nodo e ve se tem espaco
			// tem espaco, entao coloca o rowid la dentro
			if(nodo.temEspaco())
			{
				nodo.addOrdenadoArray(chave);
				System.out.println("inseri o " + chave);
				nodo.imprimeChaves();
				return nodo;
			}
			else if(nodo.temEspaco() == false)
			{
				System.out.println("***inseri pelo sem espaco " + chave);

				//nao tem espaço? tem que fazer o spli das folhas  e reerenciar
				//add pra estouraR
				nodo.addOrdenadoArray(chave);
				nodo.imprimeChaves();
				
				//chama split da esquerda
				ArrayList<T> esquerda = nodo.splitEsquerda();
				//chama split da direita
				ArrayList<T> direita = nodo.splitDireita();
				//pega o meio do array
			    T meio = (T) nodo.getEstouro();
			    
			    System.out.println("meio" + meio);
			    
			    for(int i = 0; i<direita.size(); i++)
			    {
			    	System.out.println("ESQUERDAS"+direita.get(i));
			    }
			    
			    //cria o novo nodo direito
				Nodo<T> novoDireita = new Nodo<T>(meio);
				novoDireita.addDireita(direita);
				
				 //cria o novo nodo esquerdo
				nodo.limpaFilhos();
				nodo.addEsquerda(esquerda); 
			    		
				System.out.println("imprimir o pai e seus nodos: " + nodo.pai);
				
			    //tem que fazer o nodo que estourou subir
				// c n tem pai ele eh o raiz, logo tem que criar um pai pra ele
				if(nodo.pai == null)
				{
					Nodo<T> novo = new Nodo<>(meio);
					raiz = novo;
					novo.isRamo = true;
					novo.imprimeChaves();
					novo.direito = novoDireita;
					novo.esquerdo = nodo;
					return nodo.pai = inserir0(novo, meio);
				}
				
				else
				{
					//APENAS ADD NO PAI O NOVO NODO PRA ELE REFERENCIAR OS DEMAIS e faz o split do filho 
					Nodo novo = new Nodo(meio);
					novo.esquerdo = novo;
					nodo.pai.addOrdenadoArray(inserir0(novo, meio));
				}
				
				
				
			}
		}
		else if(chave.compareTo((T) nodo.chave) < 0)
		{
			System.out.print("entrei no menor com o " + chave);
			
			//olha pro nodo e ve se tem espaco
			// tem espaco, entao coloca o rowid la dentro
			System.out.println("entrei no maior com o " + nodo.chave + "e com a chave " + chave);
			//olha pro nodo e ve se tem espaco
			// tem espaco, entao coloca o rowid la dentro
			if(nodo.isRamo == false)
			{
				System.out.println("é ramo, logo nao posso adicionar aqui, poha e tenho que ir pra baixo.");
				return nodo;
			}
			else
			{
				//cheghuei no folha que posso adicionar, agora tento colocar o rowid aqui dentro
				if(nodo.esquerdo.temEspaco() == true)
				{
		
				nodo.esquerdo.addOrdenadoArray(chave);
				return nodo;
				}
				else if(nodo.esquerdo.temEspaco() == false)
				{
					System.out.println("***estou no nodo esquerdo, que eh o " + nodo.esquerdo.chave);
					nodo.esquerdo.imprimeChaves();


					System.out.println("***inseri pelo sem espaco la no chave maior " + chave);

					//nao tem espaço? tem que fazer o spli das folhas  e reerenciar
					//add pra estouraR
					nodo.esquerdo.addOrdenadoArray(chave);
					nodo.esquerdo.imprimeChaves();

					System.out.println("*aaaa*");
					//chama split da esquerda
					ArrayList<T> esquerda = nodo.esquerdo.splitEsquerda();
					//chama split da direita
					ArrayList<T> direita = nodo.esquerdo.splitDireita();
					//pega o meio do array
				    T meio = (T) nodo.esquerdo.getEstouro();
				    
				    System.out.println("meio" + meio);
				    
				    for(int i = 0; i<direita.size(); i++)
				    {
				    	System.out.println("dd"+direita.get(i));
				    }
				    
				    //cria o novo nodo direito
					Nodo novoDireita = new Nodo(meio);
					novoDireita.addDireita(direita);
					
					 //cria o novo nodo esquerdo
					nodo.esquerdo.limpaFilhos();
					nodo.esquerdo.addEsquerda(esquerda); 
					
					System.out.println("imprimir o pai e seus nodos 00: " + nodo.pai.chave);
					nodo.esquerdo.pai.imprimeChaves();
				    		  		    
					
				    //tem que fazer o nodo que estourou subir
					// c n tem pai ele eh o raiz, logo tem que criar um pai pra ele
					if(nodo.esquerdo.pai == null)
					{
						Nodo novo = new Nodo(meio);
						//raiz = novo;
						novo.isRamo = true;
						novo.imprimeChaves();
						novo.direito = novoDireita;
						novo.esquerdo = nodo;
					//	return nodo.pai = novo;//inserir0(novo, meio);
						return nodo.pai = inserir0(novo, meio);
					}
					
					else
					{
						//APENAS ADD NO PAI O NOVO NODO PRA ELE REFERENCIAR OS DEMAIS e faz o split do filho 
						Nodo novo = new Nodo(meio);
						novo.esquerdo = novo;
						nodo.esquerdo.pai.addOrdenadoArray(inserir0(novo, meio));
					}
					
				}
				
			}
			
			
		}
		else if (chave.compareTo((T) nodo.chave) > 0)
		{
			
			System.out.println("entrei no maior com o " + nodo.chave + "e com a chave " + chave);
			//olha pro nodo e ve se tem espaco
			// tem espaco, entao coloca o rowid la dentro
			if(nodo.isRamo == false)
			{
				System.out.println("é ramo, logo nao posso adicionar aqui, poha e tenho que ir pra baixo.");
				return nodo;
			}
			else
			{
				//cheghuei no folha que posso adicionar, agora tento colocar o rowid aqui dentro
				if(nodo.direito.temEspaco() == true)
				{
		
				nodo.direito.addOrdenadoArray(chave);
				return nodo;
				}
				else if(nodo.direito.temEspaco() == false)
				{
					System.out.println("***estou no nodo direito, que eh o " + nodo.direito.chave);
					nodo.direito.imprimeChaves();


					System.out.println("***inseri pelo sem espaco la no chave maior " + chave);

					//nao tem espaço? tem que fazer o spli das folhas  e reerenciar
					//add pra estouraR
					nodo.direito.addOrdenadoArray(chave);
					nodo.direito.imprimeChaves();
					
					

					System.out.println("*aaaa*");
					//chama split da esquerda
					ArrayList<T> esquerda = nodo.direito.splitEsquerda();
					//chama split da direita
					ArrayList<T> direita = nodo.direito.splitDireita();
					//pega o meio do array
				    T meio = (T) nodo.direito.getEstouro();
				    
				    System.out.println("meio" + meio);
				    
				    for(int i = 0; i<direita.size(); i++)
				    {
				    	System.out.println("dd"+direita.get(i));
				    }
				    
				    //cria o novo nodo direito
					Nodo novoDireita = new Nodo(meio);
					novoDireita.addDireita(direita);
					
					 //cria o novo nodo esquerdo
					nodo.direito.limpaFilhos();
					nodo.direito.addEsquerda(esquerda); 
					
					System.out.println("imprimir o pai e seus nodos: " + nodo.direito.pai);
					//nodo.direito.pai.imprimeChaves();
				    		  		    
					
				    //tem que fazer o nodo que estourou subir
					// c n tem pai ele eh o raiz, logo tem que criar um pai pra ele
					if(nodo.direito.pai == null)
					{
						Nodo novo = new Nodo(meio);
						//raiz = novo;
						novo.isRamo = true;
						novo.imprimeChaves();
						novo.direito = novoDireita;
						novo.esquerdo = nodo;
						return nodo.direito.pai = inserir0(novo, meio);
					}
					
					else
					{
						//APENAS ADD NO PAI O NOVO NODO PRA ELE REFERENCIAR OS DEMAIS e faz o split do filho 
						Nodo novo = new Nodo(meio);
						novo.esquerdo = novo;
						nodo.direito.pai.addOrdenadoArray(inserir0(novo, meio));
					}
				}
			}
		}

	return nodo;
	}
	
	

	public String toString()
	{
		return String.format("Btree[raiz=%s]", toString0(raiz));
	}
	
	private String toString0(Nodo nodo)
	{
		if(nodo == null){return "#";}
		
		//String.format("%"
		
		String msg = " " + nodo.chave;
		msg += "\n[" + toString0(nodo.esquerdo) + "]";
		msg += "\n[" + toString0(nodo.direito) + "]";
		return msg;
			
	}
	
	
	
	public static void main(String[] args) {
	
		Btree<Integer> bt = new Btree();

		for(int i = 1; i< 14; i++)
		{
			bt.inserir(i);
		}
		System.out.println("raix final " + bt.raiz.chave);
		System.out.println("esq raiz final " + bt.raiz.esquerdo.chave);
		System.out.println("chaves: ");bt.raiz.esquerdo.imprimeChaves();
		System.out.println("dir raiz final " + bt.raiz.direito.chave);
		System.out.println("chaves: ");bt.raiz.direito.imprimeChaves();
		System.out.println("chaves da raiz " + bt.raiz.chave);
		System.out.println(bt.toString());
		
		
		
	}
	

}