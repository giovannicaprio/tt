import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;


public class Interface {
	
	/*
	 * Cara, a btree ficou bem simples. Vou te mostrar o passo a passo. Vou tentar fazer o possivel pra tu so precisar copiar e colar
	 * tendo que alterar pouca coisa pra adaptar
	 * 
	 * cria a arvore 
	 * cria ela apenas um vez ne. Acho que inclusive tu pode criar uma variavel statica pra chamar em outras classes se precisar
	 * 
	 * 	BTree tree = new BTree(5); // aquele 4 é a ordem dela. Pode criar com a ordem que tu quiser. 
	 * 							  //Se nao me engano a ordem do trabalho é 5, por isso deixei o 5
	 * 							 // a ordem eh d2 e tal. Se tiver 4 dentro do nodo ela ta de boa, quando entra o quinto ela faz split
	 * 
	 * 
	 * 
	 * 
	 * Inserir na btree eh uma barbada. É só chamar o metodo insert e passa ela mesma junto com o objeto referenciaDataBlock
	 * 
	 *      ReferenciaDataBlock ref = new ReferenciaDataBlock();
            ref.id = 34;
            ref.DataBlock = 78;
        	tree.insert(tree,ref);
	 *         
	 *         
	 * 
	 * 
	 * Pesquisar um rowid
	 * 
	 * Pra pesquisar basta chamar a funcao search, passando a raiz (tree.root), o idserial e dar um .rowIds
	 * 
	 * essa funcao retornar um int, logo ja pode atribui pra onde tu quer
	 * 
	 * int rowid = tree.search(tree.root,IDSERIAL).getvalue(IDSERIAL);
	 * 
	 * 
	 * DELETAR é barbada, basta dar um delete passando a arvore pra ela mesma e o IDSERIAL
	 * 
	 * tree.deleteKey(tree, IDSERIAL);

	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// Variáveis globais
	static Buffer oBuffer = new Buffer();
	static Funcoes funcoes = new Funcoes();
	HashMap<Integer, String> indices;
	// Id autoincremento
	static int iID = 0;

	public static void main(String[] args) throws IOException {
		// Criação do arquivo
		funcoes.CriarArquivo();

		// Montagem dos indices do datablock
		StringBuilder indiceFree = new StringBuilder();
		for (int i = 3; i < 65.633; i++) { //
			indiceFree.append(i + (i < 65.632 ? "," : ""));
		}
		funcoes.GravarIndice(indiceFree.toString(), "1,2", "3", "1", "3");
		// Recupera os indices
		HashMap<Integer, String> indices = funcoes.RecuperaIndice();
		// Montagem do menu
		MontagemMenu();
	}

	// Método que monta a interface do sistema.
	public static void MontagemMenu() throws IOException {
		BTree tree = new BTree(2);// Btree GIOVANNI
		ReferenciaDataBlock oRefDataBlock;
		int listaDataBlockUsados[];
		String opBusca = "";
		String valor = "1";

		
		while (Integer.parseInt(valor.trim()) > 0 	&& Integer.parseInt(valor.trim()) < 5) {

			Scanner sc = new Scanner(System.in);
			System.out.println("Bem Vindo ao Metadado SGBD");
			System.out.println("Escolha uma das opcoes abaixo:");
			System.out.println("1 - Inserir Registro.");
			System.out.println("2 - Excluir Registro.");
			System.out.println("3 - Atualizar Registro.");
			System.out.println("4 - Buscar Resgistro.");
			System.out.println("5 - Sair do sistema.\n");
			valor = sc.nextLine();

			switch (Integer.parseInt(valor.trim())) {
			case 1:
				
				 opBusca = "";
				System.out.println("Escolha a opcao de insercao: [1 - Num. JSON], [2 - Texto], [3 - Retornar ao Menu] \n");
				opBusca = sc.nextLine();
				
				switch (opBusca) {
				case "1":
					System.out.println("Inserir o numero do JSON:");
					String sDadosDigitados = sc.nextLine();
					String sDadosJson = funcoes.LeituraArquivoJson(Integer.parseInt(sDadosDigitados));
					iID++;
					 oRefDataBlock = new ReferenciaDataBlock();
					 
					InserirRegistroBanco(oRefDataBlock,sDadosJson,iID);
					
					break;
					
				case "2":
					// Recupera os indices dos datablocks que estão ocupados
					listaDataBlockUsados= funcoes.RecuperaDataBlocksUsados();
					System.out.println("Inserir o texto:");
					String sDadosDigitados1 = sc.nextLine();
					iID++;
					// Criando um objeto de referência que irá retornar com os dados principais do datablock
					oRefDataBlock = new ReferenciaDataBlock();
					
					InserirRegistroBanco(oRefDataBlock,sDadosDigitados1,iID);
					
					break;
				case "3":
					
					break;
				}

				
				break;
			case 2:
				String opBuscaDelete = "";
				System.out.println("Digite o id que deseja excluir:\n");
				opBuscaDelete = sc.nextLine();
				//######EXCLUIIR DADOS 
				ExcluirDados(opBuscaDelete.toString().trim());
							
				break;
			case 3:
				opBusca = "";
				System.out.println("Digite o ID:\n");
				opBusca = sc.nextLine();
				int ID = Integer.parseInt( opBusca.toString().trim());
				System.out.println("Digite o novo text:\n");
				opBusca = sc.nextLine();
				
				//######EXCLUIIR DADOS 
				ExcluirDados(String.valueOf(ID));
				
				//###### INSERIR DADOS 
				 oRefDataBlock = new ReferenciaDataBlock(); 
				 InserirRegistroBanco(oRefDataBlock,opBusca,ID);
					
				

				break;
			case 4:
				 opBusca = "";
				System.out.println("Escolha a opcao de busca: [1 - ID], [2 - Texto], [3 - Retornar ao Menu] \n");
				opBusca = sc.nextLine();
				// Recupera os indices dos datablocks que estão ocupados
				 listaDataBlockUsados = funcoes.RecuperaDataBlocksUsados();
				if (opBusca.equals("1")) {
					System.out.println("Digite o ID:\n");
					opBusca = sc.nextLine();
					
					//##############BUSCA DO BUFFER SOMENTE SE FOR POR ID
					String sDadosDoBuffer = oBuffer.RecuperaDoBufferID(Integer.parseInt(opBusca.toString().trim()));
					//####################################################
					
					if(sDadosDoBuffer.toString().trim()  == ""){
						funcoes.MontaSaida(funcoes.BuscaDataBlock(Integer.parseInt(opBusca), "", listaDataBlockUsados));
					}else{
						 System.out.println("ID=" + iID + " Value =" + sDadosDoBuffer + "\n");
					}
					
					break;
				} else if (opBusca.equals("2")) {
					System.out.println("Digite a palavra que procura:\n");
					opBusca = sc.nextLine();
					funcoes.MontaSaida(funcoes.BuscaDataBlock(0,opBusca.trim(), listaDataBlockUsados));

				} else if (opBusca.equals("3")) {
					break;
				} else {
					System.out
							.println("Você errou a opção,\n sistema irá retornar ao menu.");
				}
				

				break;
			case 5:
				System.exit(0);
				break;
			}

		}
	}

	
	
	public static void GravarDadosJSONemBinario() throws IOException {
		for (int i = 1; i < 7; i++) {
			String sDadosJson = funcoes.LeituraArquivoJson(i);
			iID++;
			// Recupera o rowId q ainda não está cheio
			int rowId = funcoes.RetornaIndicePrimeiroDataBlockVazio();
			// Criando um objeto de referência que irá retornar com os dados
			// principais do datablock
			ReferenciaDataBlock oRefDataBlock = new ReferenciaDataBlock();
			// Grava o texto no DataBlock
			if (funcoes.GravaDataBlock(iID, sDadosJson, String.valueOf(rowId),oRefDataBlock)) {
				System.out.println("Dados registrados com sucesso!");
			} else {
				System.out.println("Erro ao salvar dados");
			}
		}

	}

	
	private static void ExcluirDados(String ID){
		//Grava dados no buffer
		if ( oBuffer.ExcluirDoBuffer(Integer.parseInt(ID.toString().trim()))){
		
			//############# REMOVER OBJ REFDATABLOCK DA ARVORE 
				//REVER ESTA PARTE tree.REMOVEt(t, oRefDataBlock.id);
			//################################################
	
			if (funcoes.BuscaRowID(Integer.parseInt(ID.trim()))) {
				System.out.println("Registro excluído do sucesso!");
			} else {
				System.out.println("Registro não encontrado!");
			}
		}else{
			System.out.println("Registro não encontrado!");	
		}
	}
	
	private static void InserirRegistroBanco(ReferenciaDataBlock oRefDataBlock, String sDadosDigitados1, int iID ) throws IOException{
		// Recupera o rowId q ainda não está cheio
		int rowId1 = funcoes.RetornaIndicePrimeiroDataBlockVazio();
		
		 // Grava o texto no DataBlock
		if (funcoes.GravaDataBlock(iID, sDadosDigitados1,String.valueOf(rowId1), oRefDataBlock)) {
			
			//Grava dados no buffer
			oBuffer.adicionaIDSerialDados(iID,sDadosDigitados1);
			
			//############# GRAVAR O OBJ REFDATABLOCK NA ARVORE 
					//REVER ESTA PARTE tree.insert(t, oRefDataBlock.id);
			//################################################
			
			System.out.println("Dados registrados com sucesso!");
		} else {
			System.out.println("Erro ao salvar dados");
		}	
		
	}
	
}