package kr.co.bomz.cmn.db;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;

/**
 * 	ItemOrderPopupController �� �̿�� ���ҽ��� �����ؾ��ϴ� ��� ����
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public interface OrderDatabaseService {

	/**		SQL ���� ����		*/
	public SqlSession openSession() throws SQLException;
	
	/**		������ ���� ���� ��� ������Ʈ		*/
	public void updateItemOrder(SqlSession session, ItemVO item) throws Exception;
}
