<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="control" tagdir="/WEB-INF/tags/Controls" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url value="/user/delete" var="deleteUrl"/>
<c:url value="/user/edit" var="editUrl"/>

<html>
<h:head title="Mi perfil"/>

<body class="bg-color-grey">
<h:navbar loggedUser="${user}"/>
<div class="main-container">

    <!-- Modal -->
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">
                        <spring:message code="account.view.form.titleDelete"/>
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <spring:message code="account.view.form.warningDelete"/>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn bg-color-secondary" data-bs-dismiss="modal">
                        <spring:message code="account.view.form.buttonCancel"/>
                    </button>
                    <form:form action="${deleteUrl}" method="post">
                        <button type="submit" class="btn btn-danger">
                            <spring:message code="account.view.form.buttonDelete"/>
                        </button>
                    </form:form>
                </div>
            </div>
        </div>
    </div>

    <div class="card shadow card-style">
        <div class="row align-items-center">
            <div class="col-11">
                <h3 class="card-title"><spring:message code="account.view.form.title"/></h3>
            </div>

            <div class="col-1">
                <i class="fa-lg bi bi-three-dots-vertical overflow-icon"
                   data-bs-toggle="dropdown" aria-expanded="false">

                </i>
                <ul class="dropdown-menu dropdown-menu-end">
                    <li>
                        <a class="dropdown-item" href="${editUrl}">
                            <spring:message code="account.view.form.editButton"/>

                        </a>
                    </li>
                    <li>
                        <a class="dropdown-item" style="background-color: rgb(249, 103, 103);" data-bs-toggle="modal"
                           data-bs-target="#exampleModal">
                            <spring:message code="account.view.form.buttonDelete"/>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
        <hr/>
        <h:account mode="view" locations="${locations}"/>
    </div>

    <div>
        <h3 class="h3 my-2"><spring:message code="account.view.myArticles"/></h3>
        <div class="row row-cols-3 justify-content-center">
            <c:forEach var="article" items="${articles}">
                <div class="col d-flex justify-content-center">
                    <h:marketplaceCard title="${article.title}" price="${article.pricePerDay}"
                                       id="${article.id}"
                                       location="${article.location}"
                                       image_id="${article.images.size()==0 ? 1 : article.images.get(0)}"/>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<h:footer/>
</body>


<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script src="<c:url value="/resources/js/main.js" />" defer></script>
</html>