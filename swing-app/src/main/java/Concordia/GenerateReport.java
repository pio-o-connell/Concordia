package concordia;

//----------------------------------------------------------------------------//
// Generate report interrogates the memory and generates a report
// Implement 'Report' functionality' in main window
// Launches Notepad with the results - 
// An enhancement would be to generate a HTML file with the results displayed in tabular form
//
// Note - ItemReport for date not implemented.
//-------------------------------------------------------------------------------------//
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import concordia.domain.Company;
import concordia.domain.User;
// Removed obsolete Item/history imports
// import concordia.domain.Index; // Removed: Index class does not exist
import java.util.Date;

//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.PreparedStatement;

public class GenerateReport {

    // Removed static/global Company. Use dependency injection via constructor or method parameter.
    
    Date dateFrom;
    Date dateTo;

    GenerateReport(ArrayList<Company> companyList) throws IOException{
        File outputFile = new File("Report-JavaProject.txt");
        FileWriter out = new FileWriter(outputFile);
        PrintWriter outputStream = new PrintWriter(out);
        for(int i=0;i<companyList.size();i++){
            Date date = new Date();
            outputStream.println("Date"+date.toString());
            outputStream.print("Company Name: "+companyList.get(i).getCompanyName());
            outputStream.println("\tCompany Id: "+companyList.get(i).getCompanyId());
            // No longer reporting on Item/history. Add ServiceType/TransactionHistory reporting as needed.
        }
        out.close();
    }

    // Removed GenerateItemsDatesReport method: relied on Index class which does not exist

}