package MiniProject.Service;

import java.sql.SQLException;
import java.util.Scanner;

import MiniProject.DAO.BoardDAO;
import MiniProject.DTO.BoardDTO;
import MiniProject.DTO.MemberDTO;

public class BoardService {
	// 필드
	public BoardDAO boardDAO = new BoardDAO();
	public BoardDTO boardDTO = new BoardDTO();
	public MemberDTO memberDTO = new MemberDTO();
	// 생성자
	
	// 메서드
	public void boardMenu(Scanner inputStr) throws SQLException {
		boolean board = true;
		while (board) {
			System.out.println();
			System.out.println("=============2.게시판 서비스=============");
			System.out.println("1.전체 조회 | 2.게시글 작성 | 3.게시글 조회 | 4.게시글 수정 | 5.게시글 삭제 | 6.나가기");
			System.out.print(">>> ");
			String select = inputStr.next();
			switch (select) {
			case "1":
				System.out.println("------게시글 전체 조회 서비스로 이동합니다.------");
				selectAll(inputStr);
				break;
			case "2":
				System.out.println("------게시글 작성 서비스로 이동합니다.------");
				insertBoard(inputStr, boardDAO);
				break;
			case "3":
				System.out.println("------게시글 조회 서비스로 이동합니다.------");
				break;
			case "4":
				System.out.println("------게시글 수정 서비스로 이동합니다.------");
				break;
			case "5":
				System.out.println("------게시글 삭제 서비스로 이동합니다.------");
				break;
			case "6":
				System.out.println("게시판 메뉴를 종료합니다. \n이전메뉴로 돌아갑니다.");
				break;
			default:
				System.out.println("없는 메뉴입니다. 다시 선택해주세요(1~6)");
				break;
			}// 게시판 switch문 종료
		}// 게시판 while문 종료
	}// boardMenu메서드

	private void insertBoard(Scanner inputStr, BoardDAO boardDAO) throws SQLException {
		// 게시글 작성
		System.out.println("게시글 작성을 위해 로그인해주세요.");
		System.out.print("id를 입력해주세요 : ");
		String id = inputStr.next();
		
		System.out.print("pw를 입력해주세요 : ");
		String pw = inputStr.next();
		
		boardDAO.insertBoard(inputStr, id, pw, memberDTO);
		System.out.println("---------------------------------");
	}// insertBoard 종료

	private void selectAll(Scanner inputStr) throws SQLException {
		// 게시글 전체 조회하기 메뉴
		boardDAO.selectAll();
		System.out.println("---------------------------------");
	}// selectAll 종료

}//class 종료
