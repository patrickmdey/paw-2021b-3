<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@attribute name="errorNum" required="true" %>
<%@attribute name="errorMsg" required="true" %>

<c:url value="/" var="landingPage"/>
<c:url value="/resources/image/error.png" var="errorImage"/>
<div class="error-container container-height">
    <div class="d-flex justify-content-center">
        <img src="${errorImage}" width="70%" height="50%">
    </div>
    <h1 class="display-3 fw-bold">Error ${errorNum}</h1>
    <p class="lead"><spring:message code="${errorMsg}"/></p>
    <a href="${landingPage}"><spring:message code="error.return"/></a>
</div>