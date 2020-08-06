package es.eoi.mundobancario;

import java.util.Scanner;

import es.eoi.mundobancario.exception.ClienteNotFoundException;

public class MenuApp {

	public static void main(String[] args) throws ClienteNotFoundException {
		System.out.println("\n");
		System.out.println("************************************");
		System.out.println("Mundo Bancario, aquí para arruinarte");
		System.out.println("************************************");
		System.out.println("\n");
		System.out.println("¿Qué desea usted hacer?");
		System.out.println("1 - Clientes");
		System.out.println("2 - Cuentas");
		System.out.println("3 - Reports");
		Scanner scanner = new Scanner(System.in);
		int numero = scanner.nextInt();
		
		switch (numero) {
		case 1:
			ClienteApp.main(args);
			break;
		case 2:
			CuentasApp.main(args);
			break;
		case 3:
			ReportsApp.main(args);
			break;
		default:
			System.out.println("Numero no admitido");
			break;
		}

	}
}
