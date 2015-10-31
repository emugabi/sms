package student.registration.utils;

//STEP 1. Import required packages
import com.mysql.jdbc.exceptions.DeadlockTimeoutRollbackMarker;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javafx.util.Pair;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DBConnect {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/UCU";

	// Database credentials
	static final String USER = "root";
	static final String PASS = ""; // add password here

	Connection conn = null;
	Statement stmt = null;

	public DBConnect() {

		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} 
	}

	public boolean login(String username, String password) {

		PreparedStatement ps;
		try {
			ps = conn
					.prepareStatement("select username from users where username=? and password=?");
			ps.setString(1, username);
			ps.setString(2, password);
                         System.out.println("user "+username+" pass "+password);
			ResultSet rs = ps.executeQuery();

			// STEP 5: Extract data from result set
			if (rs.next()) {
				
				System.out.println("User signed in: ");
				// STEP 6: Clean-up environment
				
				return true;

			} else {
				System.out.println("Sign in error ");
				
				return false;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("Goodbye!");
		return false;
	}
        
        public String getRole(String username) {
                String role = null;
		PreparedStatement ps;
		try {
			ps = conn
					.prepareStatement("select role from users where username=?");
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();

			// STEP 5: Extract data from result set
			if (rs.next()) {
				role = rs.getString("role");
				System.out.println(" User signed is "+role);
				// STEP 6: Clean-up environment
				
				return role;

			} else {
				System.out.println(" Could not find user's role");
				
				return null;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("Goodbye!");
		return role;
	}

    public void addStudent(String fullname, String enrolNo, String dob, String address, String year) {
        PreparedStatement ps;
        try {
			ps = conn.prepareStatement("INSERT INTO `students` (`id`, `name`, `enrol_no`, `DOB`, `address` `batch`) VALUES (NULL, ?, ?, ?, ?, ?)");
                        
                        System.out.println("INSERT INTO `ucu`.`students` = "+fullname);
                        
			ps.setString(1, fullname);
                        ps.setString(2, enrolNo);
                        ps.setString(3, dob);
                        ps.setString(4, address);
                              ps.setString(5, year);
                        ps.executeUpdate();
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public TableData getAllStudents() {
        PreparedStatement ps;
		// ArrayList columnNames = new ArrayList();
		// ArrayList data = new ArrayList();
		Object[][] data = null;
                int row = 0;
		//String[] columnNames = { "id", "enrol_no", "sem", "intmark", "extmark", "total" };
		try {
			ps = conn.prepareStatement("select * from students");
                        
                        System.out.println("select * from students");
                        
			

			ResultSet allStudents = ps.executeQuery();

			ResultSetMetaData md = allStudents.getMetaData();
			int columns = md.getColumnCount();
			// STEP 5: Extract data from result set

			data = new Object[columns][5];

			
			while (allStudents.next()) {
				data[row][0] = allStudents.getString("name");
				data[row][1] = allStudents.getString("enrol_no");
				data[row][2] = allStudents.getString("DOB");
				data[row][3] = allStudents.getString("address");
				data[row][4] = allStudents.getString("batch");
				
                                System.out.println("sampling msql data data[row][1] ="+data[row][1]);
				row++;
			}

			// STEP 6: Clean-up environment
			allStudents.close();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new TableData(row, data);

	}// end getallstudents

    public TableData searchSubject_by_name(String searchTerm) {
       PreparedStatement ps;
		// ArrayList columnNames = new ArrayList();
		// ArrayList data = new ArrayList();
		Object[][] data = null;
                int row = 0;
		//String[] columnNames = { "id", "enrol_no", "sem", "intmark", "extmark", "total" };
		try {
			ps = conn.prepareStatement("select * from subject WHERE `subName` LIKE ? ");
                        
                        System.out.println("select * from subject  where subname = "+searchTerm);
                        
			ps.setString(1, "%"+searchTerm+"%");

			ResultSet subjectSearch = ps.executeQuery();

			ResultSetMetaData md = subjectSearch.getMetaData();
			int columns = md.getColumnCount();
			// STEP 5: Extract data from result set

			data = new Object[columns][4];

			
			while (subjectSearch.next()) {
				data[row][0] = subjectSearch.getString("id");
				data[row][1] = subjectSearch.getString("subName");
				data[row][2] = subjectSearch.getString("subCode");
				data[row][3] = subjectSearch.getString("semester");
				
                                System.out.println("sampling msql data data[row][1] ="+data[row][1]);
				row++;
			}

			// STEP 6: Clean-up environment
			subjectSearch.close();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new TableData(row, data);
 
    }
    
    
     public TableData searchSubject_by_code(String searchTerm) {
         PreparedStatement ps;
		// ArrayList columnNames = new ArrayList();
		// ArrayList data = new ArrayList();
		Object[][] data = null;
                int row = 0;
		//String[] columnNames = { "id", "enrol_no", "sem", "intmark", "extmark", "total" };
		try {
			ps = conn.prepareStatement("select * from subject WHERE `subCode` LIKE ? ");
                        
                        System.out.println("select * from subject  where subCode LIKE %"+searchTerm+"%");
                        
			ps.setString(1, "%"+searchTerm+"%");

			ResultSet subjectSearch = ps.executeQuery();

			ResultSetMetaData md = subjectSearch.getMetaData();
			int columns = md.getColumnCount();
                        
			// STEP 5: Extract data from result set

			data = new Object[columns][4];

			
			while (subjectSearch.next()) {
				data[row][0] = subjectSearch.getString("id");
				data[row][1] = subjectSearch.getString("subName");
				data[row][2] = subjectSearch.getString("subCode");
				data[row][3] = subjectSearch.getString("semester");
				
                                System.out.println("sampling msql data data[row][1] ="+data[row][1]);
				row++;
			}

			// STEP 6: Clean-up environment
			subjectSearch.close();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new TableData(row, data);

    }

    public void deleteStudent(String enrolNo) {
        PreparedStatement ps;
        try {
			ps = conn.prepareStatement("DELETE FROM `students` where = ?");
                        
                        System.out.println("DELETE FROM `students` where = "+enrolNo);
                        
			ps.setString(1, enrolNo);
                        ps.executeUpdate();
                        
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    public ArrayList<String> getTeacherInfo(String teacherUsername) {
        
        

		PreparedStatement ps;
		// ArrayList columnNames = new ArrayList();
		// ArrayList data = new ArrayList();
		ArrayList<String> data = null;
               
		try {
			ps = conn.prepareStatement("select * from teachers  where username=? ");
                        
                        System.out.println("select * from teachers  where username = "+teacherUsername);
                        
			ps.setString(1, teacherUsername);
                        
                    try (ResultSet teacherInfo = ps.executeQuery()) {
                        ResultSetMetaData md = teacherInfo.getMetaData();
                        
                        
                        // STEP 5: Extract data from result set
                        
                        data = new ArrayList<>();
                        
                        
                        while (teacherInfo.next()) {
                            data.add(teacherInfo.getString("id"));
                            data.add(teacherInfo.getString("name"));
                            data.add(teacherInfo.getString("username"));
                            data.add(teacherInfo.getString("year"));
                            data.add(teacherInfo.getString("subCode"));
                            
                        }
                    }
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;

	}// end teacherInfor

    public void addStudentMarks(String enrol, String internal, String external, int total, boolean pass, String subcode) {
        
        PreparedStatement ps;
        try {
			ps = conn.prepareStatement("INSERT INTO `results` (`id`,`enrol_no`, `subCode`,`intmark`, `extmark`, `total`, `pass` ) VALUES (NULL, ?, ?, ?, ?, ?, ?)");
                        
                        System.out.println("INSERT INTO `ucu`.`results` = "+enrol);
                        
                        ps.setString(1, enrol);
                        ps.setString(2, subcode);
			ps.setString(3, internal);
                        ps.setString(4, external);
                        ps.setString(5, ""+total);
                        ps.setBoolean(6, pass);
                             
                        ps.executeUpdate();
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    }
    
     public void updateStudentMarks(String enrol, String internal, String external, int total, Boolean pass, String subcode) {
        
        PreparedStatement ps;
        try {
			ps = conn.prepareStatement("UPDATE results SET subCode = ?, intmark = ?, extmark = ?, total = ?, pass = ? WHERE `enrol_no`= ?");
                        
                        System.out.println("UPDATE INTO `ucu`.`results` where enrol_no = "+enrol);
                        
                       
                        ps.setString(1, subcode);
			ps.setString(2, internal);
                        ps.setString(3, external);
                        ps.setString(4, ""+total);
                        ps.setBoolean(5, pass);
                         ps.setString(6, enrol);
                         ps.executeUpdate();
                        
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    }

    public TableData getSubjectbyStudent(String retrievedenrol_no) {
        PreparedStatement ps;
		// ArrayList columnNames = new ArrayList();
		// ArrayList data = new ArrayList();
		Object[][] data = null;
                int row = 0;
		//String[] columnNames = { "id", "enrol_no", "sem", "intmark", "extmark", "total" };
		try {
			ps = conn.prepareStatement("select subject.subCode, subName from subject INNER JOIN  results on subject.subCode = results.subCode WHERE enrol_no = ? ");
                        
                        System.out.println("select * from subject  where subCode LIKE %"+retrievedenrol_no+"%");
                        
			ps.setString(1, "%"+retrievedenrol_no+"%");

			ResultSet subjectSearch = ps.executeQuery();

			ResultSetMetaData md = subjectSearch.getMetaData();
			int columns = md.getColumnCount();
                        
			// STEP 5: Extract data from result set

			data = new Object[columns][4];

			
			while (subjectSearch.next()) {
				data[row][0] = subjectSearch.getString("id");
				data[row][1] = subjectSearch.getString("subName");
				data[row][2] = subjectSearch.getString("subCode");
				data[row][3] = subjectSearch.getString("semester");
				
                                System.out.println("sampling msql data data[row][1] ="+data[row][1]);
				row++;
			}

			// STEP 6: Clean-up environment
			subjectSearch.close();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new TableData(row, data);

    
    }

    public void updateStudentInfo(int id, String fullname, String enrolNo, String year, String address, String dob) {
        
        PreparedStatement ps;
        try {
			ps = conn.prepareStatement("UPDATE `students` SET `name`= ?,`enrol_no`= ?,`batch`= ?,`address`=?,`DOB`= ? WHERE `id`= ?");
                        
                        System.out.println("UPDATE INTO `ucu`.`results` where id = "+id);
                        
                       
                        ps.setString(1, fullname);
			ps.setString(2, enrolNo);
                        ps.setString(3, year);
                        ps.setString(4, ""+address);
                        ps.setString(5, dob);
                         ps.setInt(6, id);
                           ps.executeUpdate();
                        
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
        
    }

    public void addUser(String username, String password, String role) {
        PreparedStatement ps;
        try {
			ps = conn.prepareStatement("INSERT INTO `users` (`userid`,`username`, `password`,`role` ) VALUES (NULL, ?, ?, ?)");
                        
                        System.out.println("INSERT INTO `ucu`.`results` = "+username);
                        
                        ps.setString(1, username);
                        ps.setString(2, password);
			ps.setString(3, role);
                      
                       
                        ps.executeUpdate();
                        
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void updateAccount(int userid, String username, String password, String role) {
         //To change body of generated methods, choose Tools | Templates.
         PreparedStatement ps;
        try {
			ps = conn.prepareStatement("UPDATE `users` SET username = ? , password = ? , role= ? WHERE `userid`= ?");
                        
                        System.out.println("UPDATE INTO `ucu`.`users` where userid = "+userid);
                        
                       
                        ps.setString(1, username);
			ps.setString(2, password);
                        ps.setString(3, role);
                        
                         ps.setInt(4, userid);
                         ps.executeUpdate();
                             
                        
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void deleteAccount(int selectedRow) {
          PreparedStatement ps;
        try {
			ps = conn.prepareStatement("DELETE FROM `users` where userid = ?");
                        
                        System.out.println("DELETE FROM `users` where = "+selectedRow);
                        
			ps.setInt(1, selectedRow);
                        
                        ps.executeUpdate();
                        
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public TableData getAllUsers() {
        PreparedStatement ps;
		// ArrayList columnNames = new ArrayList();
		// ArrayList data = new ArrayList();
		Object[][] data = null;
                int row = 0;
		//String[] columnNames = { "id", "enrol_no", "sem", "intmark", "extmark", "total" };
		try {
			ps = conn.prepareStatement("select * from users");
                        
                        System.out.println("select * from users");
                        
			

			ResultSet allUsers = ps.executeQuery();

			ResultSetMetaData md = allUsers.getMetaData();
			int columns = md.getColumnCount();
			// STEP 5: Extract data from result set

			data = new Object[columns][4];

			
			while (allUsers.next()) {
				data[row][0] = allUsers.getString("username");
				data[row][1] = allUsers.getString("userid");
				data[row][2] = allUsers.getString("password");
				data[row][3] = allUsers.getString("role");
				
				
                                System.out.println("sampling msql data data[row][1] ="+data[row][1]);
				row++;
			}

			// STEP 6: Clean-up environment
			allUsers.close();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new TableData(row, data);
    }
    
    
    
    

	static class Marks {
		public String getSem() {
			return sem;
		}

		public Float getIntMark() {
			return intMark;
		}

		public Float getExtMark() {
			return extMark;
		}

		String sem;
		Float intMark;
		Float extMark;

		public Marks(String sem, Float intMk, Float extMk) {
			this.sem = sem;
			this.intMark = intMk;
			this.extMark = extMk;
		}

	}

//	public void studentRecord(String enrol_no) {
//
//		PreparedStatement ps;
//		List<Marks> rowValues = new ArrayList<Marks>();
//		try {
//			ps = conn.prepareStatement("select * from marks  where enrol_no=? ");
//			ps.setString(1, enrol_no);
//
//			ResultSet studentMarks = ps.executeQuery();
//
//			// STEP 5: Extract data from result set
//
//			while (studentMarks.next()) {
//				/*
//				 * // Retrieve by column name int id = rs.getInt("id"); String
//				 * username = rs.getString("username"); String password =
//				 * rs.getString("password"); String role = rs.getString("role");
//				 * 
//				 * // Display values System.out.print("ID: " + id);
//				 * System.out.print(", Username: " + username);
//				 * System.out.print(", Password: " + password);
//				 */
//
//				rowValues.add(new Marks(studentMarks.getString("sem"),
//						studentMarks.getFloat("intmark"), studentMarks
//						.getFloat("extmark")));
//
//			}
//			// STEP 6: Clean-up environment
//			studentMarks.close();
//			stmt.close();
//			conn.close();
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}// end studentRecord

	public class TableData {
		private int rowCount;
		private Object[][] colData;

		public TableData(int rows, Object[][] data) {
			this.rowCount = rows;
			this.colData = data;

		}

		public int getRowCount() {
			return rowCount;
		}

		public Object[][] getcolData() {
			return colData;
		}
	}

	public TableData studentMarks_by_enrol_no(String enrol_no ) {

		PreparedStatement ps;
		// ArrayList columnNames = new ArrayList();
		// ArrayList data = new ArrayList();
		Object[][] data = null;
                int row = 0;
		//String[] columnNames = { "id", "enrol_no", "sem", "intmark", "extmark", "total" };
		try {
			ps = conn.prepareStatement("select * from results  where enrol_no LIKE ? ");
                        
                        System.out.println("select * from results  where enrol_no LIKE %"+enrol_no+"%");
                        
			ps.setString(1, "%"+enrol_no+"%");

			ResultSet studentMarks = ps.executeQuery();

			ResultSetMetaData md = studentMarks.getMetaData();
			int columns = md.getColumnCount();
			// STEP 5: Extract data from result set

			data = new Object[columns][7];

			
			while (studentMarks.next()) {
				data[row][0] = studentMarks.getString("id");
				data[row][1] = studentMarks.getString("enrol_no");
				data[row][2] = studentMarks.getString("subCode");
				data[row][3] = studentMarks.getString("intmark");
				data[row][4] = studentMarks.getString("extmark");
				data[row][5] = studentMarks.getString("total");
                                data[row][6] = studentMarks.getBoolean("pass");
                                
                                System.out.println("sampling msql data data[row][1] ="+data[row][1]);
				row++;
			}

			// STEP 6: Clean-up environment
			studentMarks.close();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new TableData(row, data);

	}// end studentMarks
        
        public TableData studentInfo_by_name(String searchTerm ) {

		PreparedStatement ps;
		// ArrayList columnNames = new ArrayList();
		// ArrayList data = new ArrayList();
		Object[][] data = null;
                int row = 0;
		//String[] columnNames = { "id", "enrol_no", "sem", "intmark", "extmark", "total" };
		try {
			ps = conn.prepareStatement("select * from students WHERE `name` LIKE ? ");
                        
                        System.out.println("select * from students  where name LIKE %"+searchTerm+"%");
                        
			ps.setString(1, "%"+searchTerm+"%");

			ResultSet studentInfo = ps.executeQuery();

			ResultSetMetaData md = studentInfo.getMetaData();
			int columns = md.getColumnCount();
			// STEP 5: Extract data from result set

			data = new Object[columns][5];

			
			while (studentInfo.next()) {
                            
                            //"#", "Fullname", "Student No", "Batch", "Address", "DOB"
				 //data[row][1] = (studentInfo.getString("id"));
                            data[row][1] = (studentInfo.getString("enrol_no"));
                            data[row][0] = (studentInfo.getString("name"));
                            data[row][4] = (studentInfo.getString("DOB"));
                            data[row][2] = (studentInfo.getString("batch"));
                            data[row][3] = (studentInfo.getString("address"));
                            
                                System.out.println("sampling msql data data[row][1] ="+data[row][1]);
				row++;
			}

			// STEP 6: Clean-up environment
			studentInfo.close();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new TableData(row, data);

	}// end searchStudentByName
        
        
        public TableData studentInfo_by_enrol_no(String searchTerm ) {

		PreparedStatement ps;
		// ArrayList columnNames = new ArrayList();
		// ArrayList data = new ArrayList();
		Object[][] data = null;
                int row = 0;
		//String[] columnNames = { "id", "enrol_no", "sem", "intmark", "extmark", "total" };
		try {
			ps = conn.prepareStatement("select * from students WHERE `enrol_no` LIKE ? ");
                        
                        System.out.println("select * from students  where enrol_no = "+searchTerm);
                        
			ps.setString(1, "%"+searchTerm+"%");

			ResultSet studentInfo = ps.executeQuery();

			ResultSetMetaData md = studentInfo.getMetaData();
			int columns = md.getColumnCount();
			// STEP 5: Extract data from result set

			data = new Object[columns][5];

			
			while (studentInfo.next()) {
                            
                          data[row][1] = (studentInfo.getString("enrol_no"));
                            data[row][0] = (studentInfo.getString("name"));
                            data[row][4] = (studentInfo.getString("DOB"));
                            data[row][2] = (studentInfo.getString("batch"));
                            data[row][3] = (studentInfo.getString("address"));
				
                                System.out.println("sampling msql data data[row][1] ="+data[row][1]);
				row++;
			}

			// STEP 6: Clean-up environment
			studentInfo.close();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new TableData(row, data);

	}// end searchStudentByName
        
        
        public ArrayList<String> getStudentInfo(String enrol_no ) {

		PreparedStatement ps;
		// ArrayList columnNames = new ArrayList();
		// ArrayList data = new ArrayList();
		ArrayList<String> data = null;
               
		try {
			ps = conn.prepareStatement("select * from students  where enrol_no =? ");
                        
                        System.out.println("select * from students  where enrol_no = "+enrol_no);
                        
			ps.setString(1, enrol_no);
                    try (ResultSet studentInfo = ps.executeQuery()) {
                        ResultSetMetaData md = studentInfo.getMetaData();
                        
                        
                        // STEP 5: Extract data from result set
                        
                        data = new ArrayList<>();
                        
                        
                        while (studentInfo.next()) {
                            data.add(studentInfo.getString("id"));
                            data.add(studentInfo.getString("enrol_no"));
                            data.add(studentInfo.getString("name"));
                            data.add(studentInfo.getString("DOB"));
                            data.add(studentInfo.getString("batch"));
                            
                        }
                    }
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;

	}// end studentInfo
        
       public TableData studentMarks_by_subject(String subCode ) {

		PreparedStatement ps;
		// ArrayList columnNames = new ArrayList();
		// ArrayList data = new ArrayList();
		Object[][] data = null;
                int row = 0;
		//String[] columnNames = { "id", "enrol_no", "sem", "intmark", "extmark", "total" };
		try {
			ps = conn.prepareStatement("select * from results  where subCode LIKE ? ");
                        
                        System.out.println("select * from results  where subCode = "+subCode);
                        
			ps.setString(1, "%"+subCode+"%");

			ResultSet subCodeMarks = ps.executeQuery();

			ResultSetMetaData md = subCodeMarks.getMetaData();
			int columns = md.getColumnCount();
			// STEP 5: Extract data from result set

			data = new Object[columns][7];

			
			while (subCodeMarks.next()) { 
				data[row][0] = subCodeMarks.getString("id");
				data[row][1] = subCodeMarks.getString("enrol_no");
				data[row][2] = subCodeMarks.getString("subCode");
				data[row][3] = subCodeMarks.getString("intmark");
				data[row][4] = subCodeMarks.getString("extmark");
				data[row][5] = subCodeMarks.getString("total");
                                data[row][6] = subCodeMarks.getBoolean("pass");
                                
                                System.out.println("sampling msql data data[row][1] ="+data[row][1]);
				row++;
			}

			// STEP 6: Clean-up environment
			subCodeMarks.close();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new TableData(row, data);

	}// end studentMarks_by_subject
       
       public void deleteAllRows(final DefaultTableModel model){
           for (int i = model.getRowCount() -1; i >=0; i--){
               model.removeRow(i);
           }
       }
	
	
	public void close(){
		
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}// nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
		
		}// end try
	}

}// end DBConnect