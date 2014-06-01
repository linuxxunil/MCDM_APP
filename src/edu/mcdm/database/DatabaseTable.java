package edu.mcdm.database;

public class DatabaseTable {
	static public class CodeFile {
		// table name define
		public static String name = "CodeFile";
		// columns definie
		public static final String colHospitalNo = "HospitalNo";
		public static final String colItemType = "ItemType";
		public static final String colItemCode = "ItemCode";
		public static final String colItemDescription = "ItemDescription";
		public static final String colRemark = "Remark";
		public static final String colCheckFlag = "CheckFlag";
		public static final String colUpdateID = "UpdateID";
		public static final String colUpdateDate = "UpdateDate";
		
		// sql syntax : create
		public static String create() {	
			return "CREATE TABLE IF NOT EXISTS " + name + "(" 
				+ colHospitalNo 		+ " nvarchar(10)	NOT NULL,"
				+ colItemType 			+ " nvarchar(2)		NOT NULL,"
				+ colItemCode 			+ " nvarchar(8)		NOT NULL,"
				+ colItemDescription 	+ " nvarchar(50)	NULL,"
				+ colRemark 			+ " nvarchar(50)	NULL,"
				+ colCheckFlag 			+ " char(1) 		NULL,"
				+ colUpdateID			+ " nvarchar(256) 	NULL,"
				+ colUpdateDate 		+ " datetime 		NULL"
				+	")";
		}
	}
	
	
	static public class Department {
		// table name define
		public static String name = "Department";

		// columns definie
		public static final String colHospitalNo = "HospitalNo";
		public static final String colDepName = "DepName";
		public static final String colUpdateID = "UpdateID";
		public static final String colUpdateDate = "UpdateDate";
		public static final String colDesc = "Desc";
		
		// sql syntax : create
		public static String create() {	
			return "CREATE TABLE IF NOT EXISTS " + name + "(" 
				+ colHospitalNo 		+ " nvarchar(10)	NOT NULL,"
				+ colDepName 			+ " nvarchar(50)	NULL,"
				+ colUpdateID 			+ " nvarchar(256)	NULL,"
				+ colUpdateDate 		+ " datetime 		NULL,"
				+ colDesc 				+ " nvarchar(300)	NULL"
				+	")";
		}
	}
	
	
	static public class Doctor {
		// table name define
		public static String name = "Doctor";
		// columns definie
		public static final String colHospitalNo = "HospitalNo";
		public static final String colDepName = "DepName";
		public static final String colDorNo = "DorNo";
		public static final String colDorName = "DorName";
		public static final String colTelephone = "Telephone";
		public static final String colJobTitle 	= "JobTitle";
		public static final String colHistory = "History";
		public static final String colSubject = "Subject";
		public static final String colValid = "Valid";
		public static final String colUpdateID = "UpdateID";
		public static final String colUpdateDate = "UpdateDate";
		public static final String colDesc = "Desc";
		public static final String colPicPath = "PicPath";
		
		public static String create() {	
			return "CREATE TABLE IF NOT EXISTS " + name + "(" 
					+ colHospitalNo 	+ " nvarchar(10) 	NOT NULL,"
					+ colDepName 		+ " nvarchar(50)	NULL,"
					+ colDorNo 			+ " nvarchar(5)		NULL,"
					+ colDorName 		+ " nvarchar(50)	NULL,"
					+ colTelephone 		+ " nvarchar(50)	NULL,"
					+ colJobTitle 		+ " nvarchar(50)	NULL,"
					+ colHistory 		+ " nvarchar(1000) 	NULL,"
					+ colSubject 		+ " nvarchar(1000) 	NULL,"
					+ colValid 			+ " char(1) 		NULL,"
					+ colUpdateID 		+ " nvarchar(256) 	NULL,"
					+ colUpdateDate 	+ " datetime 		NULL,"
					+ colDesc 			+ " nvarchar(300) 	NULL,"
					+ colPicPath 		+ " nvarchar(300) 	NULL"
					+	")";
			}
	}
	
	
	static public class DoctorSchedule {
		// table name define
		public static String name = "DorSchedule";
		// columns definie
		public static final String colHospitalNo= "HospitalNo";
		public static final String colDepName = "DepName";
		public static final String colDorNo = "DorNo";
		public static final String colSchYear = "SchYear";
		public static final String colSchMonth = "SchMonth";
		public static final String colSchedule 	= "Schedule";
		public static final String colUpdateID = "UpdateID";
		public static final String colUpdateDate = "UpdateDate";
		public static final String colDesc = "Desc";
		
		public static String create() {	
			return "CREATE TABLE IF NOT EXISTS " + name + "(" 
					+ colHospitalNo 	+ " nvarchar(10) 	NOT NULL,"
					+ colDepName 		+ " nvarchar(50)	NOT NULL,"
					+ colDorNo 			+ " nvarchar(5)		NOT NULL,"
					+ colSchYear 		+ " char(4)			NOT NULL,"
					+ colSchMonth 		+ " char(2)			NOT NULL,"
					+ colSchedule		+ " char(31)		NOT NULL,"
					+ colUpdateID 		+ " nvarchar(256) 	NULL,"
					+ colUpdateDate 	+ " datetime 		NULL,"
					+ colDesc 			+ " nvarchar(300) 	NULL"
					+	")";
			}
	}
	
	
	static public class Hospital {
		// table name define
		public static String name = "Hospital";
		// columns definie
		public static final String colHospitalNo= "HospitalNo";
		public static final String colHospitalName = "HospitalName";
		public static final String colAreaID = "AreaID";
		public static final String colHospitalPhone = "HospitalPhone";
		public static final String colHospitalAddress = "HospitalAddress";
		public static final String colContactName 	= "ContactName";
		public static final String colContactPhone = "ContactPhone";
		public static final String colUpdateID = "UpdateID";
		public static final String colUpdateDate = "UpdateDate";
		public static final String colDesc = "Desc";
		public static final String colDepName = "DepName";
		public static final String colOPD_ST1 = "OPD_ST1";
		public static final String colOPD_ET1 = "OPD_ET1";
		public static final String colOPD_ST2 = "OPD_ST2";
		public static final String colOPD_ET2 = "OPD_ET2";
		public static final String colOPD_ST3 = "OPD_ST3";
		public static final String colOPD_ET3 = "OPD_ET3";
		public static final String colHospitalschedule = "Hospitalschedule";
		public static final String colhospitalstate = "hospitalstate";
		public static final String colPicPath = "PicPath";

		
		public static String create() {	
			return "CREATE TABLE IF NOT EXISTS " + name + "(" 
				+  colHospitalNo		+ " nvarchar(10)	NOT NULL,"
				+  colHospitalName 		+ " nvarchar(150) 	NOT NULL,"
				+  colAreaID 			+ " nvarchar(100)	NULL,"
				+  colHospitalPhone		+ " nvarchar(50)	NULL,"
				+  colHospitalAddress 	+ " nvarchar(300)	NULL,"
				+  colContactName 		+ " nvarchar(50)	NULL,"
				+  colContactPhone 		+ " nvarchar(50)	NULL,"
				+  colUpdateID 			+ " nvarchar(256)	NULL,"
				+  colUpdateDate 		+ " datetime 		NULL,"
				+  colDesc 				+ " nvarchar(300) 	NULL,"
				+  colDepName 			+ " nvarchar(300)	NULL,"
				+  colOPD_ST1 			+ " nvarchar(300) 	NULL,"
				+  colOPD_ET1 			+ " nvarchar(300) 	NULL,"
				+  colOPD_ST2 			+ " nvarchar(300) 	NULL,"
				+  colOPD_ET2 			+ " nvarchar(300) 	NULL,"
				+  colOPD_ST3 			+ " nvarchar(300) 	NULL,"
				+  colOPD_ET3 			+ " nvarchar(300) 	NULL,"
				+  colHospitalschedule 	+ " nvarchar(300) 	NULL,"
				+  colhospitalstate 	+ " char(2) 		NULL,"
				+  colPicPath 			+ " nvarchar(300) 	NULL,"
				+ "PRIMARY KEY("		+ colHospitalNo 	+ "),"
				+ "FOREIGN KEY("+CodeFile.colHospitalNo		+ ") REFERENCES "+CodeFile.name+","
				+ "FOREIGN KEY("+Department.colHospitalNo	+ ") REFERENCES "+Department.name+","
				+ "FOREIGN KEY("+Doctor.colHospitalNo 		+ ") REFERENCES "+Doctor.name+","
				+ "FOREIGN KEY("+DoctorSchedule.colHospitalNo	+ ") REFERENCES "+DoctorSchedule.name+","
				+ "FOREIGN KEY("+User.colHospitalNo			+ ") REFERENCES "+User.name+""
				//+ "FOREIGN KEY("+OpdRegister.colHospitalNo+ ") REFERENCES "+OpdRegister.name+","
				//+ "FOREIGN KEY("+Patient.colHospitalNo+ ") REFERENCES "+Patient.name+","
				+  ")";
		}
		
	}
	

	static public class User {
		// table name define
		public static final String name = "users";
		
		public static final String colUserid = "userid";
		public static final String colUsername = "username";
		public static final String colUserpasswd = "userpasswd";
		public static final String colUserinvalid = "userinvalid";
		public static final String colUservalid = "uservalid";
		public static final String colUseremail = "useremail";
		public static final String colUserpwdans = "userpwdans";
		public static final String colUserpwdquestion = "userpwdquestion";
		public static final String colUserbirthday = "userbirthday";
		public static final String colCompetence = "competence";
		public static final String colUserstate = "userstate";
		public static final String colPicPath = "PicPath";
		public static final String colHospitalNo = "HospitalNo";
		
		
		public static String create() {	
			return "CREATE TABLE IF NOT EXISTS " + name + "(" 
				+ colUserid 		+ " nvarchar(256)	NOT NULL,"
				+ colUsername		+ " nvarchar(50) 	NOT NULL,"
				+ colUserpasswd		+ " nvarchar(128) 	NOT NULL,"
				+ colUserinvalid	+ " datetime 		NULL,"
				+ colUservalid		+ " date 			NULL,"
				+ colUseremail		+ " nvarchar(256) 	NULL,"
				+ colUserpwdans		+ " nvarchar(256) 	NULL,"
				+ colUserpwdquestion+ " nvarchar(256) 	NULL,"
				+ colUserbirthday	+ " date 			NULL,"
				+ colCompetence		+ " char(2) 		NULL,"
				+ colUserstate		+ " char(2) 		NULL,"
				+ colPicPath		+ " nvarchar(300) 	NULL,"
				+ colHospitalNo		+ " nvarchar(10) 	NULL,"
				+ "PRIMARY KEY("	+ 	colUserid 		+ ")"
				+	")";
		}
	}
}