<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Bootstrap demo</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
<div class="container-fluid">
  <div class="row">
    <!--    NavBar 가져와서 적용해보기-->
    <div class="col">
      <nav class="navbar navbar-expand-lg " style="background-color: #b0f89d;">
        <!--      <nav class="navbar bg-primary navbar-expand-lg bg-body-tertiary bg-primary"  data-bs-theme="" >-->
        <!--      <nav class="navbar bg-primary" data-bs-theme="dark">-->
        <div class="container-fluid">
          <a class="navbar-brand" href="/todo/list">목록가기</a>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo02" aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarTogglerDemo02">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
              <li class="nav-item">
                <a class="nav-link active" aria-current="page" href="/todo/register">글작성</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#">Link</a>
              </li>
              <li class="nav-item">
                <a class="nav-link disabled" aria-disabled="true">Disabled</a>
              </li>
            </ul>
            <form class="d-flex" role="search">
              <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
              <button class="btn btn-outline-success" type="submit">Search</button>
            </form>
          </div>
        </div>
      </nav>
    </div>
  </div>
  <!--  Navbar 종료-->
  <!--  본문 시작-->
  <div class="row content">
    <div class="col">
      <!--      부트 스트랩 5.3 Card 컴포넌트 Header and Footer 의 샘플 가져오기-->
      <div class="card">
        <div class="card-header">
          목록
        </div>
        <div class="card-body">
<%--         리스트의 목록의 요소를 넣기--%>
<%--          ${dtoList}--%>
          <table class="table">
            <thead>
              <tr>
                <th scope="col">Tno</th>
                <th scope="col">Title</th>
                <th scope="col">Writer</th>
                <th scope="col">DueDate</th>
                <th scope="col">Finished</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach items="${dtoList}" var="dto">
                <tr>
                  <th scope="row"><c:out value="${dto.tno}"/> </th>
                  <td><a href="/todo/read?tno=${dto.tno}"><c:out value="${dto.title}"/></a> </td>
                  <td><c:out value="${dto.writer}"/> </td>
                  <td><c:out value="${dto.dueDate}"/> </td>
                  <td><c:out value="${dto.finished}"/> </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>

        </div>
      </div>
    </div>
  </div>
  <div class="row footer">
    <div class="row fixed-bottom" style="z-index: -100">
      <footer class="py-1 my-1">
        <p class="text-center text-muted">Footer</p>
      </footer>
    </div>

  </div>
</div>



<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
