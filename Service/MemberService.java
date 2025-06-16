package MiniProject.Service;

import java.sql.SQLException;
import java.util.Scanner;

import MiniProject.DAO.MemberDAO;
import MiniProject.DTO.MemberDTO;

public class MemberService {
	// member의 DTO와 DAO를 활용해서 CRUD를 처리한다.
	// 필드
	public MemberDAO memberDAO = new MemberDAO();
	public MemberDTO memberDTO = new MemberDTO();
	// 기본생성자
	
	// 메서드
	public void menu(Scanner inputStr, MemberDTO session) throws SQLException {
		boolean menuRun = true;
		while (menuRun) {
			System.out.println();
			System.out.println("=============1. 회원가입 및 로그인=============");
			System.out.println("1.회원가입 | 2.로그인 | 3.전체 조회 | 4.정보 조회 | 5.정보 수정 | 6. 탈퇴 | 7.나가기");
			System.out.print(">>> ");
			String memSelect = inputStr.next();
			switch (memSelect) {
			case "1":
				System.out.println("------회원가입 서비스로 이동합니다.------");
				insertMember(session, inputStr);
				break;
			case "2":
				System.out.println("------로그인 서비스로 이동합니다.------");
				loginMember(session ,inputStr);
				break;
			case "3":
				System.out.println("------전체 조회 서비스로 이동합니다.------");
				selectAll(inputStr);
				break;
			case "4":
				System.out.println("------정보 조회 서비스로 이동합니다.------");
				readOne(inputStr);
				break;
			case "5":
				System.out.println("------정보 수정 서비스로 이동합니다.------");
				modify(session, inputStr);
				break;
			case "6":
				System.out.println("------탈퇴 서비스로 이동합니다.------");
				deleteOne(session, inputStr);
				break;
			case "7":
				System.out.println("회원가입 및 로그인 메뉴를 종료합니다. \n이전메뉴로 돌아갑니다.");
				memberDAO.connection.close();
				menuRun = false;
				break;
			default :
				System.out.println("없는 메뉴입니다. 다시 선택해주세요(1~7)");
				break;
			}// 회원가입 및 로그인 switch문 종료
		}// 회원가입 및 로그인 while문 종료
	}// member 메뉴 서비스 종료

	private void deleteOne(MemberDTO session, Scanner inputStr) throws SQLException {
		// 회원을 삭제 해보자.
		System.out.println();
		System.out.println("탈퇴 진행을 위해서는 로그인이 필요합니다.");
		System.out.print("id를 입력해주세요 : ");
		String id = inputStr.next();
		
		System.out.print("pw를 입력해주세요 : ");
		String pw = inputStr.next();
		
		memberDAO.deleteOne(inputStr, session, id, pw);
		System.out.println("---------------------------------");
	}// deleteOne메서드 종료

	private void modify(MemberDTO session, Scanner inputStr) throws SQLException {
		// 정보수정 메서드
		System.out.println();
		System.out.println("정보를 수정하기 위해서는 로그인이 필요합니다.");
		System.out.print("id를 입력해주세요 : ");
		String id = inputStr.next();
		
		System.out.print("pw를 입력해주세요 : ");
		String pw = inputStr.next();
		
		memberDAO.modify(session, inputStr, id, pw);
		System.out.println("---------------------------------");
	}// modify 메서드 종료

	private void readOne(Scanner inputStr) throws SQLException {
		// 회원 한명의 정보를 조회해보자
		System.out.println();
		System.out.println("정보 조회를 하기 위해서는 로그인이 필요합니다.");
		System.out.print("id를 입력해주세요 : ");
		String id = inputStr.next();
		
		System.out.print("pw를 입력해주세요 : ");
		String pw = inputStr.next();
		
		memberDAO.readOne(inputStr, id, pw);
		System.out.println("---------------------------------");
	}// readOne메서드 종료

	private void selectAll(Scanner inputStr) throws SQLException {
		// 전체 회원을 조회해보자
		memberDAO.selectAll(inputStr);
		System.out.println("---------------------------------");
	}// selectAll 메서드 종료

	
	private void loginMember(MemberDTO session, Scanner inputStr) throws SQLException {
		// 회원가입한 사람이 로그인에 성공할수있도록 만들어보자
		
		System.out.print("id를 입력해주세요 : ");
		String id = inputStr.next();
		
		System.out.print("pw를 입력해주세요 : ");
		String pw = inputStr.next();
		
		memberDAO.loginMember(session, id, pw, memberDTO);
		System.out.println("---------------------------------");
	}// loginMember 메서드 종료

	
	private void insertMember(MemberDTO session, Scanner inputStr) throws SQLException {
		// 키보드로 id와 pw를 입력받아서 회원가입을 시켜보자.
		memberDAO.insertMember(session, inputStr);
		System.out.println("---------------------------------");
	}// insertMember 메서드 종료
}// class 종료
