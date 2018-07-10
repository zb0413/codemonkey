package com.codemonkey.test.report;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.codemonkey.report.service.ReportService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(locations = { 
	"classpath*:spring/applicationContext-*.xml"
})
@ActiveProfiles("test")
@Transactional
public class ReportServiceTest {

	@Autowired ReportService reportService;
	
	@Before
	public void before(){
		File f = new File("userReport.pdf");
		if(f.exists()){
			f.delete();
		}
		
		f = new File("userReport.xml");
		if(f.exists()){
			f.delete();
		}
		
	}
	
	@Test
	public void testCreatePdf(){
		reportService.pdf("userReport.pdf", "reports/template.jasper");
		File f = new File("userReport.pdf");
		assertTrue(f.exists());
	}
	
	@Test
	public void testCreateXml(){
		reportService.xml("userReport.xml", "reports/template.jasper");
		File f = new File("userReport.xml");
		assertTrue(f.exists());
	}
	
	@Test
	public void testPrint(){
//		reportService.print("reports/template.jasper");
	}
}
