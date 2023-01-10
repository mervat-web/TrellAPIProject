
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TrelloAPI {

@BeforeClass
        public void setup(){

    RestAssured.baseURI="https://api.trello.com";
}
@BeforeTest
void start(){
    report=new ExtentReports("C:\\Users\\merva\\TrelloAPI\\target\\report.html",true);
    test=report.startTest("Extent report ");
}
   static  ExtentReports report;
     static ExtentTest test;
     String token="ATTAe788058c3f4a7ff85f570ba0c5e058fecd50061c6d3bee3c348fa94b68359feeEE42489B";
     String telloKey="2073f68d8b3f31ec77820c074d7ed228";

     String orgName="Try";
    String listName="List1";
    String  orgID;
    String boardid;
    String listID;
    @Test(priority = 1)
    void createOrganization(){
        test.log(LogStatus.INFO ,"Create Organization Test Case is started");
         Response response =given()
                 .header("Content-Type","Accept: application/json").
                 queryParam("key",telloKey)
                .queryParam("token",token)
                .queryParam("displayName",orgName).when().post("/1/organizations");
          JsonPath path =response.jsonPath();
          int  status=response.statusCode();
          orgID= path.getString("id");

        //System.out.println(orgID);
          Assert.assertEquals(response.statusCode() ,200);
        test.log(LogStatus.PASS,"Test Case Passed");

    }
    @Test(priority = 2)
    void  getOrganizationsForAmember(){
        test.log(LogStatus.INFO ,"getOrganizationsForAmember Test Case is started");
        Response response =given().queryParam("key",telloKey)
                .queryParam("token",token)
              .when().get("/1/members/me");
        response.prettyPrint();
        Assert.assertEquals(response.statusCode() ,200);
        test.log(LogStatus.PASS,"Test Case Passed");
    }
    @Test(priority = 3)
    void createBoard(){
        test.log(LogStatus.INFO ,"Create Board Test Case is started");
        Response response =given().
                header("Content-Type","Accept: application/json").
                queryParam("key",telloKey)
                .queryParam("token",token)
                .queryParam("name","boardName").
                queryParam("idOrganization",orgID).when().post("/1/boards/");
        JsonPath path =response.jsonPath();
         boardid= path.getString("id");
       // System.out.println(boardid);
        Assert.assertEquals(response.statusCode() ,200);
        test.log(LogStatus.PASS,"Test Case Passed");
    }
    @Test(priority = 4)
    void   getBoardsForanOrganization(){
        test.log(LogStatus.INFO ,"getBoardsForanOrganization Test Case is started");
        Response response =given().pathParam("id",orgID).
                queryParam("key",telloKey)
                .queryParam("token",token)
                .when().get("/1/organizations/"+"{id}"+"/boards");
        response.prettyPrint();
        Assert.assertEquals(response.statusCode() ,200);
        test.log(LogStatus.PASS,"Test Case Passed");
    }

    @Test(priority = 5)
    void  createNewList(){
        test.log(LogStatus.INFO ,"Create List Test Case is started");
        Response response =given().header("Content-Type","Accept: application/json").
                queryParam("key",telloKey)
                .queryParam("token",token).queryParam("idBoard",boardid)
                .queryParam("name","hh").when().post("/1/lists");
        JsonPath path =response.jsonPath();
        listID= path.getString("id");
        Assert.assertEquals(response.statusCode() ,200);
        test.log(LogStatus.PASS,"Test Case Passed");
    }
    @Test(priority = 6)
    void   getListsOnaBoard(){
        test.log(LogStatus.INFO ,"Get Lists on Board  Test Case is started");
        Response response =given().pathParam("id",boardid).
                queryParam("key",telloKey)
                .queryParam("token",token)
                .when().get("/1/boards/"+"{id}"+"/lists");
        response.prettyPrint();
        Assert.assertEquals(response.statusCode() ,200);
        test.log(LogStatus.PASS,"Test Case Passed");
    }

    @Test(priority = 7)
    void   archiveList(){
        test.log(LogStatus.INFO ,"Archive List Test Case is started");
        Response response =given().pathParam("listid",listID).
                queryParam("key",telloKey)
                .queryParam("token",token).queryParam("value","true")
                .when().put("/1/lists/"+"{listid}"+"/closed");
        response.prettyPrint();
        Assert.assertEquals(response.statusCode() ,200);
        test.log(LogStatus.PASS,"Test Case Passed");
    }
    @Test(priority = 8)

    void   deleteBoard(){
        test.log(LogStatus.INFO ,"Delete Board Test Case is started");
        Response response =given().pathParam("boardid",boardid).
                queryParam("key",telloKey)
                .queryParam("token",token).queryParam("value","true")
                .when().delete("/1/boards/"+"{boardid}");
        response.prettyPrint();
        Assert.assertEquals(response.statusCode() ,200);
        test.log(LogStatus.PASS,"Test Case Passed");
    }

    @Test(priority = 9)
    void   deleteOrganization(){
        test.log(LogStatus.INFO ,"Delete Organization Test Case is started");
        Response response =given().pathParam("id",orgID).
                queryParam("key",telloKey)
                .queryParam("token",token).queryParam("value","true")
                .when().delete("/1/organizations/"+"{id}");
        response.prettyPrint();
        Assert.assertEquals(response.statusCode() ,200);
        test.log(LogStatus.PASS,"Test Case Passed");
    }
    @AfterTest
    public void createreport(){
        report.endTest(test);
        report.flush();;


    }
}
