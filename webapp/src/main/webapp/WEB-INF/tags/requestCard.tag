<%@ tag language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="articleName" required="true" %>
<%@ attribute name="renterFirstName" required="true" %>
<%@ attribute name="renteLastName" required="true" %>
<%@ attribute name="startDate" required="true" %>
<%@ attribute name="endDate" required="true" %>
<%@ attribute name="message" required="true" %>
<%@ attribute name="id" required="true" %>
<%@ attribute name="state" required="true" %>
<%@ attribute name="userId" required="true" %>
<%@ attribute name="renterEmail" required="true" %>


<c:url value="/user/my-requests/${id}/accept" var="acceptRequest"/>
<c:url value="/user/my-requests/${id}/delete" var="deleteRequest"/>


<div class="card card-style">
    <h4 class="h4 mb-2"><c:out value="${articleName}"/></h4>
    <p class="lead fw-bold mb-2"><spring:message code="myAccount.ownerRequests.requestFrom"
                                                 arguments="${renterFirstName}, ${renteLastName}"/></p>
    <div class="row">
        <p class="lead col-4"><spring:message code="myAccount.ownerRequests.startDate"
                                              arguments="${startDate}"/></p>
        <p class="lead col-5 ms-n3"><spring:message code="myAccount.ownerRequests.endDate"
                                                    arguments="${endDate}"/></p>
    </div>
    <c:if test="${state == 1}">
        <a href="mailto:${renterEmail}" class="lead">
            <c:out value="${renterEmail}"/>
        </a>
    </c:if>
    <p class="lead fw-bold">
        <spring:message code="myAccount.ownerRequests.message"/>
    </p>
    <p><c:out value="${message}"/></p>
    <c:if test="${state == 0}">
        <div class="d-flex justify-content-end">
            <form method="post" action="${acceptRequest}">
                <button type="submit" class="btn btn-success me-1">
                    <spring:message code="myAccount.ownerRequests.acceptButton"/>
                </button>
            </form>

            <form method="post" action="${deleteRequest}">
                <button type="submit" class="btn btn-danger">
                    <spring:message code="myAccount.ownerRequests.rejectButton"/>
                </button>
            </form>
        </div>
    </c:if>
</div>