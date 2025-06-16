package MiniProject.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import MiniProject.DTO.MemberDTO;

public class MemberDAO {
	// 필드 5단계 객체 생성
	public MemberDTO memberDTO = new MemberDTO();
	public Connection connection = null;
	public Statement statement =null;
	public PreparedStatement preparedStatement = null;
	public ResultSet resultSet = null;
	public int result = 0;
	
	// 기본생성자
	public MemberDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.159:1521:xe", "minipro", "minipro");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver이름이나 ojdbc6.jar 파일이 잘못되었습니다.");
			e.printStackTrace();
			System.exit(0);
		} catch (SQLException e) {
			System.out.println("URL,ID,PW가 잘못되었습니다. MemberDAO에 기본생성자를 확인하세요");
			e.printStackTrace();
			System.exit(0);
		}
	}// 기본 생성자 종료

	
	// 메서드
	public MemberDTO insertMember(MemberDTO session, Scanner inputStr) throws SQLException {
		// 입력받은 정보로 회원을 생성해보자.
		MemberDTO memberDTO = new MemberDTO();
		
		System.out.print("회원 가입하실 분의 성함을 입력해주세요 : ");
		memberDTO.setMname(inputStr.next());
		
		System.out.print("생성하실 id를 입력해주세요 : ");
		memberDTO.setId(inputStr.next());
			
		System.out.print("pw를 입력해주세요 : ");
		memberDTO.setPw(inputStr.next());
		
		try {
			String sql = "insert into member(mno, mname, id, pw, regidate) values(member_seq.nextval,?,?,?,sysdate)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, memberDTO.getMname());
			preparedStatement.setString(2, memberDTO.getId());
			preparedStatement.setString(3, memberDTO.getPw());
			
			result = preparedStatement.executeUpdate();
			
			if(result>0) {
				System.out.println(result + "명의 회원이 성공적으로 가입되었습니다.");
				connection.commit();
				
				session.setMname(memberDTO.getMname());
				session.setId(memberDTO.getId());
				session.setPw(memberDTO.getPw());
				return session;
			} else {
				System.out.println("회원가입에 실패하였습니다.");
				connection.rollback();
			}// if문 종료
		} catch (SQLException e) {
			System.out.println("예외발생 : insertMember()메서드 및 쿼리문을 확인해주세요");
			e.printStackTrace();
		} finally {
			preparedStatement.close();
		}
		return null;
	}// insertMember메서드 종료

	public MemberDTO loginMember(MemberDTO session, String id, String pw, MemberDTO memberDTO) throws SQLException {
		// 키보드로 입력받은 id 및 pw를 dao를 통해 db에서 찾아서 로그인 성공
		
		try {
			String sql = "select * from member where id=? and pw=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, pw);
			
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				memberDTO.setMno(resultSet.getInt("mno"));
				memberDTO.setMname(resultSet.getString("mname"));
				memberDTO.setId(resultSet.getString("id"));
				memberDTO.setPw(resultSet.getString("pw"));
				memberDTO.setRegidate(resultSet.getDate("regidate"));
				System.out.println(id+ "님 환영합니다.");
				return session;
			} else {
				System.out.println("존재하지 않는 회원입니다.");
			}// if문 종료
		} catch (SQLException e) {
			System.out.println("예외발생 : loginMember()메서드 및 쿼리문을 확인하세요");
			e.printStackTrace();
		} finally {
			resultSet.close();
			preparedStatement.close();
		}
		return null;
	}// loginMember 메서드 종료

	public void selectAll(Scanner inputStr) throws SQLException {
		// 전체 회원 목록을 출력해보자.
		
		try {
			String sql = "select mno, mname, id, regidate from member order by mno asc";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			
			System.out.println("회원번호\t 이름\t id\t 가입날짜\t");
			
			while(resultSet.next()) {
				System.out.print(resultSet.getInt("mno") + "\t");
				System.out.print(resultSet.getString("mname") + "\t");
				System.out.print(resultSet.getString("id") + "\t");
				System.out.println(resultSet.getDate("regidate") + "\t");
			}// 전체 보기 while문 종료
			
		} catch (SQLException e) {
			System.out.println("예외발생 : selectAll()메서드 및 쿼리문을 확인하세요.");
			e.printStackTrace();
		} finally {
			resultSet.close();
			statement.close();
		}
	}// selectAll메서드 종료

	public void readOne(Scanner inputStr, String id, String pw) throws SQLException {
		// 입력받은 id pw가 일치하면 정보 조회를 할수있게하자.
		try {
			String sql = "select mno, mname, id, regidate from member where id=? and pw=? ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, pw);
			
			resultSet = preparedStatement.executeQuery();
			
				if (resultSet.next()) {
					MemberDTO memberDTO = new MemberDTO();

					memberDTO.setMno(resultSet.getInt("mno"));
					memberDTO.setMname(resultSet.getString("mname"));
					memberDTO.setId(resultSet.getString("id"));
					memberDTO.setRegidate(resultSet.getDate("regidate"));

					System.out.println("---------------------------------------");
					System.out.println(id + "님, 회원으로 확인되셨습니다. 조회하신 정보입니다.");
					System.out.println("회원 번호 : " + memberDTO.getMno());
					System.out.println("이름 : " + memberDTO.getMname());
					System.out.println("id : " + memberDTO.getId());
					System.out.println("가입일 : " + memberDTO.getRegidate());
				} else {
					System.out.println("id 또는 pw가 일치하지 않아 조회에 실패했습니다.");
				} // if문 종료

		} catch (SQLException e) {
			System.out.println("예외발생 : readOne()메서드 및 쿼리문을 확인하세요.");
			e.printStackTrace();
		} finally {
			resultSet.close();
			preparedStatement.close();
		}
	}// readOne 메서드 종료


	public MemberDTO modify(MemberDTO session, Scanner inputStr, String id, String pw) throws SQLException {
		// 키보드로 입력받은 id pw 가 일치하는 회원이있으면 정보수정해서 다시 저장
		
		try {
			System.out.println("수정 사항을 입력하세요.");
			System.out.print("이름 : ");
			memberDTO.setMname(inputStr.next());
			
			System.out.print("id : ");
			memberDTO.setId(inputStr.next());
			
			System.out.print("pw : ");
			memberDTO.setPw(inputStr.next());
			
			String sql = "update member set mname=?, id=?, pw=?, regidate=sysdate where id=? and pw=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, memberDTO.getMname());
			preparedStatement.setString(2, memberDTO.getId());
			preparedStatement.setString(3, memberDTO.getPw());
			preparedStatement.setString(4, id);
			preparedStatement.setString(5, pw);
			
			result = preparedStatement.executeUpdate();
			
			if(result>0) {
				System.out.println(result + "개의 회원정보가 수정되었습니다.");
				connection.commit();
				
				session.setMname(memberDTO.getMname());
				session.setId(memberDTO.getId());
				session.setPw(memberDTO.getPw());
				return session;
			} else {
				System.out.println("id나 pw가 일치 하지 않아 수정이 완료되지 않았습니다.");
				connection.rollback();
			}
		} catch (SQLException e) {
			System.out.println("예외발생 : modify()메서드 및 쿼리문을 확인하세요");
			e.printStackTrace();
		} finally {
			preparedStatement.close();
		}
		return null;
	}// modify 메서드 종료


	public void deleteOne(Scanner inputStr, MemberDTO session, String id, String pw) throws SQLException {
		// 회원 한명의 정보를 삭제한다.
		
		System.out.println("정말 회원 탈퇴하시겠습니까? yes or no");
		System.out.print(">>> ");
		String answer = inputStr.next();
		
		if(answer.equalsIgnoreCase("yes")) {
			try {
				String sql = "delete from member where id=? and pw=?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, id);
				preparedStatement.setString(2, pw);
				result = preparedStatement.executeUpdate();
				if(result>0) {
					System.out.println(result + "명의 회원이 성공적으로 탈퇴되었습니다.");
					connection.commit();
				} else {
					System.out.println("id 또는 pw가 일치하지 않아 탈퇴에 실패하였습니다.");
					connection.rollback();
				}
				System.out.println("---------------------------------");
				System.out.println("남아있는 회원 목록");
				selectAll(inputStr);
				
			} catch (SQLException e) {
				System.out.println("예외발생 : deleteOne()메서드 및 쿼리문을 확인하세요.");
				e.printStackTrace();
			} finally {
				preparedStatement.close();
			}
		} else {
			System.out.println("회원 탈퇴를 취소합니다.");
		}

	}// deleteOne 메서드 종료
	
}
