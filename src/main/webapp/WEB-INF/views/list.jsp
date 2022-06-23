<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="layout/header.jsp" %>

<main class = "container">
		<h2>test</h2>
		<c:forEach var="board" items="${boards.content}">
				<div class="card">
  						<div class="card-body">
  								<h4 class = "card-title">${board.title}</h4>
  								<a href="/board/${board.id}" class="btn btn-primary">see details</a>
  						</div>
				</div>
		</c:forEach>
		<br/>
		
				<ul class="pagination justify-content-center">
					  <c:choose>
		<c:when test="${boards.first}">
				<li class="page-item disabled"><a class="page-link" href="/list?page=${boards.pageable.pageNumber - 1}">Previous</a></li>
		</c:when>
		<c:otherwise>
				<li class="page-item"><a class="page-link" href="/list?page=${boards.pageable.pageNumber - 1}">Previous</a></li>
		</c:otherwise>
	</c:choose>
					
					<li class="page-item"><a class="page-link" href="#">1</a></li>
  					<li class="page-item"><a class="page-link" href="#">2</a></li>
  					<li class="page-item"><a class="page-link" href="#">3</a></li>
  
	<c:choose>
		<c:when test="${boards.last}">
				<li class="page-item disabled"><a class="page-link" href="/list?page=${boards.pageable.pageNumber + 1}">Next</a></li>
		</c:when>
		<c:otherwise>
				<li class="page-item"><a class="page-link" href="/list?page=${boards.pageable.pageNumber + 1}">Next</a></li>
		</c:otherwise>
	</c:choose>
				</ul>
</main>

<%@ include file="layout/footer.jsp" %>