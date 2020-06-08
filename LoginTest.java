
import DataReaders.CSVDataReaderLaunchpadp;
import DataReaders.CSVDataReaderLoginp;
import Helpers.BrowserInitHelper;
import Helpers.DriverHelper;
import Pom.LoginLaunchPage;
import Utils.Config;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.AfterExecution;
import org.graphwalker.java.annotation.BeforeExecution;
import org.graphwalker.java.annotation.GraphWalker;

@GraphWalker(value = "random(edge_coverage(100))", start = "e_openbrowserenterurl")
public class LoginTest extends ExecutionContext implements LoginLaunch {
    public static Config config = new Config();
    public static String browser, url, username, password, closeBrowser, chrome, firefox, multiSite, ticket, userlevel;
    int waitTime;
    Boolean headless;
    LoginLaunchPage loginLaunchPage = new LoginLaunchPage();
    CSVDataReaderLoginp csvDataReaderLoginp = new CSVDataReaderLoginp();
    CSVDataReaderLaunchpadp csvDataReaderLaunchpadp = new CSVDataReaderLaunchpadp();

    @BeforeExecution
    public void setup() {
        config.readProperties();
        browser = config.getBrowser();
        waitTime = config.getWaitTime();
        url = config.getURL();
        username = config.getUsername();
        password = config.getPassword();
        closeBrowser = config.getCloseBrowser();
        headless = config.getHeadless();
        chrome = config.getChrome();
        firefox = config.getFirefox();
        multiSite = config.getMultiSite();
        ticket = config.getTicket();
        userlevel = config.getUserLevel();
        csvDataReaderLaunchpadp.getCsv();
        csvDataReaderLoginp.getCsv();
        BrowserInitHelper.setup();

    }

    @AfterExecution
    public void cleanup() {
        if (BrowserInitHelper.getInstance() != null) {
            BrowserInitHelper.tearDown();
            BrowserInitHelper.getInstance().quit();
        }
    }

    public void e_openbrowserenterurl() {
        BrowserInitHelper.getInstance().manage().window().maximize();
        BrowserInitHelper.getInstance().get(url);
    }

    /*Verifying Login page by District name*/
    public void v_loginpage() {
        DriverHelper.CheckTextByxpath(loginLaunchPage.getdistrictname(), "District name", csvDataReaderLoginp.getDistrictname());
    }

    /*Entering Username and Password, click on Sign in button*/
    public void e_entervalidcredentialscloickonsignin() {
     DriverHelper.sendKeysXpath(loginLaunchPage.getusername(), Config.getUsername());
     DriverHelper.setPassword_JS(Config.getPassword());
     DriverHelper.clickXpath(loginLaunchPage.getsignin());
    }

    /*Verifying the Launchpad Page*/
    public void v_launchpadPage() {
        BrowserInitHelper.getMaxWaiter();
        DriverHelper.CheckTextByxpath(loginLaunchPage.getlaunchpad(),"Launchpad", csvDataReaderLaunchpadp.getLaunchPadtitle());
    }
}
