import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
 
import java.util.HashMap;
import java.util.Scanner;

public class Interface {
	BTree tree = new BTree(2);//Btree GIOVANNI
	//Variáveis globais
	static Funcoes funcoes = new   Funcoes();
	HashMap<Integer, String> indices;
	//Id autoincremento
	static int iID = 0;
	
	public static void main(String[] args) throws IOException {
		funcoes.LeituraArquivoJson();
		//Criação do arquivo 
		funcoes.CriarArquivo();
		
		//Montagem dos indices do datablock 
		StringBuilder indiceFree = new StringBuilder();
		for(int i = 3; i < 65.633; i++){ //
			indiceFree.append(i +  (i < 65.632? "," : ""));
		}
		funcoes.GravarIndice(indiceFree.toString(),"1,2","3","1","3");
		//Recupera os indices
		HashMap<Integer, String> indices = funcoes.RecuperaIndice();
		//Montagem do menu		
		MontagemMenu();
	}

	//Método que monta a interface do sistema.
		public static void MontagemMenu() throws IOException{
			
			String valor ="1"; 
			while(Integer.parseInt(valor.trim()) > 0 && Integer.parseInt(valor.trim()) < 5){
				
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
					System.out.println("Inserir o texto:");
					String sDadosDigitados=sc.nextLine();
					iID++;
					//Recupera o rowId q ainda não está cheio
					int rowId = funcoes.RetornaIndicePrimeiroDataBlockVazio();
					//Criando um objeto de referência que irá retornar com os dados principais do datablock
					ReferenciaDataBlock oRefDataBlock = null;
					//Grava o texto no DataBlock
					if(funcoes.GravaDataBlock(iID, sDadosDigitados, String.valueOf(rowId), oRefDataBlock)){
						System.out.println("Dados registrados com sucesso!");
					}else{
						System.out.println("Erro ao salvar dados");
					}					
					break;
				case 2:
					String opBuscaDelete= "";
					System.out.println("Digite o id que deseja excluir:\n");
					opBuscaDelete = sc.nextLine();
					if(funcoes.BuscaRowID(Integer.parseInt(opBuscaDelete.trim()))){
						System.out.println("Registro excluído do sucesso!");
					}else{
						System.out.println("Registro não encontrado!");
					}
					break;
				case 3:
					
					break;
				case 4:
					String opBusca= "";
					System.out.println("Escolha a opção de busca: [1 - ID], [2 - Texto], [3 - Retornar ao Menu] \n");
					opBusca = sc.nextLine();
					//Recupera os indices dos datablocks que estão ocupados 
					int listaDataBlockUsados[] = funcoes.RecuperaDataBlocksUsados();
					if(opBusca.equals("1")){
						System.out.println("Digite o ID:\n");
						opBusca = sc.nextLine();
						funcoes.MontaSaida(funcoes.BuscaDataBlock(Integer.parseInt(opBusca), "", listaDataBlockUsados));
						break;
					}else if(opBusca.equals("2")){
						System.out.println("Digite a palavra que procura:\n");
						opBusca = sc.nextLine();
						funcoes.MontaSaida(funcoes.BuscaDataBlock(0,opBusca.trim(), listaDataBlockUsados));		
						
					}else if(opBusca.equals("3")){
						break;
					}else{
						System.out.println("Você errou a opção,\n sistema irá retornar ao menu.");
					}					
					
					break;
				case 5:
					System.exit(0);
					break;
				}
				
							
			}	
		}
	
	
	
		
		
}