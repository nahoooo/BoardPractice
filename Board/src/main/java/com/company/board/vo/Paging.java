package com.company.board.vo;

import java.util.Scanner;

public class Paging {

	   public static void main(String[] args) {
	      Scanner sc = new Scanner(System.in);
	      int page; // 현재 페이지 번호

	      int countList = 10; // 한 페이지에 출력될 게시물 수(10개를 기준으로 잡음)
	      int countPage = 10; // 한 화면에 출력될 페이지 수(통상적으로 10개 페이지를 나오게 함)

	      int totalCount = 255; // 전체 게시물의 개수 255개 기준으로 잡음. (원래는 select count(*) as totalCount from board)
	      int totalPage = totalCount / countList; // totalPage는 전체 페이지 수 (전체 게시물 / 한 페이지에 출력될 게시물 수)

	      if (totalCount % countList > 0) {// totalCount를 countList로 나눈 나머지 값이 존재한다는 것은 한 페이지가 더 있다는 의미이다.
	         totalPage++;
	      }
	      
	      while (true) {
	         System.out.print("현재 페이지 번호를 입력:");
	         page=sc.nextInt();
	         if(page==-1)
	            break;
	         if (totalPage < page) {// 현재 페이지가 전체 페이지보다 크다면 이는 출력될 페이지 범위를 벗어난 현제 페이지를 의미
	            // 따라서 현재페이지를 가장 끝 페이지인 totalPage로 이동시킨다.
	            page = totalPage;
	         }

	         int startPage = ((page - 1) / 10) * 10 + 1;
	         // 현재 페이지를 기준으로 한 화면에서 시작 페이지 값을 보여준다.

	         int endPage = startPage + countPage - 1;
	         // 현재 페이지를 기준으로 한 화면에서 끝 페이지 값을 보여준다.
	         
	         // 전체 게시물이 255개 기준으로 할 경우 전체 페이지는 26개가 나온다. 내가 보려고 하는 현재
	         // 페이지가 22페이지라고 했을 때 21에서 30까지 나오면 전체 페이지는 26까지 있기 때문에 실제
	         // 리스트를 가지고 있지 않은 페이지가 발생한다. (27,28,29,30 페이지 해당)
	         if (endPage > totalPage) {
	            endPage = totalPage;
	         }

	         if (startPage > 1) {
	            System.out.print("<a href=\"?page=1\">처음</a>");
	         }

	         if (page > 1) {
	            System.out.println("<a href=\"?page=" + (page - 1) + "\">이전</a>");
	         }

	         for (int iCount = startPage; iCount <= endPage; iCount++) {
	            if (iCount == page) {
	               System.out.print(" <b>" + iCount + "</b>");
	            } else {
	               System.out.print(" " + iCount + " ");
	            }
	         }

	         if (page < totalPage) {
	            System.out.println("<a href=\"?page=" + (page + 1) + "\">다음</a>");
	         }

	         if (endPage < totalPage) {
	            System.out.print("<a href=\"?page=" + totalPage + "\">끝</a>");
	         }
	         
	         System.out.println();
	      }
	      sc.close();
	   }


}
