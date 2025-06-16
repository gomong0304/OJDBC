package MiniProject;

import java.sql.SQLException;
import java.util.Scanner;

import MiniProject.DTO.MemberDTO;
import MiniProject.Service.BoardService;
import MiniProject.Service.MemberService;

public class MiniProjectExam {
	// ���� �޼���
	// �ʵ�
	public static MemberDTO session = new MemberDTO(); // �α��ν� ���� ����� ��ü
	public static Scanner inputStr = new Scanner(System.in);
	
	// ������
	
	// �޼���
	public static void main(String[] args) throws SQLException {
		boolean run =true;
		while (run) {
			System.out.println("===========mbc ��ī���� Ȩ������===========");
			System.out.println("=========����Ͻ� �޴��� �������ּ���=========");
			System.out.println("1.ȸ������ �� �α��� | 2.�Խ��� | 3.Ȩ������ ����");
			System.out.print(">>> ");
			String select = inputStr.next();
			switch (select) {
			case "1":
				System.out.println("ȸ������ �� �α��� �������� �̵��մϴ�");
				MemberService memberService = new MemberService();
				memberService.menu(inputStr, session);
				break;
			case "2":
				System.out.println("�Խ��� �������� �̵��մϴ�.");
				BoardService boardService = new BoardService();
				boardService.boardMenu(inputStr);
				break;
			case "3":
				System.out.println("Ȩ�������� �����մϴ�. \n�湮���ּż� �����մϴ�.");
				run = false;
				break;
			default : 
				System.out.println("���� �޴��Դϴ�. 1~3������ �Է����ּ���.");
				break;
			}// switch�� ����
		}// while�� ����
	}// main �޼��� ����
}// class ����
