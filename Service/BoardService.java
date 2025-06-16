package MiniProject.Service;

import java.sql.SQLException;
import java.util.Scanner;

import MiniProject.DAO.BoardDAO;
import MiniProject.DTO.BoardDTO;
import MiniProject.DTO.MemberDTO;

public class BoardService {
	// �ʵ�
	public BoardDAO boardDAO = new BoardDAO();
	public BoardDTO boardDTO = new BoardDTO();
	public MemberDTO memberDTO = new MemberDTO();
	// ������
	
	// �޼���
	public void boardMenu(Scanner inputStr) throws SQLException {
		boolean board = true;
		while (board) {
			System.out.println();
			System.out.println("=============2.�Խ��� ����=============");
			System.out.println("1.��ü ��ȸ | 2.�Խñ� �ۼ� | 3.�Խñ� ��ȸ | 4.�Խñ� ���� | 5.�Խñ� ���� | 6.������");
			System.out.print(">>> ");
			String select = inputStr.next();
			switch (select) {
			case "1":
				System.out.println("------�Խñ� ��ü ��ȸ ���񽺷� �̵��մϴ�.------");
				selectAll(inputStr);
				break;
			case "2":
				System.out.println("------�Խñ� �ۼ� ���񽺷� �̵��մϴ�.------");
				insertBoard(inputStr, boardDAO);
				break;
			case "3":
				System.out.println("------�Խñ� ��ȸ ���񽺷� �̵��մϴ�.------");
				break;
			case "4":
				System.out.println("------�Խñ� ���� ���񽺷� �̵��մϴ�.------");
				break;
			case "5":
				System.out.println("------�Խñ� ���� ���񽺷� �̵��մϴ�.------");
				break;
			case "6":
				System.out.println("�Խ��� �޴��� �����մϴ�. \n�����޴��� ���ư��ϴ�.");
				break;
			default:
				System.out.println("���� �޴��Դϴ�. �ٽ� �������ּ���(1~6)");
				break;
			}// �Խ��� switch�� ����
		}// �Խ��� while�� ����
	}// boardMenu�޼���

	private void insertBoard(Scanner inputStr, BoardDAO boardDAO) throws SQLException {
		// �Խñ� �ۼ�
		System.out.println("�Խñ� �ۼ��� ���� �α������ּ���.");
		System.out.print("id�� �Է����ּ��� : ");
		String id = inputStr.next();
		
		System.out.print("pw�� �Է����ּ��� : ");
		String pw = inputStr.next();
		
		boardDAO.insertBoard(inputStr, id, pw, memberDTO);
		System.out.println("---------------------------------");
	}// insertBoard ����

	private void selectAll(Scanner inputStr) throws SQLException {
		// �Խñ� ��ü ��ȸ�ϱ� �޴�
		boardDAO.selectAll();
		System.out.println("---------------------------------");
	}// selectAll ����

}//class ����
