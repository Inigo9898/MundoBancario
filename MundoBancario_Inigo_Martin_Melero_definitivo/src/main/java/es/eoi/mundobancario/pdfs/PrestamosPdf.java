package es.eoi.mundobancario.pdfs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import es.eoi.mundobancario.dto.AmortizacionDtoReport;
import es.eoi.mundobancario.dto.PrestamoDtoReport;
import es.eoi.mundobancario.email.EnviarEmail;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PrestamosPdf {

	public static String crearPrestamoPdf(PrestamoDtoReport prestamo) throws IOException {
		String destino = "C:\\Users\\inigo\\Desktop\\EOI_BANK_PRESTAMO_000.pdf";
		PdfWriter writer = new PdfWriter(destino);
		PdfDocument pdf = new PdfDocument(writer);
		Document document = new Document(pdf);
		document.setMargins(50, 50, 50, 50);

		PdfFont fuentegeneral = PdfFontFactory.createFont(com.itextpdf.io.font.FontConstants.HELVETICA);

		document.add(new Paragraph("Inigo Martin Melero")
				.setFont(fuentegeneral).setFontSize(12));
		document.add(new Paragraph("\nAplicacion de Mundo Bancario\n\n").setFont(fuentegeneral).setFontSize(20));
		document.add(
				new Paragraph("\nEl prestamo que usted quiere devolver es:\n").setFont(fuentegeneral).setFontSize(12));
		document.add(new Paragraph("\n1. Datos basicos del cliente.").setFont(fuentegeneral).setFontSize(15));
		document.add(new Paragraph("\n    Usuario: " + prestamo.getCuenta().getCliente().getNombre())
				.setFont(fuentegeneral).setFontSize(12));
		document.add(new Paragraph("    Email: " + prestamo.getCuenta().getCliente().getEmail()).setFont(fuentegeneral)
				.setFontSize(12));
		document.add(new Paragraph("\n2. Datos basicos de la cuenta.").setFont(fuentegeneral).setFontSize(15));
		document.add(new Paragraph("\n    Alias: " + prestamo.getCuenta().getAlias()).setFont(fuentegeneral)
				.setFontSize(12));
		document.add(
				new Paragraph("    Saldo: " + prestamo.getCuenta().getSaldo()).setFont(fuentegeneral).setFontSize(12));
		document.add(new Paragraph("\n3. Datos basicos del prestamo pedido.").setFont(fuentegeneral).setFontSize(15));
		document.add(new Paragraph("\n    Descripcion: " + prestamo.getDescripcion()).setFont(fuentegeneral)
				.setFontSize(12));
		document.add(new Paragraph("    Fecha: " + prestamo.getFecha()).setFont(fuentegeneral).setFontSize(12));
		document.add(new Paragraph("    Importe: " + prestamo.getImporte()).setFont(fuentegeneral).setFontSize(12));
		document.add(new Paragraph("    Plazos: " + prestamo.getPlazos()).setFont(fuentegeneral).setFontSize(12));
		document.add(new Paragraph("\n4. Amortizaciones del prestamo pedido.").setFont(fuentegeneral).setFontSize(15));
		List<AmortizacionDtoReport> amortizaciones = prestamo.getAmortizaciones();
		for (int i = 0; i < amortizaciones.size(); i++) {

			int contador1 = i + 1;
			document.add(
					new Paragraph("\n4." + contador1 + " Amortizacion: " + contador1 + " de " + prestamo.getPlazos())
							.setFont(fuentegeneral).setFontSize(14));
			AmortizacionDtoReport actual = amortizaciones.get(i);
			document.add(new Paragraph("\nFecha: " + actual.getFecha()).setFont(fuentegeneral).setFontSize(12));
			document.add(new Paragraph("\nImporte: " + actual.getImporte()).setFont(fuentegeneral).setFontSize(12));
		}
		document.close();
		EnviarEmail.main(document,"PRESTAMO");
		return "Se ha creado el pdf y enviado a su receptor";
	}
}
