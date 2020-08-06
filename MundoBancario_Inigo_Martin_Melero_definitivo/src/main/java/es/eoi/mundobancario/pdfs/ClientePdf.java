package es.eoi.mundobancario.pdfs;

import java.io.IOException;
import java.util.List;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import es.eoi.mundobancario.dto.ClienteDtoReport1;
import es.eoi.mundobancario.dto.CuentaDtoReport1;
import es.eoi.mundobancario.dto.MovimientoDtoReport;
import es.eoi.mundobancario.email.EnviarEmail;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ClientePdf {

	public static String crearClientePdf(ClienteDtoReport1 cliente) throws IOException {

		String destino = "C:\\Users\\inigo\\Desktop\\EOI_BANK_CLIENTE_000.pdf";
		PdfWriter writer = new PdfWriter(destino);
		PdfDocument pdf = new PdfDocument(writer);
		Document document = new Document(pdf);
		document.setMargins(50, 50, 50, 50);

		PdfFont fuentegeneral = PdfFontFactory.createFont(com.itextpdf.io.font.FontConstants.HELVETICA);

		document.add(new Paragraph("Inigo Martin Melero")
				.setFont(fuentegeneral).setFontSize(12));
		document.add(new Paragraph("\nAplicacion de Mundo Bancario\n\n").setFont(fuentegeneral).setFontSize(20));
		document.add(
				new Paragraph("\nEl cliente que usted quiere devolver es:\n").setFont(fuentegeneral).setFontSize(12));
		document.add(new Paragraph("\n1. Datos basicos del cliente.").setFont(fuentegeneral).setFontSize(15));
		document.add(new Paragraph("\n    Usuario: " + cliente.getUsuario()).setFont(fuentegeneral).setFontSize(12));
		document.add(new Paragraph("    Pass: " + cliente.getPass()).setFont(fuentegeneral).setFontSize(12));
		document.add(new Paragraph("    Nombre: " + cliente.getNombre()).setFont(fuentegeneral).setFontSize(12));
		document.add(new Paragraph("    Email: " + cliente.getEmail()).setFont(fuentegeneral).setFontSize(12));
		document.add(new Paragraph("\n\t2. Datos de las cuentas.\n").setFont(fuentegeneral).setFontSize(15));

		List<CuentaDtoReport1> cuentas = cliente.getCuentas();

		for (int i = 0; i < cuentas.size(); i++) {
			int contador1 = i + 1;
			document.add(
					new Paragraph("\n2." + contador1 + " Cuenta: " + contador1).setFont(fuentegeneral).setFontSize(14));
			CuentaDtoReport1 actual = cuentas.get(i);
			document.add(new Paragraph("\nAlias: " + actual.getAlias()).setFont(fuentegeneral).setFontSize(12));
			document.add(new Paragraph("Saldo: " + actual.getSaldo()).setFont(fuentegeneral).setFontSize(12));

			List<MovimientoDtoReport> movimientos = actual.getMovimientos();

			for (int j = 0; j < movimientos.size(); j++) {
				int contador2 = j + 1;
				document.add(new Paragraph("\n2." + contador2 + "." + contador2 + " Movimiento: " + contador2)
						.setFont(fuentegeneral).setFontSize(13));
				MovimientoDtoReport actual2 = movimientos.get(j);
				document.add(new Paragraph("\nDescripcion: " + actual2.getDescripcion()).setFont(fuentegeneral)
						.setFontSize(12));
				document.add(new Paragraph("Fecha: " + actual2.getFecha()).setFont(fuentegeneral).setFontSize(12));
				document.add(new Paragraph("TipoMovimiento: " + actual2.getTipomovimiento().getTipo())
						.setFont(fuentegeneral).setFontSize(12));

				if (actual2.getTipomovimiento().getTipo().toString().equals("INGRESO")
						|| actual2.getTipomovimiento().getTipo().toString().equals("PRESTAMO")) {

					document.add(new Paragraph("Importe: " + actual2.getImporte()).setFont(fuentegeneral)
							.setFontSize(12).setFontColor(ColorConstants.GREEN));
				} else {
					document.add(new Paragraph("Importe: -" + actual2.getImporte()).setFont(fuentegeneral)
							.setFontSize(12).setFontColor(ColorConstants.RED));
				}
			}
		}
		document.close();
		EnviarEmail.main(document,"CLIENTE");
		return "Se ha creado el pdf y enviado a su receptor";

		
	}

}
