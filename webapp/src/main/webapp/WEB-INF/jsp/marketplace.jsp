<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url value="/" var="marketplaceUrl"/>
<c:url value="/" var="currentUrl">
    <c:forEach items="${param}" var="entry">
        <c:if test="${entry.key != 'page'}">
            <c:param name="${entry.key}" value="${entry.value}"/>
        </c:if>
    </c:forEach>
</c:url>
<html>
<h:head title="RentApp"/>
<body class="bg-color-grey">
<h:navbar loggedUser="${user}"/>
<div class="container">
    <div class="row filter-container mb-3">
        <div class="col-md-3 col-lg-3 col-12"></div>
        <div class="col-md-8 col-lg-8 d-flex justify-content-start">
            <!-- ACA SE MUESTRAN LOS PARAMETROS DE LA URL -->
            <c:if test="${ query != null && query.length() > 0 }">
                <div class="d-flex align-items-center">
                    <span class="me-2 align-middle">
                        <spring:message code="filters.marketplace.search"/><c:out value="${query}"/>
                    </span>
                </div>
            </c:if>

            <c:if test="${ param.location != null && param.location.length() > 0 }">
                <div class="d-flex align-items-center">
                    <span class="me-2 align-middle">
                        <i class="bi bi-pin-map"></i>
                        <spring:message code="filters.marketplace.location"/>
                        <c:out value="${locationsEnum[searchForm.location].name}"/>
                    </span>
                </div>
            </c:if>


            <c:if test="${ (param.category != null && param.category.length() > 0)
            || (param.user != null && param.user.length() > 0) }">
                <div class="d-flex align-items-center">
                    <spring:message code="filters.marketplace.filtering"/>
                    <c:if test="${ param.category != null && param.category.length() > 0}">
                        <c:url value="/" var="removeCategoryUrl">
                            <c:forEach var="p" items="${param}">
                                <c:if test="${p.key != 'category'}">
                                    <c:param name="${p.key}" value="${p.value}"/>
                                </c:if>
                            </c:forEach>
                        </c:url>
                        <div class="badge bg-primary mx-2 p-2">
                            <i class="bi bi-tag"></i>
                            <spring:message code="${category}"/>
                            <a href="${removeCategoryUrl}" class="text-light">X</a>
                        </div>
                    </c:if>

                    <c:if test="${ param.user != null && param.user.length() > 0}">
                        <c:url value="/" var="removeUserUrl">
                            <c:forEach var="p" items="${param}">
                                <c:if test="${p.key != 'user'}">
                                    <c:param name="${p.key}" value="${p.value}"/>
                                </c:if>
                            </c:forEach>
                        </c:url>
                        <div class="badge bg-primary mx-2 p-2"><i class="fa fa-user"></i>
                            <c:out value="${userFilter.firstName}"/>
                            <a href="${removeUserUrl}" class="text-light">X</a>
                        </div>
                    </c:if>
                </div>
            </c:if>
        </div>
    </div>
    <div class="row align-items-start justify-content-center">
        <div class="card card-style filters-card col-md-3 col-lg-3 col-12">
            <form:form modelAttribute="searchForm" action="${marketplaceUrl}" method="get" id="searchForm">
                <form:input type="number" path="user" cssClass="d-none"/>

                <div class="row">
                    <h4 class="h4 color-rentapp-black col-8"><spring:message code="filter.title"/></h4>
                        <%--                    <button class="btn btn-link col-4" type="reset">--%>
                        <%--                        <i class="h4 color-rentapp-black bi bi-x"></i>--%>
                        <%--                    </button>--%>
                </div>
                <hr/>
                <div>
                    <form:label path="query" cssClass="font-weight-bold">
                        <spring:message code="filters.marketplace.query"/>
                    </form:label>
                    <spring:message code="filterForm.search" var="searchPlaceholder"/>
                    <form:input type="text" path="query" cssClass="form-control w-100"
                                placeholder="${searchPlaceholder}"/>
                </div>

                <form:label path="category" cssClass="font-weight-bold mt-3"><spring:message
                        code="search.rentRequestForm.category"/></form:label>
                <div class="w-100">
                    <c:forEach items="${categories}" var="category">
                        <div class="w-100">
                            <spring:message code="${category.description}" var="label"/>
                            <form:radiobutton path="category" value="${category.id}" label="${label}"/>
                        </div>
                    </c:forEach>
                </div>

                <div class="mt-3">
                    <form:label path="orderBy" cssClass="font-weight-bold">
                        <spring:message code="filterForm.orderBy"/>
                    </form:label>
                    <div class="my-2 row align-items-center">
                        <div class="col-10">
                            <form:select path="orderBy" class="form-control form-control-custom">
                                <c:forEach var="option" items="${orderOptions}">
                                    <form:option value="${option.column}">
                                        <spring:message code="${option.description}"/>
                                    </form:option>
                                </c:forEach>
                            </form:select>
                        </div>
                        <i class="col-2 ms-n2 color-rentapp-black fa-lg bi bi-filter-right"></i>
                    </div>
                </div>

                <div class="mt-3">
                    <form:label path="location" cssClass="font-weight-bold">
                        <spring:message code="filterForm.location"/>
                    </form:label>
                    <div class="my-1 row align-items-center">
                        <div class="col-10">
                            <form:select path="location" class="form-control form-control-custom">
                                <form:option value=""
                                             label="Todas las ubicaciones">Todas las ubicaciones</form:option>
                                <c:forEach var="loc" items="${locations}">
                                    <form:option value="${loc.ordinal()}" label="${loc.name}">
                                        <c:out value="${loc.name}"/>
                                    </form:option>
                                </c:forEach>
                            </form:select>

                        </div>
                        <i class="col-2 ms-n2 color-rentapp-black fa-lg bi bi-pin-map"></i>
                    </div>
                </div>

                <button type="submit" class="btn bg-color-action btn-dark mt-4 w-100">
                    <spring:message code="filterForm.search"/>
                </button>
            </form:form>
        </div>

        <div class="col-md-9 col-lg-9 col-12">
            <c:choose>
                <c:when test="${articles.size() == 0}">
                    <div class="card card-style align-items-center justify-content-start">
                        <h2 class="h2">
                            <spring:message code="marketplace.articleNotFound.title"/>
                        </h2>
                        <p class="lead">
                            <spring:message code="marketplace.articleNotFound.subtitle"/>
                        </p>
                    </div>
                </c:when>
                <c:otherwise>
                    <h:allArticles articles="${articles}" maxPage="${maxPage}" currentUrl="${currentUrl}"/>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<h:footer/>
</body>
<h:mainScript/>
</html>
