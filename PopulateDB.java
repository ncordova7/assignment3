import oracle.jrockit.jfr.StringConstantPool;

import java.lang.*;
import java.io.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class PopulateDB {

    List<List<String>> attributes = new ArrayList<List<String>>();
    Connection conn;


    public PopulateDB() {

    }

    //Takes the csvfile as a parameter and extrats the information into an array
    public List<List<String>> CSVReader(String csvfile) throws FileNotFoundException{

        Scanner s = new Scanner(new File(csvfile));
        s.useDelimiter(",");

        //Reads through each line of text
        while(s.hasNext()) {
            String temp = s.nextLine();
            List<String> tuples = Arrays.asList(temp.split(","));

            attributes.add(tuples);
        }
        s.close();
        //System.out.println(attributes);
        return attributes;
    }

    //Prints out Information from the Database Tables
    public void DisplayDatabase(Connection conn) throws Exception{
        Scanner dataTable = new Scanner(System.in);
        System.out.println("Enter the number of the data table you like to display: " + "\n" +
                "1.Clients" + "\n" +
                "2.Employees" + "\n" +
                "3.Appointments" + "\n" +
                "4.Addresses" + "\n" +
                "5.Finances" + "\n" );

        int response = dataTable.nextInt();

        switch (response) {
            case 1: {
                String displaySql = "SELECT * FROM Clients";

                try (PreparedStatement query = conn.prepareStatement(displaySql)) {
                    ResultSet rs = query.executeQuery();
                    displayClients(conn, rs);
                    rs.close();

                    break;
                }

            }case 2: {
                String displaySql = "SELECT * FROM Employees";

                try (PreparedStatement query = conn.prepareStatement(displaySql)) {
                    ResultSet rs = query.executeQuery();
                    displayEmployees(conn, rs);
                    rs.close();

                    break;
                }

            }case 3: {
                String displaySql = "SELECT * FROM Appointments";

                try (PreparedStatement query = conn.prepareStatement(displaySql)) {
                    ResultSet rs = query.executeQuery();
                    displayAppointments(conn, rs);
                    rs.close();

                    break;
                }

            }case 4: {
                String displaySql = "SELECT * FROM Addresses";

                try (PreparedStatement query = conn.prepareStatement(displaySql)) {
                    ResultSet rs = query.executeQuery();
                    displayAddresses(conn, rs);
                    rs.close();

                    break;
                }

            }case 5: {
                String displaySql = "SELECT * FROM Finances";

                try (PreparedStatement query = conn.prepareStatement(displaySql)) {
                    ResultSet rs = query.executeQuery();
                    displayFinances(conn, rs);
                    rs.close();

                    break;
                }

            }
        }

    }

    //Inserts Data to the Database Tables
    public void InsertToDatabase(Connection conn) throws Exception{

        String addressInsert = "Insert into Addresses(Street, City, Zipcode)" +
                "VALUES (?, ?, ?)";
        String appointmentInsert = "Insert into Appointments(AppointmentID, ServiceID, Appointment, HairColor, CustomerID)" +
                "VALUES (?, ?, ?, ?, ?)";
        String ClientsInsert = "Insert into Clients(CustomerID,FirstName, LastName, Street, City, Zipcode, PhoneNum, email, CreditNum, CardExpiration, CardCode)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String EmployeeInsert = "Insert into Employees(EmployeeID, FirstName, LastName, Street, City, Zipcode, PhoneNum)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        String finanacesInsert = "Insert into Finances(CustomerID, CardNum, CardExpiration, CardCode)" +
                "VALUES (?, ?, ?, ?)";

        for(int i = 1; i < attributes.size(); i++) { // start from 1 to exclude headers
            List<String> row = attributes.get(i);
            Integer ClientID = i;
            Integer EmployeeID = i;
            Integer AppointmentID = i;

            String Client_fName = row.get(0);
            String Client_lName = row.get(1);
            String C_phoneNum = row.get(2);
            String email = row.get(3);

            String C_street_address= row.get(4);
            String  C_city = row.get(5);
            Integer C_zipcode = Integer.valueOf(row.get(6));

            String creditCardNum = (row.get(7));
            String cardExp = (row.get(8));
            Integer cardCode = Integer.valueOf(row.get(9));
            //System.out.println(cardExp);

            String Employee_fName = row.get(10);
            String Employee_lName = row.get(11);
            String E_phoneNum = row.get(12);
            String E_street_address= row.get(13);
            String  E_city = row.get(14);
            Integer E_zipcode = Integer.valueOf(row.get(15));

            String HairColor = row.get(16);
            String NextAppointment = row.get(17);
            String ServiceID = row.get(18);

            try (PreparedStatement query = conn.prepareStatement(ClientsInsert)){
                query.setInt(1, ClientID);
                query.setString(2, Client_fName);
                query.setString(3, Client_lName);
                query.setString(4, C_street_address);
                query.setString(5, C_city);
                query.setInt(6, C_zipcode);
                query.setString(9, creditCardNum);
                query.setString(8, email);
                query.setString(7, C_phoneNum);
                query.setString(10, cardExp);
                query.setInt(11, cardCode);
                query.executeUpdate();
                query.clearParameters();
                System.out.println("Inserted!");
            }
            try (PreparedStatement query = conn.prepareStatement(addressInsert)){
                query.setString(1, C_street_address);
                query.setString(2, C_city);
                query.setInt(3, C_zipcode);
                query.setString(1, E_street_address);
                query.setString(2, E_city);
                query.setInt(3, E_zipcode);
                query.executeUpdate();
                query.clearParameters();
                System.out.println("Inserted!");
            }
            try (PreparedStatement query = conn.prepareStatement(appointmentInsert)){
                query.setInt(1, AppointmentID);
                query.setString(2, ServiceID);
                query.setString(4, HairColor);
                query.setString(3, NextAppointment);
                query.setInt(5, ClientID);
                query.executeUpdate();
                query.clearParameters();
                System.out.println("Inserted!");
            }
            try (PreparedStatement query = conn.prepareStatement(EmployeeInsert)){
                query.setInt(1, EmployeeID);
                query.setString(2, Employee_fName);
                query.setString(3, Employee_lName);
                query.setString(4, E_street_address);
                query.setString(5, E_city);
                query.setInt(6, E_zipcode);
                query.setString(7, E_phoneNum);
                query.executeUpdate();
                query.clearParameters();
                System.out.println("Inserted!");
            }
            try (PreparedStatement query = conn.prepareStatement(finanacesInsert)){
                query.setInt(1, ClientID);
                query.setString(2, creditCardNum);
                query.setInt(4, cardCode);
                query.setString(3, cardExp);
                query.executeUpdate();
                query.clearParameters();
                System.out.println("Inserted!");
            }

        }
    }

    //Querries to print data from each respective table
    private static void displayClients(Connection conn, ResultSet rs) throws SQLException{

            //System.out.println("If"+ response);
            try (PreparedStatement query = conn.prepareStatement("SELECT * From Clients")) {
                System.out.println("\n");
                while (rs.next()) {
                    Integer C_ID = rs.getInt("CustomerID");
                    String C_fName = rs.getString("FirstName");
                    String C_lName = rs.getString("LastName");
                    String C_streetAdd = rs.getString("Street");
                    String C_city = rs.getString("City");
                    Integer C_zip = rs.getInt("Zipcode");
                    String email = rs.getString("Email");
                    String phone = rs.getString("PhoneNum");
                    String creditNum = rs.getString("CreditNum");
                    String cardExp = rs.getString("CardExpiration");
                    String cardCode = rs.getString("CardCode");


                    System.out.print(
                        "ClientID: " + C_ID + "\t\t" +
                        " First Name: " + C_fName + "\t\t" +
                        " Last Name: " + C_lName + "\t\t" +
                        " Street Address: " + C_streetAdd + "\t\t" +
                        " City:  " + C_city + "\t\t" +
                        " Zipcode: " + C_zip + "\t\t" +
                        " Email: " + email + "\t\t" +
                        " Phone Number:" + phone + "\t\t" +
                        " Credit Card Number" + creditNum + "\t\t" +
                        " Credit Card Expiration Date: " + cardExp + "\t\t" +
                        " Credit Card Security Code: " + cardCode + "\n"
                    );

                }
                rs.close();
                //query.executeUpdate();
            }

    }
    private static void displayEmployees(Connection conn, ResultSet rs) throws SQLException{
        try (PreparedStatement query = conn.prepareStatement("SELECT * From Employees")) {
            System.out.println("\n");
            while (rs.next()) {
                Integer E_ID = rs.getInt("EmployeeID");
                String E_fName = rs.getString("FirstName");
                String E_lName = rs.getString("LastName");
                String E_streetAdd = rs.getString("Street");
                String E_city = rs.getString("City");
                Integer E_zip = rs.getInt("Zipcode");
                String phone = rs.getString("PhoneNum");



                System.out.print(
                    "EmployeeID: " + E_ID + "\t\t" +
                    " First Name: " + E_fName + "\t\t" +
                    " Last Name: " + E_lName + "\t\t" +
                    " Street Address: " + E_streetAdd + "\t\t" +
                    " City:  " + E_city + "\t\t" +
                    " Zipcode: " + E_zip + "\t\t" +
                    " Phone Number:" + phone + "\n"

                );

            }
            rs.close();
            //query.executeUpdate();
        }

    }
    private static void displayAppointments(Connection conn, ResultSet rs) throws SQLException{
        try (PreparedStatement query = conn.prepareStatement("SELECT * From Employees")) {
            System.out.println("\n");
            while (rs.next()) {
                Integer A_ID = rs.getInt("AppointmentID");
                String AppointmentDate = rs.getString("Appointment");
                String ServiceID = rs.getString("ServiceID");
                String HairColor = rs.getString("HairColor");
                Integer C_ID = rs.getInt("CustomerID");

                System.out.print(
                    " Appointment ID " + A_ID + "\t\t" +
                    " Client's Next Appointment Date: " + AppointmentDate + "\t\t" +
                    " Service: " + ServiceID + "\t\t" +
                    " Hair Color: " + HairColor + "\t\t" +
                    " CustomerID:  " + C_ID + "\n"

                );

            }
            rs.close();
            //query.executeUpdate();
        }

    }
    private static void displayAddresses(Connection conn, ResultSet rs) throws SQLException{
        try (PreparedStatement query = conn.prepareStatement("SELECT * From Addresses")) {
            System.out.println("\n");
            while (rs.next()) {
                String E_streetAdd = rs.getString("Street");
                String E_city = rs.getString("City");
                Integer E_zip = rs.getInt("Zipcode");


                System.out.print(
                    " Street Address: " + E_streetAdd + "\t\t" +
                    " City:  " + E_city + "\t\t" +
                    " Zipcode: " + E_zip + "\n"
                );

            }
            rs.close();
            //query.executeUpdate();
        }

    }
    private static void displayFinances(Connection conn, ResultSet rs) throws SQLException{
        try (PreparedStatement query = conn.prepareStatement("SELECT * From Finances")) {
            System.out.println("\n");
            while (rs.next()) {
                Integer C_ID = rs.getInt("CustomerID");
                String creditNum = rs.getString("CardNum");
                String cardExp = rs.getString("CardExpiration");
                String cardCode = rs.getString("CardCode");



                System.out.print(
                    "ClientID: " + C_ID + "\t\t" +
                    " Credit Card Number" + creditNum + "\t\t" +
                    " Credit Card Expiration Date: " + cardExp + "\t\t" +
                    " Credit Card Security Code: " + cardCode + "\n"
                );

            }
            rs.close();
            //query.executeUpdate();
        }

    }
}
