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
	// 필드 5단계
	public Connection connection = null;
	public Statement statement =null;
	public PreparedStatement preparedStatement = null;
	public ResultSet resultSet = null;
	public int result = 0;

	// 메서드
	public void insertBoard(Scanner inputStr, String id, String pw, MemberDTO memberDTO) throws SQLException {
		
		BoardDTO boardDTO = new BoardDTO();
		
		System.out.println(id + "님 게시글을 작성하실수 있습니다.");
		
		System.out.print("제목 : ");
		boardDTO.setBtitle(inputStr.next());
		
		System.out.print("작성자 : ");
		boardDTO.setBwriter(inputStr.next());
		
		Scanner inputLine = new Scanner(System.in);
		System.out.print("내용 : ");
		boardDTO.setBcontent(inputLine.nextLine());
		
		try {
			String sql = "insert into board(bno, btitle, bwriter, bcontent, bdate) values(board_seq.nextval,?,?,?,sysdate)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, boardDTO.getBtitle());
			preparedStatement.setString(2, boardDTO.getBwriter());
			preparedStatement.setString(3, boardDTO.getBcontent());
			
			result = preparedStatement.executeUpdate();
			
			if(result>0) {
				System.out.println(result + "개의 게시물이 등록되었습니다.");
				connection.commit();
			} else {
				System.out.println("게시물 등록에 실패하였습니다.");
				connection.rollback();
			}// if문 종료
		} catch (SQLException e) {
			System.out.println("예외발생 : insertBoard()메서드 및 쿼리문을 확인하세요.");
			e.printStackTrace();
		} finally {
			preparedStatement.close();
		}
	}// insertBoard종료

	public void selectAll() throws SQLException {
		// 전체 게시글 조회
		try {
			String sql = "select * from board order by bno asc";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			
			System.out.println("게시글번호\t 제목\t 작성자\t 작성일\t");
			
			while(resultSet.next()) {
				System.out.print(resultSet.getInt("mno"));
				System.out.println(resultSet.getString("btitle"));
				System.out.println(resultSet.getString("bwriter"));
				System.out.println(resultSet.getDate("bdate"));
			}// 전체 보기 while문 종료
		} catch (SQLException e) {
			System.out.println("예외발생 : selectAll()메서드 및 쿼리문을 확인하세요.");
			e.printStackTrace();
		}finally {
			resultSet.close();
			statement.close();
		}	
	}// selectAll 메서드 종료

}
