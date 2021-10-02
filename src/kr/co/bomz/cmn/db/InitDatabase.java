package kr.co.bomz.cmn.db;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.bomz.cmn.Commons;

/**
 * 	데이터베이스 초기화 작업 수행
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class InitDatabase {

	public InitDatabase(){}
		
	/**
	 * 	첫 실행을 위한 테이블 초기화 쿼리 실행
	 * @param sqlFile		초기화 쿼리 파일 URL
	 * @param dbId		디비 접속 아이디
	 * @param dbPw		디비 접속 암호
	 */
	public void initTable(URL sqlFile, String dbId, String dbPw){
		String[] querys = this.parsingQueryFile(sqlFile);
		
		Connection conn = null;
		Statement st = null;

		try{
			conn = this.databaseConnect(dbId, dbPw);
			if( conn == null )		return;
			
			st = conn.createStatement();
			
			for(String query : querys){
				try{		st.execute(query);		}catch(Exception e){}
			}
		}catch(Exception e){
			Logger logger = LoggerFactory.getLogger(Commons.CMN_LOGGER_ID);
			if( logger.isDebugEnabled() )		logger.debug("데이터베이스 테이블 초기화 중 오류", e);
		}finally{
			this.databaseDisconnect(conn, st);
		}
	}
	
	
	// 데이터베이스 접속 종료
	private void databaseDisconnect(Connection conn, Statement st){
		if( st != null )			try{		st.close();		}catch(Exception e){}
		if( conn != null )		try{		conn.close();		}catch(Exception e){}
	}
	
	// 데이터베이스 접속
	private Connection databaseConnect(String dbId, String dbPw){
		
		try{
			Class.forName( "org.mariadb.jdbc.Driver" );
			return DriverManager.getConnection(
					"jdbc:mariadb://127.0.0.1:3306/",
					dbId, dbPw
				);
			
		}catch(Exception e){
			final Logger logger = LoggerFactory.getLogger(Commons.CMN_LOGGER_ID);
			logger.error("데이터베이스 접속 실패", e);
			return null;
		}
	}
	
	// 쿼리 파일 분석
	private String[] parsingQueryFile(URL sqlFile){
		StringBuilder buffer = new StringBuilder();
		if( !this.parsingQueryFileRead(sqlFile, buffer) )		return null;	
				
		return buffer.toString().split("#");
	}

	private boolean parsingQueryFileRead(URL sqlFile, StringBuilder buffer){
		FileInputStream fis = null;
		FileChannel fc = null;
		
		ByteBuffer bb = ByteBuffer.allocateDirect(100);
		byte[] data = new byte[100];
		int readSize;
		try{
			fis = new FileInputStream( new File(sqlFile.toURI()) );
			fc = fis.getChannel();
			
			while( true ){
				bb.clear();
				readSize = fc.read(bb);
				if( readSize <= 0 )			break;
				
				bb.flip();
				bb.get(data, 0, readSize);
				buffer.append( new String(data, 0, readSize) );
			}
			
		}catch(Exception e){
			final Logger logger = LoggerFactory.getLogger(Commons.CMN_LOGGER_ID);
			logger.error("쿼리 파일 읽기 오류", e);
			return false;
		}finally{
			if( fc != null )		try{		fc.close();		}catch(Exception e){}
			if( fis != null )		try{		fis.close();		}catch(Exception e){}
		}
		
		return true;
	}
	
}
