package es.eoi.mundobancario;

import java.util.List;
import java.util.Scanner;
import org.springframework.web.client.RestTemplate;
import es.eoi.mundobancario.dto.AmortizacionDtoReport;
import es.eoi.mundobancario.dto.ClienteDtoReport1;
import es.eoi.mundobancario.dto.CuentaDtoReport1;
import es.eoi.mundobancario.dto.MovimientoDtoReport;
import es.eoi.mundobancario.dto.PrestamoDtoReport;

public class ReportsApp {

	public static void main(String[] args) {
		System.out.println("¿Qué desea usted hacer, dentro de reports?");
		System.out.println("1 - Obtener el informe del cliente");
		System.out.println("2 - Obtener el PDF del informe del cliente");
		System.out.println("3 - Obtener el informe de los prestamos");
		System.out.println("4 - Obtener el PDF del informe de los prestamos");
		System.out.println("5 - Obtener los prestamos vivos");
		System.out.println("6 - Obtener los prestamos amortizados");
		Scanner scanner2 = new Scanner(System.in);
		int devuelto = scanner2.nextInt();
		RestTemplate restTemplate = new RestTemplate();
		switch (devuelto) {
		case 1:
			System.out.println("Introduzca el ID del cliente:");
			Scanner scanner3 = new Scanner(System.in);
			int id = scanner3.nextInt();
			final String uri = "http://localhost:8080/reports/clientes/".concat(String.valueOf(id));
			ClienteDtoReport1 response = restTemplate.getForEntity(uri, ClienteDtoReport1.class).getBody();
			System.out.println("Datos solicitados:");
			System.out.println("Usuario: "+response.getUsuario());
			System.out.println("Pass: "+response.getPass());
			System.out.println("Nombre: "+response.getNombre());
			System.out.println("Email: "+response.getEmail());
			List<CuentaDtoReport1> lista = response.getCuentas();
			
			for (int i = 0; i < lista.size(); i++) {
				int contador = i + 1;
				System.out.println("Cuenta " + contador);
				System.out.println("Alias: " + lista.get(i).getAlias());
				System.out.println("Saldo: " + lista.get(i).getSaldo());
				System.out.println("Movimientos:");
				List<MovimientoDtoReport> lista2 = lista.get(i).getMovimientos();
				for (int j = 0; j < lista2.size(); j++) {
					int contador2 = j + 1;
					System.out.println("Movimiento " + contador2);
					System.out.println("Descripcion: "+lista2.get(j).getDescripcion());
					System.out.println("Importe: "+lista2.get(j).getImporte());
					System.out.println("Fecha: "+lista2.get(j).getFecha());
					System.out.println("Tipo de movimiento: "+lista2.get(j).getTipomovimiento().getTipo());
				}
			}
			break;
		case 2:
			System.out.println("Introduzca el ID del cliente:");
			Scanner scanner4 = new Scanner(System.in);
			int id2 = scanner4.nextInt();
			final String uri2 = "http://localhost:8080/reports/clientes/".concat(String.valueOf(id2));
			String response2 = restTemplate.postForEntity(uri2,"hola",String.class).getBody();
			System.out.println(response2);
			
			break;
		case 3:
			System.out.println("Introduzca el ID del prestamo:");
			Scanner scanner5 = new Scanner(System.in);
			int id3 = scanner5.nextInt();
			final String uri3 = "http://localhost:8080/reports/prestamos/".concat(String.valueOf(id3));
			PrestamoDtoReport response3 = restTemplate.getForEntity(uri3, PrestamoDtoReport.class).getBody();
			System.out.println("Datos solicitados:");
			System.out.println("Descripcion: "+response3.getDescripcion());
			System.out.println("Importe: "+response3.getImporte());
			System.out.println("Plazos: "+response3.getPlazos());
			System.out.println("Nombre: "+response3.getCuenta().getCliente().getNombre());
			System.out.println("Email: "+response3.getCuenta().getCliente().getEmail());
			List<AmortizacionDtoReport> amortizaciones = response3.getAmortizaciones();
			System.out.println("Amortizaciones: ");
			for (int i = 0; i < amortizaciones.size(); i++) {
				int contador4 = i + 1;
				System.out.println("Amortizacion "+contador4);
				System.out.println("Importe: "+amortizaciones.get(i).getImporte());
				System.out.println("Fecha: "+amortizaciones.get(i).getFecha());	
			}
			break;
		case 4:
			System.out.println("Introduzca el ID del cliente:");
			Scanner scanner6 = new Scanner(System.in);
			int id4 = scanner6.nextInt();
			final String uri4 = "http://localhost:8080/reports/prestamos/".concat(String.valueOf(id4));
			String response4 = restTemplate.postForEntity(uri4,"hola",String.class).getBody();
			System.out.println(response4);
			break;
		case 5:
			System.out.println("Se obtienen los siguientes prestamos vivos:");
			final String uri5 = "http://localhost:8080/reports/prestamosVivos";
			PrestamoDtoReport[] response5 = restTemplate.getForEntity(uri5, PrestamoDtoReport[].class).getBody();
			for (int i = 0; i < response5.length; i++) {
				int contador5 = i +1;
				System.out.println("Prestamo "+contador5);
				System.out.println("Descripcion: "+response5[i].getDescripcion());
				System.out.println("Importe: "+response5[i].getImporte());
				System.out.println("Plazos: "+response5[i].getPlazos());
				System.out.println("Nombre: "+response5[i].getCuenta().getCliente().getNombre());
				System.out.println("Email: "+response5[i].getCuenta().getCliente().getEmail());
				List<AmortizacionDtoReport> amortizaciones2 = response5[i].getAmortizaciones();
				System.out.println("Amortizaciones: ");
				for (int j = 0; j < amortizaciones2.size(); j++) {
					int contador4 = j + 1;
					System.out.println("Amortizacion "+contador4);
					System.out.println("Importe: "+amortizaciones2.get(j).getImporte());
					System.out.println("Fecha: "+amortizaciones2.get(j).getFecha());	
				}
				
			}
			break;
		case 6:
			System.out.println("Se obtienen los siguientes prestamos amortizados:");
			final String uri6 = "http://localhost:8080/reports/prestamosAmortizados";
			PrestamoDtoReport[] response6 = restTemplate.getForEntity(uri6, PrestamoDtoReport[].class).getBody();
			for (int i = 0; i < response6.length; i++) {
				int contador6 = i +1;
				System.out.println("Prestamo "+contador6);
				System.out.println("Descripcion: "+response6[i].getDescripcion());
				System.out.println("Importe: "+response6[i].getImporte());
				System.out.println("Plazos: "+response6[i].getPlazos());
				System.out.println("Nombre: "+response6[i].getCuenta().getCliente().getNombre());
				System.out.println("Email: "+response6[i].getCuenta().getCliente().getEmail());
				List<AmortizacionDtoReport> amortizaciones2 = response6[i].getAmortizaciones();
				System.out.println("Amortizaciones: ");
				for (int j = 0; j < amortizaciones2.size(); j++) {
					int contador7 = j + 1;
					System.out.println("Amortizacion "+contador7);
					System.out.println("Importe: "+amortizaciones2.get(j).getImporte());
					System.out.println("Fecha: "+amortizaciones2.get(j).getFecha());	
				}
				
			}
			break;
			
		}
	}

}
