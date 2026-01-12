package concordia;

//----------------------------------------------------------------------------
// Serialized implements the 'Save Settings' and 'Restore' buttons
// The objects state are saved to file to be subsequently restored
//
//------------------------------------------------------------------------
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import concordia.domain.Company;

import concordia.annotations.Configuration;

@Configuration
public class Serialized {
    private ArrayList<Company> companyList;

    public Serialized(ArrayList<Company> companyList){
        // Use companyList parameter directly. No hidden dependency.
        try {
            if(companyList!=null){
                FileOutputStream fileStream = new FileOutputStream("Company.ser");
                ObjectOutputStream os=new ObjectOutputStream(fileStream);
                os.writeObject(companyList);
                os.close();
                fileStream.close();
            }
            else {
                System.out.println("Null Object");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void restoreSerialized(ArrayList<Company> companyList) {
        this.companyList = companyList;
        try {
            FileInputStream fileStream= new FileInputStream("Company.ser");
            ObjectInputStream os = new ObjectInputStream(fileStream);
            ArrayList<Company> restoredList =(ArrayList<Company> )os.readObject();
            // Removed obsolete Item/history logic. Add ServiceType/TransactionHistory logic if needed.
            os.close();
            fileStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}