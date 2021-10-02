package kr.co.bomz.cmn.db;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;

/**
 * 	ItemOrderPopupController 를 이용시 리소스로 전달해야하는 디비 서비스
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public interface OrderDatabaseService {

	/**		SQL 세션 열기		*/
	public SqlSession openSession() throws SQLException;
	
	/**		아이템 정렬 순서 디비 업데이트		*/
	public void updateItemOrder(SqlSession session, ItemVO item) throws Exception;
}
