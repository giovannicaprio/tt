


public class BTree
{

// here are variables available to tree

	static int order; // order of tree

	BNode root;  //every tree has at least a root node


// ---------------------------------------------------------
// here is the constructor for tree                        |
// ---------------------------------------------------------


	public BTree(int order)
	{
		this.order = order;

		root = new BNode(order, null);

	}


// --------------------------------------------------------
// this will be method to search for a given node where   |
// we want to insert a key value. this method is called   |
// from SearchnPrintNode.  It returns a node with key     |
// value in it                                            |
// --------------------------------------------------------


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

	public void nonfullInsert(BNode x, int key)
	{
		int i = x.count; //pega o numero de chaves dentro de x pra fazer a verificacao

		if(x.leaf)
		{
			while(i >= 1 && key < x.key[i-1])//comeca a procurar o lugar corredo dele. Faz escorregar na arvore
			{
				x.key[i] = x.key[i-1];//muda os valores de lugar pra fazer espaco

				i--;
			}

			x.key[i] = key;//coloca o valor dentro do nodo
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

			nonfullInsert(x.child[j],key);//recurse
		}
	}
//--------------------------------------------------------------
//metodo que insere de forma geral e no final chama a funcao insert non full para reorganizar a arvores
//--------------------------------------------------------------

	public void insert(BTree t, int key)
	{
		BNode r = t.root;//a partir da raiz procura o nodo pra inserir corretamente
		if(r.count == 2*order - 1)//if q verifica se esta cheio. Se cair aqui faz o split
		{
			BNode s = new BNode(order,null);//cria um nodo novo

			t.root = s;    //dados para inicializar o nodo novo
	    			      
			s.leaf = false;
	    			       
			s.count = 0;   
	    			       
			s.child[0] = r;

			split(s,0,r);//split raix

			nonfullInsert(s, key); //insere
		}
		else
			nonfullInsert(r,key);//se nao ta cheio so insere
	}

// ---------------------------------------------------------------------------------
// this will be method to print out a node, or recurses when root node is not leaf |
// ---------------------------------------------------------------------------------


	public void print(BNode n)
	{
		for(int i = 0; i < n.count; i++)
		{
			System.out.print(n.getValue(i)+" " );//this part prints root node
		}

		if(!n.leaf)//this is called when root is not leaf;
		{

			for(int j = 0; j <= n.count  ; j++)//in this loop we recurse
			{				  //to print out tree in
				if(n.getChild(j) != null) //preorder fashion.
				{			  //going from left most
				System.out.println();	  //child to right most
				print(n.getChild(j));     //child.
				}
			}
		}
	}
	
	//metodo para imprimir a arvore 

	public void toString(BNode n)
	{
		for(int i = 0; i < n.count; i++)
		{
			System.out.print(n.getValue(i)+" " );//this part prints root node
		}

		if(!n.leaf)//this is called when root is not leaf;
		{

			for(int j = 0; j <= n.count  ; j++)//in this loop we recurse
			{				  //to print out tree in
				if(n.getChild(j) != null) //preorder fashion.
				{			  //going from left most
				System.out.println();	  //child to right most
				print(n.getChild(j));     //child.
				}
			}
		}
	}
	

// ------------------------------------------------------------
// this will be method to print out a node                    |
// ------------------------------------------------------------

	public void SearchPrintNode( BTree T,int x)
	{
		BNode temp= new BNode(order,null);

		temp= search(T.root,x);

		if (temp==null)
		{

		System.out.println("The Key does not exist in this tree");
		}

		else
		{

		print(temp);
		}


	}

//--------------------------------------------------------------
//this method will delete a key value from the leaf node it is |
//in.  We will use the search method to traverse through the   |
//tree to find the node where the key is in.  We will then     |
//iterated through key[] array until we get to node and will   |
//assign k[i] = k[i+1] overwriting key we want to delete and   |
//keeping blank spots out as well.  Note that this is the most |
//simple case of delete that there is and we will not have time|
//to implement all cases properly.                             |
//--------------------------------------------------------------

       public void deleteKey(BTree t, int key)
       {
			       
		BNode temp = new BNode(order,null);//temp Bnode
			
		temp = search(t.root,key);//call of search method on tree for key

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
			System.out.println("This node is either not a leaf or has less than order - 1 keys.");
		}
       }


}