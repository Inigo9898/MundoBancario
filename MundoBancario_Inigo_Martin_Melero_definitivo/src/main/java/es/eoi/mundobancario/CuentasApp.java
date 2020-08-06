package es.eoi.mundobancario;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import es.eoi.mundobancario.dto.AmortizacionDto;
import es.eoi.mundobancario.dto.CuentaDto;
import es.eoi.mundobancario.dto.MovimientoDto;
import es.eoi.mundobancario.dto.PrestamoDto;
import es.eoi.mundobancario.entity.Cliente;
import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.entity.Movimiento;
import es.eoi.mundobancario.entity.Prestamo;

public class CuentasApp {

	public static void main(String[] args) {
		System.out.println("¿Qué desea usted hacer, dentro de cuenta?");
		System.out.println("1 - Obtener todas las cuentas");
		System.out.println("2 - Obtener las cuentas deudoras");
		System.out.println("3 - Obtener cuenta según Id");
		System.out.println("4 - Anadir cuenta");
		System.out.println("5 - Actualizar el alias de la cuenta");
		System.out.println("6 - Obtener movimientos de la cuenta");
		System.out.println("7 - Obtener prestamos de la cuenta");
		System.out.println("8 - Obtener prestamos vivos de la cuenta");
		System.out.println("9 - Obtener prestamos amortizados de la cuenta");
		System.out.println("10 - Crear un prestamo");
		System.out.println("11 - Crear un ingreso");
		System.out.println("12 - Crear un pago");
		System.out.println("13 - Ejecutar amortizaciones diarias");
		Scanner scanner = new Scanner(System.in);
		int devuelto = scanner.nextInt();
		RestTemplate restTemplate = new RestTemplate();
		switch (devuelto) {
		case 1:
			final String uri1 = "http://localhost:8080/cuentas";
			CuentaDto[] cuentas = restTemplate.getForEntity(uri1, CuentaDto[].class).getBody();
			for (int i = 0; i < cuentas.length; i++) {
				int contador = i + 1;
				System.out.println("Cuenta " + contador);
				System.out.println("Alias: "+cuentas[i].getAlias());
				System.out.println("Saldo: "+cuentas[i].getSaldo());
				System.out.println("Nombre: "+cuentas[i].getCliente().getNombre());
				System.out.println("Usuario: "+cuentas[i].getCliente().getUsuario());
				System.out.println("Email: "+cuentas[i].getCliente().getEmail());
			}
			break;
		case 2:
			final String uri2 = "http://localhost:8080/cuentas/deudoras";
			CuentaDto[] cuentas2 = restTemplate.getForEntity(uri2, CuentaDto[].class).getBody();
			for (int i = 0; i < cuentas2.length; i++) {
				int contador = i + 1;
				System.out.println("Cuenta " + contador);
				System.out.println("Alias: "+cuentas2[i].getAlias());
				System.out.println("Saldo: "+cuentas2[i].getSaldo());
				System.out.println("Nombre: "+cuentas2[i].getCliente().getNombre());
				System.out.println("Usuario: "+cuentas2[i].getCliente().getUsuario());
				System.out.println("Email: "+cuentas2[i].getCliente().getEmail());
			}
			break;
		case 3:
			System.out.println("Introduzca el ID de la cuenta:");
			Scanner scanner3 = new Scanner(System.in);
			int id = scanner3.nextInt();
			final String uri3 = "http://localhost:8080/cuentas/".concat(String.valueOf(id));
			CuentaDto devuelto2 = restTemplate.getForEntity(uri3, CuentaDto.class).getBody();
			System.out.println("Cuenta seleccionada:");
			System.out.println("Alias: "+devuelto2.getAlias());
			System.out.println("Saldo: "+devuelto2.getSaldo());
			System.out.println("Nombre: "+devuelto2.getCliente().getNombre());
			System.out.println("Usuario: "+devuelto2.getCliente().getUsuario());
			System.out.println("Email: "+devuelto2.getCliente().getEmail());
			break;
		case 4:
			System.out.println("Introduzca en Json la cuenta:");
			Scanner scanner4 = new Scanner(System.in);
			String str = scanner4.nextLine();
			Gson g = new Gson();
			Cuenta cuen = g.fromJson(str, Cuenta.class);
			final String uri4 = "http://localhost:8080/cuentas";
			String response4 = restTemplate.postForObject(uri4, cuen, String.class);
			System.out.println(response4);
			break;
		case 5:
			System.out.println("Introduzca el ID de la cuenta:");
			Scanner scanner6 = new Scanner(System.in);
			int id5 = scanner6.nextInt();
			System.out.println("Introduzca en Json la cuenta");
			Scanner scanner7 = new Scanner(System.in);
			String str2 = scanner7.nextLine();
			final String uri5 = "http://localhost:8080/cuentas/".concat(String.valueOf(id5));
			Gson h = new Gson();
			Cuenta cuen2 = h.fromJson(str2, Cuenta.class);
			restTemplate.put(uri5, cuen2);
			System.out.println("Se ha actualizado la cuenta");
			break;
		case 6:
			System.out.println("Introduzca el ID de la cuenta:");
			Scanner scanner8 = new Scanner(System.in);
			int id6 = scanner8.nextInt();
			final String uri6 = "http://localhost:8080/cuentas/".concat(String.valueOf(id6).concat("/movimientos"));
			MovimientoDto[] movimientos = restTemplate.getForEntity(uri6, MovimientoDto[].class).getBody();
			for (int j = 0; j < movimientos.length; j++) {
				int contador = j + 1;
				System.out.println("Movimiento "+contador);
				System.out.println("Descripcion: "+movimientos[j].getDescripcion());
				System.out.println("Importe: "+movimientos[j].getImporte());
				System.out.println("Fecha: "+movimientos[j].getFecha());
				System.out.println("Tipo de Movimiento: "+movimientos[j].getTipomovimiento().getTipo());
			}
			break;
		case 7:
			System.out.println("Introduzca el ID de la cuenta:");
			Scanner scanner9 = new Scanner(System.in);
			int id7 = scanner9.nextInt();
			final String uri7 = "http://localhost:8080/cuentas/".concat(String.valueOf(id7).concat("/prestamos"));
			PrestamoDto[] prestamos = restTemplate.getForEntity(uri7, PrestamoDto[].class).getBody();
			for (int i = 0; i < prestamos.length; i++) {
				int contador = i + 1;
				System.out.println("Prestamo "+contador);
				System.out.println("Descripcion: "+prestamos[i].getDescripcion());
				System.out.println("Fecha: "+prestamos[i].getFecha());
				System.out.println("Importe: "+prestamos[i].getImporte());
				System.out.println("Plazos: "+prestamos[i].getPlazos());
				System.out.println("Amortizaciones:");
				List<AmortizacionDto> amortizaciones = prestamos[i].getAmortizaciones();
			for (int j = 0; j < amortizaciones.size(); j++) {
				int contador2 = j+1;
				System.out.println("Amortizacion "+contador2);
				System.out.println("Fecha: "+amortizaciones.get(j).getFecha());
				System.out.println("Importe: "+amortizaciones.get(j).getImporte());
				
			}
				
				
			}
			break;
		case 8:
			System.out.println("Introduzca el ID de la cuenta:");
			Scanner scanner10 = new Scanner(System.in);
			int id8 = scanner10.nextInt();
			final String uri8 = "http://localhost:8080/cuentas/".concat(String.valueOf(id8).concat("/prestamosVivos"));
			PrestamoDto[] prestamosvivos = restTemplate.getForEntity(uri8, PrestamoDto[].class).getBody();
			for (int i = 0; i < prestamosvivos.length; i++) {
				int contador = i + 1;
				System.out.println("Prestamo "+contador);
				System.out.println("Descripcion: "+prestamosvivos[i].getDescripcion());
				System.out.println("Fecha: "+prestamosvivos[i].getFecha());
				System.out.println("Importe: "+prestamosvivos[i].getImporte());
				System.out.println("Plazos: "+prestamosvivos[i].getPlazos());
				System.out.println("Amortizaciones:");
				List<AmortizacionDto> amortizaciones = prestamosvivos[i].getAmortizaciones();
			for (int j = 0; j < amortizaciones.size(); j++) {
				int contador2 = j+1;
				System.out.println("Amortizacion "+contador2);
				System.out.println("Fecha: "+amortizaciones.get(j).getFecha());
				System.out.println("Importe: "+amortizaciones.get(j).getImporte());
				
			}
			}
			break;
		case 9:
			System.out.println("Introduzca el ID de la cuenta:");
			Scanner scanner11 = new Scanner(System.in);
			int id9 = scanner11.nextInt();
			final String uri9 = "http://localhost:8080/cuentas/".concat(String.valueOf(id9).concat("/prestamosAmortizados"));
			PrestamoDto[] prestamosamortizados = restTemplate.getForEntity(uri9, PrestamoDto[].class).getBody();
			for (int i = 0; i < prestamosamortizados.length; i++) {
				int contador = i + 1;
				System.out.println("Prestamo "+contador);
				System.out.println("Descripcion: "+prestamosamortizados[i].getDescripcion());
				System.out.println("Fecha: "+prestamosamortizados[i].getFecha());
				System.out.println("Importe: "+prestamosamortizados[i].getImporte());
				System.out.println("Plazos: "+prestamosamortizados[i].getPlazos());
				System.out.println("Amortizaciones:");
				List<AmortizacionDto> amortizaciones = prestamosamortizados[i].getAmortizaciones();
			for (int j = 0; j < amortizaciones.size(); j++) {
				int contador2 = j+1;
				System.out.println("Amortizacion "+contador2);
				System.out.println("Fecha: "+amortizaciones.get(j).getFecha());
				System.out.println("Importe: "+amortizaciones.get(j).getImporte());
				
			}
			}
			break;
		case 10:
			System.out.println("Introduzca el ID de la cuenta:");
			Scanner scanner12 = new Scanner(System.in);
			int id10 = scanner12.nextInt();
			final String uri10 = "http://localhost:8080/cuentas/".concat(String.valueOf(id10).concat("/prestamos"));
			System.out.println("Introduzca en Json el prestamo:");
			Scanner scanner13 = new Scanner(System.in);
			String str3 = scanner13.nextLine();
			Gson i = new Gson();
			Prestamo prestamo = i.fromJson(str3, Prestamo.class);
			String response5 = restTemplate.postForObject(uri10,prestamo, String.class);
			System.out.println(response5);
			break;
		case 11:
			System.out.println("Introduzca el ID de la cuenta:");
			Scanner scanner14 = new Scanner(System.in);
			int id11 = scanner14.nextInt();
			final String uri11 = "http://localhost:8080/cuentas/".concat(String.valueOf(id11).concat("/ingresos"));
			System.out.println("Introduzca en Json el ingreso:");
			Scanner scanner15 = new Scanner(System.in);
			String str4 = scanner15.nextLine();
			Gson j = new Gson();
			Movimiento ingreso = j.fromJson(str4, Movimiento.class);
			String response6 = restTemplate.postForObject(uri11,ingreso, String.class);
			System.out.println(response6);
			break;
		case 12:
			System.out.println("Introduzca el ID de la cuenta:");
			Scanner scanner16 = new Scanner(System.in);
			int id12 = scanner16.nextInt();
			final String uri12 = "http://localhost:8080/cuentas/".concat(String.valueOf(id12).concat("/pagos"));
			System.out.println("Introduzca en Json el pago:");
			Scanner scanner17 = new Scanner(System.in);
			String str5 = scanner17.nextLine();
			Gson k = new Gson();
			Movimiento pago = k.fromJson(str5, Movimiento.class);
			String response7 = restTemplate.postForObject(uri12,pago, String.class);
			System.out.println(response7);
			break;
		case 13:
			final String uri13 = "http://localhost:8080/cuentas/ejecutarAmortizacionesDiarias";
			String auxiliar= "Amortizaciones diarias";
			String response8 = restTemplate.postForObject(uri13, auxiliar, String.class);
			System.out.println(response8);
			break;

		default:
			System.out.println("No ha introducido un numero adecuado");
			break;
		}
	}

}
