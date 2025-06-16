package MiniProject.Service;

import java.sql.SQLException;
import java.util.Scanner;

import MiniProject.DAO.MemberDAO;
import MiniProject.DTO.MemberDTO;

public class MemberService {
	// member�� DTO�� DAO�� Ȱ���ؼ� CRUD�� ó���Ѵ�.
	// �ʵ�
	public MemberDAO memberDAO = new MemberDAO();
	public MemberDTO memberDTO = new MemberDTO();
	// �⺻������
	
	// �޼���
	public void menu(Scanner inputStr, MemberDTO session) throws SQLException {
		boolean menuRun = true;
		while (menuRun) {
			System.out.println();
			System.out.println("=============1. ȸ������ �� �α���=============");
			System.out.println("1.ȸ������ | 2.�α��� | 3.��ü ��ȸ | 4.���� ��ȸ | 5.���� ���� | 6. Ż�� | 7.������");
			System.out.print(">>> ");
			String memSelect = inputStr.next();
			switch (memSelect) {
			case "1":
				System.out.println("------ȸ������ ���񽺷� �̵��մϴ�.------");
				insertMember(session, inputStr);
				break;
			case "2":
				System.out.println("------�α��� ���񽺷� �̵��մϴ�.------");
				loginMember(session ,inputStr);
				break;
			case "3":
				System.out.println("------��ü ��ȸ ���񽺷� �̵��մϴ�.------");
				selectAll(inputStr);
				break;
			case "4":
				System.out.println("------���� ��ȸ ���񽺷� �̵��մϴ�.------");
				readOne(inputStr);
				break;
			case "5":
				System.out.println("------���� ���� ���񽺷� �̵��մϴ�.------");
				modify(session, inputStr);
				break;
			case "6":
				System.out.println("------Ż�� ���񽺷� �̵��մϴ�.------");
				deleteOne(session, inputStr);
				break;
			case "7":
				System.out.println("ȸ������ �� �α��� �޴��� �����մϴ�. \n�����޴��� ���ư��ϴ�.");
				memberDAO.connection.close();
				menuRun = false;
				break;
			default :
				System.out.println("���� �޴��Դϴ�. �ٽ� �������ּ���(1~7)");
				break;
			}// ȸ������ �� �α��� switch�� ����
		}// ȸ������ �� �α��� while�� ����
	}// member �޴� ���� ����

	private void deleteOne(MemberDTO session, Scanner inputStr) throws SQLException {
		// ȸ���� ���� �غ���.
		System.out.println();
		System.out.println("Ż�� ������ ���ؼ��� �α����� �ʿ��մϴ�.");
		System.out.print("id�� �Է����ּ��� : ");
		String id = inputStr.next();
		
		System.out.print("pw�� �Է����ּ��� : ");
		String pw = inputStr.next();
		
		memberDAO.deleteOne(inputStr, session, id, pw);
		System.out.println("---------------------------------");
	}// deleteOne�޼��� ����

	private void modify(MemberDTO session, Scanner inputStr) throws SQLException {
		// �������� �޼���
		System.out.println();
		System.out.println("������ �����ϱ� ���ؼ��� �α����� �ʿ��մϴ�.");
		System.out.print("id�� �Է����ּ��� : ");
		String id = inputStr.next();
		
		System.out.print("pw�� �Է����ּ��� : ");
		String pw = inputStr.next();
		
		memberDAO.modify(session, inputStr, id, pw);
		System.out.println("---------------------------------");
	}// modify �޼��� ����

	private void readOne(Scanner inputStr) throws SQLException {
		// ȸ�� �Ѹ��� ������ ��ȸ�غ���
		System.out.println();
		System.out.println("���� ��ȸ�� �ϱ� ���ؼ��� �α����� �ʿ��մϴ�.");
		System.out.print("id�� �Է����ּ��� : ");
		String id = inputStr.next();
		
		System.out.print("pw�� �Է����ּ��� : ");
		String pw = inputStr.next();
		
		memberDAO.readOne(inputStr, id, pw);
		System.out.println("---------------------------------");
	}// readOne�޼��� ����

	private void selectAll(Scanner inputStr) throws SQLException {
		// ��ü ȸ���� ��ȸ�غ���
		memberDAO.selectAll(inputStr);
		System.out.println("---------------------------------");
	}// selectAll �޼��� ����

	
	private void loginMember(MemberDTO session, Scanner inputStr) throws SQLException {
		// ȸ�������� ����� �α��ο� �����Ҽ��ֵ��� ������
		
		System.out.print("id�� �Է����ּ��� : ");
		String id = inputStr.next();
		
		System.out.print("pw�� �Է����ּ��� : ");
		String pw = inputStr.next();
		
		memberDAO.loginMember(session, id, pw, memberDTO);
		System.out.println("---------------------------------");
	}// loginMember �޼��� ����

	
	private void insertMember(MemberDTO session, Scanner inputStr) throws SQLException {
		// Ű����� id�� pw�� �Է¹޾Ƽ� ȸ�������� ���Ѻ���.
		memberDAO.insertMember(session, inputStr);
		System.out.println("---------------------------------");
	}// insertMember �޼��� ����
}// class ����
