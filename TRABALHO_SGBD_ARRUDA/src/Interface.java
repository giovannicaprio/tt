import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Interface {
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
			System.out.println("Escolha uma das opções abaixo:");
			System.out.println("1 - Inserir Registro.");
			System.out.println("2 - Excluir Registro.");
			System.out.println("3 - Atualizar Registro.");
			System.out.println("4 - Buscar Resgistro.");
			System.out.println("5 - Sair do sistema.\n");
			valor = sc.nextLine();

			switch (Integer.parseInt(valor.trim())) {
			case 1:
<<<<<<< HEAD
				System.out.println("Inserir o texto:");
				String sDadosDigitados = sc.nextLine();
				iID++;
				// Recupera o rowId q ainda não está cheio
				int rowId = funcoes.RetornaIndicePrimeiroDataBlockVazio();
				// Criando um objeto de referência que irá retornar com os dados
				// principais do datablock
				ReferenciaDataBlock oRefDataBlock = null;
				// Grava o texto no DataBlock
				if (funcoes.GravaDataBlock(iID, sDadosDigitados,
						String.valueOf(rowId), oRefDataBlock)) {
				
					//cria a arvore de ordem 2
					BTree tree = new BTree(2);// Btree GIOVANNI
					
					//adiciona os ids seriais  na arvore
					//problema: ainda nao estou passando o objeto pra arvore, o que implica em ainda n ter o rowid
					tree.insert(tree, oRefDataBlock.id);
					
					
					System.out.println("Dados registrados com sucesso!");
				} else {
					System.out.println("Erro ao salvar dados");
=======
=======
>>>>>>> RenanCS/master
				
				 opBusca = "";
				System.out.println("Escolha a opção de inserção: [1 - Num. JSON], [2 - Texto], [3 - Retornar ao Menu] \n");
				opBusca = sc.nextLine();
				
				switch (opBusca) {
				case "1":
					System.out.println("Inserir o numero do JSON:");
					String sDadosDigitados = sc.nextLine();
					String sDadosJson = funcoes.LeituraArquivoJson(Integer.parseInt(sDadosDigitados));
					iID++;
					// Recupera o rowId q ainda não está cheio
					int rowId = funcoes.RetornaIndicePrimeiroDataBlockVazio();
					// Criando um objeto de referência que irá retornar com os dados
					// principais do datablock
					 oRefDataBlock = new ReferenciaDataBlock();
					// Grava o texto no DataBlock
					if (funcoes.GravaDataBlock(iID, sDadosJson, String.valueOf(rowId),oRefDataBlock)) {
						
						//Grava dados no buffer
						oBuffer.adicionaIDSerialDados(iID,sDadosJson);
						//############# GRAVAR O OBJ REFDATABLOCK NA ARVORE 
							//REVER ESTA PARTE tree.insert(t, oRefDataBlock.id);
						//################################################
						
						System.out.println("Dados registrados com sucesso!");
					} else {
						System.out.println("Erro ao salvar dados");
					}
					
					break;
					
				case "2":
					// Recupera os indices dos datablocks que estão ocupados
					listaDataBlockUsados= funcoes.RecuperaDataBlocksUsados();
					System.out.println("Inserir o texto:");
					String sDadosDigitados1 = sc.nextLine();
					iID++;
					
					// Recupera o rowId q ainda não está cheio
					int rowId1 = funcoes.RetornaIndicePrimeiroDataBlockVazio();
					
					// Criando um objeto de referência que irá retornar com os dados principais do datablock
					 oRefDataBlock = new ReferenciaDataBlock();
					
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
					break;
				case "3":
					
					break;
<<<<<<< HEAD
>>>>>>> RenanCS/master
=======
>>>>>>> RenanCS/master
				}

				
				break;
			case 2:
				String opBuscaDelete = "";
				System.out.println("Digite o id que deseja excluir:\n");
				opBuscaDelete = sc.nextLine();
				
				//Grava dados no buffer
				if ( oBuffer.ExcluirDoBuffer(Integer.parseInt(opBuscaDelete.toString().trim()))){
				
					//############# REMOVER OBJ REFDATABLOCK DA ARVORE 
						//REVER ESTA PARTE tree.REMOVEt(t, oRefDataBlock.id);
					//################################################
			
					if (funcoes.BuscaRowID(Integer.parseInt(opBuscaDelete.trim()))) {
						System.out.println("Registro excluído do sucesso!");
					} else {
						System.out.println("Registro não encontrado!");
					}
				}else{
					System.out.println("Registro não encontrado!");	
				}
				
				
				break;
			case 3:

				break;
			case 4:
				 opBusca = "";
				System.out.println("Escolha a opção de busca: [1 - ID], [2 - Texto], [3 - Retornar ao Menu] \n");
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

}