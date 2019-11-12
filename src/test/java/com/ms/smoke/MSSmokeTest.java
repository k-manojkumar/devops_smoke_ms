package com.ms.smoke;

import static io.restassured.RestAssured.given;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class MSSmokeTest {

	static ExtentTest test;
	static ExtentReports report;

	final private String BASE_URL = "http://35.189.232.46:7001/DevOpsService";

	@Test
	public void testSampleInterface() throws Exception {

		test = report.createTest("Validate testService");
		ExtentTest testNode = test.createNode("testService Invocation");
		try {
			given().when().get(BASE_URL + "/v1/test").then().statusCode(200);
			testNode.pass("Received Success Response from testService");
		} catch (Exception e) {
			testNode.fail("Unexpected Response from testService");
			e.printStackTrace();
			throw e;
		}
	}

	@Test
	public void testGetResource() throws Exception {

		test = report.createTest("Validate getResource");
		ExtentTest testNode = test.createNode("testService Invocation");
		try {
			given().param("fileID", "SS07001").when().get(BASE_URL + "/v1/getResource").then().statusCode(200);
			testNode.pass("Received Success Response from getResource");
		} catch (Exception e) {
			testNode.fail("Unexpected Response from getResource");
			e.printStackTrace();
			throw e;
		}
	}

	@BeforeClass

	public static void initateReport() {

		Date date = Calendar.getInstance().getTime();
		//SimpleDateFormat dateFormat = new SimpleDateFormat("ddMM_HHmm");
		SimpleDateFormat dateFormatForReport = new SimpleDateFormat("dd MMM yyyy");
		String fileName = System.getProperty("user.dir") + "/test-reports/TestReport.html";

		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
		report = new ExtentReports();
		htmlReporter.config().setAutoCreateRelativePathMedia(true);
		htmlReporter.config().setCSS("css-string");
		htmlReporter.config().setDocumentTitle("MS Smoke Test Report");
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setJS("js-string");
		htmlReporter.config().setProtocol(Protocol.HTTPS);
		htmlReporter.config().setReportName("MS Smoke Test Report");
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
		report.attachReporter(htmlReporter);
		report.setSystemInfo("Application Name", "DevOps MS");
		report.setSystemInfo("Tool", "Rest Assured");
		report.setSystemInfo("Report Date", dateFormatForReport.format(date));
		// return fileName;

	}

	@AfterClass()
	public static void finishReport() {
		report.flush();

	}

}
