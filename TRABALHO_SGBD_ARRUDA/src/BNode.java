

public class BNode
{
	static int t;  //variavel que determina a ordem da arvore
	int count; // numero de chaves no nodo
	int key[];  // array de chaves
	int rowIds;  // array de objetos que referenciam o datablock
	BNode child[]; //array de referencias para nodos
	boolean leaf; //true indica que eh folha
	BNode parent;  //nodo pai

	//construtor vazio para o nodo pq depois eu crio os dados e faco o setup
	public BNode()
	{}

	public BNode(int t, BNode parent)
	{
		this.t = t;  // ordem da btree
		this.parent = parent;
		key = new int[2*t - 1];  // array de tamanho que ela suporta e que vai guardar os rowids
		child = new BNode[2*t]; 
		leaf = true; //no inicio todos os nodos sao folhas
		count = 0; //quando adiciono chaves eles passam a ser ramos. Esse contador ajuda a controlar
	}

	public int getValue(int index)
	{
		return key[index];
	}

	public BNode getChild(int index)
	{
		return child[index];
	}


}