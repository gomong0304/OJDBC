package MiniProject;

import java.sql.SQLException;
import java.util.Scanner;

import MiniProject.DTO.MemberDTO;
import MiniProject.Service.BoardService;
import MiniProject.Service.MemberService;

public class MiniProjectExam {
	// 메인 메서드
	// 필드
	public static MemberDTO session = new MemberDTO(); // 로그인시 정보 저장용 객체
	public static Scanner inputStr = new Scanner(System.in);
	
	// 생성자
	
	// 메서드
	public static void main(String[] args) throws SQLException {
		boolean run =true;
		while (run) {
			System.out.println("===========mbc 아카데미 홈페이지===========");
			System.out.println("=========사용하실 메뉴를 선택해주세요=========");
			System.out.println("1.회원가입 및 로그인 | 2.게시판 | 3.홈페이지 종료");
			System.out.print(">>> ");
			String select = inputStr.next();
			switch (select) {
			case "1":
				System.out.println("회원가입 및 로그인 페이지로 이동합니다");
				MemberService memberService = new MemberService();
				memberService.menu(inputStr, session);
				break;
			case "2":
				System.out.println("게시판 페이지로 이동합니다.");
				BoardService boardService = new BoardService();
				boardService.boardMenu(inputStr);
				break;
			case "3":
				System.out.println("홈페이지를 종료합니다. \n방문해주셔서 감사합니다.");
				run = false;
				break;
			default : 
				System.out.println("없는 메뉴입니다. 1~3까지만 입력해주세요.");
				break;
			}// switch문 종료
		}// while문 종료
	}// main 메서드 종료
}// class 종료
