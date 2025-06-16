package MiniProject.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import MiniProject.DTO.BoardDTO;
import MiniProject.DTO.MemberDTO;

public class BoardDAO {
	// �ʵ� 5�ܰ�
	public Connection connection = null;
	public Statement statement =null;
	public PreparedStatement preparedStatement = null;
	public ResultSet resultSet = null;
	public int result = 0;

	// �޼���
	public void insertBoard(Scanner inputStr, String id, String pw, MemberDTO memberDTO) throws SQLException {
		
		BoardDTO boardDTO = new BoardDTO();
		
		System.out.println(id + "�� �Խñ��� �ۼ��ϽǼ� �ֽ��ϴ�.");
		
		System.out.print("���� : ");
		boardDTO.setBtitle(inputStr.next());
		
		System.out.print("�ۼ��� : ");
		boardDTO.setBwriter(inputStr.next());
		
		Scanner inputLine = new Scanner(System.in);
		System.out.print("���� : ");
		boardDTO.setBcontent(inputLine.nextLine());
		
		try {
			String sql = "insert into board(bno, btitle, bwriter, bcontent, bdate) values(board_seq.nextval,?,?,?,sysdate)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, boardDTO.getBtitle());
			preparedStatement.setString(2, boardDTO.getBwriter());
			preparedStatement.setString(3, boardDTO.getBcontent());
			
			result = preparedStatement.executeUpdate();
			
			if(result>0) {
				System.out.println(result + "���� �Խù��� ��ϵǾ����ϴ�.");
				connection.commit();
			} else {
				System.out.println("�Խù� ��Ͽ� �����Ͽ����ϴ�.");
				connection.rollback();
			}// if�� ����
		} catch (SQLException e) {
			System.out.println("���ܹ߻� : insertBoard()�޼��� �� �������� Ȯ���ϼ���.");
			e.printStackTrace();
		} finally {
			preparedStatement.close();
		}
	}// insertBoard����

	public void selectAll() throws SQLException {
		// ��ü �Խñ� ��ȸ
		try {
			String sql = "select * from board order by bno asc";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			
			System.out.println("�Խñ۹�ȣ\t ����\t �ۼ���\t �ۼ���\t");
			
			while(resultSet.next()) {
				System.out.print(resultSet.getInt("mno"));
				System.out.println(resultSet.getString("btitle"));
				System.out.println(resultSet.getString("bwriter"));
				System.out.println(resultSet.getDate("bdate"));
			}// ��ü ���� while�� ����
		} catch (SQLException e) {
			System.out.println("���ܹ߻� : selectAll()�޼��� �� �������� Ȯ���ϼ���.");
			e.printStackTrace();
		}finally {
			resultSet.close();
			statement.close();
		}	
	}// selectAll �޼��� ����

}
