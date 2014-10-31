/**
 * ���ݿ�
 * 
 * @author ����
 * 
 */
package com.soloman.DB;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Database {

	private int dbversion = 1;
	private String db_name = "inurse.db";
	private String table_patient = "binren";// ���˱�
	private String table_value = "celiangjieguo";// ���������
	private String table_setting = "setting";// ���ñ�
	private Context mCtx = null;
	private DatabaseHelper dbHelper;
	private SQLiteDatabase SQLdb;

	public class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
			System.out
					.println("DB databasehelper(context,name,factory,version)");
		}

		public DatabaseHelper(Context context) {
			super(context, db_name, null, dbversion);
			// TODO Auto-generated constructor stub
			System.out.println("DB databasehelper(context)");
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			System.out.println("DB onCreate");
			// ֻ�ڵ�һ�δ�����ʱ�����
			db.execSQL("create table IF NOT EXISTS " + table_patient + 
					"(id varchar(10) ," + //����id
					"firstname varchar(20), " + 
					"lastname varchar(20), " + 
					"tel varchar(20), " + 
					"mail varchar(50), " +					
					"note varchar(100) )");	//��ע		
			
			SQLdb = db;
			System.out.println("DB onCreate --- table_patient");
			
			db.execSQL("create table IF NOT EXISTS " + table_value + 
					"(id varchar(10) ," + //����id
					"devicetype varchar(10), " + // �豸���ͣ�1�¶ȼ�
					"mode varchar(10), " + // ģʽsurface body room
					"unit varchar(10), " + // ��λ��,�H
					"value varchar(10), " + // ����ֵ
					"date varchar(50), " + // ��������
					"time varchar(50), " + //����ʱ��
					"note varchar(100) )"); //ÿ����¼��note��ҽ��
			SQLdb = db;
			System.out.println("DB onCreate --- table_value");			
			
			db.execSQL("create table IF NOT EXISTS " + table_setting + 
					"(id varchar(1), " + //��ţ���Զֻ��һ����¼"1"					
					"autodel int ," + //�Զ�ɾ��0 1
					"whendel varchar(10), " + // ��ʱ�Զ�ɾ��dailly,weekly,monthly,aftersend,off
					
					"automail int, " + // �Զ�mail 0 1
					"mail1 varchar(50), " + // 
					"mail2 varchar(50), " + // 
					"whenmail varchar(10), " + // ��ʱ�Զ����ʼ�dailly,weekly,monthly
					"mailtime1 varchar(2), " + // �Զ����ʼ�ʱ��Сʱ 00-23
					"mailtime2 varchar(2), " + // �Զ����ʼ�ʱ����� 00-59
					
					"autoupload int, " + // �Զ�upload 0 1
					"serverurl varchar(100), " + // �ϴ�����
					
					"autosave int, " + // �Զ�save 0 1
					"path varchar(50), " + // ����·��
					"ext varchar(5), " + // ��׺ .txt .xls
					"separate varchar(1), " + // �ָ���,; ?|$#* ��8��
					
					"userid int, " + // ��ʾ�ֶ� userid 0 1
					"fname int, " + // ��ʾ�ֶ� firstname 0 1
					"lname int, " + // ��ʾ�ֶ� lastname 0 1
					"devicetype int, " + // ��ʾ�ֶ�devicetype 0 1
					"deviceid int, " + // ��ʾ�ֶ�deviceid 0 1
					"date int, " + // ��ʾ�ֶ�date 0 1
					"value int, " + // ��ʾ�ֶ�value 0 1
					"mode int, " + // ��ʾ�ֶ�mode 0 1
					"unit int, " + // ��ʾ�ֶ�unit 0 1
					"note int )");//��ʾ�ֶ�note 0 1
			SQLdb = db;
			System.out.println("DB onCreate --- table_setting");			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

		}
	}

	/** ���캯�� */
	public Database(Context ctx) {
		this.mCtx = ctx;
		System.out.println("DB���캯��");
	}

	public Database open() throws SQLException {
		System.out.println("DB Open");
		dbHelper = new DatabaseHelper(mCtx);
		// ֻ�е���getReadableDatabase����getWriteableDatabase�������Żᴴ�����ݿ����
		SQLdb = dbHelper.getWritableDatabase();
		return this;
	}
	
	public Boolean isOpen(){
		return SQLdb.isOpen();
	}

	public void close() {
		dbHelper.close();
	}

	public long add_patient(String id, String firstname, String lastname, String tel, String mail, String note ) {
		ContentValues cv = new ContentValues();

		cv.put("id", id);
		cv.put("firstname", firstname);
		cv.put("lastname", lastname);
		cv.put("tel", tel);
		cv.put("mail", mail);
		cv.put("note", note);

		System.out.println("DB.add_patient " + table_patient);
		return SQLdb.insert(table_patient, null, cv);
	}	

	
	public Cursor get_patient(){
		Cursor cursor = null;
		try {
			cursor = SQLdb.query(table_patient, // table��
					new String[] { "id", "firstname", "lastname", "tel", "mail","note"}, // �ֶ�
					null, // ����
					null, 
					null, //group by
					null, //having
					"id desc");//order by
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
		return cursor;
	}
	
	public Cursor get_patient(String id){
		Cursor cursor = null;
		try {
			cursor = SQLdb.query(table_patient, // table��
					new String[] { "id", "firstname", "lastname", "tel", "mail","note"}, // �ֶ�
					"id = '" + id + "'", // ����
					null, 
					null, //group by
					null, //having
					"id desc");//order by
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
		return cursor;
	}
	
	
	// �޸���Ϣ
	public int Update_patient(String id, String firstname, String lastname, String tel, String mail, String note ) {
		ContentValues cv = new ContentValues();
		cv.put("firstname", firstname);
		cv.put("lastname", lastname);
		cv.put("tel", tel);
		cv.put("mail", mail);
		cv.put("note", note);
		String[] args = { String.valueOf(id) };
		return SQLdb.update(table_patient, cv, "id=?", args);
	}
	
	public long del_patient(String id) {
		return SQLdb.delete(table_patient, "id = '" + id + "'", null);
	}

	//-------------------------------------------------------------------------------------
	
	
	public long add_Record(String id, String devicetype, String mode, String unit, String value, String date, String time ) {
		ContentValues cv = new ContentValues();

		cv.put("id", id);
		cv.put("devicetype", devicetype);
		cv.put("mode", mode);
		cv.put("unit", unit);
		cv.put("value", value);
		cv.put("date", date);
		cv.put("time", time);

		System.out.println("DB.add_patient " + table_patient);
		return SQLdb.insert(table_value, null, cv);
	}
	
	public Cursor getRecord(String devicetype ) {
		Cursor cursor = null;
		try {
			cursor = SQLdb.query(table_value, // table��
					new String[] { "id", "mode", "unit", "value", "date","time", "note" }, // �ֶ�
					"devicetype = '" + devicetype + "'", // ����
					null, null, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
		return cursor;
	}	
	
	public Cursor getRecord(String id, String devicetype ) {
		Cursor cursor = null;
		try {
			cursor = SQLdb.query(table_value, // table��
					new String[] { "id", "mode", "unit", "value", "date","time", "note" }, // �ֶ�
					"id = '" + id + "' and " + "devicetype = '" + devicetype + "'", // ����
					null, null, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
		return cursor;
	}		
	
	public Cursor getRecord(String id, String devicetype, String mode ) {
		Cursor cursor = null;
		try {
			cursor = SQLdb.query(table_value, // table��
					new String[] { "id", "mode", "unit", "value", "date","time", "note" }, // �ֶ�
					"id = '" + id + "' and " + "devicetype = '" + devicetype + "' and " + "mode = '" + mode + "'", // ����
					null, null, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
		return cursor;
	}
	
	// �޸���Ϣ
		public int updateRecord (String id, String date, String note ) {
			ContentValues cv = new ContentValues();
			cv.put("note", note);
			String[] args = { id, date };
			return SQLdb.update(table_value, cv, "id=? and date=?", args);
		}
	
	public long delRecord(String id, String date) {
		return SQLdb.delete(table_value, "id = '" + id + "' and date = '" + date + "'",
				null);
	}
	
	//--------------------------------------------------------------------------------------------
	public long add_setting(int autodel, String whendel, 
			int automail, String mail1, String mail2, String whenmail, String mailtime1, String mailtime2,
			int autoupload, String serverurl, 
			int autosave, String path, String ext, String separate,
			int userid, int fname, int lname, int devicetype, int deviceid, int date, int value, int mode, int unit, int note) {
		ContentValues cv = new ContentValues();

		cv.put("id", "1");
		
		cv.put("autodel", autodel);
		cv.put("whendel", whendel);
		
		cv.put("automail", automail);
		cv.put("mail1", mail1);
		cv.put("mail2", mail2);
		cv.put("whenmail", whenmail);
		cv.put("mailtime1", mailtime1);
		cv.put("mailtime2", mailtime2);
		
		cv.put("autoupload", autoupload);
		cv.put("serverurl", serverurl);
		
		cv.put("autosave", autosave);
		cv.put("path", path);
		cv.put("ext", ext);
		cv.put("separate", separate);
		
		cv.put("userid", userid);
		cv.put("fname", fname);
		cv.put("lname", lname);
		cv.put("devicetype", devicetype);
		cv.put("deviceid", deviceid);
		cv.put("date", date);
		cv.put("value", value);
		cv.put("mode", mode);
		cv.put("unit", unit);
		cv.put("note", note);		

		System.out.println("DB.add_setting " + table_setting);
		return SQLdb.insert(table_setting, null, cv);
	}
	
	public Cursor getSetting ( ) {
		Cursor cursor = null;
		try {
			cursor = SQLdb.query(table_setting, // table��
					new String[] { "autodel", "whendel", 
						"automail", "mail1", "mail2","whenmail","mailtime1","mailtime2",
						"autoupload", "serverurl", 
						"autosave","path","ext", "separate",
						"userid","fname","lname","devicetype","deviceid","date","value","mode","unit","note"}, // �ֶ�
					null, // ����
					null, null, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
		return cursor;
	}	
	
	public int updateSetting(int autodel, String whendel, 
			int automail, String mail1, String mail2, String whenmail, String mailtime1, String mailtime2,
			int autoupload, String serverurl, 
			int autosave, String path, String ext, String separate,
			int userid, int fname, int lname, int devicetype, int deviceid, int date, int value, int mode, int unit, int note ) {
		ContentValues cv = new ContentValues();
		
		cv.put("autodel", autodel);
		cv.put("whendel", whendel);
		
		cv.put("automail", automail);
		cv.put("mail1", mail1);
		cv.put("mail2", mail2);
		cv.put("whenmail", whenmail);
		cv.put("mailtime1", mailtime1);
		cv.put("mailtime2", mailtime2);
		
		cv.put("autoupload", autoupload);
		cv.put("serverurl", serverurl);
		
		cv.put("autosave", autosave);
		cv.put("path", path);
		cv.put("ext", ext);
		cv.put("separate", separate);
		
		cv.put("userid", userid);
		cv.put("fname", fname);
		cv.put("lname", lname);
		cv.put("devicetype", devicetype);
		cv.put("deviceid", deviceid);
		cv.put("date", date);
		cv.put("value", value);
		cv.put("mode", mode);
		cv.put("unit", unit);
		cv.put("note", note);
		
		String[] args = { "1" };
		return SQLdb.update(table_setting, cv, "id=?", args);
	}
	
	//-------------------------------------------------------------------------------------
	public void clearThis(String tableid) {
		SQLdb.delete(tableid, null, null);
	}
	
}
