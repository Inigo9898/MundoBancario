package es.eoi.mundobancario;

import java.util.Scanner;


import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.google.gson.Gson;
import es.eoi.mundobancario.dto.ClienteDto;
import es.eoi.mundobancario.dto.CuentaDto;
import es.eoi.mundobancario.entity.Cliente;
import es.eoi.mundobancario.exception.ClienteNotFoundException;

public class ClienteApp {

	public static void main(String[] args) throws ClienteNotFoundException {
		System.out.println("¿Qué desea usted hacer, dentro de cliente?");
		System.out.println("1 - Obtener todos los clientes");
		System.out.println("2 - Obtener Cliente segun Id");
		System.out.println("3 - Login cliente");
		System.out.println("4 - Obtener las cuentas del cliente, segun Id");
		System.out.println("5 - Actualizar el correo del cliente");
		System.out.println("6 - Anadir cliente");
		Scanner scanner2 = new Scanner(System.in);
		int devuelto = scanner2.nextInt();
		RestTemplate restTemplate = new RestTemplate();
		switch (devuelto) {
		case 1:
			final String uri = "http://localhost:8080/clientes";
			ResponseEntity<ClienteDto[]> response = restTemplate.getForEntity(uri, ClienteDto[].class);
			ClienteDto[] clientes = response.getBody();
			for (int i = 0; i < clientes.length; i++) {
				int contador = i + 1;
				System.out.println("Cliente " + contador);
				System.out.println("Nombre: " + clientes[i].getNombre());
				System.out.println("Email: " + clientes[i].getEmail());
				System.out.println("Usuario: " + clientes[i].getUsuario());
			}
			break;
		case 2:
			System.out.println("Introduzca el ID del cliente:");
			Scanner scanner3 = new Scanner(System.in);
			int id = scanner3.nextInt();
			final String uri2 = "http://localhost:8080/clientes/".concat(String.valueOf(id));
			ResponseEntity<ClienteDto> response2 = restTemplate.getForEntity(uri2, ClienteDto.class);
			ClienteDto cliente = response2.getBody();
			System.out.println("Cliente seleccionado:");
			System.out.println("Usuario: " + cliente.getUsuario());
			System.out.println("Nombre: " + cliente.getNombre());
			System.out.println("Email: " + cliente.getEmail());
			scanner3.close();
			break;
		case 3:
			System.out.println("Introduzca el nombre y la contrasena del cliente:");
			Scanner scanner4 = new Scanner(System.in);
			String string3 = scanner4.nextLine();
			Gson j = new Gson();
			Cliente cli = j.fromJson(string3, Cliente.class);
			final String uri3 = "http://localhost:8080/clientes/login";
			ResponseEntity<ClienteDto> response3 = restTemplate.postForEntity(uri3, cli, ClienteDto.class);
			ClienteDto cliente3 = response3.getBody();
			System.out.println("Usted ha acertado la contrasena");
			System.out.println("Cliente seleccionado:");
			System.out.println("Usuario: " + cliente3.getUsuario());
			System.out.println("Nombre: " + cliente3.getNombre());
			System.out.println("Email: " + cliente3.getEmail());
			scanner4.close();
			break;
		case 4:
			System.out.println("Introduzca el ID del cliente:");
			Scanner scanner5 = new Scanner(System.in);
			int id4 = scanner5.nextInt();
			final String uri4 = "http://localhost:8080/clientes/".concat(String.valueOf(id4).concat("/cuentas"));
			ResponseEntity<CuentaDto[]> response4 = restTemplate.getForEntity(uri4, CuentaDto[].class);
			CuentaDto[] cuentas = response4.getBody();
			for (int i = 0; i < cuentas.length; i++) {
				int contador = i + 1;
				System.out.println("Cuenta " + contador);
				System.out.println("Alias: " + cuentas[i].getAlias());
				System.out.println("Importe: " + cuentas[i].getSaldo());
			}
			scanner5.close();
			break;
		case 5:
			System.out.println("Introduzca el ID del cliente:");
			Scanner scanner7 = new Scanner(System.in);
			int id5 = scanner7.nextInt();
			System.out.println("Introduzca en Json al cliente:");
			Scanner scanner8 = new Scanner(System.in);
			String string4 = scanner8.nextLine();
			final String uri5 = "http://localhost:8080/clientes/".concat(String.valueOf(id5));
			Gson h = new Gson();
			Cliente cli3 = h.fromJson(string4, Cliente.class);
			restTemplate.put(uri5, cli3);
			System.out.println("Se ha actualizado al cliente");
			break;
		case 6:
			System.out.println("Introduzca en Json al cliente:");
			Scanner scanner6 = new Scanner(System.in);
			String str = scanner6.nextLine();
			Gson g = new Gson();
			Cliente cli2 = g.fromJson(str, Cliente.class);
			final String uri6 = "http://localhost:8080/clientes";
			String response6 = restTemplate.postForObject(uri6, cli2, String.class);
			System.out.println(response6);
			scanner6.close();
			break;
		default:
			System.out.println("Numero no admitido");
			break;
		}
		scanner2.close();
	}

}
