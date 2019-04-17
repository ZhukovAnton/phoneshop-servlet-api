<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<jsp:useBean id="recentlyViewed" type="java.util.ArrayList" scope="request"/>
<jsp:useBean id="reviews" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product Detail">
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>Description</td>
            <td class="price">Price</td>
        </tr>
        </thead>
        <tr>
            <td>
                <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
            </td>
            <td>${product.description}</td>
            <td class="price">
                <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
            </td>
        </tr>
    </table>
</tags:master>
<p>
    <form method="post">
        <input name="quantity" value="${!empty param.quantity ? param.quantity : 1}"/>
        <button>Add to Cart</button>
    </form>
</p>
<c:choose>
    <c:when test="${!empty numberFormatError || !empty outOfStockError}">
        <p style="color: firebrick">
            <c:choose>
                <c:when test="${! empty numberFormatError}">
                    <p style="color: firebrick">
                            ${numberFormatError}
                    </p>
                </c:when>
                <c:otherwise>
                    <p style="color: firebrick">
                            ${outOfStockError}
                    </p>
                </c:otherwise>
            </c:choose>
        </p>
    </c:when>
    <c:otherwise>
        <p style="color: forestgreen">
            ${param.message}
        </p>
    </c:otherwise>
</c:choose>
<tags:recentlyViewed recentlyViewed="${recentlyViewed}"/>
<table>
    <br>
    <thead>
    <tr>
        <td>
            Name
        </td>
        <td>
            Rate
        </td>
        <td>
            Comment
        </td>
    </tr>
    </thead>
    <c:forEach var="item" items="${reviews}">
        <tr>
            <td>
                    ${item.name}
            </td>
            <td>
                    ${item.rate}
            </td>
            <td>
                    ${item.comment}
            </td>
        </tr>
    </c:forEach>
</table>
<br>
<form method="post">
    <input name="name"/>
    <c:if test="${not empty nameError}">
                <span style="color: firebrick">
                        ${nameError}
                </span>
    </c:if>
    <br>
    <input name="rate" type="number"/>
    <c:if test="${not empty rateError}">
                <span style="color: firebrick">
                        ${rateError}
                </span>
    </c:if>
    <br>
    <input name="comment"/>
    <c:if test="${not empty commentError}">
                <span style="color: firebrick">
                        ${commentError}
                </span>
    </c:if>
    <br>
    <button formmethod="post" action="${pageContext.servletContext.contextPath}/review/${product.id}">
        Add comment
    </button>
</form>