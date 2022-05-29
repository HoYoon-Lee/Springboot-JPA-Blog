<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp"%>
<html>
<head>
  <title>Join</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>

<div class="container">
  <form>
    <input type="hidden" id="id" value="${principal.user.id}">
    <div class="form-group">
      <label for="userName">User Name:</label>
      <input type="text" value="${principal.user.userName}" class="form-control" placeholder="Enter user name" id="userName" readonly>
    </div>

    <div class="form-group">
      <label for="password">Password:</label>
      <input type="password" class="form-control" placeholder="Enter password" id="password">
    </div>

    <div class="form-group">
      <label for="email">Email address:</label>
      <input type="email" value="${principal.user.email}" class="form-control" placeholder="Enter email" id="email">
    </div>
  </form>
  <button id="btn-update" class="btn btn-primary">저장</button>
</div>

<script src="/js/user.js"></script>

<%@ include file="../layout/footer.jsp" %>
</body>
</html>