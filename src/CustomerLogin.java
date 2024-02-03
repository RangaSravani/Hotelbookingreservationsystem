import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class CustomerLogin
{
	static void customerLog()
	{
		String option="y";
	     int choice;
	     Scanner din=new Scanner(System.in);
	     while(option.equalsIgnoreCase("y"))
	     {
	    	 System.out.println("1. customer Register");   // Print statement
	    		System.out.println("2.customer Login ");     // print statement
	    		System.out.println("3.Logout");            // print statement
				System.out.println("Select Your Option");    // print statement
				choice=din.nextInt();
				switch(choice)
				{
				case 1:customerRegister();
					break;
				case 2:
					customerLogin();		
					 break;
				case 3: System.out.println("Customer Logout Success");					
				}			
				System.out.println("Do You Want to Continue Press Y or For Exit Press N");
	     }
	}
static void customerRegister()
{
	String cname,cemail,cmobile,cgender,ccity,cstate,cpaasword;
	Scanner din = new Scanner(System.in);
	
	System.out.println("***** Welcome top Happy Hotel Booking ****");
	System.out.println("Enter Customer Name");
	cname = din.next();
	System.out.println("Enter Customer Email");
	cemail = din.next();
	System.out.println("Enter Customer Password");
	cpaasword = din.next();
	System.out.println("Enter Customer Mobile");
	cmobile = din.next();
	System.out.println("Enter Customer Gender");
	cgender = din.next();
	System.out.println("Enter Customer City");
	ccity = din.next();
	System.out.println("Enter Customer State");
	cstate = din.next();
	try
	   {		   
		  Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbooking","root","1234");  // getConnection method is a Static method
		   String sql="insert into customer(customer_name,customer_email,password,customer_mobile,customer_gender,customer_city,customer_state)values(?,?,?,?,?,?,?)";
		   PreparedStatement ps=con.prepareStatement(sql);
		   ps.setString(1, cname);
		   ps.setString(2, cemail);
		   ps.setString(3, cpaasword);
		   ps.setString(4, cmobile);
		   ps.setString(5, cgender);
		   ps.setString(6, ccity);
		   ps.setString(7, cstate);		   
		   int i= ps.executeUpdate();
		   if(i>0)
		   {
			   System.out.println("Customer Added successfully");
			   // Email Sending Customer 
			   String senderEmail = "rangasravani2001@gmail.com";
		        String senderPassword = "zkjy lqgt yqdp ecoz";
		        // Recipient's email address
		        String RecipientEmail=cemail;//change accordingly  
		        // Set up properties for the email server
		        Properties properties = new Properties();
		        properties.put("mail.smtp.auth", "true");
		        properties.put("mail.smtp.starttls.enable", "true");
		        properties.put("mail.smtp.host", "smtp.gmail.com");
		        properties.put("mail.smtp.port", "587");
		        // Get the email session
		        Session session = Session.getInstance(properties, new Authenticator() {
		            @Override
		            protected PasswordAuthentication getPasswordAuthentication() {
		                return new PasswordAuthentication(senderEmail, senderPassword);
		            }
		        });
		        try {
		            // Create a message
		            Message message = new MimeMessage(session);
		            message.setFrom(new InternetAddress(senderEmail));
		            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(RecipientEmail));
		            message.setSubject("Hotel Reservation Confirmation");
		            message.setText("Dear " + cname + ",\n\nYou Successfully Registered\n\n\n  Thank you for choosing our hotel!");
		            // Send the message
		            Transport.send(message);
		            System.out.println("Please Login .... Start Booking your hotel rooms");
		            customerLog();
		        } 
		        catch (MessagingException e) 
		        {
		            e.printStackTrace();		            
		            System.out.println("Error sending confirmation email.");		            
		            return;
		        }
		   }
		   else
		   {
			   System.out.println("Customer not added..");
		   }
	   }
	   catch(SQLException e)
	   {
		   e.printStackTrace();
	   }	
}
static void customerLogin()
{
	Scanner din = new Scanner(System.in);
	String username,password;
	System.out.println("Enter Your Email");
	username = din.next();
	System.out.println("Enter Your Password");
	password = din.next(); 
	int check=0;
	try
	{
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbooking", "root", "1234");
		String sql="select * from customer";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		while(rs.next())
		{
			if(username.equals(rs.getString(3)) && password.equals(rs.getString(7)))
			{
				check = 1;
			}
		}
		if(check==1)
		{
			System.out.println("Welcome To Customer");
			process();
		}
		else
		{
			System.out.println("Invalid Credentials or Register First");
			customerLog();
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}
static void process()
{
	String option="y";
    int choice;
    Scanner din=new Scanner(System.in);
    try {
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbooking","root","1234");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    while(option.equalsIgnoreCase("y"))
    {
   	 Scanner sc=new Scanner(System.in); 	
		 System.out.println("1.VIEW ROOMS");  // Print statement
		 System.out.println("2.BOOK ROOMS");    // Print statement
		 System.out.println("3.BOOKED ROOMS");  // Print statement
		 System.out.println("4.CANCEL ROOMS");  // Print statement
   		System.out.println("5.View My History");     // print statement
  		 System.out.println("6.EXIT");                // Print statement
			System.out.println("Select Your Option");
			choice=din.nextInt();
			switch(choice)
			{
			 case 1:
				view_rooms();
					 break;
				 case 2:			
				try {
					book_rooms();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					 break;				 
				 case 3:
				try {
					booked_rooms();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					 break;				 
				 case 4:
					cancelrooms(choice);
					 break;				 
				 case 5:
					 viewmyhistory();
					 break;				 			 
			case 6:
				exit();
				 break; 				 
			 default:
				 System.out.println("Invalid choice. Try again. ");   // Print statement				
			}			
    }	
}

static void view_rooms()
{
	try
   	{
		String ch="yes";
   		String sql="select * from add_rooms where   Availability_status ='"+ ch +"' ";
   		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbooking","root","1234");
           	Statement st=con.createStatement();
           	ResultSet rs=st.executeQuery(sql);
           	System.out.println("List of Rooms  Available");
           	System.out.println("*-------------*------------*-------------*-----------------------*");
   			System.out.println("| roomnumber | room_type   | room_price  |  Availability_status | ");
   			System.out.println("*------------*-------------*-------------*-----------------------*");
           	while(rs.next())
           	{    
           		int roomnumber=rs.getInt(1);
           		String room_type=rs.getString(2);
           		int room_price=rs.getInt(3);
          		String availability=rs.getString("Availability_status");
           		System.out.printf("| %-11d | %-13s | %-11d | %-18s |\n ",roomnumber,room_type,room_price,availability);
           	}
           		System.out.println("*-----------*------------*---------------*--------------------*");           	
   	}
   	catch(SQLException e)
   	{
   		e.printStackTrace();
   	}
}
static void book_rooms() throws ClassNotFoundException 
{	
	try 
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbooking", "root", "1234");
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter Your  name: ");   // Print statement
		String guestName = sc.next();
		sc.nextLine();
		System.out.println("Enter Room number: ");   // Print statement
		int roomNumber = sc.nextInt();
		System.out.println("Enter contact Number: ");
		String contactNumber = sc.next();
		System.out.println("Enter Reservation Date (YYYY-MM-DD):");
		String input = sc.next();
		Date reservation_dateandtime= Date.valueOf(input);
		System.out.println("Enter you are  email");    
		 String cemail=sc.next();
		String sql = "insert into  book_rooms(guest_name, room_number, contact_number,reservation_dateandtime,customer_email)" +
		           "values('" + guestName + "'," + roomNumber + ",' " +contactNumber +"','"+reservation_dateandtime+"','"+cemail+"')";
		try (Statement st = con.createStatement())
		{
			int affectedRows = st.executeUpdate(sql);			
			if (affectedRows > 0)
			{		
				System.out.println("Reservation successful!...");
				 // Sender's email address and credentials
		        String senderEmail = "rangasravani2001@gmail.com";
		        String senderPassword = "zkjy lqgt yqdp ecoz";
		        // Recipient's email address
		        String RecipientEmail=cemail;//change accordingly  
		        // Set up properties for the email server
		        Properties properties = new Properties();
		        properties.put("mail.smtp.auth", "true");
		        properties.put("mail.smtp.starttls.enable", "true");
		        properties.put("mail.smtp.host", "smtp.gmail.com");
		        properties.put("mail.smtp.port", "587");
		        // Get the email session
		        Session session = Session.getInstance(properties, new Authenticator() {
		            @Override
		            protected PasswordAuthentication getPasswordAuthentication() {
		                return new PasswordAuthentication(senderEmail, senderPassword);
		            }
		        });
		        try {
		            // Create a message
		            Message message = new MimeMessage(session);
		            message.setFrom(new InternetAddress(senderEmail));
		            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(RecipientEmail));
		            message.setSubject("Hotel Reservation Confirmation");
		            message.setText("Dear " + guestName + ",\n\nYour reservation for Room " + roomNumber +
		                    " on " + reservation_dateandtime + " has been booked... Thank you for choosing our hotel!");
		            // Send the message
		            Transport.send(message);
		            System.out.println("Request email sent successfully.");
		            System.out.println("reservations is conformed for room "+roomNumber+".");
		        } catch (MessagingException e) 
		        {
		            e.printStackTrace();		            
		            System.out.println("Error sending confirmation email.");
		            System.out.println("Booking failed.Room might be unavailable.");
		            System.out.println("Sorry!!Reservation is not conformed");
		            System.out.println("reservation is not  conformed for room "+roomNumber+".");
		            return;
		        }
			}			
			else 
			{
				System.out.println("Reservation failed...");    // Print statement
			}
		}
	}
	catch(SQLException e)   // catch is a method,during at run time if we are any issue it will display the error
	{
		e.printStackTrace();
	}
}
   private static void booked_rooms() throws SQLException   // User-defined Method
{
	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbooking", "root", "1234");
   String sql = "select * from  booked_rooms";
	try(Statement st=con.createStatement();			
		 ResultSet rs=st.executeQuery(sql))
	{
		System.out.println("Current bookedrooms : ");    // Print statement
		System.out.println("*----------------*----------------*-----------------*");
		System.out.println("|  Booking Id    |  Room_Number   |   Availability  |");
		System.out.println("*----------------*----------------*-----------------*");
		while(rs.next())
		{
			String sql1="insert into booked_rooms(booking_id,room_number,Availability)values(?,?)";
			int bookingid=rs.getInt("booking_id");
			int roomNumber=rs.getInt("room_number");
		    String availability=rs.getString("availability");		    
			System.out.printf("|  %-15s  |  %-15s |  %-19s  |\n ",bookingid,roomNumber,availability);
		}
		System.out.println("*----------------*-----------------*------------------*");
	}
	catch(SQLException e)   // catch is a method,during at run time if we are any issue it will display the error
	{
		e.printStackTrace();
	}
}
 private static void cancelrooms(int bookingId)
{
	String sql="delete from booked_rooms where booking_id=?";
	try(Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbooking", "root", "1234");
			PreparedStatement preparedStatement=con.prepareStatement(sql))
	{
        System.out.println("Enter Your Booking Id");

    preparedStatement.setInt(1,bookingId);
    int rowsAffected=preparedStatement.executeUpdate();
    if(rowsAffected>0)
    {
    	System.out.println("Booking canceled successfully.");
    }
    else
    {
		System.out.println("Booking not found or cancellation failed.");
		
		
		
    }
	}
	catch(SQLException e)
	{
		e.printStackTrace();
	}
	}
static void  viewmyhistory()
{
	String sql="select * from booked_rooms";
	try(Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbooking", "root", "1234");
			PreparedStatement preparedStatement=con.prepareStatement(sql);
	        ResultSet rs=preparedStatement.executeQuery())
	{
		while(rs.next())
		{
			int customerId=rs.getInt("customerid");
			int roomNumber=rs.getInt("room_number");
		    String CheckInDate=rs.getString("checkInDate");
		    String CheckOutDate=rs.getString("checkOutDate");
		    System.out.println("customer Id:"+customerId+",room number:"+roomNumber+",CheckInDate:"+ CheckInDate+",CheckOutDate:"+CheckOutDate);
		}		
	}
	catch(SQLException e)
	{
		e.printStackTrace();
	}
}
static void exit()
{
	Scanner sc=new Scanner(System.in);
	System.out.println("Do You Want To Continue Press Y Or Exit Press N");
	String choice = sc.next();
}
}
 

