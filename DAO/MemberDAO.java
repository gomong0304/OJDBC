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
	// �ʵ� 5�ܰ� ��ü ����
	public MemberDTO memberDTO = new MemberDTO();
	public Connection connection = null;
	public Statement statement =null;
	public PreparedStatement preparedStatement = null;
	public ResultSet resultSet = null;
	public int result = 0;
	
	// �⺻������
	public MemberDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.159:1521:xe", "minipro", "minipro");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver�̸��̳� ojdbc6.jar ������ �߸��Ǿ����ϴ�.");
			e.printStackTrace();
			System.exit(0);
		} catch (SQLException e) {
			System.out.println("URL,ID,PW�� �߸��Ǿ����ϴ�. MemberDAO�� �⺻�����ڸ� Ȯ���ϼ���");
			e.printStackTrace();
			System.exit(0);
		}
	}// �⺻ ������ ����

	
	// �޼���
	public MemberDTO insertMember(MemberDTO session, Scanner inputStr) throws SQLException {
		// �Է¹��� ������ ȸ���� �����غ���.
		MemberDTO memberDTO = new MemberDTO();
		
		System.out.print("ȸ�� �����Ͻ� ���� ������ �Է����ּ��� : ");
		memberDTO.setMname(inputStr.next());
		
		System.out.print("�����Ͻ� id�� �Է����ּ��� : ");
		memberDTO.setId(inputStr.next());
			
		System.out.print("pw�� �Է����ּ��� : ");
		memberDTO.setPw(inputStr.next());
		
		try {
			String sql = "insert into member(mno, mname, id, pw, regidate) values(member_seq.nextval,?,?,?,sysdate)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, memberDTO.getMname());
			preparedStatement.setString(2, memberDTO.getId());
			preparedStatement.setString(3, memberDTO.getPw());
			
			result = preparedStatement.executeUpdate();
			
			if(result>0) {
				System.out.println(result + "���� ȸ���� ���������� ���ԵǾ����ϴ�.");
				connection.commit();
				
				session.setMname(memberDTO.getMname());
				session.setId(memberDTO.getId());
				session.setPw(memberDTO.getPw());
				return session;
			} else {
				System.out.println("ȸ�����Կ� �����Ͽ����ϴ�.");
				connection.rollback();
			}// if�� ����
		} catch (SQLException e) {
			System.out.println("���ܹ߻� : insertMember()�޼��� �� �������� Ȯ�����ּ���");
			e.printStackTrace();
		} finally {
			preparedStatement.close();
		}
		return null;
	}// insertMember�޼��� ����

	public MemberDTO loginMember(MemberDTO session, String id, String pw, MemberDTO memberDTO) throws SQLException {
		// Ű����� �Է¹��� id �� pw�� dao�� ���� db���� ã�Ƽ� �α��� ����
		
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
				System.out.println(id+ "�� ȯ���մϴ�.");
				return session;
			} else {
				System.out.println("�������� �ʴ� ȸ���Դϴ�.");
			}// if�� ����
		} catch (SQLException e) {
			System.out.println("���ܹ߻� : loginMember()�޼��� �� �������� Ȯ���ϼ���");
			e.printStackTrace();
		} finally {
			resultSet.close();
			preparedStatement.close();
		}
		return null;
	}// loginMember �޼��� ����

	public void selectAll(Scanner inputStr) throws SQLException {
		// ��ü ȸ�� ����� ����غ���.
		
		try {
			String sql = "select mno, mname, id, regidate from member order by mno asc";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			
			System.out.println("ȸ����ȣ\t �̸�\t id\t ���Գ�¥\t");
			
			while(resultSet.next()) {
				System.out.print(resultSet.getInt("mno") + "\t");
				System.out.print(resultSet.getString("mname") + "\t");
				System.out.print(resultSet.getString("id") + "\t");
				System.out.println(resultSet.getDate("regidate") + "\t");
			}// ��ü ���� while�� ����
			
		} catch (SQLException e) {
			System.out.println("���ܹ߻� : selectAll()�޼��� �� �������� Ȯ���ϼ���.");
			e.printStackTrace();
		} finally {
			resultSet.close();
			statement.close();
		}
	}// selectAll�޼��� ����

	public void readOne(Scanner inputStr, String id, String pw) throws SQLException {
		// �Է¹��� id pw�� ��ġ�ϸ� ���� ��ȸ�� �Ҽ��ְ�����.
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
					System.out.println(id + "��, ȸ������ Ȯ�εǼ̽��ϴ�. ��ȸ�Ͻ� �����Դϴ�.");
					System.out.println("ȸ�� ��ȣ : " + memberDTO.getMno());
					System.out.println("�̸� : " + memberDTO.getMname());
					System.out.println("id : " + memberDTO.getId());
					System.out.println("������ : " + memberDTO.getRegidate());
				} else {
					System.out.println("id �Ǵ� pw�� ��ġ���� �ʾ� ��ȸ�� �����߽��ϴ�.");
				} // if�� ����

		} catch (SQLException e) {
			System.out.println("���ܹ߻� : readOne()�޼��� �� �������� Ȯ���ϼ���.");
			e.printStackTrace();
		} finally {
			resultSet.close();
			preparedStatement.close();
		}
	}// readOne �޼��� ����


	public MemberDTO modify(MemberDTO session, Scanner inputStr, String id, String pw) throws SQLException {
		// Ű����� �Է¹��� id pw �� ��ġ�ϴ� ȸ���������� ���������ؼ� �ٽ� ����
		
		try {
			System.out.println("���� ������ �Է��ϼ���.");
			System.out.print("�̸� : ");
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
				System.out.println(result + "���� ȸ�������� �����Ǿ����ϴ�.");
				connection.commit();
				
				session.setMname(memberDTO.getMname());
				session.setId(memberDTO.getId());
				session.setPw(memberDTO.getPw());
				return session;
			} else {
				System.out.println("id�� pw�� ��ġ ���� �ʾ� ������ �Ϸ���� �ʾҽ��ϴ�.");
				connection.rollback();
			}
		} catch (SQLException e) {
			System.out.println("���ܹ߻� : modify()�޼��� �� �������� Ȯ���ϼ���");
			e.printStackTrace();
		} finally {
			preparedStatement.close();
		}
		return null;
	}// modify �޼��� ����


	public void deleteOne(Scanner inputStr, MemberDTO session, String id, String pw) throws SQLException {
		// ȸ�� �Ѹ��� ������ �����Ѵ�.
		
		System.out.println("���� ȸ�� Ż���Ͻðڽ��ϱ�? yes or no");
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
					System.out.println(result + "���� ȸ���� ���������� Ż��Ǿ����ϴ�.");
					connection.commit();
				} else {
					System.out.println("id �Ǵ� pw�� ��ġ���� �ʾ� Ż�� �����Ͽ����ϴ�.");
					connection.rollback();
				}
				System.out.println("---------------------------------");
				System.out.println("�����ִ� ȸ�� ���");
				selectAll(inputStr);
				
			} catch (SQLException e) {
				System.out.println("���ܹ߻� : deleteOne()�޼��� �� �������� Ȯ���ϼ���.");
				e.printStackTrace();
			} finally {
				preparedStatement.close();
			}
		} else {
			System.out.println("ȸ�� Ż�� ����մϴ�.");
		}

	}// deleteOne �޼��� ����
	
}
