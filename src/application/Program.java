package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import entities.Product;

public class Program {

	public static void main(String[] args) {

		//tornando a maquina local com padrão USA
		Locale.setDefault(Locale.US);
		//Instanciando Scanner
		Scanner sc = new Scanner(System.in);

		//Pedindo a entrada do caminho do arquivo
		System.out.println("Enter full file path: ");
		String path = sc.nextLine();

		//try with resources para ler o arquivo no caminho especificado
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			//criando uma lista de produtos
			List<Product> list = new ArrayList<>();
			
			//lendo a linha do arquivo
			String line = br.readLine();
			//enquanto a linha não for vazia leia
			while(line != null) {
				//System.out.println(line);
				//separando a linha em um array, a cada virgula na linha
				String[] fields = line.split(",");
				//adicionando o produto a lista
				list.add(new Product(fields[0], Double.parseDouble(fields[1])));
				line = br.readLine();
			}
			
			//Função lendo a media dos preços utilizando stream
			double avg = list.stream().map(p -> p.getPrice()).reduce(0.0, (x, y) -> x + y) / list.size();
			
			//imprimindo a media 
			System.out.println("Average Price: " + String.format("%.2f", avg));
			
			//comparator para comparar cada item em caixa alta
			Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());
			
			//organizando a lista para imprimir os produtos em ordem decrescente alfabeticamente
			List<String> names = list.stream().filter(p -> p.getPrice() < avg).map(p -> p.getName()).sorted(comp.reversed()).collect(Collectors.toList());
			
			//para cada nome na lista, imprimir.
			names.forEach(System.out::println);
			
			
			

		} catch (IOException e) {
			//exceção para pegar possiveis erros
			System.out.println("Error: " + e.getMessage());
		}
		sc.close();
	}

}
