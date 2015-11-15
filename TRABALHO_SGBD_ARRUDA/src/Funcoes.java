import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.io.FileNotFoundException; 
import java.io.FileReader; 
import java.io.IOException; 

import org.json.simple.JSONObject; 
import org.json.simple.parser.JSONParser; 
import org.json.simple.parser.ParseException; 

public class Funcoes {
	

	

	int listaDataBlockUsados[] = new int[20];

	/*
	 * 
	 * public static void main(String[] args) throws IOException {
	 * CriarArquivo();
	 * 
	 * 
	 * 
	 * listaDataBlockUsados[0] = 5;
	 * 
	 * GravarIndice("3,4,5,6,7,8,9,10,11,12,13","3","1");
	 * 
	 * HashMap<Integer, String> indices = RecuperaIndice();
	 * 
	 * //Teste INSERT 1 int id = 200; String texto = "Bla Bla bla |"; String
	 * rowID="5;2"; GravaDataBlock( id, texto, rowID);
	 * System.out.println("GRAVOU 1");
	 * 
	 * //Teste INSERT 2 int id2 = 300; String texto2 =
	 * "Padre é baleado em tentativa de assalto em Porto Alegre |"; String
	 * rowID2="5;3"; GravaDataBlock( id2, texto2, rowID2);
	 * System.out.println("GRAVOU 2");
	 * 
	 * 
	 * //Teste DELETE int id3=300; String rowID3 = "5;3"; if
	 * (ExcluirDados(id3,rowID3)){ System.out.println("Exclui com sucesso"); }
	 * 
	 * 
	 * //Teste SELECT int id4 = 0; String texto3 = "Padre"; HashMap<Integer,
	 * String> tuplas = BuscaDataBlock(id4, texto3, listaDataBlockUsados);
	 * 
	 * for(Entry<Integer, String> entry : tuplas.entrySet()) { String sDadosIns
	 * = entry.getValue(); int iID = entry.getKey(); System.out.println("ID=" +
	 * iID + " Value =" + sDadosIns + "\n\n"); }
	 * 
	 * 
	 * 
	 * }
	 */

	public static int RetornaIndicePrimeiroDataBlockVazio() throws IOException {
		HashMap<String, String> indices = new HashMap<String, String>();
		LeituraArquivo(1, indices, 1);
		int iDataBlockNaoCheio = -1;
		int iDataBlockAtual = 0;
		String sDadosInd = indices.get("free");
		String sDadosCheios = indices.get("full");
		String sIndicesCheios[] = sDadosCheios.split(",");
		boolean EstaCheio = false;
		ArrayList<Integer> aIndicesVazios = new ArrayList<Integer>();
		ArrayList<Integer> aIndicesCheios = new ArrayList<Integer>();
		
		//Preenche o arraylist com os indices que estão cheios
		for(int i =0; i < sIndicesCheios.length; i++){
			aIndicesCheios.add( Integer.parseInt(sIndicesCheios[i]));
		}		
		
		// Recupera os indices
		if (sDadosInd.contains(",")) {
			int iCount = 0;
			sDadosInd = sDadosInd.replace("/0","").replace("/","");
			String sIndices[] = sDadosInd.split(",");
			// int sIndiceCheio[] = null;
			while (iCount < sIndices.length) {
				iDataBlockAtual = Integer.parseInt(sIndices[iCount].trim());
				byte[] DataBlock = LeituraArquivo(2, null, iDataBlockAtual);
				int iQuantCheio = 0;
				for (int i = 3000; i < 3276; i++) {
					if (DataBlock[i] != 0) {
						iQuantCheio++;
						if (iQuantCheio > 10) {
							EstaCheio = true;
						}
					}
				}
				if (!EstaCheio) {
					aIndicesVazios.add(iDataBlockAtual);

					if (iDataBlockNaoCheio == -1)
						iDataBlockNaoCheio = iDataBlockAtual;

				} else {
					aIndicesCheios.add(iDataBlockAtual);
				}
				iCount++;
			}

		}

		String sDadosFree = "";
		for (int vazio : aIndicesVazios) {
			if (!"".equals(sDadosFree))
				sDadosFree += ",";
			sDadosFree += String.valueOf(vazio);
		}
		indices.put("free", sDadosInd);
		String sDadosFull = "";
		for (int vazio : aIndicesCheios) {
			if (!"".equals(sDadosFull))
				sDadosFull += ",";
			sDadosFull += String.valueOf(vazio);
		}
		indices.put("full", sDadosInd);
		
		GravarIndice(sDadosFree, sDadosFull,"3", "1",String.valueOf(iDataBlockAtual));
		
		return iDataBlockNaoCheio;
	}

	public static HashMap<Integer, String> BuscaDataBlock(int id,String sTexto, int listaDataBlock[]) throws IOException {
		RandomAccessFile leitura = new RandomAccessFile("arquivo.bin", "rw");
		// Referencia de ter feito UPDATE ou INSERT primeira posicao, al�m de
		// ter gravado
		boolean ExcluirDados = false;
		// Hash Map
		HashMap<Integer, String> mapDataBlock = new HashMap<Integer, String>();
		HashMap<Integer, String> linhas = new HashMap<Integer, String>();
		// Hash Map indice do datablock
		int indiceDataBlock[] = new int[50];
		// Posi��o de refer�ncia do valor dentro do rowid
		int posrowid = 0;

		boolean bNaoEncontrou = true;
		int TamDataBlockUtilizado = 0;

		while (bNaoEncontrou && TamDataBlockUtilizado < listaDataBlock.length
				&& listaDataBlock[TamDataBlockUtilizado] != 0) {
			// Criar um bytes do tamanho de cada datablock auxiliares
			byte[] datablockAux = new byte[4096];

			// Referencia para ler a posi��o inicial do 4kb
			int posIni = 4096 * listaDataBlock[TamDataBlockUtilizado]; // Posicao do datablock
			posIni = posIni - 4096;

			// leitura de posição inicial
			leitura.seek(posIni);

			// passagem do bytes vazios e retorno dele preenchido com os dados
			// daquela posi��o espec�fica
			leitura.read(datablockAux, 0, datablockAux.length);
			// Refer�ncia para tamanho do ID
			int tamIDBytes = 5;
			// Auxiliar para ler os dados do byte e Ler ID do dado
			StringBuilder sTamIndice = new StringBuilder();
			StringBuilder sbDados = new StringBuilder();
			StringBuilder sbID = new StringBuilder();
			
			for (int i = 200; i < datablockAux.length; i++) {

				// Diferente de dados em branco 0 bytes
				if (datablockAux[i] != 0) {
					// Se n�o for \n como refer�ncia de parada
					if (datablockAux[i] != 124) {
						char cLetra = (char) datablockAux[i];
						// Decrementa1 quando for ID
						tamIDBytes--;
						if (tamIDBytes >= 0) {
							sbID.append(cLetra); // Guarda a ID
						} else {
							sbDados.append(cLetra); // Guarda o texto
						}
					} else {
						// Faz a primeira verificação por ID
						if (id != 0) {
							if (id == Integer.parseInt(sbID.toString().trim())) {
								linhas.put(Integer.parseInt(sbID.toString()
										.trim()), sbDados.toString());
								return linhas;
							}
							// Faz a verificação por texto
						} else if (sTexto.trim() != "") {
							if (sbDados.toString().contains(sTexto.trim())) {
								linhas.put(Integer.parseInt(sbID.toString()
										.trim()), sbDados.toString());
							}
						}
						tamIDBytes = 5;
						sbDados = new StringBuilder();
						sbID = new StringBuilder();
					}
				}
			}
			TamDataBlockUtilizado++;

		}
		return linhas;

	}

	public static boolean ExcluirDados(int id, String rowID) throws IOException {
		RandomAccessFile leitura = new RandomAccessFile("arquivo.bin", "rw");
		// Referencia de ter feito UPDATE ou INSERT primeira posicao, al�m de
		// ter gravado
		boolean ExcluirDados = false;
		// Refer�ncia para tamanho do ID
		int tamIDBytes = 5;
		// Hash Map
		HashMap<Integer, String> mapDataBlock = new HashMap<Integer, String>();
		// Hash Map indice do datablock
		int indiceDataBlock[] = new int[50];
		// Posi��o de refer�ncia do valor dentro do rowid
		int posrowid = 0;
		// Auxiliar para ler os dados do byte e Ler ID do dado
		StringBuilder sTamIndice = new StringBuilder();
		StringBuilder sbDados = new StringBuilder();
		StringBuilder sbID = new StringBuilder();
		// Criar um bytes do tamanho de cada datablock auxiliares
		byte[] datablockAux = new byte[4096];
		// Pega a posi��o de mem�ria do dado dentro do datablock 5;2 ficar� so o
		// 2
		/*
		int refPos = Integer.parseInt(rowID.split(";")[1]);
		// Referencia para ler a posi��o inicial do 4kb
		int posIni = 4096 * Integer.parseInt(rowID.split(";")[0]); // Restar� so
		*/														// 5 do 5;2
		int posIni =4096 * Integer.parseInt(rowID);
		posIni = posIni - 4096;
		// leitura de posição inicial
		leitura.seek(posIni);
		// passagem do bytes vazios e retorno dele preenchido com os dados
		// daquela posi��o espec�fica
		leitura.read(datablockAux, 0, datablockAux.length);

		int icount = 0;

		// Monta a indice
		for (int i = 0; i < datablockAux.length; i++) {
			if (i < 200) {

				char cLetra = (char) datablockAux[i];
				// Fim o tamanho do primeiro indice
				if (cLetra == '/') {

					if (sTamIndice.toString().trim().length() == 0) {
						sTamIndice.append("0");
					}
					indiceDataBlock[icount] = Integer.parseInt(sTamIndice.toString().trim());
					sTamIndice = new StringBuilder();
					icount++;
				} else {
					sTamIndice.append(cLetra);
				}
			}
		}

		// Percorrer e converter o bytes
		for (int i = 200; i < datablockAux.length; i++) {

			// Diferente de dados em branco 0 bytes
			if (datablockAux[i] != 0) {
				// Se n�o for \n como refer�ncia de parada
				if (datablockAux[i] != 124) {
					char cLetra = (char) datablockAux[i];
					// Decrementa1 quando for ID
					tamIDBytes--;
					if (tamIDBytes >= 0) {
						sbID.append(cLetra); // Guarda a ID
					} else {
						sbDados.append(cLetra); // Guarda o texto
					}
				} else {
					mapDataBlock.put(posrowid,
							sbID.toString() + sbDados.toString());
					posrowid++;
					tamIDBytes = 5;
					sbDados = new StringBuilder();
					sbID = new StringBuilder();
				}
			}
		}

		for (Entry<Integer, String> entry : mapDataBlock.entrySet()) {
			String sDadosIns = entry.getValue();
			int pos = entry.getKey();
			int ini = 0;
			int ilimite = indiceDataBlock[pos];

			if (pos != 0) {
				ilimite = 0;
				int posaux = 0;
				while (posaux < pos) {
					ini += indiceDataBlock[posaux];
					posaux++;
				}

				ilimite = ini + indiceDataBlock[pos];
			}

			if(sDadosIns.substring(0,5).toString().trim().length() > 0){
				if(Integer.parseInt(sDadosIns.substring(0,5).trim()) == id){
				
					for (int i = ini; i < ilimite; i++) {
						datablockAux[200 + i] = 0;
					}
					indiceDataBlock[pos] = 0;
					ExcluirDados = true;
					break;
						
				}
			}

		}

		// Regrava o indice
		StringBuilder indice = new StringBuilder();
		for (int i = 0; i < 50; i++) {
			indice.append(String.valueOf(indiceDataBlock[i]) + "/");
		}
		// Grava o indice
		System.arraycopy(indice.toString().getBytes(), 0, datablockAux, 0,
				indice.toString().getBytes("utf-8").length);
		// Salva o arquivo dos bytes no arquivo principal
		leitura.seek(posIni);
		leitura.write(datablockAux);

		return ExcluirDados;

	}

	public static boolean GravaDataBlock(int id, String texto, String rowID, ReferenciaDataBlock oRefDataBlock)throws IOException {
		texto = texto + "|";
		RandomAccessFile leitura = new RandomAccessFile("arquivo.bin", "rw");
		// Referencia de ter feito UPDATE ou INSERT primeira posicao, al�m de
		// ter gravado
		boolean bGravou = false;
		// Refer�ncia para tamanho do ID
		int tamIDBytes = 5;
		// Hash Map
		HashMap<Integer, String> mapDataBlock = new HashMap<Integer, String>();
		// Hash Map indice do datablock
		int indiceDataBlock[] = new int[200];
		// Posi��o de refer�ncia do valor dentro do rowid
		int posrowid = 0;
		// Auxiliar para ler os dados do byte e Ler ID do dado
		StringBuilder sTamIndice = new StringBuilder();
		StringBuilder sbDados = new StringBuilder();
		StringBuilder sbID = new StringBuilder();
		// Criar um bytes do tamanho de cada datablock auxiliares
		byte[] datablockAux = new byte[4096];
		// Pega a posi��o de mem�ria do dado dentro do datablock 5;2 ficar� so o
		// 2
		//###int refPos = Integer.parseInt(rowID.split(";")[1]);
		// Referencia para ler a posi��o inicial do 4kb
		//#####int posIni = 4096 * Integer.parseInt(rowID.split(";")[0]); // Restar� so
		int posIni = 4096 * Integer.parseInt(rowID.trim()); // Restar� so
																// 5 do 5;2
		posIni = posIni - 4096;
		// leitura de posição inicial
		leitura.seek(posIni);
		// passagem do bytes vazios e retorno dele preenchido com os dados
		// daquela posi��o espec�fica
		leitura.read(datablockAux, 0, datablockAux.length);

		int icount = 0;

		for (int i = 0; i < 200; i++) {
			if (i < 200) {

				char cLetra = (char) datablockAux[i];
				// Fim o tamanho do primeiro indice
				if (cLetra == '/') {

					if (sTamIndice.toString().trim().length() == 0) {
						sTamIndice.append("0");
					}
					indiceDataBlock[icount] = Integer.parseInt(sTamIndice.toString().trim());
					sTamIndice = new StringBuilder();
					icount++;
				} else {
					sTamIndice.append(cLetra);
				}

			}
		}

		// Percorrer e converter o bytes
		for (int i = 200; i < datablockAux.length; i++) {

			// Diferente de dados em branco 0 bytes
			if (datablockAux[i] != 0) {
				// Se n�o for \n como refer�ncia de parada
				if (datablockAux[i] != 10) {
					char cLetra = (char) datablockAux[i];
					// Decrementa1 quando for ID
					tamIDBytes--;
					if (tamIDBytes >= 0) {
						sbID.append(cLetra); // Guarda a ID
					} else {
						sbDados.append(cLetra); // Guarda o texto
					}
				} else {
					// Reset Variavel
					tamIDBytes = 7;
					if (sbID.toString().length() > 0) {
						int IDConvertdo = Integer.parseInt(sbID.toString());
						StringBuilder sJuncao = new StringBuilder();
						sJuncao.append(sbID.toString());
						// UPDATE
						if (IDConvertdo == id) {
							bGravou = true;
							sJuncao.append(texto);
						}
						// CONDICAO DE N�O SER O REGISTRO
						else {
							sJuncao.append(sbDados.toString());
						}

						mapDataBlock.put(posrowid, sJuncao.toString());
						posrowid++;
					}
				}
			}
		}

		// Grava na primeira posi��o os dados
		if (mapDataBlock.isEmpty()) {
			bGravou = true;

			for (int i = 0; i < indiceDataBlock.length; i++) {
				if (indiceDataBlock[i] == 0) {
					if (indiceDataBlock[i + 1] == 0) {
						if (texto.trim().getBytes("utf-8").length < 4096) {

							String campoID = String.valueOf(id).trim();
							while (campoID.length() < 5) {
								campoID = " " + campoID;
							}

							indiceDataBlock[i] = (campoID.toString() + texto.trim()).getBytes("utf-8").length;
							mapDataBlock.put(i, (campoID.toString() + texto.trim()).toString());
							break;

						}
					}
				}
			}
		}

		// Salvar no buffer
		// datablockAux = new byte[4096];
		StringBuilder sBGravarDados = new StringBuilder();
		// Remontando o indice
		String sValorAnt = "";
		StringBuilder indice = new StringBuilder();
		for (int i = 0; i < 50; i++) {
			indice.append(String.valueOf(indiceDataBlock[i]) + "/");
		}
		// Grava o indice
		System.arraycopy(indice.toString().getBytes(), 0, datablockAux, 0,indice.toString().getBytes("utf-8").length);

		if(!mapDataBlock.isEmpty()){
			oRefDataBlock.id = Integer.parseInt(rowID.trim());
			oRefDataBlock.listaInterna = indiceDataBlock;
		}
		
		int posIndice = 0;
		for (Entry<Integer, String> entry : mapDataBlock.entrySet()) {
			posIndice++;
			String sDadosIns = entry.getValue();
			int pos = entry.getKey();
			if (pos == 0) {
				System.arraycopy(sDadosIns.toString().getBytes("utf-8"), 0,datablockAux, 200,sDadosIns.toString().getBytes("utf-8").length);
			} else {
				posIndice = indiceDataBlock[posIndice - 1];
				System.arraycopy(sDadosIns.toString().getBytes("utf-8"), 0,	datablockAux, 200 + posIndice, sDadosIns.toString().getBytes("utf-8").length);
			}
		}
		leitura.seek(posIni);
		leitura.write(datablockAux);

		return bGravou;
	}

	public static byte[] LeituraArquivo(int iOpcao, HashMap mapa, int rowID)
			throws IOException {
		RandomAccessFile leitura = new RandomAccessFile("arquivo.bin", "rw");
		byte[] DataBlockAux = new byte[4096];
		StringBuilder sbDados = new StringBuilder();
		int posIni = 0;
		int posFim = 4096; // 4k
		int size = (int) leitura.length();

		// 1 - Indice, 2 - DataBlock espec�fico
		switch (iOpcao) {
		// Retorna o Indice em um hashMap
		case 1:

			// Gambiarra para ler a posi��o do datablock
			posIni = 4096 - posFim;
			posFim = posFim * rowID;

			int icountZero = 0;
			leitura.read(DataBlockAux, posIni, posFim);
			for (int i = 0; i < DataBlockAux.length; i++) {
				if (DataBlockAux[i] != 0 ) {
					char cLetra = (char) DataBlockAux[i];
					sbDados.append(cLetra);
				} else {
					if (icountZero == 0 && sbDados.toString().length() > 0 && DataBlockAux[i - 1] != 4 && DataBlockAux[i - 1] != 122) {
						// Armazena a lista FREE
						mapa.put("free", sbDados.toString());
						// Limpa a vari�vel
						sbDados = new StringBuilder();
						icountZero++;
						// Armazena a lista full
					}  else if (sbDados.toString().length() > 0 && icountZero == 1 && DataBlockAux[i - 1] != 4 && DataBlockAux[i - 1] != 122) {
						mapa.put("full", sbDados.toString());
						// Limpa a vari�vel
						sbDados = new StringBuilder();
						icountZero++;
					}else if (sbDados.toString().length() > 0 && icountZero == 2 && DataBlockAux[i - 1] != 4 && DataBlockAux[i - 1] != 122) {
							mapa.put("root", sbDados.toString());
							// Limpa a vari�vel
							sbDados = new StringBuilder();
							icountZero++;
					} else if (sbDados.toString().length() > 0	&& icountZero == 3 && DataBlockAux[i - 1] != 4 && DataBlockAux[i - 1]  != 122) {
						mapa.put("table", sbDados.toString());
						// Limpa a vari�vel
						sbDados = new StringBuilder();
						icountZero++;
	  			   }else if (sbDados.toString().length() > 0	&& icountZero > 3 && DataBlockAux[i - 1] != 4 && DataBlockAux[i - 1]  != 122) {
						mapa.put("half", sbDados.toString());
						// Limpa a vari�vel
						sbDados = new StringBuilder();
						break;
					}
				}
			}
			break;
		// CASO QUE RETORNA O DATABLOCK ESPEC�FICO
		case 2:

			// Gambiarra para ler a posi��o do datablock
			posIni = 4096 * rowID;
			posIni = posIni - 4096;
			leitura.seek(posIni);
			while (size > 0 && posIni < size) {
				leitura.read(DataBlockAux, 0, DataBlockAux.length);
				leitura.close();
				return DataBlockAux;
			}
		}
		leitura.close();
		return DataBlockAux;
	}

	// Método que recupera os indices e retorna em uma HashMap
	public static HashMap RecuperaIndice() throws IOException {
		HashMap<String, String> mapa = new HashMap<String, String>();
		LeituraArquivo(1, mapa, 1);
		return mapa;
	}

	// Método que grava os indices
	public static void GravarIndice(String listFree, String listaFull, String root, String table, String half) {
		try {
			// Acesso randomico
			RandomAccessFile escrita = new RandomAccessFile("arquivo.bin", "rw");
			// Referencias
			String sfree = listFree;
			String sFull = listaFull;
			String shalf = half;
			String sroot = root;
			String stable = table;
			// Colocando valores nas posi��es
			escrita.seek(0);
			escrita.write(sfree.getBytes("utf-8"));

			escrita.seek(1001);
			escrita.write(sFull.getBytes("utf-8"));
			
			escrita.seek(1050);
			escrita.write(sroot.getBytes("utf-8"));

			escrita.seek(1060);
			escrita.write(stable.getBytes("utf-8"));

			escrita.seek(2000);
			escrita.write(shalf.getBytes("utf-8"));

			// Fecha o arquivo
			escrita.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Erro = " + e);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Erro = " + e);
		}
	}

	// Método que criar um arquivo
	public static void CriarArquivo() {
		try {
			//Verifica se o arquivo existe
			File f = new File("arquivo.bin");
			if(!f.exists()) { 
				
				FileOutputStream out = new FileOutputStream("arquivo.bin");
				ObjectOutputStream os = new ObjectOutputStream(out);
				// Especifica o tamanho do arquivo 256MB
				byte[] buf = new byte[260884000];
				os.write(buf);
				os.flush();
				os.close();				
			}
			

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Erro = " + e);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Erro = " + e);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			System.out.println("Erro = " + e);
		}
	}
	
	//Método que imprime tuplas do banco 
	public static void MontaSaida( HashMap<Integer, String> hTuplas){
		boolean bEncontrou = false;
		 for(Entry<Integer, String> entry : hTuplas.entrySet()) {
			 bEncontrou = true;
			 String sDadosIns  = entry.getValue(); 
			 int iID = entry.getKey(); 
			 System.out.println("ID=" + iID + " Value =" + sDadosIns + "\n");
		 }
		 
		 if(!bEncontrou){
			 System.out.println("Nenhum registro encontrado \n");
					 
		 }
	}

	public static int[] RecuperaDataBlocksUsados() {
		HashMap<String, String> indices = new HashMap<String, String>();
		int indicesComDados[] = null;
		try {
			LeituraArquivo(1, indices, 1);
			String sMeioUsado = indices.get("half").toString().trim();
			String sDados[] =  indices.get("full").split(",");
			 indicesComDados = new int [sDados.length  + 1];	
			for(int i =0; i< sDados.length; i++){
				indicesComDados[i] = Integer.parseInt(sDados[i].toString().trim());
			}
		
			indicesComDados[indicesComDados.length - 1 ] = Integer.parseInt( sMeioUsado.toString().trim());
			
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return indicesComDados;
	}
	
	
	public static boolean  BuscaRowID(int id){
		int rowId = -1;
		int IndicesdataUsados[] = RecuperaDataBlocksUsados();
		int iQuantIndices = 2;
		while(iQuantIndices < IndicesdataUsados.length){
			try {
				if(ExcluirDados(id, String.valueOf(IndicesdataUsados[iQuantIndices]))){
					return true;
				}
				iQuantIndices ++; 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		return false;
	}
	
	

	public static void LeituraArquivoJson(){
		JSONObject jsonObject; //Cria o parse de tratamento 
		JSONParser parser = new JSONParser(); //Variaveis que irao armazenar os dados do arquivo JSON 
		String nome; 
		String sobrenome; 
		String estado; 
		String pais; 
		try { //Salva no objeto JSONObject o que o parse tratou do arquivo 
			jsonObject = (JSONObject) parser.parse(new FileReader( "saida.json")); //Salva nas variaveis os dados retirados do arquivo 
			nome = (String) jsonObject.get("nome"); 
			sobrenome = (String) jsonObject.get("sobrenome"); 
			estado = (String) jsonObject.get("estado"); 
			pais = (String) jsonObject.get("pais"); 
			System.out.printf( "Nome: %s\nSobrenome: %s\nEstado: %s\nPais: %s\n", nome, sobrenome, estado, pais); 
			} //Trata as exceptions que podem ser lançadas no decorrer do processo 
		catch (FileNotFoundException e) {
			e.printStackTrace(); 
			}
		catch (IOException e) { 
			e.printStackTrace(); 
			}
		catch (ParseException e) { // TODO Auto-generated catch block 
			e.printStackTrace(); 
			}
	}
	
	
	

}
