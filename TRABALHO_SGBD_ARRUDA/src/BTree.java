
/* Esta classe foi implementada com base no livro INTRODUCTION TO ALGORITHMS 
 * de THOMAS H. CORMEN, CHARLES E. LEISERSON e RONALD L. RIVEST (1989, THE MIT PRESS)
 * 
 * Neste livro os autores descrevem como criar um btree, criar nodos, inserir nodos, 
 * fazer split de folhas e ramos e deletar nodos.
 * 
 * Entretanto, para o desevolvimento deste trabalho os pseudo-codigos descritos pelos autores
 * foram alterados para resolverem os problemas propostos neste projeto. Desta forma, os nodos
 * da btree foram alterados para receberem referencia para os datablocks que estão 
 * inseridos em um datafile. 
 * 
 * Assim, esta classe btree otimiza a pesquisa em nosso banco de dados possui possui o index
 * de cada data block e a sua posicao.
 * 
 * 
 */



public class BTree
{

	static int order; // ordem da arvore. 
	BNode root;  //cada arvore tem pelo menos o nodo raiz

	public BTree(int order)
	{
		this.order = order;
		root = new BNode(order, null);

	}
// --------------------------------------------------------
//metodo que procura por um nodo especifico               
// --------------------------------------------------------

	// tudo que era key de insercao vira  --- > ref.id pois ja bota direto o idSerial
	public BNode search(BNode root, int key)
	{
		int i = 0;//comeca procurando pelo index 0, o menor
			while(i < root.count && key > root.key[i])//segue incrementando enquanto a chave é maior que o valor atual
			{
				i++;
			}
			if(i <= root.count && key == root.key[i])//verifica se a chave esta no nodo
			{
				return root;
			}
			
		if(root.leaf)//ja vi a raiz. Se eh folha n precisa ver mais nada
        {
			return null ;
		}
		else// se nao for folha comecaa recursao pra procurar
		{
			return search(root.getChild(i),key);
		}
	}
	
//  --------------------------------------------------------
//  Metodo split que divide o nodo que queremos no qual quero inserir se ele esta cheio
//  --------------------------------------------------------

	public void split(BNode x, int i, BNode y)
	{
		BNode z = new BNode(order,null);//crio um nodo extra, ja que vou fazer a divisao
		z.leaf = y.leaf;// seta como folha
		z.count = order - 1;//atualiza o tamanho

		for(int j = 0; j < order - 1; j++)
		{
			z.key[j] = y.key[j+order]; //copia o fim do y na frente do z
		}
		if(!y.leaf)//se n for uma folha precisa redirecionar os filho
		{
			for(int k = 0; k < order; k++)
			{
				z.child[k] = y.child[k+order]; //aqui redireciona
			}
		}

		y.count = order - 1; //novo tamanho de y

		for(int j = x.count ; j> i ; j--)//qdo coloca novos x dentro do y tem que reorganizar os filhos
		{				
			x.child[j+1] = x.child[j]; //reorganiza os filhos de x

		}
		x.child[i+1] = z; //redireciona os filhos de x

		for(int j = x.count; j> i; j--)
		{
			x.key[j + 1] = x.key[j]; //troca as posicoes
		}
		x.key[i] = y.key[order-1];//por ultimo atualiza atualiza a raiz, jogando o nodo pra cima

		y.key[order-1 ] = 0; //deleta os valores de onde iniciou. Do nodo q estourou, no caso

		for(int j = 0; j < order - 1; j++)
		{
			y.key[j + order] = 0; //deleta os valores velhos
		}

		x.count ++; 
	}

// ----------------------------------------------------------
// Metodo para inserir no nodo quando ele nao esta cheio
// ----------------------------------------------------------

	public void nonfullInsert(BNode x, int key, ReferenciaDataBlock ref)
	{
		int i = x.count; //pega o numero de chaves dentro de x pra fazer a verificacao

		if(x.leaf)
		{
			while(i >= 1 && key < x.key[i-1])//comeca a procurar o lugar corredo dele. Faz escorregar na arvore
			{
				x.key[i] = x.key[i-1];//muda os valores de lugar pra fazer espaco

				i--;
			}

			x.key[i] = key;//coloca o valor do idserial dentro do array do nodo
			x.rowIds = ref.DataBlock;//coloca o objeto inteiro dentro do nodo
			x.count ++; //incrementa o numero de chaves q esse nodo tem. Guarda a info pra usar depois

		}


		else
		{
			int j = 0;
			while(j < x.count  && key > x.key[j])//procura o local certo para o nodo filho
			{			           	
				j++;
			}

			if(x.child[j].count == order*2 - 1)//achou um nodo cheio
			{
				split(x,j,x.child[j]);//essa eh a funcao que divide o nodo

				if(key > x.key[j])
				{
					j++;
				}
			}

			nonfullInsert(x.child[j], key, ref);//recusao
		}
	}
//--------------------------------------------------------------
//metodo que insere de forma geral e no final chama a funcao insert non full para reorganizar a arvores
//--------------------------------------------------------------

	public void insert(BTree t, ReferenciaDataBlock ref)
	{
		BNode r = t.root;//a partir da raiz procura o nodo pra inserir corretamente
		if(r.count == 2*order - 1)//if q verifica se esta cheio. Se cair aqui faz o split
		{
			BNode s = new BNode(order,null);//cria um nodo novo

			t.root = s;    //dados para inicializar o nodo novo
	    			      
			s.leaf = false;
	    			       
			s.count = 0;   
	    			       
			s.child[0] = r;

			split(s,0,r);//split raiz passando (nodo interno que nao esteja cheio, index, nodo r com o qual farei o split) 

			nonfullInsert(s, ref.id, ref); //insere passando (nodo interno, idserial e o objeto com as infos do datablock)
		}
		else
			nonfullInsert(r, ref.id, ref);//se nao ta cheio so insere
	}

// ---------------------------------------------------------------------------------
// metod so pra imprimir a arvore
// ---------------------------------------------------------------------------------


	public void print(BNode n)
	{
		for(int i = 0; i < n.count; i++)
		{
			System.out.print(n.getValue(i)+" nodo raiz " );
		}

		if(!n.leaf)
		{

			for(int j = 0; j <= n.count  ; j++)
			{				  
				if(n.getChild(j) != null) 
				{			 
				System.out.println("child?");	  
				print(n.getChild(j).getChild(j));     
				}
			}
		}
	}
	

// ------------------------------------------------------------
// metodo que imprime um nodo especifico
// ------------------------------------------------------------

	public void SearchPrintNode( BTree T,int x)
	{
		BNode temp= new BNode(order,null);

		temp= search(T.root,x);

		if (temp==null)
		{

		System.out.println("Essa chave não existe na B-tree");
		}

		else
		{

		print(temp);
		}


	}

//--------------------------------------------------------------
//metodo para deletar um nodo especifico
//--------------------------------------------------------------

       public void deleteKey(BTree t, int key)
       {
			       
		BNode temp = new BNode(order,null);
			
		temp = search(t.root,key);

		if(temp.leaf && temp.count > order - 1)
		{
			int i = 0;

			while( key > temp.getValue(i))
			{
				i++;
			}
			for(int j = i; j < 2*order - 2; j++)
			{
				temp.key[j] = temp.getValue(j+1);
			}
			temp.count --;
		
		}
		else
		{
			System.out.println("Esse nodo é uma folha ou tem ordem -1 de chaves.");
		}
       }


}