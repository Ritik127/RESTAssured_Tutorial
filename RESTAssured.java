package API;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;



import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.io.FileInputStream;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class RESTAssured {
RequestSpecification httprequest;
XSSFWorkbook work_book;
XSSFSheet sheet;
//initialize the XSSF workbook objct
@Test (priority=2)
public void ReadExcelFile() {
	try {
	//declare file
	File s = new File("src/test/java/API/datadriven.xlsx");
	// declare file input stream that help in read the value
	FileInputStream stream = new FileInputStream(s);
	//put them into the workbook
	work_book = new XSSFWorkbook(stream);
	}
	catch(Exception e) {
		System.out.println(e.getLocalizedMessage());
	}
	
}
//reading the data from the file
//@Test(priority=3)
public void get_data() {
	int sheetnumber =0;
	int row =1;
	int column=1;
	// get sheet number to which we want to work upon 
	sheet = work_book.getSheetAt(0);
	for(int i=0;i<4;i++) {
	int data = (int) sheet.getRow(1).getCell(1).getNumericCellValue();
//	converting string into integer;
//	int number = Integer.parseInt(data);
	int number1=19;
	//get api data
	String url = "/public/v2/users/" + data;
	Response res = httprequest.get(url);
	ResponseBody rbody =res.getBody();
	//printing the httpstatus code
	System.out.println(res.getStatusCode());
	//printing the response 
	System.out.println(rbody.asPrettyString());
	}
}
@BeforeTest 
public void init() {
	RestAssured.baseURI ="https://gorest.co.in";
	httprequest = RestAssured.given();
}

@Test (priority=4)
public void post_method() {
	// declare json object
	JSONObject qparm = new JSONObject();
	qparm.put("name","bhhh");
	qparm.put("email","kkkaranjohar@gmail.com");
//	qparm.put("gender","male");
//	qparm.put("status","active");
	// add payload into the body
	httprequest.header("Content-type","application/json").auth().oauth2("1c8e94addd3d767fa4d7fdb2ded2ae38bf9ef89585417a6a1ac682267fa0362d");
	httprequest.body(qparm.toJSONString());
	Response res = httprequest.put("/public/v2/users/4473");
	ResponseBody rbody =res.getBody();
	//printing the httpstatus code
	System.out.println(res.getStatusCode());
	//printing the response 
	System.out.println(res.asString());
}
@Test (priority=5)
public void put_method() {
	httprequest.header("Content-Type","application/json");
	httprequest.header("Content-type","application/json").auth().oauth2("1c8e94addd3d767fa4d7fdb2ded2ae38bf9ef89585417a6a1ac682267fa0362d");
Response res =	httprequest.delete("/public/v2/users/21");
System.out.println(res.getStatusCode());
	
}
}
