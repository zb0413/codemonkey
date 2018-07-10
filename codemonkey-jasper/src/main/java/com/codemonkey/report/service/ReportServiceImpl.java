package com.codemonkey.report.service;

import java.io.File;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrinterName;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codemonkey.service.AbsService;

@Component
public class ReportServiceImpl extends AbsService implements ReportService {

	@Autowired
	@Resource(name = "dataSource")
	private DataSource datasource;

	public void pdf(String destinationFile, String templatePath) {

		pdf(destinationFile , templatePath , new HashMap<String, Object>());
	}

	public void pdf(String destinationFile, String templatePath,
			Map<String, Object> param) {

		JasperPrint jasperPrint = buildJasperPrint(templatePath, param);
		if (jasperPrint != null) {
			try {
				JasperExportManager.exportReportToPdfFile(jasperPrint, destinationFile);
			} catch (JRException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void down(OutputStream outputStream, File templatePath){
		down(outputStream , templatePath , new HashMap<String, Object>());
	}
	
	public void down(OutputStream outputStream, File templatePath, Map<String, Object> param) {

		JasperPrint jasperPrint = buildJasperPrint(templatePath, param);
		if (jasperPrint != null) {
			try {
				JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
			} catch (JRException e) {
				e.printStackTrace();
			}
		}
	}

	public void xml(String destinationFile, String templatePath) {

		JasperPrint jasperPrint = buildJasperPrint(templatePath,
				new HashMap<String, Object>());
		if (jasperPrint != null) {
			try {
				JasperExportManager.exportReportToXmlFile(jasperPrint,
						destinationFile, false);
			} catch (JRException e) {
				e.printStackTrace();
			}
		}
	}

	public void print(String templatePath, Map<String, Object> param) {
		PrintService service = PrintServiceLookup.lookupDefaultPrintService();

		if (service != null) {
			String printServiceName = service.getName();
			System.out.println("Print Service Name is " + printServiceName);

			JasperPrint jasperPrint = buildJasperPrint(templatePath, param);
			if (jasperPrint != null) {
				try {
					JRPrintServiceExporter exporter = new JRPrintServiceExporter();
					PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
					printRequestAttributeSet.add(MediaSizeName.ISO_A4);

					PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
					printServiceAttributeSet.add(new PrinterName(
							printServiceName, null));
					exporter.setParameter(JRExporterParameter.JASPER_PRINT,
							jasperPrint);
					exporter.setParameter(
							JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET,
							printRequestAttributeSet);
					exporter.setParameter(
							JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET,
							printServiceAttributeSet);
					exporter.setParameter(
							JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG,
							Boolean.FALSE);
					exporter.setParameter(
							JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG,
							Boolean.FALSE);
					exporter.exportReport();
				} catch (JRException e) {
					e.printStackTrace();
				}
				// catch (URISyntaxException e) {
				// e.printStackTrace();
				// }
			}
		} else {
			System.out.println("No default print service found");
		}
	}

	private JasperPrint buildJasperPrint(String templatePath,
			Map<String, Object> params) {

		JasperPrint jasperPrint = null;
		Connection connection = null;
		try {
			String jasperReport = getJasperReport(templatePath);
			connection = datasource.getConnection();
			jasperPrint = JasperFillManager.fillReport(jasperReport, params,
					connection);
		} catch (JRException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jasperPrint;
	}

	private String getJasperReport(String templatePath) {
		
		URL url = ClassLoader.getSystemResource(templatePath);
		return url.getFile();
	}
	
	private JasperPrint buildJasperPrint(File templatePath,
			Map<String, Object> params) {

		JasperPrint jasperPrint = null;
		Connection connection = null;
		try {
			connection = datasource.getConnection();
			jasperPrint = JasperFillManager.fillReport(templatePath.getAbsolutePath(), params,
					connection);
		} catch (JRException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jasperPrint;
	}

}
